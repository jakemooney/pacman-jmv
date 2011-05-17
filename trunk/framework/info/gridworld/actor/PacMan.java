package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;

public class PacMan extends Bug{ //?????? OK, whatever - Vivek
	
	public PacMan(){
		setColor(Color.yellow);
	}
	
	/*public void act(){
		if (canMove())
			move();
		else
			removeSelfFromGrid();
	}*/
	
	/*just testing code, replace with your own class please jake
	public void move(){
		 Grid<Actor> gr = getGrid();
	        if (gr == null)
	            return;
	        Location loc = getLocation();
	        Location next = loc.getAdjacentLocation(getDirection());
	        if (gr.isValid(next))
	            moveTo(next);
	        else
	            removeSelfFromGrid();
	}*/

}
