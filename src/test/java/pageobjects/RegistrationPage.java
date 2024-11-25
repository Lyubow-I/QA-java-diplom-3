package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;
    // Локатор для кнопки "Войти в аккаунт"
    @FindBy(xpath = "//button[contains(text(),'Войти в аккаунт')]")
    private WebElement loginButton;

    // Локатор для кнопки регистрации перед
    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/p[1]/a")
    private WebElement registerButton;

     //локатор для кнопки регистрации после
    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButtonAfter;

    // Локатор для поля ввода имени
    @FindBy(xpath = "//input[@name='name']")
    private WebElement nameInput;

    // Локатор для поля ввода email
    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input")
    private WebElement emailInput;

    // Локатор для поля ввода имени
    @FindBy(xpath = "//input[@name='name']")
    private WebElement nameField;

    // Локатор для поля ввода email
    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input")
    private WebElement emailField;

    // Локатор для поля ввода пароля
    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/div/input")
    private WebElement passwordInput;

    // Локатор для сообщения об ошибке
    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/p")
    private WebElement errorMessageElement;

    // Конструктор
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Метод для ввода имени
    public void enterName2(String name) {
        nameField.sendKeys(name);
    }

    // Метод для ввода email
    public void enterEmail2(String email) {
        emailField.sendKeys(email);
    }


    public void scrollToRegisterButton() {
        WebElement registerButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/p[1]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
    }

    public void clickLoginButton() {

        loginButton.click();
    }
        public void clickRegisterButtonAfter() {

            registerButtonAfter.click();
    }

    public void enterName(String name) {

        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {

        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {

        passwordInput.sendKeys(password);
    }

    public void clickRegisterButton() {

        registerButton.click();
    }

    public WebElement getErrorMessageElement() {
        return errorMessageElement;
    }
    public boolean isErrorMessageElementDisplayed() {

        return errorMessageElement.isDisplayed();
    }
}
