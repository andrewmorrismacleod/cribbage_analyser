public enum Suit {

    HEARTS('\u2764'),
    DIAMONDS('\u2666'),
    SPADES(('\u2660')),
    CLUBS('\u2663');

    private final char value;

    Suit(char value){
        this.value = value;
    }

    public char getSymbol() {
        return this.value;
    }

}


