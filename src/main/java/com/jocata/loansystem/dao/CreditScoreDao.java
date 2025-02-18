package com.jocata.loansystem.dao;

import com.jocata.loansystem.bean.response.CibilResponse;
import com.jocata.loansystem.entity.CreditScoreDetails;
import com.jocata.loansystem.entity.CustomerDetails;

public interface CreditScoreDao {

    public CreditScoreDetails createCreditScoreDetails(CreditScoreDetails creditScoreDetails ) ;

    public void addCreditScoreDetailsUsingCibilResponse(CibilResponse cibilResponse, CustomerDetails customerDetails );

    public CreditScoreDetails getCreditScoreDetails( String creditScoreId );

    public String updateCreditScore(CreditScoreDetails creditScoreDetails);

    public String deleteCreditScore(String panNum);

}
