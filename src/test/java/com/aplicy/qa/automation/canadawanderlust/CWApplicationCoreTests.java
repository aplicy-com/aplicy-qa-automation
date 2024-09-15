package com.aplicy.qa.automation.canadawanderlust;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import com.aplicy.qa.automation.DriverUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@DisplayName("Canada Wanderlust Application Core Tests")
class CWApplicationCoreTests {

  WebDriver driver;
  String mainUrl = "https://www.canadawanderlust.com";

  @BeforeEach
  @DisplayName("Set up the Chrome WebDriver in headless mode before each test")
  public void setUp() {
    // Initialize driver using utility method (headless mode enabled)
    driver = DriverUtil.getChromeDriver(true);  // true for headless mode
  }

  @Test
  @DisplayName("Verify that the CanadaWanderlust home page title is correct")
  void homePageTitle() {
    String expectedTitle = "CanadaWanderlust | Best Travel Destinations & Tips | Explore Canada.";
    driver.get(mainUrl);
    String actualTitle = driver.getTitle();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
    assertEquals(expectedTitle, actualTitle);
  }
  @Nested
  @DisplayName("Test Explore Destinations Page")
  class ExploreDestinationsTests {

    @Test
    @DisplayName("Verify that the Destinations page title is correct")
    void exploreDestinationsPageTitle() {
      driver.get(mainUrl + "/travel-destinations");
      String expectedTitle = "Travel Destinations | Explore the World's Best Spots with Canada Wanderlust";
      assertEquals(expectedTitle, driver.getTitle());
    }
  }

  @Nested
  @DisplayName("Test Adventure Trips Page")
  class AdventureTripsTests {

    @ParameterizedTest(name = "Verify that the Adventure Trip for {1} loads correctly at {0}")
    @CsvFileSource(resources = "/__file/cw-destination-data.csv", numLinesToSkip = 1)
    @DisplayName("Verify that the Adventure Trips page loads correctly")
    void adventureTripsPage(String url, String expectedTitle) {
      driver.get(mainUrl + "/destination/" + url);
      assertEquals(expectedTitle, driver.getTitle());
    }
  }

  @Nested
  @DisplayName("Test Contact Page")
  class ContactPageTests {

    @Test
    @DisplayName("Verify Contact Page Title")
    void contactPageTitle() {
      driver.get(mainUrl + "/contact/");
      String expectedTitle = "Contact Us | Canada Wanderlust - Your Ultimate Travel Guide";
      assertEquals(expectedTitle, driver.getTitle());
    }
  }



  @AfterEach
  @DisplayName("Clean up and close the WebDriver after each test")
  public void tearDown() {
    // Clean up using utility method
    DriverUtil.quitDriver(driver);
  }
}
