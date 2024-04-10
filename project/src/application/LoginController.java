package application;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {


	//controller for login view
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
	
		    @FXML
		    private AnchorPane anchorPane;

		    @FXML
		    private Rectangle blueFrame;

		    @FXML
		    private TextField idText;
		    
		    @FXML
		    private Button loginButton;
		    
		    @FXML
		    private Button createAccountButton;

		    @FXML
		    private Label loginLabel;

		    @FXML
		    private PasswordField passwordText;

		    @FXML
		    void login(ActionEvent event) throws IOException {
		    	String id = idText.getText();
		        String password = passwordText.getText();

		        if (id.isEmpty() || password.isEmpty()) {
		            showErrorDialog("Please enter your ID and password.");
		            return;
		        }
		        
		        if(id.equals("system") && password.equals("system")) {
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeCreationView.fxml"));
		            Parent root = loader.load();
		            EmployeeCreationController employeeCreationController = loader.getController();
		            employeeCreationController.setPrimaryStage(primaryStage);

		            primaryStage.getScene().setRoot(root);
		            primaryStage.setTitle("Employee Creation View"); 
		            return;
		        }
		        
		        File doctorsDir = new File("user_info/doctors");
		        File nursesDir = new File("user_info/nurses");
		        File patientsDir = new File("user_info/patients");
		        
		        if(searchDirectory(doctorsDir, id, password)) {
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorView.fxml"));
		        	loader.setControllerFactory(controllerClass -> {
		        	    DoctorController doctorController = new DoctorController();
		        	    doctorController.setPrimaryStage(primaryStage);
		        	    doctorController.setEmployeeId(id);
		        	    return doctorController;
		        	});

		        	Parent root = loader.load();

		        	primaryStage.getScene().setRoot(root);
		        	primaryStage.setTitle("Doctor View");
		        }
		        else if (searchDirectory(nursesDir, id, password)) {
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("NurseView.fxml"));
		            Parent root = loader.load();
		            NurseController nurseController = loader.getController();
		            nurseController.setPrimaryStage(primaryStage);
		            nurseController.setEmployeeId(id);

		            primaryStage.getScene().setRoot(root);
		            primaryStage.setTitle("Nurse View");
		        }
		        else if (searchDirectory(patientsDir, id, password)) {
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientView.fxml"));

		        	loader.setControllerFactory(controllerClass -> {
		        	    PatientController patientController = new PatientController();
		        	    patientController.setPrimaryStage(primaryStage);
		        	    patientController.setPatientId(id);
		        	    return patientController;
		        	});

		        	Parent root = loader.load();

		        	primaryStage.getScene().setRoot(root);
		        	primaryStage.setTitle("Patient View");
		        }
		        else {
		        	showErrorDialog("Invalid ID / Password");
		        }
		    }
		    
		    @FXML
		    void createAccount(ActionEvent event) throws IOException {
		    	FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientCreationView.fxml"));
		        Parent root = loader.load();
		        PatientCreationController patientCreationController = loader.getController();
		        patientCreationController.setPrimaryStage(primaryStage);

		        primaryStage.getScene().setRoot(root);
		        primaryStage.setTitle("Patient Creation View"); 
		    }
		    
		    private void showErrorDialog(String message) {
		        Stage dialogStage = new Stage();
		        dialogStage.setTitle("Error");
		        VBox vbox = new VBox(new Text(message));
		        vbox.setAlignment(Pos.CENTER);
		        vbox.setPadding(new Insets(20));
		        Scene dialogScene = new Scene(vbox, 300, 100);
		        dialogStage.setScene(dialogScene);
		        dialogStage.show();
		    }
		    
		    private boolean searchDirectory(File directory, String id, String password) {
		        if (directory.exists() && directory.isDirectory()) {
		            File[] files = directory.listFiles();
		            if (files != null) {
		                for (File file : files) {
		                    if (file.isDirectory() && file.getName().equals(id)) {
		                    	if(checkPassword(file, password)) {
		                    		return true;
		                    	}
		                    }
		                }
		            }
		        }
		        return false;
		    }
		    
		    private boolean checkPassword(File patientDir, String password) {
		        File generalInfoFile = new File(patientDir, "general_info.txt");
		        if (generalInfoFile.exists() && generalInfoFile.isFile()) {
		            try (Scanner scanner = new Scanner(generalInfoFile)) {
		                while (scanner.hasNextLine()) {
		                    String line = scanner.nextLine();
		                    if (line.startsWith("Password:")) {
		                        // Extract the password from the line
		                        String storedPassword = line.substring("Password:".length()).trim();
		                        // Check if the stored password matches the entered password
		                        return storedPassword.equals(password);
		                    }
		                }
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        return false;
		    }
	}

