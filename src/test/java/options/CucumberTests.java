package options;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterSuite;
import services.Report;


@RunWith(Cucumber.class)
@CucumberOptions(strict = false,
        plugin = {"pretty"},
        glue = {"stepdefs"},
        features = {"src/test/features"},
              //tags = "@MCT",
        format={
                "html:target/cucumber-reports",
                "json:target/json/cucumber.json"
        }

)
public class CucumberTests {



}
