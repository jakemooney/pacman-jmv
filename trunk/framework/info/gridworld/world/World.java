/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Cay Horstmann
 */

package info.gridworld.world;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Ghost;
import info.gridworld.actor.PacMan;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.gui.WorldFrame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;

/**
 * 
 * A <code>World</code> is the mediator between a grid and the GridWorld GUI.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class World<T>
{
    private Grid<T> gr;
    private Set<String> occupantClassNames;
    private Set<String> gridClassNames;
    private String message;
    private JFrame frame;

    private static Random generator = new Random();

    private static final int DEFAULT_ROWS = 16;
    private static final int DEFAULT_COLS = 15;
    
    //----------------
    //additions//
    private PacMan pac;
    private Ghost[] ghosts; //0 = cyan, 1 = orange, 2 = pink, 3 = red (alphabetical)
    
    public World()
    {   
        this(new BoundedGrid<T>(DEFAULT_ROWS, DEFAULT_COLS));
        message = "Points: 0";
        
        //additions
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
        gr.remove(pacmans_crib); //get out of his house
        pac.putSelfInGrid((Grid<Actor>) gr, pacmans_crib); //puts pac man in his house
    }
			    
			    //constructs the maze: makes the walls and fills it with pellets
			    private void constructMaze(){
			    	int[] rows = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,4,4,4,4,4,4,4,4,4,4,5,5,5,5,6,6,6,6,6,6,6,6,6,6,6,6,7,7,8,8,8,8,8,8,8,8,8,8,8,8,9,9,9,9,10,10,10,10,10,10,10,10,10,10,11,11,11,11,11,11,12,12,12,12,12,12,12,12,12,12,13,13,13,13,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14};
			    	int[] columns = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0,15,0,2,4,6,7,8,9,11,15,0,4,11,15,0,2,4,6,7,8,9,11,13,15,0,2,13,15,0,2,3,5,6,7,8,9,10,12,13,15,5,10,0,2,3,5,6,7,8,9,10,12,13,15,0,2,13,15,0,2,4,6,7,8,9,11,13,15,0,4,7,8,11,15,0,2,3,4,5,10,11,12,13,15,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
			    	
			    	for (int z = 0; z < 122; z++){
			    		MazeWall w = new Mazewall();
			    		w.putSelfInGrid((Grid<Actor>) gr, new Location(z, z));
			    	}
			    	
			    	//sets the colors of the box
			    	((Actor) gr.get(new Location(6, 5))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(6, 6))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(6, 7))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(6, 8))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(6, 9))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(6, 10))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(7, 5))).setColor(Color.white);
			    	((Actor) gr.get(new Location(7, 10))).setColor(Color.white);
			    	((Actor) gr.get(new Location(8, 5))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(8, 6))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(8, 7))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(8, 8))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(8, 9))).setColor(new Color(0,0,200));
			    	((Actor) gr.get(new Location(8, 10))).setColor(new Color(0,0,200));
			
			    	
			    	
			    	
			    	
			    	
			    	/**
			    	for (int x = 0; x < 16; x++){
			    		w.putSelfInGrid((Grid<Actor>) gr, new Location(0, x));
			    		w = new MazeWall();
			    	}
			    	
			    	w.putSelfInGrid((Grid<Actor>) gr, new Location(1, 0));
			    	w = new MazeWall();
			    	w.putSelfInGrid((Grid<Actor>) gr, new Location(1, 15));
			    	
			    	for (int x = 0; x < 9; x += 2){
			    		w.putSelfInGrid((Grid<Actor>) gr, new Location(0, x));
			    		w = new MazeWall();
			    	}
			    	
			    	for (int x = 7; x < 16; x += 2){
			    		w.putSelfInGrid((Grid<Actor>) gr, new Location(0, x));
			    		w = new MazeWall();
			    	}
			    	*/
			    }
			    
			    //puts four ghosts in their start positions in the center rectangle
			    private void putGhostsInMaze(){
			    	ghosts[0].putSelfInGrid((Grid<Actor> gr, new Location(7, 6));
			    	ghosts[1].putSelfInGrid((Grid<Actor> gr, new Location(7, 7));
			    	ghosts[2].putSelfInGrid((Grid<Actor> gr, new Location(7, 8));
			    	ghosts[3].putSelfInGrid((Grid<Actor> gr, new Location(7, 9));
			    }
			    
			    //fills all empty spaces that aren't teleport spaces with pellets. also puts powerpellets in correct positions
			    private void fillWithPellets(){
			    	for (int x = 0; x < 15; x++){
			    		for (int y = 0; y < 16; y++){
			    			Location current = new Location(x, y);
			    			if (gr.get(current)) == null){ //if the space is empty
			    				Pellet pellet;
			    				if (x == 2 && y == 1 || x == 2 && y == 14 || x == 11 && y = 1 || x == 11 && y == 14)
			    					pellet = new PowerPellet();
			    				else
			    					pellet = new Pellet();
			    				pellet.putSelfInGrid(current);
			    			}
			    		}
			    	}
			    }

    public World(Grid<T> g)
    {
        gr = g;
        gridClassNames = new TreeSet<String>();
        occupantClassNames = new TreeSet<String>();
        addGridClass("info.gridworld.grid.BoundedGrid");
        addGridClass("info.gridworld.grid.UnboundedGrid");
    }

    /**
     * Constructs and shows a frame for this world.
     */
    public void show()
    {
        if (frame == null)
        {
            frame = new WorldFrame<T>(this);
            frame.setVisible(true);
        }
        else
            frame.repaint();
    }

    /**
     * Gets the grid managed by this world.
     * @return the grid
     */
    public Grid<T> getGrid()
    {
        return gr;
    }

    /**
     * Sets the grid managed by this world.
     * @param newGrid the new grid
     */
    public void setGrid(Grid<T> newGrid)
    {
        gr = newGrid;
        repaint();
    }

    /**
     * Sets the message to be displayed in the world frame above the grid.
     * @param newMessage the new message
     */
    public void setMessage(String newMessage)
    {
        message = newMessage;
        repaint();
    }

    /**
     * Gets the message to be displayed in the world frame above the grid.
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * This method is called when the user clicks on the step button, or when
     * run mode has been activated by clicking the run button.
     */
    public void step()
    {
        repaint();
    }

    /**
     * This method is called when the user clicks on a location in the
     * WorldFrame.
     * 
     * @param loc the grid location that the user selected
     * @return true if the world consumes the click, or false if the GUI should
     * invoke the Location->Edit menu action
     */
    public boolean locationClicked(Location loc)
    {
        return true;
    }
    
    /**
     * This method is called when a key was pressed. Override it if your world wants
     * to consume some keys (e.g. "1"-"9" for Sudoku). Don't consume plain arrow keys,
     * or the user loses the ability to move the selection square with the keyboard.   
     * @param description the string describing the key, in 
     * <a href="http://java.sun.com/javase/6/docs/api/javax/swing/KeyStroke.html#getKeyStroke(java.lang.String)">this format</a>. 
     * @param loc the selected location in the grid at the time the key was pressed
     * @return true if the world consumes the key press, false if the GUI should
     * consume it.
     */
    public boolean keyPressed(String description, Location loc)
    {
        if (description.equals("UP"))
        	pac.setDirection(0);
        if (description.equals("RIGHT"))
        	pac.setDirection(90);
        if (description.equals("DOWN"))
        	pac.setDirection(180);
        if (description.equals("LEFT"))
        	pac.setDirection(270);
        
        return true;
    }

    /**
     * Gets a random empty location in this world.
     * @return a random empty location
     */
    public Location getRandomEmptyLocation()
    {
        Grid<T> gr = getGrid();
        int rows = gr.getNumRows();
        int cols = gr.getNumCols();

        if (rows > 0 && cols > 0) // bounded grid
        {
            // get all valid empty locations and pick one at random
            ArrayList<Location> emptyLocs = new ArrayList<Location>();
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                {
                    Location loc = new Location(i, j);
                    if (gr.isValid(loc) && gr.get(loc) == null)
                        emptyLocs.add(loc);
                }
            if (emptyLocs.size() == 0)
                return null;
            int r = generator.nextInt(emptyLocs.size());
            return emptyLocs.get(r);
        }
        else
        // unbounded grid
        {
            while (true)
            {
                // keep generating a random location until an empty one is found
                int r;
                if (rows < 0)
                    r = (int) (DEFAULT_ROWS * generator.nextGaussian());
                else
                    r = generator.nextInt(rows);
                int c;
                if (cols < 0)
                    c = (int) (DEFAULT_COLS * generator.nextGaussian());
                else
                    c = generator.nextInt(cols);
                Location loc = new Location(r, c);
                if (gr.isValid(loc) && gr.get(loc) == null)
                    return loc;
            }
        }
    }

    /**
     * Adds an occupant at a given location.
     * @param loc the location
     * @param occupant the occupant to add
     */
    public void add(Location loc, T occupant)
    {
        getGrid().put(loc, occupant);
        repaint();
    }

    /**
     * Removes an occupant from a given location.
     * @param loc the location
     * @return the removed occupant, or null if the location was empty
     */
    public T remove(Location loc)
    {
        T r = getGrid().remove(loc);
        repaint();
        return r;
    }

    /**
     * Adds a class to be shown in the "Set grid" menu.
     * @param className the name of the grid class
     */
    public void addGridClass(String className)
    {
        gridClassNames.add(className);
    }

    /**
     * Adds a class to be shown when clicking on an empty location.
     * @param className the name of the occupant class
     */
    public void addOccupantClass(String className)
    {
        occupantClassNames.add(className);
    }

    /**
     * Gets a set of grid classes that should be used by the world frame for
     * this world.
     * @return the set of grid class names
     */
    public Set<String> getGridClasses()
    {
        return gridClassNames;
    }

    /**
     * Gets a set of occupant classes that should be used by the world frame for
     * this world.
     * @return the set of occupant class names
     */
    public Set<String> getOccupantClasses()
    {
        return occupantClassNames;
    }

    private void repaint()
    {
        if (frame != null)
            frame.repaint();
    }

    /**
     * Returns a string that shows the positions of the grid occupants.
     */
    public String toString()
    {
        String s = "";
        Grid<?> gr = getGrid();

        int rmin = 0;
        int rmax = gr.getNumRows() - 1;
        int cmin = 0;
        int cmax = gr.getNumCols() - 1;
        if (rmax < 0 || cmax < 0) // unbounded grid
        {
            for (Location loc : gr.getOccupiedLocations())
            {
                int r = loc.getRow();
                int c = loc.getCol();
                if (r < rmin)
                    rmin = r;
                if (r > rmax)
                    rmax = r;
                if (c < cmin)
                    cmin = c;
                if (c > cmax)
                    cmax = c;
            }
        }

        for (int i = rmin; i <= rmax; i++)
        {
            for (int j = cmin; j < cmax; j++)
            {
                Object obj = gr.get(new Location(i, j));
                if (obj == null)
                    s += " ";
                else
                    s += obj.toString().substring(0, 1);
            }
            s += "\n";
        }
        return s;
    }
}