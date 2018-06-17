package stepdefs;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static services.Common.GetMethodHeaders;
import static services.Common.PostMethodHeaders;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.config.JsonConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.config.JsonPathConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.net.SyslogAppender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.apache.commons.lang3.StringUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import cucumber.api.java.After;
import org.junit.Assert;
import services.ExcelRead;
import services.ReadMongoDB;
import services.SamlTokenGenerate;

public class StepDefinitions {

    static String AccountNumber = null;
    static String SortCode = null;
    static String PLAPreAuthLimit = null;
    static  int ICMInstanceID =12345;
    String SAPIErrorCode =null;
    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;
    String APIUrl= null;
    String key1 = null;
    String value1 = null;
    String key2 = null;
    String value2 = null;
    String tcname = null;
    static  int QIdInResponse=0;
    String newSamlToken = null;
    String SamlToken = null;
    ExcelRead xl = new ExcelRead();
    SamlTokenGenerate saml = new SamlTokenGenerate();
    Scenario scenario=null;
    String XlPathName = null;
    Properties prop = new Properties();
    ReadMongoDB mongoDB =  new ReadMongoDB();
    String quotbody =null;
    static  int quotationID =0;
    Logger logger = Logger.getLogger(StepDefinitions.class);
    public int RandomGen() {
        Random r = new Random(System.currentTimeMillis());
        return 1000000000 + r.nextInt(2000000000);
    }

    // Before Starting the Scenarios Execution load all properties files
    @Before
    public void beforeScenario(Scenario scenario1)throws IOException, ParseException {
        scenario = scenario1;
        logger.info("Executing Tests On ENVIRONMENT :"+System.getProperty("environment"));
        logger.info("Scenario :"+scenario.getName() +" Started....");
        String saparater =  System.getProperty("file.separator");
        String projectPath =  System.getProperty("user.dir");

        System.clearProperty("javax.net.ssl.trustStorePassword");
        System.clearProperty("javax.net.ssl.trustStore");

        String trustStoreLocation = projectPath+saparater+"fd_sapi_dev.jks";
        System.setProperty("javax.net.ssl.trustStorePassword", "fd_123");
        System.setProperty("javax.net.ssl.trustStore", trustStoreLocation);

        String configpath = projectPath+saparater+"src"+saparater+"test"+saparater+"java"+saparater+"utils"+saparater+"config.properties";
        //logger.info("Config File  :"+configpath +" Loaded Successfully....");
        prop.load(new FileInputStream(configpath));

        if(System.getProperty("environment").equalsIgnoreCase("SCT")){
            XlPathName = prop.getProperty("XlPathNameSCT");
        }else if(System.getProperty("environment").equalsIgnoreCase("MCT")){
            XlPathName = prop.getProperty("XlPathNameMCT");
        }else if(System.getProperty("environment").equalsIgnoreCase("CERT")){
            XlPathName = prop.getProperty("XlPathNameMCT");
        }
        else{

            XlPathName = prop.getProperty("XlPathNameMCT");
        }

   }

    @After

    public void afterScenario(Scenario scenario)throws  IOException {
        //If Scenario Failed Print the Response in the HTML report for reference
        if(scenario.isFailed()){
            scenario.write("The EAPI Name:" + APIUrl);
            scenario.write("The SAML Token Used:" + newSamlToken);
            scenario.write("Input Used for this Request:"+ quotbody);
            String s = response.body().prettyPrint();
            scenario.embed(s.getBytes(),"text/html");

            logger.error("Scenario :"+scenario.getName() +"Ended....And STATUS IS: "+scenario.getStatus());
        }else {
            String s = response.body().prettyPrint();
            scenario.embed( s.getBytes(),"text/html");
            logger.info("Scenario :"+scenario.getName() +"Ended....And STATUS IS: "+scenario.getStatus());
        }
    }

    @Given("^The User with Some Conditions as \"([^\"]*)\"$")
    public void userAddressAPI1(String arg1) throws IOException, ParseException, Exception{

        xl.ReadExcel(XlPathName,"Eligibility");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        logger.info("UserName Used "+un);
        System.out.println("UserName:"+un);
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        logger.info("SAML Token  Used "+string);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        logger.info("Secssion ID:"+sessionID);
        logger.info("Request ID:"+RequestID);
        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");
       //logger.info("Request Headers Used "+request.get().getHeaders());
    }

    @Given("^The User with QuotationID and Other Details for decision As \"([^\"]*)\" from module \"([^\"]*)\"$")
    public void The_User_with_QuotationID_and_Other_Details_for_decision(String arg1, String XlSheetName) throws Exception {

        xl.ReadExcel(XlPathName, XlSheetName);
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        String employmentStatus = ExcelRead.hm1.get(tcname).get("employmentStatus");
        String residentialStatus = ExcelRead.hm1.get(tcname).get("residentialStatus");
        String ukResidentStatus = ExcelRead.hm1.get(tcname).get("ukResidentStatus");
        String dayOfMonth = ExcelRead.hm1.get(tcname).get("dayOfMonth");
        String Income = ExcelRead.hm1.get(tcname).get("Income");
        String sortCode = ExcelRead.hm1.get(tcname).get("sortCode");
        String AccountNum1 = ExcelRead.hm1.get(tcname).get("AccountNumber");
if(tcname.contains("Invalid Quotation")){

    quotationID =100;
}

        String DecisionInputBody = prop.getProperty("DecisionEAPI")

                .replace("ICMXXXX", String.valueOf(ICMInstanceID))
                .replace("QuotationXXXX", String.valueOf(quotationID))
                .replace("employXXXX", employmentStatus)
                .replace("ResStatusXXXX", residentialStatus)
                .replace("incomeXXXX", Income)
                .replace("sortcodeXX", sortCode)
                .replace("accnumXXXX", AccountNum1)
                .replace("dayOFmonXXX", dayOfMonth)
                .replace("isUKXXXX", ukResidentStatus);

        request = PostMethodHeaders(un, DecisionInputBody);
        scenario.write("The Decision  API Input is : :" + DecisionInputBody);
    }


    @Given("^The User with QuotationID from MongoDB and Other Details for decision As \"([^\"]*)\" from module \"([^\"]*)\"$")
    public void The_User_with_QuotationID_and_Other_Details_for_decision1(String arg1, String XlSheetName) throws Exception {

        xl.ReadExcel(XlPathName, XlSheetName);
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        String employmentStatus = ExcelRead.hm1.get(tcname).get("employmentStatus");
        String residentialStatus = ExcelRead.hm1.get(tcname).get("residentialStatus");
        String ukResidentStatus = ExcelRead.hm1.get(tcname).get("ukResidentStatus");
        String dayOfMonth = ExcelRead.hm1.get(tcname).get("dayOfMonth");
        String Income = ExcelRead.hm1.get(tcname).get("Income");

        String DecisionInputBody = prop.getProperty("DecisionEAPI")

                .replace("ICMXXXX", String.valueOf(ICMInstanceID))
                .replace("QuotationXXXX", String.valueOf(quotationID))
                .replace("employXXXX", employmentStatus)
                .replace("ResStatusXXXX", residentialStatus)
                .replace("incomeXXXX", Income)
                .replace("sortcodeXX", SortCode)
                .replace("accnumXXXX", AccountNumber)
                .replace("dayOFmonXXX", dayOfMonth)
                .replace("isUKXXXX", ukResidentStatus);

        request = PostMethodHeaders(un, DecisionInputBody);
        scenario.write("The Decision  API Input is : :" + DecisionInputBody);
    }


    @Given("^The User with Different accounts as \"([^\"]*)\"$")
    public void The_User_with_Different_accounts_as(String arg1) throws IOException, ParseException,Exception {
        xl.ReadExcel(XlPathName,"GetEligibleAccounts");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);

        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C123456789")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");
        //.queryParam("icmInstanceId","123456");
        ///logger.info("Request Headers Used "+request.get().getHeaders());
    }

    @Given("^The User with different Addresses as \"([^\"]*)\"$")

    public void The_User_with_different_Addresses(String arg1) throws IOException, ParseException,Exception {
        xl.ReadExcel(XlPathName,"SAPI-HomeAddress");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
      newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);

        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","30")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");
        //logger.info("Request Headers Used "+request.get().getHeaders());
    }
    @Given("^The User with Different Employement status as \"([^\"]*)\"$")
    public void EmployementStatus(String arg1) throws IOException, ParseException,Exception {

        xl.ReadExcel(XlPathName,"CustomerDetailsReview");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);

        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","30")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");
        //logger.info("Request Headers Used "+request.get().getHeaders());
    }
    @When("^a user retrieves the eligibility info$")
    public void a_user_retrieves_the_eligible_Info1() {

        String environment1 = System.getProperty("environment");
        String pla_version=System.getProperty("pla_version");
        String host=prop.getProperty("ServerName").replace("{version}",pla_version);
        String EligibilityEAPI=String.format(host,environment1).concat("/personal-loans/eligibility");
        scenario.write("API used "+EligibilityEAPI);
        response = request.when().get(EligibilityEAPI);

        ICMInstanceID = response.getBody().jsonPath().get("icmInstanceId");
    }

    @When("^a user retrieves the eligibility info1$")
    public void a_user_retrieves_the_eligible_Info111() {

        String environment1 = System.getProperty("environment");
        String pla_version=System.getProperty("pla_version");
        String host=prop.getProperty("ServerName").replace("{version}",pla_version);
        String EligibilityEAPI=String.format(host,environment1).concat("/personal-loans/eligibility");
        scenario.write("API used "+EligibilityEAPI);
        response = request.when().get(EligibilityEAPI);

    }
    @When("^the User Get the Pre Auth Limit$")
    public void the_User_Get_the_Pre_Auth_Limit() {
        String preAuthSAPIURL = prop.getProperty("PreAuthlimitSAPIURL");
        response = request.when().get(preAuthSAPIURL);
        scenario.write("Response Got " + response.prettyPrint());
        logger.info("Response Got " + response.prettyPrint());
        List preAuthLimits = response.getBody().jsonPath().getList("limitDetails.limitAmount.amount");

        PLAPreAuthLimit = preAuthLimits.get(3).toString();
    }

    @When("^a user retrieves the list of eligible accounts$")
    public void a_user_retrieves_the_list_of_eligible_accounts(){
        String environment1 = System.getProperty("environment");
        String pla_version=System.getProperty("pla_version");
        String host=prop.getProperty("ServerName").replace("{version}",pla_version);
        String GetEligibleAccountsEAPI=String.format(host,environment1).concat("/personal-loans/accounts?icmInstanceId=123456");
        scenario.write(GetEligibleAccountsEAPI);
        response = request.when().get(GetEligibleAccountsEAPI);
        System.out.println("response: " + response.prettyPrint());
        logger.info("Response Got "+response.prettyPrint());

    }
    @When("^a user retrieves the HomeAdddress info$")
    public void user_retrieves_the_HomeAdddress_info(){
        APIUrl  = ExcelRead.hm1.get(tcname).get("SAPI");
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        APIUrl = APIUrl+un+"?fetchSegment=HomeAddress";
        response = request.when().get(APIUrl);
        System.out.println("response: " + response.prettyPrint());
        logger.info("Response Got "+response.prettyPrint());

        //JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze();
        //request.when().get(APIUrl).then().assertThat().body(matchesJsonSchemaInClasspath("eligible.json").using(jsonSchemaFactory));
    }
    @Then("^The status code is 200$")
    public void verifyStatus(){
        String StatusCode=   ExcelRead.hm1.get(tcname).get("StatusCode");

        json = response.then().statusCode(Integer.parseInt(StatusCode));

    }
    @Then("^The status code is 201$")
    public void verifyStatus1(){
        String StatusCode=   ExcelRead.hm1.get(tcname).get("StatusCode");

        json = response.then().statusCode(Integer.parseInt(StatusCode));

    }
    @Then("^The status code is 400$")
    public void verifyStatusAs400(){
        String StatusCode=   ExcelRead.hm1.get(tcname).get("StatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));

    }
    @Then("^Verify The status code is 400$")
    public void verifyStatusAs_400(){

        json = response.then().statusCode(400);

    }
    @Then("^The status code is 404$")
    public void verifyStatusAs404(){
        String StatusCode=   ExcelRead.hm1.get(tcname).get("StatusCode");

        json = response.then().statusCode(Integer.parseInt(StatusCode));

    }

    @Then("^The status code is 403$")
    public void verifyStatusAs403(){
        String StatusCode=   ExcelRead.hm1.get(tcname).get("StatusCode");

        json = response.then().statusCode(Integer.parseInt(StatusCode));

    }
    @And("^Verify The ID Created In MongoDB for instance$")
    public void verifyID_In_MongoDB() throws ParseException{

        String CIN=   ExcelRead.hm1.get(tcname).get("UserName");
        String  principleLoanAmt=  ExcelRead.hm1.get(tcname).get("principleLoanAmt");
        String  apr=  ExcelRead.hm1.get(tcname).get("Validation4");
        String term = ExcelRead.hm1.get(tcname).get("Validation3");
        String LoanPurpose = ExcelRead.hm1.get(tcname).get("loanPurpose");
        String firstpayment = ExcelRead.hm1.get(tcname).get("firstpayment");
        String totalInterest= ExcelRead.hm1.get(tcname).get("totalInterest");
        String SubsequentLoanAmt = ExcelRead.hm1.get(tcname).get("SubsequentLoanAmt");
        String loanPlusInterestAmount = ExcelRead.hm1.get(tcname).get("Validation8");
         QIdInResponse =  response.jsonPath().getInt("quotationId");

      mongoDB.VerifyIDAndCustomerInMongoDB(QIdInResponse,CIN,principleLoanAmt,apr,term,LoanPurpose,firstpayment,totalInterest,SubsequentLoanAmt,loanPlusInterestAmount);
    }

    @And("^Verify The ID Created In MongoDB for instance1$")
    public void verifyID_In_MongoDB1() throws ParseException{

        String CIN=   ExcelRead.hm1.get(tcname).get("UserName");
        String  principleLoanAmt=  ExcelRead.hm1.get(tcname).get("principleLoanAmt");
        String  apr=  ExcelRead.hm1.get(tcname).get("Validation4");
        String term = ExcelRead.hm1.get(tcname).get("Validation3");
        String LoanPurpose = ExcelRead.hm1.get(tcname).get("loanPurpose");
        String firstpayment = ExcelRead.hm1.get(tcname).get("firstpayment");
        String totalInterest= ExcelRead.hm1.get(tcname).get("totalInterest");
        String SubsequentLoanAmt = ExcelRead.hm1.get(tcname).get("SubsequentLoanAmt");
        String loanPlusInterestAmount = ExcelRead.hm1.get(tcname).get("Validation8");
         quotationID =  response.jsonPath().getInt("quotationId");

      mongoDB.VerifyIDAndCustomerInMongoDB(quotationID,CIN,principleLoanAmt,apr,term,LoanPurpose,firstpayment,totalInterest,SubsequentLoanAmt,loanPlusInterestAmount);
    }

    @Then("^The Response  having key1 and value validationCheck1$")
    public void verifyResponse(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }

    @Then("^The Response having the loanDecision as referred$")
    public void verifyAprovedStatus(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, contains(value1));
            }
        }
    }
    @Then("^The Response having the referralCode$")
    public void referralCode(){

        key1 = ExcelRead.hm1.get(tcname).get("Key2");
        value1 = ExcelRead.hm1.get(tcname).get("Value2");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, contains(value1));
            }
        }
    }
    @Then("^The Response having the loanDecisionReason$")
    public void loanDecisionReason(){

        key1 = ExcelRead.hm1.get(tcname).get("Key3");
        value1 = ExcelRead.hm1.get(tcname).get("Value3");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }

    @And("^The Response  having key2 and value validationCheck2$")
    public void verifyResponse1(){

        key2 = ExcelRead.hm1.get(tcname).get("Key2");
        value2 = ExcelRead.hm1.get(tcname).get("Validation2");
        if(!StringUtils.isEmpty(key2)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value2)) {
                json.body(key2, equalTo(Integer.parseInt(value2)));
            } else{
                json.body(key2, equalTo(value2));
            }
        }
    }

    @And("^The Response Time is Less Than 4 seconds$")
    public void verifyResponseTime(){

        json.time(lessThan(4000L));
    }

    @Then("^The Response having StatusCode as NO_FIRST_ACCOUNT$")
    public void The_Response_having_StatusCode_as_NO_FIRST_ACCOUNT(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
                logger.info("Response Got "+response.prettyPrint());
            }
        }


    }
    @Then("^The Response having StatusCode as Expected")
    public void The_Response_having_StatusCode_as_Expected() {

        String StatusCode = ExcelRead.hm1.get(tcname).get("StatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        String StatusCodejsonPathFromXL = ExcelRead.hm1.get(tcname).get("Key1");
        String ExpectedStatusCode = ExcelRead.hm1.get(tcname).get("Validation1");
        if (!StringUtils.isEmpty(StatusCodejsonPathFromXL)) {
            if (StringUtils.isNumeric(ExpectedStatusCode)) {
                json.body(StatusCodejsonPathFromXL, equalTo(Integer.parseInt(ExpectedStatusCode)));
            } else {
                json.body(StatusCodejsonPathFromXL, contains(ExpectedStatusCode));
            }
        }
    }
    @And("^The Response having the valid Description message$")
    public void The_Response_having_the_valid_Description_message(){

        key2 = ExcelRead.hm1.get(tcname).get("Key2");
        value2 = ExcelRead.hm1.get(tcname).get("Validation2");
        if(!StringUtils.isEmpty(key2)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value2)) {
                json.body(key2, equalTo(Integer.parseInt(value2)));
            } else{
                json.body(key2, equalTo(value2));
            }
        }
    }

    @And("^The Response having the valid Description message with code$")
    public void The_Response_having_the_valid_Description_message111(){

        key2 = ExcelRead.hm1.get(tcname).get("Key2");
        value2 = ExcelRead.hm1.get(tcname).get("Validation2");

                json.body(key2, contains(value2));

        }

    @And("^The Response having the valid Description message1$")
    public void The_Response_having_the_valid_Description_message1(){

        key2 = ExcelRead.hm1.get(tcname).get("Key2");
        value2 = ExcelRead.hm1.get(tcname).get("Value2");
        if(!StringUtils.isEmpty(key2)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value2)) {
                json.body(key2, equalTo(Integer.parseInt(value2)));
            } else{
                json.body(key2, contains(value2));
            }
        }
    }


    @Then("^The Response having StatusCode as UNDER_18")
    public void The_Response_having_StatusCode_as_UNDER_18() {

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if (!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else {
                json.body(key1, equalTo(value1));
            }
        }
    }


    @Then("^The Response having StatusCode as FAILED_STANDARD_CHECKS")
    public void The_Response_having_StatusCode_as_FAILED_STANDARD_CHECKS(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @Then("^Verify the AddressLineOne$")
    public void Verify_the_AddressLineOne(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }

    @Then("^Verify the AddressLineTwo$")
    public void Verify_the_AddressLineTwo(){

        key1 = ExcelRead.hm1.get(tcname).get("Key2");
        value1 = ExcelRead.hm1.get(tcname).get("Validation2");
        if(!StringUtils.isEmpty(key1)) {
            if(!StringUtils.isEmpty(value1)){
                //If the Key Value Presernt Check whether the response value is numaric
                if (StringUtils.isNumeric(value1)) {
                    json.body(key1, equalTo(Integer.parseInt(value1)));
                } else{
                    json.body(key1, equalTo(value1));
                }
            }
        }
    }
    @Then("^Verify the AddressLineThree$")
    public void Verify_the_AddressLineThree(){

        key1 = ExcelRead.hm1.get(tcname).get("Key3");
        value1 = ExcelRead.hm1.get(tcname).get("Validation3");
        if(!StringUtils.isEmpty(key1)) {
            if(!StringUtils.isEmpty(value1)){
                //If the Key Value Presernt Check whether the response value is numaric
                if (StringUtils.isNumeric(value1)) {
                    json.body(key1, equalTo(Integer.parseInt(value1)));
                } else{
                    json.body(key1, equalTo(value1));
                }
            }
        }
    }
    @Then("^Verify the AddressLineFour$")
    public void Verify_the_AddressLineFour(){

        key1 = ExcelRead.hm1.get(tcname).get("Key4");
        value1 = ExcelRead.hm1.get(tcname).get("Validation4");
        if(!StringUtils.isEmpty(key1)) {
            if(!StringUtils.isEmpty(value1)){
                //If the Key Value Presernt Check whether the response value is numaric
                if (StringUtils.isNumeric(value1)) {
                    json.body(key1, equalTo(Integer.parseInt(value1)));
                } else{
                    json.body(key1, equalTo(value1));
                }
            }
        }
    }
    @Then("^Verify the AddressLineFive$")
    public void Verify_the_AddressLineFive(){

        key1 = ExcelRead.hm1.get(tcname).get("Key5");
        value1 = ExcelRead.hm1.get(tcname).get("Validation5");
        if(!StringUtils.isEmpty(key1)) {
            if(!StringUtils.isEmpty(value1)){
                //If the Key Value Presernt Check whether the response value is numaric
                if (StringUtils.isNumeric(value1)) {
                    json.body(key1, equalTo(Integer.parseInt(value1)));
                } else{
                    json.body(key1, equalTo(value1));
                }
            }
        }
    }
    @Then("^Verify the AddressLineSix$")
    public void Verify_the_AddressLineSix(){

        key1 = ExcelRead.hm1.get(tcname).get("Key6");
        value1 = ExcelRead.hm1.get(tcname).get("Validation6");
        if(!StringUtils.isEmpty(key1)) {
            if(!StringUtils.isEmpty(value1)){
                //If the Key Value Presernt Check whether the response value is numaric
                if (StringUtils.isNumeric(value1)) {
                    json.body(key1, equalTo(Integer.parseInt(value1)));
                } else{
                    json.body(key1, equalTo(value1));
                }
            }
        }
    }

    @Then("^Verify the AddressPotalCode")
    public void Verify_the_AddressPotalCode(){

        key1 = ExcelRead.hm1.get(tcname).get("Key7");
        value1 = ExcelRead.hm1.get(tcname).get("Validation7");
        if(!StringUtils.isEmpty(key1)) {
            if(!StringUtils.isEmpty(value1)){
                //If the Key Value Presernt Check whether the response value is numaric
                if (StringUtils.isNumeric(value1)) {
                    json.body(key1, equalTo(Integer.parseInt(value1)));
                } else{
                    json.body(key1, equalTo(value1));
                }
            }
        }
    }



    @Then("^The Response having StatusCode as ELIGIBLE")
    public void The_Response_having_StatusCode_as_ELIGIBLE(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @Then("^The Response having Link to Quotation")
    public void The_Response_having_Link_to_Quotation(){

        key1 = ExcelRead.hm1.get(tcname).get("Key2");
        value1 = ExcelRead.hm1.get(tcname).get("Validation2");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @Then("^Verify the Number of First Accounts Displayed$")
    public void Verifythe_Number_of_First_Accounts_Displayed(){

        key1 = ExcelRead.hm1.get(tcname).get("NumberOfAccKey");
        value1 = ExcelRead.hm1.get(tcname).get("NumberOfAcc");

        if(!StringUtils.isEmpty(key1)) {
            //If Key Value is NA Don't do anything
            if(!key1.equalsIgnoreCase("NA")) {
                List list = response.jsonPath().getList("accounts.sortCode");
                int accountsize = list.size();
                Assert.assertEquals(Integer.parseInt(value1), accountsize);
            }
        }


    }
    @Then("^Verify The displayed Account Numbers$")
    public void Verify_The_displayed_Account_Numbers(){

        key1 = ExcelRead.hm1.get(tcname).get("AccountNumbers");
        key2 = ExcelRead.hm1.get(tcname).get("SortCodes");
        String[] acc  =key1.split(";");
        String[] sort  =key2.split(";");

        if(!StringUtils.isEmpty(key1)) {
if(!key1.equalsIgnoreCase("NA")){
            List sortcodelist =  response.jsonPath().getList("accounts.sortCode");
            List acclist =  response.jsonPath().getList("accounts.accountNumber");
            int accountssize = sortcodelist.size();

            for( int i=0;i<accountssize;i++){

                Assert.assertEquals(acc[i],acclist.get(i));
                Assert.assertEquals(sort[i],sortcodelist.get(i));

            }
}
        }
  }



//############################## Quotation Tests###################################


    @Given("^The User with personalLoan Eligibility \"([^\"]*)\"$")
    public void The_User_with_personalLoan_Eligibility(String arg1) throws IOException, ParseException,InterruptedException {

        xl.ReadExcel(XlPathName,"Quotation");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        String loanAmt = ExcelRead.hm1.get(tcname).get("LoanAmount");
        String repayPeriod = ExcelRead.hm1.get(tcname).get("RepaymentPeriod");
        String purpose = ExcelRead.hm1.get(tcname).get("Purpose");
        quotbody = "{ \"icmInstanceId\": \"1234567\", \"loanAmount\": { \"amount\": \""+loanAmt+"\", \"currencyCode\": \"GBP\"}, \"repaymentPeriod\":\""+ repayPeriod+"\", \"purpose\": \""+purpose+"\"}";
        System.out.println("The API Input is : :"+ quotbody);

        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        scenario.write("Input Used:"+quotbody);

        request = given()
                .config(RestAssuredConfig.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL)))
                .header("X-HSBC-Saml", string)
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","30")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234")
                .body(quotbody);

    }

    @Given("^The User with personalLoan Eligibility3 \"([^\"]*)\"$")
    public void The_User_with_personalLoan_Eligibility2(String arg1) throws IOException, ParseException,InterruptedException {

        xl.ReadExcel(XlPathName,"DPS");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);

        quotbody = "{\n" +
                "  \"requestHeader\": {\n" +
                "    \"dataVersion\": \"01\",\n" +
                "    \"userId\": \"00000000\",\n" +
                "    \"templateId\": \"STLT1102\",\n" +
                "    \"requestingApplicationId\": \"API\",\n" +
                "    \"primaryKeyType\": \"CUST\",\n" +
                "    \"primaryKey\": \"1904993338\",\n" +
                "    \"deliveryAddressKeyType\": \"0003\",\n" +
                "    \"numberOfAdditionalKeysSupplied\": 0,\n" +
                "    \"additionalKeyTable\": [],\n" +
                "    \"numberOfGenericDataGroupsPassed\": 0,\n" +
                "    \"totalLengthOfGenericData\": 0,\n" +
                "    \"totalLengthOfApplicationSpecificData\": 26,\n" +
                "    \"physicalWorkingSiteId\": \"000000\",\n" +
                "    \"sendingSiteId\": \"000000\",\n" +
                "    \"productItemCode\": \"UCHIC\",\n" +
                "    \"isBase64EncodingRequired\": true,\n" +
                "    \"optionalData\": \"CC02C000000100009VALUE9   \"\n" +
                "  }\n" +
                "}";
        System.out.println("The API Input is : :"+ quotbody);

        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        scenario.write("Input Used:"+quotbody);
        logger.info("X-HSBC-Session-Correlation-Id:"+String.valueOf(sessionID));
        logger.info("X-HSBC-Request-Correlation-Id:"+String.valueOf(RequestID));
        request = given()
                .config(RestAssuredConfig.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL)))
                .header("X-HSBC-Saml", string)
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","30")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234")
                .body(quotbody);

    }


    @Given("^The User with personalLoan Eligibility4 \"([^\"]*)\"$")
    public void The_User_with_personalLoan_Eligibility4(String arg1) throws IOException, ParseException,InterruptedException {

        xl.ReadExcel(XlPathName,"DPS1");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        quotbody = "{\n" +
                " \"requestHeader\": {\n" +
                "  \"dataVersion\": \"01\",\n" +
                "  \"templateId\": \"STLTG652\",\n" +
                "  \"userId\": \"0\",\n" +
                "  \"requestingApplicationId\": \"API\",\n" +
                "  \"primaryKeyType\": \"CUST\",\n" +
                "  \"primaryKey\": \"0977761983\",\n" +
                "  \"deliveryAddressKeyType\": \"0003\",\n" +
                "  \"numberOfAdditionalKeysSupplied\": 0,\n" +
                "  \"additionalKeyTable\": [],\n" +
                "  \"numberOfGenericDataGroupsPassed\": 0,\n" +
                "  \"totalLengthOfGenericData\": 0,\n" +
                "  \"totalLengthOfApplicationSpecificData\": 548,\n" +
                "  \"physicalWorkingSiteId\": \"000000\",\n" +
                "  \"sendingSiteId\": \"000000\",\n" +
                "  \"isSuppressStorage\": false,\n" +
                "  \"productItemCode\": \"UCHYJ\",\n" +
                "  \"isBase64EncodingRequired\": true,\n" +
                "  \"optionalData\": \"CA02C000000100001YCC02C0000001000044000CC02C00300010000236CC02C0060001000074464.36CC02C009000100006124.01CC02C01000010000905-Nov-17CC02C013000100006124.01CC02C01600010000514.50CC02C04100010000512.50CC02C04300010000235CC02C0440001000040.50CD02C000000100015mr tester testyCD02C0010001000131 street laneCD02C002000100006a townCD02C003000100006a cityCD02C004000100008t4e5 7erCD02C00500010000906-Oct-17CD02C00600010001440408712345678CD02C00700010000906-Oct-17CD02C008000100012d/12345678-9CD02C018000100014west yorkshireCD02C019000100000CD02C020000100000\",\n" +
                "  \"customerNumber\": \"0977761983\",\n" +
                "  \"applicationId\": \" \",\n" +
                "  \"applicantId\": \" \",\n" +
                "  \"businessLineCode\": \" \",\n" +
                "  \"accountNumber\": \" \"\n" +
                " }\n" +
                "} ";
        System.out.println("The API Input is : :"+ quotbody);

        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        scenario.write("Input Used:"+quotbody);
        logger.info("X-HSBC-Session-Correlation-Id:"+String.valueOf(sessionID));
        logger.info("X-HSBC-Request-Correlation-Id:"+String.valueOf(RequestID));
        request = given()
                .config(RestAssuredConfig.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL)))
                .header("X-HSBC-Saml", string)
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","30")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234")
                .body(quotbody);

    }
     @When("^a user retrieves the Quotation with some amount and Repayment Shedule info$")
    public void a_user_retrieves_the_eligible_Info1sdas(){

         String environment1 = System.getProperty("environment");
         String pla_version=System.getProperty("pla_version");
         String host=prop.getProperty("ServerName").replace("{version}",pla_version);
         String QuotationEAPI=String.format(host,environment1).concat("/personal-loans/quotations");
         scenario.write(QuotationEAPI);
        logger.info(QuotationEAPI);
        response = request.when().post(QuotationEAPI);
        quotationID = response.getBody().jsonPath().get("quotationId");
        System.out.println("response: " + response.prettyPrint());
    }

    @When("^The user retrieves Decision$")
    public void a_user_retrieves_the_Decisin(){
        String environment1 = System.getProperty("environment");
        String pla_version=System.getProperty("pla_version");
        String host=prop.getProperty("ServerName").replace("{version}",pla_version);
        String DecisionEAPI=String.format(host,environment1).concat("/personal-loans/decision");
        scenario.write(DecisionEAPI);
        logger.info(DecisionEAPI);
        response = request.when().post(DecisionEAPI);
        scenario.write("response: " + response.prettyPrint());
    }
    @Then("^The Response should display purpose of loan$")
    public void The_Response_should_display_purpose_of_loan(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @Then("^The Response should display Valid Error$")
    public void The_Response_should_display_Valid_Error(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
                json.body(key1, equalTo(value1));
        }
    }
    @Then("^The Response should display Valid Error1$")
    public void The_Response_should_display_Valid_Error1(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
            json.body(key1, contains(value1));
        }
    }
    @Then("^The Response should Contains valid message$")
    public void The_Response_should_Contains_valid_message(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        if(!StringUtils.isEmpty(key1)) {
            json.body(key1,contains(value1));
        }
    }
    @And("^The Response should display loanAmount$")
    public void The_Response_should_display_loanAmount(){

        key1 = ExcelRead.hm1.get(tcname).get("Key2");
        value1 = ExcelRead.hm1.get(tcname).get("Validation2");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @And("^Verify The Minium Loan Amount$")
    public void Verify_The_Minium_Loan_Amount() {

        key1 = ExcelRead.hm1.get(tcname).get("Key4");
        value1 = ExcelRead.hm1.get(tcname).get("Validation4");
        if (!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else {
                json.body(key1, equalTo(value1));
            }
        }
    }
        @And("^Verify The Maximum Loan Amount$")
        public void Verify_The_Maximum_Loan_Amount(){

            key1 = ExcelRead.hm1.get(tcname).get("Key5");
            value1 = ExcelRead.hm1.get(tcname).get("Validation5");
            if(!StringUtils.isEmpty(key1)) {
                //If the Key Value Presernt Check whether the response value is numaric
                if (StringUtils.isNumeric(value1)) {
                    json.body(key1, equalTo(Integer.parseInt(value1)));
                } else{
                    json.body(key1, equalTo(value1));
                }
            }
    }

    @And("^Verify The Minimum Repayment Period$")
    public void Verify_The_Minimum_Repayment_Period(){

        key1 = ExcelRead.hm1.get(tcname).get("Key6");
        value1 = ExcelRead.hm1.get(tcname).get("Validation6");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }
    @And("^Verify The maximum Repayment Period$")
    public void Verify_The_maximum_Repayment_Period(){

        key1 = ExcelRead.hm1.get(tcname).get("Key7");
        value1 = ExcelRead.hm1.get(tcname).get("Validation7");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }
    @And("^Verify The eligibleLoanAmount$")
    public void Verify_The_eligibleLoanAmount(){
        key1 = ExcelRead.hm1.get(tcname).get("Key8");
          scenario.write("Amount from SAPI :"+PLAPreAuthLimit);
          scenario.write("Amount from EAPI :"+response.getBody().jsonPath().getString(key1));
          json.body(key1, equalTo(Integer.parseInt(PLAPreAuthLimit)));
    }
    @And("^The Response Having the Purpose of Loan Options$")
    public void The_Response_Having_the_Purpose_of_Loan_Options(){

        key1 = ExcelRead.hm1.get(tcname).get("Key3");

         String loanoptions[] = key1.split(";");
        List list =  response.jsonPath().getList("loanOptions.purposes.title");
        System.out.println("Response Size:"+list.size());
        for (int i=0;i<list.size();i++){
            Assert.assertThat(list.get(i).toString().trim(), equalTo(loanoptions[i]));
        }

    }
    @And("^The Response should display repaymentPeriod")
    public void The_Response_should_display_repaymentPeriod(){

        key1 = ExcelRead.hm1.get(tcname).get("Key3");
        value1 = ExcelRead.hm1.get(tcname).get("Validation3");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }
    @And("^The Response should display interestAPR")
    public void The_Response_should_display_interestAPR(){

        key1 = ExcelRead.hm1.get(tcname).get("Key4");
        value1 = ExcelRead.hm1.get(tcname).get("Validation4");
        if(!StringUtils.isEmpty(key1)) {
            json.body(key1,closeTo(BigDecimal.valueOf(Double.parseDouble(value1)),new BigDecimal("1E-20")));

        }
    }

    @And("^The Response should display interestAnnual")
    public void The_Response_should_display_interestAnnual(){

        key1 = ExcelRead.hm1.get(tcname).get("Key5");
        value1 = ExcelRead.hm1.get(tcname).get("Validation5");
        if(!StringUtils.isEmpty(key1)) {
            json.body(key1,closeTo(BigDecimal.valueOf(Double.parseDouble(value1)),new BigDecimal("1E-20")));
        }
    }

    @And("^The Response should display initialPayment")
    public void The_Response_should_display_initialPayment(){

        key1 = ExcelRead.hm1.get(tcname).get("Key6");
        value1 = ExcelRead.hm1.get(tcname).get("Validation6");
        if(!StringUtils.isEmpty(key1)) {

            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }

    @And("^The Response should display regularPayment")
    public void The_Response_should_display_regularPayment(){

        key1 = ExcelRead.hm1.get(tcname).get("Key7");
        value1 = ExcelRead.hm1.get(tcname).get("Validation7");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }
    @And("^The Response should display totalRepayableAmount")
    public void The_Response_should_display_totalRepayableAmount(){

        key1 = ExcelRead.hm1.get(tcname).get("Key8");
        value1 = ExcelRead.hm1.get(tcname).get("Validation8");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }
    }

    @And("^The Intrest rate for the range 1000 to 2450$")
    public void The_Intrest_rate_for_the_range_1000_to_2450(){

        key1 = ExcelRead.hm1.get(tcname).get("Key2");

        value1 = ExcelRead.hm1.get(tcname).get("Validation2");


        List list =  response.jsonPath().getList("loanOptions.interestRates.minimumLoanAmount.amount");
        List maxlist =  response.jsonPath().getList("loanOptions.interestRates.maximumLoanAmount.amount");
        List list1 =  response.jsonPath().getList("loanOptions.interestRates.rate");

        if(list.get(0).toString().trim().equalsIgnoreCase("100000") && maxlist.get(0).toString().trim().equalsIgnoreCase("245000")){
            Assert.assertThat(list1.get(0).toString().trim(), equalToIgnoringCase(value1));
        }else {
            Assert.assertTrue("The Loan Amount Values not Matched",false);
        }
    }
    @And("^The Intrest rate for the range 2500 to 4950")
    public void The_Intrest_rate_for_the_range_2500_to_4950(){

        key1 = ExcelRead.hm1.get(tcname).get("Key3");
        value1 = ExcelRead.hm1.get(tcname).get("Validation3");


        List list =  response.jsonPath().getList("loanOptions.interestRates.minimumLoanAmount.amount");
        List maxlist =  response.jsonPath().getList("loanOptions.interestRates.maximumLoanAmount.amount");
        List list1 =  response.jsonPath().getList("loanOptions.interestRates.rate");

        if(list.get(1).toString().trim().equalsIgnoreCase("250000") && maxlist.get(1).toString().trim().equalsIgnoreCase("495000")){
            Assert.assertThat(list1.get(1).toString().trim(), equalToIgnoringCase(value1));
        }else {
            Assert.assertTrue("The Loan Amount Values not Matched",false);
        }
    }

    @And("^The Intrest rate for the range 5000 to 6950")
    public void The_Intrest_rate_for_the_range_5000_to_6950(){

        key1 = ExcelRead.hm1.get(tcname).get("Key4");
        value1 = ExcelRead.hm1.get(tcname).get("Validation4");


        List list =  response.jsonPath().getList("loanOptions.interestRates.minimumLoanAmount.amount");
        List maxlist =  response.jsonPath().getList("loanOptions.interestRates.maximumLoanAmount.amount");
        List list1 =  response.jsonPath().getList("loanOptions.interestRates.rate");

        if(list.get(2).toString().trim().equalsIgnoreCase("500000") && maxlist.get(2).toString().trim().equalsIgnoreCase("695000")){
            Assert.assertThat(list1.get(2).toString().trim(), equalToIgnoringCase(value1));
        }else {
            Assert.assertTrue("The Loan Amount Values not Matched",false);
        }
    }


    @And("^The Intrest rate for the range 7000 to 15000")
    public void The_Intrest_rate_for_the_range_7000_to_15000(){

        key1 = ExcelRead.hm1.get(tcname).get("Key5");
        value1 = ExcelRead.hm1.get(tcname).get("Validation5");


        List list =  response.jsonPath().getList("loanOptions.interestRates.minimumLoanAmount.amount");
        List maxlist =  response.jsonPath().getList("loanOptions.interestRates.maximumLoanAmount.amount");
        List list1 =  response.jsonPath().getList("loanOptions.interestRates.rate");

        if(list.get(3).toString().trim().equalsIgnoreCase("700000") && maxlist.get(3).toString().trim().equalsIgnoreCase("1500000")){
            Assert.assertThat(list1.get(3).toString().trim(), equalToIgnoringCase(value1));
        }else {
            Assert.assertTrue("The Loan Amount Values not Matched",false);
        }
    }

    @And("^The Intrest rate for the range 15050 to 30000")
    public void The_Intrest_rate_for_the_range_15050_to_30000(){

        key1 = ExcelRead.hm1.get(tcname).get("Key6");
        value1 = ExcelRead.hm1.get(tcname).get("Validation6");


        List list =  response.jsonPath().getList("loanOptions.interestRates.minimumLoanAmount.amount");
        List maxlist =  response.jsonPath().getList("loanOptions.interestRates.maximumLoanAmount.amount");
        List list1 =  response.jsonPath().getList("loanOptions.interestRates.rate");

        if(list.get(4).toString().trim().equalsIgnoreCase("1505000") && maxlist.get(4).toString().trim().equalsIgnoreCase("3000000")){
            Assert.assertThat(list1.get(4).toString().trim(), equalToIgnoringCase(value1));
        }else {
            Assert.assertTrue("The Loan Amount Values not Matched",false);
        }
    }

    @And("^The Intrest rate for the range 30050 to 50000")
    public void The_Intrest_rate_for_the_range_30050_to_50000() {

        key1 = ExcelRead.hm1.get(tcname).get("Key7");
        value1 = ExcelRead.hm1.get(tcname).get("Validation7");


        List list = response.jsonPath().getList("loanOptions.interestRates.minimumLoanAmount.amount");
        List maxlist = response.jsonPath().getList("loanOptions.interestRates.maximumLoanAmount.amount");
        List list1 = response.jsonPath().getList("loanOptions.interestRates.rate");

        if (list.get(5).toString().trim().equalsIgnoreCase("3005000") && maxlist.get(5).toString().trim().equalsIgnoreCase("5000000")) {
            Assert.assertThat(list1.get(5).toString().trim(), equalToIgnoringCase(value1));
        } else {
            Assert.assertTrue("The Loan Amount Values not Matched", false);
        }
    }


//################################ Customer Details Review #################################################################


    @When("^the user retrieves the Customer Personal Information$")
    public void the_user_retrieves_the_Customer_Personal_Information(){
        String environment1 = System.getProperty("environment");
        String GetPersonalDetailsEAPI=String.format(prop.getProperty("ServerName"),environment1).concat("/personal-loans/customer-details?icmInstanceId=123456");
        //APIUrl  = ExcelRead.hm1.get(tcname).get("EAPI");
        response = request.when().get(GetPersonalDetailsEAPI);
        System.out.println("response: " + response.prettyPrint());

    }

    @When("^Verify the Customer employmentStatus is UNEMPLOYED$")
    public void Verify_the_Customer_employmentStatus_is_UNEMPLOYED(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @When("^Verify the Customer employmentStatus is SELFEMPLOYED$")
    public void Verify_the_Customer_employmentStatus_is_SELFEMPLOYED(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @When("^Verify the Customer employmentStatus is DIRECTOR")
    public void Verify_the_Customer_employmentStatus_is_DIRECTOR$(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @When("^Verify the Customer employmentStatus is EMPLOYED")
    public void Verify_the_Customer_employmentStatus_is_EMPLOYED$(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @When("^Verify the Customer employmentStatus is Contractor")
    public void Verify_the_Customer_employmentStatus_is_Contractor$(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @When("^Verify the Customer employmentStatus is STUDENT")
    public void Verify_the_Customer_employmentStatus_is_STUDENT$(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @When("^Verify the Customer employmentStatus is OTHERS")
    public void Verify_the_Customer_employmentStatus_is_OTHERS$(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }
    @When("^Verify the Customer residential Status does not display$")
    public void Verify_the_Customer_residential_Status_does_not_display(){

        json.body("$", not(hasKey("residentialStatus")));
        //json.body("$", not(hasKey("employmentStatus")));

    }

    @When("^Verify customer gross Annual Income$")
    public void test2(){

        key1 = ExcelRead.hm1.get(tcname).get("Key2");
        value1 = ExcelRead.hm1.get(tcname).get("Value2");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }

//###################################Confirm Affordability#################################################
    //#########################################################################################################

    @Given("^The User with QuotationID and Other Details As \"([^\"]*)\"$")
    public void The_User_with_QuotationID_and_Other_Details_As(String arg1) throws IOException, ParseException,InterruptedException {

        xl.ReadExcel(XlPathName,"ConfirmAffordability");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);

        String loanAmt = ExcelRead.hm1.get(tcname).get("LoanAmount");
        String employmentStatus = ExcelRead.hm1.get(tcname).get("employmentStatus");
        String residentialStatus = ExcelRead.hm1.get(tcname).get("residentialStatus");
        String ukResidentStatus = ExcelRead.hm1.get(tcname).get("ukResidentStatus");

       quotbody =   "{\n" +
               "  \"icmInstanceId\": 6445757,\n" +
               "  \"quotationId\": "+quotationID+",\n" +
               "  \"employmentStatus\": \""+employmentStatus+"\",\n" +
               "  \"residentialStatus\": \""+residentialStatus+"\",\n" +
               "  \"grossAnnualIncome\": {\n" +
               "      \"amount\": "+loanAmt+",\n" +
               "      \"currencyCode\": \"GBP\"\n" +
               "  },\n" +
               "  \"ukResidentStatus\": \""+ukResidentStatus+"\"\n" +
               "}";


    }

    @When("^the user retrieves the Quotation with some amount and Repayment Shedule info$")
    public void the_user_retrieves_the_Quotation_with_some_amount_and_Repayment_Shedule_info(){
        String environment1 = System.getProperty("environment");
        String pla_version=System.getProperty("pla_version");
        String host=prop.getProperty("ServerName").replace("{version}",pla_version);
        String ApplySummaryEAPI=String.format(host,environment1).concat("/personal-loans/apply-summary");
        response = request.when().post(ApplySummaryEAPI);
        System.out.println("response: " + response.prettyPrint());

    }

    @Then("^Verify the employmentStatus$")
    public void Verify_the_employmentStatus(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }

    @Then("^Verify the grossAnnualIncome$")
    public void Verify_the_grossAnnualIncome(){

        key1 = ExcelRead.hm1.get(tcname).get("Key2");
        value1 = ExcelRead.hm1.get(tcname).get("Value2");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }

    @Then("^Verify the residentialStatus$")
    public void Verify_the_residentialStatus$(){

        key1 = ExcelRead.hm1.get(tcname).get("Key3");
        value1 = ExcelRead.hm1.get(tcname).get("Value3");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }

    @Then("^Verify the ukResidentStatus$")
    public void Verify_the_ukResidentStatus$(){

        key1 = ExcelRead.hm1.get(tcname).get("Key4");
        value1 = ExcelRead.hm1.get(tcname).get("Value4");
        if(!StringUtils.isEmpty(key1)) {
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, equalTo(Integer.parseInt(value1)));
            } else{
                json.body(key1, equalTo(value1));
            }
        }

    }

    @When("^The SAPI Response Display Error Code for \"([^\"]*)\"$")
    public void SAPIResponse(String arg1) throws IOException, ParseException,Exception {
        xl.ReadExcel(XlPathName,"ErrorCodes");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
      newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);

        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id","532532")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header("X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("Content-Type","application/json")
                .header("X-HSBC-Src-Device-Id","1234");
        APIUrl  = ExcelRead.hm1.get(tcname).get("SAPI");
        scenario.write("SAPI URL:"+APIUrl);
        response = request.when().get(APIUrl);
        System.out.println("response: " + response.prettyPrint());
        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        key2 = ExcelRead.hm1.get(tcname).get("Key1");
        value2 = ExcelRead.hm1.get(tcname).get("Value1");
        SAPIErrorCode =response.getBody().jsonPath().getString(key1);
        String StatusCode=   ExcelRead.hm1.get(tcname).get("StatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        json.body(key1, contains(value1));
        //json.body(key2, contains(value2));
        scenario.write("SAPI error Code:"+SAPIErrorCode);

    }

    @Then("^The status code is 200 or 400 or 404 or 500 or 401$")
    public void verifyStatus2(){
        String StatusCode=   ExcelRead.hm1.get(tcname).get("StatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        scenario.write("SAPI Response Status Code:"+APIUrl);
    }
    @When("^The SAPI1 Response Display Error Code for \"([^\"]*)\"$")
    public void SAPIResponse1(String arg1) throws IOException, ParseException,Exception {
        xl.ReadExcel(XlPathName,"ErrorCodes");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);

        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");
        APIUrl  = ExcelRead.hm1.get(tcname).get("SAPI1");
        scenario.write("SAPI URL:"+APIUrl);
        response = request.when().get(APIUrl);
        System.out.println("response: " + response.prettyPrint());
        key1 = ExcelRead.hm1.get(tcname).get("Key3");
        value1 = ExcelRead.hm1.get(tcname).get("Value3");
        key2 = ExcelRead.hm1.get(tcname).get("Key4");
        value2 = ExcelRead.hm1.get(tcname).get("Value4");
        SAPIErrorCode =response.getBody().jsonPath().getString(key1);
        String StatusCode=   ExcelRead.hm1.get(tcname).get("SAPI1StatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        json.body(key1, contains(value1));
        //json.body(key2, contains(value2));
        scenario.write("SAPI-1 error Code:"+SAPIErrorCode);

    }

    @When("^The PAPI Response Should Display Error Code for \"([^\"]*)\"$")
    public void PAPIResponse1(String arg1) throws IOException, ParseException, Exception {
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        xl.ReadExcel(XlPathName,"ErrorCodes");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");

        APIUrl  = ExcelRead.hm1.get(tcname).get("PAPI");
        scenario.write("PAPI URL:"+APIUrl);
        response = request.when().get(APIUrl);
        System.out.println("response: " + response.prettyPrint());
        scenario.write("PAPI Response:"+response.prettyPrint());
        key1 = ExcelRead.hm1.get(tcname).get("Key5");
        value1 = ExcelRead.hm1.get(tcname).get("Value5");
        // key2 = ExcelRead.hm1.get(tcname).get("Key6");
        //value2 = ExcelRead.hm1.get(tcname).get("Value6");
        String StatusCode=   ExcelRead.hm1.get(tcname).get("PAPIStatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        String[] x = value1.split(",");
        System.out.println("ARRAY Length:"+x.length);
        if(x.length>1){

            json.body("status.code[0]", equalTo(x[0].trim()));
            json.body("status.code[1]", equalTo(x[1].trim()));
        }else{
            json.body(key1, contains(value1));
            //json.body(key2, contains(value2));
        }

       // SAPIErrorCode =response.getBody().jsonPath().getString(key1);




    }

    @When("^The EAPI Response Should Display Error code for \"([^\"]*)\"$")
    public void EAPIResponse1(String arg1) throws IOException, ParseException {

        int sessionID= RandomGen();
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);

        xl.ReadExcel(XlPathName,"ErrorCodes");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");
        APIUrl  = ExcelRead.hm1.get(tcname).get("EAPI");
        scenario.write("EAPI URL:"+APIUrl);
        response = request.when().get(APIUrl);
        System.out.println("response: " + response.prettyPrint());
        scenario.write("EAPI Response:"+response.prettyPrint());
        key1 = ExcelRead.hm1.get(tcname).get("Key7");
        value1 = ExcelRead.hm1.get(tcname).get("Value7");
        key2 = ExcelRead.hm1.get(tcname).get("Key8");
        value2 = ExcelRead.hm1.get(tcname).get("Value8");
        String StatusCode=   ExcelRead.hm1.get(tcname).get("EAPIStatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        //json.body(key1, contains(value1));

        json.body(key2,  equalTo(value2));

    }

    @When("^The EAPI1 Response Should Display Error code for \"([^\"]*)\"$")
    public void EAPI1Response1(String arg1) throws IOException, ParseException,Exception {
        xl.ReadExcel(XlPathName,"ErrorCodes");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);

        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");
        APIUrl  = ExcelRead.hm1.get(tcname).get("EAPI");
        scenario.write("EAPI URL:"+APIUrl);
        response = request.when().get(APIUrl);
        System.out.println("response: " + response.prettyPrint());
        scenario.write("EAPI Response:"+response.prettyPrint());
        key1 = ExcelRead.hm1.get(tcname).get("Key7");
        value1 = ExcelRead.hm1.get(tcname).get("Value7");
        key2 = ExcelRead.hm1.get(tcname).get("Key8");
        value2 = ExcelRead.hm1.get(tcname).get("Value8");
        String StatusCode=   ExcelRead.hm1.get(tcname).get("EAPIStatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        //json.body(key1, contains(value1));

        json.body(key2,  contains(value2));

    }

    @When("^The Quotation SAPI Response Display Error Code for \"([^\"]*)\"$")
    public void Quotation2(String arg1)throws IOException, ParseException,Exception{

        xl.ReadExcel(XlPathName,"ErrorCodes");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
       String QsapiBoby= "{\n" +
               "  \"icmEventId\": \"123456\",\n" +
               "  \"loanAmount\": {\n" +
               "    \"amount\": \"8000\",\n" +
               "    \"currencyCode\": \"GBP\"\n" +
               "  },\n" +
               "  \"repaymentPeriod\": 36,\n" +
               "  \"purpose\": \"house\"\n" +
               "}";
        scenario.write("The Personal Loan Quotation SAPI Request data is :"+QsapiBoby);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234")
                .body(QsapiBoby);
        APIUrl  = ExcelRead.hm1.get(tcname).get("SAPI");
        scenario.write("SAPI URL:"+APIUrl);
        response = request.when().post(APIUrl.trim());
        System.out.println("response: " + response.prettyPrint());
        scenario.write("SAPI Response:"+response.prettyPrint());
        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Value1");
        key2 = ExcelRead.hm1.get(tcname).get("Key2");
        value2 = ExcelRead.hm1.get(tcname).get("Value2");
        String StatusCode=   ExcelRead.hm1.get(tcname).get("StatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        json.body(key1, contains(value1));

        //json.body(key2,  contains(value2));
    }

    @When("^The Quotation PAPI Response Should Display Error Code for \"([^\"]*)\"$")
    public void Quotation3(String arg1) throws IOException, ParseException,Exception{
        String QsapiBoby1= "{\"icmInstanceId\": 123456,\"loanAmount\": {\"amount\": \"1500000\",\"currencyCode\": \"GBP\"},\"repaymentPeriod\": 12,\"purpose\": \"car\"}\n";
        scenario.write("The Personal Loan Quotation PAPI Request data is :"+QsapiBoby1);

        xl.ReadExcel(XlPathName,"ErrorCodes");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
       newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        request = given()
                .header("X-HSBC-Saml", string)
                .header("Content-Type","application/json")
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","30")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234")
                .body(QsapiBoby1);
        APIUrl  = ExcelRead.hm1.get(tcname).get("PAPI");
        scenario.write("EAPI URL:"+APIUrl);
        response = request.when().post(APIUrl);
        System.out.println("response: " + response.prettyPrint());
        scenario.write("The Personal Loan Quotation PAPI Response  is :"+response.prettyPrint());
        key1 = ExcelRead.hm1.get(tcname).get("Key5");
        value1 = ExcelRead.hm1.get(tcname).get("Value5");
        key2 = ExcelRead.hm1.get(tcname).get("Key6");
        value2 = ExcelRead.hm1.get(tcname).get("Value6");
        String StatusCode=   ExcelRead.hm1.get(tcname).get("PAPIStatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        json.body(key1, contains(value1));
    }

    @When("^The Quotation EAPI Response Should Display Error code for \"([^\"]*)\\$")
    public void Quotation4(String arg1)throws IOException, ParseException,Exception{
        String QsapiBoby= "{\"icmInstanceId\": 123456,\"loanAmount\": { \"amount\": 1500000,\"currencyCode\": \"GBP\"},\"repaymentPeriod\": 12,\"purpose\": \"car\"}\n";
        scenario.write("The Personal Loan Quotation EAPI Request data is :"+QsapiBoby);

        xl.ReadExcel(XlPathName,"ErrorCodes");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");
        APIUrl  = ExcelRead.hm1.get(tcname).get("EAPI");
        scenario.write("EAPI URL:"+APIUrl);
        response = request.when().post(APIUrl);
        System.out.println("response: " + response.prettyPrint());
        scenario.write("The Personal Loan Quotation PAPI Response  is :"+response.prettyPrint());
        key1 = ExcelRead.hm1.get(tcname).get("Key7");
        value1 = ExcelRead.hm1.get(tcname).get("Value7");
        key2 = ExcelRead.hm1.get(tcname).get("Key8");
        value2 = ExcelRead.hm1.get(tcname).get("Value8");
        String StatusCode=   ExcelRead.hm1.get(tcname).get("EAPIStatusCode");
        json = response.then().statusCode(Integer.parseInt(StatusCode));
        json.body(key1, contains(value1));
        //json.body(key2, contains(value2));
    }

    @Given("^The User with in valid post Quotation \"([^\"]*)\"$")
    public void The_User_with_personalLoan_Eligibility1(String arg1) throws IOException, ParseException,InterruptedException {

        xl.ReadExcel(XlPathName,"GetAndStoreQuote");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        String loanAmt = ExcelRead.hm1.get(tcname).get("LoanAmount");
        String repayPeriod = ExcelRead.hm1.get(tcname).get("RepaymentPeriod");
        String purpose = ExcelRead.hm1.get(tcname).get("Purpose");
        quotbody = "{ \"icmInstanceId\": \"1234567\", \"loanAmount\": { \"amount\": \""+loanAmt+"\", \"currencyCode\": \"GBP\"}, \"repaymentPeriod\":\""+ repayPeriod+"\", \"purpose\": \""+purpose+"\"}";
        System.out.println("The API Input is : :"+ quotbody);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        scenario.write("The API Input is : :"+ quotbody);
        request = given()
                .config(RestAssuredConfig.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL)))
                .header("X-HSBC-Saml", string)
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","65")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234")
                .body(quotbody);
    }

    @Given("^The User with personalLoan Eligibility and Quotation \"([^\"]*)\"$")
    public void The_User_with_personalLoan_Eligibility_Quotation(String arg1) throws IOException, ParseException,InterruptedException {

        xl.ReadExcel(XlPathName,"GetAndStoreQuote");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        String loanAmt = ExcelRead.hm1.get(tcname).get("LoanAmount");
        String repayPeriod = ExcelRead.hm1.get(tcname).get("RepaymentPeriod");
        String purpose = ExcelRead.hm1.get(tcname).get("Purpose");
        quotbody = "{ \"icmInstanceId\": \"1234567\", \"loanAmount\": { \"amount\": \""+loanAmt+"\", \"currencyCode\": \"GBP\"}, \"repaymentPeriod\":\""+ repayPeriod+"\", \"purpose\": \""+purpose+"\"}";
        System.out.println("The API Input is : :"+ quotbody);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        scenario.write("The API Input is : :"+ quotbody);

        request = given()
                .config(RestAssuredConfig.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL)))
                .header("X-HSBC-Saml", string)
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234")
                .body(quotbody);
    }



//##################################Quotation GET###############################################################

    @Given("^The User with personalLoan Eligibility and Quotation1 \"([^\"]*)\"$")
    public void The_User_with_personalLoan_Eligibility_Quotation1(String arg1) throws IOException, ParseException,InterruptedException {

        xl.ReadExcel(XlPathName,"GetAndStoreQuote");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);
        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");

    }
    @When("^a user retrieves the Quotation from DB$")
    public void a_user_retrieves_the_quotes(){
        APIUrl  = ExcelRead.hm1.get(tcname).get("EAPI");
        APIUrl  =APIUrl.replace("id",String.valueOf(QIdInResponse));
        System.out.println("API :"+APIUrl);
        response = request.when().get(APIUrl);
        System.out.println("response: " + response.prettyPrint());
    }

    @When("^a user retrieves the Quotation from DB1$")
    public void a_user_retrieves_the_quotes1(){
        APIUrl  = ExcelRead.hm1.get(tcname).get("EAPI");
        APIUrl  =APIUrl.replace("id","11111");
        System.out.println("API :"+APIUrl);
        response = request.when().get(APIUrl);
        System.out.println("response: " + response.prettyPrint());
    }

    @Then("^The Message should be valid code and message from EAPI$")
    public void The_Message_should_be_valid_code_and_message_from_EAPI(){

        key1 = ExcelRead.hm1.get(tcname).get("Key1");
        value1 = ExcelRead.hm1.get(tcname).get("Validation1");
        key2 = ExcelRead.hm1.get(tcname).get("Key2");
        value2 = ExcelRead.hm1.get(tcname).get("Validation2");
        if(!StringUtils.isEmpty(key1)) {
            //If the Key Value Presernt Check whether the response value is numaric
            if (StringUtils.isNumeric(value1)) {
                json.body(key1, contains(Integer.parseInt(value1)));
            } else{
                json.body(key1, contains(value1));
            }
            if (StringUtils.isNumeric(value2)) {
                json.body(key2, contains(Integer.parseInt(value2)));
            } else{
                json.body(key2, contains(value2));
            }
        }
    }




    @Given("^The User with stub \"([^\"]*)\"$")
    public void Stub(String arg1) throws IOException, ParseException,InterruptedException {

        xl.ReadExcel(XlPathName,"Eligibility");
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        System.out.println("UserName:"+un);



        newSamlToken = saml.GenerateSamlToken(un);
        SamlToken=   newSamlToken.trim();
        String string = String.format("%s", SamlToken);
        System.out.println(string);
        int sessionID= RandomGen();
        Thread.sleep(1000);
        int RequestID= RandomGen();
        scenario.write("Secssion ID:"+sessionID);
        scenario.write("Request ID:"+RequestID);
        request = given()
                .header("X-HSBC-Saml", string)
                .header("X-HSBC-IP-Id","145.26.54.24")
                .header("X-HSBC-Channel-Id","FDPIB")
                .header("X-HSBC-Chnl-CountryCode","GB")
                .header("X-HSBC-Session-Correlation-Id",String.valueOf(sessionID))
                .header("X-HSBC-Request-Correlation-Id",String.valueOf(RequestID))
                .header("X-HSBC-Locale","GB")
                .header(" X-HSBC-Src-UserAgent","Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id","C12345678")
                .header("X-HSBC-CAM-Level","40")
                .header("X-HSBC-Chnl-Group-Member","HBEU")
                .header("X-HSBC-Src-Device-Id","1234");

    }



    @Given("^The User with Some Post Affordability Conditions as \"([^\"]*)\" from the module \"([^\"]*)\"$")
    public void Check_Affordability(String arg1,String XLSheet) throws Exception{

        xl.ReadExcel(XlPathName,XLSheet);
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        String canAffordRepayments = ExcelRead.hm1.get(tcname).get("canAffordRepayments");
        String sustainabilityChangeInCircumstance = ExcelRead.hm1.get(tcname).get("sustainabilityChangeInCircumstance");
        if(System.getProperty("environment").equals("SCT")){
            String quto = ExcelRead.hm1.get(tcname).get("QuotationID");
            quotationID=  Integer.parseInt(quto);
        }
        String affordabilityInputBody =  prop.getProperty("Affordability")
                .replace("varicmInstanceID",String.valueOf(ICMInstanceID))
                .replace("varQutationId",String.valueOf(quotationID))
                .replace("varConfirmAffordability",canAffordRepayments)
                .replace("varChangeInCircumstance",sustainabilityChangeInCircumstance);

        request = PostMethodHeaders(un,affordabilityInputBody);
        scenario.write("The API Input is : :"+ affordabilityInputBody);

    }
    @Given("^The User with Some Post Quotation Conditions as \"([^\"]*)\" from the module \"([^\"]*)\"$")
    public void Check_Quotation(String arg1,String XLSheet) throws Exception{

        xl.ReadExcel(XlPathName,XLSheet);
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        String loanAmt = ExcelRead.hm1.get(tcname).get("LoanAmount");
        String repayPeriod = ExcelRead.hm1.get(tcname).get("RepaymentPeriod");
        String purpose = ExcelRead.hm1.get(tcname).get("Purpose");

        String QuotationInputBody =  prop.getProperty("Quotation")
                .replace("ICMXXXX",String.valueOf(ICMInstanceID))
                .replace("AmountXXXX",loanAmt)
                .replace("PeriodXXXX",repayPeriod)
                .replace("purposeXXXX",purpose);

        request = PostMethodHeaders(un,QuotationInputBody);
        System.out.println("The API Input is : :"+ QuotationInputBody);

    }

    @Given("^The User with Some Post Apply Summary Conditions as \"([^\"]*)\" from the module \"([^\"]*)\"$")
    public void Check_ApplySummary(String arg1,String XLSheet) throws Exception{

        xl.ReadExcel(XlPathName,XLSheet);
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        String loanAmt = ExcelRead.hm1.get(tcname).get("LoanAmount");
        String employmentStatus = ExcelRead.hm1.get(tcname).get("employmentStatus");
        String residentialStatus = ExcelRead.hm1.get(tcname).get("residentialStatus");
        String ukResidentStatus = ExcelRead.hm1.get(tcname).get("ukResidentStatus");

        if(System.getProperty("environment").equals("SCT")){
            String quotationID1 =  ExcelRead.hm1.get(tcname).get("QuotationID");
            quotationID = Integer.parseInt(quotationID1) ;
        }
        String ApplySummaryInputBody =  prop.getProperty("applySummary")

                .replace("icmXXXX",String.valueOf(ICMInstanceID))
                .replace("quotXXXX",String.valueOf(quotationID))
                .replace("employXXXX",employmentStatus)
                .replace("residentXXXX",residentialStatus)
                .replace("grossXXXX",loanAmt)
                .replace("isUKXXX",ukResidentStatus);

        request = PostMethodHeaders(un,ApplySummaryInputBody);
        System.out.println("The API Input is : :"+ ApplySummaryInputBody);

    }

    @Given("^The User with Some Get Conditions as \"([^\"]*)\" from the module \"([^\"]*)\"$")
    public void getResponseRequest(String arg1,String XLSheet) throws Exception{

        xl.ReadExcel(XlPathName,XLSheet);
        tcname = arg1;
        String un = ExcelRead.hm1.get(tcname).get("UserName");
        scenario.write("UserName " +un);
        request = GetMethodHeaders(un);

     }

    @When("^Customer Rertrives Affordability info$")
    public void Customer_Rertrives_Affordability_info(){
        String environment1 = System.getProperty("environment");
        String pla_version=System.getProperty("pla_version");
        String host=prop.getProperty("ServerName").replace("{version}",pla_version);
        String AffordabilityEAPI=String.format(host,environment1).concat("/personal-loans/affordability");
        scenario.write(AffordabilityEAPI);
        response = request.when().post(AffordabilityEAPI);

    }
    @And("^The Response having the valid message Description$")
    public void The_Response_having_the_valid__message(){

        key2 = ExcelRead.hm1.get(tcname).get("Key2");
        value2 = ExcelRead.hm1.get(tcname).get("Value2");
        json.body(key2, equalTo(value2));
    }
    @And("^The Response having the valid message code$")
    public void The_Response_having_the_valid_message_Code(){

        key2 = ExcelRead.hm1.get(tcname).get("Key1");
        value2 = ExcelRead.hm1.get(tcname).get("Value1");
        json.body(key2, equalTo(value2));
    }

    @And("^The Response having the valid message code11$")
    public void The_Response_having_the_valid_message_Code1(){

        key2 = ExcelRead.hm1.get(tcname).get("Key1");
        value2 = ExcelRead.hm1.get(tcname).get("Value1");
        json.body(key2, contains(value2));
    }


    @And("^The Response having the valid Title$")
    public void The_Response_having_the_valid__Title(){

        key2 = ExcelRead.hm1.get(tcname).get("Key3");
        value2 = ExcelRead.hm1.get(tcname).get("Value3");
        json.body(key2, contains(value2));
    }
    @And("^The Response having the valid ICM code$")
    public void The_Response_having_the_valid__ICM(){

        key2 = ExcelRead.hm1.get(tcname).get("Key2");

        json.body(key2, notNullValue());
    }
    @And("^The Response having the valid Quotation$")
    public void The_Response_having_the_valid_message_Quotation$(){

        key2 = ExcelRead.hm1.get(tcname).get("Key1");

        json.body(key2, notNullValue());
    }

    @And("^The Response having the valid eventConsolidationId$")
    public void The_Response_having_the_valid__eventConsolidationId$(){

        key2 = ExcelRead.hm1.get(tcname).get("Key3");

        json.body(key2, notNullValue());
    }

    @Then("^Get The Account number and sort code$")
    public void Get_The_Account_number_and_sort_code(){

                List sortcodelist =  response.jsonPath().getList("accounts.sortCode");
                List acclist =  response.jsonPath().getList("accounts.accountNumber");

        AccountNumber = acclist.get(0).toString();
        SortCode = sortcodelist.get(0).toString();


    }









}

