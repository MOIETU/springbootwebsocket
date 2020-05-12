package com.shaylee.websocket.test.connect;

import com.shaylee.websocket.test.TestApplication;
import com.shaylee.websocket.test.client.WebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public class ConnectionTest {

	@Before
	public void setupContainer() {
		
	}
	
	@Test
	public void testConnection() {
		WebSocketClient client = new WebSocketClient("ws://127.0.0.1:8086/socketServer/UnitTestUser");
		
		log.info("创建WebsocketClient后，客户端连接状态:【{}】", client.isOpen());
		
		if (!client.isOpen()) {
			return;
		}
		
		try {
			for (int i = 0; i < 20; i++) {
				Thread.sleep(3000);
				client.sendTextMessage("测试发送 ABC ... ");
			}
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			log.error("", e);
		} finally {
			client.close();
		}
	}
}
