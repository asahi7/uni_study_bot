package MakeSchedule;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

public class Scheduler {

    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Pair<Course, Integer> > courseCombination = new ArrayList<>();
    private static ArrayList<ArrayList<Pair<Course, Integer> > > courseCombinations = new ArrayList<>();

    public static String doWork(String input){
    	if(checkInput(input)){
    		ArrayList<Course> newCourses = convertToCourses(input);
    		setCourses(newCourses);
    		combineCourses(0);
        	if(showSchedule().length() >0){
        		return showSchedule();
        	}
        }
       return"No schedule for these courses can be constructed. Delete or change a course";
    }

    public static ArrayList<Course> convertToCourses(String record){

        StringBuilder newrecord = new StringBuilder();
        String records[] = record.split("[ ]+");
        for(int i=0;i< records.length;i++){
            newrecord.append(records[i]);
        }
        record = newrecord.toString();

        ArrayList<Course> courses = new ArrayList<>();
        String delims = "[\n]";
        String[] tokens = record.split(delims);
        for(int i = 0; i<tokens.length;i++){
            Course newcourse = new Course(tokens[i]);
            courses.add(newcourse);
        }
        return courses;
    }

    private static void setCourses(ArrayList<Course> newCourses){
    	courseCombination.clear();
    	courseCombinations.clear();
        courses.clear();
        courses = newCourses;
    }

    private static void combineCourses(int courseIndex){
        if(courseIndex == courses.size()){
        	addCombinationToSchedule();
        }
        else{
            for(int optionIndex = 0; optionIndex < courses.get(courseIndex).getCourseTime().size(); optionIndex++){
            	Course course = courses.get(courseIndex);
                if(canAddTimeSlot(course,optionIndex)){
                    courseCombination.add(Pair.of(course,optionIndex));
                    combineCourses(courseIndex+1);
                    courseCombination.remove(Pair.of(course,optionIndex));
                }
            }
        }
    }

    private static void addCombinationToSchedule(){
    	ArrayList<Pair<Course, Integer> > newCourseCombination = new ArrayList<>(courseCombination);
    	 courseCombinations.add(newCourseCombination);

    }

    private static boolean canAddTimeSlot(Course course,int optionIndex){
    	boolean ans = true;
    	HashMap<String, ArrayList<Time>> timeSlots = course.getCourseTime().get(optionIndex).getTimeSlots();
    	for(int c = 0; c<courseCombination.size(); c++){
    		Course comparedCourse = courseCombination.get(c).getKey();
    		int comparedCourseOption =  courseCombination.get(c).getValue();
    		for(String day: timeSlots.keySet()){
    			if(comparedCourse.getCourseTime().get(comparedCourseOption).getTimeSlots().containsKey(day) ){
    				 ArrayList<Time> courseTimes = course.getCourseTime().get(optionIndex).getTimeSlots().get(day);
    				 ArrayList<Time> comparedCourseTimes = comparedCourse.getCourseTime().get(comparedCourseOption).getTimeSlots().get(day);
    				 for(int timeIndex = 0; timeIndex < courseTimes.size(); timeIndex++){
    					 Time courseTime = courseTimes.get(timeIndex);

    					 if(!(courseTime.getStartTime()<=courseTime.getEndTime())){
    						 return false;
    					 }

    					 for(int comparedTimeIndex = 0; comparedTimeIndex < comparedCourseTimes.size(); comparedTimeIndex++){
        					 Time comparedCourseTime = comparedCourseTimes.get(comparedTimeIndex);
        					 if(comparedCourseTime.getStartTime()>=courseTime.getEndTime()){}
        					 else if(comparedCourseTime.getEndTime()<=courseTime.getStartTime()){}
        					 else{
        						 return false;
        					 }
        				 }
    				 }
    			}
    		}

    	}

    	return ans;
    }

    private static String showSchedule(){
        StringBuilder mySchedule = new StringBuilder();
        for(int i = 0; i<courseCombinations.size();i++){
            mySchedule.append("~ "+"Combination "+(i+1)+" ~"+"\n");
            for(int j = 0; j<courseCombinations.get(i).size();j++){
                Course course = courseCombinations.get(i).get(j).getKey();
                int optionIndex = courseCombinations.get(i).get(j).getValue();
                mySchedule.append((course.courseToString(optionIndex))+"\n");
            }
            mySchedule.append("\n");
        }
        return mySchedule.toString();
    }

    public static boolean checkInput(String input){
    	/* Delete whitespaces */
    	StringBuilder record = new StringBuilder();
        String records[] = input.split("[ ]+");
        for(int i=0;i< records.length;i++){
            record.append(records[i]);
        }
        input = record.toString();
        /* Delete whitespaces */

        /* Extract lines */
        String delim1 = "[\n]";
        String[] lines = input.split(delim1);
        boolean answer = true;
        /* Nothing in input */
        if(!(lines.length>0)){
    		return false;
    	}
        /* Nothing in input */
        /* Too many courses */
        if(lines.length>10){
        	return false;
        }
        /* Too many courses */
        /*//Extract lines */

        /* Extract Name and Timeslots */
        for(int l = 0; l < lines.length; l++){
        	String delim2 = "[:]";
            String[]  timeSlots = lines[l].split(delim2,2);
            /* Incorrect "name:timeslot" format */
            if(timeSlots.length != 2){
            	//System.out.println(" Incorrect name-timeslot format");
                return false;
            }
            /*//Incorrect "name:timeslot" format */

            /* Extract timeslots */
            String timeSlotDelims = "[()]+";
            String[] timeSlotsOptions = timeSlots[1].split(timeSlotDelims);
            /* no timeslots */
            if(!(timeSlotsOptions.length>0)){
            	//System.out.println("no timeslots");
            	return false;
            }
            /*//no timeslots */

            /* Too many timeSlotsOptions */
            if(timeSlotsOptions.length>6){
            	return false;
            }
            /* Too many timeSlotsOptions*/
            for(int j = 0; j< timeSlotsOptions.length; j++){
            	if(timeSlotsOptions[j].length()==0){
            		continue;
            	}
            	String delim3 = "[,]+";
                String[] timeRecords = timeSlotsOptions[j].split(delim3);
                /* no timeRecord is present in timeslot */
                if(!(timeRecords.length>0)){
                	//System.out.println(" no timeRecord is present in timeslot");
                    return false;
                 }
                /*//no timeRecord is present in timeslot */
                for(int k = 0; k<timeRecords.length; k++){
                	String time = timeRecords[k];
                	/* too short time record */
                	if(!(time.length()>9)) {
                		//System.out.println("  too short time record: "+time);
                		// shortest timeRecord is 10 characters long (a1:00-1:01)
                		 return false;
                	}
                	/*//too short time record */
                	Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]");
                    Matcher matcher = pattern.matcher(time);
                    /* Incorrect time format */
                    if (!(matcher.find())) {
                    	//System.out.println("Incorrect time format");
                    	return false;
                    }
                    /*//Incorrect time format */
                 }

            }
             /*//Extract timeslots */
        }
        /*//Extract Name and Timeslots */
        return true;
    }

}
