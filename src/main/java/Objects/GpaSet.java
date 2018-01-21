package Objects;

import java.util.List;
import MakeSchedule.Course;
import java.sql.Timestamp;

public class GpaSet
{
    private double gpa;
    private int setId;
    private List<Course> courses;
    private Timestamp timestamp;
    private String gpaScale;
    
    public GpaSet() {
        
    }
    
    public GpaSet(double gpa, int setId, List<Course> courses)
    {
        super();
        this.gpa = gpa;
        this.setId = setId;
        this.courses = courses;
    }
    
    public double getGpa()
    {
        return gpa;
    }
    
    public void setGpa(double gpa)
    {
        this.gpa = gpa;
    }
    
    public int getSetId()
    {
        return setId;
    }
    
    public void setSetId(int setId)
    {
        this.setId = setId;
    }
    
    public List<Course> getCourses()
    {
        return courses;
    }
    
    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getGpaScale()
    {
        return gpaScale;
    }

    public void setGpaScale(String gpaScale)
    {
        this.gpaScale = gpaScale;
    }
    
}
