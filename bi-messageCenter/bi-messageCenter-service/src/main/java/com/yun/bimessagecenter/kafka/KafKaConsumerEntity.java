package com.yun.bimessagecenter.kafka;

import cn.hutool.json.JSONUtil;
import com.yun.bimessagecenter.entity.MqConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

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
     * 是否初始化成功
     */
    private boolean success = false;
    /**
     * 轮询
     */
    private int poll = 100;
    /**
     * 等待时间
     */
    private int sleep = 1000;

    /**
     * 执行
     *
     * @throws InterruptedException
     */
    public void execute() throws InterruptedException {
        processRecords();
    }

    /**
     * 动态添加 主题
     *
     * @param topic
     * @throws InterruptedException
     */
    public void subscribe(String... topic) throws InterruptedException {
        //订阅该主题中的所有分区。此处可以使用 \'assign\' 而不是 \'subscribe\' 来订阅特定分区。
        consumer.subscribe(Arrays.asList(topic));
        processRecords();
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
            long lastOffset = 0;
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("\n\roffset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
                lastOffset = record.offset();
            }
            System.out.println("lastOffset read: " + lastOffset);
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