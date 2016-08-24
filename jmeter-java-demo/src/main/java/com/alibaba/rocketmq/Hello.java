package com.alibaba.rocketmq;

/**
 * Created by wuxing on 16/7/25.
 */
public class Hello {
    public String sayHello()
    {
        return "Hello";
    }
    public String sayHelloToPerson(String s)
    {
        if(s == null || s.equals(""))
            s = "nobody";
        return (new StringBuilder()).append("Hello ").append(s).toString();
    }
    public int sum(int a,int b)
    {
        return a+b;
    }
}
