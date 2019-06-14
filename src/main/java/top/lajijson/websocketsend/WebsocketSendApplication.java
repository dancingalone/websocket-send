package top.lajijson.websocketsend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lajijson.websocketsend.server.WebSocket;

@SpringBootApplication
@RestController
@RequestMapping("/socket")
public class WebsocketSendApplication {

    @Autowired
    private WebSocket webSocket;

    public static void main(String[] args) {
        SpringApplication.run(WebsocketSendApplication.class, args);
    }

    @RequestMapping("/")
    public void send(String msg) {
        System.out.println("send :" + msg);
        webSocket.sendMessage(msg);
    }
}
