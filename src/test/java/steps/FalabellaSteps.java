package steps;

import context.WebDriverContext;
import enums.StepStatusEnum;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import util.ReportUtil;
import util.WebElementUtil;

public class FalabellaSteps {

    @And("Cerramos modal abre tu cmr aqui")
    public void cerramosModalAbreTuCmrAqui() throws InterruptedException {
        Thread.sleep(15_000);

        ReportUtil.addStep("Visualizamos modal abre tu cmr aqui", StepStatusEnum.PASSED, "Podemos ver modal abre tu cmr aqui abierto");

        WebElement imagenModalPrincipal = WebDriverContext.getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/div/div[2]/div/a/div/picture/img"));

        WebElementUtil.highlightElement(imagenModalPrincipal, 2000);

        ReportUtil.addStep("Modal abre tu cmr aqui", StepStatusEnum.PASSED, "Podemos ver modal abre tu cmr aqui abierto");

        Thread.sleep(4000);

        WebElement botonCerrarModal = WebDriverContext.getDriver().findElement(By.xpath("/html/body/div[3]/div[2]/div/div[1]"));

        WebElementUtil.highlightElement(botonCerrarModal, 2000);

        Thread.sleep(2_000);

        botonCerrarModal.click(); // CERRAR MODAL GENERAL FALABELLA

        Thread.sleep(2_000);

        ReportUtil.addStep("Modal abre tu cmr aqui cerrado", StepStatusEnum.PASSED, "Podemos ver modal abre tu cmr aqui cerrado");
    }

    @And("Cerramos modal conocer mejores ofertas")
    public void cerramosModalConocerMejoresOfertas() throws InterruptedException {
        Thread.sleep(15_000);

        // CERRAR MODAL ESQUINA SUPERIOR IZQUIERDA
        WebElement divPadreShadowRoot = WebDriverContext.getDriver().findElement(By.className("airship-html-prompt-shadow"));
        SearchContext shadowRoot = (SearchContext) ((JavascriptExecutor) WebDriverContext.getDriver())
                .executeScript("return arguments[0].shadowRoot", divPadreShadowRoot);

        ReportUtil.addStep("Modal conocer mejores ofertas abierto", StepStatusEnum.PASSED, "Podemos ver modal conocer mejores ofertas abierto");

        WebElement botonNoGracias = shadowRoot.findElement(By.cssSelector(".airship-btn.airship-btn-deny"));

        WebElementUtil.highlightElement(botonNoGracias, 2000);

        ReportUtil.addStep("Boton No Gracias Modal Secundario", StepStatusEnum.PASSED, "Podemos ver el boton No gracias del modal secundario destacado");

        Thread.sleep(4_000);

        botonNoGracias.click(); // HACER CLICK EN BOTON NO, GRACIAS

        ReportUtil.addStep("Modal conocer mejores ofertas cerrado", StepStatusEnum.PASSED, "Podemos ver modal conocer mejores ofertas cerrado");

        Thread.sleep(2_000);
    }
}