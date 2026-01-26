import Game.Field.Field;
import Game.Position;

public class Main {
    static void main() {
//        JFrame frame = new JFrame("Campo Pocado");

        var field = new Field(8);
        field.generateField(10);

        var pos = new Position(5, 5);
        field.flagCell(pos);
        field.showFieldOnConsole();

//        frame.setSize(400, 400);
//        frame.setResizable(false);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
    }
}
