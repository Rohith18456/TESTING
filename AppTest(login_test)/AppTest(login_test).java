package com.example;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Practice Test Automation")
@Feature("Login and Alert Handling")
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

    @BeforeClass
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

    @Test(description = "Valid Login Test")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with valid credentials")
    @Description("Login using correct username and password and verify success.")
    public void testLogin() throws InterruptedException {
        driver.get("https://practicetestautomation.com/practice-test-login/");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("student", "Password123");

        Thread.sleep(2000);
        String result = driver.findElement(By.tagName("h1")).getText();
        if (result.contains("Logged In Successfully")) {
            System.out.println("✅ Login Test Passed");
        } else {
            System.out.println("❌ Login Test Failed");
        }
    }

    @Test(description = "Handle JS Alerts")
    @Severity(SeverityLevel.NORMAL)
    @Story("Work with JavaScript alerts")
    @Description("Handle alert, confirm, and prompt dialogs using Selenium.")
    public void testJSAlerts() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        // JS Alert
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = driver.switchTo().alert();
        System.out.println("Alert Text: " + alert.getText());
        alert.accept();
        Thread.sleep(1000);

        // JS Confirm
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert confirm = driver.switchTo().alert();
        System.out.println("Confirm Text: " + confirm.getText());
        confirm.accept();
        Thread.sleep(1000);

        // JS Prompt
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        Alert prompt = driver.switchTo().alert();
        System.out.println("Prompt Text: " + prompt.getText());
        prompt.sendKeys("Hello from TestNG");
        prompt.accept();
        Thread.sleep(1000);

        System.out.println("✅ JS Alert Handling Completed");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
