package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class DoctorController {
	private Stage primaryStage;
	private String employeeId;
	public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
	public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

	
    @FXML
    private TableView<Appointment> appointmentsTable;

    @FXML
    private Button chatButton;
    
    @FXML
    private Button viewDetailsButton;
    
    @FXML
    private Button prescribeButton;
    
    @FXML
    private TableColumn<Appointment, String> patientNameColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentDateColumn;
    @FXML
    private TableColumn<Appointment, String> detailsColumn;

    @FXML
    private TextArea patientDetailsArea;

    @FXML
    private TextField searchField;
    
    @FXML
    private TextField idText;
    
    @FXML
    private TextField usageText;
    
    @FXML
    private TextField refillText;
    
    @FXML
    private TextField medicationText;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
    	loadAppointments();
    }

    @FXML
    private void handleViewPatientDetails() throws IOException {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        
        if (selected != null) {
        	String patientName = selected.getPatientName();
            String patientId = patientName.substring(patientName.lastIndexOf("(") + 1, patientName.lastIndexOf(")")).trim();
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHealthRecord.fxml"));
            loader.setControllerFactory(controllerClass -> {
        	    PatientController patientController = new PatientController();
        	    patientController.setPrimaryStage(primaryStage);
        	    patientController.setPatientId(patientId);
        	    patientController.setAppointmentDate(selected.getAppointmentDate());
        	    patientController.setDoctorView(true);
        	    patientController.setDoctorId(employeeId);
        	    return patientController;
        	});

        	Parent root = loader.load();

        	primaryStage.getScene().setRoot(root);
        	primaryStage.setTitle("Health Record View");
        } else {
            showAlert("No Selection", "No appointment selected.", "Please select an appointment to view details.");
        }
    }

    @FXML
    private void handlePrescribeMedication() {
        if(checkFieldsNotEmpty(idText, usageText, refillText, medicationText)) {
        	String patientId = idText.getText();
            String medication = medicationText.getText();
            String usage = usageText.getText();
            String refillInfo = refillText.getText();

            String prescription = "Medication: " + medication + "\nUsage: " + usage + "\nRefill info: " + refillInfo + "\n~~~~~\n";

            File prescriptionsFile = new File("user_info/patients/" + patientId + "/prescriptions.txt");

            try {
                // Create the prescriptions file if it doesn't exist
                if (!prescriptionsFile.exists()) {
                    prescriptionsFile.createNewFile();
                }

                // Append the prescription to the file
                FileWriter fileWriter = new FileWriter(prescriptionsFile, true);
                fileWriter.write(prescription);
                fileWriter.close();

                showAlert("Prescription Added", "Prescription Successfully Added", "Prescription has been added for patient ID: " + patientId);
            } catch (IOException e) {
                showAlert("Error", "Error Writing Prescription", "An error occurred while writing the prescription.");
                e.printStackTrace();
            }
        }
        else {
        	showAlert("Error", "Not all fields filled", "Please fill out all fields");
        }
    }

    // Adding a logout action
    @FXML
    void logout(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setPrimaryStage(primaryStage);

        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle("Login View");
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        if (!searchText.isEmpty()) {
            ObservableList<Appointment> filteredList = appointmentList.filtered(appointment ->
                appointment.getPatientName().toLowerCase().contains(searchText) ||
                appointment.getDetails().toLowerCase().contains(searchText));
            appointmentsTable.setItems(filteredList);
        } else {
            appointmentsTable.setItems(appointmentList); // Reset to all appointments if search is cleared
        }
    }
    
    @FXML
    public void openChat() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageView.fxml"));

    	loader.setControllerFactory(controllerClass -> {
    	    MessageController messageController = new MessageController();
    	    messageController.setPrimaryStage(primaryStage);
    	    messageController.setEmployeeId(employeeId);
    	    messageController.setRole("doctor");
    	    return messageController;
    	});

    	Parent root = loader.load();

    	primaryStage.getScene().setRoot(root);
    	primaryStage.setTitle("Message View");
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Placeholder Appointment class for demonstration
    public static class Appointment {
        private final SimpleStringProperty patientName;
        private final SimpleStringProperty appointmentDate;
        private final SimpleStringProperty details;

        public Appointment(String patientName, String appointmentDate, String details) {
            this.patientName = new SimpleStringProperty(patientName);
            this.appointmentDate = new SimpleStringProperty(appointmentDate);
            this.details = new SimpleStringProperty(details);
        }

        public String getPatientName() { return patientName.get(); }
        public String getAppointmentDate() { return appointmentDate.get(); }
        public String getDetails() { return details.get(); }
    }
    
    private void loadAppointments() {
    	patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));
        String baseDirectoryPath = "user_info/patients/";

        File baseDirectory = new File(baseDirectoryPath);
        if (baseDirectory.exists() && baseDirectory.isDirectory()) {
            // Iterate through patient folders
            for (File patientDirectory : baseDirectory.listFiles()) {
                if (patientDirectory.isDirectory()) {
                	String patientId = patientDirectory.getName();
                    // Inside each patient folder, look for appointments directory
                    File appointmentsDirectory = new File(patientDirectory.getPath() + "/appointments/");
                    if (appointmentsDirectory.exists() && appointmentsDirectory.isDirectory()) {
                        // Iterate through appointment files
                        for (File appointmentFile : appointmentsDirectory.listFiles()) {
                            try {
                                // Read content of each appointment file
                                Scanner scanner = new Scanner(appointmentFile);
                                StringBuilder contentBuilder = new StringBuilder();
                                while (scanner.hasNextLine()) {
                                    contentBuilder.append(scanner.nextLine()).append("\n");
                                }
                                String content = contentBuilder.toString();

                                // Extract appointment date from filename
                                String appointmentDate = appointmentFile.getName().replace(".txt", "");

                                // Extract appointment details from file content
                                String[] lines = content.split("\n");
                                String concerns = "";
                                for (String line : lines) {
                                    if (line.startsWith("Concerns:")) {
                                        concerns = line.substring("Concerns:".length()).trim();
                                        break;
                                    }
                                }

                                // Create Appointment object
                                Appointment appointment = new Appointment(getName(patientId, "patient") + " (" + patientId + ")", appointmentDate, concerns);

                                // Add appointment to the list
                                appointmentList.add(appointment);

                                scanner.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        appointmentsTable.setItems(appointmentList);
    }
    
    public String getName(String id, String role) {
    	File generalInfoFile = new File("user_info/" + role + "s/" + id + "/general_info.txt");
        if (generalInfoFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(generalInfoFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        System.out.println(value);
                        System.out.println(key);
                        switch (key) {
                            case "Full Name":
                                return value;
						default:
                                // Unknown key, do nothing
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
        	return "null";
        }
        return "null";
    }
    
    private boolean checkFieldsNotEmpty(TextInputControl... fields) {
        for (TextInputControl field : fields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
