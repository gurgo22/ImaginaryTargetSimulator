package its;

import java.util.Objects;

public class User {

    private int id;
    private String username;
    private String password;
    private int highScore;

    public User(int id, String username, String password, int highScore) {
        
        this.id = id;
        this.username = username;
        this.password = password;
        this.highScore = highScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int innerTen() {

        return 0;
    }

    @Override
    public boolean equals(Object other) {
        
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }

        User user = (User) other;

        return id == user.id
                && highScore == user.highScore
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password);
    }

    public static void main(String[] args) { }
}
