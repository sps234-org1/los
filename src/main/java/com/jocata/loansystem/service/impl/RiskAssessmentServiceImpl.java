package com.jocata.loansystem.service.impl;

import com.jocata.loansystem.bean.loanapplication.LoanApplicationRequestBean;
import com.jocata.loansystem.bean.loanproduct.LoanProductResponseBean;
import com.jocata.loansystem.bean.response.CibilResponse;
import com.jocata.loansystem.dao.RiskAssessmentDao;
import com.jocata.loansystem.entity.LoanApplicationDetails;
import com.jocata.loansystem.entity.RiskAssessmentDetails;
import com.jocata.loansystem.service.ExternalService;
import com.jocata.loansystem.service.LoanProductService;
import com.jocata.loansystem.service.RiskAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private static final Logger logger = LoggerFactory.getLogger(RiskAssessmentServiceImpl.class);

    @Autowired
    private final LoanProductService loanProductService;

    @Autowired
    private final RiskAssessmentDao riskAssessmentDao;

    public RiskAssessmentServiceImpl(LoanProductService loanProductService, RiskAssessmentDao riskAssessmentDao) {
        this.loanProductService = loanProductService;
        this.riskAssessmentDao = riskAssessmentDao;
    }

    public String addRiskAssessmentDetailsUsingCibil(CibilResponse cibilResponse, LoanApplicationRequestBean loanApplicationRequestBean, LoanApplicationDetails loanApplicationDetails) {

        long annualIncome = loanApplicationRequestBean.getAnnualIncome();
        double requestedLoanAmount = loanApplicationRequestBean.getRequestedTerm();

        LoanProductResponseBean loanProductResponseBean = loanProductService.getLoanProductsByTenure("short");
        logger.info("Loan Product Response fetched : {} term" , loanProductResponseBean.getProductName() );
        int termMonths = loanProductResponseBean.getTermMonths();
        double interestRate = loanProductResponseBean.getInterestRate();

        int creditScore = Integer.parseInt(cibilResponse.getCreditScore());
        int creditInquiries = Integer.parseInt(cibilResponse.getRecentCreditInquiries());
        double totalOutstandingBalance = Double.parseDouble(cibilResponse.getTotalOutstandingBalance());

        double emiAmount = annualIncome - totalOutstandingBalance;
        double principal = calculatePrincipal(emiAmount, interestRate/termMonths, (int) termMonths );

        String approvalStatus = checkEligibility( creditScore, creditInquiries, principal, totalOutstandingBalance);
        if (! approvalStatus.equals("Eligible") ) {
            logger.info( approvalStatus );
            approvalStatus = "Not Approved";
        }

        long approvedAmount = 0;

        if ( principal < requestedLoanAmount ) {
            approvedAmount = (long) principal;
            logger.info("Principal amount is : {} Sanctioned amount is : {}", principal, principal );

        }
        else {
            approvedAmount = (long) requestedLoanAmount;
            logger.info("Principal amount is : {} Sanctioned amount is : {}", principal, requestedLoanAmount);
        }
        return setRiskAssessmentDetails( approvalStatus, approvedAmount, termMonths, creditScore, annualIncome, loanApplicationDetails );
    }

    String setRiskAssessmentDetails(String approvalStatus, double approvedAmount, int approvedTerm, int creditScore, double annualIncome, LoanApplicationDetails loanApplicationDetails) {

        RiskAssessmentDetails riskAssessmentDetails = new RiskAssessmentDetails();
        riskAssessmentDetails.setApprovalStatus(approvalStatus);
        riskAssessmentDetails.setApprovedAmount((double) Math.round(approvedAmount));
        riskAssessmentDetails.setApprovedTerm(approvedTerm);
        riskAssessmentDetails.setCreditScore(creditScore);
        riskAssessmentDetails.setIncome(annualIncome);
        riskAssessmentDetails.setAssessmentDate(String.valueOf(new Date()));
        riskAssessmentDetails.setLoanApplicationDetails( loanApplicationDetails );

        return riskAssessmentDao.addRiskAssessmentDetails(riskAssessmentDetails);
    }

    double calculatePrincipal(double emiAmount, double monthlyInterestRate, int termMonths) {

        double principal = 0;
        try {
            principal = emiAmount * (1 - Math.pow(1 + monthlyInterestRate,-termMonths)) / monthlyInterestRate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return principal;
    }

    String checkEligibility( int creditScore, int creditInquiries, double eligibleLoanAmount, double totalOutstandingBalance) {

        String res = "Eligible";
        if (creditScore <= 750) {
            res = "Not Eligible due to low credit score";
        }
        if (creditInquiries > 15) {
            res = "Not Eligible due to high credit inquiries";
        }
        if (totalOutstandingBalance < eligibleLoanAmount) {
            res = "Not Eligible due to high outstanding balance";
        }
        return res;
    }

    String msg(String m) {
        logger.info(m);
        return m;
    }

    ;

}
