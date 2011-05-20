package info.gridworld.actor;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.*;
public class Ghost extends Critter{
	private static boolean vulnerable;
	private boolean covered;
	private Actor coveredActor;
	private Location previousloc;
	public Ghost(){
		vulnerable = false;
		previousloc = null;
	}
	public Ghost(Color c){
		this.setColor(c);
	}
	public static boolean isvulnerable(){
		return vulnerable;
	}
	public static void setVulnerable(boolean a){
		vulnerable = a;
	}
	public void act(){
		Location target = FindPacMan();
		if (target == null)
			return;
		else{
			Location loc = getMoveLocation(target);
			previousloc = this.getLocation();
			moveTo(getMoveLocation(loc));
			if (covered){
				coveredActor.putSelfInGrid(getGrid(),previousloc);
				covered = !covered;
			}
		
		}
	}
	
	public Location FindPacMan(){
		ArrayList<Location> locs  = getGrid().getOccupiedLocations();
		for (Location l: locs){
			if (getGrid().get(l) instanceof PacMan)
				return l;
		}
		return null;
	}
	public Location getMoveLocation(Location target){ 		
		
		int dir = this.getLocation().getDirectionToward(target);
		if (vulnerable)
			dir += 180;
		Location loc = getLocation().getAdjacentLocation(dir);
		if (getGrid().get(loc) instanceof PacMan && !(loc.equals(previousloc))){
			PacMan.kill();
			return loc;
		}
		else if (getGrid().get(loc) == null && !(loc.equals(previousloc))){
			return loc;
		}
		else if ((getGrid().get(loc) instanceof Pellet || getGrid().get(loc) instanceof PowerPellet1 
				|| getGrid().get(loc) instanceof PowerPellet2) && 
				!(loc.equals(previousloc))){
			coveredActor = getGrid().get(loc);
			covered = true;
			getGrid().get(loc).removeSelfFromGrid();
			return loc;
		}

		return selectMoveLocation(getMoveLocations());  
	}
	 public ArrayList<Location> getMoveLocations()
	    {
	        ArrayList<Actor> neighbors = getGrid().getNeighbors(getLocation());
	        ArrayList<Location> locs = new ArrayList();
	        for (int x = 0; x<neighbors.size(); x++){
	        	if (!(neighbors.get(x) instanceof Pellet ||
	        			neighbors.get(x) instanceof PowerPellet1 ||neighbors.get(x) instanceof PowerPellet2))
	        		neighbors.remove(x);
	        	else if (!(neighbors.get(x).getLocation().equals(previousloc)))
	        		locs.add(neighbors.get(x).getLocation());
	        }
	        return locs;
	    }
	 
	 //Finds the direct distance between any two actors on the grid, defined by the hypotenous of a right triangle
	 public double DistanceBetween(Actor a, Actor b){
		 return Math.sqrt((double)(Math.pow(Math.abs(a.getLocation().getCol() - b.getLocation().getCol()), 2) 
				 + Math.pow(Math.abs(a.getLocation().getRow() - b.getLocation().getRow()), 2)));

	 }
}

