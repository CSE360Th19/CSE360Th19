package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientController {

    private Stage primaryStage;
    
    
    @FXML
    private TableView<?> appointmentsTable;

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

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void confirmButtonClick(ActionEvent event) {

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

}
