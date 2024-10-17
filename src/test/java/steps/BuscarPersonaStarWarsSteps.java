package steps;

import client.starwarsapi.dto.ObtenerPersonaPorIdResponseDTO;
import enums.StepStatusEnum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;
import client.GenericClient;
import util.ReportUtil;

public class BuscarPersonaStarWarsSteps {
    private static final Logger logger = Logger.getLogger(BuscarPersonaStarWarsSteps.class);
    private int idPersona;
    private ObtenerPersonaPorIdResponseDTO obtenerPersonaPorIdResponseDTO;

    @Given("Un Id Persona de Star Wars {string}")
    public void unIdPersonaDeStarWars(String idPersona) {
        ReportUtil.addStep("Dado un id persona de Star Wars a buscar", StepStatusEnum.PASSED, "Asignamos el id " + this.idPersona + " de persona a buscar");

        this.idPersona = Integer.parseInt(idPersona);
    }

    @When("Buscamos la persona de Star Wars")
    public void buscamosLaPersonaDeStarWars() {
        String url = "https://swapi.py4e.com/api/people/" + idPersona;

        GenericClient.get(url);

        obtenerPersonaPorIdResponseDTO = GenericClient.lastResponse.getBody().as(ObtenerPersonaPorIdResponseDTO.class);

        logger.info("Persona encontrada: " + obtenerPersonaPorIdResponseDTO);
    }

    @And("Validamos que el nombre de la persona de Star Wars es {string}")
    public void validamosQueElNombreDeLaPersonaDeStarWarsEs(String nombrePersona) {
        Assert.assertEquals(nombrePersona, obtenerPersonaPorIdResponseDTO.getName());
    }

    @And("Validamos que el genero de la persona de Star Wars es {string}")
    public void validamosQueElGeneroDeLaPersonaDeStarWarsEs(String generoPersona) {
        Assert.assertEquals(generoPersona, obtenerPersonaPorIdResponseDTO.getGender());
    }

    @Then("Validamos que la respuesta del servicio de busqueda Persona de Star Wars es {string}")
    public void validamosQueLaRespuestaDelServicioDeBusquedaPersonaDeStarWarsEs(String httpStatus) {
        Assert.assertEquals(Integer.parseInt(httpStatus), GenericClient.lastResponse.getStatusCode());
    }
}