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
	 * \
	 * TODO: displayMap.findDisplayFor
	 */
	
	
	
	
	
	
	
	
	
	
	/**
	 * BUG LIST

	1. try to make it so that ghosts do not vanish once eaten
	
	2. "updatePoints()" when there's a change in level or death //think it works
	
	3. "actor already contained in a grid" error when ghosts are moving over another actor //occassionally get
	
	4. opens up new level once click play or step //maybe? no.
	
	5. allow the pacman's eating to be seen (ie checkmate mode right now)
	
	6. ghosts occassionally (?) leave behind a pellet
	7. two actors atop each other? ghost + pellet eatings?
	8. LEVELS ARE NOT BEING FINISHED
	
	9. gaming continues even after x-ings out //APPARENTLY IT'S WRONG?!?!?!
	
	10. when pacman eats a ghost who's on a pellet, he'll only eat the ghost and thus the level won't complete
	
	11. REPAINT AT END SOMTIEMS NOT WORK
	
	12. two ghosts behind you at end yields a death
	
	
	
	ADDITIONS LIST
	-make a third map
	-ghost points increase for each ghost eaten during powerpellet
	-icons representing lives
	-ghosts scatter mode
	-"press enter to start" or something
	-have a timer after pacman's death
	-animate pacman's death
	-flash the ghosts white right before they're about to be nonblue again
	-get them to be eyes
	-sounds
	
	-LEARN HOW TO ACCESS DIFFERENT IMAGES
	
	-undo HTML internet fetch
	-BIOs
	 */
	
	public static void main(String[] args) {
		
		PacWorld pac = new PacWorld(PacWorld.level1());
		pac.show();
        
		//Sound intro = new Sound("//E:/VG/Grade 11/AP Java/Programs/PacMan/src/pacman_intro.wav"); //?????
		//intro.play();
	}
}