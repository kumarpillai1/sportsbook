package services;

/**
 * Created by 44077707 on 15/09/2017.
 */
import static io.restassured.RestAssured.given;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import stepdefs.StepDefinitions;


public  class SamlTokenGenerate {
    static  String  accesstoken = null;
    static  String authID = null;
    private static Response response;
    private ValidatableResponse json;
    public static RequestSpecification request;
     Logger logger = Logger.getLogger(StepDefinitions.class);
//https://hkg3vl7073o.hk.hsbc:8443
    //http://hkl106409.hk.hsbc:8080

   public static String GenerateSamlToken(String Username) throws IOException, ParseException {
    //public static void main(String[] args)throws IOException, ParseException {

        request = given()
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding","gzip, deflate")
                .header("Accept-Language","en-US,en;q=0.8")
                .header("Authorization","Basic aWR2OmlkdjEyMzQ=")
                .header("Connection","keep-alive")
                .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                .header("Host","hkl105792.hk.hsbc:8080")
                .header("Origin","https://hkl105792.hk.hsbc:8080")
                .header("Referer","https://hkl105792.hk.hsbc:8080/firstdirect/pages/usersignon.html")
                .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36")
                .header("X-Requested-With","XMLHttpRequest")
                .formParam("scope","read")
                .formParam("grant_type", "client_credentials");
        response = request.when().post("http://hkl105792.hk.hsbc:8080/dsp/oauth2/firstdirect/access_token");
        accesstoken = response.then().extract().path("access_token");
        System.out.println("ACCESS TOKEN:"+accesstoken);

        request = given()
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "en-US,en;q=0.8")
                .header("Authorization", "Basic aWR2OmlkdjEyMzQ=")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Host", "hkl105792.hk.hsbc:8080")
                .header("Origin", "http://hkl105792.hk.hsbc:8080")
                .header("Referer", "http://hkl105792.hk.hsbc:8080/firstdirect/pages/usersignon.html")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36")
                .header("X-Requested-With", "XMLHttpRequest");


        response = request.when().post("http://hkl105792.hk.hsbc:8080/dsp/json/realms/firstdirect/authenticate");
        authID = response.then().extract().path("authId");
        // System.out.println("FIRST Response: " + response.prettyPrint());
        System.out.println("AUTH-ID: " + authID);

        String O_token1 = "\"name\": \"IDToken1\",                    \"value\": \"\"";

        String O_token2 = "\"name\": \"IDToken2\",                    \"value\": \"\"" ;
        String O_token3 = "\"name\": \"IDToken3\",                    \"value\": \"\"";
        String O_token4 = "\"name\": \"IDToken4\",                    \"value\": \"\"";
        String O_token5 = "\"name\": \"IDToken5\",                    \"value\": \"\"";
        String O_token6 = "\"name\": \"IDToken6\",                    \"value\": \"\"";
        String O_token7 = "\"name\": \"IDToken7\",                    \"value\": \"\"";
        String O_token8 = "\"name\": \"IDToken8\",                    \"value\": \"\"";
        String O_token8_1 = "\"name\": \"IDToken8\",                    \"value\": \"\"";

        String R_token1 = "\"name\": \"IDToken1\",                    \"value\": \"44077707\"";
        String R_token2 = "\"name\": \"IDToken2\",                    \"value\": \"" + accesstoken + "\"";
        String R_token3 = "\"name\": \"IDToken3\",                    \"value\": \"129.10.0.23\"";
        String R_token4 = "\"name\": \"IDToken4\",                    \"value\": \"40\"";
        String R_token5 = "\"name\": \"IDToken5\",                    \"value\": \""+Username+"\"";
        //String R_token5 = "\"name\": \"IDToken5\",                    \"value\": \"" + "0985004681" + "\"";
        String R_token6 = "\"name\": \"IDToken6\",                    \"value\": \"BIBI\"";
        String R_token7 = "\"name\": \"IDToken7\",                    \"value\": \"BIBI\"";
        String R_token8 = "\"name\": \"IDToken8\",                    \"value\": \"0123456789\"";
        String R_token8_1 = "\"name\": \"IDToken8\",                    \"value\": \"dsaflafkjdaf\"";


        String finaljson = response.getBody().prettyPrint().replace("\n", "").replace(O_token1, R_token1).replace(O_token2, R_token2).replace(O_token3, R_token3).replace(O_token4, R_token4).replace(O_token5, R_token5).replace(O_token6, R_token6).replace(O_token7, R_token7).replace(O_token8, R_token8).replace(O_token8_1, R_token8_1);
        System.out.println("3-API FINAL DSP TOKEN Input BODY:"+finaljson);



        request = given()
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "en-US,en;q=0.8")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .header("Host", "hkl105792.hk.hsbc:8080")
                .header("Origin", "https://hkl105792.hk.hsbc:8080")
                .header("Referer", "https://hkl105792.hk.hsbc:8080/firstdirect/pages/usersignon.html")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36")
                .header("X-Requested-With", "XMLHttpRequest")

                .body(finaljson);
        response = request.when().post("http://hkl105792.hk.hsbc:8080/dsp/json/realms/firstdirect/authenticate");
        //System.out.println("response: " + response.getBody().prettyPrint());
        String tokenID = response.then().extract().path("tokenId");
        //String finaljson1 = "{\"input_token_state\":{\"token_type\":\"SSOTOKEN\",\"tokenId\":\"" + tokenID + "\"},\"output_token_state\":{\"token_type\":\"SECPSAML\",\"subject_confirmation\":\"BEARER\"}}";

        String finaljson1 =  "{\"subject\":{\"ssoToken\":\"" + tokenID + "\"},\"resources\":[\"APIResource\"],\"environment\":{\"ApiCamLevel\":[\"30\"]},\"application\":\"IDVPolicySet\"}";
        System.out.println(" Final Json :"+finaljson1);
        System.out.println(" Token ID :"+tokenID);

        request = given()
                //.queryParam("_action","evaluate")
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "en-US,en;q=0.8")

                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .header("Host", "hkl105792.hk.hsbc:8080")
                .header("Origin", "http://hkl105792.hk.hsbc:8080")
                .header("Referer", "http://hkl105792.hk.hsbc:8080/firstdirect/pages/usersignon.html")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("dspSession",tokenID )
                .body(finaljson1);

        response = request.when().post("http://hkl105792.hk.hsbc:8080/dsp/json/realms/root/realms/firstdirect/policies?_action=evaluate");
        String y = response.getBody().jsonPath().getString("attributes.SAMLToken");
        String x = y.replace("{", "").replace("[[\"issued_token\":\"", "").replace("\\n", "").replace("}", "").replace(">\"", ">").replace("]]","");
        //String x = response.getBody().prettyPrint().replace("{", "").replace("\"issued_token\": \"", "").replace("\\n", "").replace("}", "").replace(">\"", ">");
        //logger.info("FINAL TOKEN :" + x);
        //Assert.assertTrue(x.contains(Username));
        return x;
    }
}

