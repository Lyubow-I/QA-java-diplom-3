package tests;

import org.junit.After;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.*;

import static org.junit.Assert.assertTrue;
import static pageobjects.Api.MAIN_PAGE;

@DisplayName("Авторизация пользователя")
    public class LoginTest {
        private WebDriver driver;
        private ConstructorPage constructorPage;
        private StellarBurgersPage stellarBurgersPage;
        private RegistrationPage registrationPage;
        private User user;
        private UserApi userApi;
        private String accessToken;

        @Step("Подготовка данных и браузера")
        @Before
        public void setUp() {
            user = User.getUser();
            userApi = new UserApi();
            accessToken = userApi.createUser(user);

            String browser = System.getProperty("browser", "chrome");
            driver = WebDriverCreator.createWebDriver(browser);
            driver.get(MAIN_PAGE);
            driver.manage().window().maximize();

            constructorPage = new ConstructorPage(driver);
            stellarBurgersPage = new StellarBurgersPage(driver);
            registrationPage = new RegistrationPage(driver);
        }

        @Step("Удаление данных и закрытие браузера")
        @After
        public void tearDown() {
            userApi = new UserApi();
            userApi.deleteUser(accessToken);
            driver.quit();
        }

        @DisplayName("Вход по кнопке Войти в аккаунт на главной")
        @Test
        public void loginFromMainPageTest() {
            constructorPage.clickLoginButton();
            stellarBurgersPage.setEmail(user.getEmail());
            stellarBurgersPage.setPassword(user.getPassword());
            stellarBurgersPage.clickLoginButton();
            boolean isGetOrderButtonVisible = constructorPage.isCreateOrderButtonVisible();
            assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
            System.out.println("Пользователь с email: "+ user.getEmail() + " авторизован");
        }

        @DisplayName("Вход через кнопку Личный кабинет")
        @Test
        public void loginFromPersonalAccountButton() {
            constructorPage.clickPersonalAccountButton();
            stellarBurgersPage.setEmail(user.getEmail());
            stellarBurgersPage.setPassword(user.getPassword());
            stellarBurgersPage.clickLoginButton();
            boolean isGetOrderButtonVisible = constructorPage.isCreateOrderButtonVisible();
            assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
            System.out.println("Пользователь с email: "+ user.getEmail() + " авторизован");
        }

        @DisplayName("Вход через форму регистрации")
        @Test
        public void loginFromRegistrationPageTest() {
            constructorPage.clickLoginButton();
            stellarBurgersPage.clickRegistrationButton();
            registrationPage.clickLoginButton();
            stellarBurgersPage.setEmail(user.getEmail());
            stellarBurgersPage.setPassword(user.getPassword());
            stellarBurgersPage.clickLoginButton();
            boolean isGetOrderButtonVisible = constructorPage.isCreateOrderButtonVisible();
            assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
            System.out.println("Пользователь с email: "+ user.getEmail() + " авторизован");
        }

        @DisplayName("Вход через кнопку в форме восстановления пароля")
        @Test
        public void loginFromPasswordResetPageTest() {
            constructorPage.clickLoginButton();
            stellarBurgersPage.clickRecoverButton();
            registrationPage.clickLoginButton();
            stellarBurgersPage.setEmail(user.getEmail());
            stellarBurgersPage.setPassword(user.getPassword());
            stellarBurgersPage.clickLoginButton();
            boolean isGetOrderButtonVisible = constructorPage.isCreateOrderButtonVisible();
            assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
            System.out.println("Пользователь с email: "+ user.getEmail() + " авторизован");
        }
    }

