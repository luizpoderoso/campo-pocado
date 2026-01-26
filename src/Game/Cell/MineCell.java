package Game.Cell;

import Game.Position;

public class MineCell extends Cell {
    public MineCell(Position pos) {
        super(pos);
        symbol = "*";
    }

    @Override
    public boolean Reveal() {
        setState(CellState.Revealed);
        return true; // Pocou!
    }

    @Override
    public String toString() {
        return switch (getState()) {
            case Revealed -> "[*]";
            case Flagged -> "[P]";
            default -> "[ ]";
        };
    }
}
