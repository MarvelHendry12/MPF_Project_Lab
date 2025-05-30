package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class ChrumeApp {
    Scene scene;
    BorderPane root;
    TextField searchBar;
    Button searchButton;
    VBox contentBox;
    MediaPlayer currentMediaPlayer;

    public void start(Stage ps) {
        root = new BorderPane();
        root.setPadding(new Insets(20));

        // Load background image
//        Image bgImg = new Image(getClass().getResourceAsStream("/assets/homepage.jpg"));
//        ImageView ivBG = new ImageView(bgImg);
//        ivBG.setPreserveRatio(false);
//        ivBG.setFitWidth(1300);
//        ivBG.setFitHeight(900);

        // Top search bar
        HBox searchBox = new HBox(10);
        searchBar = new TextField();
        searchButton = new Button("Search");
        searchBox.getChildren().addAll(searchBar, searchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBar.setPrefWidth(400);

        // Content area
        contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        root.setTop(searchBox);
        root.setCenter(scrollPane);

        // StackPane untuk background + aplikasi
        StackPane sp = new StackPane();
        scene = new Scene(sp, 1000, 600);
//        ivBG.fitWidthProperty().bind(scene.widthProperty());
//        ivBG.fitHeightProperty().bind(scene.heightProperty());

        sp.getChildren().addAll(root);

        setSearchAction();

        ps.setTitle("ChRUme");
        ps.setScene(scene);
        ps.show();
    }


    private void setSearchAction() {
        searchButton.setOnAction(e -> handleSearch());
        searchBar.setOnAction(e -> handleSearch());
    }

    private void handleSearch() {
        String query = searchBar.getText().trim();
        contentBox.getChildren().clear();
        stopCurrentMedia();

        if (query.isEmpty()) {
            showEmptyWebsite();
        } else if (query.equals("RUtube.net")) {
            showRUtube();
        } else if (query.equals("RUtify.net")) {
            showRUtify();
        } else if (query.equals("stockimages.net")) {
            showStockImages();
        } else {
            showDomainNotFound(query);
        }
    }

    private void showEmptyWebsite() {
        contentBox.getChildren().add(new Label("Empty Website"));
    }

    private void showDomainNotFound(String domain) {
        Label title = new Label("This site can’t be reached");
        Label subtitle = new Label("The domain \"" + domain + "\" does not exist.");
        VBox box = new VBox(10, title, subtitle);
        box.setAlignment(Pos.CENTER);
        contentBox.getChildren().add(box);
    }

    private void showRUtube() {
        File file = new File("assets/DiamondJack.mp4");
        Media media = new Media(file.toURI().toString());
        currentMediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(currentMediaPlayer);

        Button playBtn = new Button("Play");
        Button pauseBtn = new Button("Pause");
        playBtn.setOnAction(e -> currentMediaPlayer.play());
        pauseBtn.setOnAction(e -> currentMediaPlayer.pause());

        VBox mediaBox = new VBox(10, new Label("RUtube.net"), mediaView, new HBox(10, playBtn, pauseBtn));
        mediaBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().add(mediaBox);
    }

    private void showRUtify() {
        File file = new File("assets/PromQueen.mp3");
        Media media = new Media(file.toURI().toString());
        currentMediaPlayer = new MediaPlayer(media);

        Slider slider = new Slider();
        slider.setDisable(true);

        currentMediaPlayer.setOnReady(() -> slider.setMax(currentMediaPlayer.getMedia().getDuration().toSeconds()));
        currentMediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!slider.isDisabled()) {
                slider.setValue(newTime.toSeconds());
            }
        });

        Button playBtn = new Button("Play");
        Button pauseBtn = new Button("Pause");
        playBtn.setOnAction(e -> currentMediaPlayer.play());
        pauseBtn.setOnAction(e -> currentMediaPlayer.pause());

        VBox audioBox = new VBox(10, new Label("RUtify.net"), slider, new HBox(10, playBtn, pauseBtn));
        audioBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().add(audioBox);
    }

    private void showStockImages() {
        VBox imagesBox = new VBox(20);
        imagesBox.setAlignment(Pos.CENTER);

        String[] imageFiles = {"cat-image1.jpg", "cat-image2.jpg", "cat-image3.jpeg", "cat-image4.jpeg"};
        String[] names = {"orangecat", "graycat", "blackcat", "graycat2"};

        for (int i = 0; i < imageFiles.length; i++) {
            String fileName = imageFiles[i];
            String defaultName = names[i] + ".jpg";

            ImageView imageView = new ImageView("file:assets/" + fileName);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            Button downloadBtn = new Button("Download");
            int finalI = i;
            downloadBtn.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog(defaultName);
                dialog.setTitle("Download Image");
                dialog.setHeaderText("Enter filename to save:");
                dialog.showAndWait().ifPresent(name -> {
                    if (!name.matches("[a-zA-Z0-9]+\\.jpg")) {
                        showAlert("Filename must be alphanumeric and end with .jpg");
                        return;
                    }
                    File target = new File("downloads/" + name);
                    if (target.exists()) {
                        showAlert("Filename already exists!");
                        return;
                    }
                    // Simulasi penyimpanan dan shortcut
                    System.out.println("Downloaded as: " + name);
                });
            });

            VBox imgBox = new VBox(10, imageView, downloadBtn);
            imgBox.setAlignment(Pos.CENTER);
            imagesBox.getChildren().add(imgBox);
        }

        contentBox.getChildren().add(imagesBox);
    }

    private void stopCurrentMedia() {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.stop();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(msg);
    }
}
