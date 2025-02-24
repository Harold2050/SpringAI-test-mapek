package com.example.testforopenai.tool;

import java.io.*;
import java.util.*;
public class ChatSplitter {
    /**
     * 读取聊天文件并将用户和助手的对话分开存储
     *
     * @param filePath 聊天文件的路径
     * @return 包含两个列表的Map，分别存储用户对话和助手对话
     *         "userChats" -> 用户对话
     *         "assistantChats" -> 助手对话
     */
    public Map<String, List<String>> splitChat(String filePath) {
        // 用来存储用户和助手的对话
        List<String> userChats = new ArrayList<>();
        List<String> assistantChats = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder userMessage = new StringBuilder();
            StringBuilder assistantMessage = new StringBuilder();
            boolean isUser = false;

            // 逐行读取文件
            while ((line = br.readLine()) != null) {
                if (line.startsWith("USER:")) {
                    // 存储上一个助手的回答
                    if (assistantMessage.length() > 0) {
                        assistantChats.add(assistantMessage.toString().trim());
                        assistantMessage.setLength(0);  // 清空
                    }
                    // 存储当前用户的消息
                    userMessage.setLength(0);  // 清空
                    userMessage.append(line.substring(5).trim());  // 提取USER后的内容
                    isUser = true;
                } else if (line.startsWith("ASSISTANT:")) {
                    // 存储当前助手的回答
                    if (isUser) {
                        userChats.add(userMessage.toString().trim());
                        isUser = false;
                    }
                    assistantMessage.append(line.substring(10).trim());  // 提取ASSISTANT后的内容
                }
            }

            // 存储最后一条助手的回答
            if (assistantMessage.length() > 0) {
                assistantChats.add(assistantMessage.toString().trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将分开的对话返回为Map
        Map<String, List<String>> result = new HashMap<>();
        result.put("userChats", userChats);
        result.put("assistantChats", assistantChats);
        return result;
    }

    public static void main(String[] args) {
        // 测试API
        ChatSplitter chatSplitter = new ChatSplitter();
        String filePath = "src/main/resources/ChatMemory/chat.txt";

        Map<String, List<String>> chats = chatSplitter.splitChat(filePath);

        // 输出结果
        System.out.println("User Chats:");
        for (String chat : chats.get("userChats")) {
            System.out.println(chat);
        }

        System.out.println("\nAssistant Chats:");
        for (String chat : chats.get("assistantChats")) {
            System.out.println(chat);
        }
    }
}
