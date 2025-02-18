package com.jocata.loansystem.service;

import com.jocata.loansystem.bean.loanapplication.LoanApplicationRequestBean;
import com.jocata.loansystem.bean.response.AadhaarResponse;
import com.jocata.loansystem.bean.response.CibilResponse;
import com.jocata.loansystem.bean.response.PanResponse;

public interface ExternalService {

    public PanResponse getPanResponse(String panUrl, LoanApplicationRequestBean loanApplicationRequestBean);

    public AadhaarResponse getAadhaarResponse(String aadhaarUrl, LoanApplicationRequestBean loanApplicationRequestBean);

    public CibilResponse getCibilResponse(String cibilUrl, LoanApplicationRequestBean loanApplicationRequestBean);

    public CibilResponse addCibilDetails( String cibilUrl, CibilResponse cibilResponse );

}
