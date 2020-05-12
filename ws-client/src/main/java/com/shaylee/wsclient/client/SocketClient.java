package com.shaylee.wsclient.client;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

/**
 * Title: websocket客户端
 *
 * @author Adrian
 * @date 2020-05-12
 */
@Slf4j
@ClientEndpoint
public class SocketClient {

    public Session connect(String wsPath) {
        log.info("Websocket 路径 【{}】", wsPath);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        return this.connect(container, wsPath);
    }

    public Session connect(WebSocketContainer container, String wsPath) {
        try {
            Session session = container.connectToServer(this, URI.create(wsPath));
            log.info("连接服务器, 当前会话ID:【{}】", session.getId());
            return session;
        } catch (DeploymentException | IOException e) {
            log.error("", e);
        }
        return null;
    }

    @OnOpen
    public void onOpen(Session userSession) {
        log.info("连接ws服务器成功, sessionId: 【{}】", userSession.getId());
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        log.info("【{}】连接关闭, 原因:【{}】", userSession.getId(), reason);
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("收到信息, 内容:【{}】", message);
    }
}
