package com.jocata.loansystem.entity;

import jakarta.persistence.*;

@Entity
@Table( name = "loan")
public class LoanDetails {

    @Id
    @Column( name = "loan_id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer loanId;

    @OneToOne
    @JoinColumn( name = "application_id", referencedColumnName = "application_id", insertable = false, updatable = false)
    private LoanApplicationDetails loanApplicationDetails;

    @Column( name = "disbursement_date")
    private String disbursementDate;

    @Column( name = "loan_amount")
    private Double loanAmount;

    @Column( name = "interest_rate")
    private Double interestRate;

    @Column( name = "term_months")
    private Integer termMonths;

    @Column( name = "loan_balance")
    private Double loanBalance;

    @Column( name = "status")
    String status;

    @Column( name = "application_id")
    private Integer applicationId;

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
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

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public Double getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(Double loanBalance) {
        this.loanBalance = loanBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
