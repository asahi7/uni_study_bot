public class CreateTablesStrings
{
    public final static String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users ("
            + "user_id INTEGER AUTO_INCREMENT PRIMARY KEY,"
            + "username VARCHAR(255) NOT NULL,"
            + "last_activity TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
            + "reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + "CONSTRAINT constr1 UNIQUE(username))";
}