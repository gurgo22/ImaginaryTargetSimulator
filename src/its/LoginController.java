package its;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
        try {
            
            String username = usernameField.getText();
            String password = passwordField.getText();   
            
            User currentUser = DatabaseHelper.LoginUser(username, AESEncrypter.Encrypt(password));
            
            if (currentUser != null) {
                
                loginMessage.setText("Login successful!" + currentUser.getUsername());
                this.save.setCurrentUser(currentUser);
                
                LoadMainScreen();
                
            } else {
                loginMessage.setText("Invalid username or password.");
            }
        } catch (Exception exc) {
            System.out.println("Exception while encrypting: " + exc);
        }
    }
    
    @FXML
    private void Register() {
        
        try {
            
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            String encryptedPassword = AESEncrypter.Encrypt(password);
            
            if (DatabaseHelper.FindUser(username, encryptedPassword) != null) {
                
                System.out.println("This user is already registered!");
                loginMessage.setText("This user is already registered!");
                
            }else {
                
                DatabaseHelper.RegisterUser(username, encryptedPassword, "");
                LoadMainScreen();
            }
        } catch (Exception exc) {
            System.out.println("Exception while encrypting: " + exc);
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
    private void ReturnToMainScreen() {
        LoadMainScreen();
    }
    
    public static void main(String[] args) { }
}