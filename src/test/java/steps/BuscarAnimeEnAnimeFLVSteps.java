package steps;

import constants.Constants;
import context.WebDriverContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import util.OperatingSystemUtil;
import util.PipelineUtil;

import java.util.ArrayList;

public class BuscarAnimeEnAnimeFLVSteps {
    private static final Logger logger = Logger.getLogger(BuscarAnimeEnAnimeFLVSteps.class);
    private String nombreAnime; // nombreAnime transversal para realizar las validaciones

    @And("^Buscamos un anime en especifico \"([^\"]*)\"$")
    public void buscamosUnAnimeEnEspecifico(String nombreAnime) throws InterruptedException {
        this.nombreAnime = nombreAnime; // Inicializamos el nombre anime para usarlo de manera transversal

        if (!PipelineUtil.isRunning()) {
            Thread.sleep(60000);
        }

        // Clickear un elemento mediante su xpath
        WebDriverContext.getDriver().findElement(By.xpath("/html/body/div[3]")).click(); // Abrir publicidad
        Thread.sleep(2000);

        // Vuelve a la primera pestaña
        ArrayList<String> tabs = new ArrayList<>(WebDriverContext.getDriver().getWindowHandles()); // Obtener pestana de la pagina
        WebDriverContext.getDriver().switchTo().window(tabs.get(0)); // Volver a la pestaña principal

        // Llena un input mediante su name
        WebDriverContext.getDriver().findElement(By.name("q")).sendKeys(nombreAnime);
        Thread.sleep(2000);

        // Clickear un elemento mediante su clase
        WebDriverContext.getDriver().findElement(By.className("fa-search")).click();
        Thread.sleep(2000);
    }

    @And("Seleccionamos el anime encontrado")
    public void seleccionamosElAnimeEncontrado() throws InterruptedException {
        // Clickear un elemento mediante su clase
        WebDriverContext.getDriver().findElement(By.xpath("//*[text() = '" + nombreAnime + "']")).click();
        Thread.sleep(2000);
    }

    @Then("Validamos que sea el anime seleccionado")
    public void validamosQueSeaElAnimeSeleccionado() throws InterruptedException {
        // Obtener el texto de un elemento mediante su xpath
        String tituloAnimeEncontrado = WebDriverContext.getDriver().findElement(By.xpath("/html/body/div[2]/div/div/div[1]/div[2]/h1")).getText();
        Thread.sleep(2000);

        Assert.assertEquals(nombreAnime, tituloAnimeEncontrado);
    }
}