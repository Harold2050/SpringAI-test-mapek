package com.example.testforopenai.Tool;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileClean {
    /**
     * 清空指定路径的文件内容
     *
     * @param filePath 文件的路径
     * @throws IOException 如果文件操作出错
     */
    public  void clearFile(String filePath) throws IOException {
        File file = new File(filePath);

        // 判断文件是否存在
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        // 使用FileWriter以覆盖模式打开文件，默认清空文件内容
        try (FileWriter fileWriter = new FileWriter(file)) {
            // 由于FileWriter以覆盖模式打开文件，文件内容会被清空
            // 不需要写任何内容，文件会被清空
        }
    }

    public static void main(String[] args) {
        FileClean fileClean = new FileClean();
        String filePath = "src/main/resources/ChatMemory/chat.txt";
        try {
            fileClean.clearFile(filePath);
            System.out.println("File content cleared successfully.");
        } catch (IOException e) {
            System.out.println("Error clearing file content: " + e.getMessage());
        }

    }
}
