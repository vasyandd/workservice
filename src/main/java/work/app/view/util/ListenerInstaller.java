package work.app.view.util;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class ListenerInstaller {
    private final static String BAD_COLOR = "-fx-background-color: red;";
    public final static Predicate<TextField> IS_NOT_NEGATIVE_INTEGER_CHECK = textField -> {
        try {
            int number = Integer.parseInt(textField.getText().trim());
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    };
    public final static Predicate<TextField> IS_POSITIVE_INTEGER = IS_NOT_NEGATIVE_INTEGER_CHECK
            .and(textField -> !textField.getText().trim().equals("0"));
    public final static Predicate<TextField> IS_YEAR_CHECK = IS_NOT_NEGATIVE_INTEGER_CHECK.and(textField -> {
       int number = Integer.parseInt(textField.getText().trim());
       return number >= 2000 && number < 2100;
    });
    public final static Predicate<TextField> IS_EMPTY_CHECK = (textField -> textField.getText().trim().isEmpty());
    public final static Predicate<TextField> IS_POSITIVE_INTEGER_OR_EMPTY_CHECK = IS_EMPTY_CHECK
            .or(IS_POSITIVE_INTEGER);



    public void addDigitValidatorFor(TextField...fields) {
        addDigitValidatorFor((textField) -> true, fields);
    }

    public void addDigitValidatorFor(Predicate<TextField> predicate, TextField...fields) {
        for (TextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (isDigit(newValue) && predicate.test(field)) {
                    field.setStyle(null);
                } else {
                    field.setStyle(BAD_COLOR);
                }
            });
        }
    }

    private boolean isDigit(String field) {
        return field.matches("\\d*");
    }
}
