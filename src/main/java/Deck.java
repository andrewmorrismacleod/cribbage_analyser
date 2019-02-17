import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards;
    private ArrayList<Card> discardPile;

    public Deck(){
        this.cards = new ArrayList<>();
        populate();
        this.discardPile = new ArrayList<>();
    }

    public void populate(){

        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()) {
                Card card = new Card(suit, rank);
                this.cards.add(card);
            }
        }

    }

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    public void shuffle(){
        Collections.shuffle(this.cards);
    }

    public Card dealCard(){
        return this.cards.remove(0);
    }

    public void discard(Card card){
        this.discardPile.add(card);
    }

}

