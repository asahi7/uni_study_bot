import java.sql.*;
import java.util.logging.*;

public class Database
{
    private Logger logger = Logger.getLogger(Database.class.getName());
    private static volatile Connection connection;
    private static volatile Database instance;
    
    private Database() {
        try {
            connection = DriverManager.getConnection(Consts.LINK_DB, Consts.USER_DB, Consts.PASSWORD_DB);
        } catch(SQLException e) {
            logger.log(Level.SEVERE, "Connection to the Database was not successful", e);
        }
        createInitialTables();
    }
    
    /* Singleton */
    public static Database getInstance() {
        Database localInstance = instance;
        if(localInstance == null) {
            synchronized (Database.class) {
                localInstance = instance;
                if(localInstance == null) {
                    instance = localInstance = new Database();
                }
            }
        }
        return instance;
    }
    
    private void createInitialTables() {
        try {
            Statement statement = connection.createStatement();
            logger.log(Level.INFO, "Creating Database (if not already) table users");
            statement.executeUpdate(CreateTablesStrings.CREATE_USERS_TABLE);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Creation of table users was not successful", e);
        }
    }
    
    public void addUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users(username) VALUES(?) ON DUPLICATE KEY UPDATE last_activity=now()");
            statement.setString(1, username);
            logger.log(Level.INFO, "Inserting/Updating user to the users table");
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while adding to users table", e);
        }
    }
}
