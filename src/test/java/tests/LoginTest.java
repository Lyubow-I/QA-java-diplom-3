package tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.*;
import org.junit.After;
import static models.Api.*;
import static org.junit.Assert.*;


@DisplayName("Авторизация пользователя")
    public class LoginTest {
    private WebDriver driver;
    private StellarBurgersPage stellarBurgersPage;
    private RegistrationPage registrationPage;
    private ConstructorPage constructorPage;
    private UserApi userApi;
    private UserClient userClient;
    private String accessToken;
    private User user;
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

    @DisplayName("Вход по кнопке Войти в аккаунт на главной")
    @Test
    public void loginFromMainPageTest() {
        UserRandom user = UserRandom.getUser();
        Response registerResponse = userApi.register(user);
        assertEquals(200, registerResponse.getStatusCode());
        constructorPage.clickLoginButton();
        userApi.login(user);
        assertNotNull("AccessToken не должен быть null", accessToken);
        assertNotNull("userApi не должен быть null", userApi);
    }

    @DisplayName("Вход через кнопку Личный кабинет")
    @Test
    public void loginFromPersonalAccountButton() {
        UserRandom user = UserRandom.getUser();
        Response registerResponse = userApi.register(user);
        assertEquals(200, registerResponse.getStatusCode());
        constructorPage.clickPersonalAccountButton();
        userApi.login(user);
        assertNotNull("AccessToken не должен быть null", accessToken);
        assertNotNull("userApi не должен быть null", userApi);
    }

    @DisplayName("Вход через форму регистрации")
    @Test
    public void loginFromRegistrationPageTest() {
        UserRandom user = UserRandom.getUser();
        Response registerResponse = userApi.register(user);
        assertEquals(200, registerResponse.getStatusCode());
        constructorPage.clickLoginButton();
        stellarBurgersPage.clickRegistrationButton();
        registrationPage.clickLoginButton();
        userApi.login(user);
        assertNotNull("AccessToken не должен быть null", accessToken);
        assertNotNull("userApi не должен быть null", userApi);
    }

    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Test
    public void loginFromPasswordResetPageTest() {
        UserRandom user = UserRandom.getUser();
        Response registerResponse = userApi.register(user);
        assertEquals(200, registerResponse.getStatusCode());
        constructorPage.clickLoginButton();
        stellarBurgersPage.clickRecoverButton();
        registrationPage.clickLoginButton();
        userApi.login(user);
        assertNotNull("AccessToken не должен быть null", accessToken);
        assertNotNull("userApi не должен быть null", userApi);

    }
    @Step("Закрыть браузер и удалить пользователя")
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        if (accessToken != null) {
            userApi.deleteUser (accessToken);
        }
    }
}



