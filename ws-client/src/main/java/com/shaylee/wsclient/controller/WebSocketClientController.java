package com.shaylee.wsclient.controller;

import com.shaylee.wsclient.client.SocketClient;
import com.shaylee.wsclient.client.SocketSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title: WebSocket客户端控制器
 *
 * @author Adrian
 * @date 2020-05-12
 */
@Slf4j
@RestController
@RequestMapping("/ws/client")
public class WebSocketClientController {

    public static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @RequestMapping("/connect/{userName}")
    public void connect(@PathVariable("userName") String userName) {
        SocketClient socketConnect = new SocketClient();
        Session session = socketConnect.connect("ws://127.0.0.1:8086/socketServer/" + userName);
        sessionMap.put(userName, session);
        log.info("创建WebsocketClient后，客户端连接状态:【{}】", SocketSender.isOpen(session));
    }

    @RequestMapping("/send/{userName}")
    public void send(@PathVariable("userName") String userName) {
        Session session = sessionMap.get(userName);
        String text = userName + "测试发送 ABC ... ";
        SocketSender.sendTextMessage(session, text);
    }

    @RequestMapping("/close/{userName}")
    public void close(@PathVariable("userName") String userName) {
        Session session = sessionMap.get(userName);
        SocketSender.close(session);
    }
}
