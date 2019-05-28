package tetris;

import java.awt.Color;

public class SShape extends AbstactPiece{

	/**
	 * Creates an Z-Shape piece. See class description for actual location of r
	 * and c
	 *
	 * @param r
	 *            row location for this piece
	 * @param c
	 *            column location for this piece
	 * @param g
	 *            the grid for this game piece
	 *
	 */
	public SShape(int r, int c, Grid g) {
		grid = g;
		square = new Square[PIECE_COUNT];
		ableToMove = true;

		// Create the squares
		square[0] = new Square(g, r, c + 1, Color.green, true);
		square[1] = new Square(g, r, c, Color.green, true);
		square[2] = new Square(g, r + 1, c, Color.green, true);
		square[3] = new Square(g, r + 1, c - 1, Color.green, true);
	}
}
