package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/*
https://praveergupta.in/practical-guide-to-java-stream-api-7aadc02908f7
 */
public class PersonStreams {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Rients", 51, Gender.MALE),
                new Person("Suzan", 48, Gender.FEMALE),
                new Person("Lana", 12, Gender.FEMALE),
                new Person("Axel", 15, Gender.MALE),
                new Person("Trienke", 80, Gender.FEMALE),
                new Person("Cees", 75, Gender.MALE),
                new Person("Janet", 50, Gender.FEMALE),
                new Person("Peter", 48, Gender.MALE),
                new Person("Denise", 16, Gender.FEMALE),
                new Person("Sara", 14, Gender.FEMALE),
                new Person("Vera", 51, Gender.FEMALE),
                new Person("Rob", 53, Gender.MALE));


        // ----- SEARCHING -----
        // bulding a stream
        List<String> namesOfPeopleBelow20 = people.stream()
                // pipelining a computation
                .filter(person -> person.getAge() < 20)
                // pipelining another computation
                .map(Person::getName)
                // terminating a stream
                .collect(Collectors.toList());
        System.out.println(namesOfPeopleBelow20);

        // nu de map in de terminal operator
        namesOfPeopleBelow20 = people.stream()
                // pipelining a computation
                .filter(person -> person.getAge() < 20)
                // terminating a stream
                .collect(Collectors.mapping(person -> person.getName(), Collectors.toList()));
        System.out.println(namesOfPeopleBelow20);

        // count based filtering
        List<Person> smallerListOfPeople = people.stream()
                .skip(2)
                .limit(10)
                .collect(Collectors.toList());
        System.out.println(smallerListOfPeople); // Rients and Suzan missing


        // ----- SEARCHING -----
        // searching for a element
        Optional<Person> any = people.stream()
                .filter(person -> person.getAge() < 80)
                .findAny();
        System.out.println(any.isPresent() ? "any: " + any.get() : "nothing found");

        Optional<Person> first = people.stream()
                .filter(person -> person.getAge() < 80)
                .findFirst();
        System.out.println(first.isPresent() ? "first: " + first.get() : "nothing found");

        // searching for existence
        boolean isAnyOneInGroupLessThan20Years = people.stream()
                .anyMatch(person -> person.getAge() < 20);
        System.out.println(isAnyOneInGroupLessThan20Years);


        // ----- REORDERING -----
        List<Person> peopleSortedEldestToYoungest = people.stream()
                .sorted(Comparator.comparing(Person::getAge).reversed())
                .collect(Collectors.toList());
        System.out.println(peopleSortedEldestToYoungest);


        // ----- SUMMARIZING -----
        // calculating sum using reduce terminal operator
        int sum = people.stream()
                .mapToInt(Person::getAge)
                .reduce(0, (total, currentValue) -> total + currentValue);
        System.out.println(sum);

        // calculating sum using sum terminal operator
        sum = people.stream()
                .mapToInt(Person::getAge)
                .sum();
        System.out.println(sum);

        // calculating count using count terminal operator
        sum = (int) people.stream()
                .mapToInt(Person::getAge)
                .count(); // aantal elementen in de list
        System.out.println(sum);

        // calculating summary
        IntSummaryStatistics ageStatistics = people.stream()
                .mapToInt(Person::getAge)
                .summaryStatistics();
        ageStatistics.getAverage();
        ageStatistics.getCount();
        ageStatistics.getMax();
        ageStatistics.getMin();
        ageStatistics.getSum();
        System.out.println(ageStatistics);


        // ----- GROUPING -----
        // Grouping people by gender
        Map<Gender, List<Person>> peopleByGender = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getGender,
                        Collectors.toList()));
        System.out.println(peopleByGender);

        // Grouping person names by gender
        Map<Gender, List<String>> nameByGender = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getGender,
                        Collectors.mapping(Person::getName, Collectors.toList())));
        // hier dus geen map, maar in de terminal operator
        System.out.println(nameByGender);

        // Grouping average age by gender
        Map<Gender, Double> averageAgeByGender = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getGender,
                        Collectors.averagingInt(Person::getAge)
                ));
        System.out.println(averageAgeByGender);
    }
}
