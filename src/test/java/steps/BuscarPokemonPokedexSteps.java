package steps;

import context.WebDriverContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import util.WebScreenUtil;

public class BuscarPokemonPokedexSteps {
    private static final Logger logger = Logger.getLogger(BuscarPokemonPokedexSteps.class);
    private String nombrePokemon; // nombrePokemon transversal para realizar las validaciones

    @Given("^Un nombre de pokemon \"([^\"]*)\"$")
    public void unNombreDePokemon(String nombrePokemon) throws InterruptedException {
        this.nombrePokemon = nombrePokemon; // Inicializamos el nombre pagina

        logger.info("Pokemon a buscar en el pokedex: " + this.nombrePokemon);

        WebDriverContext.getDriver().get("https://pokedex-gi9f.onrender.com/pokedex/#/"); // Pagina de inicio del driver
        Thread.sleep(10000);

        WebScreenUtil.takeScreenshotAndSaveInFile(WebDriverContext.getDriver(), System.getProperty("user.dir") + "/imagenes/pagina-pokedex.png");
    }

    @When("Escribimos el nombre")
    public void escribimosElNombre() throws InterruptedException {

        // Escribe caracter por caracter
        for (int i = 0; i < nombrePokemon.length(); i++) {
            final String caracter = String.valueOf(nombrePokemon.charAt(i));
            WebDriverContext.getDriver().findElement(By.id("pokemonSearch")).sendKeys(caracter); // Buscamos un elemento html por su id y le enviamos el nombre del pokemon
            Thread.sleep(1000);
        }

        WebScreenUtil.takeScreenshotAndSaveInFile(WebDriverContext.getDriver(), System.getProperty("user.dir") + "/imagenes/pokemon-buscado.png");
    }

    @And("Seleccionamos el pokemon")
    public void loSeleccionamos() throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.id(String.format("avatar-%s", nombrePokemon.toLowerCase()))).click(); // Buscamos un elemento html por su id y lo clickeamos

        Thread.sleep(4000);

        WebScreenUtil.takeScreenshotAndSaveInFile(WebDriverContext.getDriver(), System.getProperty("user.dir") + "/imagenes/pokemon-encontrado-seleccionado.png");
    }

    @Then("Validamos que el pokemon encontrado sea el buscado")
    public void validamosQueElPokemonEncontradoSeaElBuscado() throws InterruptedException {
        // Obtener el texto de un elemento mediante su id
        String nombreDelPokemonEncontrado = WebDriverContext.getDriver().findElement(By.id("selectedPokemonModalLabel")).getAttribute("innerHTML");
        Thread.sleep(2000);

        Assert.assertEquals(nombrePokemon.toUpperCase(), nombreDelPokemonEncontrado.toUpperCase());
    }
}