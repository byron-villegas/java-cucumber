package steps;

import context.WebDriverContext;
import enums.StepStatusEnum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.ReportUtil;
import util.WebElementUtil;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BuscarProductoFalabellaSteps {
    private String nombreProducto;
    private String nombreProductoASeleccionar;

    @And("Buscamos el producto {string}")
    public void buscamosElProducto(String nombreProducto) throws InterruptedException {
        this.nombreProducto = nombreProducto; // Inicializo la variable nombre de la clase con la recibida en el feature

        WebElement barraDeBusqueda = WebDriverContext.getDriver().findElement(By.id("testId-SearchBar-Input"));

        // Ingresamos el nombre del producto a buscar en la barra de busqueda de la pagina
        barraDeBusqueda.sendKeys(this.nombreProducto);

        WebElementUtil.highlightElement(barraDeBusqueda, 5000);

        ReportUtil.addStep("Buscamos el producto '" + this.nombreProducto + "' en la barra de busqueda", StepStatusEnum.PASSED, "Visualizamos el nombre del producto en la barra de busqueda");

        Thread.sleep(2_000); // Hace que el programa espere dos segundos (2_000)

        // Presionar boton buscar de la barra de busqueda de la pagina
        WebDriverContext
                .getDriver()
                .findElement(By.cssSelector(".SearchBar-module_searchBtnIcon__2L2s0"))
                .click(); // PRESIONA BOTON BUSCAR

        ReportUtil.addStep("Presionar boton buscar", StepStatusEnum.PASSED, "Visualizamos los productos encontrados");

        Thread.sleep(5_000);
    }

    @And("Seleccionamos el producto {string}")
    public void seleccionamosElProducto(String nombreProductoASeleccionar) throws InterruptedException {
        this.nombreProductoASeleccionar = nombreProductoASeleccionar;

        WebElement productoASeleccionar = WebDriverContext.getDriver().findElement(By.xpath("(//b[contains(text(), '" + this.nombreProductoASeleccionar + "')])[1]"));

        WebElementUtil.highlightElement(productoASeleccionar, 2000);

        ReportUtil.addStep("Producto a Seleccionar Destacado", StepStatusEnum.PASSED, "Visualizamos el producto a seleccionar destacado");

        Thread.sleep(2000);

        productoASeleccionar.click();

        Thread.sleep(3_000);
    }

    @When("Visualizamos el producto")
    public void visualizamosElProducto() {
        ReportUtil.addStep("Visualizamos el producto '"+this.nombreProductoASeleccionar+"' seleccionado", StepStatusEnum.PASSED, "Visualizamos las caracteristicas del producto seleccionado");
    }

    @And("Validamos que el nombre del producto sea el buscado")
    public void validamosQueElNombreDelProductoSeaElBuscado() throws InterruptedException {
        WebElement nombreProductoVisualizado = WebDriverContext
                .getDriver()
                .findElement(By.xpath("/html/body/div[1]/div/section/div[1]/div[2]/div[2]/section[2]/div[1]/div[2]/h1"));

        WebElementUtil.highlightElement(nombreProductoVisualizado, 3000);

        if(nombreProductoVisualizado.getText().contains(nombreProducto)) {
            ReportUtil.addStep("Validamos que el nombre del producto visualizado '"+nombreProductoVisualizado.getText()+"' contiene el nombre del producto seleccionado '"+nombreProductoASeleccionar+"'", StepStatusEnum.PASSED, "El nombre del producto visualizado contiene el nombre del producto seleccionado");
        } else {
            ReportUtil.addStep("Validamos que el nombre del producto visualizado '"+nombreProductoVisualizado.getText()+"' no contiene el nombre del producto seleccionado '"+nombreProductoASeleccionar+"'", StepStatusEnum.FAILED, "El nombre del producto visualizado no contiene el nombre del producto seleccionado");
        }

        Thread.sleep(3000);

        Assert.assertEquals(true, nombreProductoVisualizado.getText().contains(nombreProducto));
    }

    @And("Validamos que el precio del producto sea {int}")
    public void validamosQueElPrecioDelProductoSea(int precioDelProductoVisualizado) throws InterruptedException {
        WebElement precioProductoVisualizado = WebDriverContext
                .getDriver()
                .findElement(By.xpath("/html/body/div[1]/div/section/div[1]/div[2]/div[2]/section[2]/div[2]/div/div[2]/div[1]/div[1]/ol/li[1]/div/span"));

        WebElementUtil.highlightElement(precioProductoVisualizado, 3000);

        String precioProductoFormateado = "$ " + (new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ITALY))).format(precioDelProductoVisualizado);

        if(precioProductoVisualizado.getText().equals(precioProductoFormateado)) {
            ReportUtil.addStep("Validamos que el precio del producto visualizado '"+precioProductoVisualizado.getText()+"' tiene el mismo precio que el esperado '"+precioProductoFormateado+"'", StepStatusEnum.PASSED, "El precio del producto visualizado es identico al que esperamos");
        } else {
            ReportUtil.addStep("Validamos que el precio del producto visualizado '"+precioProductoVisualizado.getText()+"' no tiene el mismo precio que el esperado '"+precioProductoFormateado+"'", StepStatusEnum.FAILED, "El precio del producto visualizado es no identico al que esperamos");
        }

        Thread.sleep(2000);

        Assert.assertEquals(precioProductoFormateado, precioProductoVisualizado.getText());
    }
}
