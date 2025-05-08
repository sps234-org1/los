package com.jocata.loansystem.service;

import com.jocata.loansystem.bean.LoanDisbursement.LoanDisbursementBean;
import com.jocata.loansystem.entity.LoanDisbursementDetails;

public interface LoanDisbursementService {

    public LoanDisbursementBean addLoanDisbursementDetails( int applicationId );

    public LoanDisbursementBean getLoanDisbursementDetails(String loanId);

    public LoanDisbursementBean deleteLoanDisbursementDetails(String loanId);


}
