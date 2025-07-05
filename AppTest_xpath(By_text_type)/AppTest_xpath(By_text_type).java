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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("Alert Handling")
@Feature("JavaScript Alerts on HerokuApp")
public class AppTest {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    @BeforeTest
    @Step("Setting up browser")
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

    @Test(description = "Handle JS Alert, Confirm, and Prompt")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Handle JavaScript alerts on the page")
    @Description("Test to handle simple alert, confirm alert, and prompt alert using Selenium WebDriver")
    public void loginTest() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        Thread.sleep(2000);

        handleSimpleAlert();
        handleConfirmAlert();
        handlePromptAlert();
    }

    @Step("Handle JS Alert")
    public void handleSimpleAlert() throws InterruptedException {
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        Alert alert = driver.switchTo().alert();
        Allure.addAttachment("JS Alert Text", alert.getText());
        System.out.println(alert.getText());
        Thread.sleep(1000);
        alert.accept();
        System.out.println("Alert accepted");
    }

    @Step("Handle JS Confirm Alert")
    public void handleConfirmAlert() throws InterruptedException {
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert confirm = driver.switchTo().alert();
        Allure.addAttachment("JS Confirm Text", confirm.getText());
        System.out.println(confirm.getText());
        Thread.sleep(1000);
        confirm.accept();
        System.out.println("Confirm accepted");
    }

    @Step("Handle JS Prompt Alert")
    public void handlePromptAlert() throws InterruptedException {
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        Alert prompt = driver.switchTo().alert();
        Allure.addAttachment("JS Prompt Text", prompt.getText());
        System.out.println(prompt.getText());
        Thread.sleep(1000);
        prompt.sendKeys("Hello");
        Thread.sleep(1000);
        prompt.accept();
        System.out.println("Prompt accepted");
    }

    @AfterTest
    @Step("Closing browser")
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }
}
