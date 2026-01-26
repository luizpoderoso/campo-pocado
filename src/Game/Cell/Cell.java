package Game.Cell;

import Game.GameEntity;
import Game.Position;

public abstract class Cell extends GameEntity {
    private CellState state = CellState.Hidden;
    protected String symbol;

    public Cell(Position pos) {
        super(pos);
    }

    public CellState getState() {
        return state;
    }

    protected void setState(CellState state) {
        this.state = state;
    }

    public boolean Flag() {
        setState(CellState.Flagged);
        return false;
    }

    public abstract boolean Reveal();

    @Override
    public String toString() {
        return switch (getState()) {
            case Revealed -> "[" + symbol + "]";
            case Flagged -> "[P]";
            default -> "[ ]";
        };
    }
}
