package client;

import com.google.gson.Gson;
import enums.StepStatusEnum;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.ReportUtil;
import java.util.Arrays;

public class GenericClient {
    private static final Gson gson = new Gson();
    private static final RequestSpecification restAssuredClient = RestAssured
            .with()
            .filters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter())); // Agrega los filtros para el logeo de request, response, error en consola

    public static Response lastResponse;

    public static void get(final String url) {
        ReportUtil.addStep("Peticion GET", StepStatusEnum.PASSED, "Se realiza peticion tipo get a url: " + url);

        lastResponse = restAssuredClient
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract()
                .response();

        ReportUtil.addStep("Respuesta Peticion GET", StepStatusEnum.PASSED, String.format("La respuesta de la peticion es %d \n%s", lastResponse.getStatusCode(), lastResponse.getBody().prettyPrint() + "\n "));
    }

    public static void post(final String url, final Object body, final Class<?> classType) {
        ReportUtil.addStep("Peticion POST", StepStatusEnum.PASSED, "Se realiza peticion tipo post a url: " + url + " con body: " + gson.toJson(body, classType));

        lastResponse = restAssuredClient
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();

        ReportUtil.addStep("Respuesta Peticion POST", StepStatusEnum.PASSED, String.format("La respuesta de la peticion es %d \n%s", lastResponse.getStatusCode(), lastResponse.getBody().prettyPrint() + "\n "));
    }
}