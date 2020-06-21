package com.web;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/content")
    public String broadcast(Message message) {
        byte[] bytes = (byte[]) message.getPayload(); // 客户端发送msg自动绑定到message, getPayload得到内容
        String content = new String(bytes);
        System.out.print(content);
        return "success";
    }
}
