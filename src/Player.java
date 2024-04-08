import java.util.ArrayList;
import java.util.List;

public class Player {

    int playerNumber;

    private List<Tile> hand = new ArrayList<>();

    public Player() {
    }

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void receiveTile(Tile tile) {
        hand.add(tile);
    }

    public List<Tile> getHand() {
        return hand;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
