package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

    TextArea textArea;
    HomePage homePage;
    String openedFileName;
    MenuBar menuBar;
    Menu fileMenu;
    MenuItem newItem, saveItem, exitItem;
    BorderPane root;
    Scene scene;

    public Main(HomePage homePage) {
        this.homePage = homePage;
    }

    public Main(HomePage homePage, String fileName) {
        this.homePage = homePage;
        this.openedFileName = fileName;
    }

    public void start(Stage primaryStage) {
        init();
        setLayout();
        setAction(primaryStage);

        if (openedFileName != null) {
            textArea.setText(Storage.getContent(openedFileName));
            primaryStage.setTitle(openedFileName);
        } else {
            primaryStage.setTitle("JavaFX Notepad");
        }

        scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public void init() {
        textArea = new TextArea();
        menuBar = new MenuBar();
        fileMenu = new Menu("File");
        newItem = new MenuItem("New");
        saveItem = new MenuItem("Save");
        exitItem = new MenuItem("Exit");
        root = new BorderPane();
    }

    public void setLayout() {
        fileMenu.getItems().addAll(newItem, saveItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);
        root.setTop(menuBar);
        root.setCenter(textArea);
    }

    public void setAction(Stage primaryStage) {
        newItem.setOnAction(e -> textArea.clear());

        saveItem.setOnAction(e -> {
            if (openedFileName != null) {
                Storage.updateFile(openedFileName, textArea.getText());
                System.out.println("Updated: " + openedFileName);
                return;
            }

            TextInputDialog dialog = new TextInputDialog("text.txt");
            dialog.setTitle("Save file");
            dialog.setHeaderText("Save file");
            dialog.setContentText("Rename file:");

            dialog.showAndWait().ifPresent(fileName -> {
                if (!fileName.endsWith(".txt")) {
                    fileName += ".txt";
                }

                String baseName = fileName.substring(0, fileName.length() - 4);

                if (!Storage.isAlphanumeric(baseName)) {
                    showError("File name must be alphanumeric (without extension).");
                    return;
                }

                if (Storage.exists(fileName)) {
                    showError("A file with this name already exists.");
                    return;
                }

                boolean success = Storage.saveFile(fileName, textArea.getText());
                if (success) {
                    System.out.println("Saved in memory: " + fileName);
                    homePage.updateShortcuts();
                    primaryStage.setTitle(fileName);
                    this.openedFileName = fileName;
                }
            });
        });

        exitItem.setOnAction(e -> primaryStage.close());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
