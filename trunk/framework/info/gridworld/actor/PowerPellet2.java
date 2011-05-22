package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class PowerPellet2 extends Pellet{
	
	public PowerPellet2(){
		
	}
	
	public void act(){
		Location current = getLocation();
		Grid<Actor> g = getGrid();
		removeSelfFromGrid();
		PowerPellet1 newImage = new PowerPellet1();
		newImage.putSelfInGrid(g, current);
	}

}
