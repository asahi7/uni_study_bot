import org.apache.commons.io.monitor.FileAlterationListener;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import GpaCalculator.*;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;

import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.Data;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;

import MakeSchedule.Course;
import MakeSchedule.Scheduler;
import Objects.Exam;
import Objects.GpaSet;
import Objects.Keyboards;

import java.util.*;
import java.util.Map.Entry;

import static Objects.Keyboards.*;

public class UniStudyBot extends TelegramLongPollingBot
{
    private Logger logger = Logger.getLogger(UniStudyBot.class.getName());

    private static final int START_STATE = 0;
    private static final int MAIN_MENU = 1;
    // Course Settings BEGIN
    private static final int COURSE_SETTINGS = 2;
    private static final int ADDING_COURSE = 3;
    private static final int ADD_TIME = 4;
    private static final int ADDING_TIME = 5;
    private static final int DELETING_COURSE = 7;
    // Course Settings END
    private static final int GENERATING_NEW_SCHEDULE = 8;
    private static final int CALCULATE_GPA = 6;
    private static final int COUNT_GPA_NEW = 9;
    private static final int COUNT_GPA_CURRENT = 10;
    private static final int COUNTING_GPA_NEW = 11;
    private static final int COUNTING_GPA_CURRENT = 12;
    private static final int DELETE_GPA_SET = 13;
    private static final int EXAMS_MENU = 14;
    private static final int ADDING_EXAM = 15;
    private static final int ADDING_COURSE_TO_EXAM = 16;
    private static final int DELETING_EXAM = 17;
    private static final int ADD_SUGGESTION = 18;

    public String getStateFromInt(int state) {
        String result = "";
        switch(state) {
            case 0:
                result = "START_STATE";
                break;
            case 1:
                result = "MAIN_MENU";
                break;
            case 2:
                result = "COURSE_SETTINGS";
                break;
            case 3:
                result = "ADDING_COURSE";
                break;
            case 4:
                result = "ADD_TIME";
                break;
            case 5:
                result = "ADDING_TIME";
                break;
            case 6:
                result = "CALCULATE_GPA";
                break;
            case 7:
                result = "DELETING_COURSE";
                break;
            case 8:
                result = "GENERATING_NEW_SCHEDULE";
                break;
            case 9:
                result = "COUNT_GPA_NEW";
                break;
            case 10:
                result = "COUNT_GPA_CURRENT";
                break;
            case 11:
                result = "COUNTING_GPA_NEW";
                break;
            case 12:
                result = "COUNTING_GPA_CURRENT";
                break;
            case 13:
                result = "DELETE_GPA_SET";
                break;
            case 14:
                result = "EXAMS_MENU";
                break;
            case 15:
                result = "ADDING_EXAM";
                break;
            case 16:
                result = "ADDING_COURSE_TO_EXAM";
                break;
            case 17:
                result = "DELETING_EXAM";
                break;
            case 18:
                result = "ADD_SUGGESTION";
                break;
            default:
                result = "YOU FORGOT TO ADD DESCRIPTION OF THE COMMAND!";
                break;
        }
        return result;
    }

    private SendMessage onCommandReceived(Message message) {
        SendMessage sendMessage = null;
        switch(message.getText().split(" ")[0])
        {
            case "/menu":
                sendMessage = menuSelected(message);
                break;
            case "/start":
                sendMessage = defaultSelected(message);
                break;
            case "/add_time":
                sendMessage = onAddTime(message);
                break;
            case "/view_courses":
                sendMessage = viewCoursesSelected(message);
                break;
            case "/view_exams":
                sendMessage = viewExamsSelected(message);
                break;
            case "/calculate_gpa":
                sendMessage = calculateGpaSelected(message);
                break;
            case "/add_course":
                sendMessage = addCourseSelected(message);
                break;
            case "/about":
                sendMessage = aboutSelected(message);
                break;
            default:
                sendMessage = null;
                break;
        }
        return sendMessage;
    }

    /* State Pattern */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            // Inserting user to database or updating his info
            Database.getInstance().addUser(message.getFrom().getId());

            System.out.println(message.getText()); // DEBUG ONLY

