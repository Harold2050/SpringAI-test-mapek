package com.example.testforopenai.Mapper;

import com.example.testforopenai.Entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 更新消息
     * @param message
     */
    //order是mysql中的保留字，要用``包裹起来
    @Update("update message set convo_id=#{convo_id},message=#{message},`order`=#{order},status=#{status} where id=#{id}")
    public void update(Message message);

    /**
     * 插入消息
     * @param message
     */
    @Insert("insert into message (convo_id,message,`order`,status) values (#{convo_id},#{message},#{order},#{status})")
    public void insert(Message message);

    /**
     * 删除同属于一个会话的消息
     * @param convo_id 消息所属的会话id
     */
    @Delete("delete from message where convo_id=#{convo_id}")
    public void delete_by_convo_id(Integer convo_id);

    /**
     * 获取同属于一个会话的所有消息
     * @param convo_id 会话id
     * @return List<Message>
     */
    @Select("select * from message where convo_id=#{convo_id}")
    public List<Message> get_by_convo_id(Integer convo_id);
}
