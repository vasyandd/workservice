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
import work.app.service.DeliveryStatementService;
import work.app.service.DeliveryStatements;
import work.app.service.NotificationService;
import work.app.service.model.Contract;
import work.app.service.model.DeliveryStatement;
import work.app.service.model.Notification;
import work.app.view.util.InformationWindow;
import work.app.view.util.SceneSwitcher;
import work.app.view.util.TextFieldValidator;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static work.app.view.util.TextFieldValidator.FieldPredicate.NOT_EMPTY;
import static work.app.view.util.TextFieldValidator.FieldPredicate.POSITIVE_INTEGER;

@Component
@FxmlView("notification_form.fxml")
public class NotificationFormController implements Initializable {
    private final NotificationService notificationService;
    private final SceneSwitcher switcher;
    private final DeliveryStatementService deliveryStatementService;
    private final TextFieldValidator textFieldValidator;
    @FXML
    private TextField number;
    @FXML
    private DatePicker date;
    @FXML
    private TextField productQuantity;
    @FXML
    private ChoiceBox<Contract> contractBox;
    @FXML
    private ChoiceBox<String> productBox;
    @FXML
    private TextField productNumbers;
    @FXML
    private Label invisibleInfoLabel;
    @FXML
    private Label invisibleProductQuantityLabel;

    private ObservableList<Contract> contracts = FXCollections.observableArrayList();
    private ObservableList<String> products = FXCollections.observableArrayList();
    private Map<Contract, Map<Integer, List<DeliveryStatement.Row>>> productsByContractForPeriod;
    private Map<Contract, Set<DeliveryStatement.Row>> productsByContract = new HashMap<>();
    private Map<String, Integer> productShipment = new HashMap<>();

    public NotificationFormController(NotificationService notificationService, SceneSwitcher switcher, DeliveryStatementService deliveryStatementService, TextFieldValidator textFieldValidator) {
        this.notificationService = notificationService;
        this.switcher = switcher;
        this.deliveryStatementService = deliveryStatementService;
        this.textFieldValidator = textFieldValidator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFieldsOptions();
    }

    private void setFieldsOptions() {
        List<DeliveryStatement> deliveryStatements = deliveryStatementService.getOpenDeliveryStatements();
        productsByContractForPeriod = DeliveryStatements.structureProductsByContractForPeriod(deliveryStatements);
        contracts.addAll(deliveryStatements.stream().map(DeliveryStatement::getContract).collect(Collectors.toList()));
        date.getEditor().textProperty().addListener(((observableValue, newValue, oldValue) -> products.clear()));
        contractBox.setItems(contracts);
        productBox.setItems(products);
        addListenerForChoiceBoxFields();
        textFieldValidator.addValidatorFor(POSITIVE_INTEGER, number, productQuantity);
        textFieldValidator.addValidatorFor(NOT_EMPTY, date.getEditor());
    }

    private void addListenerForChoiceBoxFields() {
        contractBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!contracts.isEmpty() && date.getValue() != null) {
                products.clear();
                Contract contract = observable.getValue();
                Integer period = date.getValue().getYear();
                productShipment.clear();
                products.addAll(productsByContractForPeriod.get(contract).get(period).stream()
                        .peek(row -> productShipment.put(row.getProductName(),
                                row.getScheduledProductQuantity() - row.getActualProductQuantity()))
                        .map(DeliveryStatement.Row::getProductName)
                        .collect(Collectors.toList()));
            }
        });
        productBox.getSelectionModel().selectedItemProperty().addListener(((observableValue, newValue, oldValue) -> {
            String selectedProduct = observableValue.getValue();
            if (selectedProduct != null) {
                invisibleInfoLabel.setVisible(true);
                int shipment = productShipment.get(selectedProduct);
                invisibleProductQuantityLabel.setText(shipment + " шт. " + selectedProduct);
                invisibleProductQuantityLabel.setVisible(true);
            } else {
                invisibleInfoLabel.setVisible(false);
                invisibleProductQuantityLabel.setVisible(false);
            }
        }));
    }

    public void saveNotification(ActionEvent event) {
        if (textFieldValidator.fieldsAreValid(number, date.getEditor(), productQuantity)) {
            Contract selectedContract = contractBox.getValue();
            Notification notification = new Notification(Integer.parseInt(number.getText()),
                    date.getValue(), productBox.getSelectionModel().getSelectedItem(),
                    Integer.parseInt(productQuantity.getText().trim()), productNumbers.getText().trim(),
                    selectedContract);
            notificationService.saveNotification(notification);
            contracts.clear();
            switcher.switchSceneTo(MainMenuController.class, event);
        } else {
            InformationWindow.viewInputDataNotValidWindow("Что-то до сих пор выделено красным!");
        }

    }


}
