public class CreateTablesStrings
{
    public final static String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users ("
            + "user_id INTEGER PRIMARY KEY,"
            + "last_activity TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
            + "reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + "CONSTRAINT constr1 UNIQUE(user_id))";
    public final static String CREATE_COURSES_TABLE = "CREATE TABLE IF NOT EXISTS courses ("
            + "name VARCHAR(255) NOT NULL,"
            + "credits INTEGER NOT NULL DEFAULT 0,"
            + "user_id INTEGER NOT NULL,"
            + "FOREIGN KEY(user_id) REFERENCES users(user_id),"
            + "letter VARCHAR(3),"
            + "room VARCHAR(255),"
            + "professor VARCHAR(255),"
            + "four_zero DECIMAL(3,2),"
            + "four_three DECIMAL(3,2),"
            + "percentage INTEGER(3),"
            + "CONSTRAINT constr2 PRIMARY KEY(name,user_id))";
    public final static String CREATE_TIMES_TABLE = "CREATE TABLE IF NOT EXISTS times ("
            + "section_id INTEGER AUTO_INCREMENT PRIMARY KEY,"
            + "name VARCHAR(255) NOT NULL,"
            + "user_id INTEGER NOT NULL,"
            + "FOREIGN KEY(name,user_id) REFERENCES courses(name,user_id) ON DELETE CASCADE,"
            + "day VARCHAR(20) NOT NULL,"
            + "start_time TIME NOT NULL,"
            + "end_time TIME NOT NULL,"
            + "CONSTRAINT constr3 UNIQUE(name,user_id,day,start_time,end_time))";
    public final static String CREATE_STATES_TABLE = "CREATE TABLE IF NOT EXISTS states ("
            + "user_id INTEGER NOT NULL,"
            + "chat_id BIGINT NOT NULL,"
            + "FOREIGN KEY(user_id) REFERENCES users(user_id),"
            + "state INTEGER NOT NULL DEFAULT 0,"
            + "CONSTRAINT constr3 PRIMARY KEY(user_id,chat_id))";
}