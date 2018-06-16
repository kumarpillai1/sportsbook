//package stepdefs;
//
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.When;
//import org.json.simple.parser.ParseException;
//
//import java.io.IOException;
//
//import static io.restassured.RestAssured.given;
//
///**
// * Created by 44077707 on 05/10/2017.
// */
//public class DemoSteps {
//
//
//}
//
//
////################################# DEMO#############################################
//
//    @Given("^The User with EAPI Integrated with SAPI and PAPI$")
//
//    public void The_User_with_EAPI_Integrated_with_SAPI_and_PAPI() throws IOException, ParseException {
////        xl.ReadExcel(prop.getProperty("XlPathName"),"Demo");
////        tcname = arg1;
////        String un = ExcelRead.hm1.get(tcname).get("UserName");
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("eligiblecustomer"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//    }
//
//    @When("^a user retrieves data from AccountList SAPI$")
//    public void method1(){
//
//       /* response = request.when().get(prop.getProperty("AccountListSAPI"));
//        System.out.println("response: " + response.prettyPrint());
//
//        scenario.write("The ACCOUNT List SAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^a user retrieves data from Eligibility SAPI$")
//    public void method2(){
///*
//        //APIUrl  = "https://fd-customer-eligibility--gb-hbeu-v1-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/0985001100/eligibility";
//        response = request.when().get(prop.getProperty("EligibileSAPI"));
//        System.out.println("response: " + response.prettyPrint());
//
//        scenario.write("The Eligibility SAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^a user retrieves data from PAPI Integrated with SAPIs$")
//    public void method3(){
///*
//
//        response = request.when().get(prop.getProperty("EligiblePAPI"));
//        System.out.println("response: " + response.prettyPrint());
//
//        scenario.write("The Eligibility PAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^The user Retrieves data from EAPI Integated with PAPI and SAPIs$")
//    public void method4(){
//
//        response = request.when().get(prop.getProperty("EligibleEAPI"));
//        System.out.println("response: " + response.prettyPrint());
//
//        scenario.write("The Eligibility EAPI Response is :"+response.prettyPrint());
//    }
//
////#################################### IN Eligible Customer##############################################
//
//
//
//    @Given("^The User with EAPI Integrated with SAPI and PAPI1$")
//
//    public void The_User_with_EAPI_Integrated_with_SAPI_and_PAPI1() throws IOException, ParseException {
////        xl.ReadExcel(prop.getProperty("XlPathName"),"Demo");
////        tcname = arg1;
////        String un = ExcelRead.hm1.get(tcname).get("UserName");
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("ineligiblecustomer"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//    }
//
//    @When("^a user retrieves data from AccountList SAPI1$")
//    public void method11(){
//
//    /*
//        response = request.when().get(prop.getProperty("AccountListSAPI1"));
//        System.out.println("response: " + response.prettyPrint());
//
//        scenario.write("The ACCOUNT List SAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^a user retrieves data from Eligibility SAPI1$")
//    public void method21(){
//
//     /*  // APIUrl  = "https://fd-customer-eligibility--gb-hbeu-v0-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/0900011424/eligibility";
//        response = request.when().get(prop.getProperty("EligibileSAPI1"));
//        System.out.println("response: " + response.prettyPrint());
//
//        scenario.write("The Eligibility SAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^a user retrieves data from PAPI Integrated with SAPIs1$")
//    public void method31(){
//
//      /*  //APIUrl  = "http://fd-customers--gb-hbeu-v0-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/eligibility/personal-loan";
//        response = request.when().get(prop.getProperty("EligiblePAPI"));
//        System.out.println("response: " + response.prettyPrint());
//
//        scenario.write("The Eligibility PAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^The user Retrieves data from EAPI Integated with PAPI and SAPIs1$")
//    public void method41(){
//
//        response = request.when().get(prop.getProperty("EligibleEAPI"));
//        System.out.println("response: " + response.prettyPrint());
//
//        scenario.write("The Eligibility EAPI Response is :"+response.prettyPrint());
//    }
//
//
////########################### Get Account List###########################
//
//
//
//    @Given("^The User with EAPI Integrated with SAPI and PAPI GetAccounts$")
//
//    public void account1() throws IOException, ParseException {
//
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("customeracc1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//    }
//
//    @When("^a user retrieves data from AccountList SAPI2$")
//    public void Account11(){
//
///*
//        response = request.when().get(prop.getProperty("AccountListSAPI2"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The ACCOUNT List SAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The ACCOUNT List SAPI Response is :"+response.prettyPrint());*/
//    }
//
//
//    @When("^a user retrieves data from PAPI Integrated with SAPIs account list$")
//    public void methoaccount111(){
///*
//        //APIUrl  = "http://fd-customers--gb-hbeu-v1-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/eligibility/personal-loan";
//        response = request.when().get(prop.getProperty("AccountlistPAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The ACCOUNT List PAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Account list PAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^The user Retrieves data from EAPI Integated with PAPI and SAPIs account list$")
//    public void account121(){
//
//        //APIUrl  = "http://fd-personal-loan-mob--gb-hbeu-v0-mct.cf.wgdc-drn-01.cloud.uk.hsbc/personal-loans/accounts?icmInstanceId=123456";
//        response = request.when().get(prop.getProperty("AccountEAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        // scenario.write("The ACCOUNT List EAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Account list  EAPI Response is :"+response.prettyPrint());
//    }
////########################### Get Account List###########################
//
//
//
//    @Given("^The User with EAPI Integrated with SAPI and PAPI GetAccounts1$")
//
//    public void account12() throws IOException, ParseException {
//
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("customeracc2"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//    }
//
//    @When("^a user retrieves data from AccountList SAPI21$")
//    public void Account22(){
///*
//        //APIUrl  ="http://fd-accounts-list--gb-hbeu-v1-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/0985004681/account-details";
//        response = request.when().get(prop.getProperty("AccountListSAPI2"));
//        System.out.println("response: " + response.prettyPrint());
//       // scenario.write("The ACCOUNT List SAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The ACCOUNT List SAPI Response is :"+response.prettyPrint());*/
//    }
//
//
//    @When("^a user retrieves data from PAPI Integrated with SAPIs account list1$")
//    public void methoaccount21(){
///*
//        //APIUrl  = "http://fd-customers--gb-hbeu-v1-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/eligibility/personal-loan";
//        response = request.when().get(prop.getProperty("AccountlistPAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The ACCOUNT List PAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Account list PAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^The user Retrieves data from EAPI Integated with PAPI and SAPIs account list1$")
//    public void account122(){
//
//        //APIUrl  = "http://fd-personal-loan-mob--gb-hbeu-v0-mct.cf.wgdc-drn-01.cloud.uk.hsbc/personal-loans/accounts?icmInstanceId=123456";
//        response = request.when().get(prop.getProperty("AccountEAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        // scenario.write("The ACCOUNT List EAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Account list  EAPI Response is :"+response.prettyPrint());
//    }
//
//
////################### Customer Details Review #####################
//
//    @Given("^The User with EAPI Integrated with SAPI and PAPI PersonalDetails$")
//
//    public void CheckDetail01() throws IOException, ParseException {
//
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("customerwithDetails1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//    }
//
//    @When("^a user retrieves data from PersonalDetails SAPI$")
//    public void CheckDetail02(){
///*
//        response = request.when().get(prop.getProperty("personalDetailsSAPI"));
//        System.out.println("response: " + response.prettyPrint());
//       //scenario.write("The Personal Details SAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Details SAPI Response is :"+response.prettyPrint());*/
//    }
//
//
//    @When("^a user retrieves data from PAPI Integrated with SAPIs PersonalDetails$")
//    public void CheckDetail03(){
///*
//        response = request.when().get(prop.getProperty("personalDetailsPAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        scenario.write("The Personal Details PAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Details PAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^The user Retrieves data from EAPI Integated with PAPI and SAPIs PersonalDetails$")
//    public void CheckDetail04(){
//
//        response = request.when().get(prop.getProperty("personalDetailsEAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        // scenario.write("The Personal Details EAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Details EAPI Response is :"+response.prettyPrint());
//    }
//    @Given("^The User with EAPI Integrated with SAPI and PAPI PersonalDetails1$")
//
//    public void CheckDetail011() throws IOException, ParseException {
//
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("customerwithDetails2"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//    }
//
//    @When("^a user retrieves data from PersonalDetails SAPI1$")
//    public void CheckDetail2(){
//
//      /*  response = request.when().get(prop.getProperty("personalDetailsSAPI1"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The Personal Details SAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Details SAPI Response is :"+response.prettyPrint());*/
//    }
//
//
////########################### Quotation Tests ###############################################################
//
//    @Given("^the User with EAPI Integrated with SAPI and PAPI Quotation$")
//
//    public void Quotation1() throws IOException, ParseException {
///*
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");*/
//    }
//    @When("^the user retrieves data from Personal Loan Quotation SAPI$")
//    public void Quotation2()throws IOException, ParseException{
//       /* String QsapiBoby= "{ \"jointCin\": \"0977758702\",\"age\": 32,\"loanAmount\": {\"currencyCode\": \"GBP\", \"amount\": 15000.00},\"repaymentAmount\": {\"currencyCode\": \"GBP\", \"amount\": 0},\"apr\": 0.159,\"term\": 12}\n";
//        scenario.write("The Personal Loan Quotation SAPI Request data is :"+QsapiBoby);
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("Content-Type","application/json")
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234")
//                .body(QsapiBoby);
//        response = request.when().post(prop.getProperty("QuotationSAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The Personal Loan Quotation SAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Quotation SAPI Response is :"+response.prettyPrint());*/
//    }
//
//    @When("^the user retrieves data from Personal loan Quotation PAPI$")
//    public void Quotation3() throws IOException, ParseException{
//       /* String QsapiBoby1= "{\"icmInstanceId\": 123456,\"loanAmount\": {\"amount\": \"1500000\",\"currencyCode\": \"GBP\"},\"repaymentPeriod\": 12,\"purpose\": \"car\"}\n";
//        scenario.write("The Personal Loan Quotation PAPI Request data is :"+QsapiBoby1);
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("Content-Type","application/json")
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234")
//                .body(QsapiBoby1);
//        response = request.when().post(prop.getProperty("QuotatioPAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The Personal Loan Quotation PAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Quotation PAPI Response  is :"+response.prettyPrint());*/
//    }
//
//    @When("^the user retrieves data from Personal loan Eligibility PAPI Integrated with Quotation PAPI$")
//    public void Quotation4()throws IOException, ParseException{
//       /* newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//        response = request.when().get(prop.getProperty("EligiblePAPI"));
//        System.out.println("response: " + response.prettyPrint());
//       // scenario.write("The Personal Loan Eligibility PAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Eligibility PAPI Response  is :"+response.prettyPrint());*/
//    }
//    @When("^the user retrieves data from Eligibility SAPI Integrated with Eligibility PAPI$")
//    public void Quotation5()throws IOException, ParseException{
///*
//        response = request.when().get(prop.getProperty("EligibilityQuoteSAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The Personal Loan Eligibility SAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Eligibility SAPI Response  is :"+response.prettyPrint());*/
//    }
//    @When("^The user Retrieves data from Quotation EAPI Integated with Quotation PAPI and SAPIs$")
//    public void Quotation6()throws IOException, ParseException{
//       /* String QsapiBoby= "{\"icmInstanceId\": 123456,\"loanAmount\": { \"amount\": 1500000,\"currencyCode\": \"GBP\"},\"repaymentPeriod\": 12,\"purpose\": \"car\"}\n";
//        scenario.write("The Personal Loan Quotation EAPI Request data is :"+QsapiBoby);
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("Content-Type","application/json")
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234")
//                .body(QsapiBoby);
//
//        response = request.when().post(prop.getProperty("QuotationEAPI"));
//        System.out.println("response: " + response.prettyPrint());
//       // scenario.write("The Personal Loan Quotation EAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Quotation EAPI Response is :"+response.prettyPrint());*/
//    }
//
//
//    @Given("^the User with EAPI Integrated with SAPI and PAPI Quotation1$")
//
//    public void Quotation11() throws IOException, ParseException {
//
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//    }
//    @When("^the user retrieves data from Personal Loan Quotation SAPI1$")
//    public void Quotation21()throws IOException, ParseException{
//       /* String QsapiBoby= "{ \"jointCin\": \"0985001100\",\"age\": 32,\"loanAmount\": {\"currencyCode\": \"GBP\", \"amount\": 7000.00},\"repaymentAmount\": {\"currencyCode\": \"GBP\", \"amount\": 0},\"apr\": 0.033,\"term\": 12}\n";
//        scenario.write("The Personal Loan Quotation SAPI Request data is :"+QsapiBoby);
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer2"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("Content-Type","application/json")
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234")
//                .body(QsapiBoby);
//        response = request.when().post(prop.getProperty("QuotationSAPI1"));
//        System.out.println("response: " + response.prettyPrint());
//       // scenario.write("The Personal Loan Quotation SAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Quotation SAPI Response  is :"+response.prettyPrint());*/
//    }
//
//    @When("^the user retrieves data from Personal loan Quotation PAPI1$")
//    public void Quotation31() throws IOException, ParseException{
//       /* String QsapiBoby1= "{\"icmInstanceId\": 123456,\"loanAmount\": {\"amount\": \"700000\",\"currencyCode\": \"GBP\"},\"repaymentPeriod\": 12,\"purpose\": \"car\"}\n";
//        scenario.write("The Personal Loan Quotation PAPI Request data is :"+QsapiBoby1);
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("Content-Type","application/json")
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234")
//                .body(QsapiBoby1);
//        response = request.when().post(prop.getProperty("QuotatioPAPI"));
//        System.out.println("response: " + response.prettyPrint());
//       // scenario.write("The Personal Loan Quotation PAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Quotation PAPI Response  is :"+response.prettyPrint());*/
//    }
//
//    @When("^the user retrieves data from Personal loan Eligibility PAPI Integrated with Quotation PAPI1$")
//    public void Quotation41()throws IOException, ParseException{
//       /* newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234");
//        response = request.when().get(prop.getProperty("EligiblePAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The Personal Loan Eligibility PAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Eligibility PAPI Response  is :"+response.prettyPrint());*/
//    }
//    @When("^the user retrieves data from Eligibility SAPI Integrated with Eligibility PAPI1$")
//    public void Quotation51()throws IOException, ParseException{
///*
//        response = request.when().get(prop.getProperty("EligibilityQuoteSAPI1"));
//        System.out.println("response: " + response.prettyPrint());
//       // scenario.write("The Personal Loan Eligibility SAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Eligibility SAPI Response  is :"+response.prettyPrint());*/
//    }
//    @When("^The user Retrieves data from Quotation EAPI Integated with Quotation PAPI and SAPIs1$")
//    public void Quotation61()throws IOException, ParseException{
//        String QsapiBoby= "{\"icmInstanceId\": 123456,\"loanAmount\": { \"amount\": 700000,\"currencyCode\": \"GBP\"},\"repaymentPeriod\": 12,\"purpose\": \"car\"}\n";
//        scenario.write("The Personal Loan Quotation EAPI Request data is :"+QsapiBoby);
//        newSamlToken = saml.GenerateSamlToken(prop.getProperty("QuotCustomer1"));
//        SamlToken=   newSamlToken.trim();
//        String string = String.format("%s", SamlToken);
//        System.out.println(string);
//        request = given()
//                .header("X-HSBC-Saml", string)
//                .header("Content-Type","application/json")
//                .header("X-HSBC-IP-Id","145.26.54.24")
//                .header("X-HSBC-Channel-Id","FDPIB")
//                .header("X-HSBC-Chnl-CountryCode","GB")
//                .header("X-HSBC-Session-Correlation-Id","1234")
//                .header("X-HSBC-Request-Correlation-Id","1345")
//                .header("X-HSBC-Locale","GB")
//                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
//                .header("X-HSBC-User-Id","C12345678")
//                .header("X-HSBC-CAM-Level","30")
//                .header("X-HSBC-Chnl-Group-Member","HBEU")
//                .header("X-HSBC-Src-Device-Id","1234")
//                .body(QsapiBoby);
//
//        response = request.when().post(prop.getProperty("QuotationEAPI"));
//        System.out.println("response: " + response.prettyPrint());
//        //scenario.write("The Personal Loan Quotation EAPI Response Time is :"+response.getTimeIn(TimeUnit.SECONDS));
//        scenario.write("The Personal Loan Quotation EAPI Response  is :"+response.prettyPrint());
//    }
//
//
//
//
//
//}
