package com.jocata.loansystem.service;

import com.jocata.loansystem.bean.loanapplication.LoanApplicationRequestBean;
import com.jocata.loansystem.entity.LoanApplicationDetails;

import java.util.List;

public interface LoanApplicationService {

    public String createLoanApplication(LoanApplicationRequestBean loanApplicationRequestBean);

    public LoanApplicationDetails getLoanApplicationDetail(Integer loanApplicationId);

    public List<LoanApplicationDetails> getAllLoanApplications();

    public String updateLoanApplication(LoanApplicationDetails loanApplicationDetails);

    public String deleteLoanApplication(Long id);

}

