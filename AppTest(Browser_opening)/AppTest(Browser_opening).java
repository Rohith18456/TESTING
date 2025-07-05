package com.example;

import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

@Epic("Practice Test Automation")
@Feature("Google Launch")
public class AppTest {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    @Test(description = "Google Homepage Launch Test")
    @Severity(SeverityLevel.NORMAL)
    @Story("Open Google homepage")
    @Description("This test opens the Google homepage and verifies if the page title contains 'Google'.")
    public void openGoogleHomePage() {
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        assert title.contains("Google");
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
