package hw9;

import entity.BoardEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;


public class TestApi extends BasicTest{


    @Test
    public void testBoard() {
        String newBoardName = RandomStringUtils.random(40, true, true);
        String boardsUrl = "/boards";
        given().spec(baseRequestSpecification)
                .when()
                .queryParam("name", newBoardName)
                .basePath(boardsUrl)
                .post()
                .then()
                .spec(responseSpecification)
                .extract().body().as(BoardEntity.class);
    }
}
