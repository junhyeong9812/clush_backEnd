//package backEnd.clush_backEnd.webSocket;
//
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//public class MyWebSocketHandler extends TextWebSocketHandler {
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // 타임아웃 설정 (예: 30분)
//        session.setTextMessageSizeLimit(8192);
//        session.setIdleTimeout(30 * 60 * 1000L); // 30분
//    }
//
//    // 메시지 처리 로직
//}
