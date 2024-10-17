package steps;

import client.pokeapi.dto.ObtenerPokedexPorIdGeneracionResponseDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import client.GenericClient;

public class BuscarPokedexSteps {
    private static final Logger logger = Logger.getLogger(BuscarPokedexSteps.class);

    private int numeroGeneracion;
    private ObtenerPokedexPorIdGeneracionResponseDTO obtenerPokedexPorIdGeneracionResponseDTO;

    @Given("Un id de generacion {int}")
    public void unIdDeGeneracion(int numeroGeneracion) {
        this.numeroGeneracion = numeroGeneracion;
    }

    @When("Buscamos el pokedex")
    public void buscamosElPokedex() {
        String url = "https://pokeapi.co/api/v2/pokedex/" + numeroGeneracion;

        GenericClient.get(url);

        obtenerPokedexPorIdGeneracionResponseDTO = GenericClient.lastResponse.as(ObtenerPokedexPorIdGeneracionResponseDTO.class);

        logger.info("Pokedex encontrada: " + obtenerPokedexPorIdGeneracionResponseDTO);
    }

    @And("Validamos que el nombre de la region es {string}")
    public void validamosQueElNombreDeLaRegionEs(String nombreRegion) {
        Assert.assertEquals(nombreRegion, obtenerPokedexPorIdGeneracionResponseDTO.getName());
    }

    @And("Validamos que la cantidad total de pokemones es {int}")
    public void validamosQueLaCantidadTotalDePokemonesEs(int cantidadTotalDePokemones) {
        Assert.assertEquals(cantidadTotalDePokemones, obtenerPokedexPorIdGeneracionResponseDTO.getPokemon_entries().size());
    }

    @Then("Validamos que la respuesta del servicio de busqueda de pokedex por generacion es {string}")
    public void validamosQueLaRespuestaDelServicioDeBusquedaDePokedexPorGeneracionEs(String httpStatus) {
        Assert.assertEquals(Integer.parseInt(httpStatus), GenericClient.lastResponse.getStatusCode());
    }
}