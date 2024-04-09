package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	private Stage primaryStage;
	 public void setPrimaryStage(Stage primaryStage) {
	        this.primaryStage = primaryStage;
	  }
    @FXML
    private Button doctorViewButton;

    @FXML
    private Button loginViewButton;

    @FXML
    private Button nurseViewButton;

    @FXML
    private Button patientViewButton;
    
    @FXML
    private Button patientCreationViewButton;

  
    @FXML
    void doctorView(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorView.fxml"));
        Parent root = loader.load();
        DoctorController doctorController = loader.getController();
        doctorController.setPrimaryStage(primaryStage);

        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Doctor View");
    }

    @FXML
    void loginView(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setPrimaryStage(primaryStage);

        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Login View");
    }

    @FXML
    void nurseView(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("NurseView.fxml"));
        Parent root = loader.load();
        NurseController nurseController = loader.getController();
        nurseController.setPrimaryStage(primaryStage);

        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Nurse View");
    }

    @FXML
    void patientView(ActionEvent event) {
    	
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientView.fxml"));
            Parent root = loader.load();
    		PatientController patientController = loader.getController();
    		patientController.setPrimaryStage(primaryStage);
    		//patientController.setPatientId(); SET PATIENTS ID TO VALUE IN LOGIN IF ITS SUCCESSFUL!
    		
		
    		primaryStage.getScene().setRoot(root);
            primaryStage.setTitle("Patient View");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    @FXML
    void patientCreationView(ActionEvent event) throws IOException {
    	/*
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientCreationView.fxml"));
        Parent root = loader.load();
        NurseController nurseController = loader.getController();
        nurseController.setPrimaryStage(primaryStage);

        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Patient Creation View"); 
        */
    }
    
    
    public void setStage(Stage stage) {
    	this.primaryStage = stage;
    }
    

}
