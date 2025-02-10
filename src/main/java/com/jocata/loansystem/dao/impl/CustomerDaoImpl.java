package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.CustomerDao;
import com.jocata.loansystem.entity.CustomerDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public String createCustomer(CustomerDetails customerDetails ) {

        Session session = sessionFactory.getCurrentSession();
        session.save( customerDetails );
        return "Customer created successfully";
    }

    public CustomerDetails getCustomerDetailsByAadhaar(String aadhaarNum ) {

            Session session = sessionFactory.getCurrentSession();
            String hql = "FROM CustomerDetails c WHERE c.identityNumber = :aadhaarNum";
            Query<CustomerDetails> query = session.createQuery(hql, CustomerDetails.class);
            query.setParameter("aadhaarNum", aadhaarNum);
            return query.uniqueResult();
    }

    public String updateCustomer(CustomerDetails customerDetails){
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
