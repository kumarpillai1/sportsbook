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
System.out.println("Inside Get Test case Status Method ");
        String saparater =  System.getProperty("file.separator");
        String projectPath =  System.getProperty("user.dir");
        String jsonReportPath = projectPath+saparater+"target"+saparater+"json"+saparater+"cucumber.json";

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
        logger.info("Zephyr Test case found in JSON report");
        Object obj2 = parser.parse(jsonObject1.get("after").toString());
        JSONArray array2 = (JSONArray) obj2;
        JSONObject jsonObject2 = (JSONObject) array2.get(0);
        Object obj3 = parser.parse(jsonObject2.get("result").toString());
        JSONObject jsonObject3 = (JSONObject) obj3;
        TestCaseStatus= jsonObject3.get("status").toString();
        logger.info("Test case Status in JSON Report is "+jsonObject3.get("status"));
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


