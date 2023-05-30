module StrongHold2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires passay;
    requires json.simple;

    opens view to javafx.fxml;
    exports view;
}