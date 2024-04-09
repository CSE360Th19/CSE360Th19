package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LoginController {


	//controller for login view
	private Stage primaryStage;
	public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
	
		    @FXML
		    private AnchorPane anchorPane;

		    @FXML
		    private Rectangle blueFrame;

		    @FXML
		    private TextField idText;
		    
		    @FXML
		    private Button loginButton;

		    @FXML
		    private Label loginLabel;

		    @FXML
		    private PasswordField passwordText;

		    @FXML
		    void buttonPress(ActionEvent event) {
		    	
		    }
	}

