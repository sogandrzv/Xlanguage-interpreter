package sample;

import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class Controller {
    private static Stage stage;
    private File file;

    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    public void choose(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        file = fileChooser.showOpenDialog(stage);
    }

    public void open(ActionEvent e) {//Open and show the chosen file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (!file.getName().endsWith(".txt")) {
                throw new InvalidFileException();
            }
            Stage area = new Stage();
            TextArea textArea = textAreaMaker();
            area.setTitle("File");
            Image icon = new Image(getClass().getResourceAsStream("icon.jpg"));
            area.getIcons().add(icon);
            VBox root = new VBox();
            root.setBackground(new Background(
                    new BackgroundImage(
                            new Image("https://s19.picofile.com/file/8437478226/Back.jpg"),
                            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                            new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                    ))
            );
            Scene scene = new Scene(root, 800, 800, Color.BLACK);
            area.setScene(scene);
            area.show();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                textArea.appendText(line + "\n");
            }
            root.getChildren().add(textArea);
        } catch (InvalidFileException event) {
            warning("Invalid type");
        } catch (FileNotFoundException event) {
            warning("any type");
        } catch (IOException event) {
            warning("any Type");
        }
    }

    public void compile(ActionEvent e) {//Compile the chosen file
        ArrayList<String> outPuts;
        try {
            if (!file.getName().endsWith(".txt")) {
                throw new InvalidFileException();
            }
            Interpreter interpreter = new Interpreter(file);
            outPuts = Print.getOutPuts();
            Stage area = new Stage();
            TextArea textArea = textAreaMaker();
            area.setTitle("Compile page");
            Image icon = new Image(getClass().getResourceAsStream("icon.jpg"));
            area.getIcons().add(icon);
            area.show();
            area.setFullScreen(true);
            VBox content = new VBox();
            content.setBackground(new Background(
                    new BackgroundImage(new Image("https://s18.picofile.com/file/8437595026/Background.png"),
                            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                            new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                    ))
            );
            Scene scene = new Scene(content, 800, 450);
            area.setScene(scene);
            area.show();
            for (String outPut : outPuts) {
                textArea.appendText(outPut + "\n");
            }
            content.getChildren().add(textArea);
        } catch (NullPointerException event) {
            warning("any type");
        } catch (InvalidFileException event) {
            warning("Invalid type");
        } catch (RedundantVariableException event) {
            warning("redundant");
        } catch (InvalidValueException event) {
            warning("Invalid value");
        }
    }

    public static void warning(String warning) {//Alert for problems in chosen file
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (warning.equalsIgnoreCase("any Type")) {
            alert.setTitle("File Error!");
            alert.setContentText("File not found!");
        } else if (warning.equalsIgnoreCase("Invalid type")) {
            alert.setTitle("File Error!");
            alert.setContentText("You chose Invalid File type!");
        } else if (warning.equalsIgnoreCase("Invalid variable")) {
            alert.setTitle("Variable Error!");
            alert.setContentText("you have invalid variable in your code!");
        } else if (warning.equalsIgnoreCase("Invalid value")) {
            alert.setTitle("Value Error!");
            alert.setContentText("One/multi variables has invalid value! ");
        } else if (warning.equalsIgnoreCase("redundant")) {
            alert.setTitle("Variable Error!");
            alert.setContentText("you have two/more variables with same name");
        }
        alert.showAndWait();
    }

    private TextArea textAreaMaker() {//Create text area for both open and compile parts
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.prefHeight(800.0);
        textArea.prefWidth(800.0);
        textArea.setStyle("-fx-control-inner-background:#000000;" +
                " -fx-font-family: Consolas;" +
                " -fx-highlight-fill: #40B0C3;" +
                " -fx-highlight-text-fill: #000000;" +
                " -fx-text-fill: #d45d9f; ");
        textArea.setFont(Font.font("Candara", 20.0));
        return textArea;
    }

}

