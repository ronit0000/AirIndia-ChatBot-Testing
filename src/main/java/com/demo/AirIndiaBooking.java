package com.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AirIndiaBooking {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        try {
            // Step 1: Open Air India page and click chatbot
            ChatBotUtils.openChatBot(driver, wait);

            // Accept cookies if present
            try {
                WebElement acceptButton = driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler"));
                acceptButton.click();
                System.out.println("Accepted cookies");
            } catch (Exception e) {
                System.out.println("No cookie banner found");
            }

            // Step 2: Check for iframe (Air India doesn't use one)
            ChatBotUtils.switchToChatIframe(driver, wait);

            // Step 3: Click on "Booking" button
            ChatBotUtils.clickBookingButton(driver, wait);

            // Step 4: Click on "New Booking" button
            ChatBotUtils.clickNewBookingButton(driver, wait);

            // Step 5: Provide flight details one by one as the bot asks
            System.out.println("📝 Entering flight details...");
            
            // Wait for bot to ask for departure city
            Thread.sleep(7000);
            System.out.println("Step 5.1: Sending departure city...");
            ChatBotUtils.sendMessage(driver, wait, "Vijayawada");
            
            // Wait for bot to ask for destination
            Thread.sleep(7000);
            System.out.println("Step 5.2: Sending destination city...");
            ChatBotUtils.sendMessage(driver, wait, "Delhi");
            
            // Wait for bot to ask for travel date
            Thread.sleep(7000);
            System.out.println("Step 5.3: Sending travel date...");
            ChatBotUtils.sendMessage(driver, wait, "14th Feb 2026");
            
            // Wait for bot to ask for trip type
            Thread.sleep(7000);
            System.out.println("Step 5.4: Sending trip type...");
            ChatBotUtils.sendMessage(driver, wait, "one-way");
            
            // Wait for bot to ask for number of passengers
            Thread.sleep(7000);
            System.out.println("Step 5.5: Sending passenger count...");
            ChatBotUtils.sendMessage(driver, wait, "1 adult");
            
            // Wait for confirmation message
            Thread.sleep(8000);

            // Step 6: Confirm the booking details
            ChatBotUtils.sendMessage(driver, wait, "yes");
            Thread.sleep(5000);

            // Step 7: Select first flight
            ChatBotUtils.selectFirstFlight(driver, wait);

            // Step 8: Select Economy class
            ChatBotUtils.selectEconomyClass(driver, wait);

            // Step 9: Click Proceed With Booking
            ChatBotUtils.clickProceedWithBooking(driver, wait);

            System.out.println("✅ Booking flow completed successfully!");
            
            Thread.sleep(5000); // Wait to view the result

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
