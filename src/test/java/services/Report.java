package services;

/**
 * Created by 44077707 on 07/09/2017.
 */
import com.pdfcrowd.Pdfcrowd;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.*;
import java.io.File;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//import com.pdfcrowd.*;
public class Report {

  static   String projectpath =  System.getProperty("user.dir");
  static   String saparater =  System.getProperty("file.separator");
    private List <String> fileList;

    static String OUTPUT_ZIP_FILE = null;
    static String SOURCE_FOLDER=null;
    static String linkaddress= "https://digital-confluence.systems.uk.hsbc/confluence/download/attachments/87910198/";
    static String filename = null;
    public Report() {
        fileList = new ArrayList < String > ();
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy-hh-mm");
        String DateToStr = format.format(curDate);
        System.out.println(DateToStr);
        filename = "API Test Automation Results-"+DateToStr+"-"+System.getProperty("environment")+".zip";
           OUTPUT_ZIP_FILE = projectpath+saparater+"target"+saparater+"API_Test_Automation_00Results-"+DateToStr+"-"+System.getProperty("environment")+".zip";
           SOURCE_FOLDER = projectpath+saparater+"target"+saparater+"cucumber-html-reports";
        System.out.println(SOURCE_FOLDER);
        System.out.println(OUTPUT_ZIP_FILE);
    }

   // public  void GenerateHTMLreport() {
      public static void main(String[] args) throws InterruptedException,Exception{


      String projectpath = System.getProperty("user.dir");
        String jsonpath = projectpath+saparater+"target"+saparater+"json"+saparater+"cucumber.json";
        String htmlpath = projectpath+saparater+"target";
        System.out.println(jsonpath);
        System.out.println(htmlpath);
        File reportOutputDirectory = new File(htmlpath);
        List<String> jsonFiles = new ArrayList();
        jsonFiles.add(jsonpath);
        //jsonFiles.add("cucumber-report-2.json");

        //String buildNumber = "1";
        String projectName = "FD-Personal Loan API Testing ";
        //boolean runWithJenkins = false;
       // boolean parallelTesting = false;

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
// optional configuration
        //configuration.setParallelTesting(parallelTesting);
        //configuration.setRunWithJenkins(runWithJenkins);
        //configuration.setBuildNumber(buildNumber);
// addidtional metadata presented on main page
        /*configuration.addClassifications("Eligibility EAPI", "http://fd-personal-loan-mob--gb-hbeu-v0-mct.cf.wgdc-drn-01.cloud.uk.hsbc/personal-loans/eligibility");
        configuration.addClassifications("Eligibility PAPI", "http://fd-customers--gb-hbeu-v0-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/eligibility/personal-loan");
        configuration.addClassifications("Eligibility SAPI", "https://fd-customer-eligibility--gb-hbeu-v1-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/{CustomerId}/eligibility");
        configuration.addClassifications("Account List SAPI", "https://get-customer-accounts-list-gb-fd-sapi-v1-mct.cf.wgdc-drn-01.cloud.uk.hsbc/customers/{CustomerId}/account-details");
*/
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        Reportable result = reportBuilder.generateReports();

       /*  Report appZip = new Report();
         appZip.generateFileList(new File(SOURCE_FOLDER));
          appZip.zipIt(OUTPUT_ZIP_FILE);*/

/*

            // create the API client instance
           Pdfcrowd.HtmlToImageClient client = new Pdfcrowd.HtmlToImageClient("suresh409", "afd0b6c2531817e05e137c73d802b77d");
             // configure the conversion
              client.setOutputFormat("png");

             // run the conversion and write the result to a file
            client.convertFileToFile(projectpath+"/target/cucumber-html-reports/overview-features.html", "MyLayout.png");
*/

/*

          System.setProperty("webdriver.ie.driver", "C:\\SWDTOOLS\\Selenium\\bin\\IEDriverServer.exe");
          WebDriver driver = new InternetExplorerDriver();
          driver.manage().window().maximize();
          driver.get("https://digital-confluence.systems.uk.hsbc/confluence/display/FTT/Personal+Loan+API+Test+Results");

         // driver.findElement(By.linkText(System.getProperty("environment"))).click();
          //driver.findElement(By.linkText("SCT")).click();
          driver.navigate().to("https://digital-confluence.systems.uk.hsbc/confluence/pages/viewpageattachments.action?pageId=87910196&metadataLink=true");
            Thread.sleep(10000);

          //driver.findElement(By.xpath("//[@id='content-metadata-attachments']")).click();
System.out.println(OUTPUT_ZIP_FILE);


          driver.findElement(By.id("file_0")).sendKeys(OUTPUT_ZIP_FILE);

          driver.findElement(By.name("confirm")).click();
          Thread.sleep(5000);
          //driver.findElement(By.linkText(System.getProperty("environment"))).click();
          driver.findElement(By.linkText("SCT")).click();
          Thread.sleep(5000);
          //driver.findElement(By.xpath("//div[@class='quick-comment-prompt']/span")).submit();
          driver.findElement(By.xpath("//div[@class='quick-comment-prompt']/span")).click();
          driver.findElement(By.xpath("//div[@class='quick-comment-prompt']/span")).sendKeys("test");
          Thread.sleep(3000);
        String finalReportLink = linkaddress+filename;
          driver.findElement(By.id("rte-button-link"));
          Thread.sleep(5000);
          driver.findElement(By.className("weblink-destination")).sendKeys(finalReportLink);
          String x = "Click here for "+filename+ "Results Download";
          driver.findElement(By.id("weblink-destination")).sendKeys(x);


*/

    }





    public void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file: this.fileList) {
                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateFileList(File node) {
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }


}
