package com.jocata.loansystem.controller;

import com.jocata.loansystem.bean.LoanDisbursement.LoanDisbursementBean;
import com.jocata.loansystem.service.LoanDisbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "api/v1/loan-disbursement" )
public class LoanDisbursementController {

    @Autowired
    private LoanDisbursementService loanDisbursementService;

    @PostMapping( "/add" )
    public LoanDisbursementBean addLoanDisbursementDetails( @RequestParam Integer applicationId ) {

        return loanDisbursementService.addLoanDisbursementDetails( applicationId );
    }

    @GetMapping( "/get/{loanId}" )
    public LoanDisbursementBean getLoanDisbursementDetails( @PathVariable String loanId) {
        return loanDisbursementService.getLoanDisbursementDetails(loanId);
    }

    @DeleteMapping( "/delete/{loanId}" )
    public LoanDisbursementBean deleteLoanDisbursementDetails(@PathVariable String loanId) {
        return loanDisbursementService.deleteLoanDisbursementDetails(loanId);
    }


}
