package de.mokkapps.websocketanonymousmessagesdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebsocketAnonymousMessagesDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketAnonymousMessagesDemoApplication.class, args);
	}

}
