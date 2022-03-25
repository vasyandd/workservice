package work.app.view;

import javafx.event.ActionEvent;

import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@FxmlView("main_menu.fxml")
public class MainMenuController {
    private final FxWeaver fxWeaver;

    public MainMenuController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    public void switchSceneToNotificationForm(ActionEvent event) throws IOException {
        Parent root = fxWeaver.loadView(NotificationFormController.class);
        Scene scene = new Scene(root);
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setScene(scene);
        thisStage.setResizable(false);
        thisStage.show();
    }

    public void switchSceneToDeliveryStatementForm(ActionEvent event) throws IOException {
        Parent root = fxWeaver.loadView(DeliveryStatementFormController.class);
        Scene scene = new Scene(root);
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setScene(scene);
        thisStage.setResizable(false);
        thisStage.setX(300);
        thisStage.setY(100);
        thisStage.show();
    }




}
