package com.jocata.loansystem.dao;

import com.jocata.loansystem.dao.impl.LoanApplicationDaoImpl;
import com.jocata.loansystem.entity.LoanApplicationDetails;

import java.util.List;

public interface LoanApplicationDao {

    public String saveLoanApplication(LoanApplicationDetails loanApplicationDetails);

    public LoanApplicationDetails getLoanApplicationByCustomerId(String customerId);

    public List<LoanApplicationDetails> getAllLoanApplications();

    public String updateLoanApplication(LoanApplicationDetails loanApplicationDetails);

    public String deleteLoanApplication(Long id);

}
