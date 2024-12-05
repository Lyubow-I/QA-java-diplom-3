package models;


import java.util.Arrays;
import java.util.Collection;

public class Browsers {

    public static Collection<Object[]> getBrowserData() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"}
        });
    }
}
