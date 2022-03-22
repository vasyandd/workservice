package work.app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.delivery_statement.DeliveryStatementService;
import work.app.delivery_statement.DeliveryStatement;

import java.math.BigInteger;
import java.net.URL;
import java.time.Month;
import java.util.*;

@Component
@FxmlView("delivery_statement_form.fxml")
public class DeliveryStatementFormController implements Initializable {
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
    private TextField productQuantity;
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
    private TableView<DeliveryStatementRowFromTableView> table;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> numberInTable;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, String> productNameCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, String> productPriceCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> periodCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> productQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> janQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> febQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> marQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> aprQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> mayQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> junQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> julQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> augQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> sepQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> octQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> novQuantityCol;
    @FXML
    private TableColumn<DeliveryStatementRowFromTableView, Integer> decQuantityCol;

    private final ObservableList<DeliveryStatementRowFromTableView> rows = FXCollections.observableArrayList();


    private final DeliveryStatementService deliveryStatementService;

    public DeliveryStatementFormController(DeliveryStatementService deliveryStatementService) {
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setItems(rows);
        numberInTable.setCellValueFactory(new PropertyValueFactory<>("numberInTable"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
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
        DeliveryStatement deliveryStatement = getDeliveryStatementFromTableView();
        deliveryStatementService.saveDeliveryStatement(deliveryStatement);
        successSave();
    }

    private DeliveryStatement getDeliveryStatementFromTableView() {
        List<DeliveryStatement.Row> rows = new ArrayList<>();
        for (DeliveryStatementRowFromTableView d : table.getItems()) {
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
                    d.productQuantity, 0,d.period, shipment, new HashMap<>(), false));
        }
        return new DeliveryStatement(contractNumber.getText().trim(),
                contractDate.getValue(),Integer.parseInt(number.getText()),
                agreementNumber.getText().trim(), rows);
    }

    public void addRowInTable(ActionEvent event) {
        rows.add(new DeliveryStatementRowFromTableView(1, productName.getText(),
                Integer.parseInt(productQuantity.getText()), Integer.parseInt(period.getText()),
                productPrice.getText(), Integer.parseInt(janQuantity.getText()),
                Integer.parseInt(febQuantity.getText()), Integer.parseInt(marQuantity.getText()),
                Integer.parseInt(aprQuantity.getText()), Integer.parseInt(mayQuantity.getText()),
                Integer.parseInt(junQuantity.getText()), Integer.parseInt(julQuantity.getText()),
                Integer.parseInt(augQuantity.getText()), Integer.parseInt(sepQuantity.getText()),
                Integer.parseInt(octQuantity.getText()), Integer.parseInt(novQuantity.getText()),
                Integer.parseInt(decQuantity.getText()))
        );
        clearInputFields();
    }

    private void clearInputFields() {
        productPrice.setText("0");
        productQuantity.setText("0");
        period.setText("0");
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

    private void successSave() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Ведомость поставки сохранена!");
        alert.showAndWait();
    }

    public static class DeliveryStatementRowFromTableView {
        private int numberInTable;
        private String productName;
        private int productQuantity;
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

        public DeliveryStatementRowFromTableView(int numberInTable, String productName, int productQuantity, int period,
                                                 String productPrice, int janQuantity, int febQuantity, int marQuantity,
                                                 int aprQuantity, int mayQuantity, int junQuantity, int julQuantity,
                                                 int augQuantity, int sepQuantity, int octQuantity, int novQuantity,
                                                 int decQuantity) {
            this.numberInTable = numberInTable;
            this.productName = productName;
            this.productQuantity = productQuantity;
            this.period = period;
            this.productPrice = productPrice;
            this.janQuantity = janQuantity;
            this.febQuantity = febQuantity;
            this.marQuantity = marQuantity;
            this.aprQuantity = aprQuantity;
            this.mayQuantity = mayQuantity;
            this.junQuantity = junQuantity;
            this.julQuantity = julQuantity;
            this.augQuantity = augQuantity;
            this.sepQuantity = sepQuantity;
            this.octQuantity = octQuantity;
            this.novQuantity = novQuantity;
            this.decQuantity = decQuantity;
        }

        public int getNumberInTable() {
            return numberInTable;
        }

        public void setNumberInTable(int numberInTable) {
            this.numberInTable = numberInTable;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public int getJanQuantity() {
            return janQuantity;
        }

        public void setJanQuantity(int janQuantity) {
            this.janQuantity = janQuantity;
        }

        public int getFebQuantity() {
            return febQuantity;
        }

        public void setFebQuantity(int febQuantity) {
            this.febQuantity = febQuantity;
        }

        public int getMarQuantity() {
            return marQuantity;
        }

        public void setMarQuantity(int marQuantity) {
            this.marQuantity = marQuantity;
        }

        public int getAprQuantity() {
            return aprQuantity;
        }

        public void setAprQuantity(int aprQuantity) {
            this.aprQuantity = aprQuantity;
        }

        public int getMayQuantity() {
            return mayQuantity;
        }

        public void setMayQuantity(int mayQuantity) {
            this.mayQuantity = mayQuantity;
        }

        public int getJunQuantity() {
            return junQuantity;
        }

        public void setJunQuantity(int junQuantity) {
            this.junQuantity = junQuantity;
        }

        public int getJulQuantity() {
            return julQuantity;
        }

        public void setJulQuantity(int julQuantity) {
            this.julQuantity = julQuantity;
        }

        public int getAugQuantity() {
            return augQuantity;
        }

        public void setAugQuantity(int augQuantity) {
            this.augQuantity = augQuantity;
        }

        public int getSepQuantity() {
            return sepQuantity;
        }

        public void setSepQuantity(int sepQuantity) {
            this.sepQuantity = sepQuantity;
        }

        public int getOctQuantity() {
            return octQuantity;
        }

        public void setOctQuantity(int octQuantity) {
            this.octQuantity = octQuantity;
        }

        public int getNovQuantity() {
            return novQuantity;
        }

        public void setNovQuantity(int novQuantity) {
            this.novQuantity = novQuantity;
        }

        public int getDecQuantity() {
            return decQuantity;
        }

        public void setDecQuantity(int decQuantity) {
            this.decQuantity = decQuantity;
        }
    }
}
