package Utilities;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import java.util.List;
import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import static Utilities.TextOnButtons.*;

public class Keyboards
{
    public static ReplyKeyboardMarkup getExamsMenuKeyboard() {
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(addExamButton());//"/add_exam"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(viewExamsButton());//"/view_exams"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(deleteExamButton());//"/delete_exam"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(clearExamDataButton());//"/clear_exam_data"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(cancelButton());//"/cancel"
        keyboardRows.add(keyboardRow);
        replyMarkup.setKeyboard(keyboardRows);
        return replyMarkup;
    }

    public static ReplyKeyboardMarkup getAddTimeKeyboard(String courseName) {
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("/add_time " + courseName);//"/add_time " + courseName
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(cancelButton());//"/cancel"
        keyboardRows.add(keyboardRow);
        replyMarkup.setKeyboard(keyboardRows);
        return replyMarkup;
    }

    public static ReplyKeyboardMarkup getCalculateGpaKeyboard() {
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(countGpaNewButton());//"/count_gpa_new"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(countGpaCurrentButton());//"/count_gpa_current"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(seeGpaPreviousButton());//"/see_gpa_previous"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(deleteGpaSetButton());//"/delete_gpa_set"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(clearGpaDataButton());//"/clear_gpa_data"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(cancelButton());//"/cancel"
        keyboardRows.add(keyboardRow);
        replyMarkup.setKeyboard(keyboardRows);
        return replyMarkup;
    }

    public static ReplyKeyboardMarkup getGpaScaleKeyboard() {
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(GpaFourButton());//"4.0"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(GpaFourPointThreeButton());//"4.3"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(GpaOneHundredButton());//"100%"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(cancelButton());//"/cancel"
        keyboardRows.add(keyboardRow);
        replyMarkup.setKeyboard(keyboardRows);
        return replyMarkup;
    }

    public static ReplyKeyboardMarkup getCourseSettingsKeyboard() {
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(addCourseButton());//"/add_course" no addCourseButton()
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(deleteCourseButton());//"/delete_course"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(viewCoursesButton());//"/view_courses"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(cancelButton());//"/cancel"
        keyboardRows.add(keyboardRow);
        replyMarkup.setKeyboard(keyboardRows);
        return replyMarkup;
    }

    public static ReplyKeyboardMarkup getMenuKeyboard() {
    	KeyboardRow keyboardRow;
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRow = new KeyboardRow();
        keyboardRow.add(viewCoursesButton());//"/view_courses"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(generateNewScheduleButton());//"/generate_new_schedule"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(calculateGpaButton());//"/calculate_gpa"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(examsMenuButton());//"/exams_menu"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(timetableButton());//"/timetable" timetableButton()
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(courseSettingsButton());//"/course_settings"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(addSuggestionButton());//"/add_suggestion"
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(aboutButton());//"/about"
        keyboardRows.add(keyboardRow);
        replyMarkup.setKeyboard(keyboardRows);
        return replyMarkup;
    }

}