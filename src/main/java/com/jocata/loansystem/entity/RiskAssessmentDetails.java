package com.jocata.loansystem.entity;

import jakarta.persistence.*;

@Entity
@Table( name = "risk_assessment")
public class RiskAssessmentDetails {

    @Id
    @Column( name = "assessment_id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer assessmentId;

    @Column( name = "credit_score")
    private Integer creditScore;

    @Column( name = "income")
    private Double income ;

    @Column( name = "approved_amount")
    private Double approvedAmount;

    @Column( name = "approved_term")
    private Integer approvedTerm;

    @Column( name = "approval_status")
    private String approvalStatus;

    @Column( name = "assessment_date")
    private String assessmentDate;

    @OneToOne
    @JoinColumn( name = "application_id", referencedColumnName = "application_id", insertable = false, updatable = false)
    private LoanApplicationDetails loanApplicationDetails;

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public LoanApplicationDetails getLoanApplicationDetails() {
        return loanApplicationDetails;
    }

    public void setLoanApplicationDetails(LoanApplicationDetails loanApplicationDetails) {
        this.loanApplicationDetails = loanApplicationDetails;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome( Double income) {
        this.income = income;
    }

    public Double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(Double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public Integer getApprovedTerm() {
        return approvedTerm;
    }

    public void setApprovedTerm(Integer approvedTerm) {
        this.approvedTerm = approvedTerm;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }
}
