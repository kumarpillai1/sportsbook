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
      |TestCase                                                   |XLSheetName|
      |Check the Intrest rates for Eligible Customer|Eligibility|
      |TestCase                                     |XLSheetName|
      |Check the Intrest rates for Eligible Customer|Eligibility|


  Scenario: 1 - New Cars Search Page Navigation
    Given As a user I am on Home page
    When I click on New Car Search navigation bar
    Then I should be taken to New Car search page

  Scenario: 2 - Search options in New Car Search Page
    Given As a user I am on Home page
    When I click on New Car Search navigation bar
    Then I should be taken to New Car search page
    And Price a New Car search option should be displayed
    And Select Make dropdown should be displayed
    And ZIP Code text box should be displayed
    And Shop New Cars Button should be displayed

  Scenario: 3 - Only number are accepted in Postcode
    Given As a user I am on New Car search page
    When I enters value in "ZIP Code" text box
    Then only number are allowed max of 5 digits

  Scenario Outline: 4 - Enter invalid in Postcode
    Given As a user I am on New Car search page
    And I enters invalid value "<invalid zipCode>" in "ZIP Code" text box
    When I click on Shop New Cars button
    Then "Invalid ZIP Code" error message should be displayed
    Examples:
      | invalid zipCode |
      | 11111         |

  Scenario: 5 - Error message in empty postcode entered
    Given As a user I am on New Car search page
    And I select "Audi" in "Select Make" dropdown
    And I did not enter any value in "ZIP Code" text box
    When I click on Shop New Cars button
    Then "Invalid ZIP Code" error message should be displayed

  Scenario: 6 - Search result page displayed
    Given As a user I am on New Car search page
    When I select "Audi" in "Select Make" dropdown
    And I enter valid value "12345" in "ZIP Code" text box
    When I click Shop New Cars button
    Then New Cars search result page should be displayed with all Audi cars

#    Currently this is not been implemented in the page
  Scenario: 7 – Error message on not selecting any Make
    Given As a user I am on New Car search page
    When I have not selected any values in "Select Make" dropdown
    And I enter valid value "12345" in "ZIP Code" text box
    When I click Shop New Cars button
    Then Error message should be displayed as "Select a Make"
