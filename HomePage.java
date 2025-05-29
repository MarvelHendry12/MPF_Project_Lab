package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends Application {

    private VBox shortcutList;
    private Scene scene;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        init();
        setStyle();
        setLayout();
        setActions();

        primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void init() {
        shortcutList = new VBox(10);
        scene = new Scene(shortcutList, 960, 780);
    }

    public void setStyle() {

    }

    public void setLayout() {
        Label notepadLauncher = new Label("Notepad");
        notepadLauncher.setStyle("-fx-font-weight: bold; -fx-underline: true;");
        shortcutList.getChildren().add(notepadLauncher);
    }

    public void setActions() {
        Label notepadLauncher = (Label) shortcutList.getChildren().get(0);
        notepadLauncher.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                Main notepadApp = new Main(this);
                try {
                    notepadApp.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        updateShortcuts();
    }

    public void updateShortcuts() {
        if (shortcutList.getChildren().size() > 1) {
            shortcutList.getChildren().remove(1, shortcutList.getChildren().size());
        }

        for (String fileName : Storage.getAllFiles().keySet()) {
            Label shortcut = new Label(fileName);
            shortcut.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-border-color: gray;");
            shortcut.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                    Main editor = new Main(this, fileName);
                    try {
                        editor.start(new Stage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            shortcutList.getChildren().add(shortcut);
        }
    }
}
