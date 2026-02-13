package com.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookFlight {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        try {
            // Step 1: Open IndiGo chat page and click chatbot
            ChatBotUtils.openChatBot(driver, wait);

            // Step 2: Switch to the iframe
            ChatBotUtils.switchToChatIframe(driver, wait);

            // Step 3: Wait for input box inside iframe
            WebElement inputBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[placeholder='Ask me anything...']")));

            // Step 4: Type and send message
            inputBox.sendKeys("book flight");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message 'trip planning' sent successfully!");

            Thread.sleep(7000); // optional: wait to view response

            inputBox.sendKeys("mumbai to delhi");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message 'mumbai to delhi' sent successfully!");

            Thread.sleep(5000); // wait before sending ENTER

            inputBox.sendKeys("one-way trip");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message 'one-way trip' sent successfully!");

            Thread.sleep(5000); // wait before sending ENTER

            inputBox.sendKeys("tomorrow");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message 'tomorrow' sent successfully!");

            Thread.sleep(5000); // wait before sending ENTER

            inputBox.sendKeys("1 adult");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message '1 adult' sent successfully!");

            // yes to confirm the details

            Thread.sleep(5000); // wait before sending ENTER
            inputBox.sendKeys("yes");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message 'yes' sent successfully!");

            // Step 7: Wait for flight options to load and select the first flight

            WebElement firstFlight = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//span[contains(@class,'flight-list-plan-fare')])[1]")
            ));
            firstFlight.click();

            System.out.println("✅ First flight option selected successfully!");
            Thread.sleep(5000); // wait to view the selection

            // select continue

            WebDriverWait waitNew = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement continueBtn = waitNew.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Continue']")
            ));
            continueBtn.click();
            System.out.println("✅ Continue button clicked successfully!");

            // Provide Full name of Passenger

            Thread.sleep(5000); // wait before sending ENTER
            inputBox.sendKeys("Pratham Kumar MALE");
            inputBox.sendKeys(Keys.ENTER);

            // Confirm no more Passenger

            Thread.sleep(5000); // wait before sending ENTER
            inputBox.sendKeys("no more passengers");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message 'no more passengers' sent successfully!");

            // add phone number

            Thread.sleep(5000); // wait before sending ENTER
            inputBox.sendKeys("+91 9876543210");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message '+91 9876543210' sent successfully!");

            // add email id

            Thread.sleep(5000); // wait before sending ENTER
            inputBox.sendKeys("prk61dlw@gmail.com");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message 'prk61dlw@gmail.com' sent successfully!");

            // confirm details
            Thread.sleep(5000); // wait before sending ENTER
            inputBox.sendKeys("yes");
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message 'yes' sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            driver.quit();
        }
    }
}
