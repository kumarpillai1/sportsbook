package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 44077707 on 12/06/2018.
 * Project name PLA
 */
public class UpdateResultsInZephyr {

    static String newCycleCycleID = null;


    static String projectname = "FDHBT";
    static String versionname = "fd Personal Loan Apply 2.0";
    static String cyclename = "PLA-API-Automation-Regression_";

    public static void main(String[] args) throws Exception {

    //public static void UpdateTestResultsInZephys() throws  Exception{
//Get the Today date and time in specific format
        DateFormat dateFormat = new SimpleDateFormat("EEE-MMM-dd-yyyy-HH:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        //Append The Date formate to cycle name
        cyclename = cyclename+System.getProperty("environment")+System.getProperty("pla_version")+dateFormat.format(cal.getTime());

        String Result=null;

        //Create new Cycle API INput Body
        String NewCycleBody = "{\"clonedCycleId\":43710,\"name\":\""+cyclename+"\",\"build\":\"\",\"environment\":\"\",\"description\":\"\",\"startDate\":\"\",\"endDate\":\"\",\"projectId\":\"30310\",\"versionId\":\"49808\"}";

        Response CloneTheCycle =  RestAssured.given()
                .header("Cache-Control","no-cache")
                .header("Accept","*/*")
                .header("Content-Type","application/json")
                .header("Authorization","Basic NDQwNzc3MDc6SHNiY0A1MDk=")
                .header("User-Agent","")
                .header("X-Atlassian-Token","no-check ")
                .body(NewCycleBody)
                .when().post(" https://jira-digital.systems.uk.hsbc/jira/rest/zapi/latest/cycle");
                System.out.println(CloneTheCycle.prettyPrint());
                System.out.println(CloneTheCycle.statusCode());

                 Assert.assertEquals(200,CloneTheCycle.statusCode());
                 Thread.sleep(2000);
                System.out.println("Created Cycle "+cyclename + "Successfull");

//Get the List of Test cases from Newly created Test cycle
                System.out.println("https://jira-digital.systems.uk.hsbc/jira/rest/zapi/latest/zql/executeSearch?zqlQuery=project=\""+projectname+"\" AND fixVersion=\""+versionname+"\"  AND cycleName IN (\""+cyclename+"\")");
            Response ListOfTestCasesInNewCycle =  RestAssured.given()
                    .header("Cache-Control","no-cache")
                    .header("Accept","*/*")
                    .header("Content-Type","application/json")
                    .header("Authorization","Basic NDQwNzc3MDc6SHNiY0A1MDk=")
                    .header("User-Agent","")
                    .header("X-Atlassian-Token","no-check ")


                    .when().get("https://jira-digital.systems.uk.hsbc/jira/rest/zapi/latest/zql/executeSearch?zqlQuery=project=\""+projectname+"\" AND fixVersion=\""+versionname+"\"  AND cycleName IN (\""+cyclename+"\")");
                     System.out.println(ListOfTestCasesInNewCycle.prettyPrint());
                   // newCycleCycleID = ListOfTestCasesInNewCycle.getBody().jsonPath().getList("executions.cycleId").get(0).toString();
                    //System.out.println("Cycle ID is :"+ListOfTestCasesInNewCycle.getBody().jsonPath().getList("executions.cycleId").get(0));


        List testCaseNames = ListOfTestCasesInNewCycle.getBody().jsonPath().getList("executions.issueSummary");
        List testCaseIds = ListOfTestCasesInNewCycle.getBody().jsonPath().getList("executions.id");

        for(int i=0;i<testCaseNames.size();i++){
            System.out.println("Test case Name in Zephyr cycle :"+testCaseNames.get(i)+"And Test case Id is :"+testCaseIds.get(i));
           String TCStatusINJsonReport = ReadTestCaseStatusFromJson.GetTestCaseStatus(testCaseNames.get(i).toString().trim());
            if(TCStatusINJsonReport.equalsIgnoreCase("passed")){
                Result="1";
            }else if(TCStatusINJsonReport.equalsIgnoreCase("failed")){

                Result="2";
            }else{
                Result="-1";
            }
            //Update the Test case Status
            String updateResultsApi = "https://jira-digital.systems.uk.hsbc/jira/rest/zapi/latest/execution/"+testCaseIds.get(i)+"/execute";
            Response executionStatus =  RestAssured.given()
                    .header("Cache-Control","no-cache")
                    .header("Accept","*/*")
                    .header("Content-Type","application/json")
                    .header("Authorization","Basic NDQwNzc3MDc6SHNiY0A1MDk=")
                    .header("User-Agent","")
                    .header("X-Atlassian-Token","no-check ")
                    .body("{\"status\":\""+Result+"\",\"changeAssignee\":false}")
                    .when().put(updateResultsApi);

        }


    }


    }


