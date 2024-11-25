package pageobjects;

import java.util.Arrays;
import java.util.Collection;

public class Browsers {

    // Для вызова тестов в браузерах chrome и yandex одной командой mvn clean test
    public static Collection<Object[]> getBrowserData() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"}
        });
    }
}
