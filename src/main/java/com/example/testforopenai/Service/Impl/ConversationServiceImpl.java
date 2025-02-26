package com.example.testforopenai.Service.Impl;

import com.example.testforopenai.Entity.Bridge;
import com.example.testforopenai.Entity.Conversation;
import com.example.testforopenai.Entity.Message;
import com.example.testforopenai.Mapper.ConversationMapper;
import com.example.testforopenai.Mapper.MessageMapper;
import com.example.testforopenai.Service.ConversationService;
import com.example.testforopenai.Tool.ChatSplitterForLine;
import com.example.testforopenai.Tool.FileClean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationMapper conversationMapper;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void save(Integer id,Integer user_id) {

        ChatSplitterForLine chatParser = new ChatSplitterForLine();
        String filePath = "src/main/resources/ChatMemory/chat.txt";
        List<String> chats = chatParser.parseChatFile(filePath);

        Conversation conversation=new Conversation();

        //即这是一个新会话
        if(id == 999){
            conversation.setUser_id(user_id);
            conversation.setConvo_title(chats.get(0));
            conversation.setConvo_content(null);
            conversation.setConvo_status(0);
            conversation.setCreate_time(new Timestamp(System.currentTimeMillis()));
            conversation.setUpdate_time(new Timestamp(System.currentTimeMillis()));
            conversation.setMetadata(null);
            conversationMapper.insert(conversation);
        }
        else{
            conversation=conversationMapper.get_by_id(id);
            //改两个东西，标题（即第一句话）和更新时间
            conversation.setConvo_title(chats.get(0));
            conversation.setUpdate_time(new Timestamp(System.currentTimeMillis()));
            conversationMapper.update(conversation);
        }

        //接下来要将对话存到message表中。但存之前，要先将所有属于这个对话的旧的记忆删除掉
        //不对，好像要继续添加，而不是把之前的全都删了
        //messageMapper.delete_by_convo_id(conversation.getId());

        //然后一句一句添加对话记忆，首先要去message表中查询当前conversation的对话数到了多少
        List<Message> messages= messageMapper.get_by_convo_id(conversation.getId());
        int i=messages.size();//递增表示次序
        for(String t:chats){
            messageMapper.insert(new Message(null,conversation.getId(),t,i,"ok"));
            i++;
        }
    }



    @Override
    public List<Message> change(Integer id)
    {
        Conversation conversation= conversationMapper.get_by_id(id);
        List<Message> messages= messageMapper.get_by_convo_id(id);
        String result = "";
        for(Message temp:messages){
             result=result + temp.getMessage()+"\n";
        }

        //修改bridge类中的两个全局变量
        Bridge.number=id;
        Bridge.text=result;

        //清空chat.txt中的内容
        FileClean fileClean = new FileClean();
        String filePath = "src/main/resources/ChatMemory/chat.txt";
        try {
            fileClean.clearFile(filePath);
            System.out.println("File content cleared successfully.");
        } catch (IOException e) {
            System.out.println("Error clearing file content: " + e.getMessage());
        }

        return messages;
    }



    @Override
    public boolean check_exist(Conversation conversation)
    {
        List<Conversation> list=conversationMapper.list();
        for(Conversation convo:list)
        {
            if(convo.getId().equals(conversation.getId()))
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public Conversation get_by_id(Integer id)
    {
        return conversationMapper.get_by_id(id);
    }


}
