Feature: 5. Check Customer Apply Summary
  As a PO I want to check Personal loan eligible customer is able to get the Valid Quotation based on his loan amount, repayment period and apr rate etc..

  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent combinations.

    Given The User with Some Post Apply Summary Conditions as "<TestCase>" from the module "<XLSheetName>"
    When the user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    Then Verify the employmentStatus
    Then Verify the grossAnnualIncome
    Then Verify the residentialStatus
    Then Verify the ukResidentStatus

    Examples:
      |TestCase                                             |XLSheetName|
      |Customer with Employment status as Employed |ApplySummary        |
      |Customer with Employment status as UnEmployed|ApplySummary        |
      |Customer with Employment status as selfemployed |ApplySummary        |
      |Customer with Employment status as Director|ApplySummary        |
      |Customer with Employment status as Other |ApplySummary        |
      |Customer with Employment status as Student|ApplySummary        |
      |Customer with Employment status as Contractor|ApplySummary        |
      | Customer with Employment status as employed And Loan Amount LessThan 1000|ApplySummary        |
      |  Customer with Employment status as Director And residentialStatus As other|ApplySummary        |
      | Customer with Employment status as Other And residentialStatus As livingWithParents|ApplySummary        |
      | Customer with Employment status as Student And UK Owener|ApplySummary        |
      | Customer with Employment status as Student And UK Tenant|ApplySummary        |
     # | Customer with Employment status as employed And UK And Pre Auth Loan Amount Less than 30k and apply loan less than preauth limit|ApplySummary        |

 @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent combinations.

    Given The User with Some Post Apply Summary Conditions as "<TestCase>" from the module "<XLSheetName>"
    When the user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 400
    And The Response having the valid Description message1

    Examples:
      |TestCase                                             |XLSheetName|
      |Customer with Employment status as unemployed And Non UK|ApplySummary        |
      |Customer with Employment status as selfemployed And Non UK|ApplySummary        |
      | Customer with Employment status as Director And Non UK|ApplySummary        |
      | Customer with Employment status as Other And Non UK|ApplySummary        |
      | Customer with Employment status as Employed And Non UK|ApplySummary        |
      | Customer with Employment status as Student And Non UK|ApplySummary        |
      |Customer with Employment status as Contractor And Non UK|ApplySummary        |
      | Customer with Employment status as unemployed And Non UK And Pre Auth Loan Amount Less Than 1000|ApplySummary        |
      | Customer with Employment status as unemployed And Non UK And Pre auth Loan Amount More Than 3000000|ApplySummary        |
      | Customer with Employment status as Employed And Loan Amount in Negetive value|ApplySummary        |
 | Customer with Employment status as employed And UK And Pre auth Loan Amount More Than 3000000|ApplySummary        |
 #| Customer with Employment status as employed And UK And Pre Auth Loan Amount Less than 30k and apply loan more than preauth limit|ApplySummary        |

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check The valid error messages displayed for backend system failures.

    Given The User with Some Post Apply Summary Conditions as "<TestCase>" from the module "<XLSheetName>"
    When the user retrieves the Quotation with some amount and Repayment Shedule info
    And The Response having StatusCode as Expected
    And The Response having the valid Description message
    Examples:
      |TestCase                                                  |XLSheetName|
      |Check Apply summary EAPI for Get Quotation SAPI fail with 400 error|ApplySummary        |
      |Check Apply summary EAPI for Get Quotation SAPI fail with 401 error|ApplySummary        |
     | Check Apply summary EAPI for Get Quotation SAPI fail with 403 error|ApplySummary        |
      |Check Apply summary EAPI for Get Quotation SAPI fail with 404 error|ApplySummary        |
      |Check Apply summary EAPI for Get Quotation SAPI fail with 500 error|ApplySummary        |
