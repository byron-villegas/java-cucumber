package steps;

import context.WebDriverContext;
import enums.StepStatusEnum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.ReportUtil;
import util.WebElementUtil;

public class GoogleSteps {
    private static final Logger logger = Logger.getLogger(GoogleSteps.class);
    private String nombrePaginaABuscar; // nombrePagina transversal para realizar las validaciones

    @Given("Una pagina a buscar {string} en google")
    public void unaPaginaABuscarEnGoogle(String nombrePaginaABuscar) {
        this.nombrePaginaABuscar = nombrePaginaABuscar; // Inicializamos el nombre pagina

        logger.info("Pagina a Buscar: " + this.nombrePaginaABuscar);
    }

    @And("Buscamos en google")
    public void buscamos() throws InterruptedException {
        WebDriverContext.getDriver().get("https://www.google.cl"); // Pagina de inicio del driver

        ReportUtil.addStep("Entrar en Pagina Google", StepStatusEnum.PASSED, "Visualizamos la pagina de google con la barra de busqueda");

        Thread.sleep(2000);

        WebElement barraBusqueda = WebDriverContext.getDriver().findElement(By.name("q"));

        // Llena un input mediante su name
        barraBusqueda.sendKeys(nombrePaginaABuscar); // Buscamos un elemento html por su nombre y le enviamos el nombre de la pagina
        WebElementUtil.highlightElement(barraBusqueda, 2000);

        ReportUtil.addStep("Buscamos la pagina '" + this.nombrePaginaABuscar + "' en la barra de busqueda", StepStatusEnum.PASSED, "Visualizamos el nombre de la pagina en la barra de busqueda");

        Thread.sleep(2000);

        WebElement botonBuscar = WebDriverContext.getDriver().findElement(By.name("btnK"));
        WebElementUtil.highlightElement(botonBuscar, 2000);

        ReportUtil.addStep("Presiona boton buscar", StepStatusEnum.PASSED, "Visualizamos el boton buscar destacado");

        Thread.sleep(2000);

        // Clickear un boton mediante su name
        botonBuscar.click(); // Buscamos un elemnto html por su nombre y lo clickeamos
        Thread.sleep(2000);
    }

    @And("Seleccionamos la pagina encontrada en google")
    public void seleccionamosLaPaginaEncontrada() throws InterruptedException {
        WebElement paginaASeleccionar = WebDriverContext.getDriver().findElement(By.xpath("(//h3)[1]"));

        WebElementUtil.highlightElement(paginaASeleccionar, 2000);

        ReportUtil.addStep("Resultados de la busqueda", StepStatusEnum.PASSED, "Visualizamos el nombre de la pagina buscada '"+this.nombrePaginaABuscar+" en el listado de resultado de busqueda");

        Thread.sleep(2000);

        // Clickear un elemento mediante su clase
        paginaASeleccionar.click(); // Buscamos un elemento por su nombre y lo clickeamos

        Thread.sleep(2000);

        ReportUtil.addStep("Visualizar Pagina Seleccionada", StepStatusEnum.PASSED, "Visualizamos la pagina seleccionada");

        Thread.sleep(2000);
    }
}

