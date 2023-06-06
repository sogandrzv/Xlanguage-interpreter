package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Image icon = new Image(getClass().getResourceAsStream("icon.jpg"));
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("X-Compiler");
            primaryStage.setScene(new Scene(root));
            Controller.setStage(primaryStage);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
