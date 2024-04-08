import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Tile> tiles = new ArrayList<>();

    public Deck() {
        initialize();
        shuffle();
        setJokers();
    }

    private void initialize() {
        String[] colors = {"Yellow", "Blue", "Black", "Red"};
        int tilesPerColor = 13;
        for (String color : colors) {
            for (int i = 1; i <= tilesPerColor; i++) {
                tiles.add(new Tile(color, i));
                tiles.add(new Tile(color, i)); // Each tile appears twice
            }
        }
        tiles.add(new Tile("None", 52)); // False Okey
        tiles.add(new Tile("None", 52)); // False Okey
    }

    private void setJokers() {
        Collections.shuffle(tiles);
        Tile faceUpTile = tiles.remove(0); // Randomly select a face-up tile
        int jokerNumber = (faceUpTile.getNumber() % 13) + 1;
        for (Tile tile : tiles) {
            if (tile.getColor().equals(faceUpTile.getColor()) && tile.getNumber() == jokerNumber) {
                tile = new Tile(tile.getColor(), tile.getNumber(), true, false);
            }
        }
        // Add two false jokers
        tiles.add(new Tile(faceUpTile.getColor(), faceUpTile.getNumber(), false, true));
        tiles.add(new Tile(faceUpTile.getColor(), faceUpTile.getNumber(), false, true));
    }

    public void shuffle() {
        Collections.shuffle(tiles);
    }

    public Tile draw() {
        if (!tiles.isEmpty()) {
            return tiles.remove(0);
        }
        return null;
    }


}
