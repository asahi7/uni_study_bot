package MakeSchedule;

import java.util.*;
import javafx.util.Pair;

public class Scheduler {

    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> courseCombinations = new ArrayList<>();
    private static ArrayList<Pair<Pair<Integer, Integer>, BitSet>> Combination = new ArrayList<>();
    private static ArrayList<ArrayList<Pair<Pair<Integer, Integer>, BitSet>>> Combinations = new ArrayList<>();
    private static HashMap<Pair<Integer, Integer>, BitSet > bitsets = new HashMap<>();

    private static int countMinutes(String day){
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


    public static ArrayList<ArrayList<Pair<Integer, Integer> >> getCourseCombinations(ArrayList<Course> newCourses) {
        setCourses(newCourses);
        combineCourses(0);
        retrieveCourseCombinations();
        return courseCombinations;
    }

    private static void setCourses(ArrayList<Course> newCourses){
        Combinations.clear();
        Combination.clear();
        courseCombinations.clear();
        bitsets.clear();
        courses.clear();
        courses = newCourses;
    }

    private static void combineCourses(int courseIndex){
        if(courseIndex == courses.size()){
            addCombinationToSchedule();
        }
        else{
            for(int optionIndex = 0; optionIndex < courses.get(courseIndex).getCourseTime().size(); optionIndex++){
                Pair<Pair<Integer, Integer>, BitSet> currentTimeSlot = getTimeSlotBitSet(new Pair(courseIndex, optionIndex));
                if(canAddTimeSlot(currentTimeSlot)){
                    Combination.add(currentTimeSlot);
                    combineCourses(courseIndex+1);
                    Combination.remove(currentTimeSlot);
                }
            }
        }
    }


    private static void retrieveCourseCombinations(){
        for(int i = 0; i<Combinations.size(); i++){
            ArrayList<Pair<Integer, Integer>> schedule = new ArrayList<>();
            for (int j = 0; j<Combinations.get(i).size();j++){
                schedule.add(Combinations.get(i).get(j).getKey());
              //  System.out.println(Combinations.get(i).get(j).getKey().getKey()+" "+ Combinations.get(i).get(j).getKey().getValue());
            }
            courseCombinations.add(schedule);
        }

    }


    private static Pair<Pair<Integer, Integer>, BitSet> getTimeSlotBitSet(Pair<Integer, Integer> courseAndSection){

        String name = courses.get(courseAndSection.getKey()).getCourseName();
        if(bitsets.containsKey(courseAndSection)){
            return new Pair(courseAndSection, bitsets.get(courseAndSection));
        }

        BitSet timeBitSet= new BitSet();
        ArrayList<TimeSlotOptions> courseTime = (courses.get(courseAndSection.getKey()).getCourseTime());
        HashMap<String, ArrayList<Time>> timeSlots = courseTime.get(courseAndSection.getValue()).getTimeSlots();

        for(Map.Entry<String, ArrayList<Time>> entry: timeSlots.entrySet()){

            int minutesInADay =  countMinutes(entry.getKey());
            for(int i = 0; i < entry.getValue().size(); i++){
                int startTimeInMinutes = minutesInADay + timeSlots.get(entry.getKey()).get(i).getStartTime();
                int endTimeInMinutes = minutesInADay + timeSlots.get(entry.getKey()).get(i).getEndTime();
               // System.out.println(startTimeInMinutes+"->"+endTimeInMinutes+" at "+entry.getKey()+" ("+courseAndSection.getKey()+","+courseAndSection.getValue()+")");
                for(int j = startTimeInMinutes ; j <endTimeInMinutes && j<20000; j++){
                    timeBitSet.set(j);
                }
            }
        }

        bitsets.putIfAbsent(courseAndSection,timeBitSet);
        return new Pair(courseAndSection,timeBitSet);

    }

    private static boolean canAddTimeSlot(Pair<Pair<Integer, Integer>, BitSet>  newTimeSlot){
        for(int i = 0; i < Combination.size(); i++){
            if(Combination.get(i).getValue().intersects(newTimeSlot.getValue())) {
                return false;
            }
        }
        return true;
    }

    private static void printCombination(ArrayList<Pair<Pair<Integer, Integer>, BitSet>> Combination){
        for(int i = 0; i< Combination.size(); i++){
            System.out.println("CourseId:" + Combination.get(i).getKey().getKey()+" SectionId: "+ Combination.get(i).getKey().getValue());
        }
    }

    private static void printCombinations(){
        for(int i = 0; i<Combinations.size(); i++){
            printCombination(Combinations.get(i));
        }
    }

    private static void addCombinationToSchedule(){
        ArrayList<Pair<Pair<Integer, Integer>, BitSet>> newCombination = new ArrayList<>(Combination);
        Combinations.add(newCombination);
    }


}
