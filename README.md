## To run

- `git clone` the project and execute `mvn clean install`
- Deploy `websocket-http-session.war` in `target` directory to any of the [Java EE 7 containers](http://www.oracle.com/technetwork/java/javaee/overview/compatibility-jsp-136984.html)

## To check

- Connect to the WebSocket endpoint `ws://localhost:8080/websocket-http-session/abhi/` (`abhi` is the user). You'll see a [response](https://github.com/abhirockzz/websocket-http-session/blob/master/src/main/java/com/wordpress/abhirockzz/websocket/http_session/Service.java#L21) such as `User abhi | WebSocket session ID 6e4b4986-2f25-4fb7-8a48-d08d8aeec66e | HTTP session ID 7cabd435d20caf3d2a6a1c6a95a4`
- Simulate another user (gitu) `ws://localhost:8080/websocket-http-session/abhi/`. You'll get back details of the HTTP and WebSocket session IDs e.g. `User gitu | WebSocket session ID 8b61fc99-efb7-476b-a12f-2c0d9d432395 | HTTP session ID 7d521c624aaca2127c6331f87253`

## Test

You can also execute a simple test located in the [`test`](https://github.com/abhirockzz/websocket-http-session/blob/master/test/src/test/java/com/wordpress/abhirockzz/websocket/http_session/test/WebSocketEndpointConcurrencyTest.java#L58) project. It's built using the WebSocket client API

- `cd <dir>\test`
- `mvn test`
