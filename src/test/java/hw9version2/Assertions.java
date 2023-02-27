package hw9version2;

import entity.BoardEntity;
import entity.CardEntity;
import entity.ListEntity;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;

public class Assertions {

    SoftAssertions softAssertions = new SoftAssertions();

    public Assertions checkBoardName(BoardEntity board, String expectedName) {
        softAssertions.assertThat(board.nameBoard()).isEqualTo(expectedName);
        softAssertions.assertAll();
        return this;
    }

    public Assertions checkListName(ListEntity list, String expectedName) {
        softAssertions.assertThat(list.name()).isEqualTo(expectedName);
        softAssertions.assertAll();
        return this;
    }

    public Assertions checkCardName(CardEntity card, String expectedName) {
        softAssertions.assertThat(card.name()).isEqualTo(expectedName);
        softAssertions.assertAll();
        return this;
    }

    public Assertions checkCode400() {
        softAssertions.assertThat(HttpStatus.SC_BAD_REQUEST);
        softAssertions.assertAll();
        return this;
    }

    public Assertions checkCode200() {
        softAssertions.assertThat(HttpStatus.SC_OK);
        softAssertions.assertAll();
        return this;
    }
}
