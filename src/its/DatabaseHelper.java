package its;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_PATH = Paths.get("src/its/database/leaderboard.db").toAbsolutePath().toString();
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection Connect() {

        Connection conn = null;
        
        try {
           
            conn = DriverManager.getConnection(URL);
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        
    }
        return conn;
    }

    public static void CreateTables() {

        String createUserTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "password TEXT NOT NULL,"
                + "high_score INTEGER NOT NULL"
                + ");";

        String createSessionTable = "CREATE TABLE IF NOT EXISTS sessions ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER,"
                + "date TEXT,"
                + "series TEXT,"
                + "FOREIGN KEY (user_id) REFERENCES users (id)"
                + ");";

        try (Connection conn = Connect(); PreparedStatement pstmt1 = conn.prepareStatement(createUserTable); PreparedStatement pstmt2 = conn.prepareStatement(createSessionTable)) {
            pstmt1.execute();
            pstmt2.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void RegisterUser(String username, String password, String highScore) {
       
        String sql = "INSERT INTO users(username, password, high_score) VALUES(?,?,?)";

        try (Connection conn = Connect(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, highScore);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static User LoginUser(String username, String password) {
        
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        User user = null;
        
        try (Connection conn = Connect(); PreparedStatement statement = conn.prepareStatement(sql) ) {
            
            statement.setString(1, username);
            statement.setString(2, password);
            
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                
                user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getInt("high_score"));
            
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return user;
    }
    
    public static User FindUser(String username, String password) {
        
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = Connect(); PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, username);
            statement.setString(2, password);
            
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getInt("high_score"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public static void UpdateHighScore(int id, int newHighScore) {
        
        String query = "UPDATE users SET high_score = ? WHERE id = ?";
        
        try (Connection conn = Connect();
        
            PreparedStatement statement = conn.prepareStatement(query)) {
            
            statement.setInt(1, newHighScore);
            statement.setInt(2, id);
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
    
    public static List<User> GetAllUsers() {
        
        List<User> users = new ArrayList<>();
        
        String query = "SELECT * FROM users ORDER BY high_score DESC";
        
        try (Connection conn = Connect(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
        
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getInt("high_score")));
            }
            
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }
    
    //SAVE USERS SESSION INTO DATABASE
    public static void SaveSession(String seriesText, int userId) {
        
        String sql = "INSERT INTO sessions(user_id, score, date) VALUES(?,?,?)";

        try (Connection conn = DatabaseHelper.Connect();
            
            PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, userId);
            statement.setString(2, seriesText);
            statement.setString(3, LocalDate.now().toString());
            
            statement.executeUpdate();
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //RETURNS USERS SESSIONS
    public List<Session> GetUserSessions(User currentUser, int userId) {
        
        List<Session> sessions = new ArrayList<>();
        String sql = "SELECT * FROM sessions WHERE user_id = ?";

        try (Connection conn = DatabaseHelper.Connect();
                
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sessions.add(new Session(rs.getInt("id"), rs.getInt("user_id"), rs.getString("date"), rs.getString("series")));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sessions;
    }

    public static void main(String[] args) { }
}
