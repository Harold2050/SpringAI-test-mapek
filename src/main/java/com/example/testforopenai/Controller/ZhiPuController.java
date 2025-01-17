package com.example.testforopenai.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zhipuAI")
public class ZhiPuController {
    /*
    private  ZhiPuAiChatModel chatModel;

    @Autowired
    public ZhiPuController(ZhiPuAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "你好呀，你是谁") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "你好呀，你是谁") String message) {
        var prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

    */
}
