package com.jocata.loansystem.entity;

import jakarta.persistence.*;

@Entity
@Table( name = "loan_disbursement" )
public class LoanDisbursementDetails {

    @Id
    @Column(name = "disbursement_id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer disbursementId;

    @ManyToOne
    @JoinColumn( name = "loan_id",  insertable = false, updatable = false)
    private LoanDetails loanDetails;

    @Column(name = "disbursement_date")
    private String disbursementDate;

    @Column(name = "disbursement_amount")
    private Double disbursementAmount;

    @Column(name = "disbursement_method")
    private String disbursementMethod;


    public Integer getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(Integer disbursementId) {
        this.disbursementId = disbursementId;
    }


    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Double getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(Double disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public String getDisbursementMethod() {
        return disbursementMethod;
    }

    public void setDisbursementMethod(String disbursementMethod) {
        this.disbursementMethod = disbursementMethod;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }
}
