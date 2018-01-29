package Utilities;



import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.vdurmont.emoji.EmojiParser;

public class TextOnButtons {

	/*
	 * getExamsMenuKeyboard()
	 *  "/add_exam" "/view_exams" "/delete_exam" "/clear_exam_data" "/cancel"
	 */

	public static String addExamButton(){
        return EmojiParser.parseToUnicode((":o:"+"Add Exam"));
    }

	public static String viewExamsButton(){
        return EmojiParser.parseToUnicode((":100:"+"View Exams"));
    }

	public static String deleteExamButton(){
        return EmojiParser.parseToUnicode((":x:"+"Delete Exam"));
    }

	public static String clearExamDataButton(){
        return EmojiParser.parseToUnicode((":heavy_check_mark:"+"Clear Exam Data"));
    }

	public static String cancelButton(){
        return EmojiParser.parseToUnicode((":arrow_upper_left:"+"Cancel"));
    }


	/*
	 * getAddTimeKeyboard()
	 *  "/add_time""/cancel"
	 */

	public static String addTimeButton(){
        return EmojiParser.parseToUnicode((":clock230:"+"Add Time"));
    }


	/*
	 * getCalculateGpaKeyboard()
	 * "/count_gpa_new" "/count_gpa_current" "/see_gpa_previous" "/delete_gpa_set" "/clear_gpa_data"
	 * "/cancel"
	 */

	public static String countGpaNewButton(){
        return EmojiParser.parseToUnicode((":chart_with_downwards_trend:"+"Count New GPA"));
    }

	public static String countGpaCurrentButton(){
        return EmojiParser.parseToUnicode((":chart_with_upwards_trend:"+"Count My Current GPA"));
    }

	public static String seeGpaPreviousButton(){
        return EmojiParser.parseToUnicode((":clipboard:"+"See Previous GPA"));
    }

	public static String deleteGpaSetButton(){
        return EmojiParser.parseToUnicode((":page_facing_up:"+"Delete GPA"));
    }

	public static String clearGpaDataButton(){
        return EmojiParser.parseToUnicode((":page_with_curl:"+"Clear GPA Data"));
    }

	/*
	 * getGpaScaleKeyboard()
	 * "4.0" "4.3" "100%" "/cancel"
	 *
	 */

	public static String GpaFourButton(){
        return EmojiParser.parseToUnicode((":female_student:"+"4.0 GPA"));
    }

	public static String GpaFourPointThreeButton(){
        return EmojiParser.parseToUnicode((":male_scientist:"+"4.3 GPA"));
    }

	public static String GpaOneHundredButton(){
        return EmojiParser.parseToUnicode((":male_teacher:"+"100%"));
    }

	/*
	 * getCourseSettingsKeyboard()
	 * "/add_course" "/delete_course" "/view_courses" "/cancel"
	 *
	 */

	public static String addCourseButton(){
        return EmojiParser.parseToUnicode((":male_technologist:"+"Add Course"));
    }

	public static String deleteCourseButton(){
        return EmojiParser.parseToUnicode((":female_technologist:"+"Delete Course"));
    }

	public static String viewCoursesButton(){
        return EmojiParser.parseToUnicode((":desktop_computer:"+"View Your Courses"));
    }

	/*
	 *  getMenuKeyboard()
	 *  "/course_settings" "/view_courses" "/generate_new_schedule" "/calculate_gpa" "/exams_menu" "/add_suggestion"
	 *
	 */

	public static String aboutButton(){
        return EmojiParser.parseToUnicode((":grey_question:"+"About this app"));
    }

	public static String addSuggestionButton(){
        return EmojiParser.parseToUnicode((":heart:"+"Your suggestions"));
    }

    public static String courseSettingsButton(){
        return EmojiParser.parseToUnicode((":gear:"+"Course Settings"));
    }

    public static String generateNewScheduleButton(){
        return EmojiParser.parseToUnicode((":spiral_note_pad:"+"Generate New Schedule"));
    }

	public static final String calculateGpaButton(){
        return EmojiParser.parseToUnicode((":100:"+"Calculate GPA"));
    }

	public static String examsMenuButton(){
        return EmojiParser.parseToUnicode((":open_book:"+"Exams"));
    }
}