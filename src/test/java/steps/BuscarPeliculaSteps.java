package steps;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import context.WebDriverContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Movie;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.FileUtil;
import util.ImageUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BuscarPeliculaSteps {
    private static final Gson gson = new Gson();
    private static List<Movie> movies;

    @Given("La url de la pagina de peliculas {string}")
    public void la_url_de_la_pagina_de_peliculas(String url) {
        WebDriverContext
                .getDriver()
                .get(url);
    }

    @When("Buscamos los datos de las peliculas {string}")
    public void buscamos_los_datos_de_las_peliculas(String archivoPeliculas) {
        String fileContent = FileUtil.getFileContent("src/test/resources/" + archivoPeliculas);

        System.out.println(fileContent);

        ArrayList<String> peliculas = gson.fromJson(fileContent, TypeToken.get(ArrayList.class));

        System.out.println(peliculas);

        movies = new ArrayList<>();

        for (String pelicula: peliculas) {
            try {
                System.out.println("Pelicula actual: " + pelicula);

                WebElement barraBusqueda = WebDriverContext
                        .getDriver()
                        .findElement(By.xpath("//*[@id='s']"));

                barraBusqueda.sendKeys(pelicula);

                Thread.sleep(1000);

                WebElement botonBusqueda = WebDriverContext
                        .getDriver()
                        .findElement(By.xpath("//*[@id='searchsubmit']"));

                botonBusqueda.click();

                WebElement peliculaASeleccionar = WebDriverContext
                        .getDriver()
                        .findElement(By.xpath("//*[@class='pelicula'][1]"));

                peliculaASeleccionar.click();

                Thread.sleep(1000);

                WebElement titulo = WebDriverContext
                        .getDriver()
                        .findElement(By.xpath("//*[@id='marco-post']/h2/a"));

                WebElement textoSipnosis = WebDriverContext
                        .getDriver()
                        .findElement(By.xpath("//*[@id='mmedia']/div[1]/p[2]"));

                WebElement textoDescripcion = WebDriverContext
                        .getDriver()
                        .findElement(By.xpath("//*[@id='mmedia']/div[1]/p[3]"));

                System.out.println(textoDescripcion);

                Movie movie = new Movie();

                movie.setTitle(titulo.getText().split("\\[")[0].trim());
                movie.setSypnosis(textoSipnosis.getText().trim());
                movie.setImage("");

                ArrayList<String> detalles = new ArrayList<>(Arrays.asList(textoDescripcion
                        .getText()
                        .split("\n")));

                setOriginalTitle(detalles, movie);
                setYear(detalles, movie);
                setDuration(detalles, movie);
                setFormat(detalles, movie);
                setResolution(detalles, movie);
                setSize(detalles, movie);
                setGenres(detalles, movie);
                setLanguages(detalles, movie);

                //setImage(movie);

                movies.add(movie);

            } catch(Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    private void setOriginalTitle(ArrayList<String> detalles, Movie movie) {
        Optional<String> originalTitleOptional = detalles
                .stream()
                .filter(originalTitle -> originalTitle.contains("Titulo"))
                .findFirst();

        originalTitleOptional.ifPresent(originalTitle -> movie.setOriginalTitle(originalTitle.split(":")[1].trim().replace(",", ".")));
    }

    private void setYear(ArrayList<String> detalles, Movie movie) {
        Optional<String> yearOptional = detalles
                .stream()
                .filter(originalTitle -> Pattern.matches("\\d+", originalTitle.split(":")[1].trim()))
                .findFirst();

        yearOptional.ifPresent(year -> {
            try {
                movie.setYear(Integer.parseInt(year.split(":")[1].trim()));
            } catch (Exception ex) {
                System.err.println("Error al obtener año " + year + " " + ex.getMessage());
            }
        });
    }

    private void setDuration(ArrayList<String> detalles, Movie movie) {
        Optional<String> durationOptional = detalles
                .stream()
                .filter(duration -> duration.contains("Duraci"))
                .findFirst();

        durationOptional.ifPresent(duration -> {
            try {
                movie.setDuration(Integer.parseInt(duration.split(":")[1].trim().split(" ")[0]));
                movie.setDurationType(duration.split(":")[1].trim().split(" ")[1].replace("minutos", "mm"));
            } catch (Exception ex) {
                System.err.println("Error al obtener duracion "+ duration + " "+ ex.getMessage());
            }
        });
    }

    private void setFormat(ArrayList<String> detalles, Movie movie) {
        Optional<String> formatOptional = detalles
                .stream()
                .filter(format -> format.contains("Formato"))
                .findFirst();

        formatOptional.ifPresent(format -> movie.setFormat(format.split(":")[1].trim().toLowerCase()));
    }

    private void setResolution(ArrayList<String> detalles, Movie movie) {
        Optional<String> resolutionOptional = detalles
                .stream()
                .filter(resolution -> resolution.contains("Resoluci"))
                .findFirst();

        resolutionOptional.ifPresent(format -> {
            String resolution = format.split(":")[1].trim();

            try {
                movie.setResolutionWidth(Integer.parseInt(resolution.substring(0, 4)));
                movie.setResolutionHeight(Integer.parseInt(resolution.substring(5, resolution.length())));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    private void setSize(ArrayList<String> detalles, Movie movie) {
        Optional<String> sizeOptional = detalles
                .stream()
                .filter(size -> size.contains("Peso"))
                .findFirst();

        sizeOptional.ifPresent(size -> {
            try {
                movie.setSize(Double.parseDouble(size.split(":")[1].trim().replace(",", ".").split(" ")[0]));
                movie.setSizeType(size.split(":")[1].trim().split(" ")[1]);
            } catch(Exception ex) {
                System.err.println("Error al obtener tamaño: " + size + " " + ex.getMessage());
            }
        });
    }

    private void setGenres(ArrayList<String> detalles, Movie movie) {
        Optional<String> genresOptional = detalles
                .stream()
                .filter(genres -> genres.contains("nero"))
                .findFirst();

        genresOptional.ifPresent(genresValue -> {
            ArrayList<String> genres = new ArrayList<>(Arrays.asList(genresValue.split(":")[1].replace(".", "").replace("|", " ").trim().split(" ")));

            movie.setGenres(genres.stream().filter(genre -> !genre.trim().isEmpty()).collect(Collectors.toList()));
        });
    }

    private void setLanguages(ArrayList<String> detalles, Movie movie) {
        Optional<String> languagesOptional = detalles
                .stream()
                .filter(languages -> languages.contains("Idioma"))
                .findFirst();

        languagesOptional.ifPresent(languagesValue -> {
            ArrayList<String> languages = new ArrayList<>(Arrays.asList(languagesValue.split(":")[1].split(" ")));

            List<String> excludes = Arrays.asList("", "(5.1)", "+", "Subs", "–");

            movie.setLanguages(languages.stream().filter(language -> !excludes.contains(language.trim())).collect(Collectors.toList()));
        });
    }

    private void setImage(Movie movie) throws InterruptedException {
        WebDriverContext
                .getDriver()
                .get("https://www.imdb.com");
        if(movie.getOriginalTitle() != null) {
            try {
                Thread.sleep(4000);

                WebElement barraBusqueda = WebDriverContext.getDriver().findElement(By.xpath("//*[@id='suggestion-search']"));
                barraBusqueda.sendKeys(movie.getOriginalTitle());

                Thread.sleep(4000);

                WebElement peliculaEncontrada = WebDriverContext.getDriver().findElement(By.xpath("//*[@id='react-autowhatever-navSuggestionSearch--item-0']/a"));

                peliculaEncontrada.click();

                WebElement imagen = WebDriverContext.getDriver().findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[3]/div[1]/div[1]/div/div[1]/img"));

                String imagenUrl = imagen.getAttribute("src");

                String extension = imagenUrl.substring(imagenUrl.lastIndexOf("."));

                System.out.println("URL Imagen: " + imagenUrl);

                FileUtil.createFolder(System.getProperty("user.dir") + "/movies");

                ImageUtil.downloadImage(imagenUrl, System.getProperty("user.dir") + "/movies/" + movie.getTitle() + extension);

                movie.setImage(movie.getTitle() + extension);

                Thread.sleep(4000);

            } catch (Exception ex) {
                System.out.println("No se pudo descargar la imagen: " + ex.getMessage());
            }
        }
    }

    @Then("Creamos el archivo {string}")
    public void creamosElArchivo(String archivoACrear) {
        String jsonData = gson.toJson(movies);

        try {
            Files.write(Paths.get(System.getProperty("user.dir") + "/" + archivoACrear), jsonData.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            Assert.fail("Archivo no creado "+archivoACrear);
        }
    }
}