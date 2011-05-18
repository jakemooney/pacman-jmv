package info.gridworld.world;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Ghost;
import info.gridworld.actor.MazeWall;
import info.gridworld.actor.PacMan;
import info.gridworld.actor.Pellet;
import info.gridworld.actor.PowerPellet1;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;

import Levels.Level;

/**
 * @author Max
 * 
 * The PacWorld contains the Level. The difference between the Level and the PacWorld is that the PacWorld deals with GUI
 * while the level simply contains the actors and the grid. To access pacman and the ghosts, I've added getters and setters
 * to the Level.
*/

public class PacWorld extends ActorWorld {
	
    private PacMan pac;    
    private Level level;
    
    /**
     * This is a class non-variable that returns a finite level, level1.
     * @return the level itself, which contains the walls, pacdots, ghosts, and pacman
     */
    public static final Level level1(){
		int[] rows = new int[]{
    			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    			1,1,
    			2,2,2,2,2,2,2,2,2,
    			3,3,3,3,
    			4,4,4,4,4,4,4,4,4,4,
    			5,5,5,5,
    			6,6,6,6,6,6,6,6,6,6,
    			7,7,
    			8,8,8,8,8,8,8,8,8,8,8,8,
    			9,9,9,9,
    			10,10,10,10,10,10,10,10,10,10,
    			11,11,11,11,11,11,
    			12,12,12,12,12,12,12,12,12,12,
    			13,13,13,13,
    			14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14};
		int[] cols = new int[]{
    			0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
    			0,15,
    			0,2,4,6,7,8,9,11,15,
    			0,4,11,15,
    			0,2,4,6,7,8,9,11,13,15,
    			0,2,13,15,
    			0,2,3,4,6,9,11,12,13,15,
    			6,9,
    			0,2,3,4,6,7,8,9,11,12,13,15,
    			0,2,13,15,
    			0,2,4,6,7,8,9,11,13,15,
    			0,4,7,8,11,15,
    			0,2,3,4,5,10,11,12,13,15,
    			0,7,8,15,
    			0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
    	};
		
		Location[] powerPelletLocs = new Location[]{
				new Location(2, 1), 
				new Location(2, 14), 
				new Location(11, 1), 
				new Location(11, 14)
		};
		
		Ghost[] ghosts = new Ghost[]{
				new Ghost(Color.cyan), 
				new Ghost(Color.green),
		};
		
		Location[] ghostLocs = new Location[]{
				new Location(7, 7),
				new Location(7, 8)
		};
		
		PacMan pac = new PacMan();
		
		Location pacLocation = new Location(11, 5);
		
    	Level level1 = new Level(15, 16, rows, cols, powerPelletLocs, ghosts, ghostLocs, pac, pacLocation); //creates a new 15x16 level
    	
    	return level1;
    }
    
    /**
     * This is a class non-variable that returns a finite level, level1.
     * @return the level itself, which contains the walls, pacdots, ghosts, and pacman
     */
    public static final Level level2(){	
    	int[] rows = new int[]{
    			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    			1, 1,
    			2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
    			3, 3, 3, 3, 3, 3, 3, 3, 3,
    			4, 4, 4, 4, 4, 4, 4, 4, 4,
    			5, 5, 5, 5, 5, 5,
    			6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
    			7, 7, 7, 7, 7,
    			8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
    			9, 9, 9, 9, 9, 9, 9, 9, 9, 9,
    			10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
    			11, 11,
    			12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
    			13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
    			
    			14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14,
    			15, 15, 15,
    			16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
    			17, 17, 17, 17,
    			18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18,
    			
    			19, 19, 19, 19, 19,
    			20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20,
    			21, 21,
    			22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22
    	};
    	
    	int[] cols = new int[]{
    			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
    			0, 18,
    			0, 2, 3, 5, 7, 8, 9, 10, 11, 13, 15, 16, 18,
    			0, 2, 3, 5, 9, 13, 15, 16, 18,
    			0, 5, 6, 7, 9, 11, 12, 13, 18,
    			0, 2, 3, 15, 16, 18,
    			0, 2, 3, 5, 7, 8, 9, 10, 11, 13, 15, 16, 18,
    			0, 5, 9, 13, 18,
    			0, 1, 2, 3, 5, 6, 7, 9, 11, 12, 13, 15, 16, 17, 18,
    			0, 1, 2, 3, 5, 13, 15, 16, 17, 18,
    			0, 1, 2, 3, 5, 7, 8, 10, 11, 13, 15, 16, 17, 18,
    			7, 11,
    			0, 1, 2, 3, 5, 7, 8, 9, 10, 11, 13, 15, 16, 17, 18,
    			0, 1, 2, 3, 5, 13, 15, 16, 17, 18,
    			0, 1, 2, 3, 5, 7, 8, 9, 10, 11, 13, 15, 16, 17, 18,
    			0, 9, 18,
    			0, 2, 3, 5, 6, 7, 9, 11, 12, 13, 15, 16, 18,
    			0, 3, 15, 18,
    			0, 1, 3, 5, 7, 8, 9, 10, 11, 13, 15, 17, 18,	
    			0, 5, 9, 13, 18,
    			0, 2, 3, 4, 5, 6, 7, 9, 11, 12, 13, 14, 15, 16, 18,
    			0, 18,
    			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18
    	};
    	
		Location[] powerPelletLocs = new Location[]{
				new Location(3, 1), 
				new Location(3, 17), 
				new Location(17, 1), 
				new Location(17, 17)
		};

		Ghost[] ghosts = new Ghost[]{
				new Ghost(Color.cyan), 
				new Ghost(Color.green),
				new Ghost(Color.pink), 
				new Ghost(Color.red)
		};
		
		Location[] ghostLocs = new Location[]{
				new Location(10, 9),
				new Location(11, 8),
				new Location(11, 9),
				new Location(11, 10),
		};
		
		PacMan pac = new PacMan();
		
		Location pacLocation = new Location(17, 9);
		
    	Level level2 = new Level(23, 19, rows, cols, powerPelletLocs, ghosts, ghostLocs, pac, pacLocation);
    	
    	((Actor) level2.getGrid().get(new Location(9, 0))).setColor(Color.black); //pieces that look like open squares but aren't
    	((Actor) level2.getGrid().get(new Location(9, 1))).setColor(Color.black); //they're above and below the teleport square
    	((Actor) level2.getGrid().get(new Location(9, 2))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(9, 16))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(9, 17))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(9, 18))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(13, 0))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(13, 1))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(13, 2))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(13, 16))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(13, 17))).setColor(Color.black);
    	((Actor) level2.getGrid().get(new Location(13, 18))).setColor(Color.black);

    	return level2;
    }
    
    /**
     * @param level: the level that the pacworld contains and sends messages to
     */
    public PacWorld(Level level){   
        super(level.getGrid()); //passes into World the proper grid
        super.setMessage("Points: " + 0); //sets the message in the text box
        
        //adds a pacman to the grid
        pac = new PacMan();
        
        //puts pacman in the grid
        Location pacmans_crib = new Location(11, 5);
        //level.getGrid().remove(pacmans_crib); //get out of his house
        pac.putSelfInGrid((Grid<Actor>) super.getGrid(), pacmans_crib); //puts pac man in his house
        pac.setDirection(Location.EAST);
    }
    
    /**
     * @override Returning true tells the GUI to NOT open up tooltips or in any way
     * respond to the location being clicked, which is what we want.
     * 
     * I'm keeping it false for the duration of the debugging mode so that we can test stuff.
     */
    public boolean locationClicked(Location loc){
        return false;
    }
    
    /**
     * @override These descriptors tell the directional changes. Returns true to
     * indicate that the World has processed the key press. 
     */
    public boolean keyPressed(String description, Location loc){
        if (description.equals("UP"))
        	pac.setDirection(Location.NORTH);
        if (description.equals("RIGHT"))
        	pac.setDirection(Location.EAST);
        if (description.equals("DOWN"))
        	pac.setDirection(Location.SOUTH);
        if (description.equals("LEFT"))
        	pac.setDirection(Location.WEST);      	
        return true;
    }
}