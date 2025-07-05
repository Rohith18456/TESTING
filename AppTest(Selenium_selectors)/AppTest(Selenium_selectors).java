package com.example;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

@Epic("Dodo Portal Automation")
@Feature("Login and Menu Navigation")
public class AppTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    @Step("Setup browser")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login and verify top menu navigation")
    public void loginTest() {
        driver.get("https://dodo.quantumnique.tech/");

       

        Map<String, String> menuList = new HashMap<>();
        menuList.put("ASSESSMENTS", "assessments");
        menuList.put("COURSES", "courses");
        menuList.put("CODE", "code-compiler");
        menuList.put("PRACTICE", "practice");
        menuList.put("LSRW", "lsrw");
        menuList.put("BLOGS", "blog");

        for (Map.Entry<String, String> entry : menuList.entrySet()) {
            step("Clicking menu: " + entry.getKey());
            WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(entry.getKey())));
            menu.click();
            wait.until(ExpectedConditions.urlContains(entry.getValue()));

            String currentUrl = driver.getCurrentUrl();
            String expectedUrl = "https://dodo.quantumnique.tech/" + entry.getValue() + "/";
            System.out.println("Expected: " + expectedUrl + " | Actual: " + currentUrl);

            if (currentUrl.equals(expectedUrl)) {
                System.out.println("✅ PASS: " + entry.getKey());
            } else {
                System.out.println("❌ FAIL: " + entry.getKey());
            }
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Step("{message}")
    public void step(String message) {
        // for Allure logs
    }
}
