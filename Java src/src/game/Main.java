package game;

import javax.swing.JOptionPane;

/**
 * This is the Main class that starts the LightsOut program . Main calls the Field and Counter Class
 * as threads of each other. The Counter will be instantiated once, and will stay for the entire playthrough.
 * The Field will be created 10 times consecutively, each with a different input value to determine
 * the number of buttons that will be on the grid (aka leveling up).
 *
 * @author Max Krider
 */
public class Main {

  //Counter gui
	static Counter count;

	//Grid square gui
	static Field level;

	/**
	 * Main method creates the new threads after a welcome message. The multithreading processes are done
     * in the main method. Fields are created, ran, and disposed in a loop before ending message and
	 * perfect score message show before counter is disposed.
	 * 
	 * @param args There are no input arguments necessary.
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "Welcome To Lights Out");

		count = new Counter();
		Thread tCount = new Thread(count, "counter");
		tCount.start();


		for(int i = 1; i <= 10; i++) {
			level = new Field(i);
			Thread tLevel = new Thread(level, "Level "+i);
			tLevel.start();
			try {
				tLevel.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}

		if(count.getTotal() == 213) {
			JOptionPane.showMessageDialog(null, "Congradulations! You have obtained a perfect score!");
		}
		else {
			JOptionPane.showMessageDialog(null, "You have beaten Lights Out! You are " + (count.getTotal()-213) + " move(s) from a perfect score");
		}
		count.dispose();
	}

	/**
	 * Creates a communication channel from the field to the counter through the main method. Called by
	 * Field class statically, it will accomplish one of two tasks depending the input parameter. If
	 * the value is 1, it will invoke a response to the counter to add to it's count. This happens on
	 * every click done by the user. If he value is 2, it will reset certain parameters in the Counter
	 * to represent a new field created by every thread instance.
	 * @param action Action identifier from the field
	 */
	public static void actionFromField(int action) {
		if(action == 1){
			count.addToCount();
		}
		else if(action == 2){
			count.newField();
		}
	}

	/**
	 * Creates a communication channel from the counter to the field through the main method. Called by
	 * Counter class statically, it invokes a response by the field to reset all of the squares. This
	 * happens when the reset button is clicked on the counter.
	 */
	public static void actionFromReset() {
		level.reset();
	}
}
