package work.app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.service.DeliveryStatementService;
import work.app.service.model.DeliveryStatement;
import work.app.service.DeliveryStatements;
import work.app.service.model.Notification;

import java.net.URL;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Component
@FxmlView("view_all_information.fxml")
public class ViewAllInformationController implements Initializable {
    private final DeliveryStatementService deliveryStatementService;
    private final ObservableList<String> products = FXCollections.observableArrayList();
    private final ObservableList<String> contracts = FXCollections.observableArrayList();
    private final ObservableList<MainTableRow> rows = FXCollections.observableArrayList();
    private Map<String, DeliveryStatement> deliveryStatementsByContract;
    private Map<String, Set<DeliveryStatement>> deliveryStatementsByProduct;
    private final Map<DeliveryStatement.Row, List<Notification>> notificationsByDeliveryStatement = new HashMap<>();

    private enum Color {
        COMPLETED("-fx-background-color: #3b8618;"),
        EXPIRED("-fx-background-color: #bb3f3f;"),
        LAST_MONTH("-fx-background-color: #8d5025;");

        private final String style;

        Color(String style) {
            this.style = style;
        }

    }

    private boolean contractSelectedInListView;

    @FXML
    private CheckBox viewCompletedRows;
    @FXML
    private CheckBox viewExpiredRows;
    @FXML
    private CheckBox viewLastMonthRows;
    @FXML
    private ListView<String> listOfContractsOrProducts;
    @FXML
    private TableView<MainTableRow> table;
    @FXML
    private Label title;
    @FXML
    private TableColumn<MainTableRow, String> productOrContractCol;
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


    public ViewAllInformationController(DeliveryStatementService deliveryStatementService) {
        this.deliveryStatementService = deliveryStatementService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchInformationForMainTable();
        setTableAndFieldsOptions();

    }

