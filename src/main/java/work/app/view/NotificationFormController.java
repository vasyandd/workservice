package work.app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.delivery_statement.model.Contract;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.service.DeliveryStatementService;
import work.app.exception.DeliverStatementNotFoundException;
import work.app.notification.model.Notification;
import work.app.notification.service.NotificationService;
import work.app.view.util.InformationWindow;
import work.app.view.util.SceneSwitcher;
import work.app.view.util.ValidatorInstaller;

import java.net.URL;
import java.util.*;

import static work.app.view.util.ValidatorInstaller.FieldPredicate.POSITIVE_INTEGER;

@Component
@FxmlView("notification_form.fxml")
public class NotificationFormController implements Initializable {
    private final NotificationService notificationService;
    private final SceneSwitcher switcher;
    private final DeliveryStatementService deliveryStatementService;
    private final ValidatorInstaller validator;
    @FXML
    private TextField number;
    @FXML
    private DatePicker date;
    @FXML
    private TextField productQuantity;
    @FXML
    ChoiceBox<Contract> contractBox;
    @FXML
    ChoiceBox<String> productBox;
    @FXML
    private TextField productNumbers;
    ObservableList<Contract> contracts = FXCollections.observableArrayList();
    ObservableList<String> products = FXCollections.observableArrayList();
    Map<Contract, Set<String>> productsByContract = new HashMap<>();

    public NotificationFormController(NotificationService notificationService, SceneSwitcher switcher, DeliveryStatementService deliveryStatementService, ValidatorInstaller validator) {
        this.notificationService = notificationService;
        this.switcher = switcher;
        this.deliveryStatementService = deliveryStatementService;
        this.validator = validator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<DeliveryStatement> deliveryStatements = deliveryStatementService.getOpenDeliveryStatements();
        deliveryStatements.forEach(d -> productsByContract.put(d.getContract(), d.getNotDeliveredProducts()));
        contractBox.setItems(contracts);
        contracts.addAll(productsByContract.keySet());
        productBox.setItems(products);
        contractBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            products.clear();
            if (!contracts.isEmpty())
            products.addAll(productsByContract.get(observable.getValue()));
        });
        validator.addValidatorFor(POSITIVE_INTEGER.predicate(), number, productQuantity);
    }


    public void saveNotification(ActionEvent event) {
        Contract selectedContract = contractBox.getValue();
        Notification notification = new Notification(null, Integer.parseInt(number.getText()),
                date.getValue(), productBox.getSelectionModel().getSelectedItem(),
                Integer.parseInt(productQuantity.getText().trim()), productNumbers.getText().trim(),
                selectedContract);
        try {
            notificationService.saveNotification(notification);
            InformationWindow.viewSuccessSaveWindow("Извещение сохранено!");
            contracts.clear();
            switcher.switchSceneTo(MainMenuController.class, event);
        } catch (DeliverStatementNotFoundException ex) {
           InformationWindow.viewInputDataNotValidWindow(ex.getMessage());
        }

    }



}
