package its;

import java.util.ArrayList;

public class Target {
    
    private ArrayList<Shot> shots;
     
    private ArrayList<Ring> rings;

    public Target() {

        this.shots = new ArrayList<>();
        this.rings = new ArrayList<>();

    }

    public void setShots(ArrayList<Shot> shots) {
        this.shots = shots;
    }

    public void setRings(ArrayList<Ring> rings) {
        this.rings = rings;
    }

    public ArrayList<Shot> getShots() {
        return shots;
    }

    public ArrayList<Ring> getRings() {
        return rings;
    }

    public static void main (String[] args) {   }
    
}
