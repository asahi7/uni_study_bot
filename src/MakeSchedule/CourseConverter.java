package MakeSchedule;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseConverter {
    Course convertToCourse(String record){
        return stringToCourse(record );
    }

    String convertFromCourse(Course course, int option){
        return courseToString(course, option);
    }

    ArrayList<Course> convertToCourses(String record){
        ArrayList<Course> courses = new ArrayList<>();
        return courses;
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
        /*//TODO TEST START
        System.out.println("####");
        for(int i=0; i< courseTime.size();i++){
            System.out.println("*");
            for(ArrayList<Time> key: courseTime.get(i).getTimeSlots().values()){
                for(int j=0;j<key.size(); j++){
                    System.out.println(timeToString(key.get(j)));
                }
                //System.out.println(timeToString(key));
            }
            System.out.println("*");

        }
        System.out.println("####");
        //TODO TEST START
        */
        return new Course(courseName,courseTime);
    }


    public String courseToString(Course course,int timeSlotOption){
        String str = course.getCourseName();
        str+=": ";
        str+=timeSloteOptionToString(course.getCourseTime().get(timeSlotOption));
        return str;
    }

    //TODO(Me) timseslots is empty
    public String timeSloteOptionToString(TimeSlotOptions timeSlots){
        //System.out.println("Cled");
        String time = "";
        for(String key: timeSlots.getTimeSlots().keySet()){
            time+=key+" ";
            for(int i=0; i < timeSlots.getTimeSlots().get(key).size(); i++){
                time+= timeToString(timeSlots.getTimeSlots().get(key).get(i));
                time+=(i<timeSlots.getTimeSlots().get(key).size()-1?", ":" ");
            }
        }
       // System.out.println(timeSlots.getTimeSlots().size());
        return time;
    }

    //Done?
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
        //System.out.println((new TimeSlotOptions(timeSlots)).getTimeSlots().size());
        return new TimeSlotOptions(timeSlots);
    }
//Done

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
        return (startHour + ":" + startMinute + " - " + endHour + ":" + endMinute);
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
