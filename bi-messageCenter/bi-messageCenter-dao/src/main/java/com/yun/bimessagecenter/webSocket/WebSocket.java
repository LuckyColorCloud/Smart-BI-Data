package com.yun.bimessagecenter.webSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yun
 */
@Slf4j
@ServerEndpoint(value = "/webSocket/{path}")
@Component
public class WebSocket {
    /**
     * 记录当前在线连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, ArrayList<Session>> clients = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        onlineCount.incrementAndGet(); // 在线数加1
        InetSocketAddress fieldInstance = (InetSocketAddress) getFieldInstance(session.getAsyncRemote(), "base#socketWrapper#socket#sc#remoteAddress");
        saveClient(session);
        log.info("有新连接加入客户端IP[{}]", fieldInstance.getHostName() + ":" + fieldInstance.getPort());
        log.info("有新连接加入：{}，当前在线人数为：{}", fieldInstance.getHostName() + ":" + fieldInstance.getPort(), onlineCount.get());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        onlineCount.decrementAndGet(); // 在线数减1
        InetSocketAddress fieldInstance = (InetSocketAddress) getFieldInstance(session.getAsyncRemote(), "base#socketWrapper#socket#sc#remoteAddress");
        clients.remove(fieldInstance.getHostName() + ":" + fieldInstance.getPort());
        log.info("有一连接关闭：{}，当前在线人数为：{}", fieldInstance.getHostName() + ":" + fieldInstance.getPort(), onlineCount.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        InetSocketAddress fieldInstance = (InetSocketAddress) getFieldInstance(session.getAsyncRemote(), "base#socketWrapper#socket#sc#remoteAddress");
        log.info("服务端收到客户端[{}]的消息:{}", fieldInstance.getHostName() + ":" + fieldInstance.getPort(), message);
        this.sendMessage(message, session);
    }

    private static Object getFieldInstance(Object obj, String fieldPath) {
        String fields[] = fieldPath.split("#");
        for (String field : fields) {
            obj = getField(obj, obj.getClass(), field);
            if (obj == null) {
                return null;
            }
        }
        return obj;
    }

    private static Object getField(Object obj, Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field;
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 保存客户端信息
     *
     * @param session
     */
    private void saveClient(Session session) {
        ArrayList<Session> orDefault = clients.getOrDefault(session.getRequestURI().getPath(), new ArrayList<Session>());
        orDefault.add(session);
        clients.put(session.getRequestURI().getPath(), orDefault);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        InetSocketAddress fieldInstance = (InetSocketAddress) getFieldInstance(session.getAsyncRemote(), "base#socketWrapper#socket#sc#remoteAddress");
        clients.remove(fieldInstance.getHostName() + ":" + fieldInstance.getPort());
        log.info("发生错误IP:{} 当前在线用户数:{}", fieldInstance.getHostName() + ":" + fieldInstance.getPort(), onlineCount.get());
        error.printStackTrace();
    }

    /**
     * 群发消息
     *
     * @param message 消息内容
     */
    private void sendMessage(String message, Session fromSession) {
        ArrayList<Session> orDefault = clients.getOrDefault(fromSession.getRequestURI().getPath(), new ArrayList<Session>());
        for (Session session : orDefault) {
            // 排除掉自己
            if (!fromSession.getId().equals(session.getId())) {
                InetSocketAddress fieldInstance = (InetSocketAddress) getFieldInstance(session.getAsyncRemote(), "base#socketWrapper#socket#sc#remoteAddress");
                log.info("服务端给客户端[{}]发送消息{}", fieldInstance.getHostName() + ":" + fieldInstance.getPort(), message);
                session.getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * kafka 消息推送
     *
     * @param message 推送内容
     * @param key     路径
     */
    public static void sendMessage(String message, String key) {
        ArrayList<Session> orDefault = clients.getOrDefault(key, new ArrayList<Session>());
        log.info("服务端给客户端[{}]发送消息{}", orDefault, message);
        for (Session session : orDefault) {
            session.getAsyncRemote().sendText(message);
        }
    }
}
