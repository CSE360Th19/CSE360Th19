package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

// Main is our system?
// if it is it should create the users (nurse, doctor, patient) and store in database
// Main should be the controller, responsible for showing views
// it should start by displaying login view

public class Main extends Application {
	private static final String FOLDER_NAME = "user_info";
	@Override
	public void start(Stage primaryStage) {
		try {
			// Start by showing Login View
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
			Parent root = loader.load();
			
			LoginController loginController = loader.getController();
			loginController.setPrimaryStage(primaryStage);
			
			// Create database folder if it doesn't exist
	        File folder = new File(FOLDER_NAME);
	        if (!folder.exists()) {
	            folder.mkdirs(); 
	        }
			
			Scene scene = new Scene(root);
			primaryStage.setTitle("Login View");
			primaryStage.setScene(scene);
			primaryStage.show();
	    } catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
