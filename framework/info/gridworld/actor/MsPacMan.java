package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;

public class MsPacMan extends Bug{
	
	public MsPacMan(){
		setDirection(90);
	}
	
	public void act(){
		if (canMove())
			move();
		else{
			turn();
			move();
		}
	}
    public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
    }
    
    public void turn(){
    	setDirection(-getDirection());
    }

}