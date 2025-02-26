package com.example.testforopenai.Tool;

import java.io.*;
import java.util.*;
public class ChatSplitterForLine {
    /**
     * 读取聊天文件，将每行对话分割存入一个List中
     *
     * @param filePath 聊天文件的路径
     * @return 存储每行对话的List<String>
     */
    public List<String> parseChatFile(String filePath) {
        List<String> chats = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // 逐行读取文件
            while ((line = br.readLine()) != null) {
                // 去除行首和行尾的空格
                line = line.trim();

                if (line.startsWith("USER:") || line.startsWith("ASSISTANT:")) {
                    // 将以"USER:"或"ASSISTANT:"开头的对话存入List
                    chats.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chats;
    }

    public static void main(String[] args) {
        // 测试API
        ChatSplitterForLine chatParser = new ChatSplitterForLine();
        String filePath = "src/main/resources/ChatMemory/chat.txt";

        List<String> chats = chatParser.parseChatFile(filePath);

        // 逐行输出所有对话
        int index = 1;
        System.out.println("Chats:");
        for (String chat : chats) {
            System.out.println(index+"."+chat);
            index++;
        }
    }
}
