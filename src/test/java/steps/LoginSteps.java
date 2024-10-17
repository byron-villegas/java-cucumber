package steps;

import context.WebDriverContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import pages.LoginPage;

public class LoginSteps {
    private static final Logger logger = Logger.getLogger(LoginSteps.class);
    private String urlPagina;
    private String email;
    private String password;
    private LoginPage loginPage;

    @Given("Una pagina de login {string}")
    public void unaPaginaDeLogin(String urlPagina) {
        this.urlPagina = urlPagina;

        logger.info("Url de Pagina: " + urlPagina);

        loginPage = new LoginPage(WebDriverContext.getDriver()); // Inicializamos la clase con el web driver

        WebDriverContext.getDriver().get(this.urlPagina);
    }

    @And("Validamos que el titulo de la pagina es {string}")
    public void validamosQueElTituloDeLaPaginaEs(String tituloPagina) {
        Assert.assertEquals(tituloPagina, loginPage.getTitle());
    }

    @And("Ingresamos el email {string}")
    public void ingresamosElEmail(String email) throws InterruptedException {
        this.email = email;

        loginPage.setEmail(this.email);

        Thread.sleep(2000); // 2 Segundos
    }

    @And("Ingresamos el password {string}")
    public void ingresamosElPassword(String password) throws InterruptedException {
        this.password = password;

        loginPage.setPassword(this.password);

        Thread.sleep(2000); // 2 Segundos
    }

    @When("Logeamos")
    public void logeamos() throws InterruptedException {
        loginPage.login();

        Thread.sleep(2000); // 2 Segundos
    }

    @Then("Validamos que el mensaje en pantalla sea {string}")
    public void validamosQueElMensajeEnPantallaSea(String mensajeEnPantalla) throws InterruptedException {
        Thread.sleep(2000); // 2 Segundos

        Assert.assertEquals(mensajeEnPantalla, loginPage.getLoginMessage());
    }
}
