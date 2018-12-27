package com.fyp.im.processor;

public interface ClientOfflineHandler {
    void handle(long userId,byte[] body);
}
