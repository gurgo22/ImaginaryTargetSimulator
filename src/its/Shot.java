package its;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Shot {
    
    private final Circle circle;
    private final Text valueText;
    private final int value;
    //save layout maybe  -  to show previous series
    
    public Shot(Circle circle, Text valueText, int ringValue) {
        this.circle = circle;
        this.valueText = valueText;
        this.value = ringValue;
    }

    public Circle getCircle() {
        return circle;
    }

    public int getRingValue() {
        return value;
    }
    
    public Text getValueText() {
        return valueText;
    }
    
    public static void main (String[] args) { }
}