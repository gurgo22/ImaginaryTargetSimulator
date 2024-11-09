package its;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LeaderboardController implements Initializable {

    @FXML
    private TableView<User> leaderboardTable;
    
    @FXML
    private TableColumn<User, String> usernameColumn;
    
    @FXML
    private TableColumn<User, Integer> highscoreColumn;
    
    private SavedTargetData save;

    public SavedTargetData GetSave() {
        return save;
    }

    public void SetSave(SavedTargetData save) {
        this.save = save;
    }

    //SET THE LEADERBOARD CONTENT
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        highscoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));

        leaderboardTable.setItems(GetUserData());
    }
    
    //RETURNS ALL USERS THAT HAVE AN EXISTING HIGH SCORE
    private ObservableList<User> GetUserData() {
        
        List<User> users = DatabaseHelper.GetAllUsers();
        
        users.removeIf(user -> user.getHighScore() == 0);
        
        return FXCollections.observableArrayList(users);
    }
    
    //EXIT THE LEADERBOARD WINDOW AND RETURN TO THE TARGET
    public void exit () {
        
         try {
             
            FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
            Parent root = loader.load();
            
            BaseController newController = loader.getController();
            newController.SetSavedData(save);
            newController.LoadSavedData();
            System.out.println(newController.GetTarget().getShots().size());

            Scene scene = new Scene(root);
            Stage stage = (Stage) leaderboardTable.getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            
            System.out.println("An error occurred while loading the test.fxml.");
        }
    }
    
    public static void main (String[] args) { }
}
