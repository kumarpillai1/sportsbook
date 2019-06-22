package options;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(strict = false,
        plugin = {"pretty"},
        glue = {"stepdefs"},
        features = {"src/test/features"},
              //tags = "@wip",
        format={
                "html:target/cucumber-reports",
                "json:target/json/cucumber.json"
        }

)
public class CucumberTests {



}
