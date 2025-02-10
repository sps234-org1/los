package com.jocata.loansystem.service.impl;

import com.jocata.loansystem.dao.CreditScoreDao;
import com.jocata.loansystem.entity.CreditScoreDetails;
import com.jocata.loansystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreServiceImpl implements CreditScoreService  {

    @Autowired
    private final CreditScoreDao creditScoreDao;

    public CreditScoreServiceImpl(CreditScoreDao creditScoreDao) {
        this.creditScoreDao = creditScoreDao;
    }

    public String createCreditScore(CreditScoreDetails creditScoreDetails ) {

        return creditScoreDao.createCreditScore( creditScoreDetails );
    }
}
