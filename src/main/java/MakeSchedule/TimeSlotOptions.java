package MakeSchedule;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeSlotOptions {
    private HashMap<String, ArrayList<Time>> timeSlots;

    TimeSlotOptions(){
        this.timeSlots = new HashMap<>();
    }

    public TimeSlotOptions(HashMap<String, ArrayList<Time>> timeSlots){
        this.timeSlots = timeSlots;
    }

    public TimeSlotOptions(String timeSlot){
        HashMap<String, ArrayList<Time>> timeSlots =  new HashMap<>();
        String delims = "[,]+";
        String[] timeRecords = timeSlot.split(delims);
        for(int i = 0; i<timeRecords.length; i++){
            String record = timeRecords[i];
            String timeRecord ="";
            String day = "";
            Time time = new Time();
            if(record.length()>9) {
                Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]");
                Matcher matcher = pattern.matcher(record);
                if (matcher.find()) {
                    timeRecord = matcher.group();
                    day = record.substring(0, record.length() - timeRecord.length());
                    time = new Time(timeRecord);
                    if (!timeSlots.containsKey(day)) {
                        ArrayList<Time> times = new ArrayList<>();
                        times.add(time);
                        timeSlots.put(day, times);
                    } else {
                        timeSlots.get(day).add(time);
                    }
                }
            }
        }
        this.timeSlots = timeSlots;
    }

    public String getTimeSlotOptionString(){
        TimeSlotOptions timeSlots = this;
        StringBuilder time = new StringBuilder("(");
        for(String key: timeSlots.getTimeSlots().keySet()){
            for(int i=0; i < timeSlots.getTimeSlots().get(key).size(); i++){
                time.append(key +" " +(timeSlots.getTimeSlots().get(key).get(i)).getTimeString());
                time.append(",");
            }
        }
        time = new StringBuilder(time.substring(0, time.length()-1));
        time.append(")");
        return time.toString();
    }

    public void setTimeSlots(HashMap<String, ArrayList<Time>> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public HashMap<String, ArrayList<Time>> getTimeSlots() {
        return timeSlots;
    }


}
