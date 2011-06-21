package Levels;

import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Ghost;
import info.gridworld.actor.Heart;
import info.gridworld.actor.MazeWall;
import info.gridworld.actor.MsPacMan;
import info.gridworld.actor.PacMan;
import info.gridworld.actor.Pellet;
import info.gridworld.actor.PowerPellet1;
import info.gridworld.actor.PowerPellet2;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Level<T> {
	
	private static Grid g; //the grid
	private static PacMan pac; //the pacman
	private static Ghost[] ghosts; //the ghosts
	
	private Location pacManStart; //the pacman's starting location
	private Location[] ghostsStart; //the ghosts starting locations
	private static MsPacMan mspac; //ms pac man
	
	/**
	 * returns the start location for pacman
	 */
	public Location getPacManStart(){
		return pacManStart;
	}
	
	/**
	 * returns the starting ghost locations
	 */
	public Location[] getGhostsStart(){
		return ghostsStart;
	}
	
	/**
	 * returns the grid of the level
	 */
	public static Grid getGrid(){
		return g;
	}
	
	/**
	 * returns an array of ghosts in the level
	 */
	public static Ghost[] getGhosts(){
		return ghosts;
	}
	
	/**
	 * returns the pacman
	 */
	public static PacMan getPac(){
		return pac;
	}
	
	public static void setPac(PacMan pac){
		Level.pac = pac;
	}
	
	/**
	 * @return if the level has been won yet
	 * 
	 * by won we mean if there are pellets left on the level
	 */
	public static boolean won(){
		for (int x = 1; x < g.getNumRows(); x++){
			for (int y = 0; y < g.getNumCols(); y++)
				if (g.get(new Location(x, y)) instanceof Pellet)
					return false;
		}		
		for (Ghost g : getGhosts()){
			if (g.getCovered() instanceof Pellet){
				return false;
			}
		}
		return true;
	}
	
	//above this line are all methods that need to be accessed by other objects
	//-----------------------------------------
	//below this line are all the methods that are simply used to MAKE the level
	
	/**
	 * @param x: the x value for the grid size
	 * @param y: the y value
	 * @param walls_x: array of x values for the wall locations
	 * @param walls_y: array of y values for the wall locations
	 * @param powerPelletLocs: locations of power pellets
	 */
	public Level(int x, int y, int[] walls_x, int[] walls_y, ArrayList<Location> powerPelletLocs, Ghost[] ghosts, Location[] ghostLocs, PacMan pac, Location pacLocation){
		this.pac = pac;
		this.ghosts = ghosts;
		pacManStart = pacLocation;
		ghostsStart = ghostLocs;
		
		g = new BoundedGrid(x, y);
		placeWalls(walls_x, walls_y);
		placeGhosts(ghosts, ghostLocs);
		placePacMan(pac, pacLocation);
		placePellets(powerPelletLocs);
		
		mspac = null;
	}	
		
			/**
			 * places the walls in the grid.
			 * @param x refers to the x coordinates of all the walls' locations
			 * @param y refers to the y coordinates
			 */
			private void placeWalls(int[] x, int[] y){
				for (int z = 0; z < x.length; z++){
			   		MazeWall wall = new MazeWall(Color.blue);
			    	wall.putSelfInGrid((Grid<Actor>) g, new Location(x[z], y[z]));
			    }
			}
						
			/**
			 * places the ghosts in the at ghost locs.
			 * if they're already contained within a grid, moves them there instead
			 * 
			 * @param ghosts: the ghosts to be put in the center
			 * @param ghostLocs: the location of each ghost
			 */
			public void placeGhosts(Ghost[] ghosts, Location[] ghostLocs){
				
				for (Ghost g : this.ghosts){
					if (g.getGrid() != null)
						g.removeSelfFromGrid();
				}
				
				this.ghosts = ghosts;
				
				if (this.ghosts.length != ghostLocs.length)
					throw new IllegalStateException("Sizes of ghosts and ghostLocs differ.");
				for (int n = 0; n < this.ghosts.length; n++){
					if (this.ghosts[n].getGrid() == null)
						this.ghosts[n].putSelfInGrid((Grid<Actor>) g, ghostLocs[n]);
					else
						this.ghosts[n].moveTo(ghostLocs[n]);
				}
			}
			
			/**
			 * places pacman
			 */
			public void placePacMan(PacMan pac, Location pacLocation){
				if (pac.getGrid() == null){
					pac.putSelfInGrid((Grid<Actor>) g, pacLocation);
					pac.setDirection(Location.EAST);
				}
				else
					pac.moveTo(pacLocation);
			}
			
			
			/**
			 * places the pellets in the grid by filling it with pellets and then removing
			 * the pellets around the jail.
			 */
			private void placePellets(ArrayList<Location> powerPelletLocs){
				fillWithPellets(powerPelletLocs);
				removePelletsAroundJail();
			}
			
			/**
			 * fills all spaces with pellets
			 */
			private void fillWithPellets(ArrayList<Location> powerPelletLocs){
		    	for (int x = 0; x < g.getNumRows(); x++){
		    		for (int y = 0; y < g.getNumCols(); y++){
		    			Location current = new Location(x, y);
		    			Pellet pellet;
		    			if (g.get(current) == null){ //if the space is empty
		    				if (powerPelletLocs.contains(current)) //if it's a powerpellet location
		    					pellet = new PowerPellet1();
		    				else
		    					pellet = new Pellet();
		    				pellet.putSelfInGrid((Grid<Actor>) g, current);
		    			}
		    		}
		    	}
		    }
			
			/**
			 * removes the pellets around the jail
			 */
			private void removePelletsAroundJail(){
				Location center = new Location(g.getNumRows() / 2, g.getNumCols() / 2);
				int x = center.getRow() + 2;
				int y = center.getCol();
				Location current = new Location(x, y);
				
				//dir is like the direction towards the center
				for (int dir = 0; dir < 405; dir += 45){
					while (g.get(current.getAdjacentLocation(dir)) instanceof MazeWall ){
							//|| current.getAdjacentLocation(dir).equals(center.getAdjacentLocation(0))
							//|| (current.getAdjacentLocation(dir).equals(center.getAdjacentLocation(315))
								//	&& g.getNumCols() % 2 == 0)){
						if (g.get(current) instanceof Pellet 
								|| g.get(current) instanceof PowerPellet1
								|| g.get(current) instanceof PowerPellet2){
							g.remove(current);
						}
						if (dir % 45 == 0 && dir % 90 != 0 && dir != 0){
							current = current.getAdjacentLocation(dir + 315);
							break;
						}
						else
							current = current.getAdjacentLocation(dir + 270);
					}
				}
			}
			
			//--------------------------------------------------------------------------
			//goodies!

			public static void placeMsPacMan(MsPacMan ms, Location location) {
				mspac = ms;
				if (mspac.getGrid() == null){
					mspac.putSelfInGrid((Grid<Actor>) g, location);
				}
				else
					mspac.moveTo(location);
			}

			//returns mspac
			public static MsPacMan getMsPac() {
				return mspac;
			}
			
			//fills the map with hearts
			public void fillWithHearts() {
				for (int x = 0; x < g.getNumRows(); x++){
		    		for (int y = 0; y < g.getNumCols(); y++){
		    			Location current = new Location(x, y);
		    			Heart heart = new Heart();
		    			if (!(g.get(current) instanceof PacMan || g.get(current) instanceof MsPacMan))
		    				heart.putSelfInGrid((Grid<Actor>) g, current);
		    		}
		    	}
			}
}