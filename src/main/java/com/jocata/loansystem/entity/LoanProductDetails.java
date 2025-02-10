package com.jocata.loansystem.entity;

import jakarta.persistence.*;

@Entity
@Table( name = "loan_product")
public class LoanProductDetails {

    @Id
    @Column(name = "product_id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "interest_rate")
    private Integer interestRate;

    @Column(name = "term_months")
    private Integer termMonths;

    @Column(name = "max_amount")
    private Double maxLoanAmount;

    @Column(name = "min_amount")
    private Double minLoanAmount;

    @Column(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public Double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(Double maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public Double getMinLoanAmount() {
        return minLoanAmount;
    }

    public void setMinLoanAmount(Double minLoanAmount) {
        this.minLoanAmount = minLoanAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
