package com.jocata.loansystem.bean.creditscore;

public class CreditScoreRequestBean {

    String panNum;
    double monthlyIncome;
    String tenure;
    double requestedLoanAmount;

    public void setPanNum(String panNum) {
        this.panNum = panNum;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }

    public void setRequestedLoanAmount(double requestedLoanAmount) {
        this.requestedLoanAmount = requestedLoanAmount;
    }

    public String getPanNum() {
        return panNum;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public String getTenure() {
        return tenure;
    }

    public double getRequestedLoanAmount() {
        return requestedLoanAmount;
    }

}
