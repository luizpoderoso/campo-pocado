package UI;

import Game.Cell.CellState;
import Game.Field.Field;
import Game.Field.GameState;
import Game.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameGUI extends JFrame {
    private final int rows;
    private final int cols;
    private final Field field;

    private final JButton[][] buttons;
    private final JLabel labelFlags = new JLabel("Bandeiras: 0");
    private final JLabel labelTimer = new JLabel("Tempo: 0s");
    private Timer timer;
    private int secondsPassed;

    public GameGUI(int side, int mines) {
        this.rows = side;
        this.cols = side;
        this.field = new Field(side, mines);
        this.buttons = new JButton[side][side];

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Campo POCADO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Painel Superior (Info)
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        labelFlags.setText("Bandeiras: " + field.remainingFlags);

        infoPanel.add(labelFlags);
        infoPanel.add(labelTimer);
        add(infoPanel, BorderLayout.NORTH);

        // Painel Central (Grade do Jogo)
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(50, 50));

                final int r = i;
                final int c = j;

                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (timer == null) startTimer();

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            handleLeftClick(r, c);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            handleRightClick(r, c);
                        }
                    }
                });
                gridPanel.add(buttons[i][j]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            secondsPassed++;
            labelTimer.setText("Tempo: " + secondsPassed + "s");
        });
        timer.start();
    }

    private void handleLeftClick(int x, int y) {
            var pos = new Position(x, y);
            field.revealCell(pos);
            updateBoardUI();

            if (field.gameState == GameState.GameOver) {
                JOptionPane.showMessageDialog(this, "Boom! Game Over.");
                timer.stop();
            } else if (field.gameState == GameState.Win) {
                JOptionPane.showMessageDialog(this, "Parabéns, você venceu!");
                timer.stop();
            }
    }

    private void handleRightClick(int x, int y) {
        var pos = new Position(x, y);
        field.flagCell(pos);
        updateBoardUI();
    }

    private void updateBoardUI() {
        labelFlags.setText("Bandeiras: " + field.remainingFlags);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                var cell = field.cells[i][j];
                var cellState = cell.getState();

                if (cellState == CellState.Revealed) {
                    buttons[i][j].setEnabled(false);
                    buttons[i][j].setText(cell.symbol);
                } else if (cellState == CellState.Flagged) {
                    buttons[i][j].setText("🚩");
                } else {
                    buttons[i][j].setText("");
                }
            }
        }
    }
}