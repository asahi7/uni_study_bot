package Objects;

import java.sql.Timestamp;
import java.util.Map;

public class Exam
{
    private int userId;
    private int examId;
    private String name;
    private Timestamp date;
    private int totalPrepLevel;
    Map<String, Integer> coursePrep;
    
    public Exam() {}
    public Map<String, Integer> getCoursePrep() {
        return coursePrep;
    }
    public void setCoursePrep(Map<String, Integer> coursePrep) {
        this.coursePrep = coursePrep;
    }
    public int getUserId()
    {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    public int getExamId()
    {
        return examId;
    }
    public void setExamId(int examId)
    {
        this.examId = examId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Timestamp getDate()
    {
        return date;
    }
    public void setDate(Timestamp date)
    {
        this.date = date;
    }
    public int getTotalPrepLevel()
    {
        return totalPrepLevel;
    }
    public void setTotalPrepLevel(int totalPrepLevel)
    {
        this.totalPrepLevel = totalPrepLevel;
    }
}
