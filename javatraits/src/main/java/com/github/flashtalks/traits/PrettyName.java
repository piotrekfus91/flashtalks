package com.github.flashtalks.traits;

public interface PrettyName {
    default String prettyName() {
        return getClass().getSimpleName().toLowerCase();
    }
}
