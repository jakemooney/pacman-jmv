package info.gridworld.actor;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

import Levels.Level;
public class PacMan extends Actor{

	private int count, pendingDir;
	private static int lives, points;
	private static boolean dead;
	private boolean pendingTurn;
	
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
	
	public static int getPoints(){
		return points;
	}
	
	public static int getLives(){
		return lives;
	}
	
	public static void setPoints(int points){
		PacMan.points = points;
	}
	
	public static void setLives(int lives){
		PacMan.lives = lives;
	}
	
	public PacMan(){
		lives = 3;
		points = 0;
		count = 51;
		dead = false;
		setColor(Color.YELLOW);
	}
	
	public void act(){
		if (pendingTurn && !(getGrid().get(getLocation().getAdjacentLocation(pendingDir)) instanceof MazeWall)){
			pendingTurn = false;
			setDirection(pendingDir);
		}
		if (count >= 50){
			Ghost.setVulnerable(false);
		}
		count++;
		int dir = getDirection();
		Location loc = getLocation().getAdjacentLocation(dir);	
		//Vivek - 5/20/11 - teleport
		if (loc.getCol() == -1 || loc.getCol() == getGrid().getNumCols()){	
				int cols = getGrid().getNumCols();
				if (loc.getCol() == -1)
					loc =  new Location(loc.getRow(), getGrid().getNumCols() - 1);
				else
					loc =  new Location(loc.getRow(), 0);
		}
			
		Actor a = getGrid().get(loc);
		if (a instanceof MazeWall )
			return;
		
		
		//jake, i found an error. PowerPellet1's and 2's ARE instances of Pellet.
		//so eating a powerpellet1 or 2 would have the same effect as eating a pellet
		//here. I tested
		//		System.out.print(new PowerPellet1() instanceof Pellet);
		//and it printed out true.
		
		//should be fixed now, i test for power pellet first now, order of priority should solve that
		else if (a instanceof PowerPellet1 || a instanceof PowerPellet2){
			points += 50;
			a.removeSelfFromGrid();
			count = 0;
			Ghost.setVulnerable(true);
		}
		else if (a instanceof Pellet){
			points += 10;
			a.removeSelfFromGrid();
			level.decrementPelletCount();
		}
		else if (a instanceof Ghost && Ghost.isvulnerable()){
			points += 200;
			a.removeSelfFromGrid();
		}
		else if (a instanceof Ghost){
			dead = true;
			lives--;
			this.removeSelfFromGrid();
			return;
		}
		
		moveTo(loc);
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public static void kill(){
		lives--;
		setDead(true);
	}
	
	public static void setDead(boolean a){
		dead = a;
	}
	public static void eatGhost(){
		points += 200;
	}
	public void setPendingDirection(int dir){
		pendingTurn = true;
		pendingDir = dir;
	}
}

