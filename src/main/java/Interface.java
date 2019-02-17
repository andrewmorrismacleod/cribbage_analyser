import java.util.Scanner;

class Interface {

    public static void main(String[]args){

        System.out.printf("Please enter the two cards you wish to add to the crib: %n%n");

        Deck deck = new Deck();
        deck.shuffle();

        Hand hand = new Hand();
        hand.populate(deck);

        //showHand will produce string output;
        hand.showHand();

        Scanner scanner = new Scanner(System.in);

        System.out.println("First Card:");
        int firstCardIndex = scanner.nextInt();
        System.out.println("Second Card:");
        int secondCardIndex = scanner.nextInt();

        if (firstCardIndex < secondCardIndex) {
            deck.discard(hand.removeCard(secondCardIndex));
            deck.discard(hand.removeCard(firstCardIndex));
        } else {
            deck.discard(hand.removeCard(firstCardIndex));
            deck.discard(hand.removeCard(secondCardIndex));
        }

        Analyser analyser = new Analyser();
        analyser.analyseHand(hand, deck);



    }
}















