import info.gridworld.world.PacWorld;

public class Runner {

	/**
	 * @author Max
	 * 
	 * This class runs a PacWorld. Not intended to place new actors. The idea is that the PacWorld
	 * object should contain all of those actors, because a PacWorld is the game itself.
	 */
	
	/**
	 * notes:
	 * 505 is the default speed for gridworld. 505 milliseconds of delay between each step.
	 * gridpanel.drawOccupants()  
	 * 
	 * GUIcontroller():
	 * "timer = new Timer(INITIAL_DELAY, new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                step();
            }
        });"
     *
     * the behavior after each click of the timer!
     * 
     * 
     * imageDisplay constructor perhaps the key to open mouthed animation / ghost wiggles
     * 
     * consider deleting MenuMaker.java
     * 
     * worldFrame.makeMenus()
     * 
     * he doesn't run because he thinks he's blocked by the pellets. ah.
     * 
     * have to re-evaluate that for:each loop because it evaluates top-down
	 */

	/**
	 * TODO: speed up the timer and remove the slider
	 * 	-done
	 * TODO: gnulicense...
	 */
	
	public static void main(String[] args) {
		
		PacWorld pac = new PacWorld(PacWorld.level1());
		pac.show();
		new AePlayWave("pacman_intro.wav").start();
        
		//Sound intro = new Sound("//E:/VG/Grade 11/AP Java/Programs/PacMan/src/pacman_intro.wav"); //?????
		//intro.play();
	}
}