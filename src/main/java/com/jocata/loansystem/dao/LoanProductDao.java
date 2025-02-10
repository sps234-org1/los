package com.jocata.loansystem.dao;

import com.jocata.loansystem.bean.loanproduct.LoanProductRequestBean;
import com.jocata.loansystem.bean.loanproduct.LoanProductResponseBean;
import com.jocata.loansystem.entity.LoanProductDetails;

import java.util.List;

public interface LoanProductDao {

    public String addLoanProduct( LoanProductDetails loanProductDetails);

    public List<LoanProductDetails> getLoanProducts();

    public LoanProductDetails getLoanProductByAmount( double amount);
}
