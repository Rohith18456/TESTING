package com.example;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.nio.file.Files;
import java.time.Duration;
import java.io.IOException;
// import org.testng.Assert;
import org.testng.annotations.AfterTest;
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

        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;

  }


@Test
public void loginTest() throws InterruptedException, IOException {
    driver.get("https://demoqa.com/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        BufferedReader br = new BufferedReader(new FileReader("data.csv"));
        String line;
        String[] headers = br.readLine().split(",");

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Map<String, String> row = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                row.put(headers[i], values[i]);
            }

            WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
            js.executeScript("arguments[0].scrollIntoView(true);", userInput);

            userInput.clear();
            userInput.sendKeys(row.get("username"));

            WebElement passInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            js.executeScript("arguments[0].scrollIntoView(true);", passInput);

            passInput.clear();
            passInput.sendKeys(row.get("password"));
            Thread.sleep(2000); 

            WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
            js.executeScript("arguments[0].scrollIntoView(true);", loginButton);
            loginButton.click();

            Thread.sleep(2000);
            driver.get("https://demoqa.com/login");
        }

}

@AfterTest
public void afterTest() {
  driver.quit();
}

}