package com.alibaba.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wuxing on 16/7/25.
 */
public class Producer {

    private final static DefaultMQProducer producer = new DefaultMQProducer("Producer");
    private static String msgKey;
    private static String msgBody;

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        final int W = 10000;
        final int K = 1000;
        final int B = 100;
//        if (args.length >= 1) {
//            msgKey = args[0];
//        } else {
//            System.exit(0);
//        }
//        Integer threadCount = args.length >= 2 ? Integer.parseInt(args[1]) : 2*K;
//        final Integer messageSize = args.length >= 3 ? Integer.parseInt(args[2]) : 1024*1;
//        final Integer onceTimeNum = args.length >= 4 ? Integer.parseInt(args[3]) : 1*W;
//        final Integer sleepTime = args.length >= 5 ? Integer.parseInt(args[4]) : 1;
        Integer threadCount = args.length >= 2 ? Integer.parseInt(args[1]) : 2;
        final Integer messageSize = args.length >= 3 ? Integer.parseInt(args[2]) : 1024*1;
        final Integer onceTimeNum = args.length >= 4 ? Integer.parseInt(args[3]) : 2;
        final Integer sleepTime = args.length >= 5 ? Integer.parseInt(args[4]) : 1;

        buildMsgBody(messageSize);
        String namesrvAddr = "114.55.108.114:9876";
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();

        final ExecutorService sendThreadPool = Executors.newFixedThreadPool(threadCount);
        for(int i = 0; i < threadCount; i++) {
            sendThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(sleepTime);
                        for (int i = 0; i < onceTimeNum; i++) {
                            testStress01();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static void testStress01() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        Message msg = buildMessage();

        SendResult sendResult = producer.send(msg);
        LocalTransactionExecuter tranExecuter=new LocalTransactionExecuter() {

            @Override
            public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
                return null;
            }
        };
        System.out.println(sendResult);
    }

    private static Message buildMessage() {
        String topic = "TopicTest";
        String tag = "tagA";
        String key = (topic.hashCode() + new Random().nextInt(10000)) + "";
        Message message = new Message(topic,
                tag,
                key,
                (msgBody.getBytes())
        );
        return message;
    }

    private static void buildMsgBody(Integer messageSize) {
        String base = "abcdefghijklmnopqrstuvwxyz";

        if (base.length() > messageSize) {
            msgBody = base.substring(0, messageSize);
        } else {
            int x = messageSize / base.length();
            int m = messageSize % base.length();
            for (int i = 0; i < x; i++) {
                msgBody += base;
            }
            msgBody += base.substring(0, m);
        }
    }
}
