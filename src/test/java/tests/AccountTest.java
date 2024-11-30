package tests;
import io.opentelemetry.sdk.metrics.internal.view.AttributesProcessor;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.*;
import org.junit.After;

import static models.Api.BASE_URL;
import static models.Api.MAIN_PAGE;
import static models.User.getUser;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AccountTest {
    private WebDriver driver;
    private ConstructorPage constructorPage;
    private StellarBurgersPage stellarBurgersPage;
    private RegistrationPage registrationPage;
    private User user;
    private UserApi userApi;
    private String accessToken;
    private String token;
    private static final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/account/profile";

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


    @Step("Авторизация пользователя")
    public void userAuthorization() {
        constructorPage.clickLoginButton();
        stellarBurgersPage.setEmail(user.getEmail());
        stellarBurgersPage.setPassword(user.getPassword());
        stellarBurgersPage.clickLoginButton();
    }

    @Description("Тестирование перехода в личный кабинет по клику на Личный кабинет")
    @Test
    public void goToAccountFromMainPageTest() {
        userAuthorization();
        constructorPage.clickPersonalAccountButton();
        assertEquals("URL после входа в аккаунт и повторного клика по кнопке «Личный кабинет» должен быть переход на страницу профиля", LOGIN_URL, driver.getCurrentUrl());
    }

    @Description("Переход из личного кабинета  по клику на Конструктор")
    @Test
    public void constructorClickTest() {
        userAuthorization();
        constructorPage.clickPersonalAccountButton();
        stellarBurgersPage.clickConstructorButton();
        assertEquals("URL после клика по кнопке Конструктор из личного кабинета должен быть главной страницей", MAIN_PAGE, driver.getCurrentUrl());
    }

    @Description("Переход из личного кабинета на логотип Stellar Burgers")
    @Test
    public void logoClickTest() {
        userAuthorization();
        constructorPage.clickPersonalAccountButton();
        stellarBurgersPage.clickLogoButton();
        assertEquals("URL после клика по логотипу Stellar Burgers должен быть главной страницей", MAIN_PAGE, driver.getCurrentUrl());
    }

    @Description("Выход по кнопке «Выйти» в личном кабинете")
    @Test
    public void logoutTest() {
        userAuthorization();
        constructorPage.clickPersonalAccountButton();
        stellarBurgersPage.clickLogoutButton();
        stellarBurgersPage.waitForLoadLoginPage();
        boolean isLoginHeader = stellarBurgersPage.isEnterHeaderVisible();
        assertTrue("Кнопка войти отображается на странице.", isLoginHeader);
        System.out.println("Пользователь Вышел из аккаунта");
    }

    @After
    public void tearDown() {
        RestAssured.baseURI = BASE_URL;
        User user = getUser ();
        userApi = new UserApi();
        driver.quit();
        Response response = userApi.createUser(user);
        response.then().assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true));

        token = response.as(UserToken.class).getAccessToken();
        userApi.deleteUser(token);
    }
    }

