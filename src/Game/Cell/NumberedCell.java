package Game.Cell;

import Game.Position;

public class NumberedCell extends Cell {
    private int bombsAround;

    public NumberedCell(Position pos) {
        super(pos);
        symbol = "0";
    }

    public void increaseBombsAround() {
        bombsAround++;
        symbol = "" + bombsAround;
    }

    public int getBombsAround() {
        return bombsAround;
    }

    @Override
    public boolean Reveal() {
        setState(CellState.Revealed);
        return false; // Não papocou!
    }
}
