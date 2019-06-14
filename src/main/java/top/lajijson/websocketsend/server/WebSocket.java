package top.lajijson.websocketsend.server;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        System.out.println("new connect. total :" + webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        System.out.println("disconnect, total:" + webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("get message: " + message);
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSocketSet) {
            System.out.println("radio ：" + message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
