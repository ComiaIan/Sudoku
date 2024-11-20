import java.util.Random;

public class Sudoku {
    private static final int GRID_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int EMPTY = 0;

    private int[][] board = new int[GRID_SIZE][GRID_SIZE];
    private Random random = new Random();

    public static void main (String[] args) {
        Sudoku sudoku = new Sudoku();
        sudoku.generatePuzzle();
        System.out.println("Generated Sudoku shit");
        sudoku.printBoard();

        System.out.println("\nSolving the Puzzle: ");
        if (sudoku.solve(0, 0)) {
            sudoku.printBoard();
        } else {
            System.out.println("No solution found");
        }
    }

    public void generatePuzzle() {
        fillDiagonalSubgrids();
        solve(0,0);
        removeNumbers(20);
    }

    public boolean solve (int row, int col) {
        if (row == GRID_SIZE) {
            return true;
        }

        int nextRow = (col == GRID_SIZE - 1) ? row + 1 : row;
        int nextCol = (col + 1) % GRID_SIZE;

        if (board[row][col] != EMPTY) {
            return solve(nextRow, nextCol);
        }

        for (int num = 1; num <= GRID_SIZE; num++) {
            if (isSafe(row, col, num)) {
                board[row][col] = num;
                if (solve(nextRow, nextCol)) {
                    return true;
                }
                board[row][col] = EMPTY;
            }
        }
        return false;
    }

    public void removeNumbers(int count) {
        for (int i = 0; i < count; i++) {
            int row, col;
            do {
                row = random.nextInt(GRID_SIZE);
                col = random.nextInt(GRID_SIZE);
            } while (board[row][col] == EMPTY);
            board[row][col] = EMPTY;
        }
    }

    public boolean isSafe(int row, int col, int num) {
        return !isInRow(row, num) && !isInCol(col, num) && !isInSubgrid(row - row % SUBGRID_SIZE, col - col % SUBGRID_SIZE, num);
    }

    private boolean isInSubgrid (int startRow, int startCol, int num) {
        for (int row = 0; row < SUBGRID_SIZE; row++) {
            for (int col = 0; col < SUBGRID_SIZE; col++) {
                if (board[startRow + row][startCol + col] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < GRID_SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol (int col, int num) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private void fillDiagonalSubgrids() {
        for(int i = 0; i < GRID_SIZE; i += SUBGRID_SIZE) {
            fillSubgrid(i, i);
        }
    }

    private void fillSubgrid(int row, int col) {
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                int num;
                do {
                    num = random.nextInt(GRID_SIZE) + 1;
                } while (isInSubgrid(row, col, num));
                board[row + i][col + j] = num;
            }
        }
    }

    public void printBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row % SUBGRID_SIZE == 0) {
                System.out.println("+-------+-------+-------+");
            }
            for (int col = 0; col < GRID_SIZE; col++) {
                if (col % SUBGRID_SIZE == 0) {
                    System.out.print("| ");
                }
                System.out.print(board[row][col] == EMPTY ? ". " : board[row][col] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }
}
