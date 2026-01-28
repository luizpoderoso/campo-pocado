package UI.GUI;

import UI.GUI.Exceptions.InvalidDifficultyException;

import javax.swing.*;
import java.awt.*;

public class MenuGUI extends JFrame {

    public MenuGUI() {
        initializeMenu();
    }

    private void initializeMenu() {
        setTitle("Campo POCADO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Painel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("CAMPO POCADO", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Botões de dificuldade
        JButton easyBtn = createDifficultyButton("Fácil (9x9, 10 Mines)", 9, 10);
        JButton mediumBtn = createDifficultyButton("Médio (16x16, 40 Mines)", 16, 40);
        JButton hardBtn = createDifficultyButton("Difícil (30x30, 99 Mines)", 30, 99);
        JButton customBtn = createCustomDifficultyButton("Customizada");

        buttonPanel.add(easyBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(mediumBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(hardBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(customBtn);

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createDifficultyButton(String label, int size, int mines) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(e -> {
            // Instanciar o jogo
            new GameGUI(size, mines);

            // Fechar o menu
            this.dispose();
        });

        return button;
    }

    private JButton createCustomDifficultyButton(String label) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(e -> {
            showCustomDifficultyDialog();
        });

        return button;
    }

    private void showCustomDifficultyDialog() {
        JTextField sideField = new JTextField(5);
        JTextField minesField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(2, 2, 5, 5));
        myPanel.add(new JLabel("Tamanho do campo (n x n):"));
        myPanel.add(sideField);
        myPanel.add(new JLabel("Número de minas:"));
        myPanel.add(minesField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Dificuldade customizada", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int side = Integer.parseInt(sideField.getText());
                int mines = Integer.parseInt(minesField.getText());

                // 4. Validate and start the game
                validateDifficulty(side, mines); // Method that throws your Exception

                new GameGUI(side, mines);
                this.dispose(); // Close menu

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Coloque números inteiros!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidDifficultyException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Configurações inválidas!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void validateDifficulty(int side, int mines) throws InvalidDifficultyException {
        if (side < 2) throw new InvalidDifficultyException("Campo muito pequeno!");
        if (side > 40) throw new InvalidDifficultyException("Campo muito grande!");
        if (mines < 1)  throw new InvalidDifficultyException("Quantidade de minas menor que 1!");
        if (mines >= (side * side))  throw new InvalidDifficultyException("Quantidade de minas muito grande!");
    }

    public static void main(String[] args) {
        // Main entry point of your application
        SwingUtilities.invokeLater(MenuGUI::new);
    }
}