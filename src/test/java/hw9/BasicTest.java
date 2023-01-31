package hw9;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BasicTest {

    public static ResponseSpecification responseSpecification;
    public static RequestSpecification baseRequestSpecification;


    @BeforeClass
    public static void setup() {
        var ApiToken = System.getenv("token");
        var ApiKey = System.getenv("key");
        RestAssured.baseURI = "https://api.trello.com/1";

        baseRequestSpecification = new RequestSpecBuilder()
                .addQueryParam("key", ApiKey)
                .addQueryParam("token", ApiToken)
                .setContentType(ContentType.JSON).build();

        responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
                .expectStatusCode(200).build();

    }

    @AfterClass
    public void close() {
        RestAssured.reset();
        baseRequestSpecification = null;
    }
}
