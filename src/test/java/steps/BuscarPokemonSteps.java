package steps;

import client.pokeapi.dto.ObtenerPokemonPorNombreResponseDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import client.GenericClient;

public class BuscarPokemonSteps {
    private static final Logger logger = Logger.getLogger(BuscarPokemonSteps.class);
    private String nombrePokemon;
    private ObtenerPokemonPorNombreResponseDTO obtenerPokemonPorNombreResponseDTO;

    @Given("^Un Pokemon \"([^\"]*)\"$")
    public void unPokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    @When("Buscamos el Pokemon")
    public void buscamosElPokemon() {
        String url = "https://pokeapi.co/api/v2/pokemon-species/" + nombrePokemon.toLowerCase();

        GenericClient.get(url);

        obtenerPokemonPorNombreResponseDTO = GenericClient.lastResponse.getBody().as(ObtenerPokemonPorNombreResponseDTO.class);

        logger.info("Pokemon encontrado: " + obtenerPokemonPorNombreResponseDTO);
    }

    @And("Validamos que el id del Pokemon es {int}")
    public void validamosQueElIdDelPokemonEs(int idPokemon) {
        Assert.assertEquals(idPokemon, obtenerPokemonPorNombreResponseDTO.getId());
    }

    @Then("Validamos que la respuesta del servicio de busqueda Pokemon es {string}")
    public void validamosQueLaRespuestaDelServicioDeBusquedaPokemonEs(String httpStatus) {
        Assert.assertEquals(Integer.parseInt(httpStatus), GenericClient.lastResponse.getStatusCode());
    }
}