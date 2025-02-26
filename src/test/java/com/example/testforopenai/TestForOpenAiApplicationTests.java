package com.example.testforopenai;

import com.example.testforopenai.Entity.Conversation;
import com.example.testforopenai.Entity.Message;
import com.example.testforopenai.Mapper.ConversationMapper;
import com.example.testforopenai.Mapper.MessageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestForOpenAiApplicationTests {

    @Autowired
    ConversationMapper conversationMapper;

    @Autowired
    MessageMapper messageMapper;
    @Test
    public void test_Conversation(){

        Conversation conversation =new Conversation();
        conversation.setId(null);
        conversation.setUser_id(999);
        conversation.setConvo_title("test");
        conversation.setConvo_content("test");
        conversation.setConvo_status(1);
        conversation.setCreate_time(new java.sql.Timestamp(System.currentTimeMillis()));
        conversation.setUpdate_time(new java.sql.Timestamp(System.currentTimeMillis()));
        conversation.setMetadata("test");
        conversationMapper.insert(conversation);
        //conversationMapper.update(conversation);

    }
    @Test
    public void test_Message(){
        Message message =new Message();
        message.setId(null);
        message.setConvo_id(999);
        message.setMessage("test");
        message.setOrder(1);
        message.setStatus("test");
        messageMapper.insert(message);
        //messageMapper.update(message);
        //messageMapper.delete_by_convo_id(1);
    }

    @Test
    public void test_ChatControl(){

    }
}
