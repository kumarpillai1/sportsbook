Feature: 3.Customer Details Review

#  @CustDetailsReview @MCT @CERT
#  Scenario Outline: "<CustomerDetailsReview>"
#  As A PO I want to Check the Personal information for Others
#
#    Given The User with Different Employement status as "<CustomerDetailsReview>"
#    When the user retrieves the Customer Personal Information
#    Then The status code is 200
#    Then Verify the Customer employmentStatus is OTHERS
#    Then Verify the Customer residential Status does not display
#    Then Verify customer gross Annual Income
#    Examples:
#      |CustomerDetailsReview                              |
#      |Customer with Employment status as Other     |

@CustDetailsReview @MCT @CERT
  Scenario Outline: "<CustomerDetailsReview>"
      As A PO I want to Check the Personal information for Unemployed Customers

    Given The User with Different Employement status as "<CustomerDetailsReview>"
    When the user retrieves the Customer Personal Information
    Then The status code is 200
    Then Verify the Customer employmentStatus is UNEMPLOYED
    Then Verify the Customer residential Status does not display
    Then Verify customer gross Annual Income
  Examples:
    |CustomerDetailsReview                              |
    |Customer with Employment status as unemployed      |

  @CustDetailsReview @MCT @CERT
  Scenario Outline: "<CustomerDetailsReview>"
  As A PO I want to Check the Personal information for self employed Customers

    Given The User with Different Employement status as "<CustomerDetailsReview>"
    When the user retrieves the Customer Personal Information
    Then The status code is 200
    Then Verify the Customer employmentStatus is SELFEMPLOYED
    Then Verify the Customer residential Status does not display
    Then Verify customer gross Annual Income
    Examples:
      |CustomerDetailsReview                              |
      |Customer with Employment status as selfemployed     |


  @CustDetailsReview @MCT @CERT
  Scenario Outline: "<CustomerDetailsReview>"
  As A PO I want to Check the Personal information for Director

    Given The User with Different Employement status as "<CustomerDetailsReview>"
    When the user retrieves the Customer Personal Information
    Then The status code is 200
    Then Verify the Customer employmentStatus is DIRECTOR
    Then Verify the Customer residential Status does not display
    Then Verify customer gross Annual Income
    Examples:
      |CustomerDetailsReview                              |
      |Customer with Employment status as Director     |



  @CustDetailsReview @MCT @CERT
  Scenario Outline: "<CustomerDetailsReview>"
  As A PO I want to Check the Personal information for Employed

    Given The User with Different Employement status as "<CustomerDetailsReview>"
    When the user retrieves the Customer Personal Information
    Then The status code is 200
    Then Verify the Customer employmentStatus is EMPLOYED
    Then Verify the Customer residential Status does not display
    Then Verify customer gross Annual Income
    Examples:
      |CustomerDetailsReview                              |
      |Customer with Employment status as Employed     |
  @CustDetailsReview @MCT @CERT
  Scenario Outline: "<CustomerDetailsReview>"
  As A PO I want to Check the Personal information for STUDENT

    Given The User with Different Employement status as "<CustomerDetailsReview>"
    When the user retrieves the Customer Personal Information
    Then The status code is 200
    Then Verify the Customer employmentStatus is STUDENT
    Then Verify the Customer residential Status does not display
    Then Verify customer gross Annual Income
    Examples:
      |CustomerDetailsReview                              |
      |Customer with Employment status as Student     |


  @CustDetailsReview @MCT @CERT
  Scenario Outline: "<CustomerDetailsReview>"
  As A PO I want to Check the Personal information for Contractor

    Given The User with Different Employement status as "<CustomerDetailsReview>"
    When the user retrieves the Customer Personal Information
    Then The status code is 200
    Then Verify the Customer employmentStatus is Contractor
    Then Verify the Customer residential Status does not display
    Then Verify customer gross Annual Income
    Examples:
      |CustomerDetailsReview                              |
      |Customer with Employment status as Contractor     |