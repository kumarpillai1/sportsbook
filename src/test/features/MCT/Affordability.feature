Feature: Affordability
  As a PO I want to check Customer CRedit Card Eligibility Status for different types of customers.


  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check The valid error messages displayed for backend system failures.

    Given The User with Some Post Affordability Conditions as "<TestCase>" from the module "<XLSheetName>"
    When Customer Rertrives Affordability info
    Then The status code is 400
    And The Response having the valid message code11
    And The Response having the valid Description message with code
    And The Response having the valid Title
    Examples:
      |TestCase                                                  |XLSheetName|
      |Check Customer Affordability with canAffordRepayments as NO|Affordability|
      |Check Customer Affordability with sustainabilityChangeInCircumstance  as YES|Affordability|

  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check The valid error messages displayed for backend system failures.

    Given The User with Some Post Affordability Conditions as "<TestCase>" from the module "<XLSheetName>"
    When Customer Rertrives Affordability info
    Then The status code is 200
    And The Response having the valid ICM code
    And The Response having the valid Quotation
    And The Response having the valid eventConsolidationId
    Examples:
      |TestCase                                                  |XLSheetName|
      |Check Customer Affordability with sustainabilityChangeInCircumstance  as No and canAffordRepayments as YES|Affordability|


  @SCT2
  Scenario Outline: "<TestCase>"
  As a Customer I want to check The valid error messages displayed for backend system failures.

    Given The User with Some Post Affordability Conditions as "<TestCase>" from the module "<XLSheetName>"
    When Customer Rertrives Affordability info
    And The Response having StatusCode as Expected
    And The Response having the valid Description message
    Examples:
      |TestCase                                  |XLSheetName|
      |Check Decision EAPI with Events  SAPI 400|Affordability|
      |Check Decision EAPI with Events  SAPI 500|Affordability|


