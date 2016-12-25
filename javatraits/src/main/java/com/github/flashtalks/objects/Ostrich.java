package com.github.flashtalks.objects;

import com.github.flashtalks.traits.PrettyName;
import com.github.flashtalks.log.Log;
import com.github.flashtalks.traits.CanWalk;
import com.github.flashtalks.traits.HasHeight;
import com.github.flashtalks.traits.HasName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Ostrich implements CanWalk, HasHeight, HasName, PrettyName {
    private Log log;
    private double height;
    private String name;
}
