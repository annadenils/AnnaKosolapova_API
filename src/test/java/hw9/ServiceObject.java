package hw9;

import utils.Endpoints;

import static hw9.BasicTest.baseRequestSpecification;
import static hw9.BasicTest.responseSpecification;
import static io.restassured.RestAssured.given;

public class ServiceObject {

    public static void deleteBoardAfterTest(String boardId) {
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
