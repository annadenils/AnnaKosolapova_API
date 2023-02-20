package hw9version2;

import entity.CardEntity;
import org.assertj.core.api.SoftAssertions;

public class CardAssertions {
    private final CardEntity card;
    SoftAssertions softAssertions = new SoftAssertions();

    public CardAssertions(CardEntity card) {
        this.card = card;
    }

    public CardAssertions checkCardName(String expectedName) {
        softAssertions.assertThat(card.name()).isEqualTo(expectedName);
        softAssertions.assertAll();
        return this;
    }

    public CardAssertions checkCardId(String expectedId) {
        softAssertions.assertThat(card.id()).isEqualTo(expectedId);
        softAssertions.assertAll();
        return this;
    }
}
