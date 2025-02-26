package com.example.testforopenai.Controller.Work;


import com.example.testforopenai.Entity.Message;
import com.example.testforopenai.Service.ConversationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    ChatMemory chatMemory;

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
}
