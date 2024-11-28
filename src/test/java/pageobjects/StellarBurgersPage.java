package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class StellarBurgersPage {
    private final WebDriver driver;
    private final By loginText = By.xpath(".//main/div/h2");
    private final By emailField = By.xpath(".//*[@type='text']");
    private final By passwordField = By.xpath(".//*[@type='password']");
    private final By loginButton = By.xpath("//div/form/button");
    private final By registerButton = By.xpath("//div/p[1]/*[@href='/register']");
    private final By recoverButton = By.xpath("//div/p[2]/*[@href='/forgot-password']");
    private final By enterHeader = By.xpath("//h2[contains(text(),'Вход')]");
    private final By constructorButton = By.xpath(".//p[text() = 'Конструктор']");
    private final By profileButton = By.xpath(".//a[text() = 'Профиль']");
    private final By logoutButton = By.xpath(".//nav[starts-with(@class, 'Account_nav')]/ul/li/button");
    private final By logoButton = By.xpath(".//header/nav/div");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");

    public StellarBurgersPage(WebDriver driver) {
        this.driver = driver;
    }
    @Step("Заполнить поле Email")
    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполнить поле Пароль")
    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать на кнопку Вход")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Проверка видимости кнопки Вход")
    public boolean isLoginButtonVisible() {
        return driver.findElement(loginButton).isDisplayed();
    }

    @Step("Нажать на кнопку Зарегистрироваться")
    public void clickRegistrationButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Нажать на кнопку восстановления пароля")
    public void clickRecoverButton() {
        driver.findElement(recoverButton).click();
    }

    @Step("Проверка видимости заголовка Вход")
    public boolean isEnterHeaderVisible() {
        return driver.findElement(enterHeader).isDisplayed();
    }


    @Step("Ожидание загрузки страницы входа")
    public void waitForLoadLoginPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> driver.findElement(loginText).isDisplayed());
    }

    public void clickEmail() {
        driver.findElement(emailField).click();
    }
    public void waitLoadingProfilePage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(profileButton));
    }


    @Step("Нажать на Конструктор")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Нажать на кнопку Выход")
    public void clickLogoutButton() {
        waitButtonIsClickable();
        driver.findElement(logoutButton).click();
    }

    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogoButton() {
        driver.findElement(logoButton).click();
    }

    @Step("Подождать пока кнопка Выти станет кликабельной")
    public void waitButtonIsClickable() {
        new WebDriverWait(driver,Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));}
}

