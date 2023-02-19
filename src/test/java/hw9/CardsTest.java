package hw9;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import entity.CardEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;
import utils.Endpoints;

public class CardsTest extends BasicTest {

    static String cardName = RandomStringUtils.random(40, true, true);

    @Test
    public static void createCard() {
        card = given()
                .spec(baseRequestSpecification)
                .when()
                .queryParam("idList", list.id())
                .queryParam("name", cardName)
                .basePath(Endpoints.Card_Url)
                .post()
                .then()
                .body("name", equalTo(cardName))
                .spec(responseSpecification).log().all()
                .extract().body().as(CardEntity.class);
    }

    @Test
    public void updateCard() {
        var newCard = CardEntity.builder().id(card.id()).name(cardName).build();
        card =  given()
                .spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Card_ID_Url)
                .pathParam("id", card.id())
                .body(newCard)
                .put()
                .then()
                .spec(responseSpecification)
                .body("name", equalTo(cardName))
                .log().all()
                .extract().body().as(CardEntity.class);
    }

    @Test
    public void getInfoAboutCard() {
        card = given()
                .spec(baseRequestSpecification)
                .basePath(Endpoints.Card_ID_Url)
                .pathParam("id", card.id())
                .get()
                .then()
                .spec(responseSpecification)
                .body("name", equalTo(cardName))
                .log().all()
                .extract().body().as(CardEntity.class);
    }

    @Test
    public void deleteCard() {
        given().spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Card_ID_Url)
                .pathParam("id", card.id())
                .delete()
                .then()
                .spec(responseSpecification);
        given().spec(baseRequestSpecification)
                .when()
                .basePath(Endpoints.Card_ID_Url)
                .pathParam("id", card.id())
                .get()
                .then()
                .spec(notFoundResponseSpecification);
        card = null;
    }

}
