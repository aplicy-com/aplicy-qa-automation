package com.aplicy.qa.automation.canadawanderlust;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aplicy.qa.automation.DriverUtil;
import java.time.Duration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;

/** Tests for the CanadaWanderlust Application Core. */
@SpringBootTest
@DisplayName("Canada Wanderlust Application Core Tests")
class CWApplicationCoreTests {

  WebDriver driver;
  String mainUrl = "https://www.canadawanderlust.com";

  @BeforeEach
  @DisplayName("Set up the Chrome WebDriver in headless mode before each test")
  public void setUp() {
    // Initialize driver using utility method (headless mode enabled)
    driver = DriverUtil.getChromeDriver(true); // true for headless mode
  }

  /**
   * Verifies that the Canada Wanderlust home page title matches the expected title.
   *
   * <p>This test navigates to the home page, and asserts that the page title matches the expected
   * title.
   */
  @Test
  @DisplayName("Verify that the CanadaWanderlust home page title is correct")
  void homePageTitle() {
    String expectedTitle = "CanadaWanderlust | Best Travel Destinations & Tips | Explore Canada.";
    driver.get(mainUrl);
    String actualTitle = driver.getTitle();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    assertEquals(expectedTitle, actualTitle);
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
    // Clean up using utility method
    DriverUtil.quitDriver(driver);
  }

  /** Tests for the Destinations page of CanadaWanderlust. */
  @Nested
  @DisplayName("Test Explore Destinations Page")
  class ExploreDestinationsTests {

    /**
     * Verifies that the Destinations page title matches the expected title.
     *
     * <p>This test navigates to the Destinations page, and asserts that the page title matches the
     * expected title.
     */
    @Test
    @DisplayName("Verify that the Destinations page title is correct")
    void exploreDestinationsPageTitle() {
      driver.get(mainUrl + "/travel-destinations");
      String expectedTitle =
          "Travel Destinations | Explore the World's Best Spots with Canada Wanderlust";
      assertEquals(expectedTitle, driver.getTitle());
    }
  }

  /** Tests for the Adventure Trips page of CanadaWanderlust. */
  @Nested
  @DisplayName("Test Adventure Trips Page")
  class AdventureTripsTests {

    /**
     * Verifies that the Adventure Trips page loads correctly and displays the correct title for
     * each destination in the CSV file.
     *
     * <p>This test uses a parameterized test to load each destination URL and verify that the title
     * matches the expected title from the CSV file.
     *
     * @param url the URL of the destination page to load
     * @param expectedTitle the expected title of the page
     */
    @ParameterizedTest(name = "Verify that the Adventure Trip for {1} loads correctly at {0}")
    @CsvFileSource(resources = "/__file/cw-destination-data.csv", numLinesToSkip = 1)
    @DisplayName("Verify that the Adventure Trips page loads correctly")
    void adventureTripsPage(String url, String expectedTitle) {
      driver.get(mainUrl + "/destination/" + url);
      assertEquals(expectedTitle, driver.getTitle());
    }
  }

  /** Tests for the Contact page of CanadaWanderlust. */
  @Nested
  @DisplayName("Test Contact Page")
  class ContactPageTests {

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
  }
}
