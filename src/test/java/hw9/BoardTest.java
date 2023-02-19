package hw9;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import entity.BoardEntity;
import entity.ListEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;
import utils.Endpoints;


public class BoardTest extends BasicTest {

    static String boardName = RandomStringUtils.random(40, true, true);

    static String listName = RandomStringUtils.random(40, true, true);

    @Test()
    public static void createBoard() {
        board = given().spec(baseRequestSpecification)
                .when()
                .queryParam("name", boardName)
                .basePath(Endpoints.Board_Url)
                .post()
                .then()
                .body("name", equalTo(boardName))
                .spec(responseSpecification).log().all()
                .extract().body().as(BoardEntity.class);
    }

    @Test
    public void getInfoAboutBoard() {
        board = given().spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Board_ID_Url)
                .pathParam("id", board.id())
                .get()
                .then()
                .spec(responseSpecification)
                .body("name", equalTo(boardName))
                .log().all()
                .extract().body().as(BoardEntity.class);
    }

    @Test
    public void editBoard() {
        String descriptionForBoard = RandomStringUtils.random(100, true, true);
        var boardNew = BoardEntity.builder().desc(descriptionForBoard).id(board.id()).build();
        board = given().spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Board_ID_Url)
                .pathParam("id", board.id())
                .body(boardNew)
                .put()
                .then()
                .body("desc", equalTo(descriptionForBoard))
                .body("name", equalTo(boardName))
                .spec(responseSpecification)
                .log().all()
                .extract().body().as(BoardEntity.class);
    }

    @Test
    public static void deleteBoard() {
        given()
                .spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Board_ID_Url)
                .pathParam("id", board.id())
                .delete()
                .then().spec(responseSpecification);
        given()
                .spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Board_ID_Url)
                .pathParam("id", board.id())
                .get()
                .then()
                .spec(notFoundResponseSpecification);
        board = null;
    }

    @Test
    public static void createListOnTheBoard() {
        list = given()
                .spec(baseRequestSpecification)
                .when()
                .queryParam("name", listName)
                .queryParam("idBoard", board.id())
                .basePath(Endpoints.List_Url)
                .post()
                .then()
                .body("name", equalTo(listName))
                .spec(responseSpecification).log().all()
                .extract().body().as(ListEntity.class);
    }
}
