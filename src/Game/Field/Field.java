package Game.Field;

import Game.Cell.Cell;
import Game.Cell.CellState;
import Game.Cell.MineCell;
import Game.Cell.NumberedCell;
import Game.Position;

import java.util.Random;

public class Field { // O campo será sempre um quadrado
    private final Random random = new Random();
    private int _remainingNumCells;

    public int remainingFlags;
    public Cell[][] cells;
    public GameState gameState = GameState.Playing;

    public Field(int side) {
        cells = new Cell[side][side];
        _remainingNumCells = side * side;
    }

    public Field(int side, int bombsQuantity) {
        cells = new Cell[side][side];
        _remainingNumCells = (side * side) - bombsQuantity;
        generateField(bombsQuantity);
    }

    public void generateField(int bombsQuantity) {
        generateEmptyField();
        randomizeBombs(bombsQuantity);
        remainingFlags = bombsQuantity;
    }

    public void revealCell(Position pos) {
        var cell = cells[pos.x][pos.y];
        var cellState = cell.getState();

        if (cell instanceof MineCell) {
            cell.symbol = "💥";
            loose();
            return;
        }

        if (cellState == CellState.Revealed)
            return;

        if (cellState == CellState.Flagged) {
            remainingFlags++;
        }

        cell.Reveal();
        _remainingNumCells--;
        var numCell = (NumberedCell) cell;

        if (numCell.getBombsAround() == 0) {
            for (int x = cell.pos.x - 1; x < cell.pos.x + 2; x++) {
                if (x < 0 || x > cells.length - 1) continue;

                for (int y = cell.pos.y - 1; y < cell.pos.y + 2; y++) {
                    if (y < 0 || y > cells.length - 1) continue;
                    if (x == cell.pos.x && y == cell.pos.y) continue;

                    revealCell(new Position(x, y));
                }
            }
        }

        if (_remainingNumCells <= 0) gameState = GameState.Win;
    }

    public void flagCell(Position pos) {
        var cell = cells[pos.x][pos.y];
        var cellState = cell.getState();

        // Se já estiver com bandeira, remova
        if (cellState == CellState.Flagged) {
            cell.UnFlag();
            remainingFlags++;
            return;
        }

        // Se não tiver mais bandeira ou já foi revelada, não faça nada.
        if (remainingFlags <= 0 || cellState == CellState.Revealed) return;

        cell.Flag();
        remainingFlags--;
    }

    private void loose() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] instanceof MineCell) cells[i][j].Reveal();
            }
        }
        gameState = GameState.GameOver;
    }

    private void randomizeBombs(int bombsQuantity) {
        var currentBombsQuantity = 0;

        while (currentBombsQuantity < bombsQuantity) {
            var x = random.nextInt(cells.length);
            var y = random.nextInt(cells[0].length);

            if (cells[x][y] instanceof MineCell)
                continue;

            var pos = cells[x][y].pos;
            cells[x][y] = new MineCell(pos);
            increaseNumberAround(pos);
            currentBombsQuantity++;
        }
    }

    private void increaseNumberAround(Position bombPos) {
        for (int x = bombPos.x - 1; x < bombPos.x + 2; x++) {
            if (x < 0 || x > cells.length - 1) continue;

            for (int y = bombPos.y - 1; y < bombPos.y + 2; y++) {
                if (y < 0 || y > cells.length - 1) continue;

                if (cells[x][y] instanceof NumberedCell)
                    ((NumberedCell) cells[x][y]).increaseBombsAround();
            }
        }
    }

    private void generateEmptyField() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                var pos = new Position(i, j);
                cells[i][j] = new NumberedCell(pos);
            }
        }
    }
}
