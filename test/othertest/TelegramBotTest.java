package test.othertest;

import org.junit.Test;
import telegram.TelegramBot;

public class TelegramBotTest {
    @Test
    public void testTelegram() {
        TelegramBot telegramBot = new TelegramBot("ErroresPP");
        telegramBot.addMessage("Error accessing properties file");
        telegramBot.send();
    }
}
