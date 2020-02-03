package de.mokkapps.websocketanonymousmessagesdemo.model;

import java.security.Principal;

/**
 * Custom Principal class which is used for anonymous user sessions in Websocket connection
 */
public class StompPrincipal implements Principal {
    private String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}