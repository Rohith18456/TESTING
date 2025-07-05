package com.example;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import io.qameta.allure.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Epic("DemoQA Automation")
@Feature("Login Functionality")
public class AppTest {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;

    @BeforeTest
    @Step("Setting up WebDriver and browser")
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

    @Test(description = "Data-driven login test from CSV")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login using multiple sets of credentials from data.csv")
    @Description("Reads usernames and passwords from a CSV file and attempts login on https://demoqa.com/login")
    public void loginTest() throws InterruptedException, IOException {
        step("Navigating to login page");
        driver.get("https://demoqa.com/login");

        BufferedReader br = new BufferedReader(new FileReader("data.csv"));
        String line;
        String[] headers = br.readLine().split(",");

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Map<String, String> row = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                row.put(headers[i], values[i]);
            }

            step("Entering username: " + row.get("username"));
            WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
            js.executeScript("arguments[0].scrollIntoView(true);", userInput);
            userInput.clear();
            userInput.sendKeys(row.get("username"));

            step("Entering password");
            WebElement passInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            js.executeScript("arguments[0].scrollIntoView(true);", passInput);
            passInput.clear();
            passInput.sendKeys(row.get("password"));

            Thread.sleep(1000);

            step("Clicking login button");
            WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
            js.executeScript("arguments[0].scrollIntoView(true);", loginButton);
            loginButton.click();

            Allure.addAttachment("Attempted login with", "Username: " + row.get("username") + ", Password: " + row.get("password"));

            Thread.sleep(2000);
            driver.get("https://demoqa.com/login");
        }
        br.close();
    }

    @AfterTest
    @Step("Tearing down browser")
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("{description}")
    public void step(String description) {
        // Placeholder to label steps in Allure reports
    }
}
