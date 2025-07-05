package com.example;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
@Feature("Multiple UI Tests")
public class AppTest {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    @Test(description = "Valid Login Test")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with valid credentials")
    @Description("Login using correct username and password and verify successful login.")
    public void loginTest() {
        driver.get("https://practicetestautomation.com/practice-test-login/");

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.id("submit"));

        username.sendKeys("student");
        password.sendKeys("Password123");
        loginBtn.click();

        String successText = driver.findElement(By.tagName("h1")).getText();
        assert successText.contains("Logged In Successfully");
    }

    @Test(description = "JS Alert Handling Test")
    @Severity(SeverityLevel.NORMAL)
    @Story("Handle JavaScript Alerts")
    @Description("Handles JS Alert, Confirm, and Prompt boxes and verifies actions.")
    public void alertHandlingTest() throws InterruptedException {
        try {
            driver.get("https://the-internet.herokuapp.com/javascript_alerts");

            // JS Alert
            driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert says: " + alert.getText());
            alert.accept();

            // JS Confirm
            driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
            Alert confirm = driver.switchTo().alert();
            System.out.println("Confirm says: " + confirm.getText());
            confirm.accept();

            // JS Prompt
            driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
            Alert prompt = driver.switchTo().alert();
            System.out.println("Prompt says: " + prompt.getText());
            prompt.sendKeys("Hello from Selenium!");
            prompt.accept();

        } catch (NoSuchWindowException e) {
            System.err.println("Window already closed: " + e.getMessage());
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
