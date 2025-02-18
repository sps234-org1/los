package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.LoanApplicationDao;
import com.jocata.loansystem.entity.LoanApplicationDetails;
import com.jocata.loansystem.service.impl.LoanApplicationServiceImpl;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Repository
@EnableTransactionManagement
public class LoanApplicationDaoImpl implements LoanApplicationDao {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

    @Autowired
    private final SessionFactory sessionFactory;

    LoanApplicationDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public LoanApplicationDetails addLoanApplication(LoanApplicationDetails loanApplicationDetails ) {

        LoanApplicationDetails existingLoanApplication = getLoanApplicationByCustomerId( loanApplicationDetails.getCustomerId().toString());
        if ( existingLoanApplication == null ) {
            Session session = sessionFactory.openSession();
            session.persist( loanApplicationDetails );
            logger.info("Loan application created successfully for customer id: " + loanApplicationDetails.getCustomerId());
            return loanApplicationDetails;
        }
        logger.info("Loan application already exists for customer id: " + loanApplicationDetails.getCustomerId());
        return null;
    }

    @Override
    public LoanApplicationDetails getLoanApplicationByCustomerId(String customerId) {
        Session session = sessionFactory.openSession();
        return session.get(LoanApplicationDetails.class, customerId);
    }

    @Override
    public List<LoanApplicationDetails> getAllLoanApplications() {
        Session session = sessionFactory.openSession();
        return session.createQuery("from LoanApplicationDetails").list();
    }

    @Override
    @Transactional
    public String updateLoanApplication(LoanApplicationDetails LoanApplicationDetails) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(LoanApplicationDetails);
        return "Loan Application updated successfully";
    }

    @Override
    public String deleteLoanApplication(Long id) {
        Session session = sessionFactory.openSession();
        LoanApplicationDetails LoanApplicationDetails = session.get(LoanApplicationDetails.class, id);
        session.delete(LoanApplicationDetails);
        return "Loan Application deleted successfully";
    }
}
