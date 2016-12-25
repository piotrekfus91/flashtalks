package com.github.flashtalks.traits;

public interface CanQuack extends Loggable {
    default void quack() {
        log("quack!");
    }
}
