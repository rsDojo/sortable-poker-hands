package de.ts.hackerang.pokerhands;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class PokerHandComputerTest {

    private static Stream<Arguments> parseArguments() {
        return Stream.of(
                Arguments.of("2C 3C 4C 5C 6C", true),
                Arguments.of("2C 3C 4C 5C 7C", false)
        );
    }


    @ParameterizedTest
    @MethodSource("parseArguments")
    void isStraight(String pokerHand, boolean expected) {

        boolean actual = PokerHandComputer.isStraight(PokerHandParser.parseHand(pokerHand));

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
