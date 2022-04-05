package work.app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.exception.DeliverStatementNotFoundException;
import work.app.service.DeliveryStatementService;
import work.app.service.NotificationService;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;
import work.app.service.model.DeliveryStatements;
import work.app.service.model.Notification;
import work.app.view.util.InformationWindow;
import work.app.view.util.SceneSwitcher;
import work.app.view.util.Validator;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static work.app.view.util.Validator.FieldPredicate.POSITIVE_INTEGER;

@Component
@FxmlView("notification_form.fxml")
public class NotificationFormController implements Initializable {
    private final NotificationService notificationService;
    private final SceneSwitcher switcher;
    private final DeliveryStatementService deliveryStatementService;
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
    @FXML
    private Label invisibleInfoLabel;
    @FXML
    private Label invisibleProductQuantityLabel;

    ObservableList<Contract> contracts = FXCollections.observableArrayList();
    ObservableList<String> products = FXCollections.observableArrayList();
    Map<Contract, Set<String>> productsByContract = new HashMap<>();

    public NotificationFormController(NotificationService notificationService, SceneSwitcher switcher, DeliveryStatementService deliveryStatementService) {
        this.notificationService = notificationService;
        this.switcher = switcher;
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<DeliveryStatement> deliveryStatements = deliveryStatementService.getOpenDeliveryStatements();
        date.getEditor().textProperty().addListener(((observableValue, newValue, oldValue) -> {
            LocalDate date2 = date.getValue();
            productsByContract = DeliveryStatements.structureProductsByContractForPeriod(deliveryStatements, date2.getYear());
            contracts.clear();
            contracts.addAll(productsByContract.keySet());
        }));
        contractBox.setItems(contracts);
        productBox.setItems(products);
        contractBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            products.clear();
            if (!contracts.isEmpty())
                products.addAll(productsByContract.get(observable.getValue()));
        });
        productBox.getSelectionModel().selectedItemProperty().addListener(((observableValue, newValue, oldValue) -> {
            if (!observableValue.getValue().isEmpty()) {
                invisibleInfoLabel.setVisible(true);
                invisibleProductQuantityLabel.setText("sadasdasdas");
                invisibleProductQuantityLabel.setVisible(true);
            }
            else {
                invisibleInfoLabel.setVisible(false);
                invisibleProductQuantityLabel.setVisible(false);
            }
        }));
        Validator.addValidatorFor(POSITIVE_INTEGER.predicate(), number, productQuantity);
    }


    public void saveNotification(ActionEvent event) {
        Contract selectedContract = contractBox.getValue();
        Notification notification = new Notification(Integer.parseInt(number.getText()),
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
