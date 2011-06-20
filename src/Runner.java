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
	 * 	 */
	
	
	
	
	
	
	
	
	
	
	/**
	 * BUG LIST

	1. try to make it so that ghosts do not vanish once eaten
	
	XXXXXXXXXXX2. "updatePoints()" when there's a change in level or death //think it works
	
	XXXXXXX3. "actor already contained in a grid" error when ghosts are moving over another actor //occassionally get
		
	XXXXXXXXXXXXXXXX5. allow the pacman's eating to be seen (ie checkmate mode right now)
	
	XXXXXXXXXXXXXX6. ghosts occassionally (?) leave behind a pellet
			
	XXXXXXXXXXXXXXXXXXX9. gaming continues even after x-ings out //APPARENTLY NOT?!?!?!	
	
	xxxxxxxxxxxxxxxxx11. REPAINT AT END SOMTIEMS NOT WORK
						-have yet to see this
	
	xxxxxxxxxxxxxxxx12. two ghosts behind you at end yields a death?
					
						-have yet to see this
	
	xxxxXXXXXXXXXXXXXXXXXXXX13. multiple part bug solution for ending levels
		-needs to setCoveredActor to null when it moves into an empty spot. can't just keep it like that
		-needs to setCoveredActor to null when it moves it into the center.
	
	14. removes a pellet when you die on level2? bottom left corner
	
	15. why do you have a kill/death in both pacman and ghost? should only need one
	
	16. ghosts constantly cover each other, play leapfrog
		-in pacman, the proper points wouldn't return once someone eats a ghost
		
	17. error in ReleaseGhosts()
	
	18. isvulnerable shouldn't be static
	
	
	ADDITIONS LIST
	-make a third map
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
		
		PacWorld pac = new PacWorld(PacWorld.level2());
		pac.show();
        
		//Sound intro = new Sound("//E:/VG/Grade 11/AP Java/Programs/PacMan/src/pacman_intro.wav"); //?????
		//intro.play();
	}
}