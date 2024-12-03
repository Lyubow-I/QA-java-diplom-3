package tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.*;
import org.junit.After;
import java.time.Duration;
import static models.Api.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.*;


@DisplayName("Авторизация пользователя")
    public class LoginTest {
    private String name = "Zena";
    private String email = "Zena@ya.ru";
    private String password = "1q2w3e";
    private WebDriver driver;
    private StellarBurgersPage stellarBurgersPage;
    private RegistrationPage registrationPage;
    private ConstructorPage constructorPage;
    private UserApi userApi;
    private UserClient userClient;
    private String accessToken;

    @Step("Подготовка данных и браузера")
    @Before
    public void setUp() {
        userApi = new UserApi();
        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverCreator.createWebDriver(browser);
        driver.manage().window().maximize();
        driver.get(MAIN_PAGE);
        constructorPage = new ConstructorPage(driver);
        stellarBurgersPage = new StellarBurgersPage(driver);
        registrationPage = new RegistrationPage(driver);
        userClient = new UserClient();
    }

    @Step(" регистрации пользователя")
    public void registration() {
        constructorPage.waitLoadingMainPage();
        constructorPage.clickLoginButton();
        stellarBurgersPage.clickRegistrationButton();
        assertEquals("URL после регистрации должен быть страницей регистрации",
                SITE_REGISTER,
                driver.getCurrentUrl());
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickRegistrationButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(USER_LOGIN));
        assertEquals("URL после регистрации должен быть страницей логина",
                USER_LOGIN,
                driver.getCurrentUrl());

    }

    @DisplayName("Вход по кнопке Войти в аккаунт на главной")
    @Test
    public void loginFromMainPageTest() {
        constructorPage.waitLoadingMainPage();
        constructorPage.clickLoginButton();
        stellarBurgersPage.setEmail(email);
        stellarBurgersPage.setPassword(password);
        stellarBurgersPage.clickLoginButton();
        boolean isGetOrderButtonVisible = constructorPage.isCreateOrderButtonVisible();
        assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
        System.out.println("Пользователь с email: " + email + " авторизован");
        userApi.deleteUser(accessToken);
    }

    @DisplayName("Вход через кнопку Личный кабинет")
    @Test
    public void loginFromPersonalAccountButton() {
        constructorPage.waitLoadingMainPage();
        constructorPage.clickPersonalAccountButton();
        stellarBurgersPage.setEmail(email);
        stellarBurgersPage.setPassword(password);
        stellarBurgersPage.clickLoginButton();
        boolean isGetOrderButtonVisible = constructorPage.isCreateOrderButtonVisible();
        assertTrue("Вместо кнопки Войти появляется кнопка Оформить заказ на главной странице", isGetOrderButtonVisible);
        System.out.println("Пользователь с email: " + email + " авторизован");
    }

    @DisplayName("Вход через форму регистрации")
    @Test
    public void loginFromRegistrationPageTest() {
        constructorPage.waitLoadingMainPage();
        constructorPage.clickLoginButton();
        stellarBurgersPage.clickRegistrationButton();
        registrationPage.clickLoginButton();
        stellarBurgersPage.setEmail(email);
        stellarBurgersPage.setPassword(password);
        stellarBurgersPage.clickLoginButton();
        boolean isGetOrderButtonVisible = constructorPage.isCreateOrderButtonVisible();
        assertTrue( isGetOrderButtonVisible);
        System.out.println("Пользователь с email: " + email + " авторизован");
    }

    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Test
    public void loginFromPasswordResetPageTest() {
        constructorPage.waitLoadingMainPage();
        constructorPage.clickLoginButton();
        stellarBurgersPage.clickRecoverButton();
        registrationPage.clickLoginButton();
        stellarBurgersPage.setEmail(email);
        stellarBurgersPage.setPassword(password);
        stellarBurgersPage.clickLoginButton();
        boolean isGetOrderButtonVisible = constructorPage.isCreateOrderButtonVisible();
        assertTrue( isGetOrderButtonVisible);
        Response loginResponse = userClient.login(email, password);
        loginResponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.email", equalToIgnoringCase(email));

        accessToken = loginResponse.as(UserToken.class).getAccessToken();

        System.out.println("Пользователь с email: " + email + " авторизован");
        assertNotNull("AccessToken не должен быть null", accessToken);
        assertNotNull("userApi не должен быть null", userApi);

    }
    @Step("Закрыть браузер и удалить пользователя")
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Удаление пользователя после всех тестов
        if (accessToken != null) {
            userApi.deleteUser (accessToken);
        }
    }
}



