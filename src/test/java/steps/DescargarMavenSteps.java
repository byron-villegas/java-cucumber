package steps;

import context.WebDriverContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import java.io.File;
import java.util.Arrays;

public class DescargarMavenSteps {
    private static final Logger logger = Logger.getLogger(DescargarMavenSteps.class);

    @Given("Una pagina de Maven")
    public void unaPaginaDeMaven() throws InterruptedException {
        // Pagina de inicio del robot
        WebDriverContext.getDriver().get("https://maven.apache.org");
        Thread.sleep(2000);
    }

    @And("Seleccionamos descargar")
    public void seleccionamosDescargar() throws InterruptedException {
        // Clickear un elemento mediante su xpath
        WebDriverContext.getDriver().findElement(By.xpath("//*[contains(@title, 'Download')]")).click();
        Thread.sleep(2000);
    }

    @When("Descargamos")
    public void descargamos() throws InterruptedException {
        // Clickear un boton mediante su xpath
        WebDriverContext.getDriver().findElement(By.xpath("(//*[starts-with(text(), 'apache-maven') and contains(text(), 'bin.zip')])[1]")).click();
        Thread.sleep(20000);
    }

    @Then("Validamos que se haya realizado la descarga")
    public void validamosQueSeHayaRealizadoLaDescarga() {
        logger.info("User Home: " + System.getProperty("user.home"));

        File[] archivosDescargados = new File(System.getProperty("user.home") + "/Downloads").listFiles();

        boolean archivoExiste = Arrays
                .stream(archivosDescargados)
                .anyMatch(archivo -> archivo.getName().contains("apache-maven"));

        Assert.assertTrue(archivoExiste);
    }
}