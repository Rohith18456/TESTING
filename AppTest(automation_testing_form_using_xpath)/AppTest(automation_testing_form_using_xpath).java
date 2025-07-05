package com.example;

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

@Epic("Practice Test Automation")
@Feature("Form Submission")
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

        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;

        driver.get("https://demoqa.com/automation-practice-form");
    }

    @Test(description = "Automation Practice Form Submission")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Fill and Submit the Practice Form")
    @Description("Fills out the DemoQA Practice Form with valid details and submits it.")
    public void formSubmissionTest() throws InterruptedException {
        driver.findElement(By.id("firstName")).sendKeys("S");
        Thread.sleep(1000);

        driver.findElement(By.id("lastName")).sendKeys("Rohith");
        Thread.sleep(1000);

        driver.findElement(By.id("userEmail")).sendKeys("717822e238@kce.ac.in");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//label[@for='gender-radio-1']")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("userNumber")).sendKeys("9026577582");
        Thread.sleep(1000);

        driver.findElement(By.id("dateOfBirthInput")).click();
        driver.findElement(By.className("react-datepicker__month-select")).sendKeys("JUNE");
        driver.findElement(By.className("react-datepicker__year-select")).sendKeys("2005");
        driver.findElement(By.xpath("//div[@class='react-datepicker__day react-datepicker__day--030']")).click();
        Thread.sleep(1000);

        WebElement subject = driver.findElement(By.id("subjectsInput"));
        subject.sendKeys("Software Testing");
        subject.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        driver.findElement(By.xpath("//label[@for='hobbies-checkbox-1']")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("uploadPicture"))
              .sendKeys("C:\\Users\\Subash Chandran K\\Pictures\\God of War Ragnarök\\ScreenShot-2025-5-27_10-57-33.png");
        Thread.sleep(1000);

        driver.findElement(By.id("currentAddress")).sendKeys("Karpagam College of Engineering");
        Thread.sleep(1000);

        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("react-select-3-input")).sendKeys("Tamil Nadu");
        driver.findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("react-select-4-input")).sendKeys("Coimbatore");
        driver.findElement(By.id("react-select-4-input")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        driver.findElement(By.id("submit")).click();
        Thread.sleep(2000);
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
