package com.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FlightStatus {
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

            // Step 2: Skip iframe check
            ChatBotUtils.switchToChatIframe(driver, wait);

            // Step 3: Click on "Flight Status" button
            Thread.sleep(3000);
            ChatBotUtils.clickFlightStatusButton(driver, wait);

            // Step 4: Wait for bot to be ready
            Thread.sleep(7000);

            // Step 5: Send flight number and date
            System.out.println("📝 Sending flight details...");
            ChatBotUtils.sendMessage(driver, wait, "AI173, today");

            System.out.println("✅ Flight Status check completed!");
            
            Thread.sleep(10000); // Wait to view the result

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
