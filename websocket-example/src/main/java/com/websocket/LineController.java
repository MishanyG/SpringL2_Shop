package com.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LineController {
    @MessageMapping("/sendMessage")
    @SendTo("/topic")
    public LineDto sendMessage(LineDto lineDto) {
        return lineDto;
    }
}
