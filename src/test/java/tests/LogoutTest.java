package tests;

import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import pageobjects.Browsers;
import java.time.Duration;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

    @RunWith(Parameterized.class)
    public  class  LogoutTest extends SpecificationTest {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Browsers.getBrowserData();
        }
        public  LogoutTest(String browser) {
            super(browser);
        }
        private final String loginUrl = "https://stellarburgers.nomoreparties.site/";
        private final String loginUrl2 = "https://stellarburgers.nomoreparties.site/account/profile";
        private final String loginUrl3 = "https://stellarburgers.nomoreparties.site/login";

    @Test
    @Step("выход по кнопке «Выйти» в личном кабинете.")
    public void testLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        stellarBurgersPage.clickLoginButton();
        stellarBurgersPage.enterEmail("zet@ya.ru");
        stellarBurgersPage.enterPassword("1q2w3e4r");
        stellarBurgersPage.clickLoginButton3();
        wait.until(ExpectedConditions.urlToBe(loginUrl));
        assertEquals(loginUrl, driver.getCurrentUrl());
        stellarBurgersPage.clickPersonalCabinetButton();
        wait.until(ExpectedConditions.urlToBe(loginUrl2));
        assertEquals(loginUrl2, driver.getCurrentUrl());
        stellarBurgersPage.clickLogoutButton();
        wait.until(ExpectedConditions.urlToBe(loginUrl));
        assertEquals(loginUrl3, driver.getCurrentUrl());
    }
    }

