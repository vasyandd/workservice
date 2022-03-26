package work.app.view.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
public class SceneSwitcher {
    private FxWeaver fxWeaver;

    public SceneSwitcher(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    public void switchSceneTo(Class<?> clazz, ActionEvent event) {
        Parent root = fxWeaver.loadView(clazz);
        Scene scene = new Scene(root);
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setScene(scene);
        thisStage.setResizable(false);
        thisStage.centerOnScreen();
        thisStage.show();
    }
}
