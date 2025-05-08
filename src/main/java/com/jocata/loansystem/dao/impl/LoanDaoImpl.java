package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.LoanDao;
import com.jocata.loansystem.entity.LoanDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public class LoanDaoImpl implements LoanDao {

    @Autowired
    private final SessionFactory sessionFactory;

    LoanDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public LoanDetails addLoanDetails(LoanDetails loanDetails) {

        Session session = sessionFactory.getCurrentSession();
        session.save(loanDetails);
        return loanDetails;
    }

    public LoanDetails getLoanDetailsById(int applicationId ) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM LoanDetails L WHERE L.applicationId = :applicationId";
        Query<LoanDetails> query = session.createQuery(hql, LoanDetails.class);
        query.setParameter("applicationId", applicationId);
        return query.uniqueResult();
    }

    @Override
    public LoanDetails getLoan(int loanId) {
        Session session = sessionFactory.getCurrentSession();
        LoanDetails loanDetails = session.find(LoanDetails.class, loanId);
        return loanDetails;
    }

}
