package de.ts.hackerang.pokerhands;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PokerCardTest {

    private static Stream<Arguments> compareArguments() {
        return Stream.of(
                Arguments.of("AC", "2C", 1),
                Arguments.of("2C", "AC", -1),
                Arguments.of("2D", "2C", 0),
                Arguments.of("QC", "TC", 1),
                Arguments.of("TC", "QC", -1),
                Arguments.of("TS", "TH", 0)
        );
    }

    private static Stream<Arguments> parseArguments() {
        return Stream.of(
                Arguments.of("2C", new PokerCard(CardValue.TWO, CardSuit.CLUBS)),
                Arguments.of("3C", new PokerCard(CardValue.THREE, CardSuit.CLUBS)),
                Arguments.of("4C", new PokerCard(CardValue.FOUR, CardSuit.CLUBS)),
                Arguments.of("5C", new PokerCard(CardValue.FIVE, CardSuit.CLUBS)),
                Arguments.of("6S", new PokerCard(CardValue.SIX, CardSuit.SPADES)),
                Arguments.of("7S", new PokerCard(CardValue.SEVEN, CardSuit.SPADES)),
                Arguments.of("8S", new PokerCard(CardValue.EIGHT, CardSuit.SPADES)),
                Arguments.of("9S", new PokerCard(CardValue.NINE, CardSuit.SPADES)),
                Arguments.of("TH", new PokerCard(CardValue.TEN, CardSuit.HEARTS)),
                Arguments.of("JH", new PokerCard(CardValue.JACK, CardSuit.HEARTS)),
                Arguments.of("QD", new PokerCard(CardValue.QUEEN, CardSuit.DIAMONDS)),
                Arguments.of("KD", new PokerCard(CardValue.KING, CardSuit.DIAMONDS)),
                Arguments.of("AD", new PokerCard(CardValue.ACE, CardSuit.DIAMONDS))
        );
    }

    @ParameterizedTest
    @MethodSource("compareArguments")
    void comparePokerCard(String firstCardString, String secondCardString, int expected) {

        PokerCard firstCard = new PokerCard(firstCardString);
        PokerCard secondCard = new PokerCard(secondCardString);

        assertThat(firstCard.compareTo(secondCard)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("parseArguments")
    void testPokerCard(String cardString, PokerCard expected) {

        PokerCard actual = new PokerCard(cardString);

        assertThat(actual).isEqualTo(expected);
    }


}
