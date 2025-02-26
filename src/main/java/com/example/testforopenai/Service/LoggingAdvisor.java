package com.example.testforopenai.Service;

import com.example.testforopenai.Entity.Bridge;
import org.springframework.ai.chat.client.RequestResponseAdvisor;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class LoggingAdvisor implements RequestResponseAdvisor {
    private int flag = 0;
    private int number=Bridge.number;

    @Override
    public AdvisedRequest adviseRequest(AdvisedRequest request, Map<String, Object> context) {

        if(number!=Bridge.number){
            number = Bridge.number;
            request.systemParams().put("memory",Bridge.text);
        }


        /*
        if(flag == 0){
            request.systemParams().put("memory","USER:我的名字是alporis\n" +
                    "ASSISTANT:我是SerChat，一个专业的微服务对话助手。");
        }
        flag = 1;
        */


        Object chatmemory = request.systemParams().get("memory");
        System.out.println("对话记忆：\n" + chatmemory);
        // 将对话记忆写入文件
        writeStringToFile(chatmemory.toString(), "src/main/resources/ChatMemory/chat.txt");
//        System.out.println(request);
        return request;
    }

    @Override
    public int getOrder() {
        return 0;
    }


    // 新增方法：将字符串写入文件
    private void writeStringToFile(String content, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
