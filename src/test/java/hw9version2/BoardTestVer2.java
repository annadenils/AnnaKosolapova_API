package hw9version2;

import entity.BoardEntity;
import entity.ListEntity;
import org.testng.annotations.*;

import java.util.Arrays;


public class BoardTestVer2 {
    private RestBoardsService restBoardsService;

    @BeforeClass
    public void setup() {
        restBoardsService = RestBoardsService.getInstance();
    }

    @AfterClass
    public void close() {
        Arrays.stream(restBoardsService.getBoards()).map(BoardEntity::id)
                .forEach(restBoardsService::deleteBoard);
    }

    @Test
    public void createBoard() {
        BoardEntity board = restBoardsService.createBoard("NewBoard");
        new Assertions().checkCode200();
    }

    @Test
    public void createBoardWithRandomName() {
        BoardEntity board = restBoardsService.createBoardWithRandomName();
        new Assertions().checkCode200();
        new Assertions().checkBoardName(board, board.nameBoard());
    }

    @Test
    public void deleteBoard() {
        BoardEntity board = restBoardsService.createBoard("newBoard");
        restBoardsService.deleteBoard(board.id());
        new Assertions().checkCode200();
        restBoardsService.checkDeleteBoard(board.id());
    }

    @Test
    public void createList() {
        BoardEntity board = restBoardsService.createBoard("newBoard");
        ListEntity list = restBoardsService.createList(board.id());
        new Assertions().checkCode200();
        new Assertions().checkListName(list, list.name());
    }

}
