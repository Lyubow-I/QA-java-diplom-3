package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConstructorPage {
    // Локаторы для активных разделов
        @FindBy(xpath = "//*[@id='root']/div/main/section[1]/div[2]/h2[2]") // Соусы
        private WebElement saucesSectionActive;

        @FindBy(xpath = "//*[@id='root']/div/main/section[1]/div[2]/h2[3]") // Начинки
        private WebElement fillingsSectionActive;

        @FindBy(xpath = "//*[@id='root']/div/main/section[1]/div[2]/h2[1]") // Булки
        private WebElement bunsSectionActive;

        // Локаторы для клика по разделам
        @FindBy(xpath = "//span[text()='Соусы']") // Раздел "Соусы"
        private WebElement saucesSection;

        @FindBy(xpath = "//span[text()='Начинки']") // Раздел "Начинки"
        private WebElement fillingsSection;

        @FindBy(xpath = "//span[text()='Булки']") // Раздел "Булки"
        private WebElement bunsSection;

        public ConstructorPage(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        public void selectSaucesSection() {
            saucesSection.click(); // Кликаем на элемент "Соусы"
        }

        public void selectFillingsSection() {
            fillingsSection.click(); // Кликаем на элемент "Начинки"
        }

        public void selectBunsSection() {
            bunsSection.click(); // Кликаем на элемент "Булки"
        }

        // Методы для получения текста заголовков
        public String getSaucesHeaderText() {
            return saucesSectionActive.getText(); // Получаем текст заголовка "Соусы"
        }

        public String getFillingsHeaderText() {
            return fillingsSectionActive.getText(); // Получаем текст заголовка "Начинки"
        }

        public String getBunsHeaderText() {
            return bunsSectionActive.getText(); // Получаем текст заголовка "Булки"
        }
        }



