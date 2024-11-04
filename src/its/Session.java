package its;

public class Session {
    
    private int id;
    private int userId;
    private String date;
    private String series;

    public Session(int id, int userId, String date, String series) {
        
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.series = series;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return series;
    }

    public void setScore(String series) {
        this.series = series;
    }
    
    @Override
    public String toString() {
        return "Session ID: " + id + ", Date: " + date + ", Score: " + series;
    }
    
    public static void main (String[] args) { }
}

