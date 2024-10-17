package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    private By emailInput = By.xpath("//*[@id='email']");
    private By passwordInput = By.xpath("//*[@id='passwd']");
    private By signInButton = By.xpath("//*[@id='SubmitLogin']");
    private By loginMessage = By.xpath("/html/body/div[4]/div/div/h3");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    private void clickSignInButton() {
        driver.findElement(signInButton).click();
    }

    public String getLoginMessage() {
        return driver.findElement(loginMessage).getText();
    }

    public void login() {
        clickSignInButton();
    }
}