package com.github.flashtalks.traits;

import com.github.flashtalks.log.Log;

public interface Loggable {
    Log getLog();

    default void log(String msg) {
        getLog().log(msg);
    }
}
