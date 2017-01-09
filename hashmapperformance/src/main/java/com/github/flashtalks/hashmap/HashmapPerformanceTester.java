package com.github.flashtalks.hashmap;

import com.google.common.base.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@State(Scope.Benchmark)
public class HashmapPerformanceTester {
    private Map<NonComparableClass, Object> nonComparableWithBigDispersionMap = new HashMap<>();
    private Map<NonComparableClass, Object> nonComparableWithSmallDispersionMap = new HashMap<>();
    private Map<NonComparableClass, Object> comparableWithBigDispersionMap = new HashMap<>();
    private Map<NonComparableClass, Object> comparableWithSmallDispersionMap = new HashMap<>();
    // those below look ugly, but they were used to test on Java 7
    private Function<Long, NonComparableClass> nonComparableClassBigDispersionFunction = new Function<Long, NonComparableClass>() {
        @Override
        public NonComparableClass apply(Long l) {
            return new NonComparableClass(l, 1000);
        }
    };
    private Function<Long, NonComparableClass> nonComparableClassSmallDispersionFunction = new Function<Long, NonComparableClass>() {
        @Override
        public NonComparableClass apply(Long l) {
            return new NonComparableClass(l, 1000);
        }
    };
    private Function<Long, NonComparableClass> comparableClassBigDispersionFunction = new Function<Long, NonComparableClass>() {
        @Override
        public NonComparableClass apply(Long l) {
            return new NonComparableClass(l, 1000);
        }
    };
    private Function<Long, NonComparableClass> comparableClassSmallDispersionFunction = new Function<Long, NonComparableClass>() {
        @Override
        public NonComparableClass apply(Long l) {
            return new NonComparableClass(l, 1000);
        }
    };

    @Benchmark
    public void fillNonComparableWithBigDispersion() {
        Map<NonComparableClass, Object> nonComparableWithBigDispersionMap = new HashMap<>();
        fillMap(nonComparableWithBigDispersionMap, nonComparableClassBigDispersionFunction);
    }

    @Benchmark
    public void fillNonComparableWithSmallDispersion() {
        Map<NonComparableClass, Object> nonComparableWithSmallDispersionMap = new HashMap<>();
        fillMap(nonComparableWithSmallDispersionMap, nonComparableClassSmallDispersionFunction);
    }

    @Benchmark
    public void fillComparableWithBigDispersion() {
        Map<NonComparableClass, Object> comparableWithBigDispersionMap = new HashMap<>();
        fillMap(comparableWithBigDispersionMap, comparableClassBigDispersionFunction);
    }

    @Benchmark
    public void fillComparableWithSmallDispersion() {
        Map<NonComparableClass, Object> comparableWithSmallDispersionMap = new HashMap<>();
        fillMap(comparableWithSmallDispersionMap, comparableClassSmallDispersionFunction);
    }

    @Benchmark
    public void fetchNonComparableWithBigDispersion() {
        getFromMap(nonComparableWithBigDispersionMap, nonComparableClassBigDispersionFunction);
    }

    @Benchmark
    public void fetchNonComparableWithSmallDispersion() {
        getFromMap(nonComparableWithSmallDispersionMap, nonComparableClassSmallDispersionFunction);
    }

    @Benchmark
    public void fetchComparableWithBigDispersion() {
        getFromMap(comparableWithBigDispersionMap, comparableClassBigDispersionFunction);
    }

    @Benchmark
    public void fetchComparableWithSmallDispersion() {
        getFromMap(comparableWithSmallDispersionMap, comparableClassSmallDispersionFunction);
    }

    @Setup
    public void setup() {
        fillMap(nonComparableWithBigDispersionMap, nonComparableClassBigDispersionFunction);
        fillMap(nonComparableWithSmallDispersionMap, nonComparableClassSmallDispersionFunction);
        fillMap(comparableWithBigDispersionMap, comparableClassBigDispersionFunction);
        fillMap(comparableWithSmallDispersionMap, comparableClassSmallDispersionFunction);
    }

    private <KEY> void getFromMap(Map<KEY, Object> map, Function<Long, KEY> generator) {
        for(int i = 0; i < 10000; i++) {
            map.get(generator.apply((long) i));
        }
    }

    private <KEY> void fillMap(Map<KEY, Object> map, Function<Long, KEY> generator) {
        Random random = new Random();

        for(int i = 0; i < 10000; i++) {
            long value = random.nextLong();
            map.put(generator.apply(value), new Object());
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + HashmapPerformanceTester.class.getSimpleName() + ".*")
                .forks(1)
                .warmupTime(TimeValue.milliseconds(100))
                .measurementTime(TimeValue.milliseconds(100))
                .warmupIterations(10)
                .measurementIterations(10)
                .shouldDoGC(true)
                .build();
        new Runner(opt).run();
    }
}