package com.govind.chatBot.chatBot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class OpenRouterService {

    private static final String URL="https://openrouter.ai/api/v1";
    private static final String TOEKN="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";

    private final WebClient webClient;

    public OpenRouterService(){
        this.webClient=WebClient.builder()
                .baseUrl(URL)
                .defaultHeader("Authorization","Bearer "+TOEKN)
                .defaultHeader("Content-Type","application/json")
                .build();

    }

    public String chat(String userMessage) {
           String requestBody = """
            {
              "model": "mistralai/mistral-7b-instruct",
              "messages": [
                {"role": "user", "content": "%s"}
              ]
            }
            """.formatted(userMessage);

           String strVal= webClient.post()
                   .uri("/chat/completions")
                   .bodyValue(requestBody)
                   .retrieve()
                   .bodyToMono(String.class)
                   .block();
       return extractContent(strVal);

    }

    public String extractContent(String jsonResponse)  {
        String content="";
         try {
             ObjectMapper mapper = new ObjectMapper();
             Map<String, Object> map = mapper.readValue(jsonResponse, Map.class);

             List<Map<String, Object>> choices =
                     (List<Map<String, Object>>) map.get("choices");

             Map<String, Object> message =
                     (Map<String, Object>) choices.get(0).get("message");

             content =(String) message.get("content");

             System.out.println(content);
         }catch (Exception e){
           e.printStackTrace();
             content="";
         }
        return content;
    }




}
