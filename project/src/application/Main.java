package application;
	
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
	@Override
	public void start(Stage primaryStage) {
		try {
			// Start by showing Nav view while testing
			FXMLLoader loader = new FXMLLoader(getClass().getResource("navView.fxml"));
			Parent root = loader.load();
			
			ViewController viewController = loader.getController();
			viewController.setStage(primaryStage);
			
			Scene scene = new Scene(root);
			primaryStage.setTitle("Nav View");
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
