package org.example.service;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;

public class PrecisionSettingsService {
    private final TelegramClient telegramClient;
    private final HashMap<String, Integer> userPrecisionSettings = new HashMap<>();

    public PrecisionSettingsService(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void showPrecisionSettings(long chatId, long messageId) {
        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text("1 знак")
                .callbackData("settings_precision_1")
                .build();

        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text("2 знаки")
                .callbackData("settings_precision_2")
                .build();

        InlineKeyboardButton button3 = InlineKeyboardButton.builder()
                .text("3 знаки")
                .callbackData("settings_precision_3")
                .build();

        InlineKeyboardButton button4 = InlineKeyboardButton.builder()
                .text("4 знаки")
                .callbackData("settings_precision_4")
                .build();

        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(button1);
        row.add(button2);
        row.add(button3);
        row.add(button4);

        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(row)
                .build();

        EditMessageText newMessage = EditMessageText.builder()
                .chatId(chatId)
                .messageId((int) messageId)
                .text("Оберіть кількість знаків після коми:")
                .replyMarkup(keyboardMarkup)
                .build();

        try {
            telegramClient.execute(newMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void handlePrecisionSetting(String callData, String chatId) {
        int precision = 2; // Default precision
        switch (callData) {
            case "settings_precision_1":
                precision = 1;
                break;
            case "settings_precision_2":
                precision = 2;
                break;
            case "settings_precision_3":
                precision = 3;
                break;
            case "settings_precision_4":
                precision = 4;
                break;
        }
        userPrecisionSettings.put(chatId, precision);
    }

    public int getPrecision(String chatId) {
        return userPrecisionSettings.getOrDefault(chatId, 2); // Default to 2 if not set
    }
}
