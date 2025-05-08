package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.LoanDisbursementDao;
import com.jocata.loansystem.entity.LoanDisbursementDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public class LoanDisbursementDaoImpl implements LoanDisbursementDao {

    @Autowired
    private final SessionFactory sessionFactory;

    LoanDisbursementDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public LoanDisbursementDetails addLoanDisbursementDetails(LoanDisbursementDetails loanDisbursementDetails) {
        Session session = sessionFactory.openSession();
        session.save(loanDisbursementDetails);
        return loanDisbursementDetails;
    }

    public LoanDisbursementDetails getLoanDisbursementDetails(String loanId) {

        Session session = sessionFactory.openSession();
        String hql = "from LoanDisbursementDetails where loanId = :loanId";
        Query<LoanDisbursementDetails> query = session.createQuery(hql, LoanDisbursementDetails.class);
        query.setParameter("loanId", loanId);
        return query.uniqueResult();
    }

    public LoanDisbursementDetails deleteLoanDisbursementDetails(String loanId) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "delete from LoanDisbursementDetails where loanId = :loanId";
        Query<LoanDisbursementDetails> query = session.createQuery(hql, LoanDisbursementDetails.class);
        query.setParameter("loanId", loanId);

        return query.uniqueResult();


    }


}
