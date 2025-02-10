package com.jocata.loansystem.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jocata.loansystem.bean.loanapplication.LoanApplicationRequestBean;
import com.jocata.loansystem.bean.loanproduct.LoanProductResponseBean;
import com.jocata.loansystem.bean.response.AadhaarResponse;
import com.jocata.loansystem.bean.response.CibilResponse;
import com.jocata.loansystem.bean.response.PanResponse;
import com.jocata.loansystem.dao.CreditScoreDao;
import com.jocata.loansystem.dao.CustomerDao;
import com.jocata.loansystem.dao.LoanApplicationDao;
import com.jocata.loansystem.dao.LoanProductDao;
import com.jocata.loansystem.entity.CreditScoreDetails;
import com.jocata.loansystem.entity.CustomerDetails;
import com.jocata.loansystem.entity.LoanApplicationDetails;
import com.jocata.loansystem.entity.LoanProductDetails;
import com.jocata.loansystem.service.LoanApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    private final RestTemplate restTemplate;

    @Autowired
    public LoanApplicationServiceImpl(RestTemplate restTemplate, LoanApplicationDao loanApplicationDao, CreditScoreDao creditScoreDao
            , CustomerDao customerDao, LoanProductDao loanProductDao) {
        this.restTemplate = restTemplate;
        this.loanApplicationDao = loanApplicationDao;
        this.creditScoreDao = creditScoreDao;
        this.customerDao = customerDao;
        this.loanProductDao = loanProductDao;
    }

    @Override
    public String createLoanApplication(LoanApplicationRequestBean loanApplicationRequestBean) {

        long start = System.currentTimeMillis();

        String panUrl = "http://localhost:8080/externalservices2/api/v1/pan/getPan";
        String aadhaarUrl = "http://localhost:8080/externalservices2/api/v1/aadhaar/getAadhaar";
        String cibilUrl = "http://localhost:8080/externalservices2/api/v1/cibil/getCibil";

        AadhaarResponse aadhaarResponse = null;
        PanResponse panResponse = null;
        CibilResponse cibilResponse = null;

        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            /**/
            Future<AadhaarResponse> aadhaarFuture = executorService.submit(() -> getAadhaarResponse(aadhaarUrl));
            Future<PanResponse> panFuture = executorService.submit(() -> getPanResponse(panUrl));
            Future<CibilResponse> cibilFuture = executorService.submit(() -> getCibilResponse(cibilUrl));

            aadhaarResponse = aadhaarFuture.get();
            panResponse = panFuture.get();
            cibilResponse = cibilFuture.get();

        } catch (Exception e) {
            msg("Error while fetching data from external services: " + e.getMessage());
        }


        logger.info("Time taken to create loan application: {} ms", System.currentTimeMillis() - start);

        return setDetails(panResponse, aadhaarResponse, cibilResponse, loanApplicationRequestBean);
    }


    public String setDetails(PanResponse panResponse, AadhaarResponse aadhaarResponse, CibilResponse cibilResponse,
                             LoanApplicationRequestBean loanApplicationRequestBean) {

        if (aadhaarResponse == null) {
            return msg("Aadhaar details not found");
        }
        setCustomerDetails(aadhaarResponse);

        CustomerDetails customerDetails = customerDao.getCustomerDetailsByAadhaar(loanApplicationRequestBean.getAadhaarNum());
        if (customerDetails == null) {
            return msg("Customer details not found");
        }

        String customerId = customerDetails.getCustomerId().toString();

        if (cibilResponse == null) {
            return msg("Cibil details not found");
        }
        setCreditScoreDetails(customerId, cibilResponse);

        if (panResponse == null) {
            return msg("PAN details not found");
        }

        return setLoanApplicationDetails(customerId, customerDetails, loanApplicationRequestBean);
    }

    public String msg(String m) {
        logger.info(m);
        return m;
    }

    public void setCustomerDetails(AadhaarResponse aadhaarResponse) {

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setIdentityNumber(aadhaarResponse.getAadhaarNum());
        String name[] = aadhaarResponse.getFullName().split(" ");
        customerDetails.setFirstName(name[0]);
        customerDetails.setLastName(name[1]);
        customerDetails.setDob(aadhaarResponse.getDob());
        customerDetails.setAddress(aadhaarResponse.getAddress());
        customerDetails.setPhoneNumber(aadhaarResponse.getContactNumber());
        customerDetails.setEmail(aadhaarResponse.getEmail());

        CustomerDetails customerDetails2 = customerDao.getCustomerDetailsByAadhaar(aadhaarResponse.getAadhaarNum());

        if (customerDetails2 == null) {
            customerDao.createCustomer(customerDetails);
        } else {
            logger.info("Customer already exists");
            customerDetails.setCustomerId(customerDetails2.getCustomerId());
            customerDao.updateCustomer(customerDetails);
        }
    }


    public void setCreditScoreDetails(String customerId, CibilResponse cibilResponse) {

        CreditScoreDetails creditScoreDetails = new CreditScoreDetails();

        String creditScore = (cibilResponse.getCreditScore() == null) ? "0" : cibilResponse.getCreditScore();
        String reportDate = (cibilResponse.getReportDate() == null) ? LocalDate.now().toString() : cibilResponse.getReportDate();

        creditScoreDetails.setCustomerId(Integer.parseInt(customerId));
        creditScoreDetails.setCreditScore(creditScore);
        creditScoreDetails.setCreditScoreDate(reportDate);

        CreditScoreDetails creditScoreDetails2 = creditScoreDao.getCreditScoreDetailsByCustomer(customerId);

        if (creditScoreDetails2 == null) {
            creditScoreDao.createCreditScore(creditScoreDetails);
        } else {
            logger.info("Credit score already exists");
            creditScoreDetails.setCreditScoreId(creditScoreDetails2.getCreditScoreId());
            creditScoreDao.updateCreditScore(creditScoreDetails);
        }
    }

    public String setLoanApplicationDetails(String customerId, CustomerDetails customerDetails, LoanApplicationRequestBean loanApplicationRequestBean) {

        double loanAmount = loanApplicationRequestBean.getLoanAmount();

        LoanProductDetails loanProductDetails = loanProductDao.getLoanProductByAmount(loanAmount);

        Integer productId = loanProductDetails.getId() == null
                ? 1 : loanProductDao.getLoanProductByAmount(loanAmount).getId();

        if (loanAmount < 0) {
            return msg("Loan amount cannot be negative");
        }

        if (loanAmount > 1000000) {
            return msg("Loan amount cannot exceed 1000000");
        }

        if (loanAmount < 10000) {
            return msg("Loan amount cannot be less than 10000");
        }

        LoanApplicationDetails existingLoanApplication = loanApplicationDao.getLoanApplicationByCustomerId(customerId);

        LoanApplicationDetails loanApplicationDetails = new LoanApplicationDetails();

        loanApplicationDetails.setCustomerId(Integer.parseInt(customerId));
        loanApplicationDetails.setProductId(productId);
        loanApplicationDetails.setLoanProductDetails( loanProductDetails );
        loanApplicationDetails.setCustomerDetails(customerDetails);
        loanApplicationDetails.setApplicationDate(LocalDate.now().toString());
        loanApplicationDetails.setLoanAmount(loanAmount);
        loanApplicationDetails.setRequestedTerm(loanApplicationRequestBean.getRequestedTerm());
        loanApplicationDetails.setLoanPurpose(loanApplicationRequestBean.getLoanPurpose());
        loanApplicationDetails.setStatus("Pending");


        String loanPurpose1 = loanApplicationRequestBean.getLoanPurpose();
        String loanPurpose2 = existingLoanApplication.getLoanPurpose();

        logger.info("Loan purpose 1: {} Loan purpose 2: {}", loanPurpose1, loanPurpose2);

        if ( existingLoanApplication == null  ) {
            return loanApplicationDao.saveLoanApplication(loanApplicationDetails);
        } else {
            if (loanPurpose1.equals(loanPurpose2)) {
                msg("Loan application already exists");
            }
            else {
                return loanApplicationDao.saveLoanApplication(loanApplicationDetails);
            }

            loanApplicationDetails.setApplicationId(existingLoanApplication.getApplicationId());
            return loanApplicationDao.updateLoanApplication(loanApplicationDetails);
        }
    }

    PanResponse getPanResponse(String panUrl) {

        PanResponse panResponse = new PanResponse();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String input1 = objectMapper.writeValueAsString(Map.of(
                    "txnId", "1",
                    "panPayload", Map.of("panNumber", "ABCDE1234Z")
            ));

            HttpHeaders httpHeaders1 = new HttpHeaders();
            httpHeaders1.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            httpHeaders1.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            logger.info("Sending request to external service pan: {}", panUrl);

            HttpEntity<String> requestEntity1 = new HttpEntity<>(input1, httpHeaders1);

            ResponseEntity<String> responseEntity1 = restTemplate.exchange(panUrl, HttpMethod.POST, requestEntity1, String.class);
            String res = responseEntity1.getBody();

            JsonNode panData = objectMapper.readTree(res);
            if (panData != null) {
                if (panData.has("panNum")) {
                    panResponse.setPanNum(panData.get("panNum").asText());
                }
                if (panData.has("fullName")) {
                    panResponse.setFullName(panData.get("fullName").asText());
                }
                if (panData.has("dob")) {
                    panResponse.setDob(panData.get("dob").asText());
                }
                if (panData.has("issueDate")) {
                    panResponse.setIssueDate(panData.get("issueDate").asText());
                }
                if (panData.has("status")) {
                    panResponse.setStatus(panData.get("status").asText());
                }
                logger.info("PAN details fetched successfully");
            }

        } catch (Exception e) {
            logger.error("Error while fetching PAN details: {}", e.getMessage());
        }
        return panResponse;
    }

    AadhaarResponse getAadhaarResponse(String aadhaarUrl) {

        AadhaarResponse aadhaarResponse = new AadhaarResponse();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String input2 = objectMapper.writeValueAsString(Map.of(
                    "aadhaarNum", "123456789012"
            ));

            HttpHeaders httpHeaders2 = new HttpHeaders();
            httpHeaders2.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            httpHeaders2.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            logger.info("Sending request to external service aadhaar: {}", aadhaarUrl);

            HttpEntity<String> requestEntity2 = new HttpEntity<>(input2, httpHeaders2);

            ResponseEntity<String> responseEntity2 = restTemplate.exchange(aadhaarUrl, HttpMethod.POST, requestEntity2, String.class);
            String res = responseEntity2.getBody();

            JsonNode aadhaarData = objectMapper.readTree(res);

            if (aadhaarData != null) {
                if (aadhaarData.has("aadharNum")) {
                    aadhaarResponse.setAadhaarNum(aadhaarData.get("aadharNum").asText());
                }
                if (aadhaarData.has("fullName")) {
                    aadhaarResponse.setFullName(aadhaarData.get("fullName").asText());
                }
                if (aadhaarData.has("dob")) {
                    aadhaarResponse.setDob(aadhaarData.get("dob").asText());
                }
                if (aadhaarData.has("gender")) {
                    aadhaarResponse.setGender(aadhaarData.get("gender").asText());
                }
                if (aadhaarData.has("address")) {
                    aadhaarResponse.setAddress(aadhaarData.get("address").asText());
                }
                if (aadhaarData.has("email")) {
                    aadhaarResponse.setEmail(aadhaarData.get("email").asText());
                }
                if (aadhaarData.has("contactNumber")) {
                    aadhaarResponse.setContactNumber(aadhaarData.get("contactNumber").asText());
                }
                if (aadhaarData.has("status")) {
                    aadhaarResponse.setStatus(aadhaarData.get("status").asText());
                }
                if (aadhaarData.has("issueDate")) {
                    aadhaarResponse.setIssueDate(aadhaarData.get("issueDate").asText());
                }
                logger.info("Aadhaar details fetched successfully");
            }


        } catch (Exception e) {
            logger.error("Error while fetching Aadhaar details: {}", e.getMessage());
        }
        return aadhaarResponse;
    }

    CibilResponse getCibilResponse(String cibilUrl) {

        CibilResponse cibilResponse = new CibilResponse();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String input3 = objectMapper.writeValueAsString(Map.of("pan", "ABCDE1234Z"));

            HttpHeaders httpHeaders3 = new HttpHeaders();
            httpHeaders3.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            httpHeaders3.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            logger.info("Sending request to external service cibil : {}", cibilUrl);

            HttpEntity<String> requestEntity3 = new HttpEntity<>(input3, httpHeaders3);

            ResponseEntity<String> responseEntity3 = restTemplate.exchange(cibilUrl, HttpMethod.POST, requestEntity3, String.class);
            String res = responseEntity3.getBody();

            JsonNode cibilData = objectMapper.readTree(res);

            if (cibilData != null) {
                if (cibilData.has("creditScore")) {
                    cibilResponse.setCreditScore(cibilData.get("creditScore").asText());
                }
                if (cibilData.has("reportDate")) {
                    cibilResponse.setReportDate(cibilData.get("reportDate").asText());
                }
                if (cibilData.has("pan")) {
                    cibilResponse.setPan(cibilData.get("pan").asText());
                }
                logger.info("CIBIL details fetched successfully");
            }


        } catch (Exception e) {
            logger.error("Error while fetching CIBIL details: {}", e.getMessage());
        }
        return cibilResponse;
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

    private void validateInputData(LoanApplicationRequestBean loanApplicationRequestBean) {

    }


}
