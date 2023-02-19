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
import org.testng.annotations.*;
import utils.Endpoints;



public class BasicTest {

    public static ResponseSpecification responseSpecification;
    public static RequestSpecification baseRequestSpecification;
    public static ResponseSpecification notFoundResponseSpecification;
    static BoardEntity board;
    static ListEntity list;
    static CardEntity card;
    ChecklistEntity checklist;



    @BeforeMethod
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
        BoardTest.createBoard();
        BoardTest.createListOnTheBoard();
        CardsTest.createCard();
    }


    @AfterMethod
    public void close() {
        if (board != null) {
            ServiceObject.deleteBoardAfterTest(board.id());
        }
        RestAssured.reset();
        baseRequestSpecification = null;
    }

}
