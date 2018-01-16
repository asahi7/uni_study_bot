package MakeSchedule;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseConverter {
    public Course convertToCourse(String record){
        return stringToCourse(record );
    }

    public String convertFromCourse(Course course, int option){
        return courseToString(course, option);
    }

    public ArrayList<Course> convertToCourses(String record){
        ArrayList<Course> courses = new ArrayList<>();
        String delims = "[\n]";
        String[] tokens = record.split(delims);
        for(int i = 0; i<tokens.length;i++){
            Course newcourse = stringToCourse(tokens[i]);
            courses.add(newcourse);
        }
        return courses;
    }

    public void showCourses(ArrayList<Course> courses){
        for(int i=0; i<courses.size(); i++){
            System.out.println(courseToString(courses.get(i),0));
        }
    }



    public Course stringToCourse(String course){
        //Math:(Monday 10:30-12:10, Tuesday 20:30 - 22:30) (Sunday 18:30 - 22:30)
        String delims = "[:]+";
        String[] tokens = course.split(delims,2);

        String courseName = tokens[0];
        ArrayList<TimeSlotOptions> courseTime= new ArrayList<>();

        String timeSlotDelims = "[()]+";
        String[] timeSlotsOptions = tokens[1].split(timeSlotDelims);

        for(int i = 0; i<timeSlotsOptions.length; i++){
            TimeSlotOptions timeSlotOptions = stringToTimeSlotOption(timeSlotsOptions[i]);
            if(timeSlotOptions.getTimeSlots().size()>0){
                courseTime.add(stringToTimeSlotOption(timeSlotsOptions[i]));
            }
        }
        return new Course(courseName,courseTime);
    }

    public String courseToString(Course course,int timeSlotOption){
        StringBuilder str = new StringBuilder(course.getCourseName());
        str.append(":");
        str.append(timeSloteOptionToString(course.getCourseTime().get(timeSlotOption)));
        return str.toString();
    }

    public String timeSloteOptionToString(TimeSlotOptions timeSlots){
        StringBuilder time = new StringBuilder("(");
        for(String key: timeSlots.getTimeSlots().keySet()){
            for(int i=0; i < timeSlots.getTimeSlots().get(key).size(); i++){
                time.append(key +" " +timeToString(timeSlots.getTimeSlots().get(key).get(i)));
                time.append(",");
            }
        }
        time = new StringBuilder(time.substring(0, time.length()-1));
        time.append(")");
        return time.toString();
    }

    public TimeSlotOptions stringToTimeSlotOption(String timeSlot){
        HashMap<String, ArrayList<Time>> timeSlots =  new HashMap<>();
        // Monday 10:30-12:10, Tuesday 20:30 - 22:30 or ' '
        String delims = "[,]+";
        String[] timeRecords = timeSlot.split(delims);
        for(int i = 0; i<timeRecords.length; i++){
            if(timeRecords[i].length()==0){continue;}
            String recordDelims = "[ ]+";
            String[] record = timeRecords[i].split(recordDelims,2);
            String key = record[0];
            Time value = stringToTime(record[1]);

            if(!timeSlots.containsKey(key)){
                ArrayList<Time> times = new ArrayList<>();
                times.add(value);
                timeSlots.put(key, times);
            }
            else{
                timeSlots.get(key).add(value);
            }

        }
        return new TimeSlotOptions(timeSlots);
    }

    public Time stringToTime(String t){
        String delims = "[ :-]+";
        String[] tokens = t.split(delims);

        int startTime = getMinutes(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
        int endTime = getMinutes(Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
        return new Time(startTime, endTime);

    }

    public String timeToString(Time time){
        String  startHour = getHour(time.getStartTime());
        String  startMinute = getMinute(time.getStartTime());
        String  endHour = getHour(time.getEndTime());
        String  endMinute = getMinute(time.getEndTime());
        return (new StringBuilder(startHour + ":" + startMinute + " - " + endHour + ":" + endMinute).toString());
    }

    private int getMinutes(int hour, int minutes){
        return hour*60 + minutes;
    }

    private String getHour(int time){
        int hour = time/60;
        return (hour==0?"00":Integer.toString(hour));
    }

    private String getMinute(int time){
        int minute = time%60;
        return (minute==0?"00":Integer.toString(minute));
    }

}
