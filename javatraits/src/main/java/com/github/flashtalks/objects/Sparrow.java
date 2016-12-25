package com.github.flashtalks.objects;

import com.github.flashtalks.log.Log;
import com.github.flashtalks.traits.CanFly;
import com.github.flashtalks.traits.HasAge;
import com.github.flashtalks.traits.PrettyName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Sparrow implements CanFly, HasAge, PrettyName {
    private Log log;
    private int age;

    @Override
    public boolean isAdult() {
        return age > 1;
    }
}
