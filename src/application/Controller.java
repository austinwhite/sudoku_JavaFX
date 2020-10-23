package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Controller implements Initializable {
	@FXML Button button_one;
	@FXML Button button_two;
	@FXML Button button_three;
	@FXML Button button_four;
	@FXML Button button_five;
	@FXML Button button_six;
	@FXML Button button_seven;
	@FXML Button button_eight;
	@FXML Button button_nine;
	@FXML Button button_check;
	@FXML Button button_solve;
	@FXML Button button_clear;
	@FXML Button button_newboard_easy;
	@FXML Button button_newboard_mid;
	@FXML Button button_newboard_hard;
	@FXML Canvas canvas;
	
	GameBoard gameboard;
	int selected_row;
	int selected_col;
	static final int INITIAL = 0;
	static final int PLAYER_INPUT = 1;
	static final int SOLVE = 2;
	static final int CHECK = 3;
	static final int EASY = 20;
	static final int MEDIUM = 40;
	static final int HARD = 60;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		gameboard = new GameBoard();
		setupMouseEvent();
		selected_row = -1;
		selected_col = -1;
		GraphicsContext context = canvas.getGraphicsContext2D();
		drawOnCanvas(context, INITIAL);
	}
	
	public void drawOnCanvas(GraphicsContext context, int type) {		
		context.clearRect(0, 0, 450, 450);
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				int y_position = row * 50 + 4;
				int x_position = col * 50 + 4;
				int width = 42;
				context.setFill(Color.WHITE);
				context.fillRoundRect(x_position, y_position, width, width, 5, 5);
			}
		}
		context.setStroke(Color.RED);
		context.setLineWidth(1);
		context.strokeRoundRect(selected_col * 50 + 4, selected_row * 50 + 4, 42, 42, 5, 5);
		
		int[][] initial = gameboard.getInitial();
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				int y_position = row * 50 + 32;
				int x_position = col * 50 + 20;
				context.setFill(Color.BLACK);
				context.setFont(new Font(20));
				if (initial[row][col] != 0) {
					context.fillText(initial[row][col] + "", x_position, y_position);
				}
			}
		}
		
		if (type == SOLVE) {
			drawSolvedBoard(context);
		} else if (type == CHECK) {
			drawCheckedBoard(context);
		} else {
			drawPlayerInput(context);
		}
	}
	
	public void drawPlayerInput(GraphicsContext context) {
		int[][] player = gameboard.getPlayer();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				int y_position = row * 50 + 32;
				int x_position = col * 50 + 20;
				context.setFill(Color.PURPLE);
				context.setFont(new Font(20));
				if (player[row][col] != 0) {
					context.fillText(player[row][col] + "", x_position, y_position);
				}
			}
		}
	}
	
	public void drawSolvedBoard(GraphicsContext context) {
		int[][] solution = gameboard.getSolution();
		int[][] initial = gameboard.getInitial();
		gameboard.clearPlayer();
 		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				int y_position = row * 50 + 32;
				int x_position = col * 50 + 20;
				context.setFill(Color.GREEN);
				context.setFont(new Font(20));
				if (initial[row][col] == 0) {
					context.fillText(solution[row][col] + "", x_position, y_position);
				}
			}
		}
	}
	
	public void drawCheckedBoard(GraphicsContext context) {
		int[][] solution = gameboard.getSolution();
		int[][] initial = gameboard.getInitial();
		int[][] player = gameboard.getPlayer();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				int y_position = row * 50 + 32;
				int x_position = col * 50 + 20;
				context.setFont(new Font(20));
				if (initial[row][col] == 0 && player[row][col] != 0) {
					if (player[row][col] == solution[row][col]) {
						context.setFill(Color.GREEN);
					} else {
						context.setFill(Color.RED);
					}
					context.fillText(solution[row][col] + "", x_position, y_position);
				}
			}
		}
	}
	
	public void setupMouseEvent() {
		canvasCellSelected();
	}
	
	public void canvasCellSelected () {
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int mouse_x = (int) event.getX();
				int mouse_y = (int) event.getY();
				selected_row = (int) (mouse_y / 50);
				selected_col = (int) (mouse_x / 50);
				drawOnCanvas(canvas.getGraphicsContext2D(), INITIAL);
			}
		});
	}
	
	public void buttonNewBoardEasyPressed() {
		gameboard.clearPlayer();
		gameboard.setK(EASY);
		gameboard.setInitial();
		drawOnCanvas(canvas.getGraphicsContext2D(), INITIAL);
	}
	
	public void buttonNewBoardMidPressed() {
		gameboard.clearPlayer();
		gameboard.setK(MEDIUM);
		gameboard.setInitial();
		drawOnCanvas(canvas.getGraphicsContext2D(), INITIAL);
	}
	
	public void buttonNewBoardHardPressed() {
		gameboard.clearPlayer();
		gameboard.setK(HARD);
		gameboard.setInitial();
		drawOnCanvas(canvas.getGraphicsContext2D(), INITIAL);
	}
	
	public void buttonClearPressed() {
		gameboard.clearPlayer();
		drawOnCanvas(canvas.getGraphicsContext2D(), INITIAL);
	}
	
	public void buttonSolvePressed() {
		drawOnCanvas(canvas.getGraphicsContext2D(), SOLVE);
	}
	
	public void buttonCheckPressed() {
		drawOnCanvas(canvas.getGraphicsContext2D(), CHECK);
	}
	
	public void buttonOnePressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(1, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
	
	public void buttonTwoPressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(2, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
	
	public void buttonThreePressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(3, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
	
	public void buttonFourPressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(4, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
	
	public void buttonFivePressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(5, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
	
	public void buttonSixPressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(6, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
	
	public void buttonSevenPressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(7, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
	
	public void buttonEightPressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(8, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
	
	public void buttonNinePressed() {
		if (selected_row < 0 || selected_col < 0) {
			return;
		}
		gameboard.modifyPlayer(9, selected_row, selected_col);
		drawOnCanvas(canvas.getGraphicsContext2D(), PLAYER_INPUT);
	}
}
