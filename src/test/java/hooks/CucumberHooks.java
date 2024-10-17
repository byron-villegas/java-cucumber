package hooks;

import context.WebDriverContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import util.ReportUtil;

public class CucumberHooks {
    @Before
    public void before(Scenario scenario) {
        ReportUtil.setStartTestInformation(scenario);
    }

    @Before("@Web")
    public void beforeWeb() { // Se ejecuta antes de iniciar el scenario
        WebDriverContext.initWebDriver();
    }

    @After
    public void after(Scenario scenario) { // Se ejecuta al terminar el scenario
        ReportUtil.setTestFinishedInformation(scenario);
        WebDriverContext.shutdownWebDriver();
    }
}