package hw9version2;

import entity.CardEntity;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import utils.Endpoints;

import java.util.HashMap;

public class RestCardsService {

    static String cardName = RandomStringUtils.random(40, true, true);

    private static RestCardsService restCardsService;
    private RestCardsService() {};
    public static RestCardsService getInstance() {
        if (restCardsService == null) {
            restCardsService = new RestCardsService();
        }
        return restCardsService;
    }

    public CardEntity createCard(String listId) {
        HashMap<String, String> cardParameters = new HashMap<>();
        cardParameters.put("idList", listId);
        cardParameters.put("name", cardName);
        Response response = new BaseService().post(cardParameters, Endpoints.Card_Url);
        response.then().statusCode(Matchers.equalTo(HttpStatus.SC_OK));
        return response.getBody().as(CardEntity.class);
    }

    public CardEntity deleteCard(String cardId) {
        return new BaseService().delete(String.format(Endpoints.Card_ID_Url_v2, cardId)).getBody().as(CardEntity.class);
    }

    public ValidatableResponse checkDeleteCard(String cardId) {
        return new BaseService().get404(String.format(Endpoints.Card_ID_Url_v2, cardId));
    }

}
