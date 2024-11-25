package tests;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageobjects.Browsers;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
    @RunWith(Parameterized.class)
    public  class  SignInTest extends SpecificationTest {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Browsers.getBrowserData(); // Вызов параметров из внешнего класса
        }
        public  SignInTest(String browser) {
            super(browser);
        }
        private final String loginUrl = "https://stellarburgers.nomoreparties.site/login";
    @Test
    @DisplayName("Вход через кнопку на главной странице")
    @Step
    public void loginViaMainPageButton() {
        stellarBurgersPage.clickLoginButton(); // Войти в аккаунт
        stellarBurgersPage.enterEmail(userEmail); // Ввод email
        stellarBurgersPage.enterPassword(userPassword); // Ввод пароля
        stellarBurgersPage.clickLoginButton3();
        assertEquals(loginUrl, driver.getCurrentUrl());
    }
    @Test
    @DisplayName("Вход через кнопку в личном кабинете")
    @Step
    public void loginViaPersonalCabinetButton() {
        stellarBurgersPage.clickPersonalCabinetButton();
        stellarBurgersPage.enterEmail(userEmail);
        stellarBurgersPage.enterPassword(userPassword);
        stellarBurgersPage.clickLoginButton3();
        assertEquals(loginUrl, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    @Step
    public void loginViaRegistrationFormButton() {
        stellarBurgersPage.clickLoginButton();
        registrationPage.scrollToRegisterButton();
        registrationPage.clickRegisterButton();
        stellarBurgersPage.clickLoginButton2();
        stellarBurgersPage.enterEmail(userEmail);
        stellarBurgersPage.enterPassword(userPassword);
        stellarBurgersPage.clickLoginButton3();
        assertEquals(loginUrl, driver.getCurrentUrl());
        }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    @Step
    public void loginViaForgotPasswordFormButton() {
        stellarBurgersPage.clickLoginButton();
        stellarBurgersPage.clickRecoverPasswordButton();
        stellarBurgersPage.clickLoginButton2();
        stellarBurgersPage.enterEmail(userEmail);
        stellarBurgersPage.enterPassword(userPassword);
        stellarBurgersPage.clickLoginButton3();
        assertEquals(loginUrl, driver.getCurrentUrl());
    }
}
