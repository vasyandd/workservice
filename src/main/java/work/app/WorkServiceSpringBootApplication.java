package work.app;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.app.view.FXController;

@SpringBootApplication
public class WorkServiceSpringBootApplication {


    public static void main(String[] args) {
        Application.launch(WorkServiceFXApplication.class, args);
    }



}
