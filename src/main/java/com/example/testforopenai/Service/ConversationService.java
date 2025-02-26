package com.example.testforopenai.Service;

import com.example.testforopenai.Entity.Conversation;
import com.example.testforopenai.Entity.Message;

import java.util.List;

public interface ConversationService {

    /**
     * 保存会话
     * @param id 会话的ID，999代表是新的会话。其他代表旧的会话
     * @param user_id 用户的ID
     */
    void save(Integer id,Integer user_id);

    /**
     * 检查conversation表中是否包含这次会话
     * @return
     */
    boolean check_exist(Conversation conversation);

    /**
     * 根据id获取会话
     * @param id
     * @return
     */
    Conversation get_by_id(Integer id);


    /**
     * 切换会话
     * @param id  历史会话的ID
     */
    List<Message> change(Integer id);
}
