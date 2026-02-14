package com.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class PlanTrip {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        try {
            // Step 1: Open Air India chat page and click chatbot
            ChatBotUtils.openChatBot(driver, wait);

            // Step 2: Switch into chatbot iframe
            ChatBotUtils.switchToChatIframe(driver, wait);

            // Step 3: Send first message "trip planning"
            ChatBotUtils.sendMessage(driver, wait, "trip planning");

            // Step 4: Wait for second-to-last chatbot reply
            String botReply1 = ChatBotUtils.waitForSecondLastBotReply(driver);
            System.out.println("💬 Chatbot replied (second-to-last): " + botReply1);

            // Step 5: Call rating function with the reply
            giveRandomRating(botReply1);

            // Step 6: Send next message "delhi"
            ChatBotUtils.sendMessage(driver, wait, "delhi");

            // Step 7: Wait for second-to-last chatbot reply
            String botReply2 = ChatBotUtils.waitForSecondLastBotReply(driver);
            System.out.println("💬 Chatbot replied (second-to-last): " + botReply2);

            // Step 8: Call rating function again
            giveRandomRating(botReply2);

            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // Rating function: random star rating and feedback
    private static void giveRandomRating(String botReply) {
        Random random = new Random();

        int starRating = random.nextInt(5) + 1; // Random 1-5 stars

        String[] feedbackOptions = {
                "Very helpful and detailed response.",
                "Good guidance, answered most queries.",
                "Average response, could be more detailed.",
                "Not very helpful, missing some info.",
                "Excellent! Very clear and precise."
        };

        System.out.println("💬 Chatbot reply being rated: " + botReply);

        String feedbackMessage = feedbackOptions[random.nextInt(feedbackOptions.length)];

        System.out.println("⭐ Random rating for chatbot reply: " + starRating + " stars");
        System.out.println("📝 Feedback message: " + feedbackMessage);
    }
}
