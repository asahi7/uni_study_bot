package MakeSchedule;

import java.util.HashMap;
import java.util.ArrayList;

public class TimeSlotOptions {
    private HashMap<String, ArrayList<Time>> timeSlots;

    TimeSlotOptions(){
        this.timeSlots = new HashMap<>();
    }

    public TimeSlotOptions(HashMap<String, ArrayList<Time>> timeSlots){
        this.timeSlots = timeSlots;
    }

    public TimeSlotOptions(String day, Time time){
        this.timeSlots = new HashMap<>();
        ArrayList<Time> times = new ArrayList<>();
        times.add(time);
        this.timeSlots.put(day,times);
    }

    public void addTimeToTimeSlot(String day, Time time){
        if(!timeSlots.containsKey(day)){
            ArrayList<Time> times = new ArrayList<>();
            times.add(time);
            timeSlots.put(day, times);
        }
        else{
            timeSlots.get(day).add(time);
        }
    }

    public void setTimeSlots(HashMap<String, ArrayList<Time>> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public HashMap<String, ArrayList<Time>> getTimeSlots() {
        return timeSlots;
    }


}
