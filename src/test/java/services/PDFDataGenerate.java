package services;


import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


public class PDFDataGenerate {

    public static void main(String[] args) throws FileNotFoundException,IOException,ParseException {

        String str =null;
        String str1 =null;
        String str2 =null;
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader("C:\\Users\\44077707\\IdeaProjects\\fd-personal-loan-api-tests\\Approve.json"));

        JSONObject jsonObject = (JSONObject) obj;

         str = (String) jsonObject.get("signedAgreementPDF");
        str1 = (String) jsonObject.get("unsignedAgreementPDF");
        str2 = (String) jsonObject.get("unsignedAgreementHTML");





        Base64 decoder = new Base64();


      byte[] decodedBytes = decoder.decode(str);
        FileOutputStream fop;
        File file = new File("C:\\Users\\44077707\\IdeaProjects\\fd-personal-loan-api-tests\\signedAgreementPDF.pdf");
        fop = new FileOutputStream(file);
        fop.write(decodedBytes);
        fop.flush();
        fop.close();



        byte[] decodedBytes1 = decoder.decode(str1);
        File file1 = new File("C:\\Users\\44077707\\IdeaProjects\\fd-personal-loan-api-tests\\unsignedAgreementPDF.pdf");
        FileOutputStream fop1;
        fop1 = new FileOutputStream(file1);
        fop1.write(decodedBytes1);
        fop1.flush();
        fop1.close();



        byte[] decodedBytes2 = decoder.decode(str2);
        File file2 = new File("C:\\Users\\44077707\\IdeaProjects\\fd-personal-loan-api-tests\\unsignedAgreementHTML.html");
        FileOutputStream fop2;
        fop2 = new FileOutputStream(file2);
        fop2.write(decodedBytes2);
        fop2.flush();
        fop2.close();



}

}