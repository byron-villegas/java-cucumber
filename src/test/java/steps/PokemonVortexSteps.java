package steps;

import context.WebDriverContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import util.ImageUtil;
import util.PropertiesUtil;
import util.WebScreenUtil;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Properties;

public class PokemonVortexSteps {
    @Given("Un usuario para iniciar sesion en pokemon")
    public void unUsuarioParaIniciarSesionEnPokemon() throws InterruptedException {
        WebDriverContext.getDriver().get("https://www.pokemon-vortex.com/login");

        Properties properties = PropertiesUtil.getPropertiesByFile("pokemon-vortex.properties");

        final String user = properties.getProperty("user");
        final String password = properties.getProperty("password");

        Thread.sleep(5000);
        WebDriverContext.getDriver().findElement(By.xpath("//*[@id='myusername']")).sendKeys(user);
        Thread.sleep(2000);
        WebDriverContext.getDriver().findElement(By.xpath("//*[@id='mypassword']")).sendKeys(password);
        Thread.sleep(2000);
        WebDriverContext.getDriver().findElement(By.xpath("//input[contains(@value, 'Log In')]")).click();
        Thread.sleep(2000);
    }

    @And("Seleccionamos batalla")
    public void seleccionamosBatalla() throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath("//*[@id='battleTab']/a")).click();
        Thread.sleep(2000);
    }

    @And("Seleccionamos luchar con cualquier miembro")
    public void seleccionamosLucharConCualquierMiembro() throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath("//*[@id='menuBox']/ul/li[6]/a")).click();
        Thread.sleep(2000);
    }

    @And("Ingresamos el usuario {string} a batallar")
    public void ingresamosElUsuarioABatallar(String usuario) throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath("//*[@id='ajax']/div[1]/form/input")).sendKeys(usuario);
        Thread.sleep(2000);
    }

    @And("Presionamos luchar")
    public void presionamosLuchar() throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath("//button[contains(., 'Battle')]")).click();
        Thread.sleep(2000);
    }

    @And("Seleccionamos el pokemon {string} a utilizar en batalla")
    public void seleccionamosElPokemonAUtilizarEnBatalla(String pokemon) throws InterruptedException {
        WebElement selectedPokemon = WebDriverContext.getDriver().findElement(By.xpath(String.format("//*[contains(text(), '%s')]", pokemon)));
        WebElement titleOfSelectedPokemon = selectedPokemon.findElement(By.xpath(".//ancestor::p"));
        titleOfSelectedPokemon.findElement(By.xpath(".//ancestor::label")).click();
        Thread.sleep(2000);
    }

    @And("Presionamos continuar para iniciar la batalla")
    public void presionamosContinuarParaIniciarLaBatalla() throws InterruptedException {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) WebDriverContext.getDriver();
        javascriptExecutor.executeScript("window.scrollBy(0,500)", "");
        Thread.sleep(2000);
        WebDriverContext.getDriver().findElement(By.xpath("//*[@id='ajax']/form/p/input")).click();
        Thread.sleep(2000);
    }

    @And("Seleccionamos el ataque a utilizar {string}")
    public void seleccionamosElAtaqueAUtilizar(String ataque) throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath(String.format("//*[text()[contains(.,'%s')]]", ataque))).click();
        Thread.sleep(2000);
    }

    @And("Presionamos atacar")
    public void presionamosAtacar() throws InterruptedException {
        boolean pokemonLive = true;

        while (pokemonLive) {
            try {
                WebDriverContext.getDriver().findElement(By.xpath("//*[@id='ajax']/form[2]/div/input[contains(@value, 'Continue')]"));
                pokemonLive = false;
            } catch (Exception ex) {
                WebDriverContext.getDriver().findElement(By.xpath("//input[contains(@value, 'Attack')]")).click();
                Thread.sleep(2000);
            }
        }
    }

    @And("Presionamos continuar para pelear con el siguiente pokemon")
    public void presionamosContinuarParaPelearConElSiguientePokemon() throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath("(//input[contains(@value, 'Continue')])[2]")).click();
        Thread.sleep(2000);
    }

    @And("Presionamos continuar para finalizar la batalla")
    public void presionamosContinuarParaFinalizarLaBatalla() throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath("(//input[contains(@value, 'Continue')])[2]")).click();
        Thread.sleep(2000);
    }

    @And("Presionamos batallar nuevamente")
    public void presionamosBatallarNuevamente() throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath("//li[contains(text(), 'Rebattle Opponent')]")).click();
        Thread.sleep(2000);
    }

    @And("Luchamos con pokemon {string} con ataque {string} por {int} veces")
    public void luchamosConPokemonConAtaquePorVeces(String pokemon, String ataque, int cantidadPelea) throws InterruptedException {
        for (int i = 1; i <= cantidadPelea; i++) {
            System.out.printf("Comienza la Pelea (%d)%n", i);
            for (int j = 1; j < 7; j++) {
                System.out.printf("Pelea actual (%d)%n", j);
                seleccionamosElPokemonAUtilizarEnBatalla(pokemon);
                presionamosContinuarParaIniciarLaBatalla();
                seleccionamosElAtaqueAUtilizar(ataque);
                presionamosAtacar();
                if (j != 6) {
                    presionamosContinuarParaPelearConElSiguientePokemon();
                    System.out.printf("Pelea siguiente (%d)%n", j + 1);
                } else {
                    presionamosContinuarParaFinalizarLaBatalla();
                    System.out.printf("Termina la Pelea (%d)%n", i);
                }
            }
            presionamosBatallarNuevamente();
        }
    }

    @Given("Ejemplo")
    public void ejemplo() throws AWTException, InterruptedException {
        boolean pokemonEncontrado = false;
        Robot robot = new Robot();

        Thread.sleep(5000);

        int[] opciones = new int[]{KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_LEFT};
        int opcionAnterior = 1;

        while (!pokemonEncontrado) {

            int opcion = (int) ((Math.random() * 4) + 1);

            while (opcion == opcionAnterior) {
                opcion = (int) ((Math.random() * 4) + 1);
            }

            opcionAnterior = opcion;

            for (int i = 0; i < 4; i++) {
                robot.keyPress(opciones[opcion]);
            }

            byte[] screenshotBytes = WebScreenUtil.takeScreenshotInBytesFormat(WebDriverContext.getDriver());

            String result = ImageUtil.readContentOfImage(screenshotBytes);

            System.out.println("---------------- TEXTO DE IMAGEN ----------------");
            System.out.println(result);
            System.out.println("---------------- TEXTO DE IMAGEN ----------------");

            if (result.toUpperCase().contains("SHING") || result.toUpperCase().contains("SHINY") || result.toUpperCase().contains("MEW") || result.toUpperCase().contains("KYO")) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) WebDriverContext.getDriver();
                javascriptExecutor.executeScript("alert('APARECIO UN SHINY O LEGENDARIO tienes 40 segundos para atraparlo')", "");
                Thread.sleep(40000);
            }
        }
    }

    @And("Seleccionamos explorar")
    public void seleccionamosExplorar() throws InterruptedException {
        WebDriverContext.getDriver().findElement(By.xpath("//*[@id='mapsTab']/a")).click();
        Thread.sleep(2000);
    }
}
