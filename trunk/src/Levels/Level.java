package Levels;

import java.awt.Color;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Ghost;
import info.gridworld.actor.MazeWall;
import info.gridworld.actor.PacMan;
import info.gridworld.actor.Pellet;
import info.gridworld.actor.PowerPellet1;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Level<T> {
	
	private Grid<T> g;
	private PacMan pac;
	private Ghost[] ghosts;
	
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
	 * @param x: the x value for the grid size
	 * @param y: the y value
	 * @param walls_x: array of x values for the wall locations
	 * @param walls_y: array of y values for the wall locations
	 * @param powerPelletLocs: locations of power pellets
	 */
	public Level(int x, int y, int[] walls_x, int[] walls_y, Location[] powerPelletLocs, Ghost[] ghosts, Location[] ghostLocs, PacMan pac, Location pacLocation){
		g = new BoundedGrid(x, y);
		placeWalls(walls_x, walls_y);
		placePellets(powerPelletLocs);
		placeGhosts(ghosts, ghostLocs);
		this.pac = pac;
		pac.putSelfInGrid((Grid<Actor>) g, pacLocation);
		pac.setDirection(Location.EAST);
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
			 * places the pellets in the grid by filling it with pellets and then removing
			 * the pellets around the jail.
			 */
			private void placePellets(Location[] powerPelletLocs){
				fillWithPellets();
				removePelletsInFrontOfJail();
				placePowerPellets(powerPelletLocs);
			}
			
			/**
			 * fills all spaces except teleport spaces with pellets
			 */
			private void fillWithPellets(){
		    	for (int x = 0; x < g.getNumRows(); x++){
		    		for (int y = 0; y < g.getNumCols(); y++){
		    			Location current = new Location(x, y);
		    			if (g.get(current) == null){ //if the space is empty
		    				Pellet pellet = new Pellet();
		    				pellet.putSelfInGrid((Grid<Actor>) g, current);
		    			}
		    		}
		    	}
		    }
			
			/**
			 * removes the pellets in the area in front of the jail.
			 */
			private void removePelletsInFrontOfJail(){
				Location center = new Location(g.getNumRows() / 2, g.getNumCols() / 2);
				int x = center.getRow() - 2;
				int y = center.getCol();
				Location current = new Location(x, y);
				
				while (g.get(current.getAdjacentLocation(Location.SOUTH)) instanceof MazeWall == true){
					g.remove(current);
					current = current.getAdjacentLocation(Location.WEST);
				}
				current = new Location(x, y);
				while (g.get(current.getAdjacentLocation(180)) instanceof MazeWall == true){
					g.remove(current);
					current = current.getAdjacentLocation(Location.EAST);
				}
			}
			
			/**
			 * places the power pellets at the specified locations
			 */
			private void placePowerPellets(Location[] powerPelletLocs){
				for (int x = 0; x < powerPelletLocs.length; x++){
					Pellet p = new PowerPellet1();
					p.putSelfInGrid((Grid<Actor>) g, powerPelletLocs[x]);
				}
			}
			
			
			/**
			 * places the ghosts in the center.
			 * @param ghosts: the ghosts to be put in the center
			 * @param ghostLocs: the locations of each ghost
			 */
			private void placeGhosts(Ghost[] ghosts, Location[] ghostLocs){
				if (ghosts.length != ghostLocs.length)
					throw new IllegalStateException("Sizes of ghosts and ghostLocs differ.");
				for (int n = 0; n < ghosts.length; n++)
					ghosts[n].putSelfInGrid((Grid<Actor>) g, ghostLocs[n]);
			}
}