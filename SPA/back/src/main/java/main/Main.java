package main;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.EmbeddedAsyncServlet;
import servlets.WebSocketChatServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        LogManager.getLogger("org.eclipse.jetty").setLevel(Level.WARN);
        Server server = new Server(8000);
//        ServletContextHandler context = new ServletContextHandler();
        ServletContextHandler context2 = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context2.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
        context2.addServlet(new ServletHolder(new EmbeddedAsyncServlet()), "/file");
        server.setHandler(context2);
        server.start();
        server.join();
    }


}