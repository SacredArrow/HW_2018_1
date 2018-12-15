package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import servlets.EmbeddedAsyncServlet;

import java.util.concurrent.TimeUnit;

@SuppressWarnings ("UnusedDeclaration")
@WebSocket
public class ChatWebSocket {
    private Session session;
    private String id = "";

    @OnWebSocketConnect
    public void onOpen(Session session)  {
        this.session = session;
//        sendString("hello there");
        System.out.println("Socket started");

    }

    @OnWebSocketMessage
    public void onMessage(String data) throws InterruptedException {
        while (session == null){TimeUnit.MILLISECONDS.sleep(100);}
        this.id = data;
        System.out.println(data);
        Integer progress = null;
        while (progress == null) {
            progress = EmbeddedAsyncServlet.map.get(id);
        }
        do {
            progress = EmbeddedAsyncServlet.map.get(id);
            sendString(Integer.toString(progress));
            System.out.println("sent " + progress);
            TimeUnit.SECONDS.sleep(1);
        } while (progress != 100);
        sendString(Integer.toString(100));
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
    }

    private void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
