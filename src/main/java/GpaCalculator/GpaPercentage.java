package GpaCalculator;

import java.util.List;
import MakeSchedule.*;

public class GpaPercentage extends GpaCalculator
{
    public GpaPercentage() {
        
    }
    
    public GpaPercentage(List<Course> courses)
    {
        super(courses);
    }
    
    public double calculate()
    {
        double sum = 0.0;
        int sumCredits = 0; 
        for(int i = 0; i < courses.size(); i++) {
            String letter = courses.get(i).getLetter();
            int credits = courses.get(i).getCredits();
            switch(letter) {
            case "A+":
                sum += 100.0 * credits;
                break;
            case "A":
                sum += 100.0 * credits;
                break;
            case "A-":
                sum += 93.0 * credits;
                break;
            case "B+":
                sum += 89.0 * credits;
                break;
            case "B":
                sum += 86.0 * credits;
                break;
            case "B-":
                sum += 83.0 * credits;
                break;
            case "C+":
                sum += 79.0 * credits;
                break;
            case "C":
                sum += 76.0 * credits;
                break;
            case "C-":
                sum += 73.0 * credits;
                break;
            case "D+":
                sum += 69.0 * credits;
                break;
            case "D":
                sum += 66.0 * credits;
                break;
            case "D-":
                sum += 63.0 * credits;
                break;
            case "F":
                sum += 0.0 * credits;
                break;
            default:
                sum += 0.0 * credits;
                break;
            }
            sumCredits += credits;
        }
        return roundToNearestTwo(sum / sumCredits);
    }
}
