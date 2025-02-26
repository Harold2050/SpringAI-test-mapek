package com.example.testforopenai.Service;

import com.example.testforopenai.Entity.Bridge;
import org.springframework.ai.chat.client.RequestResponseAdvisor;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Component
public class LoggingAdvisor implements RequestResponseAdvisor {

    private int number=Bridge.number;

    @Override
    public AdvisedRequest adviseRequest(AdvisedRequest request, Map<String, Object> context) {

        if(number!=Bridge.number){
            number = Bridge.number;
            request.systemParams().put("memory",Bridge.text);
        }

        Object chatmemory = request.systemParams().get("memory");
        System.out.println("===============================================\n");
        System.out.println("对话记忆：\n" + chatmemory);
        System.out.println("===============================================\n");


        // 将对话记忆写入文件
        writeStringToFile(chatmemory.toString(), "src/main/resources/ChatMemory/chat.txt");
        //System.out.println(request);
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
