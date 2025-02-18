package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.bean.response.AadhaarResponse;
import com.jocata.loansystem.dao.CustomerDao;
import com.jocata.loansystem.entity.CustomerDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);

    public CustomerDetails getCustomerDetailsByAadhaar(String aadhaarNum) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM CustomerDetails c WHERE c.identityNumber = :aadhaarNum";
        Query<CustomerDetails> query = session.createQuery(hql, CustomerDetails.class);
        query.setParameter("aadhaarNum", aadhaarNum);
        return query.uniqueResult();
    }

    public CustomerDetails addCustomer(CustomerDetails customerDetails) {

        CustomerDetails existingCustomer = getCustomerDetailsByAadhaar(customerDetails.getIdentityNumber());
        if (existingCustomer == null) {
            Session session = sessionFactory.getCurrentSession();
            session.save(customerDetails);
            logger.info("Customer created successfully with Aadhaar number: " + customerDetails.getIdentityNumber());
            return customerDetails;
        }
        logger.info("Customer already exists with Aadhaar number: " + customerDetails.getIdentityNumber());
        return null;
    }

    public CustomerDetails addCustomerUsingAadhaar(AadhaarResponse aadhaarResponse) {

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setIdentityNumber(aadhaarResponse.getAadhaarNum());
        String[] name = aadhaarResponse.getFullName().split(" ");
        customerDetails.setFirstName(name[0]);
        customerDetails.setLastName(name[1]);
        customerDetails.setDob(aadhaarResponse.getDob());
        customerDetails.setAddress(aadhaarResponse.getAddress());
        customerDetails.setPhoneNumber(aadhaarResponse.getContactNumber());
        customerDetails.setEmail(aadhaarResponse.getEmail());

        return addCustomer(customerDetails);
    }

    public String updateCustomer(CustomerDetails customerDetails) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(customerDetails);
        return "Customer updated successfully";
    }

    public String deleteCustomer(String aadhaarNum) {
        Session session = sessionFactory.getCurrentSession();
        CustomerDetails customerDetails = getCustomerDetailsByAadhaar(aadhaarNum);
        session.delete(customerDetails);
        return "Customer deleted successfully";
    }
}
