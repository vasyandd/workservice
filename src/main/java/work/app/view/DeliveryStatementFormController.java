package work.app.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.delivery_statement.model.Contract;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.service.DeliveryStatementService;
import work.app.view.util.InformationWindow;
import work.app.view.util.SceneSwitcher;
import work.app.view.util.ValidatorInstaller;

import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static work.app.view.util.ValidatorInstaller.FieldPredicate.*;

@Component
@FxmlView("delivery_statement_form.fxml")
public class DeliveryStatementFormController implements Initializable {
    private final ObservableList<TableRowInDeliveryStatementForm> rows = FXCollections.observableArrayList();
    private final DeliveryStatementService deliveryStatementService;
    private final SceneSwitcher switcher;
    private final ValidatorInstaller validatorInstaller;
    // FXML fields
    @FXML
    private Button deleteRowButton;
    @FXML
    private TextField number;
    @FXML
    private TextField agreementNumber;
    @FXML
    private TextField contractNumber;
    @FXML
    private DatePicker contractDate;
    @FXML
    private TextField productName;
    @FXML
    private TextField period;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField janQuantity;
    @FXML
    private TextField febQuantity;
    @FXML
    private TextField marQuantity;
    @FXML
    private TextField aprQuantity;
    @FXML
    private TextField mayQuantity;
    @FXML
    private TextField junQuantity;
    @FXML
    private TextField julQuantity;
    @FXML
    private TextField augQuantity;
    @FXML
    private TextField sepQuantity;
    @FXML
    private TextField octQuantity;
    @FXML
    private TextField novQuantity;
    @FXML
    private TextField decQuantity;
    @FXML
    private TableView<TableRowInDeliveryStatementForm> table;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, String> productNameCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, String> productPriceCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> periodCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> productQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> janQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> febQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> marQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> aprQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> mayQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> junQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> julQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> augQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> sepQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> octQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> novQuantityCol;
    @FXML
    private TableColumn<TableRowInDeliveryStatementForm, Integer> decQuantityCol;

    public DeliveryStatementFormController(DeliveryStatementService deliveryStatementService, SceneSwitcher switcher,
                                           ValidatorInstaller validatorInstaller) {
        this.deliveryStatementService = deliveryStatementService;
        this.switcher = switcher;
        this.validatorInstaller = validatorInstaller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFieldsOptions();
        setTableOptions();

    }

