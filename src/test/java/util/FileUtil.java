package util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    /**
     * Metodo encargado de eliminar archivo o carpeta, en caso de ser carpeta elimina los archivos contenidos en la carpeta y finalmente la carpeta
     *
     * @param filePath Ruta del archivo
     * @author Byron Villegas Moya
     */
    public static void deleteFilesAndFolders(String filePath) {
        File file = new File(filePath); // OBTIENE EL ARCHIVO O CARPETA DEL PATH

        if(file.exists()) { // SI EL ARCHIVO EXISTE APLICA LA ELIMINACION
            for (File childFile : file.listFiles()) { // SI POSEE ARCHIVOS O CARPETAS DENTRO ITERA UNO POR UNO
                if (childFile.isDirectory()) { // SI ES DIRECTORIO SE EJECUTA EL MISMO METODO
                    deleteFilesAndFolders(childFile.getPath());
                }
                childFile.delete(); // AL FINAL SIEMPRE SE ELIMINA EL ARCHIVO O CARPETA
            }

            file.delete(); // SE ELIMINA EL ARCHIVO O CARPETA PRINCIPAL
        }
    }

    public static void createFolder(String folderPath) {
        File diskDirFile = new File(folderPath);

        if (!diskDirFile.exists()) {
            diskDirFile.mkdirs();
        }
    }
    
    public static String getFileContent(String filePath) {
        try {
            Path path = Paths.get(filePath);

            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();

            return data.trim();
        } catch (Exception ex) {
            return "";
        }
    }
}