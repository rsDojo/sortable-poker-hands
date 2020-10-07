package de.ts.hackerang.pokerhands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

enum CardSuit {
    SPADES, HEARTS, DIAMONDS, CLUBS;

    public static CardSuit of(String suit) {
        switch (suit) {
            case "S":
                return SPADES;
            case "C":
                return CLUBS;
            case "D":
                return DIAMONDS;
            case "H":
                return HEARTS;
        }
        throw new IllegalArgumentException(suit);
    }
}

enum PokerHandValue {

    HIGH_CARD, PAIR, TWO_PAIRS, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH, ROYAL_FLUSH;
}

enum CardValue {

    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

    private final int value;

    CardValue(int value) {
        this.value = value;
    }

    public static CardValue of(String substring) {

        switch (substring) {
            case "2":
                return TWO;
            case "3":
                return THREE;
            case "4":
                return FOUR;
            case "5":
                return FIVE;
            case "6":
                return SIX;
            case "7":
                return SEVEN;
            case "8":
                return EIGHT;
            case "9":
                return NINE;
            case "T":
                return TEN;
            case "J":
                return JACK;
            case "Q":
                return QUEEN;
            case "K":
                return KING;
            case "A":
                return ACE;
        }
        throw new IllegalArgumentException(substring);
    }

    public int getValue() {
        return value;
    }
}

/**
 * https://www.codewars.com/kata/586423aa39c5abfcec0001e6/train/java
 * <p>
 * A famous casino is suddenly faced with a sharp decline of their revenues.
 * They decide to offer Texas hold'em also online. Can you help them by writing an algorithm that can rank poker hands?
 * <p>
 * Task:
 * <p>
 * Create a poker hand that has a constructor that accepts a string containing 5 cards:
 * PokerHand hand = new PokerHand("KS 2H 5C JD TD");
 * The characteristics of the string of cards are:
 * A space is used as card seperator
 * Each card consists of two characters
 * The first character is the value of the card, valid characters are:
 * `2, 3, 4, 5, 6, 7, 8, 9, T(en), J(ack), Q(ueen), K(ing), A(ce)`
 * The second character represents the suit, valid characters are:
 * `S(pades), H(earts), D(iamonds), C(lubs)`
 * <p>
 * The poker hands must be sortable by rank, the highest rank first:
 * ArrayList<PokerHand> hands = new ArrayList<PokerHand>();
 * hands.add(new PokerHand("KS 2H 5C JD TD"));
 * hands.add(new PokerHand("2C 3C AC 4C 5C"));
 * Collections.sort(hands);
 * Apply the Texas Hold'em rules for ranking the cards.
 * There is no ranking for the suits.
 * An ace can either rank high or rank low in a straight or straight flush. Example of a straight with a low ace:
 * `"5C 4D 3C 2S AS"`
 */
public class PokerHand implements Comparable<PokerHand> {


    final PokerHandValue value;
    final List<PokerCard> cards;

    public PokerHand(String hand) {
        this.cards = PokerHandParser.parseHand(hand);
        this.value = PokerHandComputer.compute(cards);
    }

    @Override
    public int compareTo(PokerHand o) {

        return value.compareTo(o.value) * -1;
    }

    @Override
    public String toString() {
        return "PokerHand{" +
                "value=" + value +
                ", cards=" + cards +
                '}';
    }
}

class PokerCard implements Comparable<PokerCard> {

    final CardValue value;
    final CardSuit suit;

    PokerCard(String card) {
        value = CardValue.of(card.substring(0, 1));
        suit = CardSuit.of(card.substring(1, 2));
    }

    public PokerCard(CardValue value, CardSuit suit) {
        this.value = value;
        this.suit = suit;
    }

    public CardValue getValue() {
        return value;
    }

    @Override
    public int compareTo(PokerCard o) {

        if (this.value.getValue() > o.value.getValue()) {
            return 1;
        } else if (this.value.getValue() < o.value.getValue()) {
            return -1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokerCard)) return false;
        PokerCard pokerCard = (PokerCard) o;
        return value == pokerCard.value &&
                Objects.equals(suit, pokerCard.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }

