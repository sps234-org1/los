package com.jocata.loansystem.controller;

import com.jocata.loansystem.bean.payment.LoanPaymentBean;
import com.jocata.loansystem.bean.payment.RepaymentScheduleBean;
import com.jocata.loansystem.service.LoanPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class LoanPaymentController {

    @Autowired
    private LoanPaymentService paymentService;

    @GetMapping("/repayment-schedule")
    List<RepaymentScheduleBean> getRepaymentSchedule(@RequestParam int loanId ) {
        return paymentService.getRepaymentSchedule(loanId);
    }

    @PostMapping("/make")
    LoanPaymentBean makePayment(@RequestBody LoanPaymentBean loanPaymentBean) {
        return paymentService.makePayment(loanPaymentBean);
    }





}
