package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.*;
import org.junit.After;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.*;

import static models.Api.MAIN_PAGE;
import static org.junit.Assert.assertTrue;

@DisplayName("Авторизация пользователя")
    public class LoginTest {
    private WebDriver driver;
    private ConstructorPage constructorPage;
    private StellarBurgersPage stellarBurgersPage;
    private RegistrationPage registrationPage;
    private User user;
    private UserApi userApi;
    private String accessToken;
    private String token;
    @Step("Подготовка данных и браузера")
    @Before
    public void setUp() {
        user = User.getUser();
        userApi = new UserApi();
        accessToken = String.valueOf(userApi.createUser(user));

        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverCreator.createWebDriver(browser);
        driver.get(MAIN_PAGE);
        driver.manage().window().maximize();

        constructorPage = new ConstructorPage(driver);
        stellarBurgersPage = new StellarBurgersPage(driver);
        registrationPage = new RegistrationPage(driver);
    }
    @Step("Удалить пользователя после теста")
    @After
    public void tearDown() {
        userApi.deleteUser(token);
        if (driver != null) {
            driver.quit();
        }
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
        System.out.println("Пользователь с email: " + user.getEmail() + " авторизован");
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
        System.out.println("Пользователь с email: " + user.getEmail() + " авторизован");
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
        System.out.println("Пользователь с email: " + user.getEmail() + " авторизован");
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
        System.out.println("Пользователь с email: " + user.getEmail() + " авторизован");
    }



        }



