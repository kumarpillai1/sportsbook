#Feature: Get And Store Of Quotation
#  As a PO I want to check Personal loan eligible customer is able to get the Valid Quotation based on his loan amount, repayment period and apr rate etc..
#
#
#  @getAndStore @MCT
#  Scenario Outline: "<QuotationTestCase>"
#  As a Customer I want to check my loan Quotation with diferent combinations.
#
#    Given The User with personalLoan Eligibility and Quotation "<QuotationTestCase>"
#    When a user retrieves the Quotation with some amount and Repayment Shedule info
#    Then The status code is 200
#    And Verify The ID Created In MongoDB for instance
#
#    Examples:
#      |QuotationTestCase                                             |
#      |Check Customer Quotation value In DB for Valid Data|
#
#  @getAndStore @MCT
#  Scenario Outline: "<QuotationTestCase>"
#  As a Customer I want to check my loan Quotation with diferent combinations.
#
#    Given The User with in valid post Quotation "<QuotationTestCase>"
#    When a user retrieves the Quotation with some amount and Repayment Shedule info
#    Then The status code is 400
#    #Then The Message should be valid
#
#    Examples:
#      |QuotationTestCase                                             |
#      |Check Customer Quotation value In DB for inValid Data|
#
#  @getAndStore @MCT
#  Scenario Outline: "<QuotationTestCase>"
#  As a Customer I want to check my loan Quotation with diferent combinations.
#
#    Given The User with personalLoan Eligibility and Quotation "<QuotationTestCase>"
#    When a user retrieves the Quotation with some amount and Repayment Shedule info
#    Then The status code is 404
#    #Then The Message should be valid
#
#    Examples:
#      |QuotationTestCase                                             |
#      |Check Customer Quotation value In DB for inValid end point|
#
#  @getAndStore @MCT
#  Scenario Outline: "<QuotationTestCase>"
#  As a Customer I want to check my loan Quotation with diferent combinations.
#
#    Given The User with personalLoan Eligibility and Quotation1 "<QuotationTestCase>"
#    When a user retrieves the Quotation from DB
#    Then The status code is 200
#
#
#    Examples:
#      |QuotationTestCase                                             |
#      |Check Customer Get Qutoes value from DB|
#  @getAndStore @MCT
#  Scenario Outline: "<QuotationTestCase>"
#  As a Customer I want to check my loan Quotation with diferent combinations.
#
#    Given The User with personalLoan Eligibility and Quotation1 "<QuotationTestCase>"
#    When a user retrieves the Quotation from DB1
#    Then The status code is 404
#    #Then The Message should be valid
#
#    Examples:
#      |QuotationTestCase                                             |
#      |Check Customer try to get invalid Qutoes value from DB|
#  @getAndStore @MCT
#  Scenario Outline: "<QuotationTestCase>"
#  As a Customer I want to check my loan Quotation with diferent combinations.
#
#    Given The User with personalLoan Eligibility and Quotation "<QuotationTestCase>"
#    When a user retrieves the Quotation with some amount and Repayment Shedule info
#    Then The status code is 400
#    Then The Message should be valid code and message from EAPI
#
#    Examples:
#      |QuotationTestCase                                             |
#      |Check Customer Quotation value In DB for inValid Loan Amount  |
#      | Check Customer Quotation value In DB for inValid Purpose|
#      |Check Customer Quotation value In DB for inValid RepaymentPeriod|
