package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * Created by 44077707 on 17/06/2018.
 * Project name PersonalLoan
 */
public class ZephyrHelper {
    static Logger logger = Logger.getLogger(ZephyrHelper.class);
    static Properties ZephyrProp = new Properties();
    static String versionID=null;
    static  String cycleId=null;
    static String ProjectID=null;
    static String IssueType=null;
    static String projectname=null;
    static String versionname=null;
    static String cyclename=null;
    static  String clonedCycleId = null;
    static String Result = null;

    public  static String TestCaseStatus = "UNEXECUTED";
    static String jsonReportPath=null;

    public ZephyrHelper() throws Exception {
        String saparater = System.getProperty("file.separator");
        String projectPath = System.getProperty("user.dir");
        String configpath = projectPath + saparater + "src" + saparater + "test" + saparater + "java" + saparater + "utils" + saparater + "zephyrTestConfig.properties";
        System.out.println("Inside Get Test case Current Execution Status from JSON Report Method..................... ");
        jsonReportPath = projectPath+saparater+"target"+saparater+"json"+saparater+"cucumber.json";
        ZephyrProp.load(new FileInputStream(configpath));
        clonedCycleId = ZephyrProp.getProperty("clonedCycleId");
        versionID = ZephyrProp.getProperty("VersionID");
        cycleId = ZephyrProp.getProperty("CycleID");
        ProjectID = ZephyrProp.getProperty("projectId");
        IssueType = ZephyrProp.getProperty("IssueType");
        projectname = ZephyrProp.getProperty("projectname");
        versionname = ZephyrProp.getProperty("versionname");
        cyclename =   ZephyrProp.getProperty("cyclenameStartWith");
    }

    public static String CreateTestcaseInZephyr(String TestCaseSummary) throws Exception {

        String NewCycleBody = "{\n" +
                "    \"fields\": {\n" +
                "       \"project\":\n" +
                "       { \n" +
                "          \"id\": \"" + ProjectID + "\"\n" +
                "       },\n" +
                "       \"summary\": \"" + TestCaseSummary + "\",\n" +
                "       \"issuetype\": {\n" +
                "          \"id\": \"" + IssueType + "\"\n" +
                "       }\n" +
                "   }\n" +
                "}";

        Response CreateTestCaseResponse = RestAssured.given()
                .header("Cache-Control", "no-cache")
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic NDQwNzc3MDc6SHNiY0A2MDk=")
                .header("User-Agent", "")
                .header("X-Atlassian-Token", "no-check ")
                .body(NewCycleBody)
                .when().post(" https://jira-digital.systems.uk.hsbc/jira/rest/api/latest/issue/");

        System.out.println(CreateTestCaseResponse.prettyPrint());
        return CreateTestCaseResponse.getBody().jsonPath().getString("id");
    }




    public static  void AddTestcaseToTestCycle(String IssueId) throws Exception{

        String x = "{\"issueId\":"+IssueId+",\"versionId\":\""+versionID+"\",\"cycleId\":\""+cycleId+"\",\"projectId\":\""+ProjectID+"\",\"assigneeType\":\"currentUser\"}";
        System.out.println(x);

        Response AddTestCaseTOCycleResponse = RestAssured.given()
                .header("Cache-Control", "no-cache")
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic NDQwNzc3MDc6SHNiY0A2MDk=")
                .header("User-Agent", "")
                .header("X-Atlassian-Token", "no-check ")
                .body(x)
                .when().post(" https://jira-digital.systems.uk.hsbc/jira/rest/zapi/latest/execution");

        System.out.println(AddTestCaseTOCycleResponse.prettyPrint());

    }





    public static String GetTestCaseStatus(String TcName) throws Exception {

        System.out.println("JSON Report path is "+jsonReportPath);
        JSONParser parser = new JSONParser();
        Object obj  = parser.parse(new FileReader(jsonReportPath));

        JSONArray array = (JSONArray) obj;

        JSONObject jsonObject = (JSONObject) array.get(0);
//Get The Array Of Elements
        Object obj1  = parser.parse(jsonObject.get("elements").toString());
        JSONArray array1 = (JSONArray) obj1;

        for (int i =0;i<array1.size();i++) {
            JSONObject jsonObject1 = (JSONObject) array1.get(i);
            String tc = jsonObject1.get("name").toString();
            logger.debug("Test case Name in Json report :"+jsonObject1.get("name"));
            logger.debug("Test case Name from Zephyr Cycle :"+TcName.toString());
            if(tc.contains(TcName.toString().trim())){
                logger.info("Test case you are looking found in JSON report");
                logger.info("Started to Find the Test Steps Status of this Test case in JSON report.............");
                Object obj2 = parser.parse(jsonObject1.get("steps").toString());
                JSONArray array2 = (JSONArray) obj2;



                //logger.info(array2.toJSONString());
                logger.info("Total number of steps for this Test case is :"+array2.size());

                for (int j =0;j<array2.size();j++) {

                    JSONObject jsonObject2 = (JSONObject) array2.get(j);
                    Object obj3 = parser.parse(jsonObject2.get("result").toString());
                    JSONObject jsonObject3 = (JSONObject) obj3;
                    String tcstatus= jsonObject3.get("status").toString();
                    if(tcstatus.contains("failed")){
                        logger.info("Test case having some failure Steps so Marking Test case as failed and exiting the Search..............");
                        TestCaseStatus="failed";
                        j = array2.size()+1;
                    }else{
                        TestCaseStatus="passed";

                    }

                }
                if (TestCaseStatus.equalsIgnoreCase("passed")){
                    logger.info("All steps of this Test case are pass, so Marking Test case as passed and exiting the Search..............");
                }
                i=array1.size()+1;
            }
            else{
                logger.info("Zephyr Test case not found in JSON Report SO Assigning Test case status as Not Executed");
                TestCaseStatus="UNEXECUTED";
            }
        }
        return TestCaseStatus;

    }






