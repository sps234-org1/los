package com.jocata.loansystem.service.impl;

import com.jocata.loansystem.bean.loanapplication.LoanApplicationRequestBean;
import com.jocata.loansystem.bean.response.AadhaarResponse;
import com.jocata.loansystem.bean.response.CibilResponse;
import com.jocata.loansystem.bean.response.PanResponse;
import com.jocata.loansystem.dao.CreditScoreDao;
import com.jocata.loansystem.dao.CustomerDao;
import com.jocata.loansystem.dao.LoanApplicationDao;
import com.jocata.loansystem.dao.LoanProductDao;
import com.jocata.loansystem.entity.CustomerDetails;
import com.jocata.loansystem.entity.LoanApplicationDetails;
import com.jocata.loansystem.entity.LoanProductDetails;
import com.jocata.loansystem.service.ExternalService;
import com.jocata.loansystem.service.LoanApplicationService;
import com.jocata.loansystem.service.RiskAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

    @Autowired
    private final LoanApplicationDao loanApplicationDao;

    @Autowired
    private final CreditScoreDao creditScoreDao;

    @Autowired
    private final CustomerDao customerDao;

    @Autowired
    private final LoanProductDao loanProductDao;

    @Autowired
    private final ExternalService externalService;

    @Autowired
    private final RiskAssessmentService riskAssessmentService;

    @Autowired
    public LoanApplicationServiceImpl(LoanApplicationDao loanApplicationDao, CreditScoreDao creditScoreDao
            , CustomerDao customerDao, LoanProductDao loanProductDao, ExternalService externalService, RiskAssessmentService riskAssessmentService) {
        this.loanApplicationDao = loanApplicationDao;
        this.creditScoreDao = creditScoreDao;
        this.customerDao = customerDao;
        this.loanProductDao = loanProductDao;
        this.externalService = externalService;
        this.riskAssessmentService = riskAssessmentService;
    }

    String panUrl = "http://localhost:8080/externalservices2/api/v1/pan/getPan";
    String aadhaarUrl = "http://localhost:8080/externalservices2/api/v1/aadhaar/getAadhaar";

    String addCibilUrl = "http://localhost:8080/externalservices2/api/v1/cibil/create";

    long start ;

    @Override
    public String createLoanApplication(LoanApplicationRequestBean loanApplicationRequestBean) {

        start = System.currentTimeMillis();
        String cibilUrl = "http://localhost:8080/externalservices2/api/v1/cibil/getCibil";
        AadhaarResponse aadhaarResponse = null;
        PanResponse panResponse = null;
        CibilResponse cibilResponse = null;

        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            Future<AadhaarResponse> aadhaarFuture = executorService.submit(() -> externalService.getAadhaarResponse(aadhaarUrl, loanApplicationRequestBean));
            Future<PanResponse> panFuture = executorService.submit(() -> externalService.getPanResponse(panUrl, loanApplicationRequestBean));
            Future<CibilResponse> cibilFuture = executorService.submit(() -> externalService.getCibilResponse(cibilUrl, loanApplicationRequestBean));
            aadhaarResponse = aadhaarFuture.get();
            panResponse = panFuture.get();
            cibilResponse = cibilFuture.get();
        } catch (Exception e) {
            msg("Error while fetching data from external services: " + e.getMessage());
        }

        if ( aadhaarResponse == null ) {
            return msg("Aadhaar response is null");
        }
        if( cibilResponse == null ) {
            return msg("Cibil response is null");
        }
        if( panResponse == null ) {
            return msg("Pan response is null");
        }

        return setDetails(panResponse, aadhaarResponse, cibilResponse, loanApplicationRequestBean);
    }


    public String setDetails(PanResponse panResponse, AadhaarResponse aadhaarResponse, CibilResponse cibilResponse,
                           LoanApplicationRequestBean loanApplicationRequestBean) {

        CustomerDetails customerDetails = customerDao.addCustomerUsingAadhaar(aadhaarResponse);
        if (customerDetails == null) {
            return msg("Customer details is null");
        }
        creditScoreDao.addCreditScoreDetailsUsingCibilResponse(cibilResponse, customerDetails);

        String customerId = customerDetails.getCustomerId().toString();
        LoanApplicationDetails loanApplicationDetails = addLoanApplication(customerId, customerDetails, loanApplicationRequestBean);

        if (loanApplicationDetails == null) {
            return msg("Loan application details is null");
        }

        String res = riskAssessmentService.addRiskAssessmentDetailsUsingCibil(cibilResponse, loanApplicationRequestBean, loanApplicationDetails);
        logger.info(res);

        int newOutstandingBalance = (int) (Double.parseDouble(cibilResponse.getTotalOutstandingBalance()) + loanApplicationDetails.getLoanAmount());

        setCibilDetails(newOutstandingBalance, cibilResponse);
        logger.info("Cibil details added successfully :{}", cibilResponse.getPan());

        logger.info("Time taken for populating details : {} ms", System.currentTimeMillis() - start);
        return "Loan application created successfully";
    }

    public void setCibilDetails(long newOutstandingBalance, CibilResponse cibilResponse) {

        CibilResponse newCibilInfo = new CibilResponse();
        newCibilInfo.setPan(cibilResponse.getPan());
        newCibilInfo.setCreditScore(cibilResponse.getCreditScore());
        newCibilInfo.setCreditHistory(cibilResponse.getCreditHistory());
        newCibilInfo.setTotalOutstandingBalance(String.valueOf(newOutstandingBalance));
        newCibilInfo.setRecentCreditInquiries(cibilResponse.getRecentCreditInquiries());
        newCibilInfo.setPaymentHistory(cibilResponse.getPaymentHistory());
        newCibilInfo.setCreditLimit(cibilResponse.getCreditLimit());
        newCibilInfo.setStatus("Active");
        newCibilInfo.setReportDate(String.valueOf(LocalDate.now()));
        externalService.addCibilDetails(addCibilUrl, newCibilInfo);
    }

    public LoanApplicationDetails addLoanApplication(String customerId, CustomerDetails customerDetails, LoanApplicationRequestBean loanApplicationRequestBean) {

        long loanAmount = loanApplicationRequestBean.getReqLoanAmount();
        LoanProductDetails loanProductDetails = loanProductDao.getLoanProductByAmount(loanAmount);
        Integer productId = loanProductDetails.getId();
        if (productId == null) {
            msg("Loan product not found");
            return null;
        }
        if (loanAmount < 0) {
            msg("Loan amount cannot be negative");
            return null;
        }
        if (loanAmount > 1000000) {
            msg("Loan amount cannot exceed 1000000");
            return null;
        }
        if (loanAmount < 10000) {
            msg("Loan amount cannot be less than 10000");
            return null;
        }
        LoanApplicationDetails loanApplicationDetails = new LoanApplicationDetails();

        loanApplicationDetails.setCustomerId(Integer.parseInt(customerId));
        loanApplicationDetails.setProductId(productId);
        loanApplicationDetails.setLoanAmount(loanAmount);
        loanApplicationDetails.setLoanProductDetails(loanProductDetails);
        loanApplicationDetails.setCustomerDetails(customerDetails);
        loanApplicationDetails.setApplicationDate(LocalDate.now().toString());
        loanApplicationDetails.setLoanAmount(loanAmount);
        loanApplicationDetails.setLoanPurpose(loanApplicationRequestBean.getLoanPurpose());
        loanApplicationDetails.setStatus("Applied");
        return loanApplicationDao.addLoanApplication(loanApplicationDetails);
    }

    public LoanApplicationDetails getLoanApplicationDetail(Integer loanApplicationId) {
        return loanApplicationDao.getLoanApplicationByCustomerId(loanApplicationId.toString());
    }

    public List<LoanApplicationDetails> getAllLoanApplications() {
        return loanApplicationDao.getAllLoanApplications();
    }

    public String updateLoanApplication(LoanApplicationDetails loanApplicationDetails) {
        return loanApplicationDao.updateLoanApplication(loanApplicationDetails);
    }

    public String deleteLoanApplication(Long id) {
        return loanApplicationDao.deleteLoanApplication(id);
    }

    public String msg(String m) {
        logger.info(m);
        return m;
    }

}
