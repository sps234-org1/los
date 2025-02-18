package com.jocata.loansystem.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jocata.loansystem.bean.loanapplication.LoanApplicationRequestBean;
import com.jocata.loansystem.bean.response.AadhaarResponse;
import com.jocata.loansystem.bean.response.CibilResponse;
import com.jocata.loansystem.bean.response.PanResponse;
import com.jocata.loansystem.service.ExternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    private final RestTemplate restTemplate;

    public ExternalServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

    public PanResponse getPanResponse(String panUrl, LoanApplicationRequestBean loanApplicationRequestBean) {

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

    public AadhaarResponse getAadhaarResponse(String aadhaarUrl, LoanApplicationRequestBean loanApplicationRequestBean) {

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

    public CibilResponse getCibilResponse(String cibilUrl, LoanApplicationRequestBean loanApplicationRequestBean) {

        CibilResponse cibilResponse = new CibilResponse();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String input3 = objectMapper.writeValueAsString(Map.of("pan", loanApplicationRequestBean.getPanNum()) );

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
                if ( cibilData.has("creditLimit") ) {
                    cibilResponse.setCreditLimit(cibilData.get("creditLimit").asText());
                }
                if ( cibilData.has("creditHistory") ) {
                    cibilResponse.setCreditHistory(cibilData.get("creditHistory").asText());
                }
                if ( cibilData.has("totalOutstandingBalance") ) {
                    cibilResponse.setTotalOutstandingBalance(cibilData.get("totalOutstandingBalance").asText());
                }
                if ( cibilData.has("recentCreditInquiries") ) {
                    cibilResponse.setRecentCreditInquiries(cibilData.get("recentCreditInquiries").asText());
                }
                if ( cibilData.has("paymentHistory") ) {
                    cibilResponse.setPaymentHistory(cibilData.get("paymentHistory").asText());
                }

                logger.info("CIBIL details fetched successfully : {} {} {} {} {} {} {} {} {}", cibilResponse.getPan(), cibilResponse.getCreditScore(),
                        cibilResponse.getCreditHistory(), cibilResponse.getTotalOutstandingBalance(), cibilResponse.getRecentCreditInquiries(),
                        cibilResponse.getPaymentHistory(), cibilResponse.getCreditLimit(), cibilResponse.getReportDate(), cibilResponse.getStatus() );

            }


        } catch (Exception e) {
            logger.error("Error while fetching CIBIL details: {}", e.getMessage());
        }
        return cibilResponse;
    }

    private void validateCibilResponse(CibilResponse cibilResponse) {
        Objects.requireNonNull(cibilResponse.getPan(), "PAN cannot be null");
        Objects.requireNonNull(cibilResponse.getCreditScore(), "Credit Score cannot be null");
        Objects.requireNonNull(cibilResponse.getCreditHistory(), "Credit History cannot be null");
        Objects.requireNonNull(cibilResponse.getTotalOutstandingBalance(), "Total Outstanding Balance cannot be null");
        Objects.requireNonNull(cibilResponse.getRecentCreditInquiries(), "Recent Credit Inquiries cannot be null");
        Objects.requireNonNull(cibilResponse.getPaymentHistory(), "Payment History cannot be null");
        Objects.requireNonNull(cibilResponse.getCreditLimit(), "Credit Limit cannot be null");
        Objects.requireNonNull(cibilResponse.getReportDate(), "Report Date cannot be null");
        Objects.requireNonNull(cibilResponse.getStatus(), "Status cannot be null");

        logger.info("CIBIL response validated successfully: {}", cibilResponse.getPan());
    }

    public CibilResponse addCibilDetails( String cibilUrl, CibilResponse cibilResponse ) {

        try {

            validateCibilResponse( cibilResponse );

            ObjectMapper objectMapper = new ObjectMapper();
            String input4 = objectMapper.writeValueAsString( Map.of(
                    "pan" , cibilResponse.getPan(),
                    "creditScore" , cibilResponse.getCreditScore(),
                    "creditHistory" , cibilResponse.getCreditHistory(),
                    "totalOutstandingBalance" , cibilResponse.getTotalOutstandingBalance(),
                    "recentCreditInquiries" , cibilResponse.getRecentCreditInquiries(),
                    "paymentHistory" , cibilResponse.getPaymentHistory(),
                    "creditLimit" , cibilResponse.getCreditLimit(),
                    "reportDate" , cibilResponse.getReportDate(),
                    "status" , cibilResponse.getStatus()
            ) );

            HttpHeaders httpHeaders4 = new HttpHeaders();
            httpHeaders4.add( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE );
            httpHeaders4.add( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );

            logger.info("Sending post request for adding cibil details :" + cibilResponse.getPan() );

            HttpEntity<String> reqEntity4 = new HttpEntity<>( input4, httpHeaders4 );
            restTemplate.exchange( cibilUrl, HttpMethod.POST, reqEntity4, String.class );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cibilResponse;
    }
}
