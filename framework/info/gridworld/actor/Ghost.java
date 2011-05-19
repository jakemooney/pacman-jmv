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
			Location loc2 = this.getLocation();
			moveTo(getMoveLocation(loc));
			if (covered){
				coveredActor.putSelfInGrid(getGrid(),loc2);
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
		
		//currently can only move to empty spots, must be able to move over pellets
		
		
		int dir = this.getLocation().getDirectionToward(target);
		if (vulnerable)
			dir += 180;
		Location loc = getLocation().getAdjacentLocation(dir);
		if (getGrid().get(loc) instanceof PacMan){
			PacMan.kill();
			return loc;
		}
		else if (getGrid().get(loc) == null)
			return loc;
		else if (getGrid().get(loc) instanceof Pellet || getGrid().get(loc) instanceof PowerPellet1 
				|| getGrid().get(loc) instanceof PowerPellet2){
			coveredActor = getGrid().get(loc);
			covered = true;
			getGrid().get(loc).removeSelfFromGrid();
			return loc;
		}
		return selectMoveLocation(getMoveLocations());  
	}

}

