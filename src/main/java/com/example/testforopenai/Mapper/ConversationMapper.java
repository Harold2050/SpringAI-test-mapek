package com.example.testforopenai.Mapper;

import com.example.testforopenai.Entity.Conversation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConversationMapper {

    /**
     * 查询全部会话
     * @return List<Conversation>
     */
    @Select("select * from conversation ")
    public List<Conversation> list();

    /**
     * 插入新会话
     * @param conversation
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into conversation (user_id,convo_title,convo_content,convo_status,create_time,update_time,metadata) values (#{user_id},#{convo_title},#{convo_content},#{convo_status},#{create_time},#{update_time},#{metadata})")
    public void insert(Conversation conversation);

    /**
     * 更新会话
     * @param conversation
     */
    @Update("update conversation set user_id=#{user_id},convo_title=#{convo_title},convo_content=#{convo_content},convo_status=#{convo_status},create_time=#{create_time},update_time=#{update_time},metadata=#{metadata} where id=#{id}")
    public void update(Conversation conversation);

    /**
     * 根据id查询会话
     * @param id
     * @return
     */
    @Select("select * from conversation where id=#{id}")
    public Conversation get_by_id(Integer id);

    /**
     * 根据id删除会话
     * @param id
     */
    @Delete("delete from conversation where id=#{id}")
    public void delete_by_id(Integer id);
}
