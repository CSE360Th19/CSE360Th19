package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewController {
	private Stage primaryStage;

    @FXML
    private Button doctorViewButton;

    @FXML
    private Button loginViewButton;

    @FXML
    private Button nurseViewButton;

    @FXML
    private Button patientViewButton;

  
    @FXML
    void doctorView(ActionEvent event) {
    	/*
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("DoctorView.fxml"));
    		Scene scene = new Scene(root);
		
    		primaryStage.setTitle("Nurse View");
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	*/
    }

    @FXML
    void loginView(ActionEvent event) {
    	/*
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
    		Scene scene = new Scene(root);
		
    		primaryStage.setTitle("Nurse View");
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	*/
    }

    @FXML
    void nurseView(ActionEvent event) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("NurseView.fxml"));
    		Scene scene = new Scene(root);
		
    		primaryStage.setTitle("Nurse View");
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void patientView(ActionEvent event) {
    	/*
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("PatientView.fxml"));
    		Scene scene = new Scene(root);
		
    		primaryStage.setTitle("Nurse View");
    		primaryStage.setScene(scene);
    		primaryStage.show();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	*/
    }
    
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
