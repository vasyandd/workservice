package work.app.view.util;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class ValidatorInstaller {
    public final static String BAD_COLOR = "-fx-background-color: red;";

    public enum FieldPredicate {
        NOT_EMPTY(textField -> !textField.getText().trim().isEmpty()),
        EMPTY(NOT_EMPTY.predicate.negate()),
        NOT_ZERO(textField -> textField.getText().trim().equals("0")),
        ZERO(NOT_ZERO.predicate.negate()),
        POSITIVE_INTEGER(NOT_EMPTY.predicate
                .and(textField -> {
                    try {
                        int number = Integer.parseInt(textField.getText().trim());
                        return number > 0;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })),
        NOT_NEGATIVE_INTEGER(POSITIVE_INTEGER.predicate.or(ZERO.predicate)),
        POSITIVE_BIG_INTEGER(NOT_EMPTY.predicate
                .and(textField -> {
                    String number = textField.getText().trim();
                    return number.matches("\\d*") && !number.startsWith("-")
                            && !number.equals("0");
                })),
        NOT_NEGATIVE_BIG_INTEGER(POSITIVE_BIG_INTEGER.predicate.or(ZERO.predicate)),
        YEAR(POSITIVE_INTEGER.predicate
                .and(textField -> {
                    int number = Integer.parseInt(textField.getText().trim());
                    return number > 2000 && number < 2100;
                }));

        private Predicate<TextField> predicate;

        FieldPredicate(Predicate<TextField> predicate) {
            this.predicate = predicate;
        }

        public Predicate<TextField> predicate() {
            return predicate;
        }
    }


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
