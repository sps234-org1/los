package com.jocata.loansystem.dao;

import com.jocata.loansystem.entity.LoanApplicationDetails;

import java.util.List;

public interface LoanApplicationDao {

    public LoanApplicationDetails addLoanApplication(LoanApplicationDetails loanApplicationDetails);

    public LoanApplicationDetails getLoanApplicationByCustomerId(String customerId);

    public List<LoanApplicationDetails> getAllLoanApplications();

    public String updateLoanApplication(LoanApplicationDetails loanApplicationDetails);

    public String deleteLoanApplication(Long id);

}
