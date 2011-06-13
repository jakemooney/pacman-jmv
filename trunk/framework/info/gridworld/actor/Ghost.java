package info.gridworld.actor;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.*;
import info.gridworld.world.PacWorld;

import java.awt.Color;
import java.util.*;

import Levels.Level;

public class Ghost extends Critter{
	
	private static boolean vulnerable;
	private boolean covered, done;
	private Actor coveredActor;
	private Location previousloc, pacloc;
	private int type, count;
	
	/**
	 * Max's additions below
	 */
	private Level level; //the level contained within
	
	//sets the level
	public void setLevel(Level level){
		this.level = level;
	}
	
	//gets the level
	public Level getLevel(){
		return level;
	}
	
	public Ghost(){
		previousloc = null;
	}
	
	public int dir(){
		if (previousloc == null)
			return 90;
		return previousloc.getDirectionToward(getLocation());
	}
	
	//---------------------------------------------------------------------------------
	
	public Ghost(Color c, int type){
		this.setColor(c);
		vulnerable = false;
		previousloc = null;
		covered = false;
		this.type = type;
		count = 0;
		done = false;
		covered = false;
		coveredActor = null;	
	}
	
	
	public static boolean isvulnerable(){
		return vulnerable;
	}
	
	
	public static void setVulnerable(boolean a){
		vulnerable = a;
	}
	
	
	@Override
	public void act(){
		count++;
		setModeColor();
		ReleaseGhost();
		if (vulnerable && count%2==0)
			return;
		if (FindTarget() == null)
			return;
		Location MOVE = GetBestMove(EliminateMoveLocations(FindPossibleMoves()), FindTarget());
		if ((FindPacMan() != null && (getLocation().getCol() == 0)) && !previousloc.equals(new Location(getLocation().getRow(), getGrid().getNumCols()-1)))
			MOVE = new Location(getLocation().getRow(), getGrid().getNumCols()-1);
		else if ((FindPacMan() != null && (getLocation().getCol() == getGrid().getNumCols()-1)) && !previousloc.equals(new Location(getLocation().getRow(), 0)))
			MOVE = new Location(getLocation().getRow(), 0);
		if (MOVE == null || !getGrid().isValid(MOVE))
			return;
		
		previousloc = getLocation();
		if (getGrid().get(MOVE) == null){
			if (covered){
				moveTo(MOVE);
				coveredActor.putSelfInGrid(getGrid(), previousloc);
				covered = false;
				coveredActor = null;
			}
			else moveTo(MOVE);
		}
		else if (getGrid().get(MOVE) instanceof Pellet || getGrid().get(MOVE) instanceof PowerPellet1 
				|| getGrid().get(MOVE) instanceof PowerPellet2 || getGrid().get(MOVE) instanceof Fruit
				|| getGrid().get(MOVE) instanceof Ghost){
			if (covered){
				Actor tempcoveredActor = getGrid().get(MOVE);
				moveTo(MOVE);
				coveredActor.putSelfInGrid(getGrid(), previousloc);
				covered = false;
				coveredActor = tempcoveredActor;
				covered=true;
			}
			else{
				coveredActor = getGrid().get(MOVE);
				covered = true;
				moveTo(MOVE);
			}
		}
		else if ((getGrid().get(MOVE) instanceof PacMan) && !vulnerable){			
			PacMan p = (PacMan) getGrid().get(MOVE);
			PacWorld.getFrame().repaint();
			p.kill();
			p.removeSelfFromGrid();
			moveTo(MOVE);
			if (p.getLives() > 0){
				PacWorld.restart();
			}
			else{
				PacWorld.gameOver();
			}	
		}
		else if ((getGrid().get(MOVE) instanceof PacMan) && vulnerable){
			PacMan.eatGhost();
			coveredActor = null;
			removeSelfFromGrid();
		}
		else
			return;
	}
	
	public Actor FindPacMan(){
		ArrayList<Location> locs  = getGrid().getOccupiedLocations();
		for (Location l: locs){
			if (getGrid().get(l) instanceof PacMan)
				return getGrid().get(l);
		}
		return null;
	}
	 public Actor FindGhost(int TYPE){
		 ArrayList<Location> locs  = getGrid().getOccupiedLocations();
			for (Location l: locs){
				if (getGrid().get(l) instanceof Ghost && ((Ghost) (getGrid().get(l))).getType() == TYPE)
					return getGrid().get(l);
			}
			return null;
	 }
	 
	 //Finds the direct distance between any locations actors on the grid, defined by the hypotenous of a right triangle
	 public double DistanceBetween(Location a, Location b){
		 return Math.sqrt((Math.pow(Math.abs(a.getCol() - b.getCol()), 2) 
				 + Math.pow(Math.abs(a.getRow() - b.getRow()), 2)));
	 }
	 
	 public Location FindTarget(){
		 Actor PacMan = FindPacMan();
		 if (PacMan == null)
			 return null;
		 if (vulnerable)
			 return new Location(getGrid().getNumRows()/2, getGrid().getNumCols()/2);
		 int pacdir = PacMan.getDirection();
		 if (type == 1)
			 return PacMan.getLocation();
		 else if (type == 2){
			 if(pacdir == Location.NORTH)
				 return new Location(PacMan.getLocation().getRow()-4, PacMan.getLocation().getCol()-4);
			 else if (pacdir == Location.SOUTH)
				 return new Location(PacMan.getLocation().getRow()+4, PacMan.getLocation().getCol());
			 else if (pacdir == Location.EAST)
				 return new Location(PacMan.getLocation().getRow(), PacMan.getLocation().getCol()+4);
			 else
				 return new Location(PacMan.getLocation().getRow(), PacMan.getLocation().getCol()-4);
		 }
		 else if (type == 3){
			 if (DistanceBetween(this.getLocation(), PacMan.getLocation()) < 10 )
				 return new Location(getGrid().getNumRows()-1, 0);
			 else
				 return PacMan.getLocation();
		 }
		 else{ 
			 if(pacdir == Location.NORTH)
				 return new Location(PacMan.getLocation().getRow()-2, PacMan.getLocation().getCol()-2);
			 else if (pacdir == Location.SOUTH)
				 return new Location(PacMan.getLocation().getRow()+2, PacMan.getLocation().getCol());
			 else if (pacdir == Location.EAST)
				 return new Location(PacMan.getLocation().getRow(), PacMan.getLocation().getCol()+2);
			 else
				 return new Location(PacMan.getLocation().getRow(), PacMan.getLocation().getCol()-2);
			 /**
			 Ghost blinky = (Ghost)FindGhost(1);
			 if(pacdir == Location.NORTH)
				 return new Location(PacMan.getLocation().getRow()-2-(int)DistanceBetween
						 (blinky.getLocation(), new Location(PacMan.getLocation().getRow()-2,
								 PacMan.getLocation().getCol()-2)), PacMan.getLocation().getCol()-2-
								 (int)DistanceBetween(blinky.getLocation(), 
										 new Location(PacMan.getLocation().getRow()-2, 
												 PacMan.getLocation().getCol()-2)));
			 else if (pacdir == Location.SOUTH)
				 return new Location(PacMan.getLocation().getRow()+2+(int)DistanceBetween
						 (blinky.getLocation(), new Location(PacMan.getLocation().getRow()+2,
								 PacMan.getLocation().getCol())), PacMan.getLocation().getCol()-2);			 else if (pacdir == Location.EAST)
				 return new Location(PacMan.getLocation().getRow(), PacMan.getLocation().getCol()+2);
			 else
				 return new Location(PacMan.getLocation().getRow(), PacMan.getLocation().getCol()-2);
			 
			 **/
		 }	 
	 }
	 public ArrayList<Location> FindPossibleMoves(){
		 ArrayList<Actor> neighbors = getGrid().getNeighbors(getLocation());
	        ArrayList<Location> locs = new ArrayList();
	        for (int x = 0; x<neighbors.size(); x++){
	        	if (!(neighbors.get(x) instanceof Pellet ||
	        			neighbors.get(x) instanceof PowerPellet1 ||neighbors.get(x) instanceof PowerPellet2 
	        			|| neighbors.get(x) instanceof PacMan || neighbors.get(x) instanceof Fruit
	        			|| neighbors.get(x) instanceof Ghost)){
	        		neighbors.remove(x);
	        		x--;
	        	}
	        	else if (getGrid().isValid(neighbors.get(x).getLocation()))
	        		locs.add(neighbors.get(x).getLocation());
	        }
	        ArrayList<Location> emptylocs = getGrid().getEmptyAdjacentLocations(getLocation());
	        for (int x = 0; x<emptylocs.size();x++){
	        	locs.add(emptylocs.get(x));
	        }
	        return locs;
	 }
	 public ArrayList<Location> EliminateMoveLocations(ArrayList<Location> locs){
		 if (previousloc != null){
			 for (int x = 0; x<locs.size(); x++){
				 if (locs.get(x).equals(previousloc)){
					 locs.remove(x);
					 x--;
				 }
			 }
		 }
		 for (int x = 0; x<locs.size(); x++){
			 if (getLocation().getDirectionToward(locs.get(x)) % 90 != 0){
				 locs.remove(x);
				 x--;
			 }
		 }
		 return locs;
	 }
	 public Location GetBestMove(ArrayList<Location> locs, Location target){
		 if (locs.size() == 0)
			 return null;
		 if (locs.size() == 1 && getGrid().get(locs.get(0)) instanceof Ghost)
			 return locs.get(0);
		 //locs = EliminateGhost(locs);
		 int choice = 0;
		 double distance = 500;
		 for (int x = 0; x<locs.size(); x++){
			 if (DistanceBetween(locs.get(x), target) < distance){
				 distance = DistanceBetween(locs.get(x), target);
				 choice = x;
			 }
		 }
		 return locs.get(choice);
	 }
	 /**public ArrayList<Location> EliminateGhost(ArrayList<Location> locs){
		 for (int x = 0; x<locs.size(); x++){
			 if (getGrid().get(locs.get(x)) instanceof Ghost){
				 locs.remove(x);
				 x--;
			 }
		 }
		 return locs;
	 }
	 **/
	 public void setModeColor(){
		 if (vulnerable){
			 if (count%2==0)
				 setColor(Color.BLUE);
			 else
				 setColor(Color.white);
		 }
		 else if (type == 1)
			 setColor(Color.red);
		 else if (type == 2)
			 setColor(Color.pink); 
		 else if (type == 3)
				 setColor(Color.orange); 
		 else if (type == 4)
			 setColor(Color.cyan);
	 }

	 public void ReleaseGhost(){
		 Location loc = new Location(getLocation().getRow() -2, getLocation().getCol());
		 if (PacMan.getCurrentPoints() <= 100){
			 done = false;
		 }
		 if (PacMan.getCurrentPoints() >= 100 && this.getType() == 2 && !done && getGrid().get(loc) == null){
			 moveTo(loc);
			 done = true;
		 }
		 else if (PacMan.getCurrentPoints() >= 300 && this.getType() == 3 && !done && getGrid().get(loc) == null){
			 moveTo(loc);
			 done=true;
		 }
		 else if (PacMan.getCurrentPoints() >= 400 && this.getType() == 4 && !done && getGrid().get(loc) == null){
			 moveTo(loc);
			 done = true;
		 }
	 }
	 public int getType(){
		 return type;
	 }
	 public Actor getCovered(){
		 return coveredActor;
	 }
	 public void setCovered(Actor a){
		 coveredActor = a;
	 }
}

