package main;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Homepage {

    Scene sc;
    BorderPane bp;
    StackPane sp;
    HBox shortcutContainer;
    VBox currentColumn;
    Image windowImg, notepadImg, trashImg, notepadImg1, chromeImg, bgImg, logoutImg, shutdownImg;
    ImageView ivWindow, ivNotepad, ivTrash, ivNotepad1, ivChrome, ivBG, ivLogout, ivShutdown;
    Label Trashlbl, Notepadlbl, Chromelbl;
    MenuBar taskbar;
    Menu windowIcon, notepadIcon;
    MenuItem logout, shutdown;
    VBox trash, notepad, chrome;

    Stage chromeStage;

    final int MAX_SHORTCUT_PER_COLUMN = 5;

    private static ArrayList<String> shortcuts = new ArrayList<>();

    public void init() {
        sp = new StackPane();
        bp = new BorderPane();
        shortcutContainer = new HBox(20);
        currentColumn = new VBox(10);
        currentColumn.setAlignment(Pos.TOP_CENTER);
        shortcutContainer.getChildren().add(currentColumn);
        shortcutContainer.setPadding(new Insets(20));

        sc = new Scene(sp, 1300, 900);
        sc.getStylesheets().add("style/Homepage.css");

        windowImg = new Image(getClass().getResourceAsStream("/assets/window-icon.png"));
        notepadImg = new Image(getClass().getResourceAsStream("/assets/notepad-icon.png"));
        trashImg = new Image(getClass().getResourceAsStream("/assets/trash-icon.png"));
        notepadImg1 = new Image(getClass().getResourceAsStream("/assets/notepad-icon.png"));
        chromeImg = new Image(getClass().getResourceAsStream("/assets/chrome.png"));
        bgImg = new Image(getClass().getResourceAsStream("/assets/homepage.jpg"));
        logoutImg = new Image(getClass().getResourceAsStream("/assets/logout3-icon.png"));
        shutdownImg = new Image(getClass().getResourceAsStream("/assets/shutdown4-icon.png"));

        ivWindow = new ImageView(windowImg);
        ivNotepad = new ImageView(notepadImg);
        ivTrash = new ImageView(trashImg);
        ivNotepad1 = new ImageView(notepadImg1);
        ivChrome = new ImageView(chromeImg);
        ivBG = new ImageView(bgImg);
        ivLogout = new ImageView(logoutImg);
        ivShutdown = new ImageView(shutdownImg);

        ivBG.fitWidthProperty().bind(sc.widthProperty());
        ivBG.fitHeightProperty().bind(sc.heightProperty());
        ivBG.setPreserveRatio(false);

        Trashlbl = new Label("Trash");
        Notepadlbl = new Label("Notepad");
        Chromelbl = new Label("ChRUme");

        taskbar = new MenuBar();
        windowIcon = new Menu();
        notepadIcon = new Menu();
        logout = new MenuItem("Log Out");
        shutdown = new MenuItem("Shut Down");

        chromeStage = new Stage();

        trash = new VBox(ivTrash, Trashlbl);
        trash.setAlignment(Pos.CENTER);

        notepad = new VBox(ivNotepad1, Notepadlbl);
        notepad.setAlignment(Pos.CENTER);

        chrome = new VBox(ivChrome, Chromelbl);
        chrome.setAlignment(Pos.CENTER);

        setImageSizes();

        windowIcon.setGraphic(ivWindow);
        notepadIcon.setGraphic(ivNotepad);
        logout.setGraphic(ivLogout);
        shutdown.setGraphic(ivShutdown);

        taskbar.setMaxHeight(20);
        taskbar.getMenus().addAll(windowIcon, notepadIcon);
        windowIcon.getItems().addAll(logout, shutdown);

        addShortcut(trash);
        addShortcut(notepad);
        addShortcut(chrome);

        bp.setLeft(shortcutContainer);
        bp.setBottom(taskbar);
        sp.getChildren().addAll(ivBG, bp);
    }

    private void setImageSizes() {
        ivWindow.setFitWidth(40); ivWindow.setFitHeight(40);
        ivNotepad.setFitWidth(40); ivNotepad.setFitHeight(40);
        ivTrash.setFitWidth(64); ivTrash.setFitHeight(64);
        ivNotepad1.setFitWidth(64); ivNotepad1.setFitHeight(64);
        ivChrome.setFitWidth(64); ivChrome.setFitHeight(64);
        ivLogout.setFitWidth(20); ivLogout.setFitHeight(20);
        ivShutdown.setFitWidth(20); ivShutdown.setFitHeight(20);
    }

    public void addShortcut(VBox shortcut) {
        if (currentColumn.getChildren().size() >= MAX_SHORTCUT_PER_COLUMN) {
            currentColumn = new VBox(10);
            currentColumn.setAlignment(Pos.TOP_CENTER);
            shortcutContainer.getChildren().add(currentColumn);
        }
        currentColumn.getChildren().add(shortcut);
    }

    public static boolean exists(String filename) {
        return shortcuts.contains(filename);
    }

    public static void addShortcut(Image img, String filename) {
        if (exists(filename)) return;

        shortcuts.add(filename);

        ImageView iv = new ImageView(img);
        iv.setFitWidth(64);
        iv.setFitHeight(64);
        Label lbl = new Label(filename);

        VBox shortcut = new VBox(iv, lbl);
        shortcut.setAlignment(Pos.CENTER);

        Homepage hp = AppNavigator.getHomepageInstance();
        if (hp != null) {
            hp.addShortcut(shortcut);
        }
    }

    public void setStyle() {
        logout.getStyleClass().add("menu-item");
        shutdown.getStyleClass().add("menu-item");
    }

    public void setAction() {
        logout.setOnAction(e -> AppNavigator.goToLogin());
        shutdown.setOnAction(e -> System.exit(0));
        chrome.setOnMouseClicked(e -> openChrume());
    }

    private void openChrume() {
        ChrumeApp app = new ChrumeApp();
        app.start(new Stage());
    }

    public void start(Stage ps) {
        init();
        AppNavigator.setHomepageInstance(this);
        setStyle();
        setAction();
        ps.setScene(sc);
        ps.centerOnScreen();
        ps.show();
    }
}
