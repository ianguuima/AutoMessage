package me.ianguuima.entity;

import lombok.Getter;

public class Message {

    @Getter
    private int id;
    @Getter
    private String message;


    public Message(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
