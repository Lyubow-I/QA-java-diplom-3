package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.ConstructorPage;
import pageobjects.RegistrationPage;
import pageobjects.StellarBurgersPage;
import java.time.Duration;
import static models.Api.*;
import static models.Api.USER_LOGIN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;

    @DisplayName("Выход из аккаунта")
    public class LogoutTest {

        private WebDriver driver;
        private StellarBurgersPage stellarBurgersPage;
        private RegistrationPage registrationPage;
        private ConstructorPage constructorPage;
        private User user;
        private UserApi userApi;
        private UserToken userToken;
        private String accessToken;
        private WebDriver webDriver;
        private UserClient userClient;

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
            userToken = new UserToken();
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
            UserRandom userRandom = UserRandom.getUser();
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

        @Description("Выход по кнопке «Выйти» в личном кабинете")
        @Test
        public void loginAsRegisteredUser() {
            registracion();
            UserRandom userRandom = UserRandom.getUser();
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
            constructorPage.clickPersonalAccountButton();
            stellarBurgersPage.clickLogoutButton();
            userApi.deleteUser(accessToken);
        }

        @Step("Закрытие браузера")
        @After
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }
    }
