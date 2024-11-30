package tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import models.UserApi;
import models.WebDriverCreator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.*;
import org.junit.After;
import static models.Api.MAIN_PAGE;
import static org.junit.Assert.assertTrue;


@DisplayName("Регистрация нового пользователя")
    public class RegistrationTest {
    private String name;
    private String email;
    private String password;
    private WebDriver driver;
    private StellarBurgersPage stellarBurgersPage;
    private RegistrationPage registrationPage;
    private ConstructorPage constructorPage;
    private User user;
    private UserApi userApi;
    private String token;
    private String accessToken;
    private WebDriver webDriver;

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

    @Step("Переход в форму регистрации")
    public void goToRegistrationForm() {
        constructorPage.clickLoginButton();
        stellarBurgersPage.clickRegistrationButton();
    }


    @DisplayName("Ошибка при регистрации с паролем менее 6 символов")
    @Test
    public void userRegistrationWithShortPasswordTest() {
        goToRegistrationForm();
        registrationPage.setName(user.getName());
        registrationPage.setEmail(user.getEmail());
        registrationPage.setPassword("1q2w3");

        boolean isErrorTextVisible = registrationPage.isIncorrectPasswordTextVisible();
        assertTrue("Текст ошибки виден на странице", isErrorTextVisible);
        System.out.println("Пользователя невозможно зарегистрировать: указан недопустимый пароль");
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}






