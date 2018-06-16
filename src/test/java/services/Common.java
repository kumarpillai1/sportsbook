package services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.specification.RequestSpecification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;

import static io.restassured.RestAssured.given;

/**
 * Created by 44077707.
 * In This Class we defined common methods like Headers for Get and Post Request's
 */
public class Common {

    /**
     * Created by 44077707.
     * In This method we defined all Headers for Get Request
     * @param cin  this CIN number will pass in Test  in run time by reading the value from  Excel
     *
     * SamlTokenGenerate.GenerateSamlToken(cin):  this Method will generate the SAMl token and pass it to the Header value
     *  this GenerateSamlToken method will run for each scenario
     */
    public static RequestSpecification GetMethodHeaders(String cin) throws Exception {
        return given()
                .header("X-HSBC-Saml", SamlTokenGenerate.GenerateSamlToken(cin))
                .header("Content-Type", "application/json")
                .header("X-HSBC-IP-Id", "145.26.54.24")
                .header("X-HSBC-Channel-Id", "OHM")
                .header("X-HSBC-Chnl-CountryCode", "GB")
                .header("X-HSBC-Session-Correlation-Id", randomGen())
                .header("X-HSBC-Request-Correlation-Id", randomGen())
                .header("X-HSBC-Locale", "GB")
                .header("X-HSBC-Src-UserAgent", "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id", "C12345678")
                .header("X-HSBC-CAM-Level", "40")
                .header("X-HSBC-Chnl-Group-Member", "HBEU")
                .header("X-HSBC-Src-Device-Id", "1234");

    }
    /**
     * Created by 44077707.
     * In This method we defined all Headers for Get Request
     * @param cin  this CIN number will pass in Test  in run time by reading the value from  Excel
     *@param inputBody  This Inputbody will pass in Runtime from test, based on test/API the input body will change
     * SamlTokenGenerate.GenerateSamlToken(cin):  this Method will generate the SAMl token and pass it to the Header value
     */
    public static RequestSpecification PostMethodHeaders(String cin, String inputBody) throws Exception {
        System.out.println("Input*********" + inputBody);
        return given()
                .header("X-HSBC-Saml", SamlTokenGenerate.GenerateSamlToken(cin))
                .header("Content-Type", "application/json")
                .header("X-HSBC-IP-Id", "145.26.54.24")
                .header("X-HSBC-Channel-Id", "OHM")
                .header("X-HSBC-Chnl-CountryCode", "GB")
                .header("X-HSBC-Session-Correlation-Id", randomGen())
                .header("X-HSBC-Request-Correlation-Id", randomGen())
                .header("X-HSBC-Locale", "GB")
                .header("X-HSBC-Src-UserAgent", "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id", "C12345678")
                .header("X-HSBC-CAM-Level", "40")
                .header("X-HSBC-Chnl-Group-Member", "HBEU")
                .header("X-HSBC-Src-Device-Id", "1234")
                .body(inputBody);
    }
       /**
     * Created by 44077707.
     * In This method we defined all Headers for Get Request
     * @param samlToken  this samlToken value will pass in test this method used only in Smoke test,
     * we will genararte only once saml token and wil use for entire Smoke suite
     *
     *
     **/
    public static RequestSpecification GetMethodHeadersUsingSaml(String samlToken) throws Exception {
        return given()
                .header("X-HSBC-Saml", samlToken)
                .header("Content-Type", "application/json")
                .header("X-HSBC-IP-Id", "145.26.54.24")
                .header("X-HSBC-Channel-Id", "OHM")
                .header("X-HSBC-Chnl-CountryCode", "GB")
                .header("X-HSBC-Session-Correlation-Id", randomGen())
                .header("X-HSBC-Request-Correlation-Id", randomGen())
                .header("X-HSBC-Locale", "GB")
                .header("X-HSBC-Src-UserAgent", "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id", "C12345678")
                .header("X-HSBC-CAM-Level", "40")
                .header("X-HSBC-Chnl-Group-Member", "HBEU")
                .header("X-HSBC-Src-Device-Id", "1234");

    }
    /**
     * Created by 44077707.
     * In This method we defined all Headers for Get Request
     * @param samlToken  this samlToken value will pass in test this method used only in Smoke test,
     *@param inputBody  This Inputbody will pass in Runtime from test, based on test/API the input body will change
     * SamlTokenGenerate.GenerateSamlToken(cin):  this Method will generate the SAMl token and pass it to the Header value
     */
    public static RequestSpecification PostMethodHeadersusingSaml(String samlToken, String inputBody) throws Exception {
              System.out.println("Input*********" + inputBody);
        return given()
                .header("X-HSBC-Saml", samlToken)
                .header("Content-Type", "application/json")
                .header("X-HSBC-IP-Id", "145.26.54.24")
                .header("X-HSBC-Channel-Id", "OHM")
                .header("X-HSBC-Chnl-CountryCode", "GB")
                .header("X-HSBC-Session-Correlation-Id", randomGen())
                .header("X-HSBC-Request-Correlation-Id", randomGen())
                .header("X-HSBC-Locale", "GB")
                .header("X-HSBC-Src-UserAgent", "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7B405")
                .header("X-HSBC-User-Id", "C12345678")
                .header("X-HSBC-CAM-Level", "40")
                .header("X-HSBC-Chnl-Group-Member", "FSDT")
                .header("X-HSBC-Src-Device-Id", "1234")
                .body(inputBody);
    }

    private static String randomGen() {
        final Random r = new Random(System.currentTimeMillis());
        return String.valueOf(1000000000 + r.nextInt(2000000000));
    }

    public static String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
    }







}