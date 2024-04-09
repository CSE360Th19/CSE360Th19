package application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class DoctorController {
    @FXML
    private TableView<?> appointmentsTable; // Placeholder for actual data type
    private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    public void initialize() {
        // Initialization logic
    }

    // Handlers for UI interactions
    @FXML
    private void onAppointmentSelect() {
        // Handle appointment selection
    }
}
