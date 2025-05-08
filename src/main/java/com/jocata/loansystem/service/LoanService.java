package com.jocata.loansystem.service;

import com.jocata.loansystem.bean.Loan.LoanBean;
import com.jocata.loansystem.entity.LoanApplicationDetails;

public interface LoanService {

    LoanBean addLoanDetails(LoanBean loanBean);

    String addLoanDetailsUsingApplictation(LoanApplicationDetails loanApplicationDetails );

    LoanBean getLoanDetailsById(int applicationId);
}
