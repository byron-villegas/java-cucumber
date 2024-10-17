package steps;

import context.WebDriverContext;
import enums.StepStatusEnum;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.ReportUtil;
import util.WebElementUtil;
import java.util.Arrays;

public class AgregarProductoCarroCompraFalabellaSteps {
    @And("Agregamos el producto al carro de compras")
    public void agregamosElProductoAlCarroDeCompras() throws InterruptedException {
        WebElement botonAgregarProductoAlCarroCompra = WebDriverContext.getDriver().findElement(By.xpath("//*[text() = 'Agregar al Carro']"));
        WebElementUtil.highlightElement(botonAgregarProductoAlCarroCompra, 3000);
        ReportUtil.addStep("Boton Agregar Producto Al Carro de Compra", StepStatusEnum.PASSED, "Visualizamos el boton agregar producto al carro de compra destacado");
        Thread.sleep(3_000);

        botonAgregarProductoAlCarroCompra.click();

        Thread.sleep(3_000);

        ReportUtil.addStep("Pantalla lo que llevas en tu Carro", StepStatusEnum.PASSED, "Visualizamos la pantalla lo que llevas en tu carro con el producto");
    }

    @And("Validamos que la cantidad total de productos del carro de compras sea {int}")
    public void validamosQueLaCantidadTotalDeProductosDelCarroDeComprasSea(int cantidadTotalProductosDelCarroDeCompras) throws InterruptedException {
        WebElement textoCantidadTotalCarroDeCompras = WebDriverContext.getDriver().findElement(By.xpath("/html/body/div[1]/header/div[1]/div/div[4]/ul/li[4]/div/span"));
        WebElementUtil.highlightElement(textoCantidadTotalCarroDeCompras, 3000);

        String cantidadTotalProductosDelCarroDeComprasFormateado = String.valueOf(cantidadTotalProductosDelCarroDeCompras);

        if (textoCantidadTotalCarroDeCompras.getText().equals(cantidadTotalProductosDelCarroDeComprasFormateado)) {
            ReportUtil.addStep("Validamos que la Cantidad Total Productos del Carro de Compras visualizado '" + textoCantidadTotalCarroDeCompras.getText() + "' es la misma que la esperada '" + cantidadTotalProductosDelCarroDeCompras + "'", StepStatusEnum.PASSED, "La cantidad total de productos del carro de compras es identica a la esperada");
        } else {
            ReportUtil.addStep("Validamos que la Cantidad Total Productos del Carro de Compras visualizado '" + textoCantidadTotalCarroDeCompras.getText() + "' no es la misma que la esperada '" + cantidadTotalProductosDelCarroDeCompras + "'", StepStatusEnum.FAILED, "La cantidad total de productos del carro de compras no es identica a la esperada");
        }

        Assert.assertEquals(cantidadTotalProductosDelCarroDeComprasFormateado, textoCantidadTotalCarroDeCompras.getText());

        Thread.sleep(3_000);
    }

    @And("Seleccionamos el tipo de garantia {string}")
    public void seleccionamosElTipoDeGarantia(String tipoDeGarantia) throws InterruptedException {
        String xpathTextoTipoDeGarantia = obtenerXpathTextoTipoDeGarantia(tipoDeGarantia);
        WebElement textoTipoDeGarantia = WebDriverContext.getDriver().findElement(By.xpath(xpathTextoTipoDeGarantia));

        textoTipoDeGarantia.click();

        Thread.sleep(3_000);

        WebElementUtil.highlightElement(textoTipoDeGarantia, 4000);

        boolean poseeTipoDeGarantiaContenido = validarSiContienePalabras(textoTipoDeGarantia, tipoDeGarantia);

        if(poseeTipoDeGarantiaContenido) {
            ReportUtil.addStep("Validamos que la garantia seleccionada '" + textoTipoDeGarantia.getText() + "' contiene la esperada '" + tipoDeGarantia + "'", StepStatusEnum.PASSED, "El tipo de garantia seleccionada contiene la esperada");
        } else {
            ReportUtil.addStep("Validamos que la garantia seleccionada '" + textoTipoDeGarantia.getText() + "' no contiene la esperada '" + tipoDeGarantia + "'", StepStatusEnum.FAILED, "El tipo de garantia seleccionada no contiene la esperada");
        }

        Thread.sleep(4_000);

        Assert.assertTrue(poseeTipoDeGarantiaContenido);
    }

    private String obtenerXpathTextoTipoDeGarantia(String tipoDeGarantia) {
        String[] tipoDeGarantiaSplit = tipoDeGarantia.split(" ");

        StringBuilder textoBase = new StringBuilder("(//*[contains(normalize-space(.), '" + tipoDeGarantiaSplit[0] + "')");

        for (int i = 0; i < tipoDeGarantiaSplit.length; i++) {
            if (i != 0) {
                textoBase.append("and contains(normalize-space(.), '").append(tipoDeGarantiaSplit[i]).append("')");
            }
        }

        textoBase.append("])[17]");

        return textoBase.toString();
    }

    private boolean validarSiContienePalabras(WebElement webElement, String textoAContener) {
        return Arrays
                .stream(textoAContener.split(" "))
                .allMatch(texto -> webElement.getText().contains(texto));
    }

    @And("Vamos al carro de compras")
    public void vamosAlCarroDeCompras() throws InterruptedException {
        WebElement botonIrAlCarroCompra = WebDriverContext.getDriver().findElement(By.xpath("//*[text() = 'Ir al Carro']"));
        WebElementUtil.highlightElement(botonIrAlCarroCompra, 3000);
        ReportUtil.addStep("Boton Ir Al Carro de Compra", StepStatusEnum.PASSED, "Visualizamos el boton ir al carro de compra destacado");
        Thread.sleep(3_000);

        botonIrAlCarroCompra.click();

        Thread.sleep(3_000);

        ReportUtil.addStep("Carro de Compra", StepStatusEnum.PASSED, "Visualizamos el producto en nuestro carro de compra");
    }
}
