package com.jocata.loansystem.controller;

import com.jocata.loansystem.bean.loanproduct.LoanProductRequestBean;
import com.jocata.loansystem.bean.loanproduct.LoanProductResponseBean;
import com.jocata.loansystem.service.LoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loanproduct")
public class LoanProductController {

    @Autowired
    LoanProductService loanProductService;

    @PostMapping("/add")
    public String addLoanProduct(@RequestBody LoanProductRequestBean loanProductRequestBean ) {

        String identityNumber = loanProductRequestBean.getIdentityNumber();
        String contactNumber = loanProductRequestBean.getContactNumber();

        if( identityNumber == null || contactNumber == null ) {
            return "Invalid Request Parameters";
        }
        if ( identityNumber.length() != 12 && identityNumber.length() != 10 ) {
            return "Invalid Identity Number";
        }
        if ( contactNumber.length() != 10 ) {
            return "Invalid Contact Number";
        }
        return loanProductService.addLoanProduct( loanProductRequestBean );
    }

    @GetMapping("/get/{amount}")
    public LoanProductResponseBean getLoanProductsByAmount(@PathVariable("amount") double amount) {
        return loanProductService.getLoanProductByAmount(amount);
    }

    @GetMapping("/get/{tenure}")
    public LoanProductResponseBean getLoanProductsByTenure( @RequestParam int tenure ) {
        return loanProductService.getLoanProductByAmount( tenure );
    }

    @GetMapping("/get")
    public List<LoanProductResponseBean> getLoanProducts() {
        return loanProductService.getLoanProducts();
    }

}
