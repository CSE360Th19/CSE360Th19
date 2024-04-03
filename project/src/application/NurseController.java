package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class NurseController {

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
    private TextArea healthIssuesText;

    @FXML
    private TextField heightInput;

    @FXML
    private TextArea immunizationText;

    @FXML
    private TextArea medicationsText;

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
    void clearCheckin(ActionEvent event) {
        clearFields(nameInput, heightInput, weightInput, tempInput, bpInput);
    }

    @FXML
    void clearSpecifics(ActionEvent event) {
        clearFields(allergiesInput, concernsInput);
    }

    @FXML
    void submitCheckin(ActionEvent event) {
        if (checkFieldsNotEmpty(nameInput, heightInput, weightInput, tempInput, bpInput)) {
            // All fields are filled
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
        } else {
            System.out.println("Not all specifics fields are filled.");
        }
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

}
