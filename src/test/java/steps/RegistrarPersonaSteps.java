package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Persona;
import org.apache.log4j.Logger;
import org.junit.Assert;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RegistrarPersonaSteps {
    private static final Logger logger = Logger.getLogger(RegistrarPersonaSteps.class);
    private Persona persona;
    private final ArrayList<Persona> personas = new ArrayList<>();

    @Given("^Una informacion de persona con rut \"([^\"]*)\", nombres \"([^\"]*)\", primer apellido \"([^\"]*)\", segundo apellido \"([^\"]*)\", edad \"([^\"]*)\", fecha de nacimiento \"([^\"]*)\"$")
    public void unaInformacionDePersona(String rut, String nombres, String primerApellido, String segundoApellido, byte edad, String fechaNacimiento) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate fechaNacimientoFormateada = LocalDate.parse(fechaNacimiento, formatters);

        this.persona = new Persona(rut, nombres, primerApellido, segundoApellido, edad, fechaNacimientoFormateada);

        logger.info("Informacion Persona: " + this.persona);
    }

    @When("Registramos")
    public void registramos() {
        this.personas.add(this.persona);
    }

    @Then("Validamos que se haya guardado")
    public void validamosQueSeHayaGuardado() {
        Assert.assertEquals(1, this.personas.size());
    }
}