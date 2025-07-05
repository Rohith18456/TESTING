package com.example;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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

@Epic("Practice Test Automation")
@Feature("Navigation and Login Functionality")
public class AppTest {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    // Page Object for Login
    class LoginPage {
        WebDriver driver;
        By username = By.id("username");
        By password = By.id("password");
        By loginBtn = By.id("submit");

        public LoginPage(WebDriver driver) {
            this.driver = driver;
        }

        public void login(String user, String pass) {
            driver.findElement(username).sendKeys(user);
            driver.findElement(password).sendKeys(pass);
            driver.findElement(loginBtn).click();
        }
    }

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

    @Test(priority = 1, description = "Navigation Test using Selenium WebDriver")
    @Severity(SeverityLevel.NORMAL)
    @Story("Test browser navigation commands")
    @Description("Open Google, navigate to Wikipedia, refresh, go back and forward.")
    public void testBrowserNavigation() throws InterruptedException {
        driver.get("https://www.google.com/");
        driver.navigate().to("https://www.wikipedia.org/");
        Thread.sleep(2000);

        driver.navigate().refresh();
        Thread.sleep(2000);
        System.out.println("✅ Refreshed the page");

        driver.navigate().back();
        Thread.sleep(2000);
        System.out.println("✅ Navigated back to the previous page");

        driver.navigate().forward();
        Thread.sleep(2000);
        System.out.println("✅ Navigated forward to the next page");
    }

    @Test(priority = 2, description = "Valid Login Test")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with valid credentials")
    @Description("Login using correct username and password and verify successful login.")
    
    
    

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
