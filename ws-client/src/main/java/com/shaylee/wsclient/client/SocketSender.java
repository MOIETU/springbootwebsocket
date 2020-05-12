package com.shaylee.wsclient.client;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;

/**
 * Title: 发送工具
 *
 * @author Adrian
 * @date 2020-05-12
 */
@Slf4j
public class SocketSender {

    public static void sendTextMessage(Session session, String message) {
        log.info("发送信息:【{}】", message);
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.sendText(message);
    }

    public static boolean isOpen(Session session) {
        return session != null && session.isOpen();
    }

    public static void close(Session session) {
        try {
            session.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
