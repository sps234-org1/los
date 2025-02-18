package com.jocata.loansystem.bean.loanapplication;

public class LoanApplicationRequestBean {

    private String panNum;

    private String aadhaarNum;

    private String contactNumber;

    private long annualIncome;

    private long reqLoanAmount;

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

    public long getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(long annualIncome) {
        this.annualIncome = annualIncome;
    }

    public long getReqLoanAmount() {
        return reqLoanAmount;
    }

    public void setReqLoanAmount(long reqLoanAmount) {
        this.reqLoanAmount = reqLoanAmount;
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
