package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
	private String employeeId;
	public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
	public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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
        if (checkFieldsNotEmpty(idInput, nameInput, heightInput, weightInput, tempInput, bpInput)) {
            // All fields are filled
            specificsSubmit.setDisable(false);
            specificsClear.setDisable(false);

            String patientId = idInput.getText();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);
            System.out.println(formattedDate);
            String filePath = "user_info/patients/" + patientId + "/appointments/" + formattedDate + ".txt";

            if (appointmentFileExists(filePath)) {
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
                showErrorDialog("Patient does not have an appointment for today.");
                System.out.println("Patient does not have an appointment for today.");
            }
        } else {
            showErrorDialog("Not all check-in fields are filled.");
            System.out.println("Not all check-in fields are filled.");
        }
    }
    private boolean appointmentFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    @FXML
    void submitSpecifics(ActionEvent event) {
        if (checkFieldsNotEmpty(allergiesInput, concernsInput)) {
            // All fields are filled
            ArrayList<String> data = new ArrayList<>();
            data.add(allergiesInput.getText());
            data.add(concernsInput.getText());

            System.out.println("Specifics data: " + data);
            String patientId = idInput.getText();
            createCheckinDataToFile(patientId);
            
        } else {
        	showErrorDialog("Not all specifics fields are filled.");
            System.out.println("Not all specifics fields are filled.");
        }
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setPrimaryStage(primaryStage);

        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Login View");
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
            // Get current date
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = currentDate.format(formatter);

            File file = new File("user_info/patients/" + patientId + "/appointments/" + date + ".txt");
            

            if (file.exists()) {
                // Append the existing content, excluding "Date" and "Doctor" lines
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                String doctorLine = "Doctor: N/A" + "\n";
                while ((line = reader.readLine()) != null) {
                	System.out.println(line);
                    if (line.startsWith("Doctor:")) {
                    	doctorLine = line;
                    }
                }
                FileWriter writer = new FileWriter(file); // Overwrite mode
                reader.close();
                System.out.println(doctorLine);
                writer.write("Date: " + date + "\n");
                writer.write(doctorLine + "\n");
                writer.write("Height: " + heightInput.getText() + "\n");
                writer.write("Weight: " + weightInput.getText() + "\n");
                writer.write("Temperature: " + tempInput.getText() + "\n");
                writer.write("Blood Pressure: " + bpInput.getText() + "\n");
                writer.write("Patient Age Group: " + (patientOverTwelveCheckbox.isSelected() ? "Over 12" : "Under 12") + "\n");
                writer.write("Allergies: " + allergiesInput.getText() + "\n");
                writer.write("Concerns: " + concernsInput.getText() + "\n");
                writer.close();
            } else {
                showErrorDialog("No appointment scheduled for today");
            }

            System.out.println("Check-in data saved successfully: " + file.getName());
        } catch (IOException e) {
            System.out.println("Error saving check-in data: " + e.getMessage());
        }
    }




}
