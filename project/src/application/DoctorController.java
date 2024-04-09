import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class DoctorController {
    @FXML
    private TableView<Appointment> appointmentsTable;

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

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));

        // Example data loading
        appointmentList.addAll(
            new Appointment("John Doe", "2024-04-05", "Annual Checkup"),
            new Appointment("Jane Smith", "2024-04-06", "Consultation")
        );
        appointmentsTable.setItems(appointmentList);
    }

    @FXML
    private void handleViewPatientDetails() {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // In a real application, you would fetch these details from a database
            patientDetailsArea.setText("Details for " + selected.getPatientName() + ": \n- Date: " + selected.getAppointmentDate() + "\n- Reason: " + selected.getDetails());
        } else {
            showAlert("No Selection", "No appointment selected.", "Please select an appointment to view details.");
        }
    }

    @FXML
    private void handleUpdateAppointment() {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Placeholder for update logic
            showAlert("Update Appointment", "Appointment Updated", "The appointment for " + selected.getPatientName() + " has been updated.");
        } else {
            showAlert("No Selection", "No appointment selected.", "Please select an appointment to update.");
        }
    }

    @FXML
    private void handleCancelAppointment() {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            appointmentList.remove(selected); // Simulate cancelling an appointment
            showAlert("Cancel Appointment", "Appointment Cancelled", "The appointment for " + selected.getPatientName() + " has been cancelled.");
        } else {
            showAlert("No Selection", "No appointment selected.", "Please select an appointment to cancel.");
        }
    }

    @FXML
    private void handlePrescribeMedication() {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Placeholder for prescribe medication logic
            showAlert("Prescribe Medication", "Medication Prescribed", "Medication has been prescribed to " + selected.getPatientName() + ".");
        } else {
            showAlert("No Selection", "No appointment selected.", "Please select a patient to prescribe medication.");
        }
    }

    @FXML
    private void handleViewMedicalHistory() {
        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Placeholder for viewing medical history
            showAlert("Medical History", "Viewing Medical History", "Medical history for " + selected.getPatientName() + " is displayed.");
        } else {
            showAlert("No Selection", "No patient selected.", "Please select a patient to view medical history.");
        }
    }

    // Adding a logout action
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Assuming you have a "Login.fxml" that is the login view
            Parent loginView = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene loginScene = new Scene(loginView);

            // Getting the current window and setting the scene to login
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions, possibly alert the user
        }
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
}
