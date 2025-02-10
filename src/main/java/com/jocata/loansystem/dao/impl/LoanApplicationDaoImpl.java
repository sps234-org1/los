package com.jocata.loansystem.dao.impl;

import com.jocata.loansystem.dao.LoanApplicationDao;
import com.jocata.loansystem.entity.LoanApplicationDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Repository
@EnableTransactionManagement
public class LoanApplicationDaoImpl implements LoanApplicationDao {

    @Autowired
    private final SessionFactory sessionFactory;

    LoanApplicationDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String saveLoanApplication(LoanApplicationDetails LoanApplicationDetails) {
        Session session = sessionFactory.openSession();
        session.save(LoanApplicationDetails);
        return "Loan Application created successfully";
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
