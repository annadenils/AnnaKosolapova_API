package hw9version2;

import entity.BoardEntity;
import entity.ListEntity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;


public class BoardTestVer2 {
    static RestBoardsService restBoardsService;
    static BoardEntity board;
    static ListEntity list;

    @BeforeMethod
    public void setup() {
        restBoardsService = RestBoardsService.getInstance();
        createBoard();
    }

    @AfterMethod
    public void close() {
        Arrays.stream(restBoardsService.getBoards()).map(BoardEntity::id)
                .forEach(restBoardsService::deleteBoard);
    }

    @Test
    public void createBoard() {
        board = restBoardsService.createBoard();
        new BoardAssertions(board).checkBoardId(board.id()).checkBoardName(board.nameBoard());
    }

    @Test
    public void deleteBoard() {
        restBoardsService.deleteBoard(board.id());
        restBoardsService.checkDeleteBoard(board.id());
    }

    @Test
    public void createList() {
        list = restBoardsService.createList(board.id());
    }

}
