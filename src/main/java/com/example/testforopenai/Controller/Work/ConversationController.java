package com.example.testforopenai.Controller.Work;


import com.alibaba.fastjson.JSONObject;
import com.example.testforopenai.Entity.Message;
import com.example.testforopenai.Service.ConversationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    ChatMemory chatMemory;

    @Autowired
    private ChatClient chatClient;

    /**
     * 保存会话,将当前会话存入conversation表中（更新conversation？），将每句话存入message表中
     * @param id 会话id
     * @param user_id 用户id
     */
    @GetMapping("/save")
    public String save(@RequestParam(value="id") Integer id, @RequestParam(value="user_id") Integer user_id){
        log.info("保存id为{}的会话",id);
        conversationService.save(id,user_id);
        return "save conversation successfully!";
    }

    /**
     * 返回该历史会话信息，并切换大模型历史记忆
     * @param id 会话id
     * @return 相应会话信息
     */
    @GetMapping("/change")
    public List<Message> change(@RequestParam(value="id") Integer id){

        log.info("切换至ID为{}的历史会话",id);
        chatMemory.clear("default");
        return conversationService.change(id);
    }


    /**
     * 重新生成回答
     * @return 重新生成的那句回答所属的消息
     * @param id 该回答所属的会话id
     */
    @GetMapping("/regenerate")
    public String re_generate(Integer id){
        return conversationService.re_generate(id);
    }



    /**
     * 这个接口用于流式返回重新生成的内容
     * @param id 会话id
     * @return
     */
    @PostMapping(value="/re_generate",produces = "text/html;charset=UTF-8")
    SseEmitter stream(@RequestBody  Integer id){
        final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        //调用service的重新生成方法
        String input = conversationService.re_generate(id);
        Flux<String> content= Flux.just(input);
        Flux<com.alibaba.fastjson.JSONObject> jsonObjectFlux = content.map(str -> {
            return JSONObject.parseObject("{\"data\":\"" + str + "\"}");
        });
        jsonObjectFlux.subscribe(
                json -> {
                    if (json != null) {
                        try {
                            emitter.send(json.toString());
                        } catch (Exception ex) {
                            emitter.completeWithError(ex);
                        }
                    }
                },
                error -> emitter.completeWithError(error),
                () -> emitter.complete()
        );
        return emitter;
    }



}
