Feature: 2.Check Customer Loan Quotation
  As a PO I want to check Personal loan eligible customer is able to get the Valid Quotation based on his loan amount, repayment period and apr rate etc..


   @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent combinations.

    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    Then The Response should display purpose of loan
    And The Response should display loanAmount
    And The Response should display repaymentPeriod
    And The Response should display interestAPR
    And The Response should display interestAnnual
    And The Response should display initialPayment
    And The Response should display regularPayment
    And The Response should display totalRepayableAmount
    And Verify The ID Created In MongoDB for instance

    Examples:
      |TestCase                                             |XLSheetName|
      |Check Customer Quotation value for the loan Amt lessthan 4950|Quotation   |
      |Check Customer Quotation value for the loan Amt lessthan 5000|Quotation   |
      |Check Customer Quotation value for the loan Amt lessthan 10000|Quotation   |
      |Check Customer Quotation value for the loan Amt lessthan 30000|Quotation   |
      |Check Customer Quotation value for the loan Amt lessthan 31000|Quotation   |
      |Check Customer Quotation value for the loan Amt lessthan or equal 50000|Quotation   |



   @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent nagetive combinations.

    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    Then The Response should Contains valid message

    Examples:
      |TestCase                                             |XLSheetName|
      | Check Customer Quotation value for the loan Amt lessthan 1000 |Quotation   |
      |Check Customer Quotation value for the loan Amt greaterthan 50000|Quotation   |
      |Check Customer Quotation value for Repayment Preriod 10|Quotation   |
      |Check Customer Quotation value for Repayment Preriod 85|Quotation   |
      |Check Customer Quotation value for Repayment Preriod -15|Quotation   |
      |Check Customer Quotation value for purpose dummyTest |Quotation   |

 @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent passitive combinations.

    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    Then The Response should display purpose of loan
    Examples:
      |TestCase                                         |XLSheetName|
      |Check Customer Quotation value for purpose HomeImprovement|Quotation   |
      |Check Customer Quotation value for purpose wedding|Quotation   |
      |Check Customer Quotation value for purpose holiday|Quotation   |
      |Check Customer Quotation value for purpose debtRepayment|Quotation   |
      |Check Customer Quotation value for purpose somethingElse|Quotation   |

   @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation even though am not eliible for loan.

    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 403
    Then The Response should display Valid Error1
    Examples:
      |TestCase                                         |XLSheetName|
      |Check Customer Qutation for in-Eligible Customer|Quotation   |

  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation APR values with different loan amounts.

    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    Then The Response should display purpose of loan
    And The Response should display loanAmount
    And The Response should display repaymentPeriod
    And The Response should display interestAPR
    And Verify The ID Created In MongoDB for instance

    Examples:
      |TestCase                                      |XLSheetName|
      |Check Customer Quotation value for the loan Amt is 1000|Quotation   |
      |Check Customer Quotation value for the loan Amt is 1050|Quotation   |
      |Check Customer Quotation value for the loan Amt is 2450|Quotation   |
      |Check Customer Quotation value for the loan Amt is 2500|Quotation   |
      |Check Customer Quotation value for the loan Amt is 4950|Quotation   |
      |Check Customer Quotation value for the loan Amt is 5000|Quotation   |
      |Check Customer Quotation value for the loan Amt is 5050|Quotation   |
      |Check Customer Quotation value for the loan Amt is 6950|Quotation   |
      |Check Customer Quotation value for the loan Amt is 7000|Quotation   |
      |Check Customer Quotation value for the loan Amt is 30000|Quotation   |

@MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent combinations.

    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    Then The Response should display purpose of loan
    And The Response should display loanAmount
    And The Response should display repaymentPeriod
    And The Response should display interestAPR
    And The Response should display interestAnnual
    And The Response should display initialPayment
    And The Response should display regularPayment
    And The Response should display totalRepayableAmount
    And Verify The ID Created In MongoDB for instance1

    Examples:
      |TestCase                                             |XLSheetName|
#      |Check Customer Quotation value for the loan Amt lessthan 2100 |Quotation   |
      |Check Customer Quotation value for the loan Amt lessthan 4950|Quotation   |

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check The valid error messages displayed for backend system failures.

    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    And The Response having StatusCode as Expected
    And The Response having the valid Description message with code
    Examples:
      |TestCase                                                  |XLSheetName|
      |Check Customer with Quotation SAPI 400 error|Quotation   |
      |Check Customer with Quotation SAPI 404 error|Quotation   |
      |Check Customer with Quotation SAPI 500 error|Quotation   |
      |Check Customer with Quotation SAPI 504 error|Quotation   |
      |Check Customer with Mongo DB Quotation SAPI 400 error|Quotation   |
      |Check Customer with Mongo DB Quotation SAPI 403 error|Quotation   |
      |Check Customer with Mongo DB Quotation SAPI 500 error|Quotation   |
      |Check Customer with Mongo DB Quotation SAPI 404 error|Quotation   |
      |Check Customer with Mongo DB Quotation SAPI 401 error|Quotation   |
     |Account list Error code as GENERAL CONSTRAINT VIOLATION with 400 status and Eligibility SAPI 200|Quotation |
      |Account list RDP400 and Eligibility SAPI 200|Quotation   |
      |Account list Error code as INVALID_CODE with 400 status and Eligibility SAPI 200|Quotation   |
      |Account list RDP401 and Eligibility SAPI 200|Quotation   |
      |Account list 401 with reason code 00003|Quotation   |
      |Account list 401 with reason code 00009|Quotation   |
      |Account list 401 with reason code 00005|Quotation   |
      |Account list 401 with reason code 00006|Quotation   |
      |Account list RDP403 and Eligibility SAPI 200|Quotation   |
      |Account list RDP404 and Eligibility SAPI 200|Quotation   |
      |Account list RDP500 and Eligibility SAPI 200|Quotation   |
      |Account list RDP406 and Eligibility SAPI 200|Quotation   |
      |Account list RDP504 and Eligibility SAPI 200|Quotation   |
     |Account list 200 and Eligibility SAPI 400|Quotation   |
      |Account list 200 and Eligibility SAPI 401|Quotation   |
      |Account list 200 and Eligibility SAPI 403|Quotation   |
      |Account list 200 and Eligibility SAPI 404|Quotation   |
      |Account list 200 and Eligibility SAPI 500|Quotation   |
      |Account list with 200 and eligibility with 500 server error|Quotation   |
      |Account list 200 and Eligibility SAPI 400 with 98007 code|Quotation   |
      |Account list 200 and Eligibility SAPI 504|Quotation   |

