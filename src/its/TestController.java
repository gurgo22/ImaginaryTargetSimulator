package its;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class TestController extends BaseController implements Initializable {
    
    @FXML
    private AnchorPane mainScreen, targetPane, scorePane, buttonPane;
    @FXML
    private ComboBox<String> seriesComboBox;
    @FXML
    private TextField currentSeriesText, totalScore, loggedInUserText;
    
    @FXML
    private ColorPicker colorSelector;
    
    @FXML
    private Circle oneCircleExtra, twoCircleExtra, threeCircleExtra, fourCircleExtra, fiveCircleExtra, sixCircleExtra, sevenCircleExtra, eightCircleExtra, nineCircleExtra, tenCircleExtra, innerTenCircleExtra;
    @FXML
    private Circle oneCircle, twoCircle, threeCircle, fourCircle, fiveCircle, sixCircle, sevenCircle, eightCircle, nineCircle, tenCircle, innerTenCircle;
    
    private Circle[] extraCircles;
    private Circle[] mainCircles;
    private Ring[] extraRings;
    private Ring[] mainRings;
    
    public TestController() {
        this.zoomToName = "medium";
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SetComponents(mainScreen, targetPane, scorePane, buttonPane, seriesComboBox, currentSeriesText, totalScore, loggedInUserText, colorSelector, 13.9);
        
        LoadSavedData();
        
        initalizeCircles();
        
        SetTheme();   
    }
    
    private void initalizeCircles() {
        
        extraCircles = new Circle[]{oneCircleExtra, twoCircleExtra, threeCircleExtra, fourCircleExtra, fiveCircleExtra, sixCircleExtra, sevenCircleExtra, eightCircleExtra, nineCircleExtra, tenCircleExtra, innerTenCircleExtra};
        mainCircles = new Circle[]{oneCircle, twoCircle, threeCircle, fourCircle, fiveCircle, sixCircle, sevenCircle, eightCircle, nineCircle, tenCircle, innerTenCircle};

        extraRings = new Ring[extraCircles.length];
        mainRings = new Ring[mainCircles.length];

        for (int i = 0; i < extraCircles.length; i++) {

            if (i == extraCircles.length - 1) {

                extraRings[i] = new Ring(extraCircles[i], 10);
                mainRings[i] = new Ring(mainCircles[i], 10);
                AddRing(extraRings[i]);
                AddRing(mainRings[i]);
                extraCircles[i].setOnMouseClicked(this::RingClicked);
                mainCircles[i].setOnMouseClicked(this::RingClicked);

            } else {

                extraRings[i] = new Ring(extraCircles[i], i + 1);
                mainRings[i] = new Ring(mainCircles[i], i + 1);
            }

            AddRing(extraRings[i]);
            AddRing(mainRings[i]);
            extraCircles[i].setOnMouseClicked(this::RingClicked);
            mainCircles[i].setOnMouseClicked(this::RingClicked);
        }
    }
    
    private void AddRing(Ring ring) {
        GetRings().add(ring);
    }
    
    @Override
    public void SetTheme () {
      
        super.SetTheme();
        super.OuterCircleTheme(this.extraCircles[0]); 
    }
    
    public static void main(String[] args) { }
}