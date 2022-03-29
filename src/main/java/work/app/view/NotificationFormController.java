package work.app.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import work.app.notification.service.NotificationService;
import work.app.view.util.SceneSwitcher;

@Component
@FxmlView("notification_form.fxml")
public class NotificationFormController {
    private final NotificationService notificationService;
    private final SceneSwitcher switcher;
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
    @FXML
    private TextField productNumber;

    public NotificationFormController(NotificationService notificationService, SceneSwitcher switcher) {
        this.notificationService = notificationService;
        this.switcher = switcher;
    }

    public void saveNotification(ActionEvent event) {
//
//        try {
//            notificationService.saveNotification(notification);
//            InformationWindow.viewSuccessSaveWindow("Извещение сохранено!");
//            switcher.switchSceneTo(MainMenuController.class, event);
//        } catch (DeliverStatementNotFoundException ex) {
//           InformationWindow.viewInputDataNotValidWindow(ex.getMessage());
//        }

    }



}
