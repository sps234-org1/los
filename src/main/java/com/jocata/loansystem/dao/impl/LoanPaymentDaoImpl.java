package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.LoanPaymentDao;
import com.jocata.loansystem.entity.LoanPaymentDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Repository
@EnableTransactionManagement
public class LoanPaymentDaoImpl implements LoanPaymentDao {

    @Autowired
    private final SessionFactory sessionFactory;

    public LoanPaymentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional( )
    public LoanPaymentDetails makePayment(LoanPaymentDetails loanPaymentDetails ) {
        Session session = sessionFactory.getCurrentSession();
        Integer id = (Integer) session.save(loanPaymentDetails);
        session.flush();
        session.refresh(loanPaymentDetails);
        LoanPaymentDetails loanPaymentDetailsDB = session.get(LoanPaymentDetails.class, id);
        return loanPaymentDetailsDB;
    }



}
