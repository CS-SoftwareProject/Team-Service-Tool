package TT.domain.project;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class WebsocketChat {
  private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

  @OnMessage
  public void onMessage(String message, Session session) throws IOException {
    synchronized (clients) {
      for (Session client : clients) {
        if (!client.equals(session)) {
          System.out.println("브로드캐스팅");

          client.setMaxTextMessageBufferSize(10000000);
          client.getBasicRemote().sendText(message);
        }
      }
    }
  }

  @OnOpen
  public void onOpen(Session session) throws IOException {
    System.out.println("세션추가" + session);
    clients.add(session);
  }

  @OnClose
  public void onClose(Session session) throws IOException {
    System.out.println("세션제거" + session);
    clients.remove(session);
  }

  @OnError
  public void onErr(Session session, Throwable t) throws IOException {}
}
