package GpaCalculator;
import java.util.*;
import MakeSchedule.*;

public abstract class GpaCalculator
{
    protected List<Course> courses;
    
    public GpaCalculator() {
        
    }
    
    public GpaCalculator(List<Course> courses)
    {
        this.courses = courses;
    }
    
    protected double roundToNearestTwo(double value) {
        return (double)Math.round(value * 100d) / 100d;
    }
    
    public abstract double calculate();
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
