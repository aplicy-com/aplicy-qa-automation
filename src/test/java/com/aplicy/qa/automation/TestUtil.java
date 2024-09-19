package com.aplicy.qa.automation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class TestUtil {
    public static void scrollPageToTheBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get the current page height and scroll incrementally
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        while (true) {
            // Scroll down by a small increment
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // Wait for the page to load more content (if necessary)
            try {
                Thread.sleep(2000); // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Get the new page height after scrolling
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");

            // Break the loop if the page height has stopped increasing (i.e., we've reached the bottom)
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }
    }
}
