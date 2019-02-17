import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;

    public Hand(){
        this.hand = new ArrayList<>();
    }

    public void populate(Deck deck){
        for (int i = 0; i < 6; i++) {
            hand.add(deck.dealCard());
        }
    }

    public void showHand(){

        String handString = "";
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = 0; i< this.hand.size(); i++){

            String faceValue = hand.get(i).getRank().toString();
            char symbol = hand.get(i).getSymbol();


            stringbuilder.append(String.format("Card %s:  %s  %s%n",i,faceValue, symbol));
        }

        System.out.println(stringbuilder);
    }

    public Card removeCard(int index){
        return this.hand.remove(index);
    }

    public ArrayList<Card> getCards(){
        return this.hand;
    }



}
