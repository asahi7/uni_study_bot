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
           // + "letter VARCHAR(3),"
            + "room VARCHAR(255),"
            + "professor VARCHAR(255),"
          //  + "four_zero DECIMAL(3,2)," // TODO delete this
          //  + "four_three DECIMAL(3,2),"
          //  + "percentage INTEGER(3),"
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
    public final static String CREATE_GPA_SETS_TABLE = "CREATE TABLE IF NOT EXISTS gpa_sets ("
            + "user_id INTEGER NOT NULL,"
            + "set_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "gpa DECIMAL(3,2) NOT NULL,"
            + "gpa_scale VARCHAR(5) NOT NULL,"
            + "created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE)";
    public final static String CREATE_GPA_SET_COURSES_TABLE = "CREATE TABLE IF NOT EXISTS gpa_set_courses ("
            + "set_id INTEGER NOT NULL,"
            + "name VARCHAR(255) NOT NULL,"
            + "credits INTEGER NOT NULL,"
            + "letter VARCHAR(3) NOT NULL,"
            + "FOREIGN KEY(set_id) REFERENCES gpa_sets(set_id) ON DELETE CASCADE)";
    public static final String CREATE_EXAMS_TABLE = "CREATE TABLE IF NOT EXISTS exams ("
            + "exam_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "name VARCHAR(255) NOT NULL,"
            + "user_id INTEGER NOT NULL,"
            + "FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,"
            + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," // TODO maybe it should be made changeable
            + "total_prep_level INTEGER DEFAULT 0)"; 
    public static final String CREATE_PREPARED_FOR_TABLE = "CREATE TABLE IF NOT EXISTS prepared_for ("
            + "exam_id INTEGER NOT NULL,"
            + "course_name VARCHAR(255) NOT NULL," // TODO name
            + "level INTEGER DEFAULT 0,"
            + "FOREIGN KEY(exam_id) REFERENCES exams(exam_id) ON DELETE CASCADE,"
            + "FOREIGN KEY(course_name) REFERENCES courses(name) ON DELETE CASCADE,"
            + "CONSTRAINT constr4 PRIMARY KEY(exam_id,course_name))"; 
    public static final String CREATE_SUGGESTIONS_TABLE = "CREATE TABLE IF NOT EXISTS suggestions ("
            + "suggestion_id INTEGER AUTO_INCREMENT PRIMARY KEY,"
            + "text TEXT NOT NULL,"
            + "user_id INTEGER NOT NULL,"
            + "FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,"
            + "post_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
}