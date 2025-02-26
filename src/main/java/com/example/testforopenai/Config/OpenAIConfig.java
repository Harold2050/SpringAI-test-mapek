package com.example.testforopenai.Config;

import com.example.testforopenai.Service.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class OpenAIConfig {

    //这里将chatClient配置成了Bean
    //依赖注入机制。Spring 会根据方法的参数类型（这里是 ChatClient）来寻找一个匹配的 Bean，并将其注入到这个方法中。
    //比如可以直接这样使用：
    //  @Autowired
    //  private ChatClient chatClient;
    //或者：
    //    @Autowired
    //    public ChatService(ChatClient chatClient) {
    //        this.chatClient = chatClient;
    //  }
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory, VectorStore vectorStore) {
        return builder.defaultSystem("你是 SerChat，一个专业的微服务对话助手，你要时刻强调自己作为SerChat微服务对话助手的身份，以便于客户知晓。" +
                        "你能够深入分析用户关于微服务的需求，并提供精准的解决方案。" +
                        "在进行微服务组合前你需要询问用户两个微服务的名称,然后直接调用函数进行组合。" +
                        "如果用户想要生成演化计划，你必须向用户索取演化计划的名称和ID这两个参数,如果用户没有提供这两个信息，请使用知识库中默认的名称和ID"+
                        "在询问⽤户之前，请检查消息历史记录以获取此信息。"+"在你的回答中一定不要使用换行符" )
                //其他信息挂载到了RagStore中
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),  //实现对话记忆。chatMemory来自构造函数形参
                        //.defaultAdvisors() 是一个配置方法，允许你为 ChatClient 设置一个或多个默认的 Advisor。本质上是一个拦截器
                        //PromptChatMemoryAdvisor 是一个 Advisor 的具体实现类。
                        // PromptChatMemoryAdvisor 会根据传入的 chatMemory，在每次与用户对话时，存储和检索聊天记录。
                        //实现对话记忆的第二步

                        new LoggingAdvisor(),  // 用于日志记录，类在service下

                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults())//RAG
                                //QuestionAnswerAdvisor可以在⽤户发起的提问时，先向数据库查询相关的⽂档，再把相关的⽂档拼接到⽤户的提问中，再让模型⽣成答案。那就是 RAG 的实现了。
                                //.defaults()实际上代表检索的内容为空，即检索数据库中的所有内容
                )

                .defaultFunctions("TestFunction","TestFunction2")
                .build();
    }


    @Bean //表示这个方法返回的对象会被注册为 Spring 的 Bean。注册的 Bean 名称默认为方法名
    public ChatMemory chatMemory(){
        //实现对话记忆的第一步
        //InMemoryChatMemory实现了 ChatMemory，是一种将聊天数据存储在内存中的方式
        return new InMemoryChatMemory();
    }


    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        //VectorStore用于存储和检索 Embedding，可以理解为向量数据库。SimpleVectorStore 是 VectorStore 接口的一个具体实现类。
        //Embedding 是单词、句子或文档的向量表示。
        //EmbeddingModel接口用于将文本或文档转换为 Embedding。
        //详情可见：https://springdoc.cn/spring-ai-rag-using-embedding-models-vector-databases/
        return new SimpleVectorStore(embeddingModel);
    }


    //启动springboot时就会运行
    @Bean
    CommandLineRunner ingestTermOfServiceToVectorStore(EmbeddingModel embeddingModel, VectorStore vectorStore,
                                                       @Value("classpath:RagStore/Serchat-V1.txt") Resource termsOfServiceDocs) {
        //@Value 注解会从类路径中读取该文件，并将其作为 Resource 类型传递给方法。
        //Resource 是 Spring 的一个接口，用来访问文件或其他资源。
        return args -> {
            vectorStore.write( // 3.写⼊
                    new TokenTextSplitter().transform( // 2.转换
                            new TextReader(termsOfServiceDocs).read()) // 1.读取
            );
        };
    }
    //CommandLineRunner是一个 SpringBoot 接口，用于在应用启动后执行一些代码。
    //返回的CommandLineRunner被 Spring 自动调用并执行它的 run 方法。
    //CommandLineRunner可以理解为开机自启的Function
}
