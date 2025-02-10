package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.CreditScoreDao;
import com.jocata.loansystem.entity.CreditScoreDetails;
import com.jocata.loansystem.service.impl.LoanApplicationServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public class CreditScoreDaoImpl implements CreditScoreDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String createCreditScore(CreditScoreDetails creditScoreDetails ) {

        Session session = sessionFactory.getCurrentSession();
        session.save( creditScoreDetails );
        logger.info( "credit score {}  score date {}  ", creditScoreDetails.getCreditScore(), creditScoreDetails.getCreditScoreDate() );
        return "Credit score created successfully";
    }

    public CreditScoreDetails getCreditScoreDetailsByCustomer( String customerId ) {
        Session session = sessionFactory.getCurrentSession();
        return session.get( CreditScoreDetails.class, customerId );
    }

    public String updateCreditScore(CreditScoreDetails creditScoreDetails) {
        Session session = sessionFactory.getCurrentSession();
        session.merge( creditScoreDetails );
        return "Credit score updated successfully";
    }

    public String deleteCreditScore(String panNum) {
        Session session = sessionFactory.getCurrentSession();
        CreditScoreDetails creditScoreDetails = session.get( CreditScoreDetails.class, panNum );
        session.delete( creditScoreDetails );
        return "Credit score deleted successfully";
    }


}
