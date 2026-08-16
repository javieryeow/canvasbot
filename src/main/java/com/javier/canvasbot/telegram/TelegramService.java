package com.javier.canvasbot.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TelegramService {
    private final RestClient restClient;
    private final String chatId;

    private static final int MAX_MESSAGE_LENGTH = 4000;
    private static final String TELEGRAM_API_URL =
            "https://api.telegram.org/bot";

    public TelegramService(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.chat.id}") String chatId
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(TELEGRAM_API_URL + botToken)
                .build();
        this.chatId = chatId;
    }
    public void sendMessage(String text) {
        for (int start = 0; start < text.length(); start += MAX_MESSAGE_LENGTH) {
            int end = Math.min(start + MAX_MESSAGE_LENGTH, text.length());
            String chunk = text.substring(start, end);
            SendMessageRequest request =
                    new SendMessageRequest(chatId, chunk);
            restClient.post()
                    .uri("/sendMessage")
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        }
    }
}
