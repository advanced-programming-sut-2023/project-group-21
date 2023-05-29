module StrongHold2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.desktop;
    requires passay;

    exports view;
    opens view to javafx.fxml;
}