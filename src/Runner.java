import info.gridworld.gui.GUIController;
import info.gridworld.world.World;


public class Runner {

	/**
	 * @author Max
	 * 
	 * Runs a PacWorld. Not intended to place new actors. The idea is that the PacWorld
	 * object should contain all of those actors, because a PacWorld is the game itself.
	 */
	
	/**
	 * notes:
	 * 505 is the default speed for gridworld. 505 milliseconds of delay between each step.
	 * 
	 */
	
	public static void main(String[] args) {
		World pac = new World();
		pac.show();
	}
}
