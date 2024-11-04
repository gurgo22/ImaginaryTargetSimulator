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

public abstract class BaseController {

    protected AnchorPane mainScreen;

    protected AnchorPane targetPane;
    
    protected AnchorPane scorePane;
    
    protected AnchorPane buttonPane;

    protected ComboBox<String> seriesComboBox;

    protected ColorPicker colorSelector;
    
    protected TextField currentSeriesText = new TextField("0"), totalScore = new TextField("0");

    private ArrayList<Shot> shotsCurrentTarget = new ArrayList<>();

    Target target = new Target();

    private double diameter;

    protected String zoomToName = "MediumController";
    
    private String theme = "#edce32";

    public void setComponents(AnchorPane mainScreen, AnchorPane targetPane,AnchorPane scorePane, AnchorPane buttonPane, ComboBox<String> seriesCombobox, TextField currentSeriesText, TextField totalScoreText, ColorPicker colorSelector, double diameterValue) {

        this.mainScreen = mainScreen;
        this.targetPane = targetPane;
        this.scorePane = scorePane;
        this.buttonPane = buttonPane;
        this.seriesComboBox = seriesCombobox;
        this.diameter = diameterValue;
        this.currentSeriesText = currentSeriesText;
        this.totalScore = totalScoreText;
        if (!mainScreen.getChildren().contains(seriesCombobox)) {
            mainScreen.getChildren().add(seriesCombobox);
        }
        this.colorSelector = colorSelector;

    }
    
    public void setTheme () {

        targetPane.setStyle("-fx-background-color: " + theme + ";");
        
        seriesComboBox.setStyle("-fx-background-color: " + theme + ";");
        
        colorSelector.setStyle("-fx-background-color: " + theme + ";");
        
        for (Node component : scorePane.getChildren()) {
            
            component.setStyle("-fx-text-fill: " + theme + ";");
            
        }
        
    }

    public void setTarget (Target target) {
        this.target = target;
    }
    
    public void setShots(ArrayList<Shot> shots) {
        this.target.setShots(shots);
    }

    public void setShotsCurrentTarget(ArrayList<Shot> shotsCurrentTarget) {
        this.shotsCurrentTarget = shotsCurrentTarget;
    }

    public void setComboBoxItems(ObservableList<String> items) {
        seriesComboBox.setItems(FXCollections.observableArrayList(items));
    }
    
    public void setColor (String color) {
        this.theme = color;  
    }

    public ObservableList<String> getComboBoxItems() {
        return seriesComboBox.getItems();
    }

    protected ArrayList<Ring> getRings() {
        return this.target.getRings();
    }

    @FXML
    public void RingClicked(MouseEvent event) {

        Circle clickedCircle = (Circle) event.getSource();

        for (int i = 0; i < target.getRings().size(); i++) {
            if (target.getRings().get(i).getCircle().equals(clickedCircle)) {

                int trueValue = target.getRings().get(i).getValue();

                addCircleAtCursor(event, this.diameter, trueValue);

                System.out.println(trueValue);
                break;
            }
        }

    }

    public void ShotClicked(MouseEvent event) {

        Object eventSource = event.getSource();

        Circle clickedCircle = null;

        if (eventSource instanceof Circle) {

            clickedCircle = (Circle) eventSource;

        } else {

            int i = 0;
            boolean e = false;

            while (i < target.getShots().size() && !e) {

                if (target.getShots().get(i).getValueText().equals((Text) eventSource)) {

                    e = true;
                    clickedCircle = target.getShots().get(i).getCircle();

                }
                i++;
            }

        }

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

        if (trueValue != 0) {
            addCircleAtCursor(event, diameter, trueValue);
            System.out.println(trueValue);
        }

    }

    public void addCircleAtCursor(MouseEvent event, double diameter, int shotValue) {

        double sceneX = event.getSceneX();
        double sceneY = event.getSceneY();
        double localX = targetPane.sceneToLocal(sceneX, sceneY).getX();
        double localY = targetPane.sceneToLocal(sceneX, sceneY).getY();

        Circle shotImage = new Circle(localX, localY, diameter, javafx.scene.paint.Color.BLACK);

        Text shotText = new Text(String.valueOf(target.getShots().size() + 1));

        shotText.setFill(javafx.scene.paint.Color.WHITE);
        shotText.setFont(Font.font(diameter * 0.7));
        double textWidth = shotText.getLayoutBounds().getWidth();
        double textHeight = shotText.getLayoutBounds().getHeight();
        shotText.setX(localX - textWidth / 2);
        shotText.setY(localY + textHeight / 4);

        Shot newShot = new Shot(shotImage, shotText, shotValue);

        newShot.getCircle().setOnMouseClicked(event1 -> this.ShotClicked(event1));
        shotImage.setOnMouseClicked(event1 -> this.ShotClicked(event1));
        shotText.setOnMouseClicked(event1 -> this.ShotClicked(event1));

        targetPane.getChildren().add(shotImage);
        targetPane.getChildren().add(shotText);
        
        shotsCurrentTarget.add(newShot);
        target.getShots().add(newShot);

        setCurrentSeries();

        if (shotsCurrentTarget.size() % 10 == 0) {

            updateSeries();

        }else if (shotsCurrentTarget.isEmpty()) {
            
            currentSeriesText.setText(shotValue + "");
            
        }

    }

    public void updateSeries() {

        seriesComboBox.getItems().add(sumSeries(target.getShots().subList(target.getShots().size() - 10, target.getShots().size() - 0)) + "");
        shotsCurrentTarget.clear();
        
    }

    public int sumSeries(List<Shot> series) {

        int sum = 0;

        for (int i = 0; i < series.size(); i++) {

            sum += series.get(i).getRingValue();

        }
        return sum;
    }

    public void setCurrentSeries() {
        
        currentSeriesText.setText(sumSeries(shotsCurrentTarget) + "");
        totalScore.setText(sumSeries(target.getShots()) + "");
    
    }

    public void clearTarget() {

        currentSeriesText.setText("0");

        for (int i = 0; i < target.getShots().size(); i++) {

            targetPane.getChildren().remove(target.getShots().get(i).getCircle());
            targetPane.getChildren().remove(target.getShots().get(i).getValueText());

        }

    }

    public void zoomTarget() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.zoomToName + ".fxml"));
            Parent root = loader.load();

            BaseController newController = loader.getController();
            newController.setColor(this.theme);
            newController.setTheme();
            newController.setShots(new ArrayList<>(target.getShots()));
            newController.setShotsCurrentTarget(new ArrayList<>(this.shotsCurrentTarget));
            newController.setComboBoxItems(this.getComboBoxItems());
           
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

    public void printTarget() {

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
    
    public void changeColor () {
        
        Color selected = colorSelector.getValue();
        
        int red = (int) (selected.getRed() * 255);
        int green = (int) (selected.getGreen() * 255);
        int blue = (int) (selected.getBlue() * 255);
        
        String hexColor = String.format("#%02X%02X%02X", red, green, blue);

        this.theme = hexColor;
        
        setTheme();
        
    }
    
    public void outerCircleTheme (Circle outerCircle) {
        
        outerCircle.setStyle("-fx-stroke: BLACK");
        outerCircle.setStyle("-fx-fill: " + this.theme);
        
    }
    
    public void resetTarget () {
        
        clearTarget();
        this.target.getShots().clear();
        this.shotsCurrentTarget.clear();
        totalScore.setText("0");
        seriesComboBox.getItems().clear();
        
    }

    public static void main(String[] args) { }

}
