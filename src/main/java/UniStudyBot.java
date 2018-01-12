import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class UniStudyBot extends TelegramLongPollingBot
{   
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Database.getInstance().addUser(update.getMessage().getFrom().getUserName());
            
            SendMessage message = new SendMessage() 
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            try {
                execute(message); 
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
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
