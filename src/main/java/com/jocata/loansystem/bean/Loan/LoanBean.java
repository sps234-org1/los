package com.jocata.loansystem.bean.Loan;

import com.jocata.loansystem.entity.LoanApplicationDetails;
import jakarta.persistence.*;

public class LoanBean {

    private int loanId;
    private int applicationId;
    private LoanApplicationDetails loanApplicationDetails;
    private String disbursementDate;
    private double interestRate;
    private int termMonths;
    private double loanAmount;
    private double loanBalance;
    String status;

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public LoanApplicationDetails getLoanApplicationDetails() {
        return loanApplicationDetails;
    }

    public void setLoanApplicationDetails(LoanApplicationDetails loanApplicationDetails) {
        this.loanApplicationDetails = loanApplicationDetails;
    }

    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public double getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(double loanBalance) {
        this.loanBalance = loanBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
