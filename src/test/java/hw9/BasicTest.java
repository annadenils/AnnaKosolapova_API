package hw9;

import entity.BoardEntity;
import entity.CardEntity;
import entity.ChecklistEntity;
import entity.ListEntity;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.Endpoints;

import static io.restassured.RestAssured.given;


public class BasicTest {

    public static ResponseSpecification responseSpecification;
    public static RequestSpecification baseRequestSpecification;
    public static ResponseSpecification notFoundResponseSpecification;
    static BoardEntity board;
    static ListEntity list;
    static CardEntity card;
    ChecklistEntity checklist;


    @BeforeClass
    public static void setup() {
        var ApiToken = System.getenv("token");
        var ApiKey = System.getenv("key");
        RestAssured.baseURI = Endpoints.Basic_Url;

        baseRequestSpecification = new RequestSpecBuilder()
                .addQueryParam("key", ApiKey)
                .addQueryParam("token", ApiToken)
                .setContentType(ContentType.JSON).build();

        responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
                .expectStatusCode(200).build();

        notFoundResponseSpecification = new ResponseSpecBuilder().expectStatusCode(404).build();

    }

    @AfterClass
    public void close() {
        if (board != null) {
            deleteBoard(board.id());
        }
        RestAssured.reset();
        baseRequestSpecification = null;
    }

    protected void deleteBoard(String boardId) {
            given()
                    .spec(baseRequestSpecification)
                    .when().basePath(Endpoints.Board_ID_Url)
                    .pathParam("id", boardId)
                    .delete()
                    .then()
                    .log().all()
                    .spec(responseSpecification);
    }

}
