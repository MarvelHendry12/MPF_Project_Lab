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

	Scene sc; 
	StackPane sp;
	BorderPane bp;
	VBox contentBox;
	HBox searchBox;
	TextField searchBar;
	Button searchButton;
	ScrollPane scrollPane;
	MediaPlayer mediaPlayer;

    public void init() {
        sp = new StackPane();
        bp = new BorderPane();

        searchBar = new TextField();
        searchBar.setPrefWidth(400);

        searchButton = new Button("Search");

        searchBox = new HBox(searchBar, searchButton);
        searchBox.setAlignment(Pos.CENTER);

        contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);

        scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);

        bp.setTop(searchBox);
        bp.setCenter(scrollPane);

        sp.getChildren().add(bp);
        sc = new Scene(sp, 1000, 600);
        sc.getStylesheets().add("style/ChrumeApp.css");
    }

    public void setStyle() {
       scrollPane.getStyleClass().add("scroll-pane-transparent");
    }

    public void setAction() {
        searchButton.setOnAction(e -> search());
        searchBar.setOnAction(e -> search());
        
    }

    public void search() {
        String query = searchBar.getText().trim();
        contentBox.getChildren().clear();
        stopMedia(); 
        
        if(query.isEmpty()) {
        	stopMedia();
        } else if (query.equals("RUtube.net")) {
            rutube();
        } else if (query.equals("RUtify.net")) {
            rutify();
        } else if (query.equals("stockimages.net")) {
            stockImage();
        } else {
            isNotFound(query);
        }
    }

    public void isNotFound(String domain) {
    	Label errorLbl = new Label("This site can’t be reached");
        Label errorSubLbl = new Label(domain + " does not exist, try checking your spelling");
        
        errorLbl.getStyleClass().add("error-title");
        errorSubLbl.getStyleClass().add("error-subtitle");
        
        searchBox.setStyle("-fx-background-color: #f2f2f2;");
        contentBox.setStyle("-fx-background-color: #f2f2f2;");
        
        VBox vb = new VBox(errorLbl, errorSubLbl);
        
        vb.setAlignment(Pos.CENTER);
        
        StackPane centeredPane = new StackPane(vb);
        centeredPane.setPrefHeight(700); 
        
        contentBox.getChildren().add(centeredPane);
    }
    
    private void rutube() {
        stopMedia(); 

        File file = new File("src/assets/DiamondJack.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        Image imgRutube = new Image(getClass().getResourceAsStream("/assets/youtube-logo.png"));
        ImageView ivRutube = new ImageView(imgRutube);
        ivRutube.setFitHeight(40);
        ivRutube.setFitWidth(60);

        Label rutubeLbl = new Label("RUtube");
        rutubeLbl.getStyleClass().add("rutube-label");

        HBox rutubeHB = new HBox(5, ivRutube, rutubeLbl);
        rutubeHB.setAlignment(Pos.TOP_LEFT);
        rutubeHB.setPadding(new Insets(10));

        Button playBtn = new Button("Play");
        Button pauseBtn = new Button("Pause");
       
        HBox controlsHB = new HBox(playBtn, pauseBtn);
        controlsHB.setSpacing(0); 
        controlsHB.setAlignment(Pos.CENTER);
        controlsHB.getStyleClass().add("video-controls");
       
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(900);
        mediaView.setSmooth(true);
        
        VBox videoCenterBox = new VBox(mediaView, controlsHB);
        videoCenterBox.setAlignment(Pos.CENTER);
        videoCenterBox.setSpacing(0); 
        
        BorderPane videoLayout = new BorderPane();
        videoLayout.setTop(rutubeHB);
        videoLayout.setCenter(videoCenterBox);
        videoLayout.setPrefHeight(900);
        
        searchBox.setStyle("-fx-background-color: #ffffff;");
        
        sp.setStyle("-fx-background-color: #2b2b2b;");

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true); 
        
        contentBox.setStyle("-fx-background-color: #2b2b2b;");
        
        contentBox.getChildren().clear();
        
        contentBox.getChildren().add(videoLayout);
        
        playBtn.setOnAction(e -> mediaPlayer.play());
        pauseBtn.setOnAction(e -> mediaPlayer.pause());
    }


    private void rutify() {
        stopMedia(); 

        File file = new File("src/assets/PromQueen.mp3");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        Image spotifyImg = new Image(getClass().getResourceAsStream("/assets/spotify-logo.png"));
        ImageView spotifyLogo = new ImageView(spotifyImg);
        spotifyLogo.setFitHeight(45);
        spotifyLogo.setFitWidth(45);

        Label rutifyLabel = new Label("RUtify");
        rutifyLabel.getStyleClass().add("rutify-label");

        HBox spotifyHB = new HBox(10, spotifyLogo, rutifyLabel);
        spotifyHB.setAlignment(Pos.CENTER_LEFT);
//        spotifyHB.setPadding(new Insets(20, 20, 20, 20));
        spotifyHB.getStyleClass().add("rutify-bar");
        BorderPane.setAlignment(spotifyHB, Pos.TOP_LEFT);

        Slider slider = new Slider();
        slider.getStyleClass().add("custom-slider");
        slider.setDisable(true);

        mediaPlayer.setOnReady(() -> {
            slider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            slider.setValue(newTime.toSeconds());
        });

        Button playBtn = new Button("Play");
        Button pauseBtn = new Button("Pause");

        playBtn.setOnAction(e -> mediaPlayer.play());
        pauseBtn.setOnAction(e -> mediaPlayer.pause());

        HBox controls = new HBox(playBtn, pauseBtn);
        controls.setAlignment(Pos.CENTER);

        VBox audioBox = new VBox(slider, controls);
        audioBox.setAlignment(Pos.CENTER);
        audioBox.setPadding(new Insets(50));
        
        BorderPane musicLayout = new BorderPane();
        musicLayout.setTop(spotifyHB);
        musicLayout.setCenter(audioBox);
        musicLayout.setPrefHeight(800);
        musicLayout.getStyleClass().add("music-layout");
        
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true); 

        searchBox.setStyle("-fx-background-color: #ffffff;");
        sp.setStyle("-fx-background-color: #2b2b2b;");
        
        contentBox.getStyleClass().add("rutify-background");
       
        contentBox.getChildren().clear();
        contentBox.getChildren().add(musicLayout); 
    }
    
    private void stockImage() {
        contentBox.getChildren().clear();
        contentBox.setStyle("-fx-background-color: #000000;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        String[][] imageData = {
            {"cat-image1.jpg", "orangecat.jpg"},
            {"cat-image2.jpg", "graycat.jpg"},
            {"cat-image3.jpeg", "blackcat.jpg"},
            {"cat-image4.jpeg", "graycat2.jpg"}
        };

        for (String[] data : imageData) {
            String assetFile = data[0];
            String defaultName = data[1];

            // Gambar
            Image image = new Image(getClass().getResourceAsStream("/assets/" + assetFile));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(false);
            imageView.setFitHeight(500);
            imageView.setFitWidth(700);

            // Tombol Download
            Button downloadButton = new Button("Download");

            downloadButton.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog(defaultName);
                dialog.setTitle("Save Shortcut");
                dialog.setHeaderText("Enter a name for the shortcut");
                dialog.setContentText("Name:");

                dialog.showAndWait().ifPresent(name -> {
                    String filename = name.trim();

                    if (filename.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Filename cannot be empty.");
                        alert.show();
                        return;
                    }

                    // Tambahkan .jpg jika belum ada
                    if (!filename.toLowerCase().endsWith(".jpg")) {
                        filename += ".jpg";
                    }

                    // Ambil nama tanpa .jpg
                    String nameWithoutExt = filename.substring(0, filename.length() - 4);

                    // Cek alphanumeric
                    boolean isAlphanumeric = true;
                    for (char c : nameWithoutExt.toCharArray()) {
                        if (!Character.isLetterOrDigit(c)) {
                            isAlphanumeric = false;
                            break;
                        }
                    }

                    if (!isAlphanumeric) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("File name is invalid");
                        alert.setContentText("File name must be alphanumeric!");
                        alert.show();
                        return;
                    }

                    if (Homepage.exists(filename)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("File with that name already exists!");
                        alert.setContentText("A file with that name has already been made");
                        alert.show();
                        return;
                    }

                    Homepage.addShortcut(image, filename);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Success!");
                    alert.setContentText("This file has been added to homepage.");
                    
                    alert.show();
                });
            });

            HBox imageRow = new HBox(80);
            imageRow.setAlignment(Pos.CENTER);
            imageRow.setPadding(new Insets(10));
            imageRow.getChildren().addAll(imageView, downloadButton);

            contentBox.getChildren().add(imageRow);
        }
    }


    public void stopMedia() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }


    public void start(Stage ps) {
        init();
        setStyle();
        setAction();
        ps.setScene(sc);
        ps.setTitle("ChRUme");
        ps.centerOnScreen();
        ps.setOnCloseRequest(e -> stopMedia());
        ps.show();
    }
}
