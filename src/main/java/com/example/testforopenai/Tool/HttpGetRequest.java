package com.example.testforopenai.Tool;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpGetRequest {

    // 公共方法，接收 URL 和 input 值，返回请求结果
    public  String sendGetRequest(String url, String input) throws Exception {
        // 拼接 URL 查询参数
        String encodedInput = encodeURIComponent(input);
        String fullUrl = url + "?message=" + encodedInput;

        // 创建HttpClient对象
        HttpClient client = HttpClient.newHttpClient();

        // 创建HTTP GET请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .build();

        // 发送请求并获取响应
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 返回响应体的内容
        return response.body();
    }

    // URL编码函数，确保参数能够被正确传输
    private static String encodeURIComponent(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    public static void main(String[] args) {
        HttpGetRequest httpGetRequest = new HttpGetRequest();
        try {
            // 示例：调用API发送GET请求
            String url = "http://localhost:8080/ai/chat";  // 替换为实际的URL
            String input = "222222222";  // 替换为实际的input值
            String result = httpGetRequest.sendGetRequest(url, input);
            // 输出返回的结果
            System.out.println("Response from API: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
