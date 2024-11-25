package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class StellarBurgersPage {
    private final WebDriver driver;

// Локатор для кнопки "Войти в аккаунт"
@FindBy(xpath = "//button[contains(text(),'Войти в аккаунт')]")
private WebElement loginButton;

// Локатор для кнопки регистрации
@FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/p[1]/a")
private WebElement registerButton;

// Локатор для кнопки "Личный Кабинет"
@FindBy(xpath = "//*[@id=\"root\"]/div/header/nav/a/p")
private WebElement personalCabinetButton;

// Локатор для кнопки "Войти" в форме регитрации
@FindBy(xpath = "//a[text()='Войти']")
private WebElement loginButton2;

// Локатор для поля ввода email
@FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[1]/div/div/input")
private WebElement emailInput;

// Локатор для поля ввода пароля
@FindBy(xpath = "//input[@type='password']")
private WebElement passwordInput;

// Локатор для поля ввода пароля в форме восстановления пароля
@FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset/div/div/input")
private WebElement passwordRestoreInput;

// Локатор для книпоки "Восстановить пароль"
@FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/p[2]/a")
private WebElement recoverPasswordButton;

// Локатор для кнопки "Войти" в: входе на главной странице, в форме регистрации, через кнопку 'Личный кабинет'
@FindBy(xpath = "//button[text()='Войти']")
private WebElement loginButton3;

// Локатор для кнопки "Войти" в форме восстановления пароля
//@FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/p/a")
//private WebElement LoginButton2;

// Локатор для кнопки "Конструктор"
@FindBy(xpath = "//*[@id=\"root\"]/div/header/nav/ul/li[1]/a/p")
private WebElement constructorButton;

// Локатор для кнопки "Выйти"
@FindBy(xpath = "//button[text()='Выход']")
private WebElement logoutButton;

// Локатор для логотипа Stellar Burgers
@FindBy(xpath = "//*[@id=\"root\"]/div/header/nav/div")
private WebElement logo;

public StellarBurgersPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
}
    // Метод для клика по кнопке "Войти в аккаунт"
    public void clickLoginButton() {
        loginButton.click();
    }

    // Метод для клика по кнопке регистрации
    public void clickRegisterButton() {
        registerButton.click();
    }

    // Метод для клика по кнопке "Личный Кабинет"
    public void clickPersonalCabinetButton() {
        personalCabinetButton.click();
    }

    // Метод для клика по кнопке "Войти" в форме регистрации
    public void clickLoginButton2() {
        loginButton2.click();
    }

    // Метод для ввода email
    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    // Метод для ввода пароля
    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    // Метод для ввода пароля в форме восстановления пароля
    public void enterPasswordRestore(String password) {
        passwordRestoreInput.sendKeys(password);
    }

    // Метод для клика по кнопке "Восстановить пароль"
    public void clickRecoverPasswordButton() {
        recoverPasswordButton.click();
    }

    //Метод для клика по кнопке "Войти" в других формах
    public void clickLoginButton3() {
      loginButton3.click();
    }

    // Метод для клика по кнопке "Конструктор"
    public void clickConstructorButton() {
        constructorButton.click();
    }

    // Метод для клика по кнопке "Выйти"
    public void clickLogoutButton() {
        logoutButton.click();
    }

    // Метод для получения логотипа
    public WebElement getLogo() {
        return logo;
    }
}