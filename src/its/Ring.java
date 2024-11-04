package its;

import javafx.scene.shape.Circle;

public class Ring {
        
    private final Circle circle;
    private final int value;

    public Ring (Circle circle, int value) {
        this.circle = circle;
        this.value = value;
    }

    public Circle getCircle() {
        return circle;
    }

    public int getValue() {
        return value;
    }
    
    public static void main (String[] args) { }
}
