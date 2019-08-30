package collectors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AddCollectionToMap {

    public static void main(String[] args) {
        List<Book> books = Arrays.asList(
                new Book(1, "Java 8 in Action", 49.99),
                new Book(2, "Java SE8 for the Really Impatient", 39.99),
                new Book(3, "Core Java Volume I -- Fundamentals", 43.30),
                new Book(4, "Functional Programming in Java", 27.64),
                new Book(5, "Making Java Groovy", 45.99),
                new Book(6, "Head First Java", 26.97),
                new Book(7, "Effective Java", 35.47),
                new Book(8, "Java 8 Pocket Guide", 10.40),
                new Book(9, "Gradle Recipes for Android", 23.76),
                new Book(10, "Spring Boot in Action", 39.97)
        );

        Map<Integer, Book> bookMap = books.stream()
                .collect(Collectors.toMap(Book::getId, book -> book));

        bookMap.forEach((k, v) ->
                System.out.println(k + ": " + v)
        );

        bookMap = books.stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));

        bookMap.forEach((k, v) ->
                System.out.println(k + ": " + v)
        );
    }

    /*
    1: Product{id=1, name='Java 8 in Action', price=49.99}
2: Product{id=2, name='Java SE8 for the Really Impatient', price=39.99}
3: Product{id=3, name='Core Java Volume I -- Fundamentals', price=43.3}
4: Product{id=4, name='Functional Programming in Java', price=27.64}
5: Product{id=5, name='Making Java Groovy', price=45.99}
6: Product{id=6, name='Head First Java', price=26.97}
7: Product{id=7, name='Effective Java', price=35.47}
8: Product{id=8, name='Java 8 Pocket Guide', price=10.4}
9: Product{id=9, name='Gradle Recipes for Android', price=23.76}
10: Product{id=10, name='Spring Boot in Action', price=39.97}
1: Product{id=1, name='Java 8 in Action', price=49.99}
2: Product{id=2, name='Java SE8 for the Really Impatient', price=39.99}
3: Product{id=3, name='Core Java Volume I -- Fundamentals', price=43.3}
4: Product{id=4, name='Functional Programming in Java', price=27.64}
5: Product{id=5, name='Making Java Groovy', price=45.99}
6: Product{id=6, name='Head First Java', price=26.97}
7: Product{id=7, name='Effective Java', price=35.47}
8: Product{id=8, name='Java 8 Pocket Guide', price=10.4}
9: Product{id=9, name='Gradle Recipes for Android', price=23.76}
10: Product{id=10, name='Spring Boot in Action', price=39.97}
     */
}
