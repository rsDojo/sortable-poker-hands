package de.ts.hackerang.pokerhands;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PokerHandTest {

    private static Stream<Arguments> arguments() {
        return Stream.of(
                Arguments.of("2C 3C 4C 5C 6C", "2C 3C 4C 5C 6C", 0),
                Arguments.of("2C 3C 4C 5C 6C", "2C 3C 4C 5C 6D", -4),
                Arguments.of("2C 3C 4C 5C 6D", "2C 3C 4C 5C 6C", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void comparePokerHands(String firstHandString, String secondHandString, int expected) {

        PokerHand firstHand = new PokerHand(firstHandString);
        PokerHand secondHand = new PokerHand(secondHandString);

        assertThat(firstHand.compareTo(secondHand)).isEqualTo(expected);
    }


}
