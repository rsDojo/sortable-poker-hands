package de.ts.hackerang.pokerhands;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PokerHandParserTest {

    private static Stream<Arguments> parseArguments() {
        return Stream.of(
                Arguments.of("2C 3C 4C 5C 6C", parse(Arrays.asList("2C", "3C", "4C", "5C", "6C"))),
                Arguments.of("6C 5C 4C 3C 2C", parse(Arrays.asList("2C", "3C", "4C", "5C", "6C")))
        );
    }

    private static List<PokerCard> parse(List<String> cards) {
        return cards.stream().map(PokerCard::new).collect(Collectors.toList());
    }

    @ParameterizedTest
    @MethodSource("parseArguments")
    void parsePokerHand(String handString, List<PokerCard> expected) {

        List<PokerCard> pokerCards = PokerHandParser.parseHand(handString);

        assertThat(pokerCards).isEqualTo(expected);
    }

}
