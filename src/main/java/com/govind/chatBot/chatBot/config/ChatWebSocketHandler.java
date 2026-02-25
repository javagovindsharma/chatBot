package com.govind.chatBot.chatBot.config;

import com.govind.chatBot.chatBot.service.OpenRouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private OpenRouterService service;
     @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String userMessage= message.getPayload();
        String botReply=service.chat(userMessage);
        for(char c: botReply.toCharArray()){
            session.sendMessage(new TextMessage(String.valueOf(c)));
            Thread.sleep(5); // typing speed
        }

    }
}