    @Override
    public String toString() {
        return "PokerCard{" +
                "value=" + value +
                ", suit='" + suit + '\'' +
                '}';
    }
}

class PokerHandParser {

    static List<PokerCard> parseHand(String hand) {
        return Arrays.stream(hand.split(" ")).map(PokerCard::new).sorted().collect(Collectors.toList());
    }
}

class PokerHandResult {

    PokerHandValue pokerHandValue;
    PokerCard kicker;

    public PokerHandResult(PokerHandValue pokerHandValue, PokerCard kicker) {
        this.pokerHandValue = pokerHandValue;
        this.kicker = kicker;
    }

    public PokerHandValue getPokerHandValue() {
        return pokerHandValue;
    }

    public PokerCard getKicker() {
        return kicker;
    }
}

class PokerHandComputer {

    static PokerHandValue compute(List<PokerCard> cards) {

        if (isFourOfAKind(cards)) {
            return PokerHandValue.FOUR_OF_A_KIND;
        }

        if (isFullHouse(cards)) {
            return PokerHandValue.FULL_HOUSE;
        }

        if (isThreeOfAKind(cards)) {
            return PokerHandValue.THREE_OF_A_KIND;
        }

        if (isTwoPair(cards)) {
            return PokerHandValue.TWO_PAIRS;
        }

        boolean isStraight = isStraight(cards);
        boolean isFlush = isFlush(cards);

        PokerCard highCard = computeHighCard(cards);

        if (isStraight && isFlush && highCard.value == CardValue.ACE) {
            return PokerHandValue.ROYAL_FLUSH;
        }

        if (isStraight && isFlush) {
            return PokerHandValue.STRAIGHT_FLUSH;
        }
        if (isFlush) {
            return PokerHandValue.FLUSH;
        }
        if (isStraight) {
            return PokerHandValue.STRAIGHT;
        }

        if (isPair(cards)) {
            return PokerHandValue.PAIR;
        }

        return PokerHandValue.HIGH_CARD;
    }

    private static boolean isPair(List<PokerCard> cards) {
        Map<CardValue, Long> collect = cards.stream().collect(Collectors.groupingBy(PokerCard::getValue, Collectors.counting()));
        return collect.containsValue(2L);
    }

    private static boolean isThreeOfAKind(List<PokerCard> cards) {
        Map<CardValue, Long> collect = cards.stream().collect(Collectors.groupingBy(PokerCard::getValue, Collectors.counting()));
        return collect.containsValue(3L) && collect.containsValue(1L);
    }

    private static boolean isFourOfAKind(List<PokerCard> cards) {

        Map<CardValue, Long> collect = cards.stream().collect(Collectors.groupingBy(PokerCard::getValue, Collectors.counting()));
        return collect.containsValue(4L);
    }

    private static boolean isFullHouse(List<PokerCard> cards) {

        Map<CardValue, Long> collect = cards.stream().collect(Collectors.groupingBy(PokerCard::getValue, Collectors.counting()));
        return collect.containsValue(3L) && collect.containsValue(2L);
    }

    private static boolean isTwoPair(List<PokerCard> cards) {

        Map<CardValue, Long> collect = cards.stream().collect(Collectors.groupingBy(PokerCard::getValue, Collectors.counting()));
        return collect.containsValue(2L) && collect.size() == 3 && collect.containsValue(1L);
    }


    private static PokerCard computeHighCard(List<PokerCard> cards) {
        return cards.get(4);
    }

    private static boolean isFlush(List<PokerCard> cards) {

        CardSuit minValue = cards.get(0).suit;

        for (int i = 1, cardsSize = cards.size(); i < cardsSize; i++) {
            PokerCard card = cards.get(i);

            if (card.suit != minValue) {
                return false;
            }

        }
        return true;
    }


    static boolean isStraight(List<PokerCard> cards) {
        int minValue = cards.get(0).value.getValue();

        for (int i = 1, cardsSize = cards.size(); i < cardsSize; i++) {
            PokerCard card = cards.get(i);

            int cardValue = card.value.getValue();
            if (cardValue != minValue + 1) {
                return false;
            }

            minValue = cardValue;
        }
        return true;
    }
}
