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

    public Course (String course){

        StringBuilder record = new StringBuilder();
        String records[] = course.split("[ ]+");
        for(int i=0;i< records.length;i++){
            record.append(records[i]);
        }
        course = record.toString();
        String delims = "[:]+";
        String[] tokens = course.split(delims,2);
        String courseName = tokens[0];
        ArrayList<TimeSlotOptions> courseTime= new ArrayList<>();

        String timeSlotDelims = "[()]+";
        String[] timeSlotsOptions = tokens[1].split(timeSlotDelims);

        for(int i = 0; i<timeSlotsOptions.length; i++){
            TimeSlotOptions timeSlotOptions = new TimeSlotOptions(timeSlotsOptions[i]);
            if(timeSlotOptions.getTimeSlots().size()>0){
                courseTime.add(new TimeSlotOptions(timeSlotsOptions[i]));
            }
        }

        this.courseName = courseName;
        this.courseTime = courseTime;
    }

    public String courseToString(int timeSlotOption){
        Course course = this;
        StringBuilder str = new StringBuilder(course.getCourseName());
        str.append(":");
        str.append((course.getCourseTime().get(timeSlotOption).getTimeSlotOptionString()));
        return str.toString();
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
