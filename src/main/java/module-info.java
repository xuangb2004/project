module btl.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens btl.project to javafx.fxml;

    exports btl.project;
}
