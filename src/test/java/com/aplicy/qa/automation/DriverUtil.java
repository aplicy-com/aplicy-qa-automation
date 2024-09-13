package com.aplicy.qa.automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverUtil {

    // Method to initialize ChromeDriver with headless mode option
    public static WebDriver getChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();  // Optional: maximize window in non-headless mode
        return driver;
    }

    // Method to quit the driver (optional for centralized cleanup)
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}
