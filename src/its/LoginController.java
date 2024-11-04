package its;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
    
    @FXML
    private AnchorPane mainScreen;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label loginMessage;
    
    private SavedTargetData save;

    public SavedTargetData GetSave() {
        return save;
    }

    public void SetSavedData(SavedTargetData save) {
        this.save = save;
    }
    
    @FXML
    private void Login() {
    
        String username = usernameField.getText();
        String password = passwordField.getText();

        User currentUser = DatabaseHelper.LoginUser(username, password);

        if (currentUser != null) {

            loginMessage.setText("Login successful!" + currentUser.getUsername());
            this.save.setCurrentUser(currentUser);
            
            LoadMainScreen();
            
        } else {
            loginMessage.setText("Invalid username or password.");
        }
    }
    
    public void LoadMainScreen () {
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
            Parent root = loader.load();
            
            BaseController newController = loader.getController();

            newController.SetSavedData(save);

            newController.LoadSavedData();
            //newController.SetCurrentUser(currentUser);

            Scene scene = new Scene(root);

            Stage stage = (Stage) mainScreen.getScene().getWindow();
            
            //stage.setFullScreen(true);
            //stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("An error occured while loading the fxml file!");
        }  
    }
    
    @FXML
    private void Register() {
        
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseHelper.FindUser(username, password) != null) {
            loginMessage.setText("This user is already registered!.");
        }else {
            DatabaseHelper.RegisterUser(username, password, "");
            LoadMainScreen();
        }
    }
    
    @FXML
    private void ReturnToMainScreen() {
        LoadMainScreen();
    }
    
    public static void main(String[] args) { }
    
}