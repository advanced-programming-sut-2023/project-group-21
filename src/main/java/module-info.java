module StrongHold2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires passay;
    requires json.simple;
    requires java.desktop;

    opens view to javafx.fxml;
    exports view;
    exports ServerConnection;
    exports controller;
    exports model;
}