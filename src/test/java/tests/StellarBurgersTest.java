package tests;


import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.Browsers;

import java.time.Duration;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class StellarBurgersTest extends SpecificationTest {
    private final Faker faker = new Faker();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Browsers.getBrowserData();
    }

    public StellarBurgersTest(String browser) {
        super(browser);
    }

    private final String loginUrl = "https://stellarburgers.nomoreparties.site/login";
    private final String loginUrl2 = "https://stellarburgers.nomoreparties.site/register";

    @Test
    @DisplayName("Успешная регистрация с паролем 6 символов")
    @Step
    public void successfulRegistrationTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        stellarBurgersPage.clickLoginButton();
        stellarBurgersPage.clickRegisterButton();
        registrationPage.enterName2(name);
        registrationPage.enterEmail2(email);
        registrationPage.enterPassword("1q2w3e");
        registrationPage.clickRegisterButtonAfter();
        wait.until(ExpectedConditions.urlToBe(loginUrl));
        assertEquals(loginUrl, driver.getCurrentUrl());

    }

    @Test
    @DisplayName("Регистрация с паролем 5 символов")
    @Step
    public void password5Test() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        stellarBurgersPage.clickLoginButton();
        stellarBurgersPage.clickRegisterButton();
        registrationPage.enterName2(name);
        registrationPage.enterEmail2(email);
        registrationPage.enterPassword("1q2w3");
        registrationPage.clickRegisterButtonAfter();
        wait.until(ExpectedConditions.visibilityOf(registrationPage.getErrorMessageElement()));
        assertTrue(registrationPage.isErrorMessageElementDisplayed());
        assertEquals("Некорректный пароль", registrationPage.getErrorMessageElement());
    }

    @Test
    @DisplayName("Регистрация с паролем 7 символов")
    @Step
    public void password7Test() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        stellarBurgersPage.clickLoginButton();
        stellarBurgersPage.clickRegisterButton();
        registrationPage.enterName2(name);
        registrationPage.enterEmail2(email);
        registrationPage.enterPassword("1й2ц3у7");
        registrationPage.clickRegisterButtonAfter();
        wait.until(ExpectedConditions.urlToBe(loginUrl));
        assertEquals(loginUrl, driver.getCurrentUrl());
    }
    @Test
    @DisplayName("Регистрация с паролем 36 символов")
    @Step
    public void password36Test() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        stellarBurgersPage.clickLoginButton();
        stellarBurgersPage.clickRegisterButton();
        registrationPage.enterName2(name);
        registrationPage.enterEmail2(email);
        registrationPage.enterPassword("1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y7u8i");
        registrationPage.clickRegisterButtonAfter();
        wait.until(ExpectedConditions.urlToBe(loginUrl));
        assertEquals(loginUrl, driver.getCurrentUrl());

    }
}