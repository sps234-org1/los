package com.jocata.loansystem.controller;


import com.jocata.loansystem.entity.CreditScoreDetails;
import com.jocata.loansystem.service.CreditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Autowired
    private CreditScoreService creditScoreService;

    @PostMapping("/credit/create")
    public String createCreditScore(@RequestBody CreditScoreDetails creditScoreDetails) {
        return creditScoreService.createCreditScore( creditScoreDetails );
    }

//    @RequestMapping("/credit/get")
//    public CreditScoreDetails getCreditScoreDetail( String creditScoreId ) {
//        return creditScoreService.getCreditScoreDetail( creditScoreId );
//    }


}
