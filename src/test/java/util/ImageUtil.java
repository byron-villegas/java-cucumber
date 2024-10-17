package util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageUtil {

    public static String readContentOfImage(byte[] image) {
        try {
            String proyectPath = System.getProperty("user.dir");
            String tempPath = proyectPath + "/temp";
            String imagePath = tempPath + "/image.png";
            String tessdataPath = proyectPath + "/tessdata";

            File tempFolder = new File(tempPath);

            if (!tempFolder.exists()) {
                tempFolder.mkdirs();
            }

            Files.write(Paths.get(imagePath), image);

            File imageFile = new File(imagePath);

            ITesseract instance = new Tesseract();
            instance.setDatapath(tessdataPath);

            String result = instance.doOCR(imageFile);

            imageFile.delete();

            return result;
        } catch (TesseractException | IOException ex) {
            return "";
        }
    }

    public static void downloadImage(String url, String imagePath) {
        try {
            URL imageURL = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) imageURL
                    .openConnection();
            httpURLConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0");

            BufferedImage saveImage = ImageIO.read(httpURLConnection.getInputStream());

            String format = imagePath
                    .substring(imagePath.lastIndexOf("."))
                    .replace(".", "");

            ImageIO.write(saveImage, format, new File(imagePath));

        } catch (IOException ex) {
            System.err.println("Error al descargar imagen: " + ex.getMessage());
        }
    }
}