package services;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import stepdefs.StepDefinitions;

import java.io.FileReader;


/**
 * Created by 44077707 on 07/06/2018.
 * Project name CCApply
 */
public class ReadTestCaseStatusFromJson {
    static Logger logger = Logger.getLogger(ReadTestCaseStatusFromJson.class);

    public  static String TestCaseStatus = "UNEXECUTED";
    public static String GetTestCaseStatus(String TcName) throws Exception {
    //static  String TcName = "Check Customer with refer marker E is eligible";

      // public static void main(String[] args) throws Exception {

        System.out.println("Inside Get Test case Current Execution Status from JSON Report Method..................... ");
        String saparater =  System.getProperty("file.separator");
        String projectPath =  System.getProperty("user.dir");
        String jsonReportPath = projectPath+saparater+"target"+saparater+"json"+saparater+"cucumber.json";
        System.out.println("JSON Report path is "+jsonReportPath);
        JSONParser parser = new JSONParser();
        Object obj  = parser.parse(new FileReader(jsonReportPath));

        JSONArray array = (JSONArray) obj;

        JSONObject jsonObject = (JSONObject) array.get(0);

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
}


