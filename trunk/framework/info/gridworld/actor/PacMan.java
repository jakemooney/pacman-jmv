package info.gridworld.actor;

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
	private boolean blocked; //if he's facing a wall
	
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
	
	public void setCount(int count){
		this.count = count;
	}
	
	public boolean isBlocked(){
		return blocked;
	}
	
	//--------------------------------------------------------------
	
	public PacMan(){
		count = 31;
		setColor(Color.YELLOW);
		pendingDir = 90;
		blocked = false; //@author max
	}
	
	@Override
	public void act(){			
		
		//Some stuff with pending direction
		Location l = getLocation().getAdjacentLocation(pendingDir);
		if (pendingTurn && (getGrid().isValid(l)==false || !(getGrid().get(l) instanceof MazeWall))){
			pendingTurn = false;
			setDirection(pendingDir);
		}
		
		
		//Deal with the teleports
		int dir = getDirection();
		Location loc = getLocation().getAdjacentLocation(dir);	
		if (loc.getCol() == -1 || loc.getCol() == getGrid().getNumCols()){	
				int cols = getGrid().getNumCols();
				if (loc.getCol() == -1)
					loc =  new Location(loc.getRow(), getGrid().getNumCols() - 1);
				else
					loc =  new Location(loc.getRow(), 0);
		}
		
		
		count++;	//Right place???
		if (count >= 30){
			Ghost.setVulnerable(false);
		}
				
		//Deal with other actors
		Actor a = getGrid().get(loc);
		
		if (a instanceof MazeWall ){
			blocked = true;
			return;
		}
		else{
			blocked = false;
			if (a instanceof PowerPellet1 || a instanceof PowerPellet2){
				points += 50;
				a.removeSelfFromGrid();
				count = 0;
				Ghost.setVulnerable(true);
			}
			else if (a instanceof Pellet){
				points += 10;
				a.removeSelfFromGrid();
			}
			else if (a instanceof Ghost && Ghost.isvulnerable()){
				if (((Ghost)a).getCovered() instanceof PowerPellet1 || ((Ghost)a).getCovered() instanceof PowerPellet2){
					points += 50;
					count = 0;
					Ghost.setVulnerable(true);
				}
				else if (((Ghost)a).getCovered() instanceof Pellet){
					points += 10;
				}
				else if (((Ghost)a).getCovered() instanceof Fruit){
					points += ((Fruit)((Ghost)a).getCovered()).getPoints();
				}
				points += 200;
				((Ghost)a).resetGhost();
				((Ghost)a).setCovered(null);
			}
			else if (a instanceof Ghost){
				dead = true;
				lives--;
				removeSelfFromGrid();
				return;
			}	
		}
		
		//15, 8 wtf
		
		//moveTo(loc);
		
		/**
		PacMan p;
		if (c % 4 == 0){
			p = new PacManOpen(count);
		}
		else if (c % 4 == 1){
			p = new PacManMiddle(count);
		}
		else if (c % 4 == 2){
			p = new PacManClosed(count);
		}
		else{
			p = new PacManMiddle(count);
		}
		
		Level.setPac(p);
		p.putSelfInGrid(getGrid(), loc);
		removeSelfFromGrid();
		p.setDirection(getDirection());
		p.setPendingDirection(pendingDir);
		*/
		moveTo(loc);
	}
	
	public static boolean isDead(){
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
