package work.app.delivery_statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DeliveryStatementRepositoryImpl implements DeliveryStatementRepository {

    private SessionFactory sessionFactory;

    public DeliveryStatementRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean saveDeliveryStatement(DeliveryStatement deliveryStatement) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.save(deliveryStatement);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public DeliveryStatement findByContractNumberAndProductNameAndPeriod(String contractNumber, String productName, int year) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        DeliveryStatement deliveryStatement = null;
        session.getTransaction().commit();
        return deliveryStatement;
    }

}
