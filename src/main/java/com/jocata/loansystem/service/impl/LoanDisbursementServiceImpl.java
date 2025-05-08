package com.jocata.loansystem.service.impl;

import com.jocata.loansystem.bean.LoanDisbursement.LoanDisbursementBean;
import com.jocata.loansystem.bean.loanapplication.LoanApplicationResponseBean;
import com.jocata.loansystem.dao.LoanApplicationDao;
import com.jocata.loansystem.dao.LoanDisbursementDao;
import com.jocata.loansystem.entity.LoanApplicationDetails;
import com.jocata.loansystem.entity.LoanDetails;
import com.jocata.loansystem.entity.LoanDisbursementDetails;
import com.jocata.loansystem.service.LoanApplicationService;
import com.jocata.loansystem.service.LoanDisbursementService;
import com.jocata.loansystem.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanDisbursementServiceImpl implements LoanDisbursementService {

    @Autowired
    private LoanDisbursementDao loanDisbursementDao;

    @Autowired
    private LoanApplicationDao loanApplicationDao;

    @Autowired
    private LoanService loanService;

    private static final Logger logger = LoggerFactory.getLogger(LoanDisbursementServiceImpl.class);

    public LoanDisbursementBean addLoanDisbursementDetails( int applicationId ) {

        LoanApplicationDetails loanApplicationDetails = loanApplicationDao.getLoanApplication(applicationId);

        if (loanApplicationDetails == null) {
            logger.info("Loan Application not found for customer ID: " + applicationId);
            return null;
        }

        String res = loanService.addLoanDetailsUsingApplictation( loanApplicationDetails );
        logger.info( "response for loan" + res );

        LoanDisbursementDetails loanDisbursementDetails = new LoanDisbursementDetails();
        loanDisbursementDetails.setDisbursementDate(LocalDate.now().toString());
        loanDisbursementDetails.setDisbursementAmount(loanApplicationDetails.getLoanAmount());
        loanDisbursementDetails.setDisbursementMethod("Bank Transfer");
        loanDisbursementDetails.setLoanDetails( null );

        logger.info( "amount : " + loanApplicationDetails.getLoanAmount() );

        loanDisbursementDao.addLoanDisbursementDetails(loanDisbursementDetails);
        logger.info("Loan Disbursement details added successfully ");

//        LoanDisbursementBean loanDisbursementBeanDB = getLoanDisbursementDetails( "1" );

        return null;

    }

    public LoanDisbursementBean getLoanDisbursementDetails(String loanId) {

        LoanDisbursementDetails loanDisbursementDetailsDB = loanDisbursementDao.getLoanDisbursementDetails(loanId);

        if (loanDisbursementDetailsDB == null) {
            logger.info("Loan Disbursement details not found for loan ID: " + loanId);
            return null;
        }

        LoanDisbursementBean loanDisbursementBeanDB = new LoanDisbursementBean();
        loanDisbursementBeanDB.setDisbursementId(loanDisbursementDetailsDB.getDisbursementId());
        loanDisbursementBeanDB.setDisbursementDate(loanDisbursementDetailsDB.getDisbursementDate());
        loanDisbursementBeanDB.setDisbursementAmount(loanDisbursementDetailsDB.getDisbursementAmount());
        loanDisbursementBeanDB.setDisbursementMethod(loanDisbursementDetailsDB.getDisbursementMethod());
        return loanDisbursementBeanDB;
    }

    public LoanDisbursementBean deleteLoanDisbursementDetails(String loanId) {

        LoanDisbursementBean loanDisbursementBeanDB = getLoanDisbursementDetails(loanId);

        if (loanDisbursementBeanDB == null) {
            logger.info("Loan Disbursement details not found for loan ID: " + loanId);
            return null;
        }
        return loanDisbursementBeanDB;

    }


}
