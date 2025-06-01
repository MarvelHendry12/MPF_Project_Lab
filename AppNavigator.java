package main;

import javafx.stage.Stage;

public class AppNavigator {
	public static Stage mainStage;
	
	private static Homepage homepage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }
    
    public static void setHomepageInstance(Homepage hp) {
        homepage = hp;
    }

    public static Homepage getHomepageInstance() {
        return homepage;
    }

    public static void goToLogin() {
        Loginpage login = new Loginpage();
        login.start(mainStage);
    }

    public static void goToHome() {
        Homepage home = new Homepage();
        home.start(mainStage);
    }
    
    public static void goToChrume() {
    	ChrumeApp chrume = new ChrumeApp();
    	chrume.start(mainStage);
    }
}
