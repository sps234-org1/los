package com.jocata.loansystem.service;

import com.jocata.loansystem.bean.creditscore.CreditScoreRequestBean;
import com.jocata.loansystem.entity.CreditScoreDetails;

public interface CreditScoreService {

    public CreditScoreDetails createCreditScore(CreditScoreDetails creditScoreDetails );

}
