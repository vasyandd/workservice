package work.app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.service.DeliveryStatementService;

import java.net.URL;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@FxmlView("view_all_information.fxml")
public class ViewAllInformationController implements Initializable {
    private DeliveryStatementService deliveryStatementService;
    private ObservableList<String> products = FXCollections.observableArrayList();
    private ObservableList<String> contracts = FXCollections.observableArrayList();
    private ObservableList<MainTableRow> rows = FXCollections.observableArrayList();
    private boolean contractSelectedInListView;
    @FXML
    private ListView<String> listOfContractsOrProducts;
    @FXML
    private TableView<MainTableRow> table;
    @FXML
    private Label title;
    @FXML
    private TableColumn<MainTableRow, String> productNameCol;
    @FXML
    private TableColumn<MainTableRow, String> productPriceCol;
    @FXML
    private TableColumn<MainTableRow, Integer> periodCol;
    @FXML
    private TableColumn<MainTableRow, Integer> scheduledProductQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> actualProductQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> janQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> febQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> marQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> aprQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> mayQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> junQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> julQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> augQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> sepQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> octQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> novQuantityCol;
    @FXML
    private TableColumn<MainTableRow, Integer> decQuantityCol;
    @FXML
    private TableColumn<MainTableRow, String> noteCol;
    private final Map<String, DeliveryStatement> deliveryStatementByContract= new HashMap<>();
    private final Map<String, DeliveryStatement> deliveryStatementByProduct= new HashMap<>();

    public ViewAllInformationController(DeliveryStatementService deliveryStatementService) {
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setItems(rows);
        List<DeliveryStatement> deliveryStatements = deliveryStatementService.getAllDeliveryStatements();
        deliveryStatements.forEach(d -> {
            products.addAll(d.getAllProducts());
            contracts.add(d.getContract().toString());
            deliveryStatementByContract.put(d.getContract().toString(), d);
            deliveryStatementByProduct.put(d.getAllProducts().toString(), d);
        });
        listOfContractsOrProducts.getSelectionModel().selectedItemProperty()
                .addListener(((observableValue, oldValue, newValue) -> {
                    if (contractSelectedInListView && observableValue.getValue() != null) {
                        fillTableByContract(observableValue.getValue());
                    } else {
                        title.setVisible(false);
                        rows.clear();
                    }
                }));
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        actualProductQuantityCol.setCellValueFactory(new PropertyValueFactory<>("actualProductQuantity"));
        scheduledProductQuantityCol.setCellValueFactory(new PropertyValueFactory<>("scheduledProductQuantity"));
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
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        noteCol.setCellFactory(new Callback<TableColumn<MainTableRow, String>, TableCell<MainTableRow, String>>() {

            @Override
            public TableCell<MainTableRow, String> call(
                    TableColumn<MainTableRow, String> param) {
                TableCell<MainTableRow, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(cell.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell ;
            }

        });
    }
    
    private void fillTableByContract(String key) {
        rows.clear();
        DeliveryStatement deliveryStatement = deliveryStatementByContract.get(key);
        rows.addAll(deliveryStatement.getRows().stream()
                .map(row -> {
                    Map<Month, String> productQuantityByMonth = row.getProductQuantityWithSlash();
                    return new MainTableRow(row.getProductName(), row.getPeriod(),
                        row.getActualProductQuantity(), row.getScheduledProductQuantity(),
                        row.getPriceForOneProduct().toString(), productQuantityByMonth.get(Month.JANUARY),
                        productQuantityByMonth.get(Month.FEBRUARY), productQuantityByMonth.get(Month.MARCH),
                        productQuantityByMonth.get(Month.APRIL), productQuantityByMonth.get(Month.MAY),
                        productQuantityByMonth.get(Month.JUNE), productQuantityByMonth.get(Month.JULY),
                        productQuantityByMonth.get(Month.AUGUST), productQuantityByMonth.get(Month.SEPTEMBER),
                        productQuantityByMonth.get(Month.OCTOBER), productQuantityByMonth.get(Month.NOVEMBER),
                        productQuantityByMonth.get(Month.DECEMBER), row.getNotificationInfo());
                })
                .collect(Collectors.toList()));
        title.setText(deliveryStatement.toString());
        title.setVisible(true);
    }


    public void fillProductsList(ActionEvent event) {
        contractSelectedInListView = false;
        listOfContractsOrProducts.setItems(products);
    }

    public void fillContractsList(ActionEvent event) {
        contractSelectedInListView = true;
        listOfContractsOrProducts.setItems(contracts);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class MainTableRow {
        private String productName;
        private int period;
        private int actualProductQuantity;
        private int scheduledProductQuantity;
        private String productPrice;
        private String janQuantity;
        private String febQuantity;
        private String marQuantity;
        private String aprQuantity;
        private String mayQuantity;
        private String junQuantity;
        private String julQuantity;
        private String augQuantity;
        private String sepQuantity;
        private String octQuantity;
        private String novQuantity;
        private String decQuantity;
        private String note;
    }
}
