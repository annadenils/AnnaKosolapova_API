package hw9version2;

import entity.BoardEntity;
import entity.CardEntity;
import entity.ListEntity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;


public class CardsTestVer2 {
    RestCardsService restCardsService;
    BoardEntity board;
    CardEntity card;
    ListEntity list;


    @BeforeMethod
    public void setupCard() {
        restCardsService = RestCardsService.getInstance();
        board = RestBoardsService.getInstance().createBoard();
        list = RestBoardsService.getInstance().createList(board.id());
    }

    @AfterMethod
    public void close() {
        RestBoardsService restBoardsService = RestBoardsService.getInstance();
        Arrays.stream(restBoardsService.getBoards()).map(BoardEntity::id)
                .forEach(restBoardsService::deleteBoard);
    }

    @Test
    public void createCard() {
        card = restCardsService.createCard(list.id());
        new CardAssertions(card).checkCardName(card.name()).checkCardId(card.id());
    }

    @Test
    public void deleteCard() {
        createCard();
        restCardsService.deleteCard(card.id());
        restCardsService.checkDeleteCard(card.id());
    }
}
