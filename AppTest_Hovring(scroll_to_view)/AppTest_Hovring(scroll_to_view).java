package com.example;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("UI Interaction")
@Feature("Menu Hover Feature")
public class AppTest {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    @BeforeTest
    @Step("Setup browser and initialize drivers")
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    @Test(description = "Test mouse hovering on demoqa menu")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to hover over nested menus on https://demoqa.com/menu")
    @Story("Hover over multi-level menu using Actions class")
    public void loginTest() throws InterruptedException {
        driver.get("https://demoqa.com/menu");

        WebElement mainItem2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Main Item 2']")));
        js.executeScript("arguments[0].scrollIntoView(true);", mainItem2);
        actions.moveToElement(mainItem2).perform();
        Thread.sleep(1000);

        WebElement subSubList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='SUB SUB LIST »']")));
        actions.moveToElement(subSubList).perform();
        Thread.sleep(1000);

        WebElement subSubItem2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sub Sub Item 2']")));
        actions.moveToElement(subSubItem2).perform();
        Thread.sleep(1000);

        System.out.println("Hovering completed successfully.");
    }

    @AfterTest
    @Step("Teardown the browser")
    public void afterTest() {
        driver.quit();
    }
}
