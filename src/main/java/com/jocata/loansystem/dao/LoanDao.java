package com.jocata.loansystem.dao;

import com.jocata.loansystem.entity.LoanDetails;

public interface LoanDao {

    LoanDetails addLoanDetails(LoanDetails loanDetails);

    LoanDetails getLoanDetailsById(int applicationId);

    LoanDetails getLoan( int loanId );


}
