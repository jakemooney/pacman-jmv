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
import info.gridworld.gui.WorldFrame;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import Levels.Level;

/**
 * @author Max
 * 
 * The PacWorld contains the Level. The difference between the Level and the PacWorld is that the PacWorld deals with GUI
 * while the level simply contains the actors and the grid. To access pacman and the ghosts, I've added getters and setters
 * to the Level.
*/

public class PacWorld extends ActorWorld {
	
    private static Level level;
    
    private static final int level1x = 15; //these are the grid sizes for each level
    private static final int level1y = 16;
    private static final int level2x = 23;
    private static final int level2y = 19;
    
    private static Timer timer;
    
    /**
     * This is a class non-variable that returns a finite level, level1.
     * @return the level itself, which contains the walls, pacdots, ghosts, and pacman
     * 
     * note that the pacman and ghosts' levels have not been set yet because it's impossible
     * to do so in this method. I set their levels in the PacWorld constructor.
     */
    public static final Level level1(){
    	int[] rows = new int[]{
    			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    			1,1,
    			2,2,2,2,2,2,2,2,2,2,
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
    			14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14
    	};
		int[] cols = new int[]{
    			0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
    			0,15,
    			0,2,4,6,7,8,9,11,13,15,
    			0,4,11,15,
    			0,2,4,6,7,8,9,11,13,15,
    			0,2,13,15,
    			0,2,3,4,6,7,8,9,11,12,13,15,
    			6,9,
    			0,2,3,4,6,7,8,9,11,12,13,15,
    			0,2,13,15,
    			0,2,4,6,7,8,9,11,13,15,
    			0,4,7,8,11,15,
    			0,2,3,4,5,10,11,12,13,15,
    			0,7,8,15,
    			0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
    	};
		
		ArrayList<Location> powerPelletLocs = new ArrayList();
		powerPelletLocs.add(new Location(2, 1)); 
		powerPelletLocs.add(new Location(2, 14)); 
		powerPelletLocs.add(new Location(11, 1)); 
		powerPelletLocs.add(new Location(11, 14)); 
		
		Ghost[] ghosts = new Ghost[]{
				new Ghost(Color.red, 1), 
				new Ghost(Color.pink, 2),
		};
		
		Location[] ghostLocs = new Location[]{
				new Location(5, 7),
				new Location(7, 8)
		};
		
		PacMan pac = new PacMan();
		
		Location pacLocation = new Location(11, 5);
		
    	Level level1 = new Level(level1x, level1y, rows, cols, powerPelletLocs, ghosts, ghostLocs, pac, pacLocation); //creates a new 15x16 level
    	
    	((Actor) level1.getGrid().get(new Location(6, 7))).setColor(Color.black);
    	((Actor) level1.getGrid().get(new Location(6, 8))).setColor(Color.black);
    	
    	((Actor) level1.getGrid().get(new Location(7, 7))).removeSelfFromGrid();
    	level1.decrementPelletCount();
    	
    	return level1;
    }
    
    /**
     * This is a class non-variable that returns a finite level, level1.
     * @return the level itself, which contains the walls, pacdots, ghosts, and pacman
     * note that the pacman and ghosts' levels have not been set yet because it's impossible
     * to do so in this method. I set their levels in the PacWorld constructor.
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
    			10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
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
    			0, 1, 2, 3, 5, 7, 8, 9, 10, 11, 13, 15, 16, 17, 18,
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
    	
    	ArrayList<Location> powerPelletLocs = new ArrayList();
		powerPelletLocs.add(new Location(3, 1)); 
		powerPelletLocs.add(new Location(3, 17)); 
		powerPelletLocs.add(new Location(17, 1)); 
		powerPelletLocs.add(new Location(17, 17)); 

		Ghost[] ghosts = new Ghost[]{
				new Ghost(Color.red,1),
				new Ghost(Color.pink,2), 
				new Ghost(Color.orange,3),
				new Ghost(Color.cyan,4) 						
		};
		
		Location[] ghostLocs = new Location[]{
				new Location(9, 9),
				new Location(11, 10),
				new Location(11, 8),
				new Location(11, 9),
		};
		
		PacMan pac = new PacMan();
		
		Location pacLocation = new Location(17, 9);
		
    	Level level2 = new Level(level2x, level2y, rows, cols, powerPelletLocs, ghosts, ghostLocs, pac, pacLocation);
    	
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
    	
    	((Actor) level2.getGrid().get(new Location(10, 9))).setColor(Color.black);
    	
    	return level2;
    }
    
    /**
     * @param level: the level that the pacworld contains and sends messages to
     */
    public PacWorld(Level level){   
        super(level.getGrid()); //passes into World the proper grid
        super.setMessage("Points: " + PacMan.getPoints() + "\nLives: " + PacMan.getLives()); //sets the message in the text box
        
        this.level = level; //sets the instance level to the parameter level
        
        level.getPac().setLevel(level); //sets the pacman's level
        for (Ghost ghost : level.getGhosts()) //sets the ghosts' levels
        	ghost.setLevel(level);
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
        	level.getPac().setPendingDirection(Location.NORTH);
        if (description.equals("RIGHT"))
        	level.getPac().setPendingDirection(Location.EAST);
        if (description.equals("DOWN"))
        	level.getPac().setPendingDirection(Location.SOUTH);
        if (description.equals("LEFT"))
        	level.getPac().setPendingDirection(Location.WEST); 
        return true;
    }
    
    /**
     * @override Overridden to set the appropriate size of the window for each level
     */
    public void show()
    { 
		int x, y;
    	
    	//tests if the grid is the same size as the grid for level1
    	if (level.getGrid().getNumRows() == level1x && level.getGrid().getNumCols() == level1y){
        	x = 449; //this is the frame size for level1 that looks best
        	y = 545;
        }
        else{
        	x = 528; //this is the frame size for level2 that looks best
        	y = 745;
        }
    	WorldFrame w = new WorldFrame(this);
    	w.setSize(x, y);
    	setFrame(w);
        getFrame().setVisible(true);          
    }
    
    /**
     * @override to update the points and lives text with every step
     */
    public void step()
    {
    	super.step();
        super.setMessage("Points: " + PacMan.getPoints() + "\nLives: " + PacMan.getLives()); //sets the message in the text box
        super.repaint();
    }
    
    /**
     * restarts the level (when pacman dies and still has a life or two)
     */
    public static void restart(){
    	
		try {
			JOptionPane.showMessageDialog(getFrame(), new JLabel("You have lost a life! You now have " + (PacMan.getLives()) + " left! Press the button below to continue."), "You have died!", 2, new ImageIcon(new URL("http://www.androidrundown.com/images/amarket/namco/pacman/ce/icon.png")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	level.placePacMan(level.getPac(), level.getPacManStart());
    	level.placeGhosts(level.getGhosts(), level.getGhostsStart());
    }
    
    /**
     * ends the game
     */
    public static void gameOver(){
    	try {
			JOptionPane.showMessageDialog(getFrame(), new JLabel("You have " + (PacMan.getLives()) + " lives left! You lose! Your Score: " + PacMan.getPoints()), " Game Over!", 2, new ImageIcon(new URL("http://www.androidrundown.com/images/amarket/namco/pacman/ce/icon.png")));
			level = level2();
			gameOver = true;
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private static boolean gameOver = false;
    public  static boolean isGameOver(){
    	return gameOver;
    }
    
}