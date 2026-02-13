# Air India ChatBot Automation

Selenium-based automation for testing Air India's chatbot functionalities.

## Prerequisites

- Java 21 or higher
- Maven (automatically set up in commands below)
- Chrome browser

## Quick Run Commands

### Run Menu-Driven Interface
```powershell
$env:MAVEN_HOME="$env:USERPROFILE\.m2\apache-maven-3.9.9"; $env:Path = "$env:MAVEN_HOME\bin;$env:Path"; cd "c:\Users\lenovo\Desktop\chat-bot\indigo-chatbot-automation"; mvn exec:java "-Dexec.mainClass=com.demo.DriverClass"
```

### Run Specific Tests

**Air India Booking Test:**
```powershell
$env:MAVEN_HOME="$env:USERPROFILE\.m2\apache-maven-3.9.9"; $env:Path = "$env:MAVEN_HOME\bin;$env:Path"; cd "c:\Users\lenovo\Desktop\chat-bot\indigo-chatbot-automation"; mvn exec:java "-Dexec.mainClass=com.demo.AirIndiaBooking"
```

**Flight Status Test:**
```powershell
$env:MAVEN_HOME="$env:USERPROFILE\.m2\apache-maven-3.9.9"; $env:Path = "$env:MAVEN_HOME\bin;$env:Path"; cd "c:\Users\lenovo\Desktop\chat-bot\indigo-chatbot-automation"; mvn exec:java "-Dexec.mainClass=com.demo.FlightStatus"
```

**Travel Guide Test:**
```powershell
$env:MAVEN_HOME="$env:USERPROFILE\.m2\apache-maven-3.9.9"; $env:Path = "$env:MAVEN_HOME\bin;$env:Path"; cd "c:\Users\lenovo\Desktop\chat-bot\indigo-chatbot-automation"; mvn exec:java "-Dexec.mainClass=com.demo.TravelGuide"
```

### Compile Only
```powershell
$env:MAVEN_HOME="$env:USERPROFILE\.m2\apache-maven-3.9.9"; $env:Path = "$env:MAVEN_HOME\bin;$env:Path"; cd "c:\Users\lenovo\Desktop\chat-bot\indigo-chatbot-automation"; mvn clean compile
```

## Project Structure

```
indigo-chatbot-automation/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── demo/
│                   ├── AirIndiaBooking.java    # Booking flow automation
│                   ├── FlightStatus.java       # Flight status check
│                   ├── TravelGuide.java        # Travel guide itinerary
│                   ├── ChatBotUtils.java       # Utility methods
│                   └── DriverClass.java        # Menu interface
├── pom.xml
└── README.md
```

## Test Scenarios

### 1. Air India Booking
- Opens Air India website
- Clicks chatbot icon
- Navigates: Booking → New Booking
- Enters flight details (Vijayawada to Delhi, one-way, 1 adult)
- Confirms booking
- Selects flight and economy class
- Proceeds with booking

### 2. Flight Status
- Opens Air India website
- Clicks chatbot icon
- Clicks Flight Status button
- Checks status for flight AI173, today

### 3. Travel Guide
- Opens Air India website
- Clicks chatbot icon
- Clicks Travel Guide button
- Enters destination (Delhi)
- Gets itinerary suggestion
- Selects Option 2
- Downloads itinerary

## Dependencies

- Selenium 4.32.0
- WebDriverManager 6.1.0
- MySQL Connector 8.3.0
- TestNG 7.7.0
- JUnit Jupiter 5.10.1

## Notes

- Chrome browser will open automatically and run the tests
- Wait times are configured to sync with chatbot responses (7 seconds between messages)
- All tests close the browser automatically after completion
