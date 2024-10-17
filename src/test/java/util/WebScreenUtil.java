package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class WebScreenUtil {
    public static void takeScreenshotAndSaveInFile(WebDriver webDriver, String filePath) {
        // Convierte el web driver en un TakeScreenShot
        TakesScreenshot takeScreenshot = ((TakesScreenshot) webDriver);

        // Crea un archivo de imagen a partir del TakeScreenShot
        byte[] bytesDelArchivo = takeScreenshot.getScreenshotAs(OutputType.BYTES);

        try {
            Files.write(Paths.get(filePath), bytesDelArchivo); // Crea la imagen en la ruta indicada
        } catch (IOException e) {
            System.err.println("Error al crear imagen: " + filePath);
        }
    }
    
    public static String takeScreenshotInBase64Format(WebDriver webDriver) {
        // Convierte el web driver en un TakeScreenShot
        TakesScreenshot takeScreenshot = ((TakesScreenshot) webDriver);

        return takeScreenshot.getScreenshotAs(OutputType.BASE64); // DEVUELVE LA IMAGEN EN BASE64
    }

    public static byte[] takeScreenshotInBytesFormat(WebDriver webDriver) {
        // Convierte el web driver en un TakeScreenShot
        TakesScreenshot takeScreenshot = ((TakesScreenshot) webDriver);

        return takeScreenshot.getScreenshotAs(OutputType.BYTES);
    }
}