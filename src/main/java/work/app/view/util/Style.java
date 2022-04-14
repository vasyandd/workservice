package work.app.view.util;

public enum Style {

    COMPLETED("completed"),
    EXPIRED("expired"),
    LAST_MONTH("last_month"),
    INVALID_DATA("invalid_data");

    private final String styleClass;

    Style(String styleClass) {
        this.styleClass = styleClass;
    }

    public String styleClass() {
        return styleClass;
    }
}
