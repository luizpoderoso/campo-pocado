package Game.Field;

import Game.Cell.Cell;
import Game.Cell.CellState;
import Game.Cell.MineCell;
import Game.Cell.NumberedCell;
import Game.Position;

import java.util.Random;
import java.util.function.Function;

public class Field { // O campo será sempre um quadrado
    private Cell[][] cells;
    private Random random = new Random();
    private GameState gameState = GameState.Playing;

    public Field(int side) {
        cells = new Cell[side][side];
    }

    public void generateField(int bombsQuantity) {
        generateEmptyField();
        randomizeBombs(bombsQuantity);
        System.out.println("Campo criado!");
    }

    public void revealCell(Position pos) {
        var cell = cells[pos.x][pos.y];

        if (cell instanceof MineCell) {
            loose();
            return;
        }

        if (cell.getState() == CellState.Revealed)
            return;

        cell.Reveal();
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
    }

    public void flagCell(Position pos) {
        cells[pos.x][pos.y].Flag();
    }

    private void loose() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] instanceof MineCell) cells[i][j].Reveal();
            }
        }
        System.out.println("Você perdeu!");
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

    public void showFieldOnConsole() {
        System.out.print("   ");
        for (int j = 0; j < cells[0].length; j++) {
            System.out.print("  " + (j + 1));
        }

        System.out.println();
        for (int i = 0; i < cells.length; i++) {
            System.out.print((i + 1) + " - ");
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(cells[i][j]);
            }
            System.out.println();
        }
    }
}
