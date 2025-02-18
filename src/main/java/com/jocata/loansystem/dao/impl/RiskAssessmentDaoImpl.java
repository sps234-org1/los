package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.RiskAssessmentDao;
import com.jocata.loansystem.entity.RiskAssessmentDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public class RiskAssessmentDaoImpl implements RiskAssessmentDao {

    SessionFactory sessionFactory;

    public RiskAssessmentDaoImpl( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }

    public String addRiskAssessmentDetails( RiskAssessmentDetails riskAssessmentDetails ) {

        Session session = sessionFactory.getCurrentSession();
        session.save( riskAssessmentDetails );
        return "Risk Assessment Details created successfully";
    }
}
