package de.mokkapps.websocketanonymousmessagesdemo.scheduler;

import de.mokkapps.websocketanonymousmessagesdemo.service.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {
    private final GreetingService greetingService;

    Scheduler(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Scheduled(fixedRateString = "6000", initialDelayString = "0")
    public void schedulingTask() {
        log.info("Send messages due to schedule");
        greetingService.sendMessages();
    }
}
