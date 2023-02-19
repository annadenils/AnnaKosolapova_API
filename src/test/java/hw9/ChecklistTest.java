package hw9;

import static io.restassured.RestAssured.given;

import entity.ChecklistEntity;
import org.testng.annotations.Test;
import utils.Endpoints;


public class ChecklistTest extends BasicTest {

    @Test
    public void createChecklist() {
        checklist = given().spec(baseRequestSpecification)
                .when()
                .queryParam("idCard", card.id())
                .basePath(Endpoints.Checklist_Url)
                .post()
                .then()
                .spec(responseSpecification)
                .log().all()
                .extract().body().as(ChecklistEntity.class);
    }

    @Test
    public void deleteChecklist() {
        createChecklist();
        given().spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Checklist_ID_Url)
                .pathParam("id", checklist.id())
                .delete()
                .then()
                .spec(responseSpecification);
        given().spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Checklist_ID_Url)
                .pathParam("id", checklist.id())
                .get()
                .then()
                .spec(notFoundResponseSpecification);
    }
}
