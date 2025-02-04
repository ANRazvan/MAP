module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.management;


    opens gui to javafx.fxml;
    exports gui;
}