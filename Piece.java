package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * A super type that contains all of the behaviors common to all of the pieces
 * @author YuYu Huang CSC 143
 *
 */
public interface Piece {

	void draw(Graphics g);

	void move(Direction direction);

	Point[] getLocations();

	Color getColor();

	boolean canMove(Direction direction);

	void rotate();

}
