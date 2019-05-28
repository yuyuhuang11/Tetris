package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * The implementation of the methods common to all of the pieces
 * @author YuYu Huang CSC 143
 *
 */
public abstract class AbstactPiece implements Piece{

	protected boolean ableToMove; // can this piece move

	protected Square[] square; // the squares that make up this piece

	// Made up of PIECE_COUNT squares
	protected Grid grid; // the board this piece is on

	// number of squares in one Tetris game piece
	protected static final int PIECE_COUNT = 4;

	/**
	 * Draws the piece on the given Graphics context
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < PIECE_COUNT; i++) {
			square[i].draw(g);
		}
	}

	/**
	 * Moves the piece if possible
	 * Freeze the piece if it cannot move down anymore
	 *
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			if (direction == Direction.SPACE){
				while (canMove(Direction.DOWN)){
					move(Direction.DOWN);
				}

			}else{
				for (int i = 0; i < PIECE_COUNT; i++)
					square[i].move(direction);
			}
		}

		// if we couldn't move, see if because we're at the bottom
		else if (direction == Direction.DOWN) {
			ableToMove = false;
		}
	}


	public void rotate(){
		int pivotRow = square[1].getRow();
		int pivotCol = square[1].getCol();

		if (canRotate()){
			for (int i = 0; i < PIECE_COUNT; i++){
				if (i != 1){
					square[i].rotate(pivotRow, pivotCol);
				}
			}
		}
	}

	public boolean canRotate(){

		boolean answer = true;
		int pivotRow = square[1].getRow();
		int pivotCol = square[1].getCol();

		for (int i = 0; i < PIECE_COUNT; i++) {
			if (i != 1){
			answer = answer && square[i].canRotate(pivotRow, pivotCol);
			}
		}

		return answer;
	}

	/**
	 * Returns the (row,col) grid coordinates occupied by this Piece
	 *
	 * @return an Array of (row,col) Points
	 */
	public Point[] getLocations() {
		Point[] points = new Point[PIECE_COUNT];
		for (int i = 0; i < PIECE_COUNT; i++) {
			points[i] = new Point(square[i].getRow(), square[i].getCol());
		}
		return points;
	}

	/**
	 * Return the color of this piece
	 */
	public Color getColor() {
		// all squares of this piece have the same color
		return square[0].getColor();
	}

	/**
	 * Returns if this piece can move in the given direction
	 *
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove)
			return false;

		// Each square must be able to move in that direction
		boolean answer = true;
		for (int i = 0; i < PIECE_COUNT; i++) {
			answer = answer && square[i].canMove(direction);
		}

		return answer;
	}
}
