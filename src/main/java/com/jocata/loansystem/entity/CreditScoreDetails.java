package com.jocata.loansystem.entity;

import jakarta.persistence.*;

@Entity
@Table( name = "credit_score")
public class CreditScoreDetails {

    @Id
    @Column( name = "credit_score_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer creditScoreId;

    @Column( name = "customer_id")
    private Integer customerId;

    @ManyToOne
    @JoinColumn( name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private CustomerDetails customerDetails;

    @Column( name = "score")
    private String creditScore;

    @Column( name = "score_date")
    private String creditScoreDate;



    public Integer getCreditScoreId() {
        return creditScoreId;
    }

    public void setCreditScoreId(Integer creditScoreId) {
        this.creditScoreId = creditScoreId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId( Integer customerId) {
        this.customerId = customerId;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getCreditScoreDate() {
        return creditScoreDate;
    }

    public void setCreditScoreDate(String creditScoreDate) {
        this.creditScoreDate = creditScoreDate;
    }


//    public CustomerDetails getCustomerDetails() {
//        return customerDetails;
//    }
//
//    public void setCustomerDetails(CustomerDetails customerDetails) {
//        this.customerDetails = customerDetails;
//    }

}
