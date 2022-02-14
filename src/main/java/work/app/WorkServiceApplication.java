package work.app;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import work.app.delivery_statement.DeliveryStatementRepository;
import work.app.delivery_statement.DeliveryStatementRepositoryImpl;
import work.app.delivery_statement.DeliveryStatementService;
import work.app.delivery_statement.DeliveryStatementServiceImpl;
import work.app.view.MainFrame;

public class WorkServiceApplication {

    public static void main(String[] args) {

        SessionFactory sessionFactory = loadSessionFactory();
        DeliveryStatementRepository deliveryStatementRepository = new DeliveryStatementRepositoryImpl(sessionFactory);
        DeliveryStatementService deliveryStatementService = new DeliveryStatementServiceImpl(deliveryStatementRepository);

        MainFrame mainFrame = new MainFrame(null, null);

      //  repository.addDeliveryStatement(new DeliveryStatement(contract, product, (short) 2019, shipment));

    }

    public static SessionFactory loadSessionFactory() {
        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            System.out.println(e.getMessage());
            StandardServiceRegistryBuilder.destroy( registry );
        }

        return sessionFactory;
    }
}