    private void setTableAndFieldsOptions() {
        table.setItems(rows);
        products.addAll(deliveryStatementsByProduct.keySet());
        contracts.addAll(new ArrayList<>(deliveryStatementsByContract.keySet()));
        listOfContractsOrProducts.getSelectionModel().selectedItemProperty()
                .addListener(((observableValue, oldValue, newValue) -> {
                    if (observableValue.getValue() != null) {
                        setSelectedForCheckBoxFields(false, viewCompletedRows, viewLastMonthRows, viewExpiredRows);
                        if (contractSelectedInListView) {
                            fillTableByContract(observableValue.getValue());
                        } else {
                            fillTableByProduct(observableValue.getValue());
                        }
                    } else {
                        title.setVisible(false);
                        rows.clear();
                    }
                }));
        viewCompletedRows.selectedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (observableValue.getValue().booleanValue()) {
                setSelectedForCheckBoxFields(false, viewExpiredRows, viewLastMonthRows);
                table.getItems().removeIf(row -> !row.isCompleted);
            } else {
                String selectedItem = listOfContractsOrProducts.getSelectionModel().selectedItemProperty().get();
                if (contractSelectedInListView) {
                    fillTableByContract(selectedItem);
                } else {
                    fillTableByProduct(selectedItem);
                }
            }
        }));
        viewExpiredRows.selectedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (observableValue.getValue().booleanValue()) {
                setSelectedForCheckBoxFields(false, viewCompletedRows, viewLastMonthRows);
                table.getItems().removeIf(row -> !row.isExpired);
            } else {
                String selectedItem = listOfContractsOrProducts.getSelectionModel().selectedItemProperty().get();
                if (contractSelectedInListView) {
                    fillTableByContract(selectedItem);
                } else {
                    fillTableByProduct(selectedItem);
                }
            }
        }));
        viewLastMonthRows.selectedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (observableValue.getValue().booleanValue()) {
                setSelectedForCheckBoxFields(false, viewExpiredRows, viewCompletedRows);
                table.getItems().removeIf(row -> !row.isLastMonthNow);
            } else {
                String selectedItem = listOfContractsOrProducts.getSelectionModel().selectedItemProperty().get();
                if (contractSelectedInListView) {
                    fillTableByContract(selectedItem);
                } else {
                    fillTableByProduct(selectedItem);
                }
            }
        }));
        setCellValueFactories();
        setRowFactories();
    }

    private void setVisibleForFields(boolean isVisible, CheckBox... fields) {
        for (CheckBox f : fields) {
            f.setVisible(isVisible);
        }
    }

    ;

    private void setSelectedForCheckBoxFields(boolean isSelected, CheckBox... fields) {
        for (CheckBox f : fields) {
            f.setSelected(isSelected);
        }
    }

    private void fetchInformationForMainTable() {
        List<DeliveryStatement> deliveryStatements = deliveryStatementService.getAllDeliveryStatements();
        deliveryStatementsByProduct = DeliveryStatements.structureByProduct(deliveryStatements);
        deliveryStatements.forEach(ds ->
                notificationsByDeliveryStatement.putAll(DeliveryStatements.structureNotificationsByDeliveryStatementRow(ds)));
        deliveryStatementsByContract = DeliveryStatements.structureByContract(deliveryStatements);
    }

    private void setRowFactories() {
        table.setRowFactory(tv -> new TableRow<>() {
            @Override
            public void updateItem(MainTableRow item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.isCompleted) {
                    setStyle(Color.COMPLETED.style);
                } else if (item.isExpired) {
                    setStyle(Color.EXPIRED.style);
                } else if (item.isLastMonthNow) {
                    setStyle(Color.LAST_MONTH.style);
                } else {
                    setStyle("");
                }
            }
        });
    }

    private void fillTableByProduct(String key) {
        rows.clear();
        Set<DeliveryStatement> deliveryStatements = deliveryStatementsByProduct.get(key);
        for (DeliveryStatement d : deliveryStatements) {
            rows.addAll(d.getRows().stream()
                    .filter(row -> row.getProductName().equals(key))
                    .map(row -> {
                        Map<Month, String> productQuantityByMonth = row.getProductQuantityWithSlash();
                        return new MainTableRow(d.getContract().toString(), row.getPeriod(),
                                row.getActualProductQuantity(), row.getScheduledProductQuantity(),
                                row.getPriceForOneProduct().toString(), productQuantityByMonth.get(Month.JANUARY),
                                productQuantityByMonth.get(Month.FEBRUARY), productQuantityByMonth.get(Month.MARCH),
                                productQuantityByMonth.get(Month.APRIL), productQuantityByMonth.get(Month.MAY),
                                productQuantityByMonth.get(Month.JUNE), productQuantityByMonth.get(Month.JULY),
                                productQuantityByMonth.get(Month.AUGUST), productQuantityByMonth.get(Month.SEPTEMBER),
                                productQuantityByMonth.get(Month.OCTOBER), productQuantityByMonth.get(Month.NOVEMBER),
                                productQuantityByMonth.get(Month.DECEMBER),
                                Notification.mapListNotificationsToString(notificationsByDeliveryStatement.get(row)),
                                row.isClosed(), row.isExpired(), row.isLastMonthNow());
                    })
                    .collect(Collectors.toList()));
        }
        title.setText("Все ведомости поставки по изделию " + key);
        title.setVisible(true);
        setVisibleForFields(true, viewCompletedRows, viewLastMonthRows, viewExpiredRows);
    }

    private void setCellValueFactories() {
        productOrContractCol.setCellValueFactory(new PropertyValueFactory<>("productOrContract"));
        productOrContractCol.setSortable(true);
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        actualProductQuantityCol.setCellValueFactory(new PropertyValueFactory<>("actualProductQuantity"));
        scheduledProductQuantityCol.setCellValueFactory(new PropertyValueFactory<>("scheduledProductQuantity"));
        periodCol.setCellValueFactory(new PropertyValueFactory<>("period"));
        periodCol.setSortable(true);
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
        setWrapFields(noteCol, productOrContractCol);
    }

    @SafeVarargs
    private void setWrapFields(TableColumn<MainTableRow, String>... cols) {
        for (TableColumn<MainTableRow, String> col : cols) {
            col.setCellFactory(tc -> {
                TableCell<MainTableRow, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(noteCol.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            });
        }
    }

    private void fillTableByContract(String key) {
        rows.clear();
        DeliveryStatement deliveryStatement = deliveryStatementsByContract.get(key);
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
                            productQuantityByMonth.get(Month.DECEMBER),
                            Notification.mapListNotificationsToString(notificationsByDeliveryStatement.get(row)),
                            row.isClosed(), row.isExpired(), row.isLastMonthNow());
                })
                .collect(Collectors.toList()));
        title.setText(deliveryStatement.toString());
        title.setVisible(true);
        setVisibleForFields(true, viewCompletedRows, viewLastMonthRows, viewExpiredRows);
    }


    public void fillProductsList(ActionEvent event) {
        setVisibleForFields(false, viewExpiredRows, viewLastMonthRows, viewCompletedRows);
        contractSelectedInListView = false;
        listOfContractsOrProducts.setItems(products);
    }

    public void fillContractsList(ActionEvent event) {
        setVisibleForFields(false, viewExpiredRows, viewLastMonthRows, viewCompletedRows);
        contractSelectedInListView = true;
        listOfContractsOrProducts.setItems(contracts);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class MainTableRow {
        private String productOrContract;
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
        private boolean isCompleted;
        private boolean isExpired;
        private boolean isLastMonthNow;
    }
}
