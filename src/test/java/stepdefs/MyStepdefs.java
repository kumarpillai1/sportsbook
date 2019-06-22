package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import helper.FileReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;


public class MyStepdefs {

    private final FileReader fileReader;
    Response response;
    String jsonData;
    JsonPath j;
    RequestSpecification request1 = RestAssured.given();
    private String football;
    private String home;
    private String away;
    private ValidatableResponse json;

    public MyStepdefs(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public org.json.JSONObject returnPageContent() {
        FileReader fileReader = new FileReader();

        return fileReader.generatePageContent().getJSONObject("MyStepdefs");
    }

    @Given("^local environment is up and running$")
    public void localEnvironmentIsUpAndRunning() throws Throwable {
        RestAssured.baseURI = "http://localhost:8888/";
        football = returnPageContent().getString("football");
        home = returnPageContent().getString("home");
        away = returnPageContent().getString("away");
        request1 = RestAssured.given();
        response = request1.get("football/live");

        jsonData = response.getBody().asString();
        j = new JsonPath(jsonData);


    }

    public void methodN() {


    }


    @Then("^The status code is 200$")
    public void verifyStatus() {

        int StatusCode = 200;

        json = response.then().statusCode(StatusCode);

    }

    @Then("^check the response contains only football events$")
    public void checkTheResponseContainsOnlyFootballEvents() throws Throwable {
        RestAssured.baseURI = "http://localhost:8888/";
        football = returnPageContent().getString("football");
        home = returnPageContent().getString("home");
        away = returnPageContent().getString("away");
        response = request1.get("football/live");

        jsonData = response.getBody().asString();
        j = new JsonPath(jsonData);

        int myJsonArraySize = j.getList("events.className.flatten()").size();

        for (int i = 0; i < myJsonArraySize; i++) {


            String className = "events[" + i + "].className";
            Assert.assertEquals(j.get(className).toString(), football, j.getList(" events.findAll{  it.className == 'Football' }.eventId") + "eventId");
        }
    }

    @And("^verify \"([^\"]*)\" competitor is available for all events$")
    public void verifyCompetitorIsAvailableForAllEvents(String arg0) throws Throwable {
        int myJsonArraySize = j.getList("events.className.flatten()").size();

        for (int i = 0; i < myJsonArraySize; i++) {

            String eventpath = "events[" + i + "].eventId";
            String eventscores = "events[" + i + "].scores";
            Assert.assertEquals(j.get(eventscores).toString().substring(1, 5), away, "Home Market erroring in eventId" + j.get(eventscores).toString().substring(1, 5));
            Assert.assertEquals(j.get(eventscores).toString().substring(9, 13), home, "Away Market erroring in eventId" + j.get(eventscores).toString().substring(8, 13));
        }

    }
}


