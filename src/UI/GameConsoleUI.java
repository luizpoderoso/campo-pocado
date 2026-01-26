package UI;

import Game.Field.Field;
import Game.Field.GameState;
import Game.Position;

import java.util.Scanner;

public class GameConsoleUI {
    private Field field;
    private Scanner scanner = new Scanner(System.in);

    public GameConsoleUI(Field field) {
        this.field = field;
    }

    public void run() {
        System.out.println("=====CAMPO POCADO=====");

        while (true) {
            showFieldOnConsole();
            chooseCell();
            switch (field.gameState) {
                case GameState.GameOver:
                    System.out.println("Você perdeu, bluezão troxão :p");
                    return;
                case GameState.Win:
                    System.out.println("Você venceu, seu alexandro :D");
                    return;
            }
        }
    }

    public void chooseCell() {
        System.out.println("Jogue: ");
        var response = scanner.nextLine();
        var splitted = response.split("");

        var choose = Integer.parseInt(splitted[0]);
        var x = Integer.parseInt(splitted[1]);
        var y = Integer.parseInt(splitted[2]);

        var pos = new Position(x - 1, y - 1);

        if (choose == 1)
            field.flagCell(pos);
        else
            field.revealCell(pos);

        System.out.println();
    }

    public void showFieldOnConsole() {
        var cells = field.cells;

        // Mostrar campo
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

        // Mostrar info
        System.out.println("Bandeiras restantes: " + field.remainingFlags);
    }
}
