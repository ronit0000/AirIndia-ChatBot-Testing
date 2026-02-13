package com.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class FlightsFromCity {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        try {
            ChatBotUtils.openChatBot(driver, wait);
            ChatBotUtils.switchToChatIframe(driver, wait);

            WebElement flightsFromCityOption = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class,'quick-btn') and .//span[contains(text(),'Flights from city')]]")
                    )
            );
            flightsFromCityOption.click();

            String botReply1 = ChatBotUtils.waitForBotReply(driver, wait);
            System.out.println("💬 Chatbot replied: " + botReply1);
            Thread.sleep(5000);

            ChatBotUtils.sendMessage(driver, wait, "vijayawada to visakapatnam on 10/10/25");

            String botReply2 = ChatBotUtils.waitForBotReply(driver, wait);
            System.out.println("💬 Chatbot replied: " + botReply2);
            Thread.sleep(5000);

            List<WebElement> flightCards = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded")
                    )
            );

            if (flightCards.isEmpty()) {
                System.out.println("⚠ No flight options found.");
            } else {
                WebElement selectedFlight = flightCards.get(0);
                WebElement bookBtn = selectedFlight.findElement(By.xpath(".//button[contains(text(),'Book Flight')]"));
                wait.until(ExpectedConditions.elementToBeClickable(bookBtn));
                bookBtn.click();
                System.out.println("✅ Clicked 'Book Flight' button for selected flight option.");
            }

            ChatBotUtils.sendMessage(driver, wait, "1 adult passenger");
            String botReply3 = ChatBotUtils.waitForBotReply(driver, wait);
            System.out.println("💬 Chatbot replied: " + botReply3);
            Thread.sleep(5000);

            ChatBotUtils.sendMessage(driver, wait, "yes");
            String botReply4 = ChatBotUtils.waitForBotReply(driver, wait);
            System.out.println("💬 Chatbot replied: " + botReply4);
            Thread.sleep(5000);

            Thread.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
