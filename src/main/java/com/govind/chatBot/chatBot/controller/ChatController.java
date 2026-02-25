package com.govind.chatBot.chatBot.controller;

import com.govind.chatBot.chatBot.service.OpenRouterService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final OpenRouterService openRouterService;

    public ChatController(OpenRouterService openRouterService) {
        this.openRouterService = openRouterService;
    }

    public String chat(@RequestBody String message){
        System.out.println("message==>>"+message);
        return openRouterService.chat(message);
    }
}
