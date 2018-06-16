Feature: 4. Check Customer Eligible accounts
  As a PO I want to check list of eligible accounts for the customers to pay the repayments.

  @MCT3 @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility Without FIRST Account.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the list of eligible accounts
    Then The status code is 200
    Then Verify the Number of First Accounts Displayed
    Then Verify The displayed Account Numbers

    Examples:
      |TestCase                                                   |XLSheetName|
      |Check Customer with one First Acc and multiple CC And Saving accounts      |GetEligibleAccounts|
#      |Check Customer with One First acc and multiple CC And Saving accounts and CC Joint accounts|GetEligibleAccounts|
#      |Check Customer with One First acc and 2 saving accounts|GetEligibleAccounts|
#      | Check Customer with One First acc and 1 saving account|GetEligibleAccounts|
#      | Check Customer with 3 First accounts and 1 saving account|GetEligibleAccounts|
#      | Check Customer with only one First Account|GetEligibleAccounts|
#      |Check Customer with only one First Account1|GetEligibleAccounts|
#



 @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility Without FIRST Account.

   Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the list of eligible accounts
    Then The status code is 400
    Then The Response should display Valid Error1

    Examples:
      |TestCase                   |XLSheetName|
      |Check Customer with No Accounts            |GetEligibleAccounts|
     |Check Customer with Only Saving Accounts   |GetEligibleAccounts|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check The valid error messages displayed for backend system failures.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the list of eligible accounts
    And The Response having StatusCode as Expected
    And The Response having the valid Description message with code
    Examples:
      |TestCase                                                                   |XLSheetName|
      |Account list Error code as GENERAL CONSTRAINT VIOLATION with 400 status and Eligibility SAPI 200|GetEligibleAccounts|
      |Account list RDP400 and Eligibility SAPI 200|GetEligibleAccounts|
      |Account list Error code as INVALID_CODE with 400 status and Eligibility SAPI 200|GetEligibleAccounts|
      |Account list RDP401 and Eligibility SAPI 200|GetEligibleAccounts|
      |Account list 401 with reason code 00003|GetEligibleAccounts|
      |Account list 401 with reason code 00009|GetEligibleAccounts|
      |Account list 401 with reason code 00005|GetEligibleAccounts|
      |Account list 401 with reason code 00006|GetEligibleAccounts|
      |Account list RDP403 and Eligibility SAPI 200|GetEligibleAccounts|
      |Account list RDP404 and Eligibility SAPI 200|GetEligibleAccounts|
      |Account list RDP500 and Eligibility SAPI 200|GetEligibleAccounts|
      |Account list RDP406 and Eligibility SAPI 200|GetEligibleAccounts|
      |Account list RDP504 and Eligibility SAPI 200|GetEligibleAccounts|