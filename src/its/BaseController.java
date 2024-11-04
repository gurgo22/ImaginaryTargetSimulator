package its;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseController {

    private AnchorPane mainScreen;
    private AnchorPane targetPane;
    private AnchorPane scorePane;
    private AnchorPane buttonPane;
    private ComboBox<String> seriesComboBox;
    private ColorPicker colorSelector;
    private TextField currentSeriesText = new TextField("0"), totalScore = new TextField("0"), loggedInUserText = new TextField("0");

    private SavedTargetData save;
    private User currentUser;
    private ArrayList<Shot> shotsCurrentTarget = new ArrayList<>();
    private Target target = new Target();
    private double diameter;
    protected String zoomToName;
    private String theme = "#edce32";

    public void SetComponents(AnchorPane mainScreen, AnchorPane targetPane,AnchorPane scorePane, AnchorPane buttonPane, ComboBox<String> seriesCombobox, TextField currentSeriesText, TextField totalScoreText, TextField loggedInUserText, ColorPicker colorSelector, double diameterValue) {

        this.mainScreen = mainScreen;
        this.targetPane = targetPane;
        this.scorePane = scorePane;
        this.buttonPane = buttonPane;
        this.seriesComboBox = seriesCombobox;
        this.diameter = diameterValue;
        this.currentSeriesText = currentSeriesText;
        this.totalScore = totalScoreText;
        this.loggedInUserText = loggedInUserText;
        if (!mainScreen.getChildren().contains(seriesCombobox)) {
            mainScreen.getChildren().add(seriesCombobox);
        }
        this.colorSelector = colorSelector;
    }
    
    public void LoadSavedData() {

        if (this.save != null) {
            this.currentUser = save.getCurrentUser();
            if (currentUser != null) {
                loggedInUserText.setText("User: " + currentUser.getUsername());
            }
            this.currentSeriesText.setText(save.getCurrentSeriesText());
            this.seriesComboBox.setItems(save.getSeries());
            this.totalScore.setText(save.getTotalScore());
            this.shotsCurrentTarget = save.getShotsCurrentTarget();
            this.target.setShots(save.getTarget().getShots());
            this.theme = save.getTheme();
            SetTheme();
        }
    }
    
    //SETS COMPONENTS COLOR
    public void SetTheme () {

        targetPane.setStyle("-fx-background-color: " + theme + ";");
        
        seriesComboBox.setStyle("-fx-background-color: " + theme + ";");
        
        colorSelector.setStyle("-fx-background-color: " + theme + ";");
        
        loggedInUserText.setStyle("-fx-text-fill: " + theme + ";");
        
        for (Node component : scorePane.getChildren()) {
            component.setStyle("-fx-text-fill: " + theme + ";");
        }
    }

    public void SetCurrentUser(User user) {
        this.currentUser = user;
    }

    public User GetCurrentUser() {
        return currentUser;
    }

    public void SetTarget (Target target) {
        this.target = target;
    }
    
    public void SetShots(ArrayList<Shot> shots) {
        this.target.setShots(shots);
    }

    public void SetShotsCurrentTarget(ArrayList<Shot> shotsCurrentTarget) {
        this.shotsCurrentTarget = shotsCurrentTarget;
    }

    public void SetComboBoxItems(ObservableList<String> items) {
        seriesComboBox.setItems(FXCollections.observableArrayList(items));
    }
    
    public void SetColor (String color) {
        this.theme = color;  
    }

    public ObservableList<String> GetComboBoxItems() {
        return seriesComboBox.getItems();
    }
    
    public String GetZoomToName () {
        return this.zoomToName;
    }

    public ArrayList<Ring> GetRings() {
        return this.target.getRings();
    }

    public ArrayList<Shot> GetShotsCurrentTarget() {
        return shotsCurrentTarget;
    }

    public Target GetTarget() {
        return target;
    }
    
    public void SetSavedData (SavedTargetData save) {
        this.save = save;
    }
    
    //WHEN A RING IS CLICKED
    @FXML
    public void RingClicked(MouseEvent event) {

        Circle clickedCircle = (Circle) event.getSource();

        for (int i = 0; i < target.getRings().size(); i++) {
            if (target.getRings().get(i).getCircle().equals(clickedCircle)) {

                int trueValue = target.getRings().get(i).getValue();

                AddCircleAtCursor(event, this.diameter, trueValue);

                System.out.println(trueValue);
                break;
            }
        }
    }
    
    public Circle FindClickedCircleText(MouseEvent event) {

        Circle clickedCircle = null;
        int i = 0;
        boolean exists = false;

        while (i < target.getShots().size() && !exists) {
            //CHECK IF VALUE TEXT IS SAME AS THE CLICKED VALUE TEXT
            if (target.getShots().get(i).getValueText().equals((Text) event.getSource())) {

                exists = true;
                clickedCircle = target.getShots().get(i).getCircle();

            }
            i++;
        }
        return clickedCircle;
    }
    
    //RETURN VALUE OF PARENT SHOT
    public int GetShotTrueValue (Circle clickedCircle) {

        int trueValue = 0;

        int i = 0;
        boolean e = false;
        
        while (i < target.getShots().size() && !e) {

            if (target.getShots().get(i).getCircle().equals(clickedCircle)) {

                e = true;
                trueValue = target.getShots().get(i).getRingValue();

            }
            i++;
        }
        return trueValue;
    }
    
    //WHEN A SHOT IS CLICKED
    public void ShotClicked(MouseEvent event) {
        
        Object eventSource = event.getSource();

        Circle clickedCircle = null;

        //WHEN THE SHOT CIRCLE IS CLICKED
        if (eventSource instanceof Circle) {

            clickedCircle = (Circle) eventSource;

        } else {    //WHEN THE SHOT VALUE TEXT IS CLICKED
            clickedCircle = FindClickedCircleText(event);
        }

        int trueValue = GetShotTrueValue(clickedCircle);

        if (trueValue != 0) {
            AddCircleAtCursor(event, diameter, trueValue);
            System.out.println(trueValue);
        }
    }

    public void SetShotText (Text shotText, double x, double y) {
        
        shotText.setFill(javafx.scene.paint.Color.WHITE);
        shotText.setFont(Font.font(diameter * 0.7));
        double textWidth = shotText.getLayoutBounds().getWidth();
        double textHeight = shotText.getLayoutBounds().getHeight();
        shotText.setX(x - textWidth / 2);
        shotText.setY(y + textHeight / 4);
    }
    
    public void UpdateSeriesAfterShot (int shotValue) {
        
        if (shotsCurrentTarget.size() % 10 == 0) {
            UpdateSeries();
        }else if (shotsCurrentTarget.isEmpty()) {
            currentSeriesText.setText(shotValue + "");
        } 
    }
    
    //PLACES A SHOT AT THE COURSOR COORDINATES
    public void AddCircleAtCursor(MouseEvent event, double diameter, int shotValue) {
        
        double sceneX = event.getSceneX();
        double sceneY = event.getSceneY();
        double localX = targetPane.sceneToLocal(sceneX, sceneY).getX();
        double localY = targetPane.sceneToLocal(sceneX, sceneY).getY();

        Circle shotImage = new Circle(localX, localY, diameter, javafx.scene.paint.Color.BLACK);

        Text shotText = new Text(String.valueOf(target.getShots().size() + 1));

        SetShotText(shotText, localX, localY);

        Shot newShot = new Shot(shotImage, shotText, shotValue);

        newShot.getCircle().setOnMouseClicked(event1 -> this.ShotClicked(event1));
        shotImage.setOnMouseClicked(event1 -> this.ShotClicked(event1));
        shotText.setOnMouseClicked(event1 -> this.ShotClicked(event1));

        targetPane.getChildren().add(shotImage);
        targetPane.getChildren().add(shotText);
        
        shotsCurrentTarget.add(newShot);
        target.getShots().add(newShot);

        SetCurrentSeries();

        UpdateSeriesAfterShot(shotValue);
    }
    
    //ADDS THE LAST 10 SHOT SERIE TO THE COMBOBOX
    public void UpdateSeries() {

        seriesComboBox.getItems().add(SumSeries(target.getShots().subList(target.getShots().size() - 10, target.getShots().size() - 0)) + "");
        shotsCurrentTarget.clear();
    }

    public int SumSeries(List<Shot> series) {

        int sum = 0;

        for (int i = 0; i < series.size(); i++) {
            sum += series.get(i).getRingValue();
        }
        return sum;
    }

    public void SetCurrentSeries() {
        
        currentSeriesText.setText(SumSeries(shotsCurrentTarget) + "");
        totalScore.setText(SumSeries(target.getShots()) + "");
    }

    public void ClearTarget() {

        currentSeriesText.setText("0");

        for (int i = 0; i < target.getShots().size(); i++) {
            
            targetPane.getChildren().remove(target.getShots().get(i).getCircle());
            targetPane.getChildren().remove(target.getShots().get(i).getValueText());
            
        }
    }
    
    //CREATES NEW CONTROLLER AND INITIALIZES A SCENE BASED ON IT
    public Scene LoadNewController(String controllerName) {
        
        Scene scene = null;
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(controllerName + ".fxml"));
            Parent root = loader.load();
            this.save = new SavedTargetData(currentUser, currentSeriesText.getText(), seriesComboBox.getItems(), totalScore.getText(), shotsCurrentTarget, target, zoomToName, theme);
            
            //WHEN LOGIN PAGE IS OPENED
            if (controllerName.contains("login")) {
                
                LoginController newController = loader.getController();
                newController.SetSavedData(this.save);
            
            }else {
                //WHEN TARGET IS ZOOMED
                BaseController newController = loader.getController();
                newController.SetSavedData(this.save);
                newController.LoadSavedData();
            }
            
            scene = new Scene(root);
        
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

    //CHANGES TARGET SIZE
    public void ZoomTarget() {

        Scene scene = LoadNewController(zoomToName);
        
        Stage stage = (Stage) mainScreen.getScene().getWindow();
        
        //stage.setFullScreen(true);
        //stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
    //SAVE TARGET AS PNG
    public void PrintTarget() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save Target Image");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.png"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null && file.getPath().endsWith(".png")) {

            file = new File(file.getPath() + ".png");

            WritableImage image = targetPane.snapshot(new SnapshotParameters(), null);

            try {

                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                System.out.println("Target saved to " + file.getPath());

            } catch (IOException e) {
                System.out.println("An error occured while saving the file!");
            }
        }
    }
    //SETS THEME COLOR
    public void ChangeColor () {
        
        Color selected = colorSelector.getValue();
        
        int red = (int) (selected.getRed() * 255);
        int green = (int) (selected.getGreen() * 255);
        int blue = (int) (selected.getBlue() * 255);
        
        String hexColor = String.format("#%02X%02X%02X", red, green, blue);

        this.theme = hexColor;
        
        SetTheme();
    }
    
    public void OuterCircleTheme (Circle outerCircle) {
        
        outerCircle.setStyle("-fx-stroke: BLACK");
        outerCircle.setStyle("-fx-fill: " + this.theme);
    }
    
    public void ResetTarget () {
        
        ClearTarget();
        this.target.getShots().clear();
        this.shotsCurrentTarget.clear();
        totalScore.setText("0");
        seriesComboBox.getItems().clear();
    }
    
    //NEED A METHOD HERE AS WELL BEACUSE OF THE USER ID
    public void SaveSession(String seriesText) {
        DatabaseHelper.SaveSession(seriesText, currentUser.getId());
    }
    
    //OPEN LOGIN PAGE
    public void OpenLoginWindow() throws IOException {

        this.save = new SavedTargetData(currentUser, currentSeriesText.getText(), seriesComboBox.getItems(), totalScore.getText(), shotsCurrentTarget, target, zoomToName, theme);
        
        LoadSavedData();
        Scene scene = LoadNewController("login");

        Stage stage = (Stage) mainScreen.getScene().getWindow();

        //stage.setFullScreen(true);
        //stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
        
    public void SaveHighScore () {
        
        if (currentUser != null && BestSerie() > currentUser.getHighScore()) {
            DatabaseHelper.UpdateHighScore(currentUser.getId(), BestSerie());
        }
    }
    
    //CALCULATE USERS CURRENT BEST SERIE
    public int BestSerie () {
        
        int best = 0;
        
        for (String serie: seriesComboBox.getItems()) {
            
            if (Integer.parseInt(serie) > best) {
                best = Integer.parseInt(serie);
            }
        }
        return best;
    }
    
    //OPEN LEADERBOARD SCREEN
    public void LoadLeaderboard () {

        Target copy = new Target();
        copy.setRings(this.target.getRings());
        copy.setShots(this.target.getShots());
        
        SavedTargetData saved = new SavedTargetData(currentUser, currentSeriesText.getText(), seriesComboBox.getItems(), totalScore.getText(), shotsCurrentTarget, copy, zoomToName, theme);
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("leaderboard.fxml"));
            Parent root = loader.load();

            LeaderboardController newController = loader.getController();
            newController.SetSave(saved);
            
            Scene scene = new Scene(root);

            Stage stage = (Stage) mainScreen.getScene().getWindow();
            
            //stage.setFullScreen(true);
            //stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("An error occured while loading the fxml file!" + e);
        }
    }

    public static void main(String[] args) { }

}
