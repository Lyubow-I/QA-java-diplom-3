package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
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
public class AccountTest {
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

    @Step("Регистрация")
    public void registracion() {
        constructorPage.waitLoadingMainPage();
        constructorPage.clickLoginButton();
        stellarBurgersPage.clickRegistrationButton();
        assertEquals("URL после регистрации должен быть страницей регистрации",
                SITE_REGISTER,
                driver.getCurrentUrl());
        UserRandom userRandom = UserRandom.getUser ();
        String userName = userRandom.getName();
        String userEmail = userRandom.getEmail();
        String userPassword = userRandom.getPassword();
        registrationPage.setName(userName);
        registrationPage.setEmail(userEmail);
        registrationPage.setPassword(userPassword);
        registrationPage.clickRegistrationButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(USER_LOGIN));
        assertEquals("URL после регистрации должен быть страницей логина",
                USER_LOGIN,
                driver.getCurrentUrl());

    }
    @Step("Вход под созданным пользователем")
    public void loginAsRegisteredUser() {
        UserRandom userRandom = UserRandom.getUser ();
        String userEmail = userRandom.getEmail();
        String userPassword = userRandom.getPassword();
        stellarBurgersPage.setEmail(userEmail);
        stellarBurgersPage.setPassword(userPassword);
        registrationPage.clickLoginButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));
        assertEquals("URL после входа должен быть главной страницей",
                MAIN_PAGE,
                driver.getCurrentUrl());
        Response loginResponse = userClient.login(userEmail, userPassword);
        loginResponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.email", equalToIgnoringCase(userEmail));

        String accessToken = loginResponse.as(UserToken.class).getAccessToken();

    }

    @Description("Тестирование перехода в личный кабинет по клику на Личный кабинет")
    @Test
    public void goToAccountFromMainPageTest() {
        registracion();
        loginAsRegisteredUser();
        constructorPage.clickPersonalAccountButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(USER_PROFILE));
        assertEquals("URL после входа в аккаунт и клика по кнопке «Личный кабинет» должен быть переход на страницу профиля", USER_PROFILE, driver.getCurrentUrl());

    }

    @Description("Переход из личного кабинета  по клику на Конструктор")
    @Test
    public void constructorClickTest() {
        registracion();
        loginAsRegisteredUser();
        constructorPage.clickPersonalAccountButton();
        stellarBurgersPage.clickConstructorButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));
        assertEquals("URL после клика по кнопке Конструктор из личного кабинета должен быть главной страницей", MAIN_PAGE, driver.getCurrentUrl());
    }

    @Description("Переход из личного кабинета на логотип Stellar Burgers")
    @Test
    public void logoClickTest() {
        registracion();
        loginAsRegisteredUser();
        constructorPage.clickPersonalAccountButton();
        stellarBurgersPage.clickLogoButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(MAIN_PAGE));
        assertEquals("URL после клика по логотипу Stellar Burgers должен быть главной страницей", MAIN_PAGE, driver.getCurrentUrl());
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


