Feature: 6. Check Decision
  As a PO I want to check Personal loan eligible customer is able to get the Valid Quotation based on his loan amount, repayment period and apr rate etc..


@MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent combinations.

     Given The User with QuotationID and Other Details for decision As "<TestCase>" from module "<XLSheetName>"
    When The user retrieves Decision
    Then The status code is 400
    And The Response having the valid message code11
    And The Response having the valid Description message with code
    And The Response having the valid Title
    Examples:
      |TestCase                                             |XLSheetName|
      |Check loan Decision for non UK Customer|Decision                 |
      |Customer loan Decision with Employment status as unemployed And Non UK|Decision |
     | Customer loan Decision with Employment status as selfemployed And Non UK|Decision |
     | Customer loan Decision with Employment status as Director And Non UK|Decision |
     |Customer loan Decision with Employment status as Other And Non UK|Decision |
     | Customer loan Decision with Employment status as Employed And Non UK|Decision |
     | Customer loan Decision with Employment status as Student And Non UK|Decision |
      |Customer loan Decision with Employment status as Contractor And Non UK|Decision |
      |Customer loan Decision with dayofmonth as 0|Decision |
     | Customer loan Decision with dayofmonth as -1|Decision |
      |Customer loan Decision with income as -1000|Decision |
     | Customer loan Decision for Invalid Sort code|Decision |
     | Customer loan Decision for Invalid Account Number|Decision |
      |Customer loan Decision for Invalid Employment Status|Decision |
      |Customer loan Decision for Invalid residentialStatus Status|Decision |
      |Customer loan Decision for Invalid Quotation ID Status     |Decision |

  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent combinations.
#Eligibility
    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    #Quotation
    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    #Affordability
    Given The User with Some Post Affordability Conditions as "<TestCase>" from the module "<XLSheetName>"
    When Customer Rertrives Affordability info
    Then The status code is 200
    #Get Eligible Accounts
    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the list of eligible accounts
    Then The status code is 200
    Then Get The Account number and sort code
    Given The User with QuotationID from MongoDB and Other Details for decision As "<TestCase>" from module "<XLSheetName>"
    When The user retrieves Decision
    Then Verify The status code is 400
    And The Response having the valid message code11
    And The Response having the valid Description message with code
    And The Response having the valid Title
    Examples:
      |TestCase                                             |XLSheetName|
      |Customer loan Decision with Employment status as UnEmployed|Decision                 |
      |Customer loan Decision with Employment status as Student|Decision                 |
      |Customer loan Decision with Employment status as SelfEmployed|Decision                 |
      |Customer loan Decision with Employment status as Director|Decision                 |
      |Customer loan Decision with Employment status as other|Decision                 |



#
#    And The Response having the loanDecision
#    And The Response having the personalLoanProductId
#    And The Response having the signedPDF and UnSignedPDF and HTML

  Scenario Outline: "<DecisionTestCasePAPI>"
  As a Customer I want to check my loan Quotation with diferent combinations.

  Given The User with QuotationID and Other Details for decision As1 "<DecisionTestCasePAPI>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    And The Response having the loanDecision as referred
    And The Response having the referralCode
    And The Response having the loanDecisionReason

    Examples:
      |DecisionTestCasePAPI                                             |
    #|Customer with quote id and loan status UnEmployedPAPI|
     |Customer loan Decision with beheviral Score 340 and risk band 2 and DCM Score 360 P3 referal PAPI|
      #|Customer loan Decision with beheviral Score 340 and risk band 2 and DCM Score 360 P3 referal PAPI1|
      #|Customer loan Decision with beheviral Score 340 and risk band 2 and DCM Score 360 P3 referal PAPI2|


  Scenario Outline: "<TestCase>"
  As a Customer I want to check my loan Quotation with diferent combinations.
    #Eligibility
    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    #Quotation
    Given The User with Some Post Quotation Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the Quotation with some amount and Repayment Shedule info
    Then The status code is 200
    #Affordability
    Given The User with Some Post Affordability Conditions as "<TestCase>" from the module "<XLSheetName>"
    When Customer Rertrives Affordability info
    Then The status code is 200
    #Get Eligible Accounts
    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the list of eligible accounts
    Then The status code is 200
   # Then Get the account number an dsort code
    Given The User with QuotationID and Other Details for decision As "<DecisionTestCase>"
    When The user retrieves Decison
    Then The status code is 400
    And The Response having the loanDecision as referred
    Then The Response having the referralCode

    Examples:
      |DecisionTestCase                                             |XLSheetName|
      #|Customer loan Decision with Employment status as UnEmployed |
      |Customer loan Decision with beheviral Score 340 and risk band 2 and DCM Score 360 P3 referal|Decision|



