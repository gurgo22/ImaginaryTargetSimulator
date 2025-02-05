package its;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class MediumController extends BaseController implements Initializable {

    @FXML
    private AnchorPane mainScreen, targetPane, scorePane, buttonPane;
    
    @FXML
    private ComboBox<String> seriesComboBox;
    
    @FXML
    protected TextField currentSeriesText, totalScore;
    
    @FXML
    private ColorPicker colorSelector;
    
    @FXML
    private Circle[] extraCircles, mainCircles;

    @FXML
    private Circle sevenCircleExtra, eightCircleExtra, nineCircleExtra, tenCircleExtra, innerTenCircleExtra;
    
    @FXML
    private Circle sevenCircle, eightCircle, nineCircle, tenCircle, innerTenCircle;

    private Ring[] extraRings, mainRings;

    public MediumController() {
        this.seriesComboBox = new ComboBox<>();
        this.zoomToName = "small";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setComponents(mainScreen, targetPane, scorePane, buttonPane, seriesComboBox, currentSeriesText, totalScore, colorSelector, 30.25);
        extraCircles = new Circle[]{sevenCircleExtra, eightCircleExtra, nineCircleExtra, tenCircleExtra, innerTenCircleExtra};
        mainCircles = new Circle[]{sevenCircle, eightCircle, nineCircle, tenCircle, innerTenCircle};

        extraRings = new Ring[extraCircles.length];
        mainRings = new Ring[mainCircles.length];

        for (int i = 0; i < extraCircles.length; i++) {

            if (i == extraCircles.length - 1) {
                extraRings[i] = new Ring(extraCircles[i], 10);
                mainRings[i] = new Ring(mainCircles[i], 10);
                addRing(extraRings[i]);
                addRing(mainRings[i]);
                extraCircles[i].setOnMouseClicked(this::RingClicked);
                mainCircles[i].setOnMouseClicked(this::RingClicked);
            } else {
                extraRings[i] = new Ring(extraCircles[i], i + 1 + 6);
                mainRings[i] = new Ring(mainCircles[i], i + 1 + 6);
            }
            
            addRing(extraRings[i]);
            addRing(mainRings[i]);
            extraCircles[i].setOnMouseClicked(this::RingClicked);
            mainCircles[i].setOnMouseClicked(this::RingClicked);
        }
        
        setTheme();

    }

    private void addRing(Ring ring) {
        getRings().add(ring);
    }

    @Override
    public void setTheme () {
        
        super.setTheme();
        
        super.outerCircleTheme(this.extraCircles[0]);
        
    }
    
    public static void main(String[] args) {
    }

}
