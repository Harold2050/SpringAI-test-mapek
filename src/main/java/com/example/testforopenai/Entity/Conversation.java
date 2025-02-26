package com.example.testforopenai.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    private Integer id;
    private Integer user_id;
    private String convo_title;
    private String convo_content;
    private Integer convo_status;
    private Timestamp create_time;
    private Timestamp update_time;
    private String metadata;
}
