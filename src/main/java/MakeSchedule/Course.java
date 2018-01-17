package MakeSchedule;

import java.util.ArrayList;

public class Course {
    private String courseName;
    private ArrayList<TimeSlotOptions> courseTime;

    private Course(){
        this.courseTime = new ArrayList<>();
    }

    public Course(String courseName, ArrayList<TimeSlotOptions> timeSlotOptions){
        this.courseName = courseName;
        this.courseTime = timeSlotOptions;
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

    public ArrayList<TimeSlotOptions> getCourseTime() {
        return courseTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseTime(ArrayList<TimeSlotOptions> courseTime) {
        this.courseTime = courseTime;
    }
}
