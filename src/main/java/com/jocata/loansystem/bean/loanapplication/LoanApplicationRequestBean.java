package com.jocata.loansystem.bean.loanapplication;

public class LoanApplicationRequestBean {

    private String panNum;

    private String aadhaarNum;

    private String contactNumber;

    private Double loanAmount;

    private Integer requestedTerm;

    private String loanPurpose;

    public String getPanNum() {
        return panNum;
    }

    public void setPanNum(String panNum) {
        this.panNum = panNum;
    }

    public String getAadhaarNum() {
        return aadhaarNum;
    }

    public void setAadhaarNum(String aadhaarNum) {
        this.aadhaarNum = aadhaarNum;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getRequestedTerm() {
        return requestedTerm;
    }

    public void setRequestedTerm(Integer requestedTerm) {
        this.requestedTerm = requestedTerm;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }
}
