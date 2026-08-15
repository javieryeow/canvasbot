package com.javier.canvasbot.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TelegramService {
    private final RestClient restClient;
    private final String botToken;
    private final String chatId;

    public TelegramService(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.chat.id}") String chatId
    ) {
        this.restClient = RestClient.create();
        this.botToken = botToken;
        this.chatId = chatId;
    }

    public void sendMessage(String text) {
        String url = "https://api.telegram.org/bot"
                + botToken
                + "/sendMessage";
        SendMessageRequest request = new SendMessageRequest(chatId, text);
        restClient.post()
                .uri(url)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
