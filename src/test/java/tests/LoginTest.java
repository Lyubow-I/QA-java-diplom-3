package tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
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
    @Step("Вход под логиным")
    private void login(UserRandom user) {
        stellarBurgersPage.setEmail(user.getEmail());
        stellarBurgersPage.setPassword(user.getPassword());
        registrationPage.clickLoginButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));

        Response loginResponse = userClient.login(user.getEmail(), user.getPassword());
        loginResponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.email", equalToIgnoringCase(user.getEmail()));

        accessToken = loginResponse.as(UserToken.class).getAccessToken();

    }
    @DisplayName("Вход по кнопке Войти в аккаунт на главной")
    @Test
    public void loginFromMainPageTest() {
        UserRandom user = UserRandom.getUser();
        Response registerResponse = userApi.register(user);
        assertEquals(200, registerResponse.getStatusCode());
        constructorPage.clickLoginButton();
        login(user);
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
        login(user);
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
        login(user);
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
        login(user);
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