    private void setFieldsOptions() {
        period.setText(String.valueOf(LocalDate.now().getYear()));
        validatorInstaller.addValidatorFor(NOT_NEGATIVE_INTEGER.predicate(), janQuantity, febQuantity, marQuantity,
                aprQuantity, mayQuantity, junQuantity, julQuantity, augQuantity,
                sepQuantity, octQuantity, novQuantity, decQuantity);
        validatorInstaller.addValidatorFor(NOT_NEGATIVE_BIG_INTEGER.predicate(), productPrice);
        validatorInstaller.addValidatorFor(NOT_EMPTY.predicate(), productName, contractNumber);
        validatorInstaller.addValidatorFor(POSITIVE_INTEGER.predicate().or(EMPTY.predicate()), number, agreementNumber);
        validatorInstaller.addValidatorFor(YEAR.predicate(), period);
        deleteRowButton.disableProperty().bind(Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
    }

    private void setTableOptions() {
        table.setItems(rows);
        table.setOnMouseClicked(mouseEvent -> {
            TableRowInDeliveryStatementForm model = table.getSelectionModel().getSelectedItem();
            productName.setText(model.productName);
            productPrice.setText(model.productPrice);
            period.setText(String.valueOf(model.period));
            janQuantity.setText(String.valueOf(model.janQuantity));
            febQuantity.setText(String.valueOf(model.febQuantity));
            marQuantity.setText(String.valueOf(model.marQuantity));
            aprQuantity.setText(String.valueOf(model.aprQuantity));
            mayQuantity.setText(String.valueOf(model.mayQuantity));
            junQuantity.setText(String.valueOf(model.junQuantity));
            julQuantity.setText(String.valueOf(model.julQuantity));
            augQuantity.setText(String.valueOf(model.augQuantity));
            sepQuantity.setText(String.valueOf(model.sepQuantity));
            octQuantity.setText(String.valueOf(model.octQuantity));
            novQuantity.setText(String.valueOf(model.novQuantity));
            decQuantity.setText(String.valueOf(model.decQuantity));
        });
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productQuantityCol.setCellValueFactory(cellData -> cellData.getValue().productQuantityProperty().asObject());
        periodCol.setCellValueFactory(new PropertyValueFactory<>("period"));
        janQuantityCol.setCellValueFactory(new PropertyValueFactory<>("janQuantity"));
        febQuantityCol.setCellValueFactory(new PropertyValueFactory<>("febQuantity"));
        marQuantityCol.setCellValueFactory(new PropertyValueFactory<>("marQuantity"));
        aprQuantityCol.setCellValueFactory(new PropertyValueFactory<>("aprQuantity"));
        mayQuantityCol.setCellValueFactory(new PropertyValueFactory<>("mayQuantity"));
        junQuantityCol.setCellValueFactory(new PropertyValueFactory<>("junQuantity"));
        julQuantityCol.setCellValueFactory(new PropertyValueFactory<>("julQuantity"));
        augQuantityCol.setCellValueFactory(new PropertyValueFactory<>("augQuantity"));
        sepQuantityCol.setCellValueFactory(new PropertyValueFactory<>("sepQuantity"));
        octQuantityCol.setCellValueFactory(new PropertyValueFactory<>("octQuantity"));
        novQuantityCol.setCellValueFactory(new PropertyValueFactory<>("novQuantity"));
        decQuantityCol.setCellValueFactory(new PropertyValueFactory<>("decQuantity"));
    }

    public void saveDeliveryStatement(ActionEvent event) {
        if(inputDataForSaveIsValid()) {
            DeliveryStatement deliveryStatement = getDeliveryStatementFromTableView();
            deliveryStatementService.saveDeliveryStatement(deliveryStatement);
            table.getItems().clear();
            InformationWindow.viewSuccessSaveWindow("Ведомость поставки сохранена!");
            switcher.switchSceneTo(MainMenuController.class, event);
        } else {
            InformationWindow.viewInputDataNotValidWindow("В шапке что-то выделено красным");
        }
    }

    private DeliveryStatement getDeliveryStatementFromTableView() {
        List<DeliveryStatement.Row> rows = new ArrayList<>();
        for (TableRowInDeliveryStatementForm d : table.getItems()) {
            Map<Month, Integer> shipment = new HashMap<>();
            shipment.put(Month.JANUARY, d.janQuantity);
            shipment.put(Month.FEBRUARY, d.febQuantity);
            shipment.put(Month.MARCH, d.marQuantity);
            shipment.put(Month.APRIL, d.aprQuantity);
            shipment.put(Month.MAY, d.mayQuantity);
            shipment.put(Month.JUNE, d.junQuantity);
            shipment.put(Month.JULY, d.julQuantity);
            shipment.put(Month.AUGUST, d.augQuantity);
            shipment.put(Month.SEPTEMBER, d.sepQuantity);
            shipment.put(Month.OCTOBER, d.octQuantity);
            shipment.put(Month.NOVEMBER, d.novQuantity);
            shipment.put(Month.DECEMBER, d.decQuantity);
            rows.add(new DeliveryStatement.Row(new BigInteger(d.productPrice.trim()), d.productName.trim(),
                   shipment, new HashMap<>(), false, d.period, new ArrayList<>()));
        }
        Integer currentNumber = number.getText().trim().isEmpty() ? null : Integer.parseInt(number.getText().trim());
        Integer currentAgreementNumber = agreementNumber.getText().trim().isEmpty()
                ? 0 : Integer.parseInt(agreementNumber.getText().trim());
        Contract contract = new Contract(contractNumber.getText().trim(), contractDate.getValue(), currentAgreementNumber);
        return new DeliveryStatement(null, currentNumber, contract , false, rows);
    }

    public void addRowInTable(ActionEvent event) {
        if (inputDataForTableIsValid()) {
            TableRowInDeliveryStatementForm row = mapInputDataToTable();
            rows.add(row);
            clearInputFields();
        } else {
            InformationWindow.viewInputDataNotValidWindow("Что-то все еще выделено красным");
        }
    }

    private boolean inputDataForTableIsValid() {
        return !productName.getStyle().equals(ValidatorInstaller.BAD_COLOR) && !productPrice.getStyle().equals(ValidatorInstaller.BAD_COLOR)
                && !period.getStyle().equals(ValidatorInstaller.BAD_COLOR) && !janQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR)
                && !febQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR) && !marQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR)
                && !aprQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR) && !mayQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR)
                && !junQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR) && !julQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR)
                && !augQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR) && !sepQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR)
                && !octQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR) && !novQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR)
                && !decQuantity.getStyle().equals(ValidatorInstaller.BAD_COLOR);
    }

    private boolean inputDataForSaveIsValid() {
        return !contractNumber.getStyle().equals(ValidatorInstaller.BAD_COLOR) && !agreementNumber.getStyle().equals(ValidatorInstaller.BAD_COLOR)
                && !number.getStyle().equals(ValidatorInstaller.BAD_COLOR);
    }

    public void deleteRowInTable(ActionEvent event) {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
    }


    private TableRowInDeliveryStatementForm mapInputDataToTable() {
            return new TableRowInDeliveryStatementForm(productName.getText(),
                    Integer.parseInt(period.getText()), productPrice.getText(),
                    Integer.parseInt(janQuantity.getText()), Integer.parseInt(febQuantity.getText()),
                    Integer.parseInt(marQuantity.getText()), Integer.parseInt(aprQuantity.getText()),
                    Integer.parseInt(mayQuantity.getText()), Integer.parseInt(junQuantity.getText()),
                    Integer.parseInt(julQuantity.getText()), Integer.parseInt(augQuantity.getText()),
                    Integer.parseInt(sepQuantity.getText()), Integer.parseInt(octQuantity.getText()),
                    Integer.parseInt(novQuantity.getText()), Integer.parseInt(decQuantity.getText()));
    }

    private void clearInputFields() {
        productPrice.setText("0");
        janQuantity.setText("0");
        febQuantity.setText("0");
        marQuantity.setText("0");
        aprQuantity.setText("0");
        mayQuantity.setText("0");
        junQuantity.setText("0");
        julQuantity.setText("0");
        augQuantity.setText("0");
        sepQuantity.setText("0");
        octQuantity.setText("0");
        novQuantity.setText("0");
        decQuantity.setText("0");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TableRowInDeliveryStatementForm {
        private String productName;
        private int period;
        private String productPrice;
        private int janQuantity;
        private int febQuantity;
        private int marQuantity;
        private int aprQuantity;
        private int mayQuantity;
        private int junQuantity;
        private int julQuantity;
        private int augQuantity;
        private int sepQuantity;
        private int octQuantity;
        private int novQuantity;
        private int decQuantity;
        

        IntegerProperty productQuantityProperty() {
            return new SimpleIntegerProperty(janQuantity + febQuantity + marQuantity + aprQuantity + mayQuantity
                    + junQuantity + julQuantity + augQuantity + sepQuantity + octQuantity + novQuantity + decQuantity);
        }

    }
}
