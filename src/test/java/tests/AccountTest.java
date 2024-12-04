package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
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
import java.util.HashMap;
import java.util.Map;

import static java.rmi.activation.Activatable.register;
import static models.Api.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.*;
public class AccountTest {
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
        userClient = new UserClient();
        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverCreator.createWebDriver(browser);
        driver.manage().window().maximize();
        driver.get(MAIN_PAGE);
        constructorPage = new ConstructorPage(driver);
        stellarBurgersPage = new StellarBurgersPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    private void waitUntilUrlIs(String url) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(url));
    }

    @Description("Тестирование перехода в личный кабинет по клику на Личный кабинет")
    @Test
    public void goToAccountFromMainPageTest() {
        UserRandom user = UserRandom.getUser();
        Response registerResponse = userApi.register(user);
        assertEquals(200, registerResponse.getStatusCode());

        waitUntilUrlIs(MAIN_PAGE);
        userApi.login(user);
        constructorPage.clickPersonalAccountButton();

        waitUntilUrlIs(USER_PROFILE);
        assertEquals("URL после входа в аккаунт и клика по кнопке «Личный кабинет» должен быть переход на страницу профиля",
                USER_PROFILE, driver.getCurrentUrl());
    }
    @Description("Переход из личного кабинета  по клику на Конструктор")
    @Test
    public void constructorClickTest() {
        UserRandom user = UserRandom.getUser();
        Response registerResponse = userApi.register(user);
        assertEquals(200, registerResponse.getStatusCode());

        waitUntilUrlIs(MAIN_PAGE);
        userApi.login(user);
        constructorPage.clickPersonalAccountButton();
        stellarBurgersPage.clickConstructorButton();

        waitUntilUrlIs(MAIN_PAGE);
        assertEquals("URL после клика по кнопке Конструктор из личного кабинета должен быть главной страницей", MAIN_PAGE, driver.getCurrentUrl());
    }

    @Description("Переход из личного кабинета на логотип Stellar Burgers")
    @Test
    public void logoClickTest() {
        UserRandom user = UserRandom.getUser();
        Response registerResponse = userApi.register(user);
        assertEquals(200, registerResponse.getStatusCode());

        waitUntilUrlIs(MAIN_PAGE);
        userApi.login(user);
        constructorPage.clickPersonalAccountButton();
        stellarBurgersPage.clickLogoButton();

        waitUntilUrlIs(MAIN_PAGE);
        assertEquals("URL после клика по логотипу Stellar Burgers должен быть главной страницей", MAIN_PAGE, driver.getCurrentUrl());
    }

    @Step("Закрыть браузер и удалить пользователя")
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        if (accessToken != null) {
            userApi.deleteUser(accessToken);

        }
    }

}






