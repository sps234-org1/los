package com.jocata.loansystem.service;

import com.jocata.loansystem.bean.loanproduct.LoanProductRequestBean;
import com.jocata.loansystem.bean.loanproduct.LoanProductResponseBean;

import java.util.List;

public interface LoanProductService {

    String addLoanProduct(LoanProductRequestBean loanProductRequestBean);

    public List<LoanProductResponseBean> getLoanProducts();

    public LoanProductResponseBean getLoanProductByAmount(double amount);

    public LoanProductResponseBean getLoanProductsByTenure( String tenure );

}
