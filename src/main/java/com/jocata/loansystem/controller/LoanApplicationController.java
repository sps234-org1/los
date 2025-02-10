package com.jocata.loansystem.controller;

import com.jocata.loansystem.bean.loanapplication.LoanApplicationRequestBean;
import com.jocata.loansystem.entity.LoanApplicationDetails;
import com.jocata.loansystem.service.LoanApplicationService;
import com.jocata.loansystem.service.impl.LoanApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loanApplication")
public class LoanApplicationController {

    @Autowired
    public LoanApplicationService loanApplicationService ;

    @PostMapping("/create")
    public String createLoanApplication( @RequestBody LoanApplicationRequestBean loanApplicationRequestBean) {
        return loanApplicationService.createLoanApplication(loanApplicationRequestBean);
    }

    @GetMapping("/get/{id}")
    public LoanApplicationDetails getLoanApplicationDetail( @RequestParam( name = "id") Integer id ) {
        return loanApplicationService.getLoanApplicationDetail(id);
    }

    public List<LoanApplicationDetails> getAllLoanApplications() {
        return loanApplicationService.getAllLoanApplications();
    }

    @PostMapping("/update")
    public String updateLoanApplication(@RequestBody LoanApplicationDetails loanApplicationDetails) {
        return loanApplicationService.updateLoanApplication(loanApplicationDetails);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLoanApplication(@RequestParam( name = "id") Long id) {
        return loanApplicationService.deleteLoanApplication(id);
    }


}