            // If a command received
            SendMessage sendMessage = onCommandReceived(message);
            if(sendMessage != null) {
                try{
                    sendMessage.setChatId(update.getMessage().getChatId());
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return;
            }

            // Getting user's current state
            final int state = Database.getInstance().getState(message.getFrom().getId(), message.getChatId());
            System.out.println(getStateFromInt(state)); // DEBUG ONLY

            switch(state) {
                case MAIN_MENU:
                    sendMessage = onMainMenu(message);
                    break;
                case COURSE_SETTINGS:
                    sendMessage = onCourseSettings(message);
                    break;
                case ADDING_COURSE:
                    sendMessage = onAddingCourse(message);
                    break;
                case ADD_TIME:
                    sendMessage = onAddTime(message);
                    break;
                case ADDING_TIME:
                    sendMessage = onAddingTime(message);
                    break;
                case DELETING_COURSE:
                    sendMessage = onDeletingCourse(message);
                    break;
                case GENERATING_NEW_SCHEDULE:
                    sendMessage = onGeneratingNewSchedule(message);
                    break;
                case CALCULATE_GPA:
                    sendMessage = onCalculateGpa(message);
                    break;
                case COUNT_GPA_NEW:
                    sendMessage = onSelectGpaScale(message, COUNT_GPA_NEW);
                    break;
                case COUNT_GPA_CURRENT:
                    sendMessage = onSelectGpaScale(message, COUNT_GPA_CURRENT);
                    break;
                case COUNTING_GPA_NEW:
                    sendMessage = onCountingGpaNew(message);
                    break;
                case COUNTING_GPA_CURRENT:
                    sendMessage = onCountingGpaCurrent(message);
                    break;
                case DELETE_GPA_SET:
                    sendMessage = onDeleteGpaSet(message);
                    break;
                case EXAMS_MENU:
                    sendMessage = onExamsMenu(message);
                    break;
                case ADDING_EXAM:
                    sendMessage = onAddingExam(message);
                    break;
                case ADDING_COURSE_TO_EXAM:
                    sendMessage = onAddingCourseToExam(message);
                    break;
                case DELETING_EXAM:
                    sendMessage = onDeletingExam(message);
                    break;
                case ADD_SUGGESTION:
                    sendMessage = onAddSuggestion(message);
                    break;
                default:
                    sendMessage = onDefault(message);
                    break;
            }

            try {
                if(sendMessage == null) {
                    sendMessage = new SendMessage();
                    sendMessage.setText("Unknown option");
                }
                sendMessage.setChatId(update.getMessage().getChatId());
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    private void sendErrorMessage(String errorMessage, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyToMessageId(message.getMessageId()).setText(errorMessage);
        sendMessage.setChatId(message.getChatId());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendInfoMessage(String infoMessage, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(infoMessage);
        sendMessage.setChatId(message.getChatId());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage onDefault(Message message) {
        if(message.getText().equals("/menu")) {
            return menuSelected(message);
        } else {
            return defaultSelected(message);
        }
    }

    private SendMessage onMainMenu(Message message) {
        if(message.getText().equals("/course_settings")) {
            return courseSettingsSelected(message);
        }
        else if(message.getText().equals("/generate_new_schedule")) {
            return generateNewScheduleSelected(message);
        }
        else if(message.getText().equals("/view_courses")) {
            return viewCoursesSelected(message);
        }
        else if(message.getText().equals("/calculate_gpa")) {
            return calculateGpaSelected(message);
        }
        else if(message.getText().equals("/exams_menu")) {
            return examsMenuSelected(message);
        }
        else if(message.getText().equals("/add_suggestion")) {
            return addSuggestionSelected(message);
        }
        else if(message.getText().equals("/about")) {
            return aboutSelected(message);
        }
        return menuSelected(message);
    }
    
    private SendMessage onAddSuggestion(Message message) {
        SendMessage cancelMessage = cancelSelected(message, menuSelected(message));
        if(cancelMessage != null) return cancelMessage;
        Database.getInstance().addSuggestion(message.getFrom().getId(), message.getText());
        sendInfoMessage("Thank you for your time!", message);
        return menuSelected(message);
    }

    private SendMessage onCalculateGpa(Message message) {
        SendMessage cancelMessage = cancelSelected(message, menuSelected(message));
        if(cancelMessage != null) return cancelMessage;

        if(message.getText().equals("/count_gpa_new")) {
            return selectGpaScaleSelected(message);
        } else if(message.getText().equals("/see_gpa_previous")) {
            return seeGpaPreviousSelected(message);
        } else if(message.getText().equals("/clear_gpa_data")) {
            return clearGpaDataSelected(message);
        } else if(message.getText().equals("/count_gpa_current")) {
            return selectGpaScaleSelected(message);
        } else if(message.getText().equals("/delete_gpa_set")) {
            return deleteGpaSetSelected(message);
        }
        return calculateGpaSelected(message);
    }

    private GpaCalculator getGpaCalculator(Message message) {
        String gpaScale = message.getReplyToMessage().getText();
        if(gpaScale == null) {
            sendErrorMessage("You must not cancel the force reply", message); // TODO better error message & check this call
            throw new IllegalStateException("No correct gpa scale was specified in the reply message");
        }
        GpaCalculator gpaCalculator = null;
        if(gpaScale.equals("4.0")) {
            gpaCalculator = new GpaFourZero();
        } else if(gpaScale.equals("4.3")) {
            gpaCalculator = new GpaFourThree();
        } else if(gpaScale.equals("100%")) {
            gpaCalculator = new GpaPercentage();
        } else {
            sendErrorMessage("Some error has occurred", message);
            throw new IllegalStateException("No correct gpa scale was specified in the reply message");
        }
        return gpaCalculator;
    }

    private void checkLimitsOfGpaData(List<String> courseInputs, Message message) {
        int gpa_sets = Database.getInstance().getNoOfGpaSets(message.getFrom().getId());
        System.out.println("gpa_sets_no: " + gpa_sets); // DEBUG ONLY
        if(gpa_sets > 10) {
            sendErrorMessage("The limit for number of user sets is 10\nPlease delete your GPA countings", message);
            throw new IllegalStateException("The limit of 10 gpa user sets has exceeded");
        }
        if(courseInputs.size() > 150) {
            sendErrorMessage("The limit for number of courses is 150", message);
            throw new IllegalStateException("The limit of 150 number of courses in the input has exceeded");
        }
    }

    private SendMessage cancelSelected(Message message, SendMessage toSend) {
        if(message.getText().equals("/cancel")) {
            try {
                SendMessage sendMessage = new SendMessage().setText("Cancelling..");
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove());
                sendMessage.setChatId(message.getChatId());
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return toSend.setReplyToMessageId(null);
        }
        return null;
    }

    private SendMessage onAddingCourseToExam(Message message) {
        SendMessage cancelMessage = cancelSelected(message, menuSelected(message));
        if(cancelMessage != null) return cancelMessage;
        int examId = -1;
        try {
            examId = Integer.parseInt(message.getReplyToMessage().getText());
            // Biology,88 Literature,99
            Splitter splitter = Splitter.on(';').trimResults().omitEmptyStrings();
            List<String> courses = splitter.splitToList(message.getText());
            Map<String, Integer> courseMap = new HashMap<>();
            Splitter splitter2 = Splitter.on(",").trimResults().omitEmptyStrings();
            for(int i = 0; i < courses.size(); i++) {
                List<String> el = splitter2.splitToList(courses.get(i));
                int prepLevel = Integer.parseInt(el.get(1));
                if(prepLevel < 0 || prepLevel > 100) {
                    throw new IllegalArgumentException("Preparation level value is not correct");
                }
                courseMap.put(el.get(0), prepLevel);
            }
            if(! Database.getInstance().existCourseNames(message.getFrom().getId(), courseMap.keySet())) {
                throw new NoSuchElementException("Courses with given names do not exist");
            }
            for(Entry<String, Integer> entry : courseMap.entrySet()) {
                Database.getInstance().addCourseToExam(examId, entry.getKey(), entry.getValue()); // TODO make addCoursesToExam
            }
            Database.getInstance().updateTotalPrepLevel(examId);
            int totalLevel = Database.getInstance().getTotalPrepLevel(examId);
            sendInfoMessage("Your total preparation level for this exam: " + totalLevel, message);
            return examsMenuSelected(message);
        } catch (Exception e) {
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
            sendErrorMessage("Error occurred. Please, try again", message);
        }
        if(examId == -1) {
            return examsMenuSelected(message);
        }
        else {
            return addCourseToExamSelected(message, examId);
        }
    }

    private SendMessage onAddingExam(Message message) { // TODO
        SendMessage cancelMessage = cancelSelected(message, menuSelected(message));
        if(cancelMessage != null) return cancelMessage;
        try {
            Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();
            List<String> examInputs = splitter.splitToList(message.getText());
            // Midterm,17.04.2018,100
            String regex = "^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$";
            if(! Pattern.matches(regex, examInputs.get(1))) {
                throw new IllegalArgumentException("Regex doesn't match date format");
            }
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = dateFormat.parse(examInputs.get(1));
            Timestamp timestamp = new Timestamp(date.getTime());
            int prepLevel = Integer.parseInt(examInputs.get(2));
            if(prepLevel < 0 || prepLevel > 100) {
                throw new IllegalArgumentException("The prepLevel parameter must be in range [0-100]");
            }
            Exam exam = new Exam();
            exam.setUserId(message.getFrom().getId());
            exam.setDate(timestamp);
            exam.setName(examInputs.get(0));
            exam.setTotalPrepLevel(prepLevel);
            int examId = Database.getInstance().addExam(exam);
            if(examId == -1) {
                sendErrorMessage("Something bad has occurred", message);
                return examsMenuSelected(message);
            }
            return addCourseToExamSelected(message, examId);
        } catch (Exception e) {
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
            sendErrorMessage("Error occurred. Please, try again", message);
        }
        return examsMenuSelected(message);
    }

    private SendMessage onExamsMenu(Message message) {
        SendMessage cancelMessage = cancelSelected(message, menuSelected(message));
        if(cancelMessage != null) return cancelMessage;
        if(message.getText().equals("/add_exam")) {
            return addExamSelected(message);
        } else if(message.getText().equals("/view_exams")) {
            return viewExamsSelected(message);
        } else if(message.getText().equals("/clear_exam_data")) {
            return clearExamDataSelected(message);
        } else if(message.getText().equals("/delete_exam")) {
            return deleteExamSelected(message);
        }
        return examsMenuSelected(message);
    }

    private SendMessage onDeleteGpaSet(Message message) {
        SendMessage cancelMessage = cancelSelected(message, calculateGpaSelected(message));
        if(cancelMessage != null) return cancelMessage;
        try {
            Splitter splitter = Splitter.on(CharMatcher.whitespace()).trimResults().omitEmptyStrings();
            List<String> inputString = splitter.splitToList(message.getText());
            List<Integer> setIds = new ArrayList<>();
            for(int i = 0; i < inputString.size(); i++) {
                setIds.add(Integer.parseInt(inputString.get(i)));
            }
            Database.getInstance().deleteGpaSets(setIds, message.getFrom().getId());
            sendInfoMessage("You have successfully deleted GPA sets", message);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
            sendErrorMessage("Error occurred. Please, try again", message);
        }
        return calculateGpaSelected(message);
    }

    private SendMessage onCountingGpaNew(Message message) {
        SendMessage cancelMessage = cancelSelected(message, calculateGpaSelected(message));
        if(cancelMessage != null) return cancelMessage;
        try {
            Splitter splitter = Splitter.on(';').trimResults().omitEmptyStrings();
            List<String> courseInputs = splitter.splitToList(message.getText());
            checkLimitsOfGpaData(courseInputs, message);
            GpaCalculator gpaCalculator = getGpaCalculator(message);
            List<Course> courses = new ArrayList<>();
            for(int i = 0; i < courseInputs.size(); i++) {
                Splitter splitter2 = Splitter.on(",").trimResults().omitEmptyStrings();
                List<String> courseInput = splitter2.splitToList(courseInputs.get(i));
                Course course = new Course();
                course.setCourseName(courseInput.get(0));
                course.setCredits(Integer.parseInt(courseInput.get(1)));
                if(! checkIfLetterGrade(courseInput.get(2))) {
                    sendErrorMessage("Some letter you specified is not correct", message);
                    throw new IllegalStateException("User-specified letters contain error");
                }
                course.setLetter(courseInput.get(2));
                courses.add(course);
            }
            gpaCalculator.setCourses(courses);
            double gpa = gpaCalculator.calculate();
            System.out.println(gpa); // DEBUG ONLY
            Database.getInstance().saveGpaSet(message.getFrom().getId(), gpa, message.getReplyToMessage().getText(), courses);
            sendInfoMessage("Your overall GPA: " + gpa, message);
            return calculateGpaSelected(message);
        } catch(Exception e) { // TODO make an error format Exception
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
        }
        return calculateGpaSelected(message);
    }

    private boolean checkIfLetterGrade(String string) {
        Pattern pattern = Pattern.compile("(?i)^([ABCD][-+]?|[F])$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    private SendMessage onCountingGpaCurrent(Message message) {
        SendMessage cancelMessage = cancelSelected(message, calculateGpaSelected(message));
        if(cancelMessage != null) return cancelMessage;
        List<Course> courses = Database.getInstance().getAllCourseNameCredits(message.getFrom().getId());
        if(courses.isEmpty()) {
            sendErrorMessage("You don't have any courses yet. Go to /add_course", message);
            return calculateGpaSelected(message);
        }
        try {
            Splitter splitter = Splitter.on(CharMatcher.whitespace()).trimResults().omitEmptyStrings();
            List<String> courseInputs = splitter.splitToList(message.getText());
            checkLimitsOfGpaData(courseInputs, message);
            GpaCalculator gpaCalculator = getGpaCalculator(message);
            if(courses.size() != courseInputs.size()) {
                sendErrorMessage("Looks like you didn't specify letter grades correctly", message);
                throw new IllegalArgumentException("Number of elements between courses and letter grades do not match");
            }
            for(int i = 0; i < courseInputs.size(); i++) {
                if(! checkIfLetterGrade(courseInputs.get(0))) {
                    sendErrorMessage("Some letter you specified is not correct", message);
                    throw new IllegalStateException("User-specified letters contain error");
                }
                courses.get(i).setLetter(courseInputs.get(i));
            }
            gpaCalculator.setCourses(courses);
            double gpa = gpaCalculator.calculate();
            System.out.println(gpa); // DEBUG ONLY
            Database.getInstance().saveGpaSet(message.getFrom().getId(), gpa, message.getReplyToMessage().getText(), courses);
            sendInfoMessage("Your overall GPA: " + gpa, message);
            return calculateGpaSelected(message);
        } catch(Exception e) { // TODO make an error format Exception
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
        }
        return calculateGpaSelected(message);
    }

    private SendMessage onSelectGpaScale(Message message, final int state) {
        SendMessage cancelMessage = cancelSelected(message, calculateGpaSelected(message));
        if(cancelMessage != null) return cancelMessage;

        String text = message.getText();
        if(! text.equals("4.0") && ! text.equals("4.3") && ! text.equals("100%")) {
            return calculateGpaSelected(message);
        }
        if(state == COUNT_GPA_NEW) {
            return countingGpaNewSelected(message);
        } else if(state == COUNT_GPA_CURRENT) {
            return countingGpaCurrentSelected(message);
        }
        return calculateGpaSelected(message);
    }

    private SendMessage onCourseSettings(Message message) {
        SendMessage cancelMessage = cancelSelected(message, menuSelected(message));
        if(cancelMessage != null) return cancelMessage;

        if(message.getText().equals("/add_course")) {
            return addCourseSelected(message);
        } else if(message.getText().equals("/delete_course")) {
            return deleteCourseSelected(message);
        } else if(message.getText().equals("/view_courses")) {
            return viewCoursesSelected(message);
        }
        return courseSettingsSelected(message);
    }

    private SendMessage onGeneratingNewSchedule(Message message)
    {
        SendMessage cancelMessage = cancelSelected(message, menuSelected(message));
        if(cancelMessage != null) return cancelMessage;

        try {
            // TODO make preconditions checking
            String result = Scheduler.doWork(message.getText());
            sendInfoMessage(result, message);            
            return menuSelected(message);
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
        return menuSelected(message);
    }

    // TODO make courseName case-insensitive
    private SendMessage onAddingCourse(Message message) {
        SendMessage cancelMessage = cancelSelected(message, courseSettingsSelected(message));
        if(cancelMessage != null) return cancelMessage;

        try {
            Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();
            List<String> strings = splitter.splitToList(message.getText());
            String name = strings.get(0);
            int credits = Integer.parseInt(strings.get(1));
            Preconditions.checkArgument(credits > 0 && credits < 100);
            String professor = null, room = null;
            if(strings.size() > 2) {
                professor = strings.get(2);
            }
            if(strings.size() > 3) {
                room = strings.get(3);
            }
            Database.getInstance().addCourse(message.getFrom().getId(), name, credits, professor, room);
            // TODO Maybe setReplyToMessageId is not necessary
            return addTimeSelected(message, name).setText(name + " was successfully added");
        } catch(IllegalArgumentException | IndexOutOfBoundsException e) {
            logger.log(Level.INFO, "This message was passed: " + message.getText() + "\nFrom user: " + message.getFrom().getId(), e);
            return addCourseSelected(message);
        }
        catch(Exception e) {
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
        }
        // If something goes unexpected
        return courseSettingsSelected(message);
    }
    
    private SendMessage onDeletingExam(Message message) {
        SendMessage cancelMessage = cancelSelected(message, examsMenuSelected(message));
        if(cancelMessage != null) return cancelMessage;
        try {
            Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();
            List<String> strings = splitter.splitToList(message.getText());
            Preconditions.checkArgument(strings.size() > 0);
            if(! Database.getInstance().existExamNames(message.getFrom().getId(), strings)) {
                throw new NoSuchElementException("Exams with given names do not exist");
            }
            Database.getInstance().deleteExams(message.getFrom().getId(), strings);
            return examsMenuSelected(message).setText("Exams were successfully deleted");
        } catch (NoSuchElementException e) {
            logger.log(Level.INFO, "This message was passed: " + message.getText() + "\nFrom user: " + message.getFrom().getId(), e);
            sendErrorMessage("Exam does not exist", message);
            return deleteExamSelected(message);
        } catch(IllegalArgumentException e) {
            logger.log(Level.INFO, "This message was passed: " + message.getText() + "\nFrom user: " + message.getFrom().getId(), e);
            sendErrorMessage("Incorrect format", message);
            return deleteExamSelected(message);
        }
        catch(Exception e) {
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
        }
        // If something goes unexpected
        return examsMenuSelected(message);
    }

    private SendMessage onDeletingCourse(Message message) {
        SendMessage cancelMessage = cancelSelected(message, courseSettingsSelected(message));
        if(cancelMessage != null) return cancelMessage;

        try {
            Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();
            List<String> strings = splitter.splitToList(message.getText());
            Preconditions.checkArgument(strings.size() > 0);
            if(! Database.getInstance().existCourseNames(message.getFrom().getId(), strings)) {
                throw new NoSuchElementException("Courses with given names do not exist");
            }
            Database.getInstance().deleteCourses(message.getFrom().getId(), strings);
            return courseSettingsSelected(message).setText("Courses were successfully deleted");
        } catch (NoSuchElementException e) {
            logger.log(Level.INFO, "This message was passed: " + message.getText() + "\nFrom user: " + message.getFrom().getId(), e);
            sendErrorMessage("Course does not exist", message);
            return deleteCourseSelected(message);
        } catch(IllegalArgumentException e) {
            logger.log(Level.INFO, "This message was passed: " + message.getText() + "\nFrom user: " + message.getFrom().getId(), e);
            sendErrorMessage("Incorrect format", message);
            return deleteCourseSelected(message);
        }
        catch(Exception e) {
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
        }
        // If something goes unexpected
        return courseSettingsSelected(message);
    }

    private SendMessage onAddingTime(Message message) { // TODO make adding time with commas
        SendMessage cancelMessage = cancelSelected(message, courseSettingsSelected(message));
        if(cancelMessage != null) return cancelMessage;
        SendMessage sendMessage = new SendMessage();
        String courseName = null;
        Message reply;
        try {
            reply = message.getReplyToMessage();
            Preconditions.checkNotNull(reply);
            Preconditions.checkNotNull(reply.getText());
            Preconditions.checkArgument(! reply.getText().equals(""));
            System.out.println(reply.getText()); // DEBUG ONLY
            // TODO change to regular expressions
            courseName = reply.getText().split("\n")[0];
            courseName = courseName.split(": ")[1];
            List<String> courses = new ArrayList<>();
            courses.add(courseName);
            if(Database.getInstance().existCourseNames(message.getFrom().getId(), courses) == false) {
                throw new IllegalArgumentException("Given course name does not exist");
            }
            System.out.println(courseName); // DEBUG ONLY
        } catch (Exception e) {
            sendErrorMessage("The course you are adding to does not exist", message);
            return courseSettingsSelected(message);
        }
        try {
            Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();
            List<String> strings = splitter.splitToList(message.getText());
            String dayOfWeek = strings.get(0).toLowerCase();
            dayOfWeek = Character.toUpperCase(dayOfWeek.charAt(0)) + dayOfWeek.substring(1); // TODO unicode?
            System.out.println(dayOfWeek); // DEBUG ONLY
            DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            String startTime = strings.get(1);
            String endTime = strings.get(2);
            if(! Pattern.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", startTime) ||
                    ! Pattern.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", endTime)) {
                throw new IllegalArgumentException();
            }
            startTime = startTime + ":00";
            endTime = endTime + ":00";
            System.out.println(startTime + " - " + endTime); // DEBUG ONLY
            Database.getInstance().addTime(message.getFrom().getId(), courseName, dayOfWeek, startTime, endTime);
            return addTimeSelected(message, courseName).setText("Time was successfully added");
        } catch (IllegalArgumentException | NullPointerException e) {
            ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
            sendErrorMessage("Incorrect format", message);
            sendMessage.setText(reply.getText()).setReplyMarkup(forceReplyKeyboard);
            return sendMessage;
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "This message was passed: " + message.getText() + "\n From user: " + message.getFrom().getId(), e);
        }
        return courseSettingsSelected(message);
    }

    /* /add_time courseName */
    private SendMessage onAddTime(Message message) {
        SendMessage cancelMessage = cancelSelected(message, courseSettingsSelected(message));
        if(cancelMessage != null) return cancelMessage;

        if(message.getText().split(" ")[0].equals("/add_time")) {
            return addingTimeSelected(message);
        }
        return null;
    }
    
    private SendMessage deleteExamSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Enter the exam name you want to delete, or a list of them\n"
                + "Example: Midterm,Finals,Quiz I\n"
                + "Write the exact name as written in /view_exams\n"
                + "Or write /cancel to go to previous menu");
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), DELETING_EXAM);
        return sendMessage;
    }
    
    private SendMessage aboutSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Telegram bot for efficient studying http://t.me/uni_study_bot\n"
                + "With this compact, convenient and free to use bot you can improve your university life!\n"
                + "1. Manage your course data\n"
                + "2. Calculate expected GPAs\n"
                + "3. Generate course schedules needed for online course registrations\n"
                + "4. Manage exams: include preparation level for each course (We believe this is a good tool to motivate yourself)\n"
                + "5. There are more things to come!\n"
                + "View on github: https://github.com/asahi7/uni_study_bot");
        // TODO add more information and usage guides
        return sendMessage;
    }
    
    private SendMessage clearExamDataSelected(Message message) {
        Database.getInstance().clearExamData(message.getFrom().getId());
        sendInfoMessage("All exam data has been deleted", message);
        return examsMenuSelected(message);
    }

    private SendMessage viewExamsSelected(Message message) {
        List<Exam> exams = Database.getInstance().getExams(message.getFrom().getId());
        StringBuilder msg = new StringBuilder();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SendMessage sendMessage = new SendMessage();
        for(int i = 0; i < exams.size(); i++) {
            msg.append("{\n");
            Exam exam = exams.get(i);
            Date date = new Date(exam.getDate().getTime());
            String dateString = dateFormat.format(date);
            msg.append(exam.getName() + ", " + dateString + ", " + exam.getTotalPrepLevel() + "%" + ":\n");
            for(Entry<String, Integer> entry : exam.getCoursePrep().entrySet()) {
                msg.append(entry.getKey() + " - " + entry.getValue() + "%" + "\n");
            }
            msg.append("}\n");
        }
        if(exams.isEmpty()) {
            sendMessage.setText("You don't have exams yet");
        } else {
            sendMessage.setText(msg.toString());
        }
        if(Database.getInstance().getState(message.getFrom().getId(), message.getChatId()) == EXAMS_MENU) {
            sendMessage.setReplyMarkup(getExamsMenuKeyboard());
        }
        return sendMessage;
    }

    private SendMessage addCourseToExamSelected(Message message, int examId) {
        SendMessage sendMessage = new SendMessage();
        String courses = Database.getInstance().getAllCoursesAsString(message.getFrom().getId());
        if(courses != null && ! courses.isEmpty()) {
            sendInfoMessage(courses, message);
        } else {
            sendInfoMessage("You don't have any courses to add, please add courses first", message);
            return courseSettingsSelected(message);
        }
        String msg = "Write courses you want to add to the exam in the following format:\n"
                   + "COURSE_NAME,PREPARATION_LEVEL\n"
                   + "Example: Biology,88 ; Literature,99\n"
                   + "Note: The PREPARATION_LEVEL's range is [0-100]. COURSE_NAME must match one of your existing courses. "
                   + "Put ';' as a separator between courses\n"
                   + "Or write /cancel to go to previous menu";
        sendInfoMessage(msg, message);
        sendMessage.setText(Integer.toString(examId)).setReplyMarkup(new ForceReplyKeyboard());
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), ADDING_COURSE_TO_EXAM);
        return sendMessage;
    }

    private SendMessage addExamSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Write the exam you want to be saved in the following format:\n"
                + "EXAM_NAME,DATE,PREPARATION_LEVEL\n"
                + "Required fields: EXAM_NAME\n"
                + "Example: Midterm,17.04.2018,100\n"
                + "Example: Finals,13.06.2018,78\n"
                + "Note: Date must match exact the same format. The PREPARATION_LEVEL's range is [0-100]\n"
                + "Or write /cancel to go to previous menu");
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), ADDING_EXAM);
        return sendMessage;
    }

    private SendMessage deleteGpaSetSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Input setID of a set which you want to delete\n"
                + "You may add several of them\n"
                + "Example: 1 2 3 4\n"
                + "Or write /cancel to go to previous menu");
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), DELETE_GPA_SET);
        return sendMessage;
    }

    private SendMessage selectGpaScaleSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Select the GPA scale");
        sendMessage.setReplyMarkup(getGpaScaleKeyboard());
        if(message.getText().equals("/count_gpa_current")) {
            Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), COUNT_GPA_CURRENT);
        } else {
            Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), COUNT_GPA_NEW);
        }
        return sendMessage;
    }

    private SendMessage examsMenuSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(getExamsMenuKeyboard());
        sendMessage.setText("Select the menu item"); // TODO more friendly message
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), EXAMS_MENU);
        return sendMessage;
    }

    private SendMessage countingGpaNewSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyToMessageId(message.getMessageId());
        String info = "Write a list of courses in the following format:\n"
                + "COURSE_NAME,NUM_OF_CREDITS,LETTER_GRADE\n"
                + "Example: Calculus,3,A ; Philosophy,3,B+\n"
                + "Note: Put ';' as a separator between courses. "
                + "Any whitespace characters between courses will do\n"
                + "Or write /cancel to go to previous menu";
        sendInfoMessage(info, message);
        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        sendMessage.setReplyMarkup(forceReplyKeyboard).setText(message.getText());
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), COUNTING_GPA_NEW);
        return sendMessage;
    }

    private SendMessage countingGpaCurrentSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyToMessageId(message.getMessageId());
        StringBuilder info = new StringBuilder("Write a letter grade for each of the following courses (in order) "
                + "by replacing question marks:\n");
        List<Course> courses = Database.getInstance().getAllCourseNameCredits(message.getFrom().getId());
        if(courses.isEmpty()) {
            sendErrorMessage("You don't have any courses yet. Go to /add_course", message);
            return calculateGpaSelected(message);
        }
        for(int i = 0; i < courses.size(); i++) {
            info.append(courses.get(i).getCourseName() + ", " + courses.get(i).getCredits() + " credits - ?" + "\n");
        }
        info.append("Example: A+ B- B A+\n");
        info.append("Or write /cancel to go to previous menu");
        sendInfoMessage(info.toString(), message);
        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        sendMessage.setReplyMarkup(forceReplyKeyboard).setText(message.getText());
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), COUNTING_GPA_CURRENT);
        return sendMessage;
    }

    private SendMessage clearGpaDataSelected(Message message) { // TODO are you sure button?
        SendMessage sendMessage = new SendMessage();
        Database.getInstance().clearGpaData(message.getFrom().getId());
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), CALCULATE_GPA);
        sendMessage.setText("Your GPA sets have been successfully deleted").setReplyMarkup(getCalculateGpaKeyboard());
        return sendMessage;
    }

    private SendMessage seeGpaPreviousSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        List<GpaSet> gpaSets = Database.getInstance().getGpaSets(message.getFrom().getId());
        String result;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < gpaSets.size(); i++) {
            GpaSet gpaSet = gpaSets.get(i);
            sb.append("{\n");
            for(int j = 0; j < gpaSet.getCourses().size(); j++) {
                Course course = gpaSet.getCourses().get(j);
                sb.append(course.getCourseName() + "," + course.getCredits() + "," + course.getLetter() + "\n");
            }
            sb.append("} setID: " + gpaSet.getSetId() + ", GPA: " + gpaSet.getGpa() + "/" + gpaSet.getGpaScale() + "\n\n");
        }
        result = sb.toString();
        if(gpaSets.isEmpty()) {
            result = "You don't have any GPA sets yet";
        }
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), CALCULATE_GPA);
        sendMessage.setText(result).setReplyMarkup(getCalculateGpaKeyboard());
        return sendMessage;
    }

    private SendMessage viewCoursesSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        String courses = Database.getInstance().getAllCoursesAsString(message.getFrom().getId());
        if(courses == null) courses = "You don't have courses yet";
        sendMessage.setText(courses);
        final int state = Database.getInstance().getState(message.getFrom().getId(), message.getChatId());
        if(state == MAIN_MENU) {
            sendMessage.setReplyMarkup(getMenuKeyboard());
        } else if(state == COURSE_SETTINGS) {
            sendMessage.setReplyMarkup(getCourseSettingsKeyboard());
        }
        return sendMessage;
    }

    private SendMessage addingTimeSelected(Message message) {
        Splitter splitter = Splitter.on(' ').trimResults().limit(2);
        SendMessage sendMessage = new SendMessage();
        String courseName = null;
        try{
            courseName = splitter.splitToList(message.getText()).get(1);
            Preconditions.checkNotNull(courseName);
            Preconditions.checkArgument(! courseName.equals(""));
            List<String> courses = new ArrayList<>();
            courses.add(courseName);
            if(! Database.getInstance().existCourseNames(message.getFrom().getId(), courses)) {
                throw new NoSuchElementException();
            }
        } catch(IndexOutOfBoundsException | IllegalArgumentException | NullPointerException e) {
            sendMessage.setText("Incorrect format, should be /add_time <COURSE_NAME>");
            Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), MAIN_MENU);
            return sendMessage;
        } catch (NoSuchElementException e) {
            sendMessage.setText("Such course does not exist. Please check your syntax");
            Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), MAIN_MENU);
            return sendMessage;
        }
        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        sendMessage.setText("You are adding time to course: " + courseName + "\n"
                + "Enter the time in the format: DAY,START_TIME,END_TIME\n"
                + "Required fields: DAY,START_TIME,END_TIME\n"
                + "Example: Monday,13:00,14:15\n"
                + "Or write /cancel to go to previous menu").setReplyMarkup(forceReplyKeyboard);
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), ADDING_TIME);
        return sendMessage;
    }

    private SendMessage addTimeSelected(Message message, String courseName) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(getAddTimeKeyboard(courseName));
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), ADD_TIME);
        return sendMessage;
    }
    
    private SendMessage addSuggestionSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please add your suggestions here\n"
                + "Or write /cancel to go to previous menu");
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), ADD_SUGGESTION);
        return sendMessage;
    }

    private SendMessage addCourseSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Enter the course info in the format: COURSE_NAME,NUM_OF_CREDITS,PROFESSOR,ROOM\n"
                + "Required fields: COURSE_NAME,NUM_OF_CREDITS\n"
                + "Example: Calculus,3,Mark Zuckerberg,106-711\n"
                + "Or write /cancel to go to previous menu");
        ReplyKeyboardRemove replyMarkup = new ReplyKeyboardRemove();
        sendMessage.setReplyMarkup(replyMarkup);
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), ADDING_COURSE);
        return sendMessage;
    }

    private SendMessage deleteCourseSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Enter the course name you want to delete, or a list of them\n"
                + "Example: Calculus,History,Fluid Mechanics\n"
                + "Write the exact name as written in /view_courses\n"
                + "Or write /cancel to go to previous menu");
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), DELETING_COURSE);
        return sendMessage;
    }

    private SendMessage generateNewScheduleSelected(Message message) {
    	SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Enter the name of courses along with their respective schedules\n"
                + "Example: \n"
                + "Chemistry: (Tuesday 9:00 - 10:15, Thursday 9:00 - 10:15)\n"
                + "Math: (Monday 9:00 - 10:15, Wednesday 9:00 - 10:15) (Tuesday 9:00 - 10:15, Thursday 9:00 - 10:15)\n"
                + "Every new course should start from new line. \n"
                + "Every course name should be followed by a colon \":\". \n"
                + "Every time slot option should be enclosed in brackets. \n"
                + "Or write /cancel to go to previous menu");
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), GENERATING_NEW_SCHEDULE);
        return sendMessage;
    }


    private SendMessage calculateGpaSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Select the menu item");
        sendMessage.setReplyMarkup(getCalculateGpaKeyboard());
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), CALCULATE_GPA);
        return sendMessage;
    }

    private SendMessage courseSettingsSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Select the menu item");
        sendMessage.setReplyMarkup(getCourseSettingsKeyboard());
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), COURSE_SETTINGS);
        return sendMessage;
    }

    private SendMessage menuSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Select the menu item");
        sendMessage.setReplyMarkup(getMenuKeyboard());
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), MAIN_MENU);
        return sendMessage;
    }

    private SendMessage defaultSelected(Message message) {
        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardRemove replyMarkup = new ReplyKeyboardRemove();
        sendMessage.setText("Welcome to our app, " +
                (message.getFrom().getUserName() == null ? "" : message.getFrom().getUserName()) +
                "!\nTo begin write /menu").setReplyMarkup(replyMarkup);
        Database.getInstance().setState(message.getFrom().getId(), message.getChatId(), START_STATE);
        return sendMessage;
    }

    @Override
    public String getBotUsername() {
        return Consts.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Consts.TOKEN;
    }
}
