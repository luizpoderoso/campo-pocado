package Game.Cell;

import Game.Position;

public class NumberedCell extends Cell {
    private int bombsAround;

    public NumberedCell(Position pos) {
        super(pos);
    }

    public void increaseBombsAround() {
        bombsAround++;
    }

    public int getBombsAround() {
        return bombsAround;
    }

    @Override
    public boolean Reveal() {
        setState(CellState.Revealed);
        return false; // Não papocou!
    }

    @Override
    public String toString() {
        return switch (getState()) {
            case Revealed -> "[" + bombsAround + "]";
            case Flagged -> "[🚩]";
            default -> "[ ]";
        };
    }
}
