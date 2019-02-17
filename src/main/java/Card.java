public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit(){
        return this.suit;
    }

    public Rank getRank() {
        return this.rank;
    }

    public int getFaceValue() {
        return this.rank.getValue();
    }

    public char getSymbol(){
        return this.suit.getSymbol();
    }

}
