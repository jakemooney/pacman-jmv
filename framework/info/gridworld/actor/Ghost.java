package info.gridworld.actor;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.*;
public class Ghost extends Critter{
	private static boolean vulnerable;
	private Actor covered;
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
			
			moveTo(getMoveLocation(loc));	
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
		if (getGrid().get(loc) == null)
			return loc;
		return selectMoveLocation(getMoveLocations());  
	}
	public void move(Location loc){
		if (getGrid().get(loc).equals(null)){
			Location loc2 = this.getLocation();
			moveTo(loc);
		//	if (!covered.equals(null))
				//covered.putSelfInGrid(loc2);
			
		}
		else{
			covered = getGrid().get(loc);
		}
	}
}

