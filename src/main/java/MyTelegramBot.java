import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyTelegramBot extends TelegramLongPollingBot {
    final String BOT_NAME;
    final String BOT_TOKEN;
    // формируем строку ссылки для запроса, с ключом
    final String NASA_URL = "https://api.nasa.gov/planetary/apod?" +
            "api_key=hGaaGXOsRYUsP9hECA6qaKTpuO0IBaHUWfIO3U31&";

    public MyTelegramBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            // получить команду, точнее массив команда и плюс ещё что-то, введенное через пробел
            String[] separatedAction = update.getMessage().getText().split(" ");
            // получить ID чата
            long chatId = update.getMessage().getChatId();

            // получаем команду из массива
            String action = separatedAction[0];

            switch (action){
                case "Помощь":
                case "/help":
                    sendMessage("","Бот присылающий картинку дня от NASA", chatId);
                    break;
                case "Старт":
                case "/start":
                case "Картинка":
                case "/image":
                    String[] arrStr = Utils.getUrl(NASA_URL);

                    String imageUrl = arrStr[0];
                    //imageUrl = imageUrl.replace("_","\\_");
                    System.out.println(imageUrl);
                    String imageExplanation = arrStr[1];

                    sendMessage(imageUrl, imageExplanation, chatId);
                    break;
                case "Дата":
                    sendMessage("", "Введите дату в формате YYYY-MM-DD",chatId);
                    break;
                case "/date":
                    String date = separatedAction[1];
                    arrStr = Utils.getUrl(NASA_URL + "&date=" + date);

                    imageUrl = arrStr[0] ;
                    imageExplanation = arrStr[1];

                    sendMessage(imageUrl, imageExplanation, chatId);
                    break;
                default:
                    if(action.matches("\\d{4}-\\d{2}-\\d{2}")){
                        date = action;
                        arrStr = Utils.getUrl(NASA_URL + "&date=" + date);

                        imageUrl = arrStr[0] ;
                        imageExplanation = arrStr[1];

                        sendMessage(imageUrl, imageExplanation, chatId);
                    }
                    else sendMessage("", "Хьюстон, у нас проблемы, я не понял команду", chatId);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    void sendMessage(String link, String explanation, long chatID){
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.enableMarkdown(true);

        // Создаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Старт");
        keyboardFirstRow.add("Помощь");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("Картинка");
        keyboardSecondRow.add("Дата");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);


        message.setChatId(chatID);
        //System.out.println(link);
        message.setText(link + "\n\n" + explanation);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            System.out.println("Не отправилось сообщение");
        }
    }
}
