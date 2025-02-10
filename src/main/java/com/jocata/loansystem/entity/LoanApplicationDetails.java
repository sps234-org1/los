package com.jocata.loansystem.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "loan_application")
public class LoanApplicationDetails {

    @Id
    @Column(name = "application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @Column(name = "customer_id")
    private Integer customerId;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private CustomerDetails customerDetails;

    @Column(name = "product_id")
    private Integer productId;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private LoanProductDetails loanProductDetails;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "application_date")
    private String applicationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "requested_term")
    private Integer requestedTerm;

    @Column(name = "loan_purpose")
    private String loanPurpose;


    public Integer getApplicationId() {
        return applicationId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    public LoanProductDetails getLoanProductDetails() {
        return loanProductDetails;
    }

    public void setLoanProductDetails(LoanProductDetails loanProductDetails) {
        this.loanProductDetails = loanProductDetails;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
