package com.github.flashtalks.traits;

public interface CanWalk extends Loggable {
    default void walk() {
        log("walking...");
    }
}
