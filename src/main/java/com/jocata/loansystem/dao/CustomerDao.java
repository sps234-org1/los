package com.jocata.loansystem.dao;

import com.jocata.loansystem.entity.CustomerDetails;

public interface CustomerDao {

    public String createCustomer(CustomerDetails customerDetails ) ;

    public CustomerDetails getCustomerDetailsByAadhaar(String aadhaarNum );

    public String updateCustomer(CustomerDetails customerDetails);

    public String deleteCustomer(String aadhaarNum);



}
