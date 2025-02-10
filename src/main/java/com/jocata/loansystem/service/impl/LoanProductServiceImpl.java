package com.jocata.loansystem.service.impl;

import com.jocata.loansystem.bean.loanproduct.LoanProductRequestBean;
import com.jocata.loansystem.bean.loanproduct.LoanProductResponseBean;
import com.jocata.loansystem.dao.LoanProductDao;
import com.jocata.loansystem.entity.LoanProductDetails;
import com.jocata.loansystem.service.LoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LoanProductServiceImpl implements LoanProductService {

    @Autowired
    LoanProductDao loanProductDao;

    public String addLoanProduct(LoanProductRequestBean loanProductRequestBean){

        String identityNumber = loanProductRequestBean.getIdentityNumber();
        String contactNumber = loanProductRequestBean.getContactNumber();

        String aadhaarRegex = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$";
        String panRegex = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$";
        String mobileRegex = "^[6-9]{1}[0-9]{9}$";

//        if ( ! contactNumber.matches( mobileRegex ) ) {
//            return "Invalid Contact Number";
//        }
//        else if ( ! ( identityNumber.matches( aadhaarRegex ) || identityNumber.matches( panRegex ) ) ) {
//            return "Invalid Identity Number";
//        }

        LoanProductDetails loanProductDetails = new LoanProductDetails();
        loanProductDetails.setProductName( loanProductRequestBean.getProductName() );
        loanProductDetails.setInterestRate( loanProductRequestBean.getInterestRate() );
        loanProductDetails.setTermMonths( loanProductRequestBean.getTermMonths() );
        loanProductDetails.setMaxLoanAmount( loanProductRequestBean.getMaxLoanAmount() );
        loanProductDetails.setMinLoanAmount( loanProductRequestBean.getMinLoanAmount() );
        loanProductDetails.setDescription( loanProductRequestBean.getDescription() );

        return loanProductDao.addLoanProduct( loanProductDetails );
    }

    public List<LoanProductResponseBean> getLoanProducts() {

        List<LoanProductDetails> loanProductDetailsList = loanProductDao.getLoanProducts();
        List<LoanProductResponseBean> loanProductResponseBeanList = new ArrayList<>();

        for ( LoanProductDetails loanProductDetails : loanProductDetailsList ) {
            LoanProductResponseBean loanProductResponseBean = new LoanProductResponseBean();
            loanProductResponseBean.setProductName( loanProductDetails.getProductName() );
            loanProductResponseBean.setInterestRate( loanProductDetails.getInterestRate() );
            loanProductResponseBean.setTermMonths( loanProductDetails.getTermMonths() );
            loanProductResponseBean.setMaxLoanAmount( loanProductDetails.getMaxLoanAmount() );
            loanProductResponseBean.setMinLoanAmount( loanProductDetails.getMinLoanAmount() );
            loanProductResponseBean.setDescription( loanProductDetails.getDescription() );
            loanProductResponseBeanList.add( loanProductResponseBean );
        }
        return loanProductResponseBeanList;
    }

    public LoanProductResponseBean getLoanProductByAmount(double amount) {

        LoanProductDetails loanProductDetails = loanProductDao.getLoanProductByAmount(amount);

        LoanProductResponseBean loanProductResponseBean = new LoanProductResponseBean();
        loanProductResponseBean.setProductName( loanProductDetails.getProductName() );
        loanProductResponseBean.setInterestRate( loanProductDetails.getInterestRate() );
        loanProductResponseBean.setTermMonths( loanProductDetails.getTermMonths() );
        loanProductResponseBean.setMaxLoanAmount( loanProductDetails.getMaxLoanAmount() );
        loanProductResponseBean.setMinLoanAmount( loanProductDetails.getMinLoanAmount() );
        loanProductResponseBean.setDescription( loanProductDetails.getDescription() );
        loanProductResponseBean.setId( loanProductDetails.getId() );
        return loanProductResponseBean;
    }
}
