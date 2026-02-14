package com.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ChatBotUtils {

    /**
     * Sends a message to the chatbot
     */
    public static void sendMessage(WebDriver driver, WebDriverWait wait, String message) {
        try {
            WebElement inputBox = null;
            
            // Try Air India selector first
            try {
                inputBox = wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("inputChat")));
                System.out.println("✅ Found Air India input field");
            } catch (Exception e1) {
                // Try by class
                try {
                    inputBox = wait.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector("textarea.input-chat")));
                    System.out.println("✅ Found input by class");
                } catch (Exception e2) {
                    // Fallback to placeholder
                    inputBox = wait.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector("input[placeholder='Ask me anything...']")));
                    System.out.println("✅ Found input by placeholder");
                }
            }
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", inputBox);
            inputBox.click();
            inputBox.clear();
            inputBox.sendKeys(message);
            inputBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Message '" + message + "' sent successfully!");
        } catch (TimeoutException e) {
            System.out.println("⚠ Input box not found to send message: " + message);
        }
    }

    /**
     * Waits for and extracts the bot's reply
     */
    public static String waitForBotReply(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        Thread.sleep(3000);
        List<WebElement> spans = driver.findElements(By.tagName("span"));
        String botReply = null;
        for (WebElement span : spans) {
            String text = span.getText().trim();
            if (!text.isEmpty() && !text.equals("Ask me anything...")) {
                botReply = text;
            }
        }
        return botReply != null ? botReply : "⚠ Could not detect chatbot reply!";
    }

    /**
     * Waits for and extracts the second-to-last bot reply
     */
    public static String waitForSecondLastBotReply(WebDriver driver) {
        try {
            Thread.sleep(40000);

            List<WebElement> spans = driver.findElements(By.tagName("span"));
            List<String> validReplies = new ArrayList<>();

            for (WebElement span : spans) {
                if (span.isDisplayed()) {
                    String text = span.getText().trim();
                    if (!text.isEmpty() && !text.equals("Ask me anything...")) {
                        validReplies.add(text);
                    }
                }
            }

            if (validReplies.size() >= 2) {
                return validReplies.get(validReplies.size() - 2);
            } else if (validReplies.size() == 1) {
                return validReplies.getFirst();
            } else {
                return "⚠ Could not detect chatbot reply!";
            }
        } catch (InterruptedException e) {
            return "⚠ Interrupted while waiting for bot reply!";
        }
    }

    /**
     * Opens the Air India chat page and clicks the chatbot
     */
    public static void openChatBot(WebDriver driver, WebDriverWait wait) {
        try {
            driver.get("https://www.airindia.com/");
            
            // Wait for page to fully load
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
            Thread.sleep(5000); // Wait for any dynamic content to load
            
            System.out.println("🌐 Page loaded successfully!");
            
            // Try multiple selectors for the Air India chatbot
            WebElement chatImage = null;
            
            try {
                // Try by ID - primary method for Air India
                chatImage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.id("ask-aig")));
                System.out.println("✅ Found chatbot by ID 'ask-aig'");
            } catch (Exception e1) {
                System.out.println("⚠ ID selector failed, trying class name...");
                try {
                    // Try by class name
                    chatImage = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("img.floating-chat-bot-icon")));
                    System.out.println("✅ Found chatbot by class 'floating-chat-bot-icon'");
                } catch (Exception e2) {
                    System.out.println("⚠ Class selector failed, trying alt attribute...");
                    try {
                        // Try by alt attribute
                        chatImage = wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.cssSelector("img[alt='Air India Chat bot icon']")));
                        System.out.println("✅ Found chatbot by alt attribute");
                    } catch (Exception e3) {
                        System.out.println("⚠ Alt attribute failed, trying src pattern...");
                        try {
                            // Try by src containing pattern
                            chatImage = wait.until(ExpectedConditions.presenceOfElementLocated(
                                    By.cssSelector("img[src*='cbot-scripts.airindia.com']")));
                            System.out.println("✅ Found chatbot by src pattern");
                        } catch (Exception e4) {
                            // Try XPath
                            chatImage = wait.until(ExpectedConditions.presenceOfElementLocated(
                                    By.xpath("//img[@id='ask-aig' or contains(@class,'floating-chat-bot-icon')]")));
                            System.out.println("✅ Found chatbot by XPath");
                        }
                    }
                }
            }
            
            // Ensure element is visible
            wait.until(ExpectedConditions.visibilityOf(chatImage));
            
            // Scroll to element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", chatImage);
            Thread.sleep(1000);
            
            // Try to click
            try {
                // Wait for clickability
                wait.until(ExpectedConditions.elementToBeClickable(chatImage));
                chatImage.click();
                System.out.println("✅ Chatbot clicked successfully (normal click)!");
            } catch (Exception e) {
                // If regular click fails, use JavaScript click
                System.out.println("⚠ Normal click failed, trying JavaScript click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", chatImage);
                System.out.println("✅ Chatbot clicked successfully (JS click)!");
            }
            
            Thread.sleep(3000); // Wait for chatbot to load
            
        } catch (Exception e) {
            System.out.println("❌ Error clicking chatbot: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to open chatbot", e);
        }
    }

    /**
     * Switches to the chatbot iframe (if exists)
     */
    public static void switchToChatIframe(WebDriver driver, WebDriverWait wait) {
        // Skipping iframe detection for faster execution
        System.out.println("ℹ️  Skipping iframe detection - chatbot is in main page.");
    }
    
    /**
     * Clicks Air India "Booking" button
     */
    public static void clickBookingButton(WebDriver driver, WebDriverWait wait) {
        try {
            Thread.sleep(2000); // Wait for chatbot to fully load
            
            // Find and click the Booking button
            WebElement bookingButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'response-button') and .//img[contains(@class,'booking-icon')]]")));
            bookingButton.click();
            System.out.println("✅ Clicked 'Booking' button");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("⚠️ Could not click Booking button: " + e.getMessage());
        }
    }
    
    /**
     * Clicks Air India "Flight Status" button
     */
    public static void clickFlightStatusButton(WebDriver driver, WebDriverWait wait) {
        try {
            Thread.sleep(2000); // Wait for chatbot to fully load
            
            // Find and click the Flight Status button
            WebElement flightStatusButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'response-button') and .//img[contains(@class,'flight-status-icon')]]")));
            flightStatusButton.click();
            System.out.println("✅ Clicked 'Flight Status' button");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("⚠️ Could not click Flight Status button: " + e.getMessage());
        }
    }
    
    /**
     * Clicks Air India "New Booking" button
     */
    public static void clickNewBookingButton(WebDriver driver, WebDriverWait wait) {
        try {
            // Find and click the New Booking button
            WebElement newBookingButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class,'response-button') and contains(text(),'New Booking')]")));
            newBookingButton.click();
            System.out.println("✅ Clicked 'New Booking' button");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("⚠️ Could not click New Booking button: " + e.getMessage());
        }
    }
    
    /**
     * Selects the first available flight card
     */
    public static void selectFirstFlight(WebDriver driver, WebDriverWait wait) {
        try {
            Thread.sleep(3000); // Wait for flight results to load
            
            WebElement flightCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("div.chatbot-info-flight-container.flight-search-response-card")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", flightCard);
            Thread.sleep(1000);
            flightCard.click();
            System.out.println("✅ Selected first flight");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("⚠️ Could not select flight: " + e.getMessage());
        }
    }
    
    /**
     * Clicks Economy class option
     */
    public static void selectEconomyClass(WebDriver driver, WebDriverWait wait) {
        try {
            WebElement economyButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'class-name') and contains(text(),'economy')]")));
            economyButton.click();
            System.out.println("✅ Selected Economy class");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("⚠️ Could not select Economy: " + e.getMessage());
        }
    }
    
    /**
     * Clicks Proceed With Booking button
     */
    public static void clickProceedWithBooking(WebDriver driver, WebDriverWait wait) {
        try {
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.booking-button")));
            proceedButton.click();
            System.out.println("✅ Clicked 'Proceed With Booking' button");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("⚠️ Could not click Proceed button: " + e.getMessage());
        }
    }
}
