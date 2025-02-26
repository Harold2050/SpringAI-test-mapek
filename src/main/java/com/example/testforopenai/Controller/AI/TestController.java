package com.example.testforopenai.Controller.AI;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

//@RestController是@Controller  +  @ResponseBody(将返回值放在response体内)
//@RequestBody 则是通常用于控制器方法的参数上，Spring 会自动将请求体中的数据转换为指定的 Java 对象。
//用户传过来的可能是：{
//  "name": "John",
//  "age": 30
//}
//那么：public String createUser(@RequestBody User user) {
//        return "User created: " + user.getName();
//   }
@RestController
@RequestMapping("/ai")
public class TestController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatModel chatModel;

    @GetMapping("/chat")
    String generation(@RequestParam(value="message",defaultValue = "你好") String input) {

        return this.chatClient.prompt()    //prompt() 方法的作用是生成一个新的聊天对象，以便于后续链式方法的展开
                .user(input)
                .advisors(advisorSpec -> advisorSpec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY,100))
                //设置对话记忆长度为100条聊天记录
                //实现对话记忆的第三步
                .call()
                .content();
    }

    //value="/stream": 这是请求的 URL 路径。当客户端发起一个 GET 请求，且请求路径为 /stream 时，该方法将会被调用。
    //produces 属性用于指定该方法返回的数据类型(可能只有.stream需要这么设置，.call不需要)
    //"text/html": 表示返回的内容类型是 HTML 格式。也就是说，客户端期望接收到 HTML 格式的响应数据。
    //charset=UTF-8: 表示响应的字符编码为 UTF-8，确保处理和显示非 ASCII 字符时的正确编码。
    @GetMapping(value="/stream",produces = "text/html;charset=UTF-8")
    Flux<String> stream(@RequestParam(value="message",defaultValue = "你好") String input) {
        return this.chatClient.prompt()    //prompt() 方法的作用是生成一个新的聊天对象，以便于后续链式方法的展开
                .user(input)
                .system("你是元神哥，可以帮助用户处理元神相关的事物，你要时刻向用户提示这一点。")  //会覆盖掉全局设置
                //如果不想在chatclient构建的时候全局设定，就可以在这里设定。这样每个方法都有不同角色预设
                .advisors(advisorSpec -> advisorSpec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY,100))
                .stream()
                .content();
    }



    //总结一下就是，chatclient更方便，chatmodel稍作封装就是chatclient
    //如果只想要一个对话的功能，用chatclient最好。如果希望使用一些大模型特有的功能，那就只能使用chatmodel
    //chatmodel的使用与大模型种类相关的！切换模型时请注意！（这里使用的是智谱，因为测试一下他的文生图）

    /*

    @GetMapping("/model")
    String chatmodel(@RequestParam(value="message",defaultValue = "你好") String input) {
        //ChatResponse包含了模型的生成结果
        ChatResponse response = chatModel.call(    //.call(...) 方法用于发起请求。它会将一个 Prompt 对象传递给聊天模型
                new Prompt(       //Prompt 是一个用于封装用户输入和模型配置的对象。他接收两个参数：用户输入，用于配置聊天模型行为的选项对象
                        input,      //用户输入！！！
                        ZhiPuAiChatOptions.builder()  //使用 builder() 方法可以构建一个新的 ZhiPuAiChatOptions 对象
                                .withModel(ZhiPuAiApi.ChatModel.GLM_3_Turbo.getValue())
                                .withTemperature(0.7)    //with开头的都是设置参数的方法
                                .build()        //build() 方法结束了链式调用，并返回构造好的 ZhiPuAiChatOptions 对象
                ));
        return response.getResult().getOutput().getContent();
    }

    */

}
