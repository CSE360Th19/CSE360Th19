package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NurseController {
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private TextField allergiesInput;

    @FXML
    private TextField bpInput;

    @FXML
    private Button checkinClearButton;

    @FXML
    private Button checkinSubmitButton;

    @FXML
    private TextField concernsInput;

    @FXML
    private Label healthIssuesText;

    @FXML
    private TextField heightInput;

    @FXML
    private Label immunizationText;

    @FXML
    private Label medicationsText;

    @FXML
    private TextField nameInput;

    @FXML
    private CheckBox patientOverTwelveCheckbox;

    @FXML
    private Button specificsClear;

    @FXML
    private Button specificsSubmit;

    @FXML
    private TextField tempInput;

    @FXML
    private TextField weightInput;
    
    @FXML
    private Label patientIdLabel;
    
    @FXML
    private TextField idInput;

    @FXML
    private CheckBox newPatientCheckbox;

    @FXML
    void clearCheckin(ActionEvent event) {
        clearFields(nameInput, heightInput, weightInput, tempInput, bpInput, idInput);
        clearSpecifics(event);
        specificsSubmit.setDisable(true);
        specificsClear.setDisable(true);
    }

    @FXML
    void clearSpecifics(ActionEvent event) {
        clearFields(allergiesInput, concernsInput);
    }

    @FXML
    void submitCheckin(ActionEvent event) {
        if (checkFieldsNotEmpty(nameInput, heightInput, weightInput, tempInput, bpInput) || checkFieldsNotEmpty(idInput, heightInput, weightInput, tempInput, bpInput)) {
            // All fields are filled
        	specificsSubmit.setDisable(false);
            specificsClear.setDisable(false);
            
            ArrayList<String> data = new ArrayList<>();
            data.add(nameInput.getText());
            data.add(heightInput.getText());
            data.add(weightInput.getText());
            data.add(tempInput.getText());
            data.add(bpInput.getText());
            String patientAgeGroup = patientOverTwelveCheckbox.isSelected() ? "Over 12" : "Under 12";
            data.add(patientAgeGroup);

            System.out.println("Check-in data: " + data);
        } else {
        	showErrorDialog("Not all check-in fields are filled.");
            System.out.println("Not all check-in fields are filled.");
        }
    }

    @FXML
    void submitSpecifics(ActionEvent event) {
        if (checkFieldsNotEmpty(allergiesInput, concernsInput)) {
            // All fields are filled
            ArrayList<String> data = new ArrayList<>();
            data.add(allergiesInput.getText());
            data.add(concernsInput.getText());

            System.out.println("Specifics data: " + data);
            if (newPatientCheckbox.isSelected()) {
                // Generate a unique patient ID
                String patientId = generateUniquePatientId();
                idInput.setText(patientId);
                
                // Show the patient ID label
                patientIdLabel.setVisible(true);
                patientIdLabel.setText("Patient ID Created: " + patientId);
                
                createPatientDirectory(patientId);
                createPatientInfoFile(patientId, nameInput.getText());
                createCheckinDataToFile(patientId);
            }
            else {
            	createCheckinDataToFile(idInput.getText());
            }
            
        } else {
        	showErrorDialog("Not all specifics fields are filled.");
            System.out.println("Not all specifics fields are filled.");
        }
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("NavView.fxml"));
        Parent root = loader.load();
        MainController navController = loader.getController();
        navController.setPrimaryStage(primaryStage);

        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Navigation View");
    }

    // Helper method to check if all provided text fields are not empty
    private boolean checkFieldsNotEmpty(TextInputControl... fields) {
        for (TextInputControl field : fields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    // Helper method to clear all provided input fields
    private void clearFields(TextInputControl... fields) {
        for (TextInputControl field : fields) {
            field.clear();
        }
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
    
    private void createCheckinDataToFile(String patientId) {
        try {
            // Create or append to the check-in data file
            File file = new File("user_info/patients/" + patientId + "/checkin_data.txt");
            FileWriter writer = new FileWriter(file, true);

            // Get current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            // Write check-in data to file
            writer.write("Date: " + formattedDateTime + "\n");
            writer.write("Height: " + heightInput.getText() + "\n");
            writer.write("Weight: " + weightInput.getText() + "\n");
            writer.write("Temperature: " + tempInput.getText() + "\n");
            writer.write("Blood Pressure: " + bpInput.getText() + "\n");
            writer.write("Patient Age Group: " + (patientOverTwelveCheckbox.isSelected() ? "Over 12" : "Under 12") + "\n");
            writer.write("Allergies: " + allergiesInput.getText() + "\n");
            writer.write("Concerns: " + concernsInput.getText() + "\n");
            writer.write("----------------------------------------------------\n");

            // Close the writer
            writer.close();

            System.out.println("Check-in data saved successfully: " + file.getName());
        } catch (IOException e) {
            System.out.println("Error saving check-in data: " + e.getMessage());
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
        // Check if the patient ID is unique by scanning through existing patient directories
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
    
    private void createPatientInfoFile(String patientId, String name) {
        try {
            // Create a file for general patient information
            File file = new File("user_info/patients/" + patientId + "/general_info.txt");
            FileWriter writer = new FileWriter(file);

            // Write patient's name and ID to the file
            writer.write("Name: " + name + "\n");
            writer.write("ID: " + patientId + "\n");

            // Close the writer
            writer.close();

            System.out.println("General info file created successfully: " + file.getName());
        } catch (IOException e) {
            System.out.println("Error creating general info file: " + e.getMessage());
        }
    }
    

}
