package top.lajijson.websocketsend.server;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token为前端连接时的标识，后端根据此token维护用户与webSocket的绑定
 */
@Component
@ServerEndpoint("/webSocket/{token}")
public class WebSocket {

    private Session session;

    private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam(value = "token") String token, Session session) {
        this.session = session;
        webSocketMap.put(token, this);
        System.out.println("new connect：" + token + ". total :" + webSocketMap.size());
    }

    @OnClose
    public void onClose(@PathParam(value = "token") String token) {
        webSocketMap.remove(token);
        System.out.println("disconnect:" + token + ", total:" + webSocketMap.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("get message: " + message);
    }

    public void sendMessage(String message) {
        // 根据业务逻辑，找到token指向的webSocket
        WebSocket webSocket = webSocketMap.get(String.valueOf(message.charAt(0)));
        // 找不到返回
        if (webSocket == null) {
            return;
        }
        try {
            webSocket.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
