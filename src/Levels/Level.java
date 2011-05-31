package Levels;

import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Ghost;
import info.gridworld.actor.MazeWall;
import info.gridworld.actor.PacMan;
import info.gridworld.actor.Pellet;
import info.gridworld.actor.PowerPellet1;
import info.gridworld.actor.PowerPellet2;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Level<T> {
	
	private Grid<T> g;
	private PacMan pac;
	private Ghost[] ghosts;
	private int pelletCount;
	private static boolean won; //if the level has been won or not
	
	private Location pacManStart;
	private Location[] ghostsStart;
	
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
	public Grid<T> getGrid(){
		return g;
	}
	
	/**
	 * returns an array of ghosts in the level
	 */
	public Ghost[] getGhosts(){
		return ghosts;
	}
	
	/**
	 * returns the pacman
	 */
	public PacMan getPac(){
		return pac;
	}
	
	/**
	 * decrements the amount of pellets (to be used by pacman)
	 * also tells the game that the level has been one once pelletCount reaches zero
	 */
	public void decrementPelletCount(){
		pelletCount--;
		if (pelletCount == 0)
			won = true;
	}
	
	/**
	 * gets the pellet count
	 */
	public int getPelletCount(){
		return pelletCount;
	}
	
	/**
	 * @return if the level has been won yet
	 */
	public static boolean won(){
		return won;
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
		pelletCount = 0;
		won = false;
		pacManStart = pacLocation;
		ghostsStart = ghostLocs;
		
		g = new BoundedGrid(x, y);
		placeWalls(walls_x, walls_y);
		placeGhosts(ghosts, ghostLocs);
		placePacMan(pac, pacLocation);
		placePellets(powerPelletLocs);
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
				if (ghosts.length != ghostLocs.length)
					throw new IllegalStateException("Sizes of ghosts and ghostLocs differ.");
				for (int n = 0; n < ghosts.length; n++){
					if (ghosts[n].getGrid() == null)
						ghosts[n].putSelfInGrid((Grid<Actor>) g, ghostLocs[n]);
					else
						ghosts[n].moveTo(ghostLocs[n]);
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
			 * fills all spaces except teleport spaces with pellets
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
		    				pelletCount++;
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
							decrementPelletCount();
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
}