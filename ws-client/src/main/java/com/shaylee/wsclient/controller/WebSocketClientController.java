package com.shaylee.wsclient.controller;

import com.shaylee.wsclient.client.SocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public static Map<String, SocketClient> map = new ConcurrentHashMap<>();

    @RequestMapping("/connect/{userName}")
    public void connect(@PathVariable("userName") String userName) {
        SocketClient client = new SocketClient("ws://127.0.0.1:8086/socketServer/" + userName);
        map.put(userName, client);
        log.info("创建WebsocketClient后，客户端连接状态:【{}】", client.isOpen());
    }

    @RequestMapping("/send/{userName}")
    public void send(@PathVariable("userName") String userName) {
        SocketClient client = map.get(userName);
        client.sendTextMessage("测试发送 ABC ... ");
    }

    @RequestMapping("/close/{userName}")
    public void close(@PathVariable("userName") String userName) {
        SocketClient client = map.get(userName);
        client.close();
    }
}
