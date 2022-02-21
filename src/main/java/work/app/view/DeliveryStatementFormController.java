package work.app.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.delivery_statement.DeliveryStatementService;
import work.app.delivery_statement.model.DeliveryStatement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@FxmlView("delivery_statement_form.fxml")
public class DeliveryStatementFormController {
    private TextField number;
    private TextField contractNumber;
    private DatePicker contractDate;
    private TextField productName;
    private TextField period;
    private TextField productPrice;
    private TextField productQuantity;
    private TextField janQuantity;
    private TextField febQuantity;
    private TextField marQuantity;
    private TextField aprQuantity;
    private TextField mayQuantity;
    private TextField junQuantity;
    private TextField julQuantity;
    private TextField augQuantity;
    private TextField sepQuantity;
    private TextField octQuantity;
    private TextField novQuantity;
    private TextField decQuantity;
    private TextField note;

    private DeliveryStatementService deliveryStatementService;

    public DeliveryStatementFormController(DeliveryStatementService deliveryStatementService) {
        this.deliveryStatementService = deliveryStatementService;
    }

    public void saveDeliveryStatement(ActionEvent event) {
        DeliveryStatement deliveryStatement = new DeliveryStatement();
        deliveryStatement.setContractNumber(contractNumber.getText().trim());
        deliveryStatement.setNumber(Integer.parseInt(number.getText().trim()));
        deliveryStatement.setContractDate(contractDate.getValue());

        deliveryStatementService.saveDeliveryStatement(deliveryStatement);
    }

    private void successSave() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Ведомость поставки сохранена!");
        alert.showAndWait();
    }
}
