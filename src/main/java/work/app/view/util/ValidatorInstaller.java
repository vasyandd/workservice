package work.app.view.util;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class ValidatorInstaller {
    public final static String BAD_COLOR = "-fx-background-color: red;";
    public final static Predicate<TextField> IS_NOT_NEGATIVE_INTEGER_CHECK = textField -> {
        String number = textField.getText().trim();
        if (number.matches("\\d*")) {
            return Integer.parseInt(number) >= 0;
        }
        return false;
    };
    public final static Predicate<TextField> IS_POSITIVE_DIGIT_CHECK = textField -> {
        String number = textField.getText().trim();
        if (number.matches("\\d*")) {
            return Integer.parseInt(number) >= 0;
        }
        return false;
    };
    public final static Predicate<TextField> IS_NOT_EMPTY = textField -> !textField.getText().trim().isEmpty();
    public final static Predicate<TextField> IS_POSITIVE_INTEGER = IS_NOT_NEGATIVE_INTEGER_CHECK
            .and(textField -> !textField.getText().trim().equals("0"));
    public final static Predicate<TextField> IS_YEAR_CHECK = IS_NOT_NEGATIVE_INTEGER_CHECK.and(textField -> {
       int number = Integer.parseInt(textField.getText().trim());
       return number >= 2000 && number < 2100;
    });
    public final static Predicate<TextField> IS_EMPTY_CHECK = (textField -> textField.getText().trim().isEmpty());
    public final static Predicate<TextField> IS_POSITIVE_INTEGER_OR_EMPTY_CHECK = IS_EMPTY_CHECK
            .or(IS_POSITIVE_INTEGER);


    public void addValidatorFor(Predicate<TextField> predicate, TextField...textFields) {
        for (TextField field : textFields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (predicate.test(field)) {
                    field.setStyle(null);
                } else {
                    field.setStyle(BAD_COLOR);
                }
            });
        }
    }


}
