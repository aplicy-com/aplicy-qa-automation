package com.aplicy.qa.automation.canadawanderlust;

import static com.aplicy.qa.automation.TestUtil.scrollPageToTheBottom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import com.aplicy.qa.automation.DriverUtil;
import java.time.Duration;
import javax.lang.model.element.Element;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

/** Tests for the Contact page of CanadaWanderlust. */
@SpringBootTest
@DisplayName("Tests for the Contact page of CanadaWanderlust.")
class CWContactPageTest {
  WebDriver driver;
  String mainUrl = "https://www.canadawanderlust.com";

  @BeforeEach
  @DisplayName("Set up the Chrome WebDriver in headless mode before each test")
  public void setUp() {
    // Initialize driver using utility method (headless mode enabled)
    driver = DriverUtil.getChromeDriver(true); // true for headless mode in production
  }

  /**
   * Verifies that the Contact page title matches the expected title.
   *
   * <p>This test navigates to the Contact page, and asserts that the page title matches the
   * expected title.
   */
  @Test
  @DisplayName("Verify Contact Page Title")
  void contactPageTitle() {
    driver.get(mainUrl + "/contact/");
    String expectedTitle = "Contact Us | Canada Wanderlust - Your Ultimate Travel Guide";
    assertEquals(expectedTitle, driver.getTitle());
  }

  /**
   * Verifies that the Contact form can be successfully submitted with valid inputs.
   *
   * <p>This test navigates to the Contact page, enters valid data into the form fields, submits the
   * form, and asserts that a success alert is displayed with the expected message.
   */
  @Test
  @DisplayName("Submit Contact Form.")
  void submitContactForm_success_form_submitted() {
    driver.get(mainUrl + "/contact/");

    // Locate the fields by their 'name' attributes
    WebElement nameField = driver.findElement(By.name("name"));
    WebElement phoneField = driver.findElement(By.name("phone"));
    WebElement emailField = driver.findElement(By.name("email"));
    WebElement messageField = driver.findElement(By.name("message"));

    // Enter data into the fields
    nameField.sendKeys("John Doe");
    phoneField.sendKeys("0777123456");
    emailField.sendKeys("john@example.com");
    messageField.sendKeys("This is a test message.");
    // Submit the form using the CSS selector for the button
    // Locate the button using XPath
    WebElement button = driver.findElement(By.xpath("//button[contains(text(), 'Send Message')]"));

    // Scroll to the button
    scrollPageToTheBottom(driver);

    // Click the button after scrolling
    button.click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement alert =
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'alert-success')]")));

    // Verify that the success alert is displayed
    assertTrue("Success alert should be visible", alert.isDisplayed());

    // Get the message text from the alert
    String actualMessage = alert.getText();

    // Assert that the message is as expected
    String expectedMessage = "Form Submitted Successfully";
    assertEquals(expectedMessage, actualMessage);
  }

  /**
   * Submits the contact form with various combinations of input data to verify that required fields
   * are validated correctly.
   *
   * <p>This test submits the form with the given input data and verifies that the form fields are
   * marked as valid or invalid as expected.
   *
   * @param name The name to enter into the form
   * @param phone The phone number to enter into the form
   * @param email The email address to enter into the form
   * @param message The message to enter into the form
   * @param nameValid Whether the name field should be valid
   * @param phoneValid Whether the phone field should be valid
   * @param emailValid Whether the email field should be valid
   * @param messageValid Whether the message field should be valid
   */
  @ParameterizedTest
  @CsvSource({
    " '', 0777123456, john@example.com, This is a test message.,false,true,true,true",
    "Jane Smith, 1234567890, jane@example.com, Another test message.,true,true,true,true",
    "Mike Doe, 'asdfsdsd' , mikeexample.com, Sample message for testing.,true,true,false,true"
  })
  @DisplayName("Submit Contact Form with Various Inputs")
  void submitContactForm_fields_required(
      String name,
      String phone,
      String email,
      String message,
      boolean nameValid,
      boolean phoneValid,
      boolean emailValid,
      boolean messageValid) {

    driver.get(mainUrl + "/contact/");
    // Locate the fields by their 'name' attributes
    WebElement nameField = driver.findElement(By.name("name"));
    WebElement phoneField = driver.findElement(By.name("phone"));
    WebElement emailField = driver.findElement(By.name("email"));
    WebElement messageField = driver.findElement(By.name("message"));

    // Enter data into the fields
    nameField.sendKeys(name);
    phoneField.sendKeys(phone);
    emailField.sendKeys(email);
    messageField.sendKeys(message);

    // Scroll to the button
    scrollPageToTheBottom(driver);

    // Locate the submit button
    WebElement button = driver.findElement(By.xpath("//button[contains(text(), 'Send Message')]"));

    // Try submitting the form without filling required fields
    button.click();

    // Validate the name field
    boolean isNameFieldValid = isFieldValid(nameField);
    boolean isPhoneFieldValid = isFieldValid(phoneField);
    boolean isEmailFieldValid = isFieldValid(emailField);
    boolean isMsgFieldValid = isFieldValid(messageField);

    // Assert that the name field is valid
    assertEquals(nameValid, isNameFieldValid);
    assertEquals(phoneValid, isPhoneFieldValid);
    assertEquals(emailValid, isEmailFieldValid);
    assertEquals(messageValid, isMsgFieldValid);
  }

  /**
   * Returns true if the given form field is valid according to the browser's form validation rules.
   *
   * <p>This method uses the {@link JavascriptExecutor} to execute the  method on the given field, which returns a boolean indicating whether
   * the field is valid or not.
   *
   * @param nameField the form field to check
   * @return true if the field is valid, false otherwise
   */
  private boolean isFieldValid(WebElement nameField) {
    return (boolean)
        ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].checkValidity();", nameField);
  }

  /**
   * Closes the WebDriver instance created by the test after the test is completed.
   *
   * <p>This cleanup is necessary to prevent resource leaks and ensure that the test process can
   * exit cleanly.
   */
  @AfterEach
  @DisplayName("Clean up and close the WebDriver after each test")
  public void tearDown() {
    DriverUtil.quitDriver(driver);
  }
}
