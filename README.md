# Sportsbook UI Automation Test


To Run the test, please download the maven dependencies. To run via maven, mvn clean install , or mvn clean test.


Technologies used:
Java 1.8, IDE : IntelliJ, maven, Cucumber( for BDD scenarios), Testng, log4j
(Setup the maven java environment using Intellij) 

Open the project folder containing pom.xml
If the setup is good, java sdk, maven would be enabled. The project will show src folder
Once maven clean command is executed, dependencies will be downloaded. And the red lines( errored lines) will be gone
 
Assumptions: 

Assertions parameters have been hardcoded, but we can change it to spreadsheet or a properties/yaml file inside the framework. According to the project / framework.
Environment/site under test is hardcoded, would also moved to properties file

Test Coverage to include:
Positive Test Cases, ( Happy path), negative test, invalid test scenarios, 

Examples:


Check football event is available for betting
Check user is able to login. 
Check user is able to add credit
Check user is able to click and select market and bet
Usability for entire process is seamless, rendering, 
Check all markets are available at the start of the event or before the event
Number of markets and its value change during game progression. Runtime dynamic updating of stakes
Check voided bets does not appear
Market rate varies during game progression and response within given time frame

Check all the available fields are in correct format and relevant. 
For example 
All parameters / keys are received from an event, its status, markets, other fields 


Response format json
Task Two
I will covering below test scenarios as well
1) when Team A wins
2) Team B  wins
3) Draw
4) No result
5) Match cancelled 
6) Dynamic market variation


# Api Validation:
Endpoint not available 404
Bad request 400
Server related errors 500

# Negative scenario Testing
With the test data given, I am  able to think of few negative scenarios for now.
Match start should not be in past 
Match status should not be other than active, eventID is always numeric, className is Football, 
Goals should not negative, decimal, string.
Corresponding markets should be unavailable during rest of the game
Event, markets  does not appear after game is completed or during similar events
Markets should be unavailable during events like ( goal is scored) ( either all markets or corresponding markets)
Bets are not possible for past events


# Non functional requirement
Check website is loaded in n seconds.
Changing the stakes using api. 
Secure logging using api
Delay in response or market load
