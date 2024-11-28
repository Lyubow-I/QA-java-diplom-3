package tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.*;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static pageobjects.Api.MAIN_PAGE;


@DisplayName("Регистрация нового пользователя")
    public class RegistrationTest {
        private WebDriver driver;
        private String name;
        private String email;
        private String password;
        private StellarBurgersPage stellarBurgersPage;
        private RegistrationPage registrationPage;
        private ConstructorPage constructorPage;
        private User user;
        private UserApi userApi;
        private String accessToken;
        @Step("Подготовка данных и браузера")
        @Before
        public void setUp() {
            user = User.getUser();
            userApi = new UserApi();
            accessToken = userApi.createUser(user);
            name = "Test User";
            email = "test@example.com";
            password = "1q2w3e";

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

        @Step("Переход в форму регистрации")
        public void goToRegistrationForm() {
            constructorPage.clickLoginButton();
            stellarBurgersPage.clickRegistrationButton();
        }

        @DisplayName("Успешная регистрация пользователя")
        @Test
        public void correctUserRegistrationTest() {
            goToRegistrationForm();

            registrationPage.setName(name);
            registrationPage.setEmail(email);
            registrationPage.setPassword(password);

            registrationPage.clickRegistrationButton();

            boolean isEnterHeaderVisible = stellarBurgersPage.isLoginButtonVisible();
            assertTrue("Заголовок Вход отображается", isEnterHeaderVisible);
            System.out.println("Пользователь успешно зарегистрирован с email: " + email);
        }

        @DisplayName("Ошибка при регистрации с паролем менее 6 символов")
        @Test
        public void userRegistrationWithShortPasswordTest() {
            password = RandomStringUtils.randomAlphabetic(4);
            goToRegistrationForm();

            registrationPage.setName(name);
            registrationPage.setEmail(email);
            registrationPage.setPassword("1q2w3");
            registrationPage.clickRegistrationButton();

            boolean isErrorTextVisible = registrationPage.isIncorrectPasswordTextVisible();
            assertTrue("Текст ошибки виден на странице", isErrorTextVisible);
            System.out.println("Пользователя невозможно зарегистрировать: указан недопустимый пароль");
        }
    }

