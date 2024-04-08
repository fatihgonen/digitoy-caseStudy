public class Main {
    public static void main(String[] args) {

        System.out.println("Game started!");
        Game game = new Game();
        game.start();
        //evaluate hands
        game.evaluateHands();

    }
}