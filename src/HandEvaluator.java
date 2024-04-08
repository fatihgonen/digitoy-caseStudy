import java.util.*;
import java.util.stream.Collectors;

public class HandEvaluator {

//    static boolean isRun(List<Tile> tiles) {
//        int jokerCount = (int) tiles.stream().filter(Tile::isJoker).count();
//        List<Tile> sortedTiles = tiles.stream().filter(t -> !t.isJoker()).sorted(Comparator.comparing(Tile::getNumber)).collect(Collectors.toList());
//
//        int expectedNumber = sortedTiles.get(0).getNumber();
//        String expectedColor = sortedTiles.get(0).getColor();
//
//        for (Tile tile : sortedTiles) {
//            if (!tile.getColor().equals(expectedColor)) {
//                return false;
//            }
//            while (tile.getNumber() != expectedNumber && jokerCount > 0) {
//                jokerCount--;
//                expectedNumber++;
//            }
//            if (tile.getNumber() != expectedNumber) {
//                return false;
//            }
//            expectedNumber++;
//        }
//        return true;
//    }
//
//    static boolean isSet(List<Tile> tiles) {
//        int jokerCount = (int) tiles.stream().filter(Tile::isJoker).count();
//        Map<String, Tile> colorMap = new HashMap<>();
//        for (Tile tile : tiles) {
//            if (!tile.isJoker()) {
//                if (colorMap.put(tile.getColor(), tile) != null) {
//                    return false; // More than one tile of the same color without jokers
//                }
//            }
//        }
//        return colorMap.size() + jokerCount >= 3; // Ensure at least three distinct tiles or jokers
//    }
}
