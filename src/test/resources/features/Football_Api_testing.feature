@version:Release-1
Feature: As a User
  Check football events are loaded and markets, competitors are working properly

  Only football events are placed in Football frames/page
  All markets have competitors home, away
  Market is available for Both Teams To Score

  Assumption: Postcode is for US and not for UK and validations covered based on US post code standards and not UK Postcode standards


  @Football @Events
  Scenario Outline: "<TestCase>" NewTestCase
  As a customer check the live events offered
    Given local environment is up and running
    Then check the response contains only football events
    Examples:
      | TestCase                                      | XLSheetName |
      | Check the Intrest rates for Eligible Customer | Eligibility |


  Scenario: 1 - New Cars Search Page Navigation
    Given As a user I log into Home page
    When I click on FootBall events
    Then I select "FootBall Game"

  Scenario: 2 - Search options in New Car Search Page
    Given As a user I am on Home page
    Then I log into the application
    Then user is able to select market "Both Teams To Score"
    Then check market is selected and available to place bet

  Scenario: 2 - Search options in New Car Search Page
    Given As a user I am on Home page
    Then I log into the application
    Then user is able to select market "Both Teams To Score"
    Then check market is selected
    Then place a bet