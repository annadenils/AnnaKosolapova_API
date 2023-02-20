package hw9version2;

import entity.BoardEntity;
import entity.ListEntity;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import utils.Endpoints;

import java.util.HashMap;

public class RestBoardsService extends BaseService {
    private static RestBoardsService restBoardsService;
    private RestBoardsService() {}

    public static RestBoardsService getInstance() {
        if (restBoardsService == null) {
            restBoardsService = new RestBoardsService();
        }
        return restBoardsService;
    }

    static String boardName = RandomStringUtils.random(40, true, true);
    static String listName = RandomStringUtils.random(40, true, true);

    public BoardEntity createBoard() {
        HashMap<String, String> boardParameters = new HashMap<>();
        boardParameters.put("name", boardName);

        Response response = new BaseService().post(boardParameters, Endpoints.Board_Url);
        response.then().statusCode(Matchers.equalTo(HttpStatus.SC_OK));
        return response.getBody().as(BoardEntity.class);
    }

    public BoardEntity deleteBoard(String boardId) {
        return new BaseService().delete(String.format(Endpoints.Board_ID_Url_v2, boardId)).getBody().as(BoardEntity.class);
    }

    public BoardEntity[] getBoards() {
        return new BaseService().get(Endpoints.Board_All_Url).getBody().as(BoardEntity[].class);
    }

    public ValidatableResponse checkDeleteBoard(String boardId) {
        return new BaseService().get404(String.format(Endpoints.Board_ID_Url_v2, boardId));
    }

    public ListEntity createList(String boardId) {
        HashMap<String, String> boardParameters = new HashMap<>();
        boardParameters.put("name", listName);
        boardParameters.put("idBoard", boardId);

        Response response = new BaseService().post(boardParameters, Endpoints.List_Url);
        response.then().statusCode(Matchers.equalTo(HttpStatus.SC_OK));
        return response.getBody().as(ListEntity.class);
    }
}
