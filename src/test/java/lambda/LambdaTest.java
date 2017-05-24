package lambda;

import lambda.entity.Person;
import org.apache.commons.math3.primes.Primes;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by acer_liuyutong on 2017/4/10.
 */
public class LambdaTest {
    private static int[] numbers = new int[] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
    private static Integer[] numbers2 = new Integer[] { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };

    private static String[] strings = new String[] { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static Person[] personArr = new Person[] {
            new Person(1, "liu1", 1, 11),
            new Person(1, "liu2", 1, 12),
            new Person(1, "liu3", 1, 13),
            new Person(1, "liu4", 0, 14),
            new Person(1, "liu5", 0, 15),
            new Person(1, "liu6", 0, 15),
            new Person(1, "liu7", 0, 15)
    };

    @Test
    public void test1(){
        String s = "abcdefg我是lyt";
        s.chars().forEach(System.out::println);

        Map<String,Object> map = new HashMap<>();
        map.put("ss","324");
        System.out.println(map);
    }

    @Test
    public void test2(){

        String[] nums = {"1","2","3","4","5"};
        List<String> strings = Arrays.asList(nums);
        List<Integer> collect = strings.stream().map(Integer::parseInt)
                .filter(Primes::isPrime)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test3(){
        String[] nums = {"1","2","3","4","5","3"};
        List<String> strings = Arrays.asList(nums);
        Map<Integer, Integer> collect = strings.stream().map(Integer::parseInt)
                .filter(Primes::isPrime)
                .collect(Collectors.groupingBy(p -> p, Collectors.summingInt(e -> 1)));
        System.out.println(collect);
    }

    @Test
    public void test4(){

        String[] nums = {"1","2","3","4","5"};
        List<String> strings = Arrays.asList(nums);
        Integer reduce = strings.stream().map(Integer::parseInt)
                .filter(Primes::isPrime)
                .distinct()
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);
    }

    @Test
    public void test5(){
        List<Person> persons = Arrays.asList(personArr);
        Map<Integer, Integer> collect = persons.stream().filter(p -> p.getAge() >= 12 && p.getAge() <= 14)
                .collect(Collectors.groupingBy(Person::getSex, Collectors.summingInt(e -> 1)));
        System.out.println(collect);

    }

    @Test
    public void test6(){
        OptionalInt max = Arrays.stream(numbers).max();
        if (max.isPresent()) {
            System.out.println(max.getAsInt());
        }

        IntStream stream = Arrays.stream(numbers);
//        stream.forEach(System.out::println);

//        stream.sorted().forEach(System.out::println);
        IntStream sorted = stream.sorted();

        Stream<int[]> numbers0 = Stream.of(numbers);


        numbers0.forEach(System.out::println);

        Optional<Integer> max1 = Stream.of(LambdaTest.numbers2).max(Integer::compareTo);
        if (max1.isPresent()) System.out.println(max1.get());

        Arrays.stream(personArr).sorted((o1, o2) -> o1.getAge().compareTo(o2.getAge()));

    }

    @Test
    public void test7(){
        List<Person> list = Arrays.asList(personArr);

        String collect = list.stream().map(Person::getName).collect(Collectors.joining(";"));
        System.out.println(collect);

        String collect1 = Arrays.stream(strings).collect(Collectors.joining(";"));
        System.out.println(collect1);
    }
}
