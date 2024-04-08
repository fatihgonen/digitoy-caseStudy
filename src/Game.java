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
        for (int i = 0; i < 15; i++) {
            players[0].receiveTile(deck.draw());
        }
        for (int j = 1; j < players.length; j++) {
            for (int i = 0; i < 14; i++) {
                players[j].receiveTile(deck.draw());
            }
        }
    }

    public void start() {
        distributeTiles();
        // Other game logic here
    }

    public void evaluateHands() {
        int minUngroupedTiles = Integer.MAX_VALUE;
        Player closestPlayer = null;
        List<Tile> closestPlayerHandSorted = null;

        for (Player player : players) {
            List<Tile> hand = new ArrayList<>(player.getHand()); // Make a copy of the hand to sort
            int ungroupedTiles = calculateOptimalArrangement(hand);

            System.out.println("Player " + player.playerNumber + " has hand: " + hand);

            // Sort the hand
            Collections.sort(hand, (a, b) -> {
                if (a.getColor().equals(b.getColor())) {
                    return Integer.compare(a.getNumber(), b.getNumber());
                }
                return a.getColor().compareTo(b.getColor());
            });
            // Print the sorted hand
            System.out.println("Evaluating hand for player "+ player.playerNumber + ": ");
            System.out.println("Player " + player.playerNumber + " has " + ungroupedTiles + " ungrouped tiles.");
            System.out.println("Sorted hand: " + hand);

            // Determine the player closest to winning
            if (ungroupedTiles < minUngroupedTiles) {
                minUngroupedTiles = ungroupedTiles;
                closestPlayer = player;
                closestPlayerHandSorted = new ArrayList<>(hand); // Keep a sorted copy of the closest hand
            }
        }
        // Print the player who is closest to winning
        if (closestPlayer != null) {
            System.out.println("\nPlayer closest to winning: Player " + closestPlayer.playerNumber +" with cards " +
                    closestPlayerHandSorted + " with " + minUngroupedTiles + " ungrouped tiles.");
        }
    }

    private int calculateOptimalArrangement(List<Tile> hand) {
        int jokerCount = (int) hand.stream().filter(Tile::isJoker).count();
        List<Tile> nonJokerTiles = hand.stream().filter(t -> !t.isJoker()).collect(Collectors.toList());
        List<List<Tile>> allCombinations = generateAllCombinations(nonJokerTiles);
        int minUngroupedTiles = nonJokerTiles.size(); // Worst case: all non-joker tiles are ungrouped

        for (List<Tile> combination : allCombinations) {
            if (isRun(combination, jokerCount) || isSet(combination, jokerCount)) {
                int groupedTiles = combination.size();
                int ungroupedTiles = nonJokerTiles.size() - groupedTiles;
                minUngroupedTiles = Math.min(minUngroupedTiles, ungroupedTiles);
            }
        }
        return minUngroupedTiles;
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
