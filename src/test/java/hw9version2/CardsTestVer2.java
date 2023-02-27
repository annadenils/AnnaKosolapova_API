package hw9version2;

import entity.BoardEntity;
import entity.CardEntity;
import entity.ListEntity;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;


public class CardsTestVer2 {
    private RestCardsService restCardsService;
    private RestBoardsService restBoardsService;


    @BeforeClass
    public void setup() {
        restBoardsService = RestBoardsService.getInstance();
        restCardsService = RestCardsService.getInstance();
    }

    @AfterClass
    public void close() {
        RestBoardsService restBoardsService = RestBoardsService.getInstance();
        Arrays.stream(restBoardsService.getBoards()).map(BoardEntity::id)
                .forEach(restBoardsService::deleteBoard);
    }

    @Test
    public void createCard() {
        BoardEntity board = restBoardsService.createBoard("newBoard");
        ListEntity list = restBoardsService.createList(board.id());
        CardEntity card = restCardsService.createCard(list.id());
        new Assertions().checkCode200();
        new Assertions().checkCardName(card, card.name());
    }

    @Test
    public void deleteCard() {
        BoardEntity board = restBoardsService.createBoard("newBoard");
        ListEntity list = restBoardsService.createList(board.id());
        CardEntity card = restCardsService.createCard(list.id());
        restCardsService.deleteCard(card.id());
        new Assertions().checkCode200();
        restCardsService.checkDeleteCard(card.id());
    }

    @Test
    public void checkCardWithWrongId() {
        BoardEntity board = restBoardsService.createBoard("newBoard");
        ListEntity list = restBoardsService.createList(board.id());
        CardEntity card = restCardsService.createCard(list.id());
        restCardsService.getCard("87");
        new Assertions().checkCode400();
    }
}
