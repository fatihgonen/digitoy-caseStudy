import java.util.List;

public class EvaluationResult {

    private final List<List<Tile>> groups;
    private final int ungroupedTiles;

    public EvaluationResult(List<List<Tile>> groups, int ungroupedTiles) {
        this.groups = groups;
        this.ungroupedTiles = ungroupedTiles;
    }

    public List<List<Tile>> getGroups() {
        return groups;
    }

    public int getUngroupedTiles() {
        return ungroupedTiles;
    }

    @Override
    public String toString() {
        return "Groups: " + groups + ", UngroupedTiles: " + ungroupedTiles;
    }
}
