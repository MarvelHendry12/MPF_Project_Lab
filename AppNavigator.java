package main;

import javafx.stage.Stage;

public class AppNavigator {
	public static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void goToLogin() {
        Loginpage login = new Loginpage();
        login.start(mainStage);
    }

    public static void goToHome() {
        Homepage home = new Homepage();
        home.start(mainStage);
    }
}
