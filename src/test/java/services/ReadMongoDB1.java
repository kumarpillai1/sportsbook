package services;

/**
 * Created by 44077707 on 12/10/2017.
 */

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Properties;

import static com.mongodb.client.model.Filters.eq;


public class ReadMongoDB1 {

    String ServerName = null;
    String DBName = null;
    String Un = null;
    String Pw= null;
    Properties prop = new Properties();
    public static void main(String[] args) throws ParseException{

            String projectpath = System.getProperty("user.dir");
            String trustStoreLocation = "C:\\Users\\44077707\\IdeaProjects\\fd-personal-loan-api-tests\\fd-mongodb.jks";
            System.setProperty("javax.net.ssl.trustStorePassword", "letmein");
            System.setProperty("javax.net.ssl.trustStore", trustStoreLocation);
        MongoClientURI connectionString = new MongoClientURI("mongodb://plappuat:plappuat@hkg3vl4942o.hk.hsbc:5255,hkg3vl4943o.hk.hsbc:5255,hkg3vl4944o.hk.hsbc:5255,hkg3vl4945o.hk.hsbc:5255/personal-loan-uat?ssl=true");

        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("personal-loan-uat");
        MongoCollection<Document> collection = mongoDatabase.getCollection("quotes");
        //Document myDoc = collection.find(eq("_id",Id)).first();
        //System.out.println("Mongo Response: "+myDoc.toJson());
















//
//
//        MongoClient mongoClient = new MongoClient("hkg3vl4945o.hk.hsbc", 5255);
//        MongoCredential credential = MongoCredential.createCredential("plappuat", "personal-loan-uat", "plappuat".toCharArray());
//        MongoDatabase database = mongoClient.getDatabase("personal-loan-uat");
//        MongoCollection<Document> collection = database.getCollection("quotes");
//        String sample  = collection.find().first().toJson().toString();
////        System.out.println("Mongo Response: "+myDoc.toJson());
//        System.out.println("Mongo Response: "+ sample);
//


    }






}