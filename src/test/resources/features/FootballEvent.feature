@version:Release-1
Feature: As a User
  Check football events are loaded and markets, competitors are working properly

  Only football events are placed in Football frames/page
  All markets have competitors home, away
  Market is available for Both Teams To Score
  Assumption: Postcode is for US and not for UK and validations covered based on US post code standards and not UK Postcode standards


  @Football @SelectMarket
  Scenario: 1 - select market into betplace frame
    Given environment is up and running
    When As a user I am on Home page
    Then I log into the application
    Then select the event
    Then user is able to select market "Both Teams To Score"
    Then check market is selected and available to place bet


  @Football @Events @loadHomepage
  Scenario: 2 - Load Home page
    Given As a user I am on Home page
    Then I log into the application
    Then I can see markets and events are loaded

  @Football @Events @placebet
  Scenario: 3 - place a bet
    Given As a user I am on Home page
    Then I log into the application
    Then user is able to select market "Both Teams To Score"
    Then check market is selected
    Then place a bet

