package com.jocata.loansystem.bean.LoanDisbursement;

import com.jocata.loansystem.entity.LoanDetails;

public class LoanDisbursementBean {

    private Integer disbursementId;
    private LoanDetails loanDetails;
    private String disbursementDate;
    private long disbursementAmount;
    private String disbursementMethod;

    public Integer getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(Integer disbursementId) {
        this.disbursementId = disbursementId;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }

    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public long getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(long disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public String getDisbursementMethod() {
        return disbursementMethod;
    }

    public void setDisbursementMethod(String disbursementMethod) {
        this.disbursementMethod = disbursementMethod;
    }
}
