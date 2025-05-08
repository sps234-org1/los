package com.jocata.loansystem.service.impl;

import com.jocata.loansystem.bean.payment.LoanPaymentBean;
import com.jocata.loansystem.bean.payment.RepaymentScheduleBean;
import com.jocata.loansystem.dao.LoanDao;
import com.jocata.loansystem.dao.LoanPaymentDao;
import com.jocata.loansystem.entity.LoanDetails;
import com.jocata.loansystem.entity.LoanPaymentDetails;
import com.jocata.loansystem.service.LoanPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {

    @Autowired
    private final LoanDao loanDao;

    @Autowired
    private final LoanPaymentDao loanPaymentDao;

    private static final Logger logger = LoggerFactory.getLogger(LoanPaymentServiceImpl.class);

    LoanPaymentServiceImpl(LoanDao loanDao, LoanPaymentDao loanPaymentDao) {
        this.loanDao = loanDao;
        this.loanPaymentDao = loanPaymentDao;
    }

    @Override
    public List<RepaymentScheduleBean> getRepaymentSchedule(int loanId) {

        LoanDetails loanDetails = loanDao.getLoan(loanId);
        if (loanDetails == null) {
            logger.error("Loan details not found for application id: " + loanId);
            return null;
        }
        double loanAmount = loanDetails.getLoanAmount();
        double annualInterestRate = loanDetails.getInterestRate();
        int loanTermMonth = loanDetails.getTermMonths();

        List<RepaymentScheduleBean> list = new ArrayList<>();

        double monthlyInterestRate = (annualInterestRate / 100) / 12;

        double emi = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermMonth))
                / (Math.pow(1 + monthlyInterestRate, loanTermMonth) - 1);


        double remainingBalance = loanAmount;

        for (int i = 1; i <= loanTermMonth; i++) {
            double interestForMonth = remainingBalance * monthlyInterestRate;
            double principalForMonth = emi - interestForMonth;
            remainingBalance -= principalForMonth;

            double roundedPrincipalForMonth = Math.round(principalForMonth * 100.0) / 100.0;
            double roundedInterestForMonth = Math.round(interestForMonth * 100.0) / 100.0;
            double roundedRemainingBalance = Math.round(remainingBalance * 100.0) / 100.0;


            RepaymentScheduleBean responseBean = new RepaymentScheduleBean();
            responseBean.setId(i);
            responseBean.setPrincipal(roundedPrincipalForMonth);
            responseBean.setInterest(roundedInterestForMonth);
            responseBean.setBalance(roundedRemainingBalance);
            list.add(responseBean);
        }
        return list;
    }

    @Override
    public LoanPaymentBean makePayment(LoanPaymentBean loanPaymentBean) {

        LoanDetails loanDetails = loanDao.getLoan( loanPaymentBean.getLoanDetails().getLoanId() );
        if ( loanDetails == null ) {
            logger.error("Loan details not found for loan id: " + loanPaymentBean.getLoanDetails().getLoanId());
            return null;
        }
        logger.info("Loan details found for loan id: " + loanPaymentBean.getLoanDetails().getLoanId());

        double paymentAmount = loanPaymentBean.getPaymentAmount();
        double loanAmount = loanDetails.getLoanAmount();
        double remainingBalance = loanAmount - paymentAmount;

        LoanPaymentDetails loanPaymentDetails = new LoanPaymentDetails();
        loanPaymentDetails.setLoanDetails(loanDetails);
        loanPaymentDetails.setLoanDetails(loanPaymentBean.getLoanDetails());
        loanPaymentDetails.setPaymentAmount( paymentAmount );
        loanPaymentDetails.setRemainingBalance( remainingBalance );
        loanPaymentDetails.setPaymentMethod(loanPaymentBean.getPaymentMethod());

        logger.info( " loan details : {} {} {} {}", loanPaymentDetails.getLoanDetails().getDisbursementDate(), loanPaymentDetails.getLoanDetails().getLoanAmount(), loanPaymentDetails.getLoanDetails().getInterestRate(), loanPaymentDetails.getLoanDetails().getTermMonths() );

        LoanPaymentDetails loanPaymentDetailsDB = loanPaymentDao.makePayment(loanPaymentDetails);

        if ( loanPaymentDetailsDB == null ) {
            logger.error("Error while making payment for loan id: " + loanPaymentBean.getLoanDetails());
            return null;
        }

        LoanPaymentBean responseBean = new LoanPaymentBean();
        responseBean.setPaymentId(loanPaymentDetailsDB.getPaymentId());
        responseBean.setLoanDetails(loanPaymentDetailsDB.getLoanDetails());
        responseBean.setPaymentAmount(loanPaymentDetailsDB.getPaymentAmount());
        responseBean.setRemainingBalance(loanPaymentDetailsDB.getRemainingBalance());
        responseBean.setPaymentMethod(loanPaymentDetailsDB.getPaymentMethod());
        return responseBean;
    }




}
