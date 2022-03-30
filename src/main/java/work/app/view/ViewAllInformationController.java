package work.app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.service.DeliveryStatementService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FxmlView("view_all_information.fxml")
public class ViewAllInformationController implements Initializable {
    private DeliveryStatementService deliveryStatementService;
    private ObservableList<String> products = FXCollections.observableArrayList();
    private ObservableList<String> contracts = FXCollections.observableArrayList();
    @FXML
    private ListView<String> listOfContractsOrProducts;

    public ViewAllInformationController(DeliveryStatementService deliveryStatementService) {
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<DeliveryStatement> deliveryStatements = deliveryStatementService.getAllDeliveryStatements();
        deliveryStatements.forEach(d -> {
            products.addAll(d.getAllProducts());
            contracts.add(d.getContract().toString());
        });
    }

    public void fillProductsList(ActionEvent event) {
        listOfContractsOrProducts.setItems(products);
    }

    public void fillContractsList(ActionEvent event) {
        listOfContractsOrProducts.setItems(contracts);
    }
}
