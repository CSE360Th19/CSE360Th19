package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PatientCreationController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField insuranceNumberField;
    
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;
    
    @FXML 
    private VBox container;

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
        String insuranceNumber = insuranceNumberField.getText();

        if (fullName.isEmpty() || insuranceNumber.isEmpty() || password.isEmpty()) {
            showErrorDialog("Please enter the full name, password, and insurance number.");
            return;
        }

        String patientId = generateUniquePatientId();
        createPatientInfoFile(patientId, fullName, password, insuranceNumber);
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Your ID");
        VBox vbox = new VBox(new Text("Your ID Number is: " + patientId + "\n" + "You must remember this, write it down."));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        Scene dialogScene = new Scene(vbox, 300, 100);
        dialogStage.setScene(dialogScene);
        dialogStage.show();

        // Optionally, you can clear the fields or perform other actions after
        // submission
        clearFields();

        // You can also switch back to another view, for example:
        // switchToNavView();
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

    private void createPatientInfoFile(String patientId, String name, String password, String insuranceNumber) {
        try {
            // Create patient directory if it doesn't already exist
            createPatientDirectory(patientId);

            // Create a file for general patient information
            File file = new File("user_info/patients/" + patientId + "/general_info.txt");
            FileWriter writer = new FileWriter(file);
            writer.write("Full Name: " + name + "\n");
            writer.write("Password: " + password + "\n");
            writer.write("Insurance Number: " + insuranceNumber + "\n");
            writer.close();

            System.out.println("General info file created successfully: " + file.getName());
        } catch (IOException e) {
            System.out.println("Error creating general info file: " + e.getMessage());
        }
    }

    private void createPatientDirectory(String patientId) {
        try {
            // Create a directory for the patient
            File directory = new File("user_info/patients/" + patientId);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Patient directory created successfully: " + directory.getName());
            }
        } catch (Exception e) {
            System.out.println("Error creating patient directory: " + e.getMessage());
        }
    }

    private String generateUniquePatientId() {
        // Generate a random 5-digit number for patient ID
        Random random = new Random();
        String patientId;
        do {
            patientId = String.format("%05d", random.nextInt(100000));
        } while (!isUniquePatientId(patientId));
        return patientId;
    }

    private boolean isUniquePatientId(String patientId) {
        // Check if the patient ID is unique by scanning through existing patient, doctor, and nurse directories
        File[] directories = {
            new File("user_info/patients"),
            new File("user_info/doctors"),
            new File("user_info/nurses")
        };

        for (File directory : directories) {
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory() && file.getName().equals(patientId)) {
                            return false;
                        }
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
        insuranceNumberField.clear();
    }
}
