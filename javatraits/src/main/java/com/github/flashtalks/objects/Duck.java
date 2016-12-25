package com.github.flashtalks.objects;

import com.github.flashtalks.log.Log;
import com.github.flashtalks.traits.CanFly;
import com.github.flashtalks.traits.CanQuack;
import com.github.flashtalks.traits.HasAge;
import com.github.flashtalks.traits.PrettyName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Duck implements HasAge, CanQuack, CanFly, PrettyName {
    private Log log;
    private int age;

    public boolean isAdult() {
        return age > 1;
    }
}
