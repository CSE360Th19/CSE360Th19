package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PatientCreationController {
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
	// createPatientInfoFile will be the main method, called when a submit button is pressed
	// It should then create a file with all the necessary information
	// Information is up to you, but I would at least add the following:
	// 		Full name, insurance number, prior immunizations
	// Add back button, which switches back to the NavView, see NurseView method logout() for how to do that
	private void createPatientInfoFile(String patientId, String name) {
        try {
        	// You should use showErrorDialog(String message) method if submit is pressed when fields are still empty!
        	
        	// Generate a unique patient id with generateUniquePatientId()
        	
        	// Create patient directory if it doesn't already exist, use unique generate ID 
        	createPatientDirectory(patientId);
        	
            // Create a file for general patient information
            File file = new File("user_info/patients/" + patientId + "/general_info.txt");
            FileWriter writer = new FileWriter(file);

            // Get information from all inputs in PatientCreationView.fxml
            // and write it to the file
            //writer.write(your text here \n);

            // Close the writer
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
    
}
