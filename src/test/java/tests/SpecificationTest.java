package tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import pageobjects.ConstructorPage;
import pageobjects.RegistrationPage;
import pageobjects.StellarBurgersPage;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;
import pageobjects.WebDriverManager;
public class SpecificationTest {
    protected WebDriver driver;
    protected ConstructorPage constructorPage;
    protected RegistrationPage registrationPage;
    protected StellarBurgersPage stellarBurgersPage;
    protected final String baseUrl = "https://stellarburgers.nomoreparties.site/";
    protected final String browser;
    public final String userName = "zet";
    public final String userEmail = "zet@ya.ru";
    public final String userPassword = "1q2w3e4r";
    protected String name;
    protected String email;
    public SpecificationTest(String browser) {
        this.browser = browser;
    }
    @Step
    @Before
    public void setUp() {
        driver = WebDriverManager.createWebDriver();
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        constructorPage = new ConstructorPage(driver);
        stellarBurgersPage  = new StellarBurgersPage(driver);
        registrationPage = new RegistrationPage(driver);
        System.out.println("Тест запущен: " + browser);
        Faker faker = new Faker();
        name = faker.name().fullName();
        email = faker.internet().emailAddress();
    }

    @Step
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
