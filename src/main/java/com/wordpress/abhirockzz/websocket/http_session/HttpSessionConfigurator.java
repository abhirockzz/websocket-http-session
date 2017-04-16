package com.wordpress.abhirockzz.websocket.http_session;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        System.out.println("modifyHandshake() Current thread " + Thread.currentThread().getName());
        String user = request.getParameterMap().get("user").get(0);
        sec.getUserProperties().put(user, request.getHttpSession());
        System.out.println("modifyHandshake() User " + user + " with http session ID " + ((HttpSession) request.getHttpSession()).getId());
    }

}
