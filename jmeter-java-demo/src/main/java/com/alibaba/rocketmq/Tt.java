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
public class Tt {

    private final static DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");

    public static void main(String[] args) {
        int messageSize = 27;
        String result = "";
        String base = "abcdefghijklmnopqrstuvwxyz";

        if (base.length() > messageSize) {
            result = base.substring(0, messageSize);
        } else {
            int x = messageSize / base.length();
            int m = messageSize % base.length();
            for (int i = 0; i < x; i++) {
                result += base;
            }
            result += base.substring(0, m);
        }

        System.out.println(result);

    }







































}
