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
    public  class   PersonalCabinetTest extends SpecificationTest {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Browsers.getBrowserData(); // Вызов параметров из внешнего класса
        }
        public   PersonalCabinetTest(String browser) {
            super(browser);
        }
        private final String loginUrl = "https://stellarburgers.nomoreparties.site/";
        private final String loginUrl2 = "https://stellarburgers.nomoreparties.site/account/profile";
    @Test
    @Step("переход по клику на «Личный кабинет.")
    public void testGoToPersonalCabinet() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        stellarBurgersPage.clickPersonalCabinetButton();
        stellarBurgersPage.enterEmail("zet@ya.ru");
        stellarBurgersPage.enterPassword("1q2w3e4r");
        stellarBurgersPage.clickLoginButton3();
        wait.until(ExpectedConditions.urlToBe(loginUrl));
        assertEquals( loginUrl, driver.getCurrentUrl());
        stellarBurgersPage.clickPersonalCabinetButton();
        wait.until(ExpectedConditions.urlToBe(loginUrl2));
        assertEquals(loginUrl2, driver.getCurrentUrl());
    }
    }
