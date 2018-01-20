package MakeSchedule;

import java.util.*;
import org.apache.commons.lang3.tuple.Pair;

public class Scheduler {

    // TODO : startTime>endTime case like saturday 23:30 - sunday 00:30

    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Pair<Integer, Integer>> combination = new ArrayList<>();
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> courseCombinations = new ArrayList<>();
    private static BitSet mainBitSet = new BitSet();

    private static int countMinutesInDay(String day){
        switch (day){
            case "Monday":
                return 0;
            case "Tuesday":
                return 1440;
            case "Wednesday":
                return 2880;
            case "Thursday":
                return 4320;
            case "Friday":
                return 5760;
            case "Saturday":
                return 7200;
            case "Sunday":
                return 8640;
        }
        return 0;
    }

    public static String doWork(String input){
        ArrayList<Course> newCourses = convertToCourses(input);
        setCourses(newCourses);
        combineCourses(0);
        return showSchedule();
    }

    public static ArrayList<Course> convertToCourses(String record){
        ArrayList<Course> courses = new ArrayList<>();
        String delims = "[\n]";
        String[] tokens = record.split(delims);
        for(int i = 0; i<tokens.length;i++){
            Course newcourse = new Course(tokens[i]);
            courses.add(newcourse);
        }
        return courses;
    }


    public static ArrayList<ArrayList<Pair<Integer, Integer> >> getCourseCombinationsList(ArrayList<Course> newCourses) {
        setCourses(newCourses);
        combineCourses(0);
        return courseCombinations;
    }

    private static void setCourses(ArrayList<Course> newCourses){
        combination.clear();
        courseCombinations.clear();
        mainBitSet.clear();
        courses.clear();
        courses = newCourses;
    }

    private static void combineCourses(int courseIndex){
        if(courseIndex == courses.size()){
            addCombinationToSchedule();
            mainBitSet.clear();
        }
        else{
            for(int optionIndex = 0; optionIndex < courses.get(courseIndex).getCourseTime().size(); optionIndex++){
                if(canAddTimeSlot(courseIndex,optionIndex )){
                    //combination.add(new Pair(courseIndex,optionIndex));
                    combination.add(Pair.of(courseIndex,optionIndex));
                    combineCourses(courseIndex+1);
                    //combination.remove(new Pair(courseIndex,optionIndex));
                    combination.remove(Pair.of(courseIndex,optionIndex));
                }
            }
        }
    }

    private static boolean canAddTimeSlot(int courseIndex, int optionIndex){
        List<TimeSlotOptions> courseTime = (courses.get(courseIndex).getCourseTime());
        HashMap<String, ArrayList<Time>> timeSlots = courseTime.get(optionIndex).getTimeSlots();

        for(String day: timeSlots.keySet()){
                if(!checkTimes(courseIndex, optionIndex, day)){
                    return false;
                }
        }

        return true;
    }

    private static int getStartTime(int courseIndex, int optionIndex, String day, int t){
        Time time = courses.get(courseIndex).getCourseTime().get(optionIndex).getTimeSlots().get(day).get(t);
        int startTime = countMinutesInDay(day) + time.getStartTime();
        return startTime;
    }

    private static int getEndTime(int courseIndex, int optionIndex, String day, int t){
        Time time = courses.get(courseIndex).getCourseTime().get(optionIndex).getTimeSlots().get(day).get(t);
        int endTime = countMinutesInDay(day) + time.getEndTime();
        return endTime;
    }

    private static boolean checkTimes(int courseIndex, int optionIndex, String day){
        ArrayList<Time> times = courses.get(courseIndex).getCourseTime().get(optionIndex).getTimeSlots().get(day);
        boolean checkTime = true;
        int backTime = 0, backMinute = 1;
        for(int time = 0; time < times.size(); time++){
            if(checkTime){
                int startTime = getStartTime(courseIndex, optionIndex, day, time);
                int endTime = getEndTime(courseIndex, optionIndex, day, time);
                for(int minute = startTime; minute < endTime; minute++){
                    if(mainBitSet.get(minute) == true){
                        backTime = time;
                        backMinute = minute - 1;
                        checkTime = false;
                        break;
                    }
                    mainBitSet.set(minute);
                }
            }
        }
        for(int time = backTime; time >= 0; time--){
            for(int minute = backMinute; minute >= 0; minute--){
                mainBitSet.clear(minute);
            }
        }
        return checkTime;
    }

    private static void addCombinationToSchedule(){
        ArrayList<Pair<Integer, Integer>> newCombination = new ArrayList<>(combination);
        courseCombinations .add(newCombination);
    }

    private static String showSchedule(){
        StringBuilder mySchedule = new StringBuilder();

        for(int i = 0; i<courseCombinations.size();i++){
            mySchedule.append("~"+"\n");
            for(int j = 0; j<courseCombinations.get(i).size();j++){
                int courseIndex = courseCombinations.get(i).get(j).getKey();
                int optionIndex = courseCombinations.get(i).get(j).getValue();
                mySchedule.append((courses.get(courseIndex).courseToString(optionIndex))+"\n");
            }
            mySchedule.append("\n");
        }

        return mySchedule.toString();
    }
}