    public static void CreateNewCycle() throws  Exception{


        //Get the Today date and time in specific format
        DateFormat dateFormat = new SimpleDateFormat("EEE-MMM-dd-yyyy-HH:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        //Append The Date formate to cycle name
        cyclename = cyclename+System.getProperty("environment")+System.getProperty("pla_version")+dateFormat.format(cal.getTime());

        //Create new Cycle API INput Body
        String NewCycleBody = "{\"clonedCycleId\":"+clonedCycleId+",\"name\":\""+cyclename+"\",\"build\":\"\",\"environment\":\"\",\"description\":\"\",\"startDate\":\"\",\"endDate\":\"\",\"projectId\":\""+ProjectID+"\",\"versionId\":\""+versionID+"\"}";
System.out.println(NewCycleBody);
        Response CloneTheCycle =  RestAssured.given()
                .header("Cache-Control","no-cache")
                .header("Accept","*/*")
                .header("Content-Type","application/json")
                .header("Authorization","Basic NDQwNzc3MDc6SHNiY0A2MDk=")
                .header("User-Agent","")
                .header("X-Atlassian-Token","no-check ")
                .body(NewCycleBody)
                .when().post(" https://jira-digital.systems.uk.hsbc/jira/rest/zapi/latest/cycle");
        System.out.println(CloneTheCycle.prettyPrint());
        System.out.println(CloneTheCycle.statusCode());

        Assert.assertEquals(200,CloneTheCycle.statusCode());
        Thread.sleep(2000);
        System.out.println("Created Cycle "+cyclename + "Successfull");
    }



    public static void ReadTestCaseFromZephyrCycleAndUpdateTheStatus() throws Exception{
       // Clone The Exiting Test Cycle
        CreateNewCycle();


        //Get the List of Test cases from Newly created Test cycle
       // System.out.println("https://jira-digital.systems.uk.hsbc/jira/rest/zapi/latest/zql/executeSearch?zqlQuery=project=\""+projectname+"\" AND fixVersion=\""+versionname+"\"  AND cycleName IN (\""+cyclename+"\")");
        Response ListOfTestCasesInNewCycle =  RestAssured.given()
                .header("Cache-Control","no-cache")
                .header("Accept","*/*")
                .header("Content-Type","application/json")
                .header("Authorization","Basic NDQwNzc3MDc6SHNiY0A2MDk=")
                .header("User-Agent","")
                .header("X-Atlassian-Token","no-check ")
                .when().get("https://jira-digital.systems.uk.hsbc/jira/rest/zapi/latest/zql/executeSearch?zqlQuery=project=\""+projectname+"\" AND fixVersion=\""+versionname+"\"  AND cycleName IN (\""+cyclename+"\")");
                System.out.println(ListOfTestCasesInNewCycle.prettyPrint());


        List testCaseNames = ListOfTestCasesInNewCycle.getBody().jsonPath().getList("executions.issueSummary");
        List testCaseIds = ListOfTestCasesInNewCycle.getBody().jsonPath().getList("executions.id");

        for(int i=0;i<testCaseNames.size();i++){
            System.out.println("Test case Name in Zephyr cycle :"+testCaseNames.get(i)+"And Test case Id is :"+testCaseIds.get(i));
            //Calling the Get Test case Method
            String TCStatusINJsonReport = GetTestCaseStatus(testCaseNames.get(i).toString().trim());

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
                    .header("Authorization","Basic NDQwNzc3MDc6SHNiY0A2MDk=")
                    .header("User-Agent","")
                    .header("X-Atlassian-Token","no-check ")
                    .body("{\"status\":\""+Result+"\",\"changeAssignee\":false}")
                    .when().put(updateResultsApi);

        }


    }


















}


