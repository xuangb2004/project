module btl.project {
    requires javafx.controls;
    requires javafx.fxml;

    opens btl.project to javafx.fxml;
    exports btl.project;
}
