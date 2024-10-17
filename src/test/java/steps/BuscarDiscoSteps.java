package steps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import context.WebDriverContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Product;
import model.Track;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.FileUtil;
import util.ImageUtil;
import util.WebElementUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscarDiscoSteps {
    private static final Logger logger = Logger.getLogger(BuscarDiscoSteps.class);
    private static final String DISKS_DIR = System.getProperty("user.dir") + "/discos";
    private String sku;
    private String tipo;
    private String nombreArtista;
    private String editor;
    private String nombreDisco;
    private String pais;
    private String anoDisco;
    private List<String> imagenes;
    private List<String> generos;
    private List<Track> trackList;
    private String diskDir;

    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    @Given("Un sku {string}")
    public void unSkuYTipo(String sku) {
        logger.info("SKU: " + sku);

        this.sku = sku;

        WebDriverContext
                .getDriver()
                .get("https://www.discogs.com/");
    }

    @Given("Una Url {string} un sku {string}")
    public void unaUrlUnSkuYTipo(String url, String sku) {
        logger.info("URL: " + url);
        logger.info("SKU: " + sku);

        this.sku = sku;

        WebDriverContext
                .getDriver()
                .get(url);
    }

    @And("Cerramos modal aceptar cookies")
    public void cerramosModalAceptarCookies() throws InterruptedException {
        Thread.sleep(2000);

        WebElement acceptCookies = WebDriverContext
                .getDriver()
                .findElement(By.id("onetrust-accept-btn-handler"));

        WebElementUtil.highlightElement(acceptCookies, 2000);

        acceptCookies.click();

        Thread.sleep(2000);
    }

    @And("Lo buscamos mediante el sku")
    public void loBuscamosMedianteElSku() throws InterruptedException {
        logger.info("SKU a buscar: " + sku);

        WebElement searchInput = WebDriverContext
                .getDriver()
                .findElement(By.name("q"));

        WebElementUtil.highlightElement(searchInput, 2000);

        searchInput.sendKeys(sku);

        Thread.sleep(9000);
    }

    @When("Seleccionamos el primer resultado")
    public void seleccionamosElPrimerResultado() throws InterruptedException {
        WebElement primerResultado = WebDriverContext
                .getDriver()
                .findElement(By.xpath("html/body/div[2]/div[1]/div/header/div/form/div/div[2]/ul/li[1]/a/div"));

        WebElementUtil.highlightElement(primerResultado, 2000);

        Thread.sleep(2000);

        primerResultado.click();

        Thread.sleep(2000);
    }

    @And("Obtenemos el nombre del artista")
    public void obtenemosElNombreDelArtista() throws InterruptedException {
        WebElement nombreDelArtista = WebDriverContext
                .getDriver()
                .findElement(By.xpath("//*[@id='page']/div[1]/div[2]/div/h1"));

        WebElementUtil.highlightElement(nombreDelArtista, 2000);

        nombreArtista = nombreDelArtista
                .getText()
                .split("\u2013")[0]
                .trim();

        logger.info("Nombre Artista: " + nombreArtista);

        Thread.sleep(2000);
    }

    @And("Obtenemos el nombre del album")
    public void obtenemosElNombreDelAlbum() throws InterruptedException {
        WebElement nombreDelDisco = WebDriverContext
                .getDriver()
                .findElement(By.xpath("//*[@id='page']/div[1]/div[2]/div/h1"));

        WebElementUtil.highlightElement(nombreDelDisco, 2000);

        nombreDisco = nombreDelDisco
                .getText()
                .split("\u2013")[1]
                .trim();

        logger.info("Nombre Disco: " + nombreDisco);

        Thread.sleep(2000);
    }

    @And("Obtenemos el editor")
    public void obtenemosElEditor() throws InterruptedException {
        try {
            WebElement editorDelDiscoTitulo = WebDriverContext
                    .getDriver()
                    .findElement(By.xpath("(//*/th[contains(text(), 'Label')])[1]"));

            WebElement editorDelDisco = editorDelDiscoTitulo.findElement(By.xpath(".//following::td"));

            WebElementUtil.highlightElement(editorDelDisco, 2000);

            editor = editorDelDisco
                    .getText()
                    .split("\u2013")[0]
                    .trim();

        } catch (Exception ex) {
            editor = "";
        }

        logger.info("Editor: " + editor);
        Thread.sleep(2000);
    }

    @And("Obtenemos el formato")
    public void obtenemosElFormato() throws InterruptedException {
        try {
            WebElement formatoDelDiscoTitulo = WebDriverContext
                    .getDriver()
                    .findElement(By.xpath("(//*/th[contains(text(), 'Format')])[1]"));

            WebElement formatoDelDisco = formatoDelDiscoTitulo.findElement(By.xpath(".//following::td"));

            WebElementUtil.highlightElement(formatoDelDisco, 2000);

            tipo = formatoDelDisco
                    .getText()
                    .split(",")[0]
                    .trim();

            if (tipo.contains("x")) {
                tipo = tipo.split("x")[1]
                        .trim();
            }

            tipo = tipo + "s";

        } catch (Exception ex) {
            tipo = "";
        }

        logger.info("Tipo: " + tipo);
        Thread.sleep(2000);
    }

    @And("Obtenemos el pais")
    public void obtenemosElPais() throws InterruptedException {
        try {
            WebElement paisDelDiscoTitulo = WebDriverContext
                    .getDriver()
                    .findElement(By.xpath("(//*/th[contains(text(), 'Country')])[1]"));

            WebElement paisDelDisco = paisDelDiscoTitulo.findElement(By.xpath(".//following::td"));

            WebElementUtil.highlightElement(paisDelDisco, 2000);

            pais = paisDelDisco
                    .getText();
        } catch (Exception ex) {
            pais = "";
        }

        logger.info("Pais: " + pais);

        Thread.sleep(2000);
    }

    @And("Obtenemos el ano de publicacion")
    public void obtenemosElAnoDePublicacion() throws InterruptedException {
        boolean poseeTituloAno = poseeTituloAno();

        String tituloXPath = "Year";

        if (!poseeTituloAno) {
            tituloXPath = "Released";
        }

        try {
            WebElement anoDelDiscoTitulo = WebDriverContext
                    .getDriver()
                    .findElement(By.xpath("(//*/th[contains(text(), '" + tituloXPath + "')])[1]"));

            WebElement anoDelDisco = anoDelDiscoTitulo.findElement(By.xpath(".//following::td"));

            WebElementUtil.highlightElement(anoDelDisco, 2000);

            String[] anoSplit = anoDelDisco
                    .getText()
                    .split(" ");

            anoDisco = anoSplit[anoSplit.length - 1];
        } catch (Exception ex) {
            anoDisco = "";
        }

        logger.info("Ano: " + anoDisco);

        Thread.sleep(2000);
    }

    @And("Obtenemos los generos")
    public void obtenemosLosGeneros() throws InterruptedException {
        WebElement generosDelDiscoTitulo = WebDriverContext
                .getDriver()
                .findElement(By.xpath("(//*/th[contains(text(), 'Genre')])[1]"));

        WebElement generosDelDisco = generosDelDiscoTitulo.findElement(By.xpath(".//following::td"));

        WebElementUtil.highlightElement(generosDelDisco, 2000);

        generos = new ArrayList<>();

        List<String> listadoDeGeneros = Arrays.asList(generosDelDisco
                .getText()
                .toUpperCase()
                .split(","));

        listadoDeGeneros.forEach(genero -> generos.add(genero.trim()));

        generos.add(tipo.equalsIgnoreCase("VINYLS") ? "VINILOS" : tipo.toUpperCase());

        logger.info("Generos: " + generos);

        Thread.sleep(2000);
    }

    @And("Obtenemos el listado de canciones")
    public void obtenemosElListadoDeCanciones() throws InterruptedException {
        trackList = new ArrayList<>();

        WebElement tablaListadoCanciones = WebDriverContext
                .getDriver()
                .findElement(By.xpath("*//div[3]/section/div/table/tbody"));

        WebElementUtil.highlightElement(tablaListadoCanciones, 2000);

        Thread.sleep(2000);

        List<WebElement> listadoCanciones = tablaListadoCanciones.findElements(By.xpath("tr"));

        for (WebElement cancion : listadoCanciones) {
            WebElementUtil.highlightElement(cancion, 2000);

            WebElement posicionCancion = cancion.findElement(By.xpath("td"));
            WebElement nombreCancion = cancion.findElement(By.xpath("td[3]"));

            String nombre = nombreCancion
                    .getText()
                    .split("\n")[0];
            String posicion = posicionCancion
                    .getText();
            String duracion = "";

            try {
                WebElement duracionCancion = cancion.findElement(By.xpath("td[4]"));
                duracion = duracionCancion.getText();
            } catch (Exception ex) {

            }

            Thread.sleep(2000);

            logger.info("Cancion: " + posicion + " - " + nombre + " - " + duracion);

            trackList.add(Track
                    .builder()
                    .name(nombre)
                    .position(posicion)
                    .duration(duracion)
                    .build());
        }

        Thread.sleep(2000);
    }

    @And("Abrimos la imagen")
    public void abrimosLaImagen() throws InterruptedException {
        WebElement imagenSeleccionada = WebDriverContext
                .getDriver()
                .findElement(By.xpath("//*[@id='page']/div[1]/div[2]/div/div[1]/div/a"));

        WebElementUtil.highlightElement(imagenSeleccionada, 2000);

        Thread.sleep(2000);

        imagenSeleccionada.click();

        Thread.sleep(10000);
    }

    @And("Obtenemos las imagenes")
    public void obtenemosLasImagenes() throws InterruptedException {
        FileUtil.createFolder(DISKS_DIR);

        FileUtil.deleteFilesAndFolders(DISKS_DIR);

        Thread.sleep(2000);

        diskDir = DISKS_DIR + "/" + sku + "/";

        FileUtil.createFolder(diskDir);

        imagenes = new ArrayList<>();

        try {
            List<WebElement> imagenesADescargar = WebDriverContext
                    .getDriver()
                    .findElements(By.xpath("//*[@id='page']/div[3]/ul/li"));

            for (int i = 0; i < imagenesADescargar.size(); i++) {
                WebElement imagenAClickear = imagenesADescargar
                        .get(i)
                        .findElement(By.xpath("a/picture/img"));

                imagenAClickear.click();

                Thread.sleep(1000);

                WebElement imagen = WebDriverContext
                        .getDriver()
                        .findElement(By.xpath("//*[@id='image-gallery']/div/button/picture/img"));

                WebElementUtil.highlightElement(imagen, 2000);

                int numeroImagen = i + 1;

                String imagenUrl = imagen.getAttribute("src");

                String extension = imagenUrl.substring(imagenUrl.lastIndexOf("."));

                logger.info("URL Imagen " + numeroImagen + ": " + imagenUrl);

                ImageUtil.downloadImage(imagenUrl, diskDir + numeroImagen + extension);

                imagenes.add("assets/images/products/" + tipo.toLowerCase() + "/" + sku + "/" + numeroImagen + extension);

                Thread.sleep(2000);
            }
        } catch (Exception ex) {
            System.err.println("Error al descargar imagenes: " + ex.getMessage());
        }
    }

    @Then("Generamos el json de salida")
    public void generamosElJsonDeSalida() throws IOException {
        Product product = Product
                .builder()
                .sku(sku)
                .name(nombreDisco)
                .description(nombreDisco + ". Publicado en " + anoDisco)
                .author(nombreArtista)
                .publisher(editor)
                .yearCreated(anoDisco.isEmpty() ? null : Short.parseShort(anoDisco))
                .country(pais)
                .images(imagenes)
                .categories(generos)
                .trackList(trackList)
                .favorite(false)
                .build();

        String json = gson.toJson(product);

        logger.info("JSON Producto: \n" + json);

        File archivo = new File(diskDir + "result" + ".json");

        Files.write(archivo.toPath(), json.getBytes());
    }

    private boolean poseeTituloAno() {
        try {
            WebDriverContext
                    .getDriver()
                    .findElement(By.xpath("(//*/th[contains(string(), 'Year:')])[1]"));

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /*
    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/vinyls.json")));
        List<Product> productos = gson.fromJson(text, new TypeToken<List<Product>>() {}.getType());

        for (Product product: productos) {
            if(product.getPublisher() == null) {
                product.setPublisher("");
            }
            if(product.getCountry() == null) {
                product.setCountry("");
            }
            if(product.getTrackList() == null) {
                product.setTrackList(new ArrayList<>());
            }
        }

        String json = gson.toJson(productos);

        File archivo = new File(System.getProperty("user.dir") + "/result.json");

        Files.write(archivo.toPath(), json.getBytes());
    }*/
}
