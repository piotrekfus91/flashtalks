package com.github.flashtalks.traits;

public interface CanFly extends Loggable {
    default void fly() {
        log("flying...");
    }
}
