package exception;

import java.io.IOException;
import java.net.URL;

/**
 * TelegramBot
 * @author Yazmin
 * @version 17/07/2020
 */

public class TelegramBot {
    private String url = "https://api.telegram.org/bot1098401798:AAEycvrpsUUIUb0oOcUO-" +
            "_tGsvlfJEK8dVg/sendMessage?chat_id=@";
    private String group;
    private String message = "";

    public TelegramBot(String groupId) {
        this.group = groupId;
    }

    /**
     * Method to adda message
     * @param message to send
     */
    public void addMessage(String message) {
        this.message += message + "%0A";
    }

    /**
     * Method to send to telegram
     */
    public void send() {
        try {
            String completeURL = this.url + this.group + "&text=" + this.message;
            new URL(completeURL).openConnection().getInputStream();
        } catch (IOException ioException) {
            new Exception().log(ioException);
        } finally {
            this.message = "";
        }
    }

    /**
     * Method to send a exception to telegram
     * @param exception message to send
     */
    public static void sendToTelegram(String exception) {
        TelegramBot telegramBot = new TelegramBot("ErroresPP");
        telegramBot.addMessage(exception);
        telegramBot.send();
    }
}
