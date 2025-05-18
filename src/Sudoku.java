import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sudoku extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private int[][] board = new int[SIZE][SIZE];

    public Sudoku() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        Font font = new Font("SansSerif", Font.BOLD, 20);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(font);
                final int r = row, c = col;

                cell.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        String text = cell.getText();
                        if (!text.matches("[1-9]?")) {
                            cell.setText("");
                        } else if (!text.isEmpty()) {
                            int number = Integer.parseInt(text);
                            if (isValidMove(r, c, number)) {
                                board[r][c] = number;
                                cell.setForeground(Color.BLACK);
                            } else {
                                cell.setForeground(Color.RED);
                            }
                        } else {
                            board[r][c] = 0;
                        }
                    }
                });

                cells[row][col] = cell;
                gridPanel.add(cell);
            }
        }

        JButton checkButton = new JButton("Verificar vitória");
        checkButton.addActionListener(e -> {
            if (isBoardFull() && isBoardValid()) {
                JOptionPane.showMessageDialog(this, "Parabéns! Você completou o Sudoku!", "Vitória", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ainda não está correto. Continue tentando!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        add(gridPanel, BorderLayout.CENTER);
        add(checkButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private boolean isValidMove(int row, int col, int number) {
        for (int i = 0; i < SIZE; i++) {
            if ((i != col && board[row][i] == number) || (i != row && board[i][col] == number)) {
                return false;
            }
        }
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if ((i != row || j != col) && board[i][j] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBoardFull() {
        for (int[] row : board) {
            for (int num : row) {
                if (num == 0) return false;
            }
        }
        return true;
    }

    private boolean isBoardValid() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = board[row][col];
                board[row][col] = 0;
                if (!isValidMove(row, col, value)) {
                    board[row][col] = value;
                    return false;
                }
                board[row][col] = value;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sudoku::new);
    }
} 
