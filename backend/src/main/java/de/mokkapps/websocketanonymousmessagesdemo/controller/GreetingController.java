package de.mokkapps.websocketanonymousmessagesdemo.controller;

import de.mokkapps.websocketanonymousmessagesdemo.model.Greeting;
import de.mokkapps.websocketanonymousmessagesdemo.model.HelloMessage;
import de.mokkapps.websocketanonymousmessagesdemo.service.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Slf4j
@Controller
public class GreetingController {

    private final GreetingService greetingService;

    GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @MessageMapping("/hello")
    @SendToUser("/topic/greetings")
    public Greeting greeting(HelloMessage message, Principal principal) throws Exception {
        log.info("Received greeting message {} from {}", message, principal.getName());
        greetingService.addUserName(principal.getName());
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}