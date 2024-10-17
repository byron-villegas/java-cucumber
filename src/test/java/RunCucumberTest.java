
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:build/cucumber/cucumber-report.html", "json:build/cucumber/cucumber.json"},
        features = "src/test/resources/features",
        tags = "not @ignore"
)
public class RunCucumberTest {

}