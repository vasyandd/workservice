package work.app.contract;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ContractRepositoryImpl implements ContractRepository{
    private SessionFactory sessionFactory;

    public ContractRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveContract(Contract contract) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(contract);
        session.getTransaction().commit();
        session.close();
    }
}
