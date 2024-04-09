package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EmployeeCreationController {

    @FXML
    private Button backButton;

    @FXML
    private VBox container;

    @FXML
    private TextField fullNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;
    
    @FXML
    private CheckBox doctorCheckbox;
    
    @FXML
    private CheckBox nurseCheckbox;

    private Stage primaryStage;
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        // Initialize any necessary components or bindings
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent e) {
        String fullName = fullNameField.getText();
        String password = passwordField.getText();

        if (fullName.isEmpty() || password.isEmpty()) {
            showErrorDialog("Please enter the full name and password.");
            return;
        }
        if(doctorCheckbox.isSelected() && nurseCheckbox.isSelected()) {
        	showErrorDialog("Only check one box");
        	return;
        }
        if(!(doctorCheckbox.isSelected() || nurseCheckbox.isSelected())) {
        	showErrorDialog("Please check one box");
        	return;
        }
        
        String employeeId = generateUniqueEmployeeId();
        if(doctorCheckbox.isSelected()) {
        	createEmployeeInfoFile(employeeId, fullName, password, "Doctor");
        }
        if(nurseCheckbox.isSelected()) {
        	createEmployeeInfoFile(employeeId, fullName, password, "Nurse");
        }


        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Your ID");
        VBox vbox = new VBox(new Text("Your ID Number is: " + employeeId + "\n" + "You must remember this, write it down."));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        Scene dialogScene = new Scene(vbox, 300, 100);
        dialogStage.setScene(dialogScene);
        dialogStage.show();

        // Optionally, you can clear the fields or perform other actions after
        // submission
        clearFields();
    }

    @FXML
    private void handleBackButtonAction(ActionEvent e) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setPrimaryStage(primaryStage);

        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Login View");
    }

    private void createEmployeeInfoFile(String employeeId, String name, String password, String role) {
        try {
            // Create patient directory if it doesn't already exist
        	if(role.equals("Doctor")) {
        		createDoctorDirectory(employeeId);
        	}
        	else {
        		createNurseDirectory(employeeId);
        	}
            

            // Create a file for general patient information
            File file = new File("user_info/" + role + "s/" + employeeId + "/general_info.txt");
            FileWriter writer = new FileWriter(file);
            writer.write("Full Name: " + name + "\n");
            writer.write("Password: " + password + "\n");
            writer.write("Role: " + role + "\n");
            writer.close();

            System.out.println("General info file created successfully: " + file.getName());
        } catch (IOException e) {
            System.out.println("Error creating general info file: " + e.getMessage());
        }
    }

    private void createDoctorDirectory(String employeeId) {
        try {
            // Create a directory for the patient
            File directory = new File("user_info/doctors/" + employeeId);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Doctor directory created successfully: " + directory.getName());
            }
        } catch (Exception e) {
            System.out.println("Error creating doctor directory: " + e.getMessage());
        }
    }
    private void createNurseDirectory(String employeeId) {
        try {
            // Create a directory for the patient
            File directory = new File("user_info/nurses/" + employeeId);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Nurse directory created successfully: " + directory.getName());
            }
        } catch (Exception e) {
            System.out.println("Error creating nurse directory: " + e.getMessage());
        }
    }

    private String generateUniqueEmployeeId() {
        // Generate a random 5-digit number for patient ID
        Random random = new Random();
        String patientId;
        do {
            patientId = String.format("%05d", random.nextInt(100000));
        } while (!isUniqueEmployeeId(patientId));
        return patientId;
    }

    private boolean isUniqueEmployeeId(String patientId) {
        // Check if the patient ID is unique by scanning through existing patient
        // directories
        File patientsDirectory = new File("user_info/patients");
        if (patientsDirectory.exists() && patientsDirectory.isDirectory()) {
            File[] patientDirectories = patientsDirectory.listFiles();
            if (patientDirectories != null) {
                for (File directory : patientDirectories) {
                    if (directory.isDirectory() && directory.getName().equals(patientId)) {
                        // Found a directory with the same name as the generated patient ID
                        return false;
                    }
                }
            }
        }
        // If no directory matches the generated patient ID, it's unique
        return true;
    }

    private void showErrorDialog(String message) {
        // Implementation of showErrorDialog as in the provided code
        // I'll leave this part to remain as it is since it's already implemented
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Error");
        VBox vbox = new VBox(new Text(message));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        Scene dialogScene = new Scene(vbox, 300, 100);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void clearFields() {
        fullNameField.clear();
        passwordField.clear();
    }
}
