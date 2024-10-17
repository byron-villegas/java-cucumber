package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.junit.Assert;

public class CalculadoraSteps {
    private static final Logger logger = Logger.getLogger(CalculadoraSteps.class);
    private int numero1;
    private int numero2;
    private int resultado;

    @Given("Dos numeros {int} y {int}")
    public void sumaDeDosNumeros(int numero1, int numero2) {
        this.numero1 = numero1;
        this.numero2 = numero2;
    }

    @When("Sumamos")
    public void sumamos() {
        this.resultado = numero1 + numero2;
        logger.info(String.format("Suma %d + %d = %d", numero1, numero2, this.resultado));
    }

    @When("Restamos")
    public void restamos() {
        this.resultado = numero1 - numero2;
        logger.info(String.format("Resta %d - %d = %d", numero1, numero2, this.resultado));
    }

    @Then("Validamos que el resultado sea {int}")
    public void resultadoSuma(int resultado) {
        Assert.assertEquals(resultado, this.resultado);
    }
}