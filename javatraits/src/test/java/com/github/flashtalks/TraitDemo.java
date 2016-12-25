package com.github.flashtalks;

import com.github.flashtalks.traits.*;
import com.github.flashtalks.log.Log;
import com.github.flashtalks.objects.Duck;
import com.github.flashtalks.objects.Human;
import com.github.flashtalks.objects.Ostrich;
import com.github.flashtalks.objects.Sparrow;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TraitDemo {
    private List<String> messages = new ArrayList<>();
    private Log log = (msg) -> messages.add(msg);
    private Human johnDoe = Human.builder().log(log).age(20).height(1.8).name("John Doe").build();
    private Human youngJohnDoe = Human.builder().log(log).age(12).height(1.5).name("Johnny Doe").build();
    private Ostrich theRoadRunner = Ostrich.builder().log(log).height(2.5).name("The Road Runner").build();
    private Duck theUglyDuckling = Duck.builder().log(log).age(1).build();
    private Duck theBeautifulSwan = Duck.builder().log(log).age(3).build();
    private Sparrow jack = Sparrow.builder().log(log).age(3).build();
    private Sparrow elemelek = Sparrow.builder().log(log).age(1).build();

    private List<Object> objects = Lists.newArrayList(
            johnDoe, youngJohnDoe, theRoadRunner, theUglyDuckling,
            theBeautifulSwan, jack, elemelek
    );

    @BeforeMethod
    private void setUp() {
        messages.clear();
    }

    @Test
    public void showAdults() throws Exception {
        List<String> result = objects.stream()
                .filter(HasAge.class::isInstance)
                .filter(PrettyName.class::isInstance)
                .filter(o -> ((HasAge)o).isAdult())
                .map(o -> ((PrettyName)o).prettyName() + " has age " + ((HasAge)o).getAge())
                .collect(Collectors.toList());

        assertThat(result).containsExactly(
                "human has age 20",
                "duck has age 3",
                "sparrow has age 3"
        );
    }

    @Test
    public void letEveryoneSpeak() throws Exception {
        objects.stream()
                .filter(CanSpeak.class::isInstance)
                .map(o -> (CanSpeak) o)
                .forEach(CanSpeak::speak);

        assertThat(messages).containsExactly(
                "hello!",
                "hello!"
        );
    }

    @Test
    public void letPassTheDoor() {
        List<String> result = objects.stream()
                .filter(HasHeight.class::isInstance)
                .filter(HasName.class::isInstance)
                .map(o -> (HasHeight & HasName) o)
                .filter(o -> o.getHeight() < 1.7)
                .map(o -> o.getName() + " passes, because it only has " + o.getHeight())
                .collect(Collectors.toList());

        assertThat(result).containsExactly(
                "Johnny Doe passes, because it only has 1.5"
        );
    }
}
