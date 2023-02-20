package hw9version2;

import entity.BoardEntity;
import org.assertj.core.api.SoftAssertions;

public class BoardAssertions {
    private final BoardEntity board;

    SoftAssertions softAssertions = new SoftAssertions();

    public BoardAssertions(BoardEntity board) {
        this.board = board;
    }

    public BoardAssertions checkBoardName(String expectedName) {
        softAssertions.assertThat(board.nameBoard()).isEqualTo(expectedName);
        softAssertions.assertAll();
        return this;
    }

    public BoardAssertions checkBoardId(String expectedId) {
        softAssertions.assertThat(board.id()).isEqualTo(expectedId);
        softAssertions.assertAll();
        return this;
    }
}
