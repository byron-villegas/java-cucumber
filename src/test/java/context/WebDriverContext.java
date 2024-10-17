package context;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import util.PipelineUtil;

public class WebDriverContext {
    private static WebDriver webDriver;
    private static final Logger logger = Logger.getLogger(WebDriverContext.class);
    public static String browserName;

    public static void initWebDriver() {
        if(webDriver == null) {
            webDriver = generateWebDriverByBrowser();
        }
    }

    public static void shutdownWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
            browserName = null;
        }
    }

    public static WebDriver getDriver() {
        return webDriver;
    }

    private static RemoteWebDriver generateWebDriverByBrowser() {
        if (WebDriverManager.edgedriver().getBrowserPath().isPresent()) {
            logger.info("Navegador Edge encontrado");
            WebDriverManager.edgedriver().setup();
            browserName = "Edge";
            return new EdgeDriver(generateEdgeOptions());
        }

        if (WebDriverManager.chromedriver().getBrowserPath().isPresent()) {
            logger.info("Navegador Chrome encontrado");
            WebDriverManager.chromedriver().setup();
            browserName = "Chrome";
            return new ChromeDriver(generateChromeOptions());
        }

        if (WebDriverManager.firefoxdriver().getBrowserPath().isPresent()) {
            logger.info("Navegador Firefox encontrado");
            WebDriverManager.firefoxdriver().setup();
            browserName = "Firefox";
            return new FirefoxDriver();
        }

        if (WebDriverManager.operadriver().getBrowserPath().isPresent()) {
            logger.info("Navegador Opera encontrado");
            WebDriverManager.chromedriver().setup();
            browserName = "Opera";
            return new ChromeDriver(generateChromeOptions());
        }

        throw new IllegalArgumentException("No se ha encontrado un navegador para utilizar");
    }

    private static EdgeOptions generateEdgeOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();

        edgeOptions.addArguments("--start-maximized");

        return edgeOptions;
    }

    private static ChromeOptions generateChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();

        if (PipelineUtil.isRunning()) {
            chromeOptions.addArguments("--headless=new"); // CI/CD
            chromeOptions.addArguments("--disable-dev-shm-usage"); // CI/CD
            chromeOptions.addArguments("--remote-debugging-port=9222"); // CI/CD
        }

        chromeOptions.addArguments("--start-maximized");

        return chromeOptions;
    }
}