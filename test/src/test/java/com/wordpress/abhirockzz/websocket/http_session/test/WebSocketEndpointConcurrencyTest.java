package com.wordpress.abhirockzz.websocket.http_session.test;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Test;


public class WebSocketEndpointConcurrencyTest {

    public WebSocketEndpointConcurrencyTest() {
    }

    public class TestClient extends Endpoint {

        @Override
        public void onOpen(Session sn, EndpointConfig ec) {
            try {
                sn.addMessageHandler(String.class, new MessageHandler.Whole<String>() {
                    @Override
                    public void onMessage(String m) {
                        System.out.println("got message from server - " + m);
                    }
                });
            } catch (Exception ex) {
                Logger.getLogger(WebSocketEndpointConcurrencyTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void onClose(Session session, CloseReason closeReason) {
            System.out.println("Session " + session.getId() + " closed");
        }

    }

    CountDownLatch threadStartLatch = new CountDownLatch(1);
    final static int NUM_OF_USERS = 3;

    @Test
    public void test() throws InterruptedException, DeploymentException, IOException {
        final WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        final TestClient testClient = new TestClient();

        Runnable connect = new Runnable() {
            @Override
            public void run() {
                try {
                    threadStartLatch.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WebSocketEndpointConcurrencyTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    System.out.println(Thread.currentThread().getName() + " trying to establish connection with server");
                    Session session = webSocketContainer.connectToServer(testClient,
                            ClientEndpointConfig.Builder.create().build(),
                            URI.create("ws://localhost:8080/websocket-http-session/" + Thread.currentThread().getName() + "/"));
                            //URI.create("ws://localhost:8080/websocket-http-session/test/"));

                    System.out.println(Thread.currentThread().getName() + " connection established with server. ID " + session.getId());
                } catch (Exception ex) {
                    Logger.getLogger(WebSocketEndpointConcurrencyTest.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        
        ExecutorService es = Executors.newFixedThreadPool(NUM_OF_USERS);
        
        for (int i = 1; i <= NUM_OF_USERS; i++) {
            es.submit(connect);
        }

        threadStartLatch.countDown();
        CountDownLatch wait = new CountDownLatch(1);
        wait.await(NUM_OF_USERS, TimeUnit.SECONDS);
        wait.countDown();
    }
}
