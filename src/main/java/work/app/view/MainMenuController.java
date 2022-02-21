package work.app.view;

import javafx.event.ActionEvent;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.*;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;


@Component
@FxmlView("main_menu.fxml")
public class MainMenuController {
    private FxWeaver fxWeaver;

    public MainMenuController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    public void switchSceneToNotificationForm(ActionEvent event) throws IOException {
        Parent root = fxWeaver.loadView(NotificationFormController.class);
        Scene scene = new Scene(root);
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setScene(scene);
        thisStage.show();
    }

    public void switchSceneToDeliveryStatementForm(ActionEvent event) throws IOException {
        Parent root = fxWeaver.loadView(DeliveryStatementFormController.class);
        Scene scene = new Scene(root);
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setScene(scene);
        thisStage.show();
    }


}
