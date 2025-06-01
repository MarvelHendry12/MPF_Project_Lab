package main;

import javafx.geometry.HPos;
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
    	welcome.getStyleClass().add("welcome-label");
    	errorMsg.getStyleClass().add("error-label");
    	passwordPF.getStyleClass().add("password-field");
    	loginbtn.getStyleClass().add("login-button");
    
        gp.setVgap(10);
        gp.setHgap(10);

        gp.setAlignment(Pos.CENTER);
    }

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
        GridPane.setHalignment(errorMsg, HPos.CENTER);

        bp.setCenter(gp);
        root.getChildren().addAll(background, bp);
    }

    public void init() {
    	root = new StackPane();
    	bp = new BorderPane();
    	gp = new GridPane();

    	welcome = new Label("Welcome RU24-2!");
    	errorMsg = new Label();

    	loginbtn = new Button("Login");
    	passwordPF = new PasswordField();

    	userIcon = new ImageView(new Image(getClass().getResourceAsStream("/assets/default_profile_pic.png")));
    	userIcon.setFitWidth(250);
    	userIcon.setFitHeight(250);

    	sc = new Scene(root, 1300, 900); 
    	
    	sc.getStylesheets().add("style/Loginpage.css");
    	
    	background = new ImageView(new Image(getClass().getResourceAsStream("/assets/nature.jpg")));
    	background.setPreserveRatio(false);
    	background.fitWidthProperty().bind(sc.widthProperty());
    	background.fitHeightProperty().bind(sc.heightProperty());
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