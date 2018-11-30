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

    @OnWebSocketConnect
    public void onOpen(Session session) throws InterruptedException {
        this.session = session;
//        sendString("hello there");
        do {
            sendString(Integer.toString((int)EmbeddedAsyncServlet.progress));
            System.out.println("sent" + (int)EmbeddedAsyncServlet.progress);
            TimeUnit.SECONDS.sleep(1);
        } while (EmbeddedAsyncServlet.progress != 100);
        sendString(Integer.toString((int)EmbeddedAsyncServlet.progress));
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        sendString(data);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
