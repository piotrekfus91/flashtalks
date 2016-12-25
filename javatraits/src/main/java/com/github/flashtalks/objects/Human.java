package com.github.flashtalks.objects;

import com.github.flashtalks.traits.PrettyName;
import com.github.flashtalks.log.Log;
import com.github.flashtalks.traits.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Human implements HasAge, HasHeight, HasName, CanSpeak, CanWalk, PrettyName {
    private Log log;
    private int age;
    private double height;
    private String name;

    public boolean isAdult() {
        return age > 18;
    }
}
