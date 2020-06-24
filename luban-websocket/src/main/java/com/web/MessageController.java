package com.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @MessageMapping("/content")
    @SendTo("/topic/message")
    public String broadcast(Message message) {
        byte[] bytes = (byte[]) message.getPayload(); // 客户端发送msg自动绑定到message, getPayload得到内容
        String content = new String(bytes);
        System.out.print(content);
        return "success: from Server side";
    }

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;  // Spring提供的服务端给客户端发消息的类

    // 服务端给客户端: 群发
    @GetMapping("/server-send")
    public void server2client() {
        simpMessagingTemplate.convertAndSend("/topic/message", "服务端给客户端，群发");
    }

    // 服务端给客户端: 点对点发送
    @GetMapping("/server-send")
    public void server2pointclient() {
        simpMessagingTemplate.convertAndSendToUser("lei", "/p2p", "服务给客户：点对点");
    }
}
