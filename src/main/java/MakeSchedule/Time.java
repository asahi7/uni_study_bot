package MakeSchedule;

public class Time {
    private int startTime;
    private int endTime;

    public Time() {}

    public Time(int startTime, int endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Time (String t){
        String delims = "[ :-]+";
        String[] tokens = t.split(delims);
        int startTime = getMinutes(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
        int endTime = getMinutes(Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTimeString(){
        Time time = this;
        String  startHour = getHour(time.getStartTime());
        String  startMinute = getMinute(time.getStartTime());
        String  endHour = getHour(time.getEndTime());
        String  endMinute = getMinute(time.getEndTime());
        return (startHour + ":" + startMinute + " - " + endHour + ":" + endMinute);
    }

    private int getMinutes(int hour, int minutes){
        return (hour*60 + minutes);
    }

    private String getHour(int time){
        int hour = time/60;
        return (hour == 0? "00" : Integer.toString(hour));
    }

    private String getMinute(int time){
        int minute = time%60;
        return (minute == 0? "00" : Integer.toString(minute));
    }

    public int getStartTime() {
        return this.startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int endTime){
        this.endTime = endTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

}
