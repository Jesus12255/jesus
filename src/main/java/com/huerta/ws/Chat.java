
package com.huerta.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class Chat {

    private static Set<Session> sessiones = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        try {
            sessiones.add(session);
            session.getBasicRemote().sendText("{\"usuario\":\"sistema\", \"mensaje\":\"Conexión establecida\"}");
        } catch (IOException ex) {
        }
    }

    @OnMessage
    public void onMessage(String mensaje, Session session) {
        try {
            for (Session s : sessiones) {
                s.getBasicRemote().sendText( mensaje);
            }
            
        } catch (IOException ex) {
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessiones.remove(session);
    }
}
