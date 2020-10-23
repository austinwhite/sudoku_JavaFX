package application;

public class Solver {
	public static boolean safe(int[][] board, int row, int col, int num) {
        for (int currCol = 0; currCol < 9; currCol++) {
            if (board[row][currCol] == num) {
                return false;
            }
        }

        for (int currRow = 0; currRow < 9; currRow++) {
            if (board[currRow][col] == num) {
                return false;
            }
        }

        int rowStart = row - row % 3;
        int colStart = col - col % 3;

        for (int currRow = rowStart; currRow < rowStart + 3; currRow++) {
            for (int currCol = colStart; currCol < colStart + 3; currCol++) {
                if (board[currRow][currCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }
	
    public static boolean solve(int[][] board) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            if (safe(board, row, col, num)) {
                board[row][col] = num;
                if (solve(board)) {
                    return true;
                } else {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }
}
