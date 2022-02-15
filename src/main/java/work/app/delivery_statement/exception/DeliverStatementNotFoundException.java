package work.app.delivery_statement.exception;

import work.app.delivery_statement.model.DeliveryStatement;
import work.app.delivery_statement.model.DeliveryStatementRow;

public class DeliverStatementNotFoundException extends RuntimeException{

    public DeliverStatementNotFoundException(String message) {
        super(message);
    }
}
