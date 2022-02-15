package work.app;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import work.app.delivery_statement.DeliveryStatementRepository;
import work.app.delivery_statement.DeliveryStatementService;
import work.app.delivery_statement.DeliveryStatementServiceImpl;
import work.app.view.MainFrame;

public class WorkServiceApplication {

    public static void main(String[] args) {


        MainFrame mainFrame = new MainFrame(null, null);


    }


}
