package org.cg.services.core.websocket.controller;

public class Ping {
    
    private String content;

    public Ping(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
