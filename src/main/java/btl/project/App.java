package btl.project;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("login"), 640, 400);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        if (fxml.equals("login")) {
            primaryStage.setWidth(640);
            primaryStage.setHeight(400);
        } else if (fxml.equals("hotel")) {
            primaryStage.setMaximized(true);
        } else if (fxml.equals("admin")) {
            primaryStage.setWidth(1350);
            primaryStage.setHeight(775);
            primaryStage.centerOnScreen();
        } else if (fxml.equals("guest")) {
            primaryStage.setWidth(1350);
            primaryStage.setHeight(775);
            primaryStage.centerOnScreen();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}