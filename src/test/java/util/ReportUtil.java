package util;

import context.WebDriverContext;
import enums.StepStatusEnum;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.cucumber.java.Scenario;
import model.Step;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class ReportUtil {
    private static final Configuration freeMarkerConfiguration;
    private static final String FILE_HTML_EXTENSION = ".html";
    private static final String FILE_PDF_EXTENSION = ".pdf";
    private static final String EVIDENCE_FOLDER_PATH = System.getProperty("user.dir") + "/evidencias/";
    private static String featureFileName;
    private static String featureName;
    private static int testNumber = 1;
    private static String testName;
    private static String testType;
    private static String testStatus;
    private static LocalDateTime startTime;
    private static LocalDateTime endTime;
    private static String executionTime;
    private static LocalDate executionDate;
    private static List<Step> steps;

    static { // SE EJECUTA AL INICIAR LA CLASE
        freeMarkerConfiguration = new Configuration(Configuration.VERSION_2_3_32);

        try {
            freeMarkerConfiguration.setDirectoryForTemplateLoading(new File("src/test/resources/templates")); // RUTA DONDE SE ENCUENTRAN LOS REPORTES.HTML
        } catch (IOException e) {
            throw new IllegalArgumentException("Error no se encontro la carpeta templates para la carga de templates");
        }

        freeMarkerConfiguration.setDefaultEncoding("UTF-8");
        freeMarkerConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freeMarkerConfiguration.setLogTemplateExceptions(false);
        freeMarkerConfiguration.setWrapUncheckedExceptions(true);
        freeMarkerConfiguration.setFallbackOnNullLoopVariable(false);
        freeMarkerConfiguration.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
    }

    public static void setStartTestInformation(Scenario scenario) {
        setStartData(scenario);
    }

    private static void setStartData(Scenario scenario) {
        startTime = LocalDateTime.now();

        String numero = String.format("%02d.-", testNumber);

        if (testName != null && testName.contains(scenario.getName())) {
            testName = scenario.getName();
            testName = numero + testName + "-(Example " + testNumber + ")";
            testNumber++;
        } else {
            testNumber = 1;
            testName = scenario.getName();
            testName = numero + testName + "-(Example " + testNumber + ")";
        }

        setFeatureFileName(scenario);
        setFeatureName();
        setTestType(scenario);

        executionDate = LocalDate.now();
        steps = new ArrayList<>();
    }

    /**
     * Metodo encargado de setear el nombre del archivo del feature
     *
     * @author Byron Villegas Moya
     */
    private static void setFeatureFileName(Scenario scenario) {
        String featurePath = scenario.getUri().getPath();
        featureFileName = featurePath
                .substring(featurePath.lastIndexOf("/"))  // SACAMOS LA POSICION DEL ULTIMO /
                .replace("/", ""); // REEMPLAZAMOS  / POR NADA PARA QUITARLO
    }

    /**
     * Metodo encargado de setear el nombre del feature
     *
     * @author Byron Villegas Moya
     */
    private static void setFeatureName() {
        File featureFile = new File("src/test/resources/features/" + featureFileName);

        featureName = "No encontrado";

        try {
            List<String> lineas = Files.readAllLines(featureFile.toPath());
            for (String linea : lineas) {
                if (linea.contains("Feature:")) {
                    featureName = linea
                            .substring(linea.indexOf("Feature:")) // SACAMOS LA POSICION DE FEATURE:
                            .replace("Feature:", "") // REEMPLAZAMOS FEATURE: POR NADA PARA QUITARLO
                            .trim(); // QUITAMOS LOS ESPACIOS SOBRANTES
                }
            }
        } catch (IOException ex) {
            System.err.println("Error no se encontro el archivo: " + featureFileName + ex.getMessage());
        }
    }

    /**
     * Metodo encargado de setear el tipo de test
     *
     * @param scenario Test que acaba de iniciar
     * @author Byron Villegas Moya
     */
    private static void setTestType(Scenario scenario) {
        String firstTagName = "No definido";

        if (!scenario.getSourceTagNames().isEmpty()) {
            firstTagName = scenario
                    .getSourceTagNames()
                    .stream()
                    .findFirst()
                    .get();
        }

        switch (firstTagName) {
            case "@Web":
                testType = "Web";
                break;
            case "@Microservice":
                testType = "Microservicio";
                break;
        }
    }

    public static void addStep(final String stepName, final StepStatusEnum stepStatus, final String stepDescription) {
        Step stepToAdd = Step
                .builder()
                .name(stepName)
                .status(stepStatus.name())
                .description(stepDescription)
                .build();

        if (WebDriverContext.getDriver() != null) { // SIGNIFICA QUE ESTA AUTOMATIZANDO WEB Y APLICA TOMAR CAPTURA
            stepToAdd.setImage(WebScreenUtil.takeScreenshotInBase64Format(WebDriverContext.getDriver()));
        }

        steps.add(stepToAdd);
    }

    /**
     * Metodo encargado de setear la informacion del termino del test y posteriormente generar el reporte
     *
     * @param scenario Test que acaba de terminar
     * @author Byron Villegas Moya
     */
    public static void setTestFinishedInformation(Scenario scenario) {
        setTestFinishedData(scenario);
        generateHtmlReport();
        generatePdfReport();
    }

    /**
     * Metodo encargado de setear la informacion del termino del test
     *
     * @param scenario Test que acaba de terminar
     * @author Byron Villegas Moya
     */
    private static void setTestFinishedData(Scenario scenario) {
        endTime = LocalDateTime.now();
        executionTime = ChronoUnit.SECONDS.between(startTime, endTime) + " " + "segundos";
        testStatus = scenario.getStatus().name();
    }

    /**
     * Metodo encargado de generar el reporte de cada test
     *
     * @author Byron Villegas Moya
     */
    private static void generateHtmlReport() {
        try {
            Template template = freeMarkerConfiguration.getTemplate("report.html"); // BUSCAMOS EL TEMPLATE POR EL NOMBRE DEL ARCHIVO.HTML

            StringWriter stringWriter = new StringWriter(); // PARA OBTENER EN FORMATO STRING HTML EL TEMPLATE A PROCESAR
            template.process(generateParameters(), stringWriter); // PROCESA EL TEMPLATE CON LOS PARAMETROS
            String html = stringWriter.toString(); // NOS DEVUELVE EL STRING

            createEvidenceFolder(); // CREA LA CARPETA DE EVIDENCIAS
            createFeatureFolder(); // CREA LA CARPETA DEL FEATURE

            createReportFile(html); // CREA EL ARCHIVO DE REPORTE DE PRUEBAS

        } catch (Exception ex) {
            System.err.println("Error al procesar template: " + ex.getMessage());
        }
    }

    /**
     * Metodo encargado de generar los parametros para procesar el template
     *
     * @return Los parametros seteados
     * @author Byron Villegas Moya
     */
    private static Map<String, Object> generateParameters() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("featureFileName", featureFileName);
        parameters.put("featureName", featureName);
        parameters.put("testName", testName);
        parameters.put("userName", GitUtil.getCurrentUsername());
        parameters.put("userEmail", GitUtil.getCurrentUserEmail());
        parameters.put("currentBranch", GitUtil.getCurrentBranch());
        parameters.put("currentRepositoryUrl", GitUtil.getCurrentRepositoryUrl());
        parameters.put("testType", testType);
        parameters.put("testStatus", testStatus);
        parameters.put("browserName", WebDriverContext.browserName);
        parameters.put("environment", "QA");
        parameters.put("testDuration", executionTime);
        parameters.put("executionDate", DateTimeFormatter.ofPattern("dd-MM-yyyy").format(executionDate));
        parameters.put("startTime", DateTimeFormatter.ofPattern("HH:mm:ss").format(startTime));
        parameters.put("endTime", DateTimeFormatter.ofPattern("HH:mm:ss").format(endTime));
        parameters.put("steps", steps);

        return parameters;
    }

    /**
     * Metodo encargado de crear la carpeta de evidencias
     *
     * @author Byron Villegas Moya
     */
    private static void createEvidenceFolder() {
        File carpetaEvidencia = new File(EVIDENCE_FOLDER_PATH);

        if (!carpetaEvidencia.exists()) { // SOLO SI NO EXISTE CREA LA CARPETA
            carpetaEvidencia.mkdir(); // CREA LA CARPETA
        }
    }

    /**
     * Metodo encargado de crear la carpeta del feature
     *
     * @author Byron Villegas Moya
     */
    private static void createFeatureFolder() {
        File carpetaFeature = new File(EVIDENCE_FOLDER_PATH + "/" + featureName);

        if (!carpetaFeature.exists()) { // SOLO SI NO EXISTE CREA LA CARPETA
            carpetaFeature.mkdir(); // CREA LA CARPETA
        }
    }

    /**
     * Metodo encargado de crear el archivo de reporte del test
     *
     * @param html Html generado mediante el template y parametros
     * @author Byron Villegas Moya
     */
    private static void createReportFile(final String html) {
        String nombreArchivoACrear = testName + "-" + testStatus + FILE_HTML_EXTENSION;
        String rutaArchivoACrear = EVIDENCE_FOLDER_PATH + "/" + featureName + "/" + nombreArchivoACrear;

        try {
            Files.write(Paths.get(rutaArchivoACrear), html.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Error al crear archivo de reporte: " + nombreArchivoACrear);
        }
    }

    private static void generatePdfReport() {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = ReportUtil.class.getResourceAsStream("/reports/report.jrxml");

            Map<String, Object> parameters = generateParameters();
            parameters.put("steps", new JRBeanCollectionDataSource(steps));

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(1));

            JRPdfExporter jrPdfExporter = new JRPdfExporter();
            jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

            jrPdfExporter.exportReport();

            String nombreArchivoACrear = testName + "-" + testStatus + FILE_PDF_EXTENSION;
            String rutaArchivoACrear = EVIDENCE_FOLDER_PATH + "/" + featureName + "/" + nombreArchivoACrear;

            try (FileOutputStream stream = new FileOutputStream(rutaArchivoACrear)) {
                stream.write(byteArrayOutputStream.toByteArray());
            }

        } catch (JRException | IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error al generar reporte: " + ex.getMessage());
        }
    }
}