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
		if (getGrid().get(loc) instanceof PacMan){
			PacMan.kill();
			System.out.println("a");
			return loc;
		}
		else if (getGrid().get(loc) == null){
			System.out.println("b");
			return loc;
		}
		else if (getGrid().get(loc) instanceof Pellet || getGrid().get(loc) instanceof PowerPellet1 
				|| getGrid().get(loc) instanceof PowerPellet2){
			System.out.println("c");
			coveredActor = getGrid().get(loc);
			covered = true;
			getGrid().get(loc).removeSelfFromGrid();
			return loc;
		}
		System.out.println("d");

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
}

