package info.gridworld.world;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Ghost;
import info.gridworld.actor.PacMan;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * @author Max
 * 
 * The PacWorld contains the grid and the actors. It's a "game" object. Controls
 * the game environment by taking inputs and modifying the actors based on those
 * inputs.
*/

public class PacWorld extends World {
	
    private PacMan pac;
    private Ghost[] ghosts; //0 = cyan, 1 = orange, 2 = pink, 3 = red (alphabetical)
    
    public PacWorld(){   
        super(new BoundedGrid<Actor>(15, 16)); //passes into World the proper grid size
        super.setMessage("Points: " + 0); //sets the message in the text box
        
        //adds a pacman to the grid
        pac = new PacMan();
        
        //initializes ghosts and colors them
        ghosts = new Ghost[4];
    	ghosts[0] = new Ghost(Color.cyan);
    	ghosts[1] = new Ghost(Color.orange);
    	ghosts[2] = new Ghost(Color.pink);
    	ghosts[3] = new Ghost(Color.red);
        
        constructMaze(); //makes walls
        putGhostsInMaze(); //puts ghosts in center
        fillWithPellets(); //fills all empty spaces with pellets
        
        //puts pacman in the grid
        Location pacmans_crib = new Location(11, 5);
        super.getGrid().remove(pacmans_crib); //get out of his house
        pac.putSelfInGrid((Grid<Actor>) super.getGrid(), pacmans_crib); //puts pac man in his house
    }
			    
    //constructs the maze: makes the walls and fills it with pellets
    private void constructMaze(){
    	int[] rows = new int[]{
    			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    			1,1,
    			2,2,2,2,2,2,2,2,2,
    			3,3,3,3,
    			4,4,4,4,4,4,4,4,4,4,
    			5,5,5,5,
    			6,6,6,6,6,6,6,6,6,6,6,6,
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
    			0,2,3,5,6,7,8,9,10,12,13,15,
    			5,10,
    			0,2,3,5,6,7,8,9,10,12,13,15,
    			0,2,13,15,
    			0,2,4,6,7,8,9,11,13,15,
    			0,4,7,8,11,15,
    			0,2,3,4,5,10,11,12,13,15,
    			0,7,8,15,
    			0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    	
    	for (int z = 0; z < 121; z++){
   		Rock w = new Rock();
    		w.putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(rows[z], cols[z]));
    	}
    	
    	//sets the colors of the box
    	((Actor) super.getGrid().get(new Location(6, 5))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(6, 6))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(6, 7))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(6, 8))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(6, 9))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(6, 10))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(7, 5))).setColor(Color.white);
    	((Actor) super.getGrid().get(new Location(7, 10))).setColor(Color.white);
    	((Actor) super.getGrid().get(new Location(8, 5))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(8, 6))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(8, 7))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(8, 8))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(8, 9))).setColor(new Color(0,0,200));
    	((Actor) super.getGrid().get(new Location(8, 10))).setColor(new Color(0,0,200));

    	//TODO delete the following
    	/**
    	for (int x = 0; x < 16; x++){
    		w.putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(0, x));
    		w = new MazeWall();
    	}
    	
    	w.putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(1, 0));
    	w = new MazeWall();
    	w.putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(1, 15));
    	
    	for (int x = 0; x < 9; x += 2){
    		w.putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(0, x));
    		w = new MazeWall();
    	}
    	
    	for (int x = 7; x < 16; x += 2){
    		w.putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(0, x));
    		w = new MazeWall();
    	}
    	*/
    }
    
    //puts four ghosts in their start positions in the center rectangle
    private void putGhostsInMaze(){
    	ghosts[0].putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(7, 6));
    	ghosts[1].putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(7, 7));
    	ghosts[2].putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(7, 8));
    	ghosts[3].putSelfInGrid((Grid<Actor>) super.getGrid(), new Location(7, 9));
    }
    
    //fills all empty spaces that aren't teleport spaces with pellets. also puts powerpellets in correct positions
    private void fillWithPellets(){
    	for (int x = 0; x < 15; x++){
    		for (int y = 0; y < 16; y++){
    			Location current = new Location(x, y);
    			if (super.getGrid().get(current) == null){ //if the space is empty
    				Flower pellet;
    				if (x == 2 && y == 1 || x == 2 && y == 14 || x == 11 && y == 1 || x == 11 && y == 14)
    					pellet = new Flower(Color.blue);
    				else
    					pellet = new Flower();
    				pellet.putSelfInGrid((Grid<Actor>) getGrid(), current);
    			}
    		}
    	}
    }

    
    /**
     * @override Returning true tells the GUI to NOT open up tooltips or in any way
     * respond to the location being clicked, which is what we want.
     */
    public boolean locationClicked(Location loc){
        return true;
    }
    
    /**
     * @override These descriptors tell the directional changes. Returns true to
     * indicate that the World has processed the key press.
     */
    public boolean keyPressed(String description, Location loc){
        if (description.equals("UP"))
        	pac.setDirection(Location.WEST);
        if (description.equals("RIGHT"))
        	pac.setDirection(Location.NORTH);
        if (description.equals("DOWN"))
        	pac.setDirection(Location.EAST);
        if (description.equals("LEFT"))
        	pac.setDirection(Location.SOUTH);  
        return true;
    }
}