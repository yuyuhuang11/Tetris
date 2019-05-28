package tetris;

import static org.junit.Assert.*;
import java.awt.Color;
import java.awt.Point;
import org.junit.Test;

/**
 * This is to check the correctness of the key features of the game
 * 1. The motion of a square
 * 2. The motion of an L-shaped piece
 * 3. The removal of one or more rows within the grid
 * 4. The modification of some other existing classes
 * 5. The rotation of all pieces
 * 6. Check that no rotation is done when the piece can't rotate
 *
 * @author Yuyu Huang CSC 143
 */

public class TetrisUnitTest {

	@Test
	public void testMotionOfASquare(){

		//Create a square appearing at (0,4) at a grid.
		Grid g = new Grid();
		Square square = new Square(g, 0, Grid.WIDTH/2 - 1, Color.red, true);

		// Moving the square to the right for six steps, namely
		// call the move(Direction direction) for six times.
		// We can test the canMove(Direction direction) through move(Direction direction),
		// because it is inside canMove(Direction direction).
		for (int i = 0; i < 6; i++){
			square.move(Direction.RIGHT);
		}

		// Since the column for the grid is from 0 to 9, if we move the square from (0,4),
		// it should be reach the right edge of the grid.
		// The new location of the square will be (0,9).
		assertEquals(0, square.getRow());
		assertEquals(9, square.getCol());

		// Starting from the very right at (0,9), let's test if LEFT case works.
		// Moving the square to the left for six steps
		for (int i = 0; i < 8; i++){
			square.move(Direction.LEFT);
		}

		// The newest location for the square should be (0,1)
		// test if the location of the square is (0,1).
		assertEquals(0, square.getRow());
		assertEquals(1, square.getCol());
	}

	@Test
	public void testMotionOfLShape(){
		// Create a LShape piece at (1,4) in a grid
		// Locations for pieces of the LShape:
		// 	The top one:   		   (0,4)
		// 	The middle one:		   (1,4)
		// 	The bottom one: 	   (2,4)
		// 	The bottom right one:  (2,5)
		int row,col;
		row = 1;
		col = 4;
		Grid g = new Grid();
		LShape L = new LShape(row, col, g);

		// Moving the LShape to the right for three steps
		int steps = 3;
		for (int i = 0; i < steps; i ++){
			L.move(Direction.RIGHT);
		}

		// New locations for the LShape after moving three steps to the right
		// should reach the edge of the right at (1,8).
		int finalCol = col + steps; // the finalCol is defined for the middle suqare.

		if (finalCol > 8){	// if the LShape reaches the edge,
			finalCol = 8;   // the lcoation for the middle piece should be (1, 8).
							// and the bottom right one will reach the edge at (2, 9).
		}

		// Expected result:
		// The top one:   		  (0,7)
		// The middle one:		  (1,7)
		// The bottom one: 	  	  (2,7)
		// The bottom right one:  (2,8)
		Point[] newLocation = {new Point(row - 1, finalCol),
							   new Point(row, finalCol),
							   new Point(row + 1, finalCol),
							   new Point(row + 1, finalCol + 1)};


		// check that the LShape piece has been updated correctly
		for (int i = 0; i < 4; i++){
			assertEquals(newLocation[i], L.getLocations()[i]);
		}


		//**********************************
		// Let's test the Left direction now.
		// Moving the L-shape piece to the left for four steps.
		steps = 4;
		for (int j = 0; j < steps; j ++){
			L.move(Direction.LEFT);
		}

		// New locations for the LShape after moving four steps to the left
		// should be at (1,8).

		// the finalCol is defined for the middle square.
		finalCol = finalCol - steps;

		if (finalCol < 0){	// if the LShape reaches the edge,
			finalCol = 0;   // the location for the middle piece should be (1, 0).
								// and the bottom right one will reach the edge at (2, 1).
		}

		// Expected result:
		// The top one:   		  (0,3)
		// The middle one:		  (1,3)
		// The bottom one: 	  	  (2,3)
		// The bottom right one:  (2,4)
		Point[] newLocationLeft = {new Point(row - 1, finalCol),
							       new Point(row, finalCol),
					   	           new Point(row + 1, finalCol),
					   	           new Point(row + 1, finalCol + 1)};

		// check that the LShape piece has been updated correctly
		for (int j = 0; j < 4; j++){
			assertEquals(newLocationLeft[j], L.getLocations()[j]);

		}
	}

	@Test
	public void testCheckRows() {
		// Create a grid with a full row at the bottom
		// and two squares above the full bottom row
		Grid g = new Grid();
		// full bottom rows
		for (int col = 0; col < Grid.WIDTH; col++) {
			g.set(Grid.HEIGHT - 1, col, Color.GREEN);

		}

		// add two squares above the two bottom row
		g.set(Grid.HEIGHT - 2, 3, Color.RED);
		g.set(Grid.HEIGHT - 3, 3, Color.RED);

		// remove the full row
		g.checkRows();

		// check that the grid has been updated correctly
		for (int row = 0; row < Grid.HEIGHT; row++) {
			for (int col = 0; col < Grid.WIDTH; col++) {
				// check the square at (row,col)
				// must have: (18,3) and (19,3) set
				// and all of the others not set
				if ((row == 18 || row == 19) && col == 3) {
					assertTrue(g.isSet(row, col));
				} else {
					assertFalse(g.isSet(row, col));
				}
			}
		}
	}

	@Test
	public void testSpaceButton(){
		// Create a LShape piece with row = 1 and col = 4
		int row = 1, col = 4;
		Grid g = new Grid();
		LShape L = new LShape(row, col, g);

		L.move(Direction.SPACE);

		// After run the space button,
		// the LShape should be moved to the bottom just in one step.
		Point[] newLocation = {new Point(17, col),
				   new Point(18, col),
				   new Point(19, col),
				   new Point(19, col + 1)};

		// check that the LShape piece has been updated correctly
		for (int i = 0; i < 4; i++){
			assertEquals(newLocation[i], L.getLocations()[i]);
		}
	}

	@Test
	public void testRotation(){
		/**
		 * Check that rotation goes as planned when it is allowed
		 */

		Grid g = new Grid();

		/**
		 * Test rotation of L Shape
		 */

		// Create a L Shape at (1, 4) in grid
		Piece L = new LShape(1, 4, g);

		// Rotate it once;
		L.rotate();

		// Expected new location array for the L Shape
		Point[] expectedLocationL = {new Point(1, 5),
									new Point(1, 4),
									new Point(1, 3),
									new Point(2, 3)};

		// check that the L Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationL[i], L.getLocations()[i]);
		}

		/**
		 * Test rotation of Z Shape
		 */

		// Create a Z Shape at (1, 1) in grid
		Piece Z = new ZShape(1, 1, g);

		// Rotate it once;
		Z.rotate();

		// Expected new location array for the Z Shape
		Point[] expectedLocationZ = {new Point(0, 1),
									 new Point(1, 1),
									 new Point(1, 0),
									 new Point(2, 0)};

		// check that the Z Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationZ[i], Z.getLocations()[i]);
		}

		/**
		 * Test rotation of J Shape
		 */
		// Create a J Shape at (1, 8) in grid
		Piece J = new JShape(1, 8, g);

		// Rotate it once;
		J.rotate();

		// Expected new location array for the J Shape
		Point[] expectedLocationJ = {new Point(1, 9),
									 new Point(1, 8),
									 new Point(1, 7),
									 new Point(0, 7)};

		// check that the J Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationJ[i], J.getLocations()[i]);
		}

		/**
		 * Test rotation of T Shape
		 */
		// Create a T Shape at (4, 1) in grid
		Piece T = new TShape(4, 1, g);

		// Rotate it once;
		T.rotate();

		// Expected new location array for the T Shape
		Point[] expectedLocationT = {new Point(3, 1),
									 new Point(4, 1),
									 new Point(5, 1),
									 new Point(4, 0)};

		// check that the T Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationT[i], T.getLocations()[i]);
		}

		/**
		 * Test rotation of S Shape
		 */
		// Create a S Shape at (4, 4) in grid
		Piece S = new SShape(4, 4, g);

		// Rotate it once;
		S.rotate();

		// Expected new location array for the S Shape
		Point[] expectedLocationS = {new Point(5, 4),
									 new Point(4, 4),
									 new Point(4, 3),
									 new Point(3, 3)};

		// check that the S Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationS[i], S.getLocations()[i]);
		}

		/**
		 *  Test rotation of Square Shape
		 */
		// Create a Square Shape at (4, 8) in grid
		Piece Square = new SquareShape(4, 8, g);

		// Rotate it once;
		Square.rotate();

		// Expected new location array for the Square Shape
		Point[] expectedLocationSquare = {new Point(4, 7),
								  	      new Point(4, 8),
									      new Point(5, 7),
									      new Point(5, 8)};

		// check that the Square Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationSquare[i], Square.getLocations()[i]);
		}

		/**
		 * Test rotation of Bar Shape
		 */
		// Create a Bar Shape at (8, 4) in grid
		Piece Bar = new BarShape(8, 4, g);

		// Rotate it once;
		Bar.rotate();

		// Expected new location array for the Bar Shape
		Point[] expectedLocationBar = {new Point(7, 4),
								  	   new Point(8, 4),
									   new Point(9, 4),
									   new Point(10, 4)};

		// check that the Bar Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationBar[i], Bar.getLocations()[i]);
		}
	}

	@Test
	public void testIfRotate(){

		/**
		 *  Case 1: no rotation when the piece reaches out of the grid
		 */

		// Create a J Shape right next to the right edge
		Grid g = new Grid();
		Piece J = new JShape(1, 9, g);

		// Rotate the J Shape
		J.rotate();

		// Due to the block of the right edge,
		// the J Shape should not rotate and stay at the same place.
		// Expected new location array for the J Shape:
		Point[] expectedLocationJ = {new Point(0, 9),
								 	 new Point(1, 9),
									 new Point(2, 9),
									 new Point(2, 8)};

		// check that the J Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationJ[i], J.getLocations()[i]);
		}

		/**
		 *  Case 2: no rotation if the new location of square is occupied
		 */

		// Create a full column at row = 4 in the grid
		for (int row = 0; row < Grid.HEIGHT; row ++) {
			g.set(row, Grid.WIDTH - 6, Color.GREEN);
		}

		// Create a L Shape right next to the full column
		Piece L = new LShape(2, 5, g);

		// Try to rotate the L Shape
		L.rotate();

		// Due to the block of the full column,
		// the L Shape should not rotate and stay at the same place.
		// Expected new location array for the L Shape:
		Point[] expectedLocationL = {new Point(1, 5),
									 new Point(2, 5),
									 new Point(3, 5),
									 new Point(3, 6)};

		// check that the J Shape piece has been updated correctly
		for (int i = 0; i < 4; i ++){
			assertEquals(expectedLocationL[i], L.getLocations()[i]);
		}
	}
}
