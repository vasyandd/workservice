package work.app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.delivery_statement.DeliveryStatementService;
import work.app.notification.Notification;
import work.app.notification.NotificationService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("notification_form.fxml")
public class NotificationFormController {
    private NotificationService notificationService;
    @FXML
    private TextField number;
    @FXML
    private TextField contractNumber;
    @FXML
    private TextField agreement;
    @FXML
    private DatePicker date;
    @FXML
    private TextField productQuantity;
    @FXML
    private TextField productName;

    public NotificationFormController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void saveNotification(ActionEvent event) {
        Notification notification = new Notification(Integer.parseInt(number.getText().trim()),
                date.getValue(), productName.getText().trim(), Integer.parseInt(productQuantity.getText().trim()),
                contractNumber.getText().trim(), agreement.getText().trim());
        notificationService.saveNotification(notification);
        successSave();
    }

    private void successSave() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Извещение сохранено!");
        alert.showAndWait();
    }

}
