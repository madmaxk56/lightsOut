package game;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The Counter Class is an extension of the JFrame class and implements the Runnable interface. This
 * class creates the windows that will hold information on the number of clicks taken for the current
 * level as well as the sum of clicks from all levels.
 *
 * The Counter class mainly listens to the Field class and does no work in the run() method other than
 * setting itself visible. The method's only user input is the reset button which if pressed, will
 * reset the squares in the Field. In addition, if a new field is created, values wil adjust accordingly
 * on the counter. Disposal is done via main.
 */

public class Counter extends JFrame implements Runnable {
	private static final long serialVersionUID = -261709293731680744L;

 	//Current Grid and Total counts
	private int count;
	private int total;

	//Reset button
	private JButton jReset;
	//Counter labels for gui
	private JLabel jCount, jTotal;

	/**
   * The Counter constructor creates the gui that will be used to count and display the count information
	 * to the user. Both count integer values are set to 0. After all initial values are set, the constructor
	 * calls createContents() to create the JFrame.
	 */
	public Counter() {
		total = 0;
		count = 0;
		createContents();
	}

  /**
   * Runs the Counter thread. Its only purpose is to show the Jframe and its contents. Disposal is
	 * done via the Main method.
	 */
	@Override
	public void run() {
		setVisible(true);
	}

	/**
	 * The createContents method is the last thing called by the Contructor. It creates the Jframe
	 * pane and sets the parameters for the counter and its labels. It then creates a button that
	 * will act as the reset button and creates a listener for it.
	 */
	protected void createContents(){
		//Window Frame Settings
		getContentPane().setLayout(new GridLayout(3, 1));
		setSize(75, 100);
		setLocation(600,100);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Sum count and its label
		jTotal = new JLabel("Total: " + total);
		jTotal.setHorizontalAlignment(JLabel.CENTER);
		jTotal.setFont(new Font("Arial", Font.BOLD, 20));
		add(jTotal);

		//Level count and its label
		jCount = new JLabel("Count: " + count);
		jCount.setHorizontalAlignment(JLabel.CENTER);
		jCount.setFont(new Font("Arial", Font.BOLD, 20));
		add(jCount);

		//Reset button instantiation
		jReset = new JButton();
		jReset.addActionListener(listener);
		jReset.setText("Reset");
		jReset.setFont(new Font("Arial", Font.BOLD, 20));
		add(jReset);
	}

	/**
	 * User input is monitored via the ActionListener. The listener monitors the reset button. When a
	 * press is registered, it sends a signal to the field to reset the current grid, as well as resets
	 * all counters to thier values at the beginning of the current level with the reset() method.
	 */
	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JButton) {
				Main.actionFromReset();
				reset();
			}
		}
	};

  /**
	 * Subtracts the count of the current level from the total count, resetting the total count to its
	 * value athe beginning of the level. It then calls the newField() method to reset all other values.
	 */
	public void reset() {
		total -= count;
		newField();
	}

	/**
	 * Called by the field to signify a new level, or called locally to signify a reset, the newField
	 * method resets the count to 0 while updating the value of total.
	 */
	public void newField() {
		count = 0;
		jTotal.setText("Total: " + total);
		jCount.setText("Count: " + count);
	}

	/**
	 * Called by the Field Class, it increments both count and total values and updates thier respective
	 * labels to reflect them.
	 */
	public void addToCount() {
		jCount.setText("Count: " + ++count);
		jTotal.setText("Total: " + ++total);
	}

	/**
	 * Returns the total number of moves taken throughout the game
	 * @return The total number of moves
	 */
	public int getTotal() {
		return total;
	}
}
