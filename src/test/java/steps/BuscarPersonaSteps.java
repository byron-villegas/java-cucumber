package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Persona;
import org.apache.log4j.Logger;
import org.junit.Assert;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BuscarPersonaSteps {
    private static final Logger logger = Logger.getLogger(BuscarPersonaSteps.class);
    private final List<Persona> personas = new ArrayList<>();
    private String rut;
    private Persona persona;

    @Given("Un listado de personas")
    public void unListadoDePersonas(DataTable table) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");

        List<List<String>> rows = table.asLists(String.class);

        int i = 0;
        for (List<String> columns : rows) {

            if (i != 0) {
                LocalDate fechaNacimientoFormateada = LocalDate.parse(columns.get(5), formatters);

                Persona persona = new Persona(columns.get(0), columns.get(1), columns.get(2), columns.get(3), Byte.parseByte(columns.get(4)), fechaNacimientoFormateada);

                logger.info("Lista Personas: " + persona);

                personas.add(persona);
            }

            i++;
        }
    }

    @When("^Buscamos por rut \"([^\"]*)\"$")
    public void buscamosPorRut(String rut) {
        this.rut = rut;
        this.persona = this.personas
                .stream()
                .filter(persona -> persona.getRut().equals(rut))
                .findFirst()
                .orElse(new Persona());
    }

    @Then("Validamos que sea la persona encontrada")
    public void validamosQueSeaLaPersonaEncontrada() {
        Assert.assertEquals(this.rut, this.persona.getRut());
    }
}