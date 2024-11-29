package tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;;
import models.WebDriverCreator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.ConstructorPage;

import static models.Api.MAIN_PAGE;

public class ConstructorSectionsTest {
    private WebDriver driver;
    private ConstructorPage constructorPage;

    @Step("Подготовка данных и браузера")
    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverCreator.createWebDriver(browser);
        driver.get(MAIN_PAGE);
        driver.manage().window().maximize();

        constructorPage = new ConstructorPage(driver);
    }

    @Test
    @DisplayName("Переход к разделу Булки")
    public void bunSectionTest() {
        constructorPage.clickSauceButton();
        constructorPage.clickBunsButton();
        boolean isBunsChoiceSectionVisible = constructorPage.isChoiceSectionVisible();
        Assert.assertTrue(isBunsChoiceSectionVisible);
    }

    @Test
    @DisplayName("Переход к разделу Соусы")
    public void sauceSectionTest() {
        constructorPage.clickSauceButton();
        boolean isSauceChoiceSectionVisible = constructorPage.isChoiceSectionVisible();
        Assert.assertTrue(isSauceChoiceSectionVisible);
    }

    @Test
    @DisplayName("Переход к разделу Начинки")
    public void fillingSectionTest() {
        constructorPage.clickFillingButton();
        boolean isFillingChoiceSectionVisible = constructorPage.isChoiceSectionVisible();
        Assert.assertTrue(isFillingChoiceSectionVisible);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}