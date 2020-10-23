package application;

public class GameBoard {
	private int[][] solution;
	private int[][] initial;
	private int[][] player;
	private int K = 40;

	public GameBoard() {
		setInitial();
		solution = copy2DArray(initial);
		player = new int[9][9];
		Solver.solve(solution);
	}

	public int[][] getSolution() {
		return solution;
	}

	public int[][] getInitial() {
		return initial;
	}
	
	public void setInitial() {
		initial = generateBoard();
	}

	public int[][] getPlayer() {
		return player;
	}
	
	public void clearPlayer() {
		player = new int[9][9];
	}
	
	public void setK(int Difficulty) {
		K = Difficulty;
	}
	
	private int[][] copy2DArray(int[][] initial) {
		int[][] copied = new int[9][];
		for (int i = 0; i < 9; i++) {
			copied[i] = initial[i].clone();
		}
		return copied;
	}

	public void modifyPlayer(int val, int row, int col) {
		if (initial[row][col] == 0) {
			if (val >= 0 && val <= 9) {
				player[row][col] = val;
			} else {
				System.out.println("Value passed to player falls out of range.");
			}
		}
	}
	
	public int[][] generateBoard() {	
		int[][] board = new int[][] {
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0}
		};
		
		fillTopRow(board);
		fillRemainder(board);
		removeKCells(board);
		
		return board;
	}
	
	public void fillTopRow(int[][] board) {		
		int amt_placed = 0;
		int[] nums_placed = new int[10];
		int curr_board_idx = 0;
		
		for (int i = 0; i < 10; i++) {
			nums_placed[i] = 0;
		}
		
		while (amt_placed < 9) {
			int value = (int) (Math.random() * 9+1);
			if (nums_placed[value] == 0) {
				amt_placed++;
				nums_placed[value] = value;
				board[0][curr_board_idx] = value;
				curr_board_idx++;
			}
		}
	}
	
	public void fillRemainder(int[][] board) {
		Solver.solve(board);
	}
	
	public void removeKCells(int[][] board) {
		for (int i = 0; i < K; i++) {
			int cellNum = (int) (Math.random() * (9*9)+1);
			int row = (cellNum / 9);
			if (row == 9) {
				row -= 1;
			}
			int col = cellNum % 9;
			board[row][col] = 0;
		}
	}
}
