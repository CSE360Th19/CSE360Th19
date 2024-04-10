package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import application.DoctorController.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MessageController implements Initializable {
	private Stage primaryStage;
	private String employeeId;
	private String role;
	public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
	public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
	public void setRole(String role) {
        this.role = role;
    }
	
    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Message, String> idColumn;

    @FXML
    private TextField idInput;

    @FXML
    private TableColumn<Message, String> messageColumn;

    @FXML
    private TextField messageInput;

    @FXML
    private TableView<Message> messageTable;

    @FXML
    private TableColumn<Message, String> nameColumn;

    @FXML
    private Button sendButton;

    private ObservableList<Message> messageList = FXCollections.observableArrayList();

    @FXML
    void backButton(ActionEvent event) throws IOException {
    	if(role.equals("patient")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientView.fxml"));

        	loader.setControllerFactory(controllerClass -> {
        	    PatientController patientController = new PatientController();
        	    patientController.setPrimaryStage(primaryStage);
        	    patientController.setPatientId(employeeId);
        	    return patientController;
        	});

        	Parent root = loader.load();

        	primaryStage.getScene().setRoot(root);
        	primaryStage.setTitle("Patient View");
    	}
    	else {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorView.fxml"));
    		
        	loader.setControllerFactory(controllerClass -> {
        	    DoctorController doctorController = new DoctorController();
        	    doctorController.setPrimaryStage(primaryStage);
        	    doctorController.setEmployeeId(employeeId);
        	    return doctorController;
        	});

        	Parent root = loader.load();

        	primaryStage.getScene().setRoot(root);
        	primaryStage.setTitle("Doctor View");
    	}
    }
    
    public void loadMessages() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        // Clear the existing messageList
        messageList.clear();

        // Construct the directory path based on the user's role and ID
        String directoryPath = "user_info/" + role + "s/" + employeeId + "/messages/";

        // Check if the directory exists
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            // Get a list of message files in the directory
            File[] messageFiles = directory.listFiles();
            if (messageFiles != null) {
                // Read each message file and add its content to the messageList
                for (File file : messageFiles) {
                    try {
                        // Read the content of the file
                        List<String> lines = Files.readAllLines(file.toPath());
                        // Extract message content (assuming one message per line)
                        StringBuilder messageContent = new StringBuilder();
                        for (String line : lines) {
                            if (line.startsWith("Message:")) {
                                messageContent.append(line.substring("Message:".length()).trim()).append("\n");
                            }
                        }
                        // Add a new Message object to the messageList
                        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                        if(role.equals("patient")) {
                        	messageList.add(new Message(getName(fileName, "doctor"), fileName, messageContent.toString()));
                        }
                        else {
                        	messageList.add(new Message(getName(fileName, "patient"), fileName, messageContent.toString()));
                        }
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // Set the messageList to the TableView
        messageTable.setItems(messageList);
    }

    
    public void sendMessage() {
    	String id = idInput.getText();
    	String message = messageInput.getText();
    	if(id.isEmpty() || message.isEmpty()) {    
    		showErrorDialog("Please enter the ID and message.");
    	}
    	
    	try {
    		File doctorsDir = new File("user_info/doctors");
    		File patientsDir = new File("user_info/patients");
    		if(searchDirectory(doctorsDir, id)) {
                String directoryPath = "user_info/" + role + "s/" + employeeId + "/messages/";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    boolean created = directory.mkdirs(); // This creates all necessary parent directories as well
                    if (!created) {
                    System.out.println("Failed to create directory: " + directoryPath);
                        return; // Return early if directory creation fails
                    }
                }
                
                // save to senders first
                File file = new File("user_info/"+role+"s/" + employeeId + "/messages/" + id + ".txt");
                FileWriter writer = new FileWriter(file, true);
                writer.write("Message: " + getName(employeeId, role) + " - " + message + "\n");
                writer.close();
                System.out.println("Message sent successfully: " + file.getName());
                
                // save to targets 
                String directoryPath2 = "user_info/doctors/" + id + "/messages/";
                File directory2 = new File(directoryPath2);
                if (!directory2.exists()) {
                    boolean created = directory2.mkdirs(); // This creates all necessary parent directories as well
                    if (!created) {
                    System.out.println("Failed to create directory: " + directoryPath2);
                        return; // Return early if directory creation fails
                    }
                }
                File file2 = new File("user_info/doctors/" + id + "/messages/" + employeeId + ".txt");
                FileWriter writer2 = new FileWriter(file2, true);
                writer2.write("Message: " + getName(employeeId, role) + " - " + message + "\n");
                writer2.close();
                System.out.println("Message sent successfully: " + file2.getName());
                loadMessages();
    		}
    		else if (searchDirectory(patientsDir, id)) {
    			String directoryPath = "user_info/" + role + "s/" + employeeId + "/messages/";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    boolean created = directory.mkdirs(); // This creates all necessary parent directories as well
                    if (!created) {
                    System.out.println("Failed to create directory: " + directoryPath);
                        return; // Return early if directory creation fails
                    }
                }
                
                // save to senders first
                File file = new File("user_info/"+role+"s/" + employeeId + "/messages/" + id + ".txt");
                FileWriter writer = new FileWriter(file, true);
                writer.write("Message: " + getName(employeeId, role) + " - " + message + "\n");
                writer.close();
                System.out.println("Message sent successfully: " + file.getName());
                
                // save to targets 
                String directoryPath2 = "user_info/patients/" + id + "/messages/";
                File directory2 = new File(directoryPath2);
                if (!directory2.exists()) {
                    boolean created = directory2.mkdirs(); // This creates all necessary parent directories as well
                    if (!created) {
                    System.out.println("Failed to create directory: " + directoryPath2);
                        return; // Return early if directory creation fails
                    }
                }
                File file2 = new File("user_info/patients/" + id + "/messages/" + employeeId + ".txt");
                FileWriter writer2 = new FileWriter(file2, true);
                writer2.write("Message: " + getName(employeeId, role) + " - " + message + "\n");
                writer2.close();
                System.out.println("Message sent successfully: " + file2.getName());
                loadMessages();
    		}
    		else {
    			showErrorDialog("Invalid ID given");
    		}
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    	
    
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
    	
    
    private boolean searchDirectory(File directory, String id) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory() && file.getName().equals(id)) {
                    	return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static class Message {
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty message;

        public Message(String name, String id, String message) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.message = new SimpleStringProperty(message);
        }

        public String getName() { return name.get(); }
        public String getId() { return id.get(); }
        public String getMessage() { return message.get(); }
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	loadMessages();
    }
    
    

}
