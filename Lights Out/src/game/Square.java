package game;

import java.awt.Color;

import javax.swing.JButton;
/**
 * The Square Class in an extension of the JButton class. This class is an object that fills the
 * Array of Squares in the Field class. Along with it's inherited traits from JButton, the Square
 * class also holds values for its X and Y coordinate position in the Field, as well as its Array
 * index in grid[]. In addition, Square also holds a boolean value for the state of the button,
 * if it is lighted or not. Methods include getting and setting X, Y, and Array positions,
 * getting the lighted value, changing the lighted value, and turning on the light from a field
 * reset;
 *
 * @author Max Krider
 */
public class Square extends JButton{
	private static final long serialVersionUID = -8837291784062862311L;

	//Boolean lighted value
	private boolean lighted;
	//X,Y, and Array integer positions
	private int xpos;
	private int ypos;
	private int apos;

	/**
	 * The Square constructor creates the squares that will be added to the grid[] array in the
	 * Field class. Each square is instantiated with its Cartesian X and Y coordinates, as well
	 * as its index in the array. Initial button settings are done here
	 * @param x The Square's X-coordinate in the grid
	 * @param y The Square's Y-coordinate in the grid
	 * @param a The Square's index in the array
	 */
	public Square(int x, int y, int a){
		xpos = x;
		ypos = y;
		apos = a;
		setBackground(Color.YELLOW);
		lighted = true;
	}

	/**
	 * Changes the light value of the square. This is done by changing the Square's background,
	 * from yellow to black or visa-versa, and changing the lighted boolean value accordingly
	 */
	public void changeSquare() {
		if(!lighted) {
			setBackground(Color.YELLOW);
			lighted = true;
		}
		else {
			setBackground(Color.BLACK);
			lighted = false;
		}
	}

	/**
	 * Forces the Square to turn back on, regardless of it's previous position. This method
	 * is called during a Field reset.
	 */
	public void forceOn() {
		lighted = true;
		setBackground(Color.YELLOW);
	}

	/**
	 * X-coordinate getter.
	 * @return The X-coordinate for the Square
	 */
	public int getXpos() {
		return xpos;
	}

	/**
	 * Y-coordinate getter.
	 * @return The Y-coordinate for the Square
	 */
	public int getYpos() {
		return ypos;
	}

	/**
	 * Array index getter
	 * @return The Square's index in the Array
	 */
	public int getApos() {
		return apos;
	}

	/**
	 * lighted boolean getter
	 * @return The Square's boolean light value
	 */
	public boolean getLight() {
		return lighted;
	}

}
