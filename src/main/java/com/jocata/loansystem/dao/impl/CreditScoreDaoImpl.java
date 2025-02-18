package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.bean.response.CibilResponse;
import com.jocata.loansystem.dao.CreditScoreDao;
import com.jocata.loansystem.entity.CreditScoreDetails;
import com.jocata.loansystem.entity.CustomerDetails;
import com.jocata.loansystem.service.impl.LoanApplicationServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;

@Repository
@EnableTransactionManagement
public class CreditScoreDaoImpl implements CreditScoreDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CreditScoreDetails createCreditScoreDetails(CreditScoreDetails creditScoreDetails) {

        Session session = sessionFactory.getCurrentSession();
        session.save(creditScoreDetails);
        logger.info("Credit score details added successfully with credit score id: " + creditScoreDetails.getCreditScoreId());
        return creditScoreDetails;
    }

    public void addCreditScoreDetailsUsingCibilResponse( CibilResponse cibilResponse, CustomerDetails customerDetails) {

        CreditScoreDetails creditScoreDetails = new CreditScoreDetails();
        String reportDate = (cibilResponse.getReportDate() == null) ? LocalDate.now().toString() : cibilResponse.getReportDate();
        String creditScore = (cibilResponse.getCreditScore() == null) ? "700" : cibilResponse.getCreditScore();
        creditScoreDetails.setCreditScore(creditScore);
        creditScoreDetails.setCreditScoreDate(reportDate);
        creditScoreDetails.setCustomerDetails(customerDetails);
        createCreditScoreDetails(creditScoreDetails);
    }

    public CreditScoreDetails getCreditScoreDetails(String creditScoreId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(CreditScoreDetails.class, creditScoreId);
    }

    public String updateCreditScore(CreditScoreDetails creditScoreDetails) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(creditScoreDetails);
        return "Credit score updated successfully";
    }

    public String deleteCreditScore(String panNum) {
        Session session = sessionFactory.getCurrentSession();
        CreditScoreDetails creditScoreDetails = session.get(CreditScoreDetails.class, panNum);
        session.delete(creditScoreDetails);
        return "Credit score deleted successfully";
    }


}
