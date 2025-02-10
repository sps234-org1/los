package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.LoanProductDao;
import com.jocata.loansystem.entity.LoanProductDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@EnableTransactionManagement
@Repository
public class LoanProductDaoImpl implements LoanProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    public String addLoanProduct(LoanProductDetails loanProductDetails) {
        Session session = sessionFactory.getCurrentSession();
        session.save(loanProductDetails);
        return "Loan Product Added";
    }

    public List<LoanProductDetails> getLoanProducts() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("from LoanProductDetails", LoanProductDetails.class).list();
        return session.createQuery("from LoanProductDetails", LoanProductDetails.class).list();
    }

    public LoanProductDetails getLoanProductByAmount(double amount) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM LoanProductDetails l WHERE :amount BETWEEN l.minLoanAmount AND l.maxLoanAmount";
        return session.createQuery(hql, LoanProductDetails.class)
                .setParameter("amount", amount)
                .uniqueResult();
    }
}
