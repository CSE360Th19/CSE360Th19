package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class PatientController implements Initializable {

	
    private Stage primaryStage;
    private String patientId;
    
    @FXML
    private HBox appointment1;

    @FXML
    private Button appointment1button;

    @FXML
    private Label appointment1date;

    @FXML
    private Label appointment1doctor;
    
    @FXML
    private Button appointment1remove;

    @FXML
    private HBox appointment2;

    @FXML
    private Button appointment2button;

    @FXML
    private Label appointment2date;

    @FXML
    private Label appointment2doctor;
    
    @FXML
    private Button appointment2remove;

    @FXML
    private HBox appointment3;

    @FXML
    private Button appointment3button;

    @FXML
    private Label appointment3date;

    @FXML
    private Label appointment3doctor;
    
    @FXML
    private Button appointment3remove;

    @FXML
    private HBox appointment4;

    @FXML
    private Button appointment4button;

    @FXML
    private Label appointment4date;

    @FXML
    private Label appointment4doctor;
    
    @FXML
    private Button appointment4remove;

    @FXML
    private HBox appointment5;

    @FXML
    private Button appointment5button;

    @FXML
    private Label appointment5date;

    @FXML
    private Label appointment5doctor;
    
    @FXML
    private Button appointment5remove;
    
    

    @FXML
    private Button bookAppointmentButton;

    @FXML
    private Button healthRecordButton;

    @FXML
    private TableView<?> immunizationsTable;

    @FXML
    private Button insuranceButton;

    @FXML
    private Button prescriptionsButton;

    @FXML
    private TextField allergiesField;

    @FXML
    private TextField bpField;

    @FXML
    private TextArea concernsArea;

    @FXML
    private TextArea healthIssueArea;

    @FXML
    private TextField heightField;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField weightField;
    
    @FXML
    private TextArea insuranceInfoText;

    @FXML
    private Button backButton;

    @FXML
    private TableView<?> prescriptionTab;

    @FXML
    private TableView<?> bookAppointmentTab;

    @FXML
    private Button confirmButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private DatePicker dateInput;
    
    @FXML
    private Button refreshButton;
    
    

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @FXML
    void appointmentsTabView(ActionEvent event) {

    }

    @FXML
    void bookAppBtnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookAppointmentsView.fxml"));
            Parent page = loader.load();
            PatientController controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
            Scene scene = new Scene(page);
            primaryStage.setTitle("Patient View");
            primaryStage.setScene(scene);
    		primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void healthRecBtnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientHealthRecord.fxml"));
            Parent page = loader.load();
            PatientController controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
            Scene scene = new Scene(page);
            primaryStage.setTitle("Patient View");
            primaryStage.setScene(scene);
    		primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void immunizationsTabView(ActionEvent event) {

    }

    @FXML
    void prescriptionTabDisp(ActionEvent event) {
    
    }

    @FXML
    void insuranceBtnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InsuranceView.fxml"));
            Parent page = loader.load();
            PatientController controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
            Scene scene = new Scene(page);
            primaryStage.setTitle("Patient View");
            primaryStage.setScene(scene);
    		primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void prescriptionsBtnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PrescriptionsView.fxml"));
            Parent page = loader.load();
            PatientController controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
            Scene scene = new Scene(page);
            primaryStage.setTitle("Patient View");
            primaryStage.setScene(scene);
    		primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientView.fxml"));
            Parent page = loader.load();
            PatientController controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
            Scene scene = new Scene(page);
            primaryStage.setTitle("Patient View");
            primaryStage.setScene(scene);
    		primaryStage.show();
    		loadAppointments();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void confirmButtonClick(ActionEvent event) {
    	try {
    		// Get current date and time
            LocalDate currentDate = LocalDate.now();
            LocalDate selectedDate = dateInput.getValue();
            if (selectedDate != null && (selectedDate.isAfter(currentDate) || selectedDate.isEqual(currentDate))) {
            	String directoryPath = "user_info/patients/" + patientId + "/appointments/";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    boolean created = directory.mkdirs(); // This creates all necessary parent directories as well
                    if (!created) {
                        System.out.println("Failed to create directory: " + directoryPath);
                        return; // Return early if directory creation fails
                    }
                }
                
                File[] files = directory.listFiles();
                if (files != null && files.length >= 5) {
                    showErrorDialog("You already have the maximum of 5 appointments.");
                    return; // Exit the method
                }
                
            	File file = new File("user_info/patients/" + patientId + "/appointments/" + selectedDate.toString() + ".txt");
            	if(!file.exists()) {
            		FileWriter writer = new FileWriter(file, true);
            		writer.write("Date: " + selectedDate.toString() + "\n");
                    writer.write("Doctor: N/A \n");
                    writer.write("Height: \n");
                    writer.write("Weight: \n");
                    writer.write("Temperature: \n");
                    writer.write("Blood Pressure: \n");
                    writer.write("Patient Age Group: \n");
                    writer.write("Allergies: \n");
                    writer.write("Concerns: \n");
            		writer.close();
            		System.out.println("Check-in data saved successfully: " + file.getName());
            	}
            	else {
            		showErrorDialog("You already have an appointment for this date");
            	}
            }
            else {
            	showErrorDialog("The date selected is in the past");
            }

        } catch (IOException e) {
            System.out.println("Error saving check-in data: " + e.getMessage());
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
    
    @FXML
    void removeAppointment(ActionEvent event) throws IOException {
        Button buttonClicked = (Button) event.getSource();
        HBox appointmentHBox = (HBox) buttonClicked.getParent();
        Label dateLabel = (Label) appointmentHBox.lookup("#" + buttonClicked.getId().replace("remove", "date"));

        String appointmentDate = dateLabel.getText();

        String directoryPath = "user_info/patients/" + patientId + "/appointments/";
        File appointmentFile = new File(directoryPath + appointmentDate + ".txt");

        if (appointmentFile.exists()) {
            if (appointmentFile.delete()) {
                System.out.println("Appointment file deleted successfully: " + appointmentFile.getName());
                // Refresh the view after removing the appointment
                loadAppointments();
            } else {
                System.out.println("Failed to delete appointment file: " + appointmentFile.getName());
            }
        } else {
            System.out.println("Appointment file does not exist: " + appointmentFile.getName());
        }
    }
    
    
    @FXML
    private void loadAppointments() {
        String directoryPath = "user_info/patients/" + patientId + "/appointments/";
        File directory = new File(directoryPath);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
            	// Initialize all appointments to empty
                clearAppointments();
                
                for (int i = 0; i < files.length && i < 5; i++) { // Limit to the first 5 appointments
                    File file = files[i];
                    try {
                        Scanner scanner = new Scanner(file);
                        String date = null;
                        String doctor = null;
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            if (line.startsWith("Date:")) {
                                date = line.substring(6).trim();
                            } else if (line.startsWith("Doctor:")) {
                                doctor = line.substring(8).trim();
                            }
                        }
                        scanner.close();

                        // Populate the corresponding appointment HBox
                        HBox appointmentHBox = getAppointmentHBox(i + 1);
                        if (appointmentHBox != null) {
                            Label doctorLabel = (Label) appointmentHBox.lookup("#appointment" + (i + 1) + "doctor");
                            Label dateLabel = (Label) appointmentHBox.lookup("#appointment" + (i + 1) + "date");
                            Button button1 = (Button) appointmentHBox.lookup("#appointment" + (i + 1) + "button");
                            Button button2 = (Button) appointmentHBox.lookup("#appointment" + (i + 1) + "remove");

                            if (doctor != null) {
                                doctorLabel.setText(doctor);
                            }
                            if (date != null) {
                                dateLabel.setText(date); // Set date in date label
                            }
                            button1.setVisible(true);
                            button1.setDisable(false);
                            button2.setVisible(true);
                            button2.setDisable(false);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
 // Helper method to clear all appointment details
    private void clearAppointments() {
        for (int i = 1; i <= 5; i++) {
            HBox appointmentHBox = getAppointmentHBox(i);
            if (appointmentHBox != null) {
                Label doctorLabel = (Label) appointmentHBox.lookup("#appointment" + i + "doctor");
                Label dateLabel = (Label) appointmentHBox.lookup("#appointment" + i + "date");
                Button button1 = (Button) appointmentHBox.lookup("#appointment" + i + "button");
                Button button2 = (Button) appointmentHBox.lookup("#appointment" + i + "remove");

                doctorLabel.setText("");
                dateLabel.setText("");
                button1.setVisible(false);
                button1.setDisable(true);
                button2.setVisible(false);
                button2.setDisable(true);
            }
        }
    }


    // Helper method to get the corresponding appointment HBox
    private HBox getAppointmentHBox(int index) {
        switch (index) {
            case 1:
                return appointment1;
            case 2:
                return appointment2;
            case 3:
                return appointment3;
            case 4:
                return appointment4;
            case 5:
                return appointment5;
            default:
                return null;
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAppointments();
    }
}
