module btl.project {
    requires java.desktop;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens btl.project to javafx.fxml;

    exports btl.project;
    exports btl.classes;
}
