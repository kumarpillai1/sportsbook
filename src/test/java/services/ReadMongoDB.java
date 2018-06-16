package services;

/**
 * Created by 44077707 on 12/10/2017.
 */

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoIterable;
import org.apache.commons.codec.binary.Base64;
import org.bson.Document;
import org.testng.Assert;
import org.json.simple.parser.ParseException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import static com.mongodb.MongoClientOptions.builder;

public class ReadMongoDB {

    String ServerName = null;
    String DBName = null;
    String Un = null;
    String Pw= null;
    Properties prop = new Properties();
    //public static void main(String[] args) throws ParseException{

 public void VerifyIDAndCustomerInMongoDB(int Id,String customerId,String principleLoanAmt,String apr,String term,String LoanPurpose,String firstpayment,String totalInterest,String SubsequentLoanAmt,String loanPlusInterestAmount)throws ParseException{

        Document myDoc=null;

        System.out.println("Quotation ID:"+Id);
        System.out.println(customerId);
        System.out.println(principleLoanAmt);
        System.out.println(apr);
        System.out.println(term);
        System.out.println(loanPlusInterestAmount);



if(System.getProperty("environment").equalsIgnoreCase("MCT")) {

         MongoClient mongoClient = new MongoClient("hkg3vl4946o.hk.hsbc", 5255);
         MongoCredential credential = MongoCredential.createCredential("plappsit", "personal-loan-sit", "plappsit".toCharArray());
         MongoDatabase database = mongoClient.getDatabase("personal-loan-sit");
         MongoCollection<Document> collection = database.getCollection("quotes");
         myDoc = collection.find(eq("_id", Id)).first();

     }else if(System.getProperty("environment").equalsIgnoreCase("SCT")){

         MongoClient mongoClient = new MongoClient("hkg3vl4946o.hk.hsbc", 5255);
         MongoCredential credential = MongoCredential.createCredential("plappsit", "personal-loan-sit", "plappsit".toCharArray());
         MongoDatabase database = mongoClient.getDatabase("personal-loan-sit");
         MongoCollection<Document> collection = database.getCollection("quotes");
         myDoc = collection.find(eq("_id", Id)).first();

     }else if(System.getProperty("environment").equalsIgnoreCase("CERT")){


         String projectpath = System.getProperty("user.dir");
         String saparater =  System.getProperty("file.separator");
         String trustStoreLocation = projectpath+saparater+"fd-mongodb.jks";
         System.setProperty("javax.net.ssl.trustStorePassword", "letmein");
         System.setProperty("javax.net.ssl.trustStore", trustStoreLocation);
         MongoClientURI connectionString = new MongoClientURI("mongodb://plappuat:plappuat@hkg3vl4942o.hk.hsbc:5255,hkg3vl4943o.hk.hsbc:5255,hkg3vl4944o.hk.hsbc:5255,hkg3vl4945o.hk.hsbc:5255/personal-loan-uat?ssl=true");
         MongoClient mongoClient = new MongoClient(connectionString);
         MongoDatabase mongoDatabase = mongoClient.getDatabase("personal-loan-uat");
         MongoCollection<Document> collection = mongoDatabase.getCollection("quotes");
          myDoc = collection.find(eq("_id",Id)).first();
     }
                System.out.println("Mongo Response: "+myDoc.toJson());
                System.out.println(myDoc.getString(customerId));
                String totalAmt = myDoc.getString("loanPlusInterestAmount");

                //Assert.assertTrue( myDoc.getString("customerId").contains(customerId));
                Assert.assertTrue(myDoc.getInteger("principalLoanAmount").equals(Integer.parseInt(principleLoanAmt)));
                Assert.assertTrue(myDoc.getDouble("apr").equals(Double.parseDouble(apr)));
                Assert.assertTrue( myDoc.getInteger("term").equals(Integer.parseInt(term)));
                Assert.assertTrue( myDoc.getString("loanPurpose").equalsIgnoreCase(LoanPurpose));
                Assert.assertTrue( myDoc.getDouble("firstRepaymentAmount").equals(Double.parseDouble(firstpayment)));
                Assert.assertTrue( myDoc.getDouble("totalInterestAmount").equals(Double.parseDouble(totalInterest)));
                Assert.assertTrue( myDoc.getDouble("subsequentRepaymentAmount").equals(Double.parseDouble(SubsequentLoanAmt)));
// To rest the system Properties
                 System.clearProperty("javax.net.ssl.trustStorePassword");
                 System.clearProperty("javax.net.ssl.trustStore");

        }


    public static int GetTheSampleAvailableQuotationID()throws Exception{

        Document myDoc=null;


        if(System.getProperty("environment").equalsIgnoreCase("MCT")) {

            MongoClient mongoClient = new MongoClient("hkg3vl4946o.hk.hsbc", 5255);
            MongoCredential credential = MongoCredential.createCredential("plappsit", "personal-loan-sit", "plappsit".toCharArray());
            MongoDatabase database = mongoClient.getDatabase("personal-loan-sit");
            MongoCollection<Document> collection = database.getCollection("quotes");
            myDoc = collection.find(eq("loanPurpose", "car")).first();

        }else if(System.getProperty("environment").equalsIgnoreCase("CERT")){
            String projectpath = System.getProperty("user.dir");
            String saparater =  System.getProperty("file.separator");
            String trustStoreLocation = projectpath+saparater+"fd-mongodb.jks";
            System.setProperty("javax.net.ssl.trustStorePassword", "letmein");
            System.setProperty("javax.net.ssl.trustStore", trustStoreLocation);
            MongoClientURI connectionString = new MongoClientURI("mongodb://plappuat:plappuat@hkg3vl4942o.hk.hsbc:5255,hkg3vl4943o.hk.hsbc:5255,hkg3vl4944o.hk.hsbc:5255,hkg3vl4945o.hk.hsbc:5255/personal-loan-uat?ssl=true");
            MongoClient mongoClient = new MongoClient(connectionString);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("personal-loan-uat");
            MongoCollection<Document> collection = mongoDatabase.getCollection("quotes");
            myDoc = collection.find(eq("loanPurpose", "car")).first();

        }

// To rest the system Properties
        System.clearProperty("javax.net.ssl.trustStorePassword");
        System.clearProperty("javax.net.ssl.trustStore");
        return myDoc.getInteger("_id");

    }




}