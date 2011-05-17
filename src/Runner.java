import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import info.gridworld.actor.Actor;
import info.gridworld.actor.PacMan;
import info.gridworld.grid.Location;
import info.gridworld.gui.GUIController;
import info.gridworld.world.PacWorld;
import info.gridworld.world.World;


public class Runner {

	/**
	 * @author Max
	 * 
	 * Runs a PacWorld. Not intended to place new actors. The idea is that the PacWorld
	 * object should contain all of those actors, because a PacWorld is the game itself.
	 */
	
	
	
	//Why is everything commented out??
	
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
	 */
	
	public static void main(String[] args) {
		PacWorld pac = new PacWorld();
		PacMan p = new PacMan();
		pac.add(new Location(2,3), p);
		pac.show();
	}
}
