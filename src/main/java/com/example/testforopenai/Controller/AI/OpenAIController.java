package com.example.testforopenai.Controller.AI;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/openai")
public class OpenAIController {
/*
    private final OpenAiChatModel chatModel;

    @Autowired
    public OpenAIController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "你好呀") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "你好呀") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

 */

}
