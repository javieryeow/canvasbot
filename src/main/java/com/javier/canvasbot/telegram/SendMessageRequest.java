package com.javier.canvasbot.telegram;

public record SendMessageRequest(
        String chat_id,
        String text
) {}
