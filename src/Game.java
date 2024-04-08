import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private Deck deck;
    private Player[] players = new Player[4];

    public Game() {
        deck = new Deck();
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i+1);
        }
    }

    public void distributeTiles() {
        // First player gets 15 tiles, others get 14
        System.out.println("Distributing tiles...");
        for (int i = 0; i < 15; i++) {
            players[0].receiveTile(deck.draw());
        }
        System.out.println("Player " + players[0].getPlayerNumber() + "'s hand: " + players[0].getHand());
        for (int j = 1; j < players.length; j++) {
            for (int i = 0; i < 14; i++) {
                players[j].receiveTile(deck.draw());
            }
            System.out.println("Player " + players[j].getPlayerNumber() + "'s hand: " + players[j].getHand());
        }
    }

    public void start() {
        distributeTiles();
    }

    public void evaluateHands() {
        int minUngroupedTiles = Integer.MAX_VALUE;
        Player closestPlayer = null;
        EvaluationResult bestResult = null;

        for (Player player : players) {
            List<Tile> hand = new ArrayList<>(player.getHand());
            EvaluationResult result = calculateOptimalArrangement(hand);

            System.out.println("Player " + player.getPlayerNumber() + "'s optimal arrangement: " + result);

            if (result.getUngroupedTiles() < minUngroupedTiles) {
                minUngroupedTiles = result.getUngroupedTiles();
                closestPlayer = player;
                bestResult = result;
            }
        }

        if (closestPlayer != null) {
            System.out.println("\nPlayer closest to winning: Player " + closestPlayer.getPlayerNumber() +
                    " with optimal hand configuration: " + bestResult);
        }
    }

    private EvaluationResult calculateOptimalArrangement(List<Tile> hand) {
        int jokerCount = (int) hand.stream().filter(Tile::isJoker).count();
        List<Tile> nonJokerTiles = hand.stream().filter(t -> !t.isJoker()).collect(Collectors.toList());
        List<List<Tile>> allCombinations = generateAllCombinations(nonJokerTiles);
        int minUngroupedTiles = nonJokerTiles.size(); // Worst case: all non-joker tiles are ungrouped
        List<List<Tile>> bestGroups = new ArrayList<>();

        for (List<Tile> combination : allCombinations) {
            if (isRun(combination, jokerCount) || isSet(combination, jokerCount)) {
                int groupedTiles = combination.size();
                int ungroupedTiles = nonJokerTiles.size() - groupedTiles;
                if (ungroupedTiles < minUngroupedTiles) {
                    minUngroupedTiles = ungroupedTiles;
                    bestGroups.clear();
                    bestGroups.add(new ArrayList<>(combination)); // Store the best group found
                }
            }
        }

        return new EvaluationResult(bestGroups, minUngroupedTiles);
    }

    private List<List<Tile>> generateAllCombinations(List<Tile> tiles) {
        List<List<Tile>> allCombinations = new ArrayList<>();
        // Generate all subsets of the tiles list
        int n = tiles.size();
        for (int i = 0; i < (1 << n); i++) {
            List<Tile> subset = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) { // Check if the jth element is in the subset
                    subset.add(tiles.get(j));
                }
            }
            if (subset.size() >= 3) {
                allCombinations.add(subset);
            }
        }
        return allCombinations;
    }

    private boolean isRun(List<Tile> tiles, int jokerCount) {
        if (tiles.isEmpty() && jokerCount < 3) return false;

        int neededJokers = 0;
        Tile previous = null;
        for (Tile tile : tiles) {
            if (previous != null) {
                int gap = tile.getNumber() - previous.getNumber() - 1;
                if (gap > 0) {
                    neededJokers += gap; // Gap needs to be filled with jokers
                }
            }
            previous = tile;
        }

        // Check if there are enough jokers to fill the gaps
        return neededJokers <= jokerCount;
    }

    private boolean isSet(List<Tile> tiles, int jokerCount) {
        if (tiles.size() + jokerCount < 3) return false;

        Map<String, Integer> counts = new HashMap<>();
        for (Tile tile : tiles) {
            counts.put(tile.getColor(), counts.getOrDefault(tile.getColor(), 0) + 1);
        }

        // More than one tile of the same color means a joker cannot help
        if (counts.values().stream().anyMatch(count -> count > 1)) {
            return false;
        }

        return tiles.size() + jokerCount >= 3; // At least three tiles including jokers
    }

}
