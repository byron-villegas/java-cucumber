package util;

import context.WebDriverContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.util.Timer;
import java.util.TimerTask;

public class WebElementUtil {
    public static void highlightElement(WebElement webElement, long durationInMilliseconds) {
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) WebDriverContext.getDriver();
            javascriptExecutor.executeScript("arguments[0].style.background='yellow'", webElement);
            javascriptExecutor.executeScript("arguments[0].style.border='2px solid red'", webElement);

            TimerTask timerTask = new java.util.TimerTask() {
                @Override
                public void run() {
                    javascriptExecutor.executeScript("arguments[0].style.background=''", webElement);
                    javascriptExecutor.executeScript("arguments[0].style.border=''", webElement);
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask, durationInMilliseconds);
        } catch (Exception ex) {
            System.err.println("No se pudo destacar el elemento");
        }
    }
}