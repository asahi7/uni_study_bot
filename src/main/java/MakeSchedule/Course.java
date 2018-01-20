package MakeSchedule;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private int credits;
    private String letter;
    private List<TimeSlotOptions> courseTime;

    public Course(){
        this.courseTime = new ArrayList<>();
    }
    
    public Course(String courseName, int credits){
        this.courseName = courseName;
        this.credits = credits;
        this.courseTime = new ArrayList<>();
    }
    
    public Course(String courseName, int credits, String letter){
        this.courseName = courseName;
        this.credits = credits;
        this.letter = letter;
        this.courseTime = new ArrayList<>();
    }
    
    public Course(String courseName, ArrayList<TimeSlotOptions> timeSlotOptions){
        this.courseName = courseName;
        this.courseTime = timeSlotOptions;
    }
    
    public void addTimeSlotOption(TimeSlotOptions timeSlotOption) {
        courseTime.add(timeSlotOption);
    }

    public void addToTimeSlotOption(int i, String day, Time time) {
        this.courseTime.get(i).addTimeToTimeSlot(day, time);
    }

    public List<TimeSlotOptions> getCourseTime() {
        return courseTime;
    }

    public String getCourseName() {
        return courseName;
    }
    
    public int getCredits() {
        return credits;
    }
    
    public String getLetter() {
        return letter;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseTime(ArrayList<TimeSlotOptions> courseTime) {
        this.courseTime = courseTime;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public void setLetter(String letter) {
        this.letter = letter;
    }
}
