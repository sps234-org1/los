package com.jocata.loansystem.service;

import com.jocata.loansystem.bean.payment.LoanPaymentBean;
import com.jocata.loansystem.bean.payment.RepaymentScheduleBean;

import java.util.List;

public interface LoanPaymentService {

    List<RepaymentScheduleBean> getRepaymentSchedule(int loanId );

    LoanPaymentBean makePayment( LoanPaymentBean loanPaymentBean );
}
