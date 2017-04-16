package com.wordpress.abhirockzz.websocket.http_session;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/{user}/" , configurator = HttpSessionConfigurator.class)
public class Service {
    
    HttpSession httpSession;
    
    @OnOpen
    public void opened(@PathParam("user") String user, Session session, EndpointConfig config) throws IOException{
        System.out.println("opened() Current thread "+ Thread.currentThread().getName());
        this.httpSession = (HttpSession) config.getUserProperties().get(user);
        System.out.println("User joined "+ user + " with http session id "+ httpSession.getId());
        String response = "User " + user + " | WebSocket session ID "+ session.getId() +" | HTTP session ID " + httpSession.getId();
        System.out.println(response);
        session.getBasicRemote().sendText(response);
    }
    
}
