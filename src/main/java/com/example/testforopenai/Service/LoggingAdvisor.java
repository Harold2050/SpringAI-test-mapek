package com.example.testforopenai.Service;

import org.springframework.ai.chat.client.RequestResponseAdvisor;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class LoggingAdvisor implements RequestResponseAdvisor {
    @Override
    public AdvisedRequest adviseRequest(AdvisedRequest request, Map<String, Object> context) {
        //System.out.println("对话记忆如下\n"+request.systemParams().get("memory"));
        Object chatmemory = request.systemParams().get("memory");
        System.out.println("对话记忆：\n" + chatmemory);
        // 将对话记忆写入文件
        writeStringToFile(chatmemory.toString(), "src/main/resources/ChatMemory/chat.txt");
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
