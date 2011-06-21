package info.gridworld.world;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.AePlayWave;
import info.gridworld.actor.Ghost;
import info.gridworld.actor.MsPacMan;
import info.gridworld.actor.PacMan;
import info.gridworld.grid.Location;
import info.gridworld.gui.GUIController;
import info.gridworld.gui.WorldFrame;

import java.awt.Color;
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
    
    public static Level getLevel(){
    	return level;
    }
    
    /**
     * @return level, if reached end of game then return null
     */
    public static Level nextLevel(){
    	if (level.getGrid().getNumRows() == level1x && level.getGrid().getNumCols() == level1y)
    		return level2();
    	else if (level.getGrid().getNumRows() == level2x && level.getGrid().getNumCols() == level2y)
    		return level3();
    	else 
    		return null;
    }
    
    private static final int level1x = 15; //these are the grid sizes for each level
    private static final int level1y = 16;
        
    public static final Location[] getGhostLocs1(){
    	return new Location[]{
    			new Location(5, 7),
    			new Location(7, 8)
    	    };
    }
    //-------------------------------------------------------------------------------
    private static final int level2x = 23;
    private static final int level2y = 19;
    
    public static final Location[] getGhostLocs2(){
    	return new Location[]{
    			new Location(9, 9),
    			new Location(11, 10),
    			new Location(11, 8),
    			new Location(11, 9),
    	};
    }
    
    //-------------------------------------------------------------------
    private static final int level3x = 15;
    private static final int level3y = 33;
    
    public static final Location[] getGhostLocs3(){
    	return new Location[]{
    	    	new Location(5, 16),
    			new Location(7, 17),
    			new Location(7, 16),
    			new Location(7, 15),
    	};
    }
    
    public static final Location msPacLocation(){
    	return new Location(1, 16);
    }
    
    //-----------------------------------------------------------------------------
    
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
    			11,11,11,11,
    			12,12,12,12,12,12,12,12,12,12,12,12,
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
    			0,4,11,15,
    			0,2,3,4,5,7,8,10,11,12,13,15,
    			0,7,8,15,
    			0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
    	};
		
		ArrayList<Location> powerPelletLocs = new ArrayList();
		powerPelletLocs.add(new Location(2, 1)); 
		powerPelletLocs.add(new Location(2, 14)); 
		powerPelletLocs.add(new Location(11, 1)); 
		powerPelletLocs.add(new Location(11, 14)); 
		
		PacMan pac = new PacMan();
		
		Location pacLocation = new Location(11, 5);
		
		Ghost[] ghosts1 = new Ghost[]{
				new Ghost(Color.red, 1), 
				new Ghost(Color.pink, 2),
		    };
		
    	Level level1 = new Level(level1x, level1y, rows, cols, powerPelletLocs, ghosts1, getGhostLocs1(), pac, pacLocation); //creates a new 15x16 level
    	
    	((Actor) level1.getGrid().get(new Location(6, 7))).setColor(Color.black);
    	((Actor) level1.getGrid().get(new Location(6, 8))).setColor(Color.black);
    	
    	((Actor) level1.getGrid().get(new Location(7, 7))).removeSelfFromGrid();
    	
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
		
		PacMan pac = new PacMan();
		
		Location pacLocation = new Location(17, 9);
		
		Ghost[] ghosts2 = new Ghost[]{
			new Ghost(Color.red,1),
			new Ghost(Color.pink,2), 
			new Ghost(Color.orange,3),
			new Ghost(Color.cyan,4) 						
		};
		
    	Level level2 = new Level(level2x, level2y, rows, cols, powerPelletLocs, ghosts2, getGhostLocs2(), pac, pacLocation);
    	
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
     * This is a class non-variable that returns a finite level, level1.
     * @return the level itself, which contains the walls, pacdots, ghosts, and pacman
     * 
     * note that the pacman and ghosts' levels have not been set yet because it's impossible
     * to do so in this method. I set their levels in the PacWorld constructor.
     */
	 public static final Level level3(){
		 int[] rows = new int[]{
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				1,1,1,1,
				2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,
				3,3,3,3,3,3,
				4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,
				5,5,5,5,
				6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,
				7,7,
				8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,
				9,9,9,9,
				10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
				11,11,11,11,
				12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,
				13,13,13,13,13,13,
				14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14
		};

		int[] cols = new int[]{
				0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32,  //0
				0,13,	19, 32,								//1
				0,2,4,6,7,8,9,11,13,14,15,16,17, 18, 19, 21, 23, 24, 25, 26, 28, 30, 32,			//2
				0,4,11,	21, 28, 32,
							//3
				0,2,4,6,7,8,9,11,12,13,14,15,				17, 18, 19, 20, 21, 23, 24, 25, 26, 28, 30, 32,//4
				0,2,	30, 32,								//5
				0,2,3,4,6,7,8,9,11,12,14,15,16,17, 18, 20, 21, 23, 24, 25, 26, 28, 29, 30, 32,			//6
				14,		18,								//7
				0,2,3,4,6,7,8,9,11,12,14,15,16,17, 18, 20, 21, 23, 24, 25, 26, 28, 29, 30, 32,			//8
				0,2,30, 32,									//9
				0,2,4,6,7,8,9,11,12,13,14,15,17, 18, 19, 20, 21, 23, 24, 25, 26, 28, 30, 32,			//10
				0,4,28, 32,	 								//11
				0,2,3,4,5,7,8,10,11,12,13,15, 16, 17, 19, 20, 21, 22, 24, 25, 27, 28, 29, 30, 32,			//12
				0,7,8,	24, 25, 32,								//13
				0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32//14
		};
				
		int[] cols3 = new int[]{
				17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32,
				19, 32,
				17, 18, 19, 21, 23, 24, 25, 26, 28, 30, 32,
				21, 28, 32,
				17, 18, 19, 20, 21, 23, 24, 25, 26, 28, 30, 32,
				30, 32,
				17, 18, 20, 21, 23, 24, 25, 26, 28, 29, 30, 32,
				18,
				17, 18, 20, 21, 23, 24, 25, 26, 28, 29, 30, 32,
				30, 32,
				17, 18, 19, 20, 21, 23, 24, 25, 26, 28, 30, 32,
				28, 32,
				17, 19, 20, 21, 22, 24, 25, 27, 28, 29, 30, 32,
				24, 25, 32,
				17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32
		};
		
		ArrayList<Location> powerPelletLocs = new ArrayList();
		powerPelletLocs.add(new Location(2, 1)); 
		powerPelletLocs.add(new Location(2, 31)); 
		powerPelletLocs.add(new Location(7, 12)); 
		powerPelletLocs.add(new Location(7, 20)); 
		powerPelletLocs.add(new Location(11, 1)); 
		powerPelletLocs.add(new Location(11, 31)); 
		
		PacMan pac = new PacMan();
		
		Location pacLocation = new Location(13, 16);
		
		Ghost[] ghosts3 = new Ghost[]{
				new Ghost(Color.red,1),
				new Ghost(Color.pink,2), 
				new Ghost(Color.orange,3),
				new Ghost(Color.cyan,4) 						
		};
		
		Level level3 = new Level(level3x, level3y, rows, cols, powerPelletLocs, ghosts3, getGhostLocs3(), pac, pacLocation); //creates a new 15x16 level
		
		MsPacMan mspac = new MsPacMan();
		level3.placeMsPacMan(mspac, new Location(1, 16));
		
		level3.getGrid().remove(new Location(1, 14));
		level3.getGrid().remove(new Location(1, 15));
		level3.getGrid().remove(new Location(1, 17));
		level3.getGrid().remove(new Location(1, 18));
		
		return level3;
	}
	     
	 
	 private static boolean firstTime = true;
	 
    /**
     * @param level: the level that the pacworld contains and sends messages to
     */
    public PacWorld(Level level){  
        super(level.getGrid()); //passes into World the proper grid
        
        refreshMessage();
        this.level = level; //sets the instance level to the parameter level
        
        level.getPac().setLevel(level); //sets the pacman's level
        for (Ghost ghost : level.getGhosts()) //sets the ghosts' levels
        	ghost.setLevel(level);
        
    }
    
    public void refreshMessage(){
        super.setMessage("Points: " + PacMan.getPoints() + "\nLives: " + PacMan.getLives()); //sets the message in the text box
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
    	
    	PacMan.updatePoints();
		int x, y;
    	
    	//tests if the grid is the same size as the grid for level1
    	if (level.getGrid().getNumRows() == level1x && level.getGrid().getNumCols() == level1y){
        	x = 449; //this is the frame size for level1 that looks best
        	y = 525;
        }
        else if (level.getGrid().getNumRows() == level2x && level.getGrid().getNumCols() == level2y){
        	x = 528; //this is the frame size for level2 that looks best
        	y = 725;
        }
        else{
        	x = 878;
        	y = 520;
        }
    	@SuppressWarnings({ "rawtypes", "unchecked" })
		WorldFrame w = new WorldFrame(this);
    	w.setSize(x, y);
    	setFrame(w);
        getFrame().setVisible(true);
        
    	if (firstTime){
    		new AePlayWave("pacman_beginning.wav").start();

        	ImageIcon z = new ImageIcon(PacWorld.getFrame().getClass().getResource("GridWorld.png"));

    		JLabel welcome = new JLabel("<HTML>Oh no! Ms. PacMan has been kidnapped by evil ghosts!<br><br>Use the arrow keys to navigate PacMan to avoid the ghosts. <br><br>Get points by eating pellets. <br><br>Eating a flashing Power Pellet will allow you to temporarily eat ghosts.<br><br>Are you ready?</HTML>");
    		int a = JOptionPane.showConfirmDialog(getFrame(), welcome, "Instructions", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, z);

    		if (a == 1){
        		JOptionPane.showMessageDialog(getFrame(), "You're ready.", "Shut up.", 2, z);
    		}
    		
    		firstTime = false;
        }
    	
    	PacMan.extrapac(); //sound
    	GUIController.pause(3);
    	((WorldFrame) getFrame()).getController().run();
    }
    
    /**
     * @override to update the points and lives text with every step
     */
    public void step()
    {
    	super.step();
    	refreshMessage();
    	super.repaint();
    }
    
    /**
     * restarts the level (when pacman dies and still has a life or two)
     */
    public static void restart(){
    	PacMan.updatePoints();
		try {
			JOptionPane.showMessageDialog(getFrame(), new JLabel("You have lost a life! You now have " + 
					(PacMan.getLives()) + " left! Press the button below to continue."), "You have died!", 2,  
					new ImageIcon(PacWorld.getFrame().getClass().getResource("GridWorld.png")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	level.placePacMan(level.getPac(), level.getPacManStart());
    	
    	if (level.getGrid().getNumRows() == level3x && level.getGrid().getNumCols() == level3y)
    		level.placeMsPacMan(new MsPacMan(), msPacLocation());
    	
    	Ghost[] replacements = new Ghost[level.getGhosts().length];
    	
    	for (int x = 0; x < replacements.length; x++){
    		if (Level.getGhosts()[x].getCovered() != null)
    			Level.getGhosts()[x].getCovered().putSelfInGrid(level.getGrid(), level.getGhosts()[x].getPreviousLocation());
    		replacements[x] = new Ghost (level.getGhosts()[x].getColor(), x + 1);
    		replacements[x].setVulnerable(false);
    		
    		
    	}
    	
    	level.placeGhosts(replacements, level.getGhostsStart());
    	
    	PacMan.setDead(false);
    }
    
    /**
     * ends the game
     * 
     */
    public static void gameOver(){
    	PacMan.updatePoints();
    	
    	String s = new String("<HTML>You have " + (PacMan.getLives()) + " lives left!" +
				" You lose! <br><br> Your Score: " + PacMan.getPoints()) + "<br><br>Would you like to play again?</HTML>";	
    	
    	JLabel message = new JLabel(s);
    	
    	ImageIcon x = new ImageIcon(PacWorld.getFrame().getClass().getResource("GridWorld.png"));
    	int a = JOptionPane.showConfirmDialog(getFrame(), message, "Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, x);
    	
    	if (a == 1){
    		System.exit(0);
    	}
    	
    	restartGame();
    	firstTime = true;
    }
    
    public static void restartGame(){
    	Level l = PacWorld.level1();

        
		
		PacMan.setDead(false);
		PacMan.setLives(3);
		PacMan.setPoints(0);
		PacWorld.setGameOver(false);
		
		getFrame().dispose();
        PacWorld p = new PacWorld(l);
		p.refreshMessage();
		((WorldFrame) getFrame()).getController().stop();
        p.show();	
    }
    
    private static boolean gameOver = false;
    public  static boolean isGameOver(){
    	return gameOver;
    }
    
    public static void setGameOver(boolean b){
    	gameOver = b;
    }
    
    private static boolean gameWon = false;
    
    public static void gameWon(){
    	String s = new String("<HTML>You have saved Lady PacMan from the evil ghosts! <br><br>Your Score: " + PacMan.getPoints()) + "<br><br>Would you like to play again?</HTML>";	
    	
    	JLabel message = new JLabel(s);
    	
    	ImageIcon x = new ImageIcon(PacWorld.getFrame().getClass().getResource("GridWorld.png"));
    	int a = JOptionPane.showConfirmDialog(getFrame(), message, "You won!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, x);
    	
    	if (a == 1){
    		System.exit(0);
    	}
    	else{
    		restartGame();
    	}
    }
    
    public static boolean isGameWon(){
    	return gameWon;
    }
    
    public static void setGameWon(boolean g){
    	gameWon = g;
    }
    
}