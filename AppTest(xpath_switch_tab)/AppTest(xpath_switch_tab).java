package com.example;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import io.qameta.allure.Step;

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

    @Test(description = "Test switching between multiple browser windows")
    @Description("Test opens new tab, new window, and message window, and switches between them.")
    public void loginTest() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");

        String mainWindowHandle = driver.getWindowHandle();

        // Open new tab
        clickButtonById("tabButton");
        switchToNewWindow(mainWindowHandle, "new tab");

        // Open new window
        driver.switchTo().window(mainWindowHandle);
        clickButtonById("windowButton");
        switchToNewWindow(mainWindowHandle, "new window");

        // Open message window (use JS to avoid ad blocking)
        driver.switchTo().window(mainWindowHandle);
        WebElement messageBtn = driver.findElement(By.id("messageWindowButton"));
        js.executeScript("arguments[0].scrollIntoView(true);", messageBtn);
        js.executeScript("arguments[0].click();", messageBtn);
        switchToNewWindow(mainWindowHandle, "message window");

        // Switch back
        driver.switchTo().window(mainWindowHandle);
    }

    @Step("Clicking button with ID: {id}")
    public void clickButtonById(String id) {
        WebElement btn = driver.findElement(By.id(id));
        btn.click();
    }

    @Step("Switching to {windowType}")
    public void switchToNewWindow(String mainHandle, String windowType) throws InterruptedException {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(mainHandle)) {
                driver.switchTo().window(handle);
                System.out.println("Switched to " + windowType + ": " + handle);
                Thread.sleep(2000); // wait to see the effect
                break;
            }
        }
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
