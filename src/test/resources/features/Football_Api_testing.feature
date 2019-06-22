@version:Release-1
Feature: As a User
  Check football events are loaded and markets, competitors are working properly

  Only football events are placed in Football frames/page
  All markets have competitors home, away
  Market is available for Both Teams To Score

  Assumption: Postcode is for US and not for UK and validations covered based on US post code standards and not UK Postcode standards


  @Football @Events
  Scenario: "<TestCase>" NewTestCase
  As a customer check the live events offered
    Given local environment is up and running
    Then The status code is 200
    Then check the response contains only football events
    And verify "Home and Away" competitor is available for all events

