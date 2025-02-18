package com.jocata.loansystem.service;

import com.jocata.loansystem.bean.loanapplication.LoanApplicationRequestBean;
import com.jocata.loansystem.bean.response.CibilResponse;
import com.jocata.loansystem.entity.LoanApplicationDetails;

public interface RiskAssessmentService {

    public String addRiskAssessmentDetailsUsingCibil(CibilResponse cibilResponse, LoanApplicationRequestBean loanApplicationRequestBean, LoanApplicationDetails loanApplicationDetails );
}
