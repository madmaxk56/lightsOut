package game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The Field Class is an extension of the JFrame class and implements the Runnable interface. This
 * class creates the window that the player will mostly be using during play. Starting as a threaded
 * process, the Field Class creates a grid of buttons, based on an input size parameter.
 *
 * The Field class mainly responds to user input, and barely does any work itself within the run()
 * method. In between starting and disposing of the grid, the method sleeps, waiting for the lightsOut
 * flag, signifying that the player has beaten the grid. After window disposal, the program returns to
 * Main.
 *
 * @author Max Krider
 */
public class Field extends JFrame implements Runnable{
	private static final long serialVersionUID = -8837291784062862311L;

	//Grid Square Array
	private Square[] grid;
	//Grid Length and Area
	int gridLength;
	int gridArea;

	//Win Pattern Check
	boolean lightsOut;

	/**
	 * The Field constructor creates the grid that the player will use. After setting its length and
	 * area parameters using the input parameter "size", an array of Squares is created that will fill
	 * the JFrame. Because the grid will always be square, the grid area will always be equal to the
	 * grid length squared. After the array is instantiated, the constructor calls createConents() to
	 * create the JFrame.
	 * @param size - The number of squares in a grid's row
	 */
	public Field(int size) {
		lightsOut = false;
		gridLength = size;
		gridArea = size*size;
		grid = new Square[gridArea];
		createContents();
	}

	/**
	 * Runs the Field thread. Its only purpose is to show the grid and wait for the boolean value
	 * lightsOut to become true.  Once it does, the field is disposed of. In addition, the method
	 * also calls back to main for thread synchronization during startup.
	 */
	@Override
	public void run() {
		getMessage();
		setVisible(true);
		Main.actionFromField(2);	//Information on parameter can be found on Main.java
		try {
			while(!lightsOut) {
				Thread.sleep(100);
			}
		}catch (InterruptedException e){
			System.out.println("Interruption");
		} finally {
			dispose();
		}
	}

	/**
	 * Starting messages for each field, depending on grid length.
	 */
	public void getMessage() {
		switch(gridLength) {
		case 1:JOptionPane.showMessageDialog(null, "First! The Tutorial. Press the big yellow square."); break; //Min 1 (1)
		case 2:JOptionPane.showMessageDialog(null, "Easy! Now for 2x2. Turn all the yellow squares to black."); break; //Min 4 (12)
		case 3:JOptionPane.showMessageDialog(null, "How about somthing harder?"); break; //Min 5 (13)
		case 4:JOptionPane.showMessageDialog(null, "This one is easier than you think."); break; //Min 4 (x)
		case 5:JOptionPane.showMessageDialog(null, "This is where it gets fun!"); break; //Min 15 (45)
		case 6:JOptionPane.showMessageDialog(null, "What don't you press?"); break; //Min 28 (1346)
		case 7:JOptionPane.showMessageDialog(null, "Think Symmetry."); break; //Min 33 (12467)
		case 8:JOptionPane.showMessageDialog(null, "I see you're here for the long hull."); break; //Min 40 (1278)
		case 9:JOptionPane.showMessageDialog(null, "Here's a biggie."); break; //Min 39 (19)
		case 10:JOptionPane.showMessageDialog(null, "Last One!"); break; //Min 44 (13810)
		}
	}

	/**
	 * The createContents method is the last thing called by the Constructor. It creates the JFrame
	 * pane and sets the parameters for the field. Afterwards, it instantiates each Square in the
	 * array and adds its listener before adding it to the grid.
	 */
	protected void createContents() {
		//Window JFrame Settings
		getContentPane().setLayout(new GridLayout(gridLength, gridLength));
		setSize(500, 500);
		setLocation(10,10);
		setResizable(false);
		setTitle(gridLength + " x " + gridLength + " Grid");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//X and Y variables tie the array positions to a Cartesian coordinate system.
		int x=0;
		int y=0;

		//Square instantiation and addition to the grid with coordinates and index
		for(int i=0; i<gridArea; i++) {
			grid[i] = new Square(x++, y, i);
			grid[i].addActionListener(listener);
			//Go to next row when at end of current row
			if(x >= gridLength) {
				x=0;
				y++;
			}
			add(grid[i]);
		}
	}

	/**
	 * Called by the Counter object through the main method. If the user presses the reset button
	 * on the counter, all squares will revert back on.
	 */
	public void reset() {
		for(Square s: grid) {
			s.forceOn();
		}
	}

	/**
	 * User input is monitored via the ActionListener. The listener monitors for any button press
	 * on the grid. When a press is registered, the listener goes through the array of buttons
	 * to determine which button had been pressed in the grid. Once the square has been identified,
	 * the listener calls the hitLight method, passing in the square parameter "s". After every
	 * event, the listener calls the checkOut() method to determine if a win situation has been
	 * reached, changing the lightsOut value to true if the win situation is confirmed. In addition,
	 * after every event, a signal is sent to the counter to increase the number of clicks on both
	 * total and current level.
	 */
	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JButton) {
				for(Square s: grid) {
					if(e.getSource().equals(s)) {
						hitLight(s);
						Main.actionFromField(1); //Information on parameter can be found on Main.java
					}
				}
			}
			if(checkOut())
				lightsOut = true;
		}
	};

	/**
	 * Light values for squares are changed here. Starting with the user-pressed square s, the square's
	 * light value is changed from yellow to black or visa-versa. The square that is immediately above,
	 * below, left, and right of the user-pressed square are also changed, provided that the button
	 * the user had pressed is not along a wall or in a corner of the grid. To determine the locations
	 * of the Squares in the Array from it's Cartesian position, the method XY2A is called.
	 * @param s - Square pressed by the user
	 */
	private void hitLight(Square s) {
		grid[XY2A(s.getXpos(), s.getYpos())].changeSquare(); //Changes Center
		if(s.getYpos() != 0)
			grid[XY2A(s.getXpos(), s.getYpos()-1)].changeSquare(); //Changes Top
		if(s.getXpos() != 0)
			grid[XY2A(s.getXpos()-1, s.getYpos())].changeSquare(); //Change Left
		if(s.getYpos() < gridLength-1)
			grid[XY2A(s.getXpos(), s.getYpos()+1)].changeSquare(); // Changes Bottom
		if(s.getXpos() < gridLength-1)
			grid[XY2A(s.getXpos()+1, s.getYpos())].changeSquare(); //Change Right
	}

	/**
	 * The private method that converts the Square's XY coordinates to Array position. This method
	 * goes through the entire array of squares. At each position, the Square's stored XY position
	 * is compared to the input XY parameters. If there is a match, the Square's array position is
	 * returned.
	 * @param x - The X-coordinate of the square on the Cartesian grid
	 * @param y - The Y-coordinate of the square on the Cartesian grid
	 * @return The position of the square in the Array
	 */
	private int XY2A(int x, int y) {
		for(Square s: grid) {
			if(s.getXpos() == x && s.getYpos() == y) {
				return s.getApos();
			}
		}
		return -1;
	}

	/**
	 * This private method goes through the entire array of Squares. At each square, the method checks
	 * the Square's light value. If all light values are at 0, or false, the win condition has been
	 * met and the method will return true. If the win condition is not met, the method will return
	 * false.
	 * @return A boolean value stating the win condition
	 */
	private boolean checkOut() {
		for(Square s: grid) {
			if(s.getLight()) {
				return false;
			}
		}
		return true;
	}
}
