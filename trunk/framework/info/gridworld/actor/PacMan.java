package info.gridworld.actor;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.world.PacWorld;

import java.awt.Color;
import Levels.Level;
public class PacMan extends Actor{

	private int count, pendingDir;
	private static int lives = 3, points = 0, currentpoints = 0;
	private static boolean dead = false;
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
		count = 31;
		setColor(Color.YELLOW);
	}
	
	@Override
	public void act(){		
		
		if (pendingTurn && !(getGrid().get(getLocation().getAdjacentLocation(pendingDir)) instanceof MazeWall)){
			pendingTurn = false;
			setDirection(pendingDir);
		}
		if (count >= 30){
			Ghost.setVulnerable(false);
		}
		count++;
		int dir = getDirection();
		Location loc = getLocation().getAdjacentLocation(dir);	
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
		else if (a instanceof PowerPellet1 || a instanceof PowerPellet2){
			points += 50;
			a.removeSelfFromGrid();
			count = 0;
			Ghost.setVulnerable(true);
			level.decrementPelletCount();
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
			removeSelfFromGrid();
			if (lives > 0){
				PacWorld.restart();
			}
			else{
				PacWorld.gameOver();
			}
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
	public static void updatePoints(){
		currentpoints = points;
	}
	public static int getCurrentPoints(){
		return points-currentpoints;
	}
	
}

