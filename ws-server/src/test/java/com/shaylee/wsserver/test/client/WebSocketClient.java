package com.shaylee.wsserver.test.client;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
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
public class WebSocketClient {

    private Session session;

    public WebSocketClient(String path) {
        log.info("Websocket 路径 【{}】", path);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            this.session = container.connectToServer(this, URI.create(path));
        } catch (DeploymentException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public WebSocketClient(WebSocketContainer container, String wsPath) {
        try {
            this.session = container.connectToServer(this, URI.create(wsPath));
            log.info("连接服务器, 返回session, 会话ID:[{}]", session.getId());
        } catch (DeploymentException | IOException e) {
            log.error("", e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        log.info("连接ws服务器成功, sessionId: [{}]", userSession.getId());
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {

    }

    @OnMessage
    public void onMessage(String message) {
        log.info("收到信息, 内容:【{}】", message);
    }

    public void sendTextMessage(String message) {
        log.info("发送信息:【{}】", message);
        RemoteEndpoint.Async async = this.session.getAsyncRemote();
        async.sendText(message);
    }

    /**
     * 连接是否打开
     * */
    public boolean isOpen() {
        return this.session != null && this.session.isOpen();
    }

    public void close() {
        try {
            this.session.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
