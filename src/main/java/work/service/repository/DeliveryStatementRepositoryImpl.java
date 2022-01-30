package work.service.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import work.service.entity.Contract;
import work.service.entity.DeliveryStatement;
import work.service.entity.Product;

import java.util.Collection;
import java.util.List;

public class DeliveryStatementRepositoryImpl implements DeliveryStatementRepository{

    private SessionFactory sessionFactory;

    public DeliveryStatementRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean addDeliveryStatement(DeliveryStatement deliveryStatement) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.save(deliveryStatement);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Collection<DeliveryStatement> getDeliveryStatementsByContract(Contract contract) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        List<DeliveryStatement> deliveryStatement = session.createNativeQuery("select d from DeliveryStatement d where d.contract = ?", DeliveryStatement.class)
                .setParameter(0, contract).getResultList();
        session.getTransaction().commit();
        return deliveryStatement;
    }

    @Override
    public Collection<DeliveryStatement> getDeliveryStatementsByProduct(Product product) {
        return null;
    }
}
