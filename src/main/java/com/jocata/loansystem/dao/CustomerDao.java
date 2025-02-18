package com.jocata.loansystem.dao;

import com.jocata.loansystem.bean.response.AadhaarResponse;
import com.jocata.loansystem.entity.CustomerDetails;

public interface CustomerDao {

    public CustomerDetails addCustomer(CustomerDetails customerDetails ) ;

    public CustomerDetails addCustomerUsingAadhaar(AadhaarResponse aadhaarResponse);

    public CustomerDetails getCustomerDetailsByAadhaar(String aadhaarNum );

    public String updateCustomer(CustomerDetails customerDetails);

    public String deleteCustomer(String aadhaarNum);



}
