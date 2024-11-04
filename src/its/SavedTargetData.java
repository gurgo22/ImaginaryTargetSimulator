package its;

import java.util.ArrayList;
import javafx.collections.ObservableList;

public class SavedTargetData {
    
    private User currentUser;
    
    private String currentSeriesText, totalScore;
    
    private ObservableList<String> series;
    
    private ArrayList<Shot> shotsCurrentTarget;

    Target target;

    private String zoomToName;
    
    private String theme;

    public SavedTargetData(User currentUser, String currentSeriesText, ObservableList<String> series, String totalScore, ArrayList<Shot> shotsCurrentTarget, Target target, String zoomToName, String theme) {
     
        this.currentUser = currentUser;
        this.currentSeriesText = currentSeriesText;
        this.series = series;
        this.totalScore = totalScore;
        this.shotsCurrentTarget = shotsCurrentTarget;
        this.target = target;
        this.zoomToName = zoomToName;
        this.theme = theme;
    }

    public void setShotsCurrentTarget(ArrayList<Shot> shotsCurrentTarget) {
        this.shotsCurrentTarget = shotsCurrentTarget;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public void setZoomToName(String zoomToName) {
        this.zoomToName = zoomToName;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentSeriesText() {
        return currentSeriesText;
    }

    public void setCurrentSeriesText(String currentSeriesText) {
        this.currentSeriesText = currentSeriesText;
    }
    
    public ObservableList<String> getSeries() {
        return series;
    }

    public void setSeries(ObservableList<String> series) {
        this.series = series;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public ArrayList<Shot> getShotsCurrentTarget() {
        return shotsCurrentTarget;
    }

    public Target getTarget() {
        return target;
    }

    public String getZoomToName() {
        return zoomToName;
    }

    public String getTheme() {
        return theme;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public static void main (String[] args) { }
    
}
