package com.jocata.loansystem.dao;

import com.jocata.loansystem.entity.LoanDisbursementDetails;

public interface LoanDisbursementDao {

    LoanDisbursementDetails addLoanDisbursementDetails(LoanDisbursementDetails loanDisbursementDetails);

    LoanDisbursementDetails getLoanDisbursementDetails(String loanId);

    LoanDisbursementDetails deleteLoanDisbursementDetails(String loanId);

}
