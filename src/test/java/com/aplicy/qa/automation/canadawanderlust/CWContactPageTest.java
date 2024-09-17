package com.aplicy.qa.automation.canadawanderlust;

import com.aplicy.qa.automation.DriverUtil;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CWContactPageTest {
    WebDriver driver;
    String mainUrl = "https://www.canadawanderlust.com";

    @BeforeEach
    @DisplayName("Set up the Chrome WebDriver in headless mode before each test")
    public void setUp() {
        // Initialize driver using utility method (headless mode enabled)
        driver = DriverUtil.getChromeDriver(true);  // true for headless mode
    }

    @Test
    @DisplayName("Verify Contact Page Title")
    void contactPageTitle() {
        driver.get(mainUrl + "/contact/");
        String expectedTitle = "Contact Us | Canada Wanderlust - Your Ultimate Travel Guide";
        Assertions.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    @DisplayName("Submit Contact Form")
    void submitContactForm() {
        driver.get(mainUrl + "/contact/");

        // Locate the Name, Phone, Email, and Message fields
        WebElement nameField = driver.findElement(By.name("name"));
        WebElement phoneField = driver.findElement(By.name("phone"));
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement messageField = driver.findElement(By.name("button -md -dark-1 bg-accent-1 text-white col-12"));

        // Enter data into the fields
        nameField.sendKeys("John Doe");
        phoneField.sendKeys("0777123456");
        emailField.sendKeys("john@example.com");
        messageField.sendKeys("This is a test message.");

        // Submit the form
        WebElement sendMessageButton = driver.findElement(By.cssSelector("button[type='submit']"));
        sendMessageButton.click();

        // Verify success message (Assume a div with class 'success-message' appears on successful submission)
        WebElement successMessage = driver.findElement(By.className("success-message"));
        assertTrue(successMessage.isDisplayed(), "Form submission failed or success message not displayed.");
    }

    @AfterEach
    @DisplayName("Clean up and close the WebDriver after each test")
    public void tearDown() {
        DriverUtil.quitDriver(driver);
    }
}
