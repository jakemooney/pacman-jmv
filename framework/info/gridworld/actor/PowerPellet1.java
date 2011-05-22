package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class PowerPellet1 extends Pellet{
	
	public PowerPellet1(){
		
	}
	
	public void act(){
		Location current = getLocation();
		Grid<Actor> g = getGrid();
		removeSelfFromGrid();
		PowerPellet2 newImage = new PowerPellet2();
		newImage.putSelfInGrid(g, current);
	}

}
