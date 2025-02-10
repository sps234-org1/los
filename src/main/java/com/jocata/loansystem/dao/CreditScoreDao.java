package com.jocata.loansystem.dao;

import com.jocata.loansystem.entity.CreditScoreDetails;

public interface CreditScoreDao {

    public String createCreditScore(CreditScoreDetails creditScoreDetails ) ;

    public CreditScoreDetails getCreditScoreDetailsByCustomer( String customerId );

    public String updateCreditScore(CreditScoreDetails creditScoreDetails);

    public String deleteCreditScore(String panNum);

}
