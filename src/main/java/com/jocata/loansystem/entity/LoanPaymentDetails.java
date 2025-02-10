package com.jocata.loansystem.entity;

import jakarta.persistence.*;

@Entity
@Table( name = "loan_payment" )
public class LoanPaymentDetails {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @ManyToOne
    @JoinColumn( name = "loan_id", referencedColumnName = "loan_id", insertable = false, updatable = false)
    private LoanDetails loanDetails;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "remaining_balance")
    private Double remainingBalance;

    @Column(name = "payment_method")
    private String paymentMethod;


    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(Double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
