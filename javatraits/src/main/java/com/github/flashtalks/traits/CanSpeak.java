package com.github.flashtalks.traits;

public interface CanSpeak extends Loggable {
    default void speak() {
        log("hello!");
    }
}
