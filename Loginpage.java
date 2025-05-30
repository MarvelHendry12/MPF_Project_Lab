package main;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Loginpage {

    Scene sc;
    StackPane root;
    BorderPane bp;
    GridPane gp;
    Label welcome, errorMsg;
    Button loginbtn;
    PasswordField passwordPF;
    ImageView userIcon;
    ImageView background;

    public void setStyle() {
        welcome.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: white;");
        errorMsg.setStyle("-fx-text-fill: red; -fx-font-size: 14px; ");
        passwordPF.setStyle("-fx-text-fill: white; -fx-background-radius: 15; -fx-pref-width: 120px; -fx-background-color: transparent;"
        		+ " -fx-border-color: white; -fx-border-radius: 5; -fx-prompt-text-fill: white;");
        loginbtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-radius: 5px; "
        		+ "-fx-border-color: white;");
        gp.setVgap(10);
        gp.setHgap(10);

        gp.setAlignment(Pos.CENTER);
    }

//    BUAT KE HOMEPAGE!!!!!!!
//    public void loadHomePage(Stage stage) {
//        Label homeLabel = new Label("Welcome to Home Page!");
//        homeLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333;");
//
//        StackPane homeLayout = new StackPane(homeLabel);
//        Scene homeScene = new Scene(homeLayout, 600, 400);
//
//        stage.setScene(homeScene);
//    }

    public void setAction() {
    	loginbtn.setOnAction(e -> {
    	    if (passwordPF.getText().equals("admin123")) {
    	        AppNavigator.goToHome();
    	    } else {
    	        errorMsg.setText("Wrong password!");
    	    }
    	});
    }

    public void setLayout() {
        HBox iconBox = new HBox(userIcon);
        iconBox.setAlignment(Pos.CENTER);

        HBox loginBox = new HBox(5, passwordPF, loginbtn);
        loginBox.setAlignment(Pos.CENTER);
        
        HBox welcomeBox = new HBox(welcome);
        welcomeBox.setAlignment(Pos.CENTER);
        gp.add(welcomeBox, 0, 1, 2, 1);        

        gp.add(iconBox, 0, 0, 2, 1);        
        gp.add(loginBox, 0, 2, 2, 1);       
        gp.add(errorMsg, 0, 3, 2, 1);       

        bp.setCenter(gp);
        root.getChildren().addAll(background, bp);
    }

    public void init() {
        try {
            background = new ImageView(new Image(getClass().getResourceAsStream("/assets/nature.jpg")));
//            background.setFitWidth(600); tinggal disesuaikan
//            background.setFitHeight(400); tinggal disesuaikan
            background.setPreserveRatio(false);
        } catch (Exception e) {
            System.out.println("Error: Background image not found!");
            background = new ImageView();
        }
        
        root = new StackPane();
        bp = new BorderPane();
        gp = new GridPane();

        welcome = new Label("Welcome RU24-2!");
        errorMsg = new Label();

        loginbtn = new Button("Login");
        passwordPF = new PasswordField();

        try {
            userIcon = new ImageView(new Image(getClass().getResourceAsStream("/assets/default_profile_pic.png")));
            userIcon.setFitWidth(140);
            userIcon.setFitHeight(140);
        } catch (Exception e) {
            System.out.println("Error: Profile image not found!");
            userIcon = new ImageView();
        }

        sc = new Scene(root, 800, 500);
    }

    public void start(Stage ps){
        init();
        setStyle();
        setLayout();
        setAction();
        ps.setScene(sc);
        ps.show();
    }
}
