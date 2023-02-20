package hw9version2;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import utils.Endpoints;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseService {

    public static RequestSpecification requestSpecification;

    public BaseService() {
        var ApiToken = System.getenv("token");
        var ApiKey = System.getenv("key");

        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(Endpoints.Basic_Url)
                .addQueryParam("key", ApiKey)
                .addQueryParam("token", ApiToken)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setContentType(ContentType.JSON).build();
    }

    public Response get(String uri) {
        Response response = given(requestSpecification).get(uri);
        response.then().statusCode(Matchers.equalTo(HttpStatus.SC_OK));
        return response;
    }

    public ValidatableResponse get404(String uri) {
        return given(requestSpecification).get(uri).then().statusCode(Matchers.equalTo(HttpStatus.SC_NOT_FOUND));
    }

    public Response post(HashMap<String, String> parameters, String uri) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            requestSpecification.queryParam(parameter.getKey(), parameter.getValue());
        }
        return given(requestSpecification
                .header("Content-Type", "application/json"))
                .post(uri);
    }

    public Response delete(String uri) {
        return given(requestSpecification).delete(uri);
    }
}

