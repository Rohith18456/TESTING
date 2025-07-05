package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

@Epic("Practice Test Automation")
@Feature("Drag and Drop Functionality")
public class AppTest {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://jqueryui.com/droppable/"); // Missing in your original — added
    }

    @Test(description = "Drag and Drop Test using iframe")
    @Severity(SeverityLevel.NORMAL)
    @Story("Perform drag and drop inside an iframe")
    @Description("Switch to iframe and perform drag and drop. Verify the text changes to 'Dropped!'.")
    public void dragAndDropTest() throws InterruptedException {
        WebElement frame = driver.findElement(By.className("demo-frame"));
        driver.switchTo().frame(frame);

        WebElement drag = driver.findElement(By.id("draggable"));
        WebElement drop = driver.findElement(By.id("droppable"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(drag, drop).perform();
        Thread.sleep(2000); // Optional: better to use WebDriverWait in real test

        String resultText = drop.getText();
        assert resultText.equals("Dropped!") : "Expected 'Dropped!' but got: " + resultText;
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
