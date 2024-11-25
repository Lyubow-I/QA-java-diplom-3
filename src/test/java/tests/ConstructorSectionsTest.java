package tests;

import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobjects.Browsers;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class  ConstructorSectionsTest extends SpecificationTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Browsers.getBrowserData();
    }
    public  ConstructorSectionsTest(String browser) {
        super(browser);
    }

    @Test
    @Step("Проверка перехода в раздел 'Соусы'")
    public void testSaucesSection() {
        constructorPage.selectSaucesSection();
        assertEquals("Соусы", constructorPage.getSaucesHeaderText());
    }

    @Test
    @Step("Проверка перехода в раздел 'Начинки'")
    public void testFillingsSection() {
        constructorPage.selectFillingsSection();
        assertEquals("Начинки", constructorPage.getFillingsHeaderText());
    }

    @Test
    @Step("Проверка перехода в раздел 'Булки'")
    public void testBunsSection() {
        constructorPage.selectFillingsSection();
        constructorPage.selectBunsSection();
        assertEquals("Булки", constructorPage.getBunsHeaderText());
    }
}
