package com.github.flashtalks.objects;

import com.github.flashtalks.log.Log;
import com.github.flashtalks.traits.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Human implements HasAge, HasHeight, HasName, CanSpeak, CanWalk, PrettyName {
    private Log log;
    private int age;
    private double height;
    private String name;

    public boolean isAdult() {
        return age > 18;
    }
}
