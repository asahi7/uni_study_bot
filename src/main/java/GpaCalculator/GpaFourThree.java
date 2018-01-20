package GpaCalculator;

import java.util.List;
import MakeSchedule.*;

public class GpaFourThree extends GpaCalculator
{
    public GpaFourThree() {
        super();
    }
    
    public GpaFourThree(List<Course> courses)
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
                sum += 4.3 * credits;
                break;
            case "A":
                sum += 4.0 * credits;
                break;
            case "A-":
                sum += 3.7 * credits;
                break;
            case "B+":
                sum += 3.3 * credits;
                break;
            case "B":
                sum += 3.0 * credits;
                break;
            case "B-":
                sum += 2.7 * credits;
                break;
            case "C+":
                sum += 2.3 * credits;
                break;
            case "C":
                sum += 2.0 * credits;
                break;
            case "C-":
                sum += 1.7 * credits;
                break;
            case "D+":
                sum += 1.3 * credits;
                break;
            case "D":
                sum += 1.0 * credits;
                break;
            case "D-":
                sum += 0.7 * credits;
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
