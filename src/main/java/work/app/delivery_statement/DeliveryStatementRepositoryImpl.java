package work.app.delivery_statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import work.app.delivery_statement.model.DeliveryStatement;

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
    public DeliveryStatement findByContract(String contractNumber) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        DeliveryStatement deliveryStatement = session.createQuery("from DeliveryStatement ds where ds.contractNumber = :number", DeliveryStatement.class)
                .setParameter("number", contractNumber).getSingleResult();
        session.getTransaction().commit();
        return deliveryStatement;
    }

}
