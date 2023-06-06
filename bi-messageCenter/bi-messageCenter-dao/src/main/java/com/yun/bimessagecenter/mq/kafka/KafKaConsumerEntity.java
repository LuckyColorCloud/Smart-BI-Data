package com.yun.bimessagecenter.mq.kafka;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.yun.bimessagecenter.entity.MqConfigEntity;
import com.yun.bimessagecenter.service.TopicService;
import com.yun.bimessagecenter.websocket.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 卡夫卡消费者
 *
 * @author Yun
 */
@Slf4j
public class KafKaConsumerEntity {
    /**
     * 消费者
     */
    private KafkaConsumer<String, String> consumer = null;
    /**
     * 键子对
     */
    private Map<String, String> map = new ConcurrentHashMap<String, String>();
    /**
     * 是否初始化成功
     */
    private boolean success = false;
    /**
     * 线程
     */
    private Thread thread = null;
    /**
     * 轮询
     */
    private int poll = 100;
    /**
     * 等待时间
     */
    private int sleep = 1000;

    private TopicService topicService = SpringUtil.getBean(TopicService.class);

    /**
     * 执行
     */
    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public void execute() {
        if (thread == null) {
            thread = new Thread(() -> {
                try {
                    processRecords();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }

    /**
     * 动态添加 主题
     *
     * @param topic
     * @param
     * @throws InterruptedException
     */
    public void subscribe(String path, String topic) {
        //订阅该主题中的所有分区。此处可以使用 \'assign\' 而不是 \'subscribe\' 来订阅特定分区。
        map.put(topic, path);
        consumer.subscribe(map.keySet());
    }

    /**
     * 动态删除 主题
     *
     * @param topic
     * @param
     * @throws InterruptedException
     */
    public void rmTopic(String topic) {
        //订阅该主题中的所有分区。此处可以使用 \'assign\' 而不是 \'subscribe\' 来订阅特定分区。
        map.remove(topic);
        consumer.subscribe(map.keySet());
    }

    /**
     * 初始化
     *
     * @param mqConfig mq的配置项
     */
    public void init(MqConfigEntity mqConfig) {
        if (consumer == null) {
            if (JSONUtil.isTypeJSONObject(mqConfig.getConfig())) {
                consumer = new KafkaConsumer(JSONUtil.toBean(mqConfig.getConfig(), Properties.class));
                success = true;
                poll = mqConfig.getPoll();
                sleep = mqConfig.getTime();
            } else {
                log.error("初始化失败!!! 失败参数:{}", mqConfig.getConfig());
            }
        }
    }

    /**
     * 执行任务
     *
     * @throws InterruptedException
     */
    private void processRecords() throws InterruptedException {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(poll));
            for (ConsumerRecord<String, String> record : records) {
                if (StrUtil.isNotEmpty(record.value())) {
                    String path = this.map.get(record.topic());
                    String value = record.value();
                    try {
                        WebSocket.sendMessage(value, path);
                    } catch (Exception e) {
                        WebSocket.sendMessage(record.value(), path);
                    }
                }
                log.info("接收到消息:{}", record.value());
            }
            Thread.sleep(sleep);
        }
    }

    /**
     * 判断是否成功
     *
     * @return 是否
     */
    public boolean isSuccess() {
        return success;
    }

}