import java.util.Scanner;

public class ConnectFour {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = '.';
    private static final char PLAYER_ONE = 'X'; // Red
    private static final char PLAYER_TWO = 'O'; // Yellow

    private final char[][] board;

    public ConnectFour() {
        board = new char[ROWS][COLS];
        initializeBoard();
    }

    // Fills the grid with empty placeholder dots
    private void initializeBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = EMPTY;
            }
        }
    }

    // Prints current board state to console
    public void printBoard() {
        System.out.println("\n  0 1 2 3 4 5 6"); // Column indices for user convenience
        for (int r = 0; r < ROWS; r++) {
            System.out.print("| ");
            for (int c = 0; c < COLS; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-----------------");
    }

    // Drops a piece into the chosen column, landing on the lowest available row
    public boolean dropPiece(int col, char player) {
        if (col < 0 || col >= COLS) {
            System.out.println("Invalid column! Pick between 0 and " + (COLS - 1));
            return false;
        }
        
        // Start from bottom row and move up
        for (int r = ROWS - 1; r >= 0; r--) {
            if (board[r][col] == EMPTY) {
                board[r][col] = player;
                return true;
            }
        }
        
        System.out.println("Column " + col + " is full!");
        return false;
    }

    // Checks the board to see if the current player has won
    public boolean checkWin(char player) {
        // 1. Horizontal Check
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS - 3; c++) {
                if (board[r][c] == player && board[r][c+1] == player && 
                    board[r][c+2] == player && board[r][c+3] == player) {
                    return true;
                }
            }
        }

        // 2. Vertical Check
        for (int r = 0; r < ROWS - 3; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] == player && board[r+1][c] == player && 
                    board[r+2][c] == player && board[r+3][c] == player) {
                    return true;
                }
            }
        }

        // 3. Diagonal Check (Down-Right)
        for (int r = 0; r < ROWS - 3; r++) {
            for (int c = 0; c < COLS - 3; c++) {
                if (board[r][c] == player && board[r+1][c+1] == player && 
                    board[r+2][c+2] == player && board[r+3][c+3] == player) {
                    return true;
                }
            }
        }

        // 4. Diagonal Check (Up-Right)
        for (int r = 3; r < ROWS; r++) {
            for (int c = 0; c < COLS - 3; c++) {
                if (board[r][c] == player && board[r-1][c+1] == player && 
                    board[r-2][c+2] == player && board[r-3][c+3] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    // Checks if the board is completely full (resulting in a tie game)
    public boolean isBoardFull() {
        for (int c = 0; c < COLS; c++) {
            if (board[0][c] == EMPTY) {
                return false; 
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ConnectFour game = new ConnectFour();
        Scanner scanner = new Scanner(System.in);
        char currentPlayer = PLAYER_ONE;
        boolean gameRunning = true;

        System.out.println("Welcome to Connect Four!");
        game.printBoard();

        while (gameRunning) {
            System.out.print("Player " + currentPlayer + ", choose a column (0-6): ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                scanner.next(); // Clear invalid input
                continue;
            }
            
            int col = scanner.nextInt();

            if (game.dropPiece(col, currentPlayer)) {
                game.printBoard();

                if (game.checkWin(currentPlayer)) {
                    System.out.println("Congratulations! Player " + currentPlayer + " wins!");
                    gameRunning = false;
                } else if (game.isBoardFull()) {
                    System.out.println("It's a tie game!");
                    gameRunning = false;
                } else {
                    // Switch turns
                    currentPlayer = (currentPlayer == PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE;
                }
            }
        }
        scanner.close();
    }
}