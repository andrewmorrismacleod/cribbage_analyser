import com.sun.tools.javac.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Analyser {

    public void analyseHand(Hand hand, Deck deck){

        ArrayList<Double> scores = handScore(hand, deck);

        System.out.printf("On average, you would score %s by discarding those cards %n", scores.get(0));
        System.out.printf("The best score would be %s", scores.get(1));

    }


    public ArrayList<Double> handScore(Hand hand, Deck deck){

        int total = 0;
        int bestScore = 0;

        //Loop over each card in the deck
        for (Card card : deck.getCards()) {

            //Clone the 4 cards retained by the player and add the current deck "starter card" to these.
            ArrayList<Card> scoringHand = (ArrayList<Card>) hand.getCards().clone();
            scoringHand.add(card);

            //Generate all possible combinations of hands
            ArrayList<ArrayList<Card>> allSetsOfCards = generatePowerSet(scoringHand);
            int runMaxLength = runMaxLength(allSetsOfCards);
            int currentScore = 0;


            //Loop over each combination and score the hand.
            for (ArrayList<Card> cards : allSetsOfCards) {
               currentScore += score15(cards);
               currentScore += scorePair(cards);
               currentScore += scoreRun(cards, runMaxLength);
               currentScore += scoreFlush(cards);
               currentScore += scoreHisHeel(cards);
            }

            total += currentScore;
            bestScore = Math.max(bestScore, currentScore);

        }

        ArrayList<Double> finalScores = new ArrayList<>();

        finalScores.add((double) total / (double) deck.getCards().size());
        finalScores.add((double) bestScore);
        return finalScores;

    }


    //Generates the power set (less empty set) i.e. 2^5 - 1 of the set of cards . This is comprised of:
    //1 set containing all 5 cards
    //5 sets containing 4 of the 5 cards
    //10 sets containing 3 of the 5 cards
    //10 sets containing 2 of the 5 cards
    //5 sets containing 1 of the 5 cards

    private ArrayList<ArrayList<Card>> generatePowerSet(ArrayList<Card> cards){
        ArrayList<ArrayList<Card>> powerSet = new ArrayList<>();

        for (int i = 1; i < Math.pow(2,cards.size()); i++){

            ArrayList<Card> setOfCards = new ArrayList<>();
            String[] binaryDigits = Integer.toBinaryString(i).split("");
            Collections.reverse(Arrays.asList(binaryDigits));

            for (int j = 0; j<binaryDigits.length; j++){

                if (Integer.parseInt(binaryDigits[j]) == 1){
                    setOfCards.add(cards.get(j));
                }
            }
            powerSet.add(setOfCards);
        }

        return powerSet;
    }

    public int score15(ArrayList<Card> cards){
        int TotalPointValue = totalValueOfCards(cards);
        if(TotalPointValue == 15){
            return 2;
        }  else {
            return 0;
        }

    }

    private int scoreRun(ArrayList<Card> cards, int runMaxLength){
        //Could try and use comparators here but "easier" to check that:
        //1. the total of the card values is equal to the difference of the triangular numbers of the highest card value and (lowest card value -1)
        //2. there are at least three cards
        //3. all of the cards are different
        //4. the run is equal to the longest possible length

        boolean sufficientNumber = cards.size()>= 3;
        boolean isMaxLength = cards.size() == runMaxLength;
        int minimumValue = minValue(cards);
        int maximumValue = maxValue(cards);
        boolean isValidTotal = triangularNumber(maximumValue) - triangularNumber(minimumValue-1) == totalOrdinalValueOfCards(cards);
        boolean allCardsAreUnique = allCardsUnique(cards);
        if (isValidTotal && sufficientNumber && allCardsAreUnique && isMaxLength){

            return cards.size();
        } else {
            return 0;
        }
    }

    private int runMaxLength(ArrayList<ArrayList<Card>> allHands){

        int maxLength = 0;
        for (ArrayList<Card> hand : allHands){
            int minimumValue = minValue(hand);
            int maximumValue = maxValue(hand);
            boolean isValidTotal = triangularNumber(maximumValue) - triangularNumber(minimumValue-1) == totalOrdinalValueOfCards(hand);
            boolean allCardsAreUnique = allCardsUnique(hand);
            if (isValidTotal && allCardsAreUnique && hand.size()>maxLength) {
                maxLength = hand.size();
            }
        }
        return maxLength;
    }

    private int scoreFlush(ArrayList<Card> cards){
        if (cards.size() == 5){
            int total = 1;
            Suit firstCardSuit = cards.get(0).getSuit();
            for (int i = 1; i < 4; i++){
                if (cards.get(i).getSuit() == firstCardSuit) {
                    total++;
                }
                if (total == 4) {
                    if (cards.get(4).getSuit() == firstCardSuit){
                        total ++;
                    }
                    return total;
                }
            }
        }
        return 0;
    }

    private int scoreHisHeel(ArrayList<Card> cards){
        if (cards.size() == 5) {
            if (cards.get(4).getRank() == Rank.JACK) {
                return 1;
            }
        }
        return 0;
    }

    private int scorePair(ArrayList<Card> cards){
        if (cards.size() == 2){
            if (cards.get(0).getRank() == cards.get(1).getRank()) {
                return 2;
            }
        }
        return 0;
    }

    private int minValue(ArrayList<Card> cards){
        int minCardValue = 14;
        for (Card card: cards){
            if (card.getRank().ordinal()+1 < minCardValue){
                minCardValue = card.getRank().ordinal()+1;
            }
        }
        return minCardValue;
    }

    private int maxValue(ArrayList<Card> cards){
        int maxCardValue = 0;
        for (Card card: cards){
            if (card.getRank().ordinal()+1 > maxCardValue){
                maxCardValue = card.getRank().ordinal()+1;
            }
        }
        return maxCardValue;
    }

    private int triangularNumber(int n){
        return n * (n + 1) / 2;
    }

    private boolean allCardsUnique(ArrayList<Card> cards){
        for (int i = 0; i < cards.size()-1; i++){
            for (int j = i+1; j < cards.size(); j++){
                if (cards.get(i).getRank() == cards.get(j).getRank()){
                    return false;
                }
            }
        }
        return true;
    }

    private int totalOrdinalValueOfCards(ArrayList<Card> cards){
        int total = 0;
        for (Card card : cards){
            total += card.getRank().ordinal()+1;
        }
        return total;
    }

    private int totalValueOfCards(ArrayList<Card> cards){
        int total = 0;
        for (Card card : cards){
            total += card.getRank().getValue();
        }
        return total;
    }

}
