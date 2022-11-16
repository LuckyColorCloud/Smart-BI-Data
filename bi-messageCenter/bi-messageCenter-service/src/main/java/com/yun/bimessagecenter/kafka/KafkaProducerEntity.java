package com.yun.bimessagecenter.kafka;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author Yun
 */
@Slf4j
public class KafkaProducerEntity {
    /**
     * 生产者
     */
    private Producer<String, String> producer = null;
    /**
     * 是否初始化成功
     */
    private boolean success = false;

    /**
     * 不需要加锁 单个对象即单个客户端理论上没有多线程问题
     * 这里的判空只是防止 重复没必要的初始化
     *
     * @param jsonConfig jsonObject的配置
     */
    public void init(String jsonConfig) {
        if (producer == null) {
            if (JSONUtil.isTypeJSONObject(jsonConfig)) {
                this.producer = new KafkaProducer(JSONUtil.toBean(jsonConfig, Properties.class));
                success = true;
            } else {
                log.error("初始化失败!!! 失败参数:{}", jsonConfig);
            }
        }
    }

    /**
     * 发送消息
     *
     * @param topic     主题
     * @param partition broker.id
     * @param key       key
     * @param value     value
     * @throws InterruptedException
     */
    public void sendMessages(String topic, Integer partition, String key, String value) throws InterruptedException {
        this.producer.send(
                new ProducerRecord<String, String>(topic, partition, key, value));
        // 允许生产者在程序退出前完成消息的发送
        Thread.sleep(20);
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