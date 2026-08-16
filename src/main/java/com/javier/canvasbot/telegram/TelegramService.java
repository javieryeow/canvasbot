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
        int maxLength = 4000;
        for (int start = 0; start < text.length(); start += maxLength) {
            int end = Math.min(start + maxLength, text.length());
            String chunk = text.substring(start, end);
            SendMessageRequest request =
                    new SendMessageRequest(chatId, chunk);
            String url = "https://api.telegram.org/bot"
                    + botToken
                    + "/sendMessage";
            restClient.post()
                    .uri(url)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        }
    }

//    public void sendMessage(String text) {
//        String url = "https://api.telegram.org/bot"
//                + botToken
//                + "/sendMessage";
//        SendMessageRequest request = new SendMessageRequest(chatId, text);
//        restClient.post()
//                .uri(url)
//                .body(request)
//                .retrieve()
//                .toBodilessEntity();
//    }

}
