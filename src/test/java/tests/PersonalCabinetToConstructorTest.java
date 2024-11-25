package tests;

import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.Browsers;
import java.time.Duration;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

    @RunWith(Parameterized.class)
    public  class  PersonalCabinetToConstructorTest extends SpecificationTest {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Browsers.getBrowserData(); // Вызов параметров из внешнего класса
        }
        public  PersonalCabinetToConstructorTest(String browser) {
            super(browser);
        }
        private final String loginUrl = "https://stellarburgers.nomoreparties.site/";
    @Test
    @Step("Переход по клику из личного кабинета в конструктор ")
    public void testGoToConstructorFromPersonalCabinet() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        stellarBurgersPage.clickPersonalCabinetButton();
        stellarBurgersPage.enterEmail("zet@ya.ru");
        stellarBurgersPage.enterPassword("1q2w3e4r");
        stellarBurgersPage.clickLoginButton3();
        stellarBurgersPage.clickPersonalCabinetButton();
        stellarBurgersPage.clickConstructorButton();
        wait.until(ExpectedConditions.urlToBe(loginUrl));
        assertEquals(loginUrl, driver.getCurrentUrl());
    }
    @Test
    @Step("Переход по клику из личного кабинета на логотип Stellar Burgers. ")
    public void testGoToLogoFromPersonalCabinet() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        stellarBurgersPage.clickPersonalCabinetButton();
        stellarBurgersPage.enterEmail("zet@ya.ru");
        stellarBurgersPage.enterPassword("1q2w3e4r");
        stellarBurgersPage.clickLoginButton3();
        stellarBurgersPage.clickPersonalCabinetButton();
        stellarBurgersPage.getLogo();
        wait.until(ExpectedConditions.urlToBe(loginUrl));
        assertEquals(loginUrl, driver.getCurrentUrl());
    }
}
