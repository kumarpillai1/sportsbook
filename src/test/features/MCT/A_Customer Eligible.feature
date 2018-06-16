Feature: 1.Check Customer Loan Eligibility
  As a PO I want to check Customer Personal Loan Eligibility Status for different types of customers.

  @MCT  @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid
#Pre-Condition Get The Pre-Auth Limit from SAPI
    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When the User Get the Pre Auth Limit
    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
   #And The Response having Link to Quotation
    And The Response Having the Purpose of Loan Options
    And Verify The Minium Loan Amount
    And Verify The Maximum Loan Amount
    And Verify The Minimum Repayment Period
    And Verify The maximum Repayment Period
    And Verify The eligibleLoanAmount
    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check the Eligibility|Eligibility                                                      |

   @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Intrest rate for the range 1000 to 2450
    And The Intrest rate for the range 2500 to 4950
    And The Intrest rate for the range 5000 to 6950
    And The Intrest rate for the range 7000 to 15000
    And The Intrest rate for the range 15050 to 30000
    And The Intrest rate for the range 30050 to 50000
    Examples:
      |TestCase                                     |XLSheetName|
      |Check the Intrest rates for Eligible Customer|Eligibility|

   @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid

#Pre-Condition Get The Pre-Auth Limit from SAPI
     Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
     When the User Get the Pre Auth Limit

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Response Having the Purpose of Loan Options
    And Verify The Minium Loan Amount
    And Verify The Maximum Loan Amount
    And Verify The Minimum Repayment Period
    And Verify The maximum Repayment Period
    And Verify The eligibleLoanAmount
    Examples:
      |TestCase               |XLSheetName|
      |Check the Eligibility1|Eligibility|


  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    Examples:
      |TestCase               |XLSheetName|
      |Check Customer with All valid data but Inhebit marker as Z|Eligibility|
      |Check Customer with refer marker E is eligible|Eligibility|

  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info1
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Response having the valid Description message
    Examples:
      |TestCase               |XLSheetName|
      |Check Customer deceased( Reffer marker J)|Eligibility|
      |Check Customer with Age Under 18|Eligibility|
      |Check Customer with  No First Account and  Age Under 18|Eligibility|
      |Check Check Customer with All valid data but Inhebit marker as I|Eligibility|


  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Intrest rate for the range 1000 to 2450
    And The Intrest rate for the range 2500 to 4950
    And The Intrest rate for the range 5000 to 6950
    And The Intrest rate for the range 7000 to 15000
    And The Intrest rate for the range 15050 to 30000
    And The Intrest rate for the range 30050 to 50000
    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check the Intrest rates for Eligible Customer1|Eligibility|



  @MCT @CERT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if i have all valid but Different Reffer markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE

    Examples:
      |TestCase                                                             |XLSheetName|
      |Check Customer with All valid data but Reffer marker                 |Eligibility|

  @Eligible
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    Examples:
      |TestCase                                                                   |XLSheetName|
      | Check the Intrest rates for Eligible Customer2|Eligibility|


  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility Without FIRST Account.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as NO_FIRST_ACCOUNT
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with No First Account           |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I dont't have First Account and my age is Under 18.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as UNDER_18
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with  No First Account and  Age Under 18                    |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I dont't have First Account and also am having Invalid Inhebit Markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as NO_FIRST_ACCOUNT
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with No First Account And Invalid Inhebit Markers           |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if am Under 18.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as UNDER_18
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Age Under 18                                           |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if am Not Completed ID n V.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as FAILED_STANDARD_CHECKS
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Not Completed ID n V                                    |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I don't have IB/ID04 status as active.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as FAILED_STANDARD_CHECKS
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with not having a IB/ID04 status of Active                   |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I dont't have First Account and also Not Completed ID n V.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as NO_FIRST_ACCOUNT
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with No First Account And Not Completed ID n V               |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I dont't have First Account and also Not Completed ID n V.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as NO_FIRST_ACCOUNT
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with No First Account And Not Completed ID n V              |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I dont't have First Account and also not have a IB/ID04 status as Active.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as NO_FIRST_ACCOUNT
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with No First Account And not have a IB/ID04 status of Active|Eligibility|
  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I dont't have First Account and No Refer marker.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as NO_FIRST_ACCOUNT
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with No First Account And Refer marker                       |Eligibility|
  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if am under 18 and have some Inhebit markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as UNDER_18
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Age Under 18 and  Inhebit Markers                       |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have some Inhebit markers and Not Completed ID n V.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as FAILED_STANDARD_CHECKS
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Inhebit Markers and Not Completed ID n V                |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have some Inhebit markers and not have a IB/ID04 status as Active.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as FAILED_STANDARD_CHECKS
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Inhebit Markers and not have a IB/ID04 status of Active |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have some Inhebit markers and some Refer Markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as FAILED_STANDARD_CHECKS
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Inhebit Markers and Refer Markers                       |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if am under 18 and Not Completed ID n V.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as UNDER_18
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Age Under 18 and  Not Completed ID n V                  |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if am under 18 and not have a IB/ID04 status as Active.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as UNDER_18
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Age Under 18 and  not have a IB/ID04 status of Active   |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if am under 18 and some refer markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as UNDER_18
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Age Under 18 and  refer marker                         |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I not completed ID n V and also having IB/IB04 status as In-Actibe.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as FAILED_STANDARD_CHECKS
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Not Completed ID n V and not having a IB/ID04 status of Active|Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I not completed ID n V and with some Refer marker.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as FAILED_STANDARD_CHECKS
    And The Response having the valid Description message
    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Not Completed ID n V and refer marker                   |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a PO I want to make sure the API Response is not More than 3 Sec.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response Time is Less Than 4 seconds
    Examples:
      |TestCase                                      |XLSheetName|
      |Check the API Response time                   |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    #And The Response having Link to Quotation

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check the Eligibility|Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I am under 18 and having Inhebit and refer markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as UNDER_18
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with Age Under 18 and refer marker and Inhebit marker       |Eligibility|
  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if i have all valid but Different Reffer markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Response having the valid Description message

    Examples:
      |TestCase                                                                   |XLSheetName|
      |Check Customer with All valid data but Reffer marker  as B                 |Eligibility|
      |Check Customer with All valid data but Reffer marker  as C                 |Eligibility|
      |Check Customer with All valid data but Reffer marker  as D                 |Eligibility|
      |Check Customer with All valid data but Reffer marker  as I                 |Eligibility|
      |Check Customer with All valid data but Reffer marker  as M                 |Eligibility|
      |Check Customer with All valid data but Reffer marker  as P                |Eligibility|
      |Check Customer with All valid data but Reffer marker  as V                 |Eligibility|
      |Check Customer with All valid data but Reffer marker  as Y                 |Eligibility|
      |Check Customer with All valid data but Reffer marker  as H                 |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid but Different Inhebit markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    Examples:
      |TestCase                                                  |XLSheetName|
      |Check Customer with All valid data but Reffer marker  as E |Eligibility|
      |Check Customer with All valid data but Reffer marker  as F |Eligibility|
      |Check Customer with All valid data but Reffer marker  as U |Eligibility|
      |Check Customer with All valid data but Reffer marker  as W |Eligibility|
      |Check Customer with All valid data but Reffer marker  as X |Eligibility|
      |Check Customer with All valid data but Reffer marker  as Z |Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid but Different Inhebit markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Response having the valid Description message

    Examples:
      |TestCase                                                  |XLSheetName|
      |Check Customer with All valid data but Inhebit marker as A|Eligibility|
      |Check Customer with All valid data but Inhebit marker as W|Eligibility|
      |Check Customer with All valid data but Inhebit marker as Z|Eligibility|
      |Check Customer with All valid data but Inhebit marker as Space|Eligibility|
      |Check Customer with All valid data but Inhebit marker as I|Eligibility|
      |Check Customer with All valid data but Inhebit marker as Q|Eligibility|

  @SCT
  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid but Different IB Status.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Response having the valid Description message

    Examples:
      |TestCase                                                  |XLSheetName|
      |Check Customer with All valid data but IB Status as readOnlyFailedVerification|Eligibility|
      |Check Customer with All valid data but IB Status as deceasedOrSuspended|Eligibility|
      |Check Customer with All valid data but IB Status as withdrawn|Eligibility|
      |Check Customer with All valid data but IB Status as Closed|Eligibility|
      |Check Customer with All valid data but IB Status as readOnly|Eligibility|
      |Check Customer Which has 2 Accounts one is valid and another is with inhebit markers|Eligibility|
      |Check Customer Which has 2 Accounts one is Joint and another is single account and single acc has inhenit|Eligibility|


  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid but Different Inhebit markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as UNDER_18
    And The Response having the valid Description message

    Examples:
      |TestCase                            |XLSheetName|
      |Check Customer with age equal to 18 |Eligibility|
      |Check Customer age 1 day Lessthan 18|Eligibility|



  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility if I have all valid but Different Inhebit markers.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Response having the valid Description message

    Examples:
      |TestCase                              |XLSheetName|
      |Check Customer age 1 day graterthan 18|Eligibility|



  Scenario Outline: "<TestCase>"
  As a Customer I want to check my Personal loan eligibility with Different APR Values.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    Then The status code is 200
    And The Response having StatusCode as ELIGIBLE
    And The Intrest rate for the range 1000 to 2450
    And The Intrest rate for the range 2500 to 4950
    And The Intrest rate for the range 5000 to 6950
    And The Intrest rate for the range 7000 to 15000
    And The Intrest rate for the range 15050 to 30000
    And The Intrest rate for the range 30050 to 50000
    Examples:
      |TestCase                              |XLSheetName|
      |Check Customer with APR Discount code 321|Eligibility|
      |Check Customer with APR Discount code 600|Eligibility|
      |Check Customer with APR Discount code 310|Eligibility|
      |Check Customer with APR Discount code 330|Eligibility|
      |Check Customer with APR Discount code 400|Eligibility|
      |Check Customer with APR Discount code 910|Eligibility|
      |Check Customer with APR Discount code 911|Eligibility|
      |Check Customer with APR Discount code 911|Eligibility|

  @SCT @FDGMM-725
  Scenario Outline: "<TestCase>"
  As a Customer I want to check The valid error messages displayed for backend system failures.

    Given The User with Some Get Conditions as "<TestCase>" from the module "<XLSheetName>"
    When a user retrieves the eligibility info
    And The Response having StatusCode as Expected
    And The Response having the valid Description message with code
    Examples:
      |TestCase                                                  |XLSheetName|
      |Account list Error code as GENERAL CONSTRAINT VIOLATION with 400 status and Eligibility SAPI 200|Eligibility|
      |Account list RDP400 and Eligibility SAPI 200|Eligibility                                                    |
      |Account list Error code as INVALID_CODE with 400 status and Eligibility SAPI 200|Eligibility                |
      |Account list RDP401 and Eligibility SAPI 200|Eligibility                                                    |
      |Account list 401 with reason code 00003|Eligibility                                                         |
      |Account list 401 with reason code 00009|Eligibility                                                         |
      |Account list 401 with reason code 00005|Eligibility                                                         |
      |Account list 401 with reason code 00006|Eligibility                                                         |
      |Account list RDP403 and Eligibility SAPI 200|Eligibility                                                    |
      |Account list RDP404 and Eligibility SAPI 200|Eligibility                                                    |
      |Account list RDP500 and Eligibility SAPI 200|Eligibility                                                    |
      |Account list RDP406 and Eligibility SAPI 200|Eligibility                                                    |
      |Account list RDP504 and Eligibility SAPI 200|Eligibility                                                    |
      |Account list 200 and Eligibility SAPI 400|Eligibility                                                       |
      |Account list 200 and Eligibility SAPI 401|Eligibility                                                       |
      |Account list 200 and Eligibility SAPI 403|Eligibility                                                       |
      |Account list 200 and Eligibility SAPI 404|Eligibility                                                       |
      |Account list 200 and Eligibility SAPI 500|Eligibility                                                       |
      |Account list with 200 and eligibility with 500 server error|Eligibility                                     |
      |Account list 200 and Eligibility SAPI 400 with 98007 code|Eligibility                                       |
      |Account list 200 and Eligibility SAPI 504|Eligibility                                                       |





