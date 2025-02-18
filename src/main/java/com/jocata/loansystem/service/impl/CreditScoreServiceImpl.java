package com.jocata.loansystem.service.impl;

import com.jocata.loansystem.dao.CreditScoreDao;
import com.jocata.loansystem.entity.CreditScoreDetails;
import com.jocata.loansystem.service.CreditScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

    private static final Logger logger = LoggerFactory.getLogger(CreditScoreServiceImpl.class);

    @Autowired
//    @Lazy
    private final CreditScoreDao creditScoreDao;

    public CreditScoreServiceImpl(CreditScoreDao creditScoreDao) {
        this.creditScoreDao = creditScoreDao;
    }

    public CreditScoreDetails createCreditScore(CreditScoreDetails creditScoreDetails) {
        return creditScoreDao.createCreditScoreDetails(creditScoreDetails);
    }


}
