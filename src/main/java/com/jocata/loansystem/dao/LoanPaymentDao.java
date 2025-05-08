package com.jocata.loansystem.dao;

import com.jocata.loansystem.entity.LoanPaymentDetails;

public interface LoanPaymentDao {

    LoanPaymentDetails makePayment( LoanPaymentDetails loanPaymentDetails );


}
