package com.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class TravelGuide {
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) {
        try {
            setUp();
            runTravelGuideFlow();
            System.out.println("\nTravel Guide test completed successfully!");
        } catch (Exception e) {
            System.err.println("Error during Travel Guide test: " + e.getMessage());
            e.printStackTrace();
        } finally {
            tearDown();
        }
    }

    private static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    private static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static void runTravelGuideFlow() {
        try {
            // Open Air India website
            driver.get("https://www.airindia.com/");
            System.out.println("Opened Air India website");
            Thread.sleep(3000);

            // Accept cookies if present
            try {
                WebElement acceptButton = driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler"));
                acceptButton.click();
                System.out.println("Accepted cookies");
            } catch (Exception e) {
                System.out.println("No cookie banner found");
            }

            // Click chatbot icon
            WebElement chatbotIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img#ask-aig")));
            chatbotIcon.click();
            System.out.println("Clicked chatbot icon");
            Thread.sleep(3000);

            // Click Travel Guide button
            WebElement travelGuideButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'response-button')][.//img[contains(@class,'travel-guide-icon')]]")
            ));
            travelGuideButton.click();
            System.out.println("Clicked Travel Guide button");
            Thread.sleep(10000);

            // Enter destination: Delhi
            WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("textarea#inputChat")));
            inputField.clear();
            inputField.sendKeys("Delhi");
            System.out.println("Entered destination: Delhi");
            Thread.sleep(2000);

            // Click Send button (try direct img element first)
            try {
                WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img.send-icon")));
                sendButton.click();
                System.out.println("Clicked Send button");
            } catch (Exception e) {
                // Fallback: Press Enter
                inputField.sendKeys(org.openqa.selenium.Keys.ENTER);
                System.out.println("Pressed Enter to send");
            }
            Thread.sleep(10000);

            // Click "Get your itinerary" button
            WebElement itineraryButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'nudge-action-button') and contains(.,'Get your itinerary')]")
            ));
            itineraryButton.click();
            System.out.println("Clicked 'Get your itinerary' button");
            Thread.sleep(10000);

            // Click Option 2
            WebElement option2Button = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'response-button') and normalize-space()='2']")
            ));
            option2Button.click();
            System.out.println("Clicked Option 2");
            Thread.sleep(10000);

            // Click Download Itinerary
            WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'download-text') and contains(.,'Download Itinerary')]")
            ));
            downloadButton.click();
            System.out.println("Clicked 'Download Itinerary'");
            Thread.sleep(30000);

        } catch (Exception e) {
            throw new RuntimeException("Error in Travel Guide flow", e);
        }
    }
}
