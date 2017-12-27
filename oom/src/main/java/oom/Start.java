package oom;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Start {
    public static void main(String[] args) {
        List<SomeClass> list = new LinkedList<>();
        while(true) {
            list.add(new SomeClass());
        }
    }

    private static class SomeClass {
        int x = new Random().nextInt();
        int y = new Random().nextInt();
    }
}
