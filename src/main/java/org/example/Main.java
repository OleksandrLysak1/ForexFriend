package org.example;

import org.example.service.BotService;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        try {
            String botToken = System.getenv("BOT_TOKEN");
            if (botToken == null || botToken.isEmpty()) {
                throw new IllegalArgumentException("Bot token must not be null or empty. Make sure the environment variable BOT_TOKEN is set.");
            }
            System.out.println("BOT_TOKEN: " + botToken); // Додавання логування
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(botToken, new BotService(botToken));
            System.out.println("BotService successfully started!");
            Thread.currentThread().join();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
