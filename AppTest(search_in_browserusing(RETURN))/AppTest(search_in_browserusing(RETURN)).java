package com.example;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
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
import io.qameta.allure.Story;

@Epic("Search Functionality")
@Feature("Google Search")
public class AppTest {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    @BeforeTest
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;

        driver.get("https://www.google.com");
    }

    @Test(description = "Search for Amazon and verify results")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User searches for 'Amazon' on Google")
    @Description("This test searches 'Amazon' in Google and verifies if results are shown.")
    public void loginTest() throws InterruptedException, IOException {
        Thread.sleep(2000); 
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Amazon", Keys.RETURN);
        Thread.sleep(5000);

        if (driver.getPageSource().contains("No results found.")) {
            System.out.println("❌ Test Failed: No results found for the search query.");
        } else {
            System.out.println("✅ Test Passed: Results found for Amazon.");
        }
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
