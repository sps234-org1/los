package com.jocata.loansystem.service.impl;

import com.jocata.loansystem.bean.Loan.LoanBean;
import com.jocata.loansystem.dao.LoanDao;
import com.jocata.loansystem.entity.LoanApplicationDetails;
import com.jocata.loansystem.entity.LoanDetails;
import com.jocata.loansystem.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanDao loanDao;

    private static final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    public LoanBean addLoanDetails(LoanBean loanBean) {

        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setApplicationId( loanBean.getApplicationId() );
        loanDetails.setDisbursementDate(LocalDate.now().toString());
        loanDetails.setLoanAmount(loanBean.getLoanAmount());
        loanDetails.setInterestRate(loanBean.getInterestRate());
        loanDetails.setTermMonths(loanBean.getTermMonths());
        loanDetails.setLoanBalance(loanBean.getLoanAmount());
        loanDetails.setStatus("Active");

        loanDao.addLoanDetails(loanDetails);
        LoanBean loanBeanDB = getLoanDetailsById(loanBean.getLoanApplicationDetails().getApplicationId());
        return loanBeanDB;
    }

    @Override
    public String addLoanDetailsUsingApplictation(LoanApplicationDetails loanApplicationDetails) {

        LoanBean loanBean = new LoanBean();
        loanBean.setApplicationId(loanApplicationDetails.getApplicationId());
        loanBean.setLoanApplicationDetails(loanApplicationDetails);
        loanBean.setLoanAmount(loanApplicationDetails.getLoanAmount());
        loanBean.setTermMonths( loanApplicationDetails.getRequestedTerm() );
        loanBean.setInterestRate( 18 );
        loanBean.setLoanBalance(loanApplicationDetails.getLoanAmount());
        loanBean.setStatus("Active");
        addLoanDetails(loanBean);
        return "Loan details added successfully";
    }

    public LoanBean getLoanDetailsById(int applicationId) {

        LoanDetails loanDetailsDB = loanDao.getLoanDetailsById(applicationId);
        if (loanDetailsDB == null) {
            logger.info("Loan details not found for application ID: " + applicationId);
            return null;
        }
        LoanBean loanBeanDB = new LoanBean();
        loanBeanDB.setLoanId(loanDetailsDB.getLoanId());
        loanBeanDB.setLoanApplicationDetails(loanDetailsDB.getLoanApplicationDetails());
        loanBeanDB.setDisbursementDate(loanDetailsDB.getDisbursementDate());
        loanBeanDB.setLoanAmount(loanDetailsDB.getLoanAmount());
        loanBeanDB.setInterestRate(loanDetailsDB.getInterestRate());
        loanBeanDB.setTermMonths(loanDetailsDB.getTermMonths());
        loanBeanDB.setLoanBalance(loanDetailsDB.getLoanBalance());
        loanBeanDB.setStatus(loanDetailsDB.getStatus());
        return loanBeanDB;

    }

}
