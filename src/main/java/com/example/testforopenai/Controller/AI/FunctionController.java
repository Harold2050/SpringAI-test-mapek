package com.example.testforopenai.Controller.AI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionController {

    public record TestObject(String a, String b) {}


    @Bean
    @Description("微服务组合")
    //Function<T,R>表示接收T参数，返回R参数。Function内部的apply方法则是：R apply(T t)。即返回R，以T为传入的形参
    public Function<TestObject,String> TestFunction() {
        return request -> {
            //apply调用方法
            System.out.println("first parameter:"+request.a()+"\n"+"second parameter:"+request.b());
            return "Test Function successfully!";
        };  //注意这里要加“;”
    }

    @Bean
    @Description("演化计划")
    //Function<T,R>表示接收T参数，返回R参数。Function内部的apply方法则是：R apply(T t)。即返回R，以T为传入的形参
    public Function<TestObject,String> TestFunction2() {
        return request -> {
            //apply调用方法
            System.out.println("first parameter:"+request.a()+"\n"+"second parameter:"+request.b());
            return "Test Function successfully!";
        };  //注意这里要加“;”
    }


    //上面用到了lambda表达式。Lambda 表达式的主要作用是为 函数式接口 提供实现。
    //而Function就是一个函数式接口，它有一个 apply 方法
    //什么是函数式接口？   1，接口中 只能有一个抽象方法。2，可以有多个默认方法（default 方法）或静态方法。
    //抽象方法就是接口中我们最常用的，中没有方法体，且必须在实现类中被重写的方法。
    //默认方法就是接口中必须有方法体（即提供默认实现），且使用 default 关键字修饰的方法。实现接口的类可以选择重写默认方法，也可以直接使用默认实现。

    //Lambda表达式的一般形式：(parameters) -> expression。parameters 是 Lambda 表达式的输入参数。expression执行实际的操作。
    //比如：(int x, int y) -> x + y;

    //request实际上只是一个名称，可以变成任何，比如var。
    //request 是 Lambda 表达式的输入参数，它是 T 类型的对象

    //可能会有疑惑：TestFunction 方法的返回值类型是 Function<TestObject, String>，
    // 而这里使用了 request -> { ... } 作为返回值。这可能让你感到困惑
    //但是Function<TestObject, String> 表示一个接受 String 类型参数
    // 并返回 String 类型结果的函数。而request -> { ... } 实际上就是实现了这个。
    // request就是传进去的TestObject类型参数。在{ ... }实现apply方法，并要return 一个string。
    //所以：request -> { ... } 是一个 Lambda 表达式，它表示一个实现了 Function<TestObject, String> 接口的对象。





    //所以如果不使用Lambda表达式，TestFunction方法就是下面这样实现的，即匿名类
	/*
	public Function<TestObject, String> TestFunction() {
		return new Function<TestObject, String>() {
			@Override
			public String apply(TestObject request) {
				System.out.println("first parameter:"+request.a()+"second parameter:"+request.b());
                return "Test Function successfully!";
			}
		};
	}
	*/

}
