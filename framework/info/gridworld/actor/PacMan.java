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
		
		//Important stuff
		int dir = getDirection();
		Location loc = getLocation().getAdjacentLocation(dir);	
		
		//Deal with the teleports
		if (loc.getCol() == -1 || loc.getCol() == getGrid().getNumCols()){	
				if (loc.getCol() == -1)
					loc =  new Location(loc.getRow(), getGrid().getNumCols() - 1);
				else
					loc =  new Location(loc.getRow(), 0);
		}
		
		count++;	
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
				Chomp();
			}
			else if (a instanceof Pellet){
				points += 10;
				a.removeSelfFromGrid();
				Chomp();
			}
			else if (a instanceof MsPacMan){
				level.fillWithHearts();
				PacWorld.getFrame().repaint();
				blocked = true; //doubt this is needed but w/e
				PacWorld.gameWon();
				PacWorld.setGameWon(false); //the game has ended after this. back to the beginning
			}
			else if (a instanceof Fruit){
				points += ((Fruit) a).getPoints();
				a.removeSelfFromGrid();
				EatFruit();
			}
			else if (a instanceof Ghost && Ghost.isvulnerable()){
				if (((Ghost)a).getCovered() != null){
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
					else if (((Ghost)a).getCovered() instanceof Ghost){
						points += 200;
						Location loc2 = new Location(getGrid().getNumRows()/2, getGrid().getNumCols()/2);
						 if (getGrid().get(loc2) == null){
							 ((Ghost)((Ghost)a).getCovered()).putSelfInGrid(getGrid(), loc2);
						 }
						 else if (getGrid().get(new Location(loc2.getRow(), loc2.getCol()-1)) == null){
							 ((Ghost)((Ghost)a).getCovered()).putSelfInGrid(getGrid(), new Location(loc2.getRow(), loc2.getCol()-1));
						 }
						 else if (getGrid().get(new Location(loc2.getRow(), loc2.getCol()+1)) == null){
							 ((Ghost)((Ghost)a).getCovered()).putSelfInGrid(getGrid(), new Location(loc2.getRow(), loc2.getCol()+1));
						 }
						((Ghost)((Ghost)a).getCovered()).reset();
					}
					((Ghost)a).setCovered(null);
				}
				points += 200;
				EatGhost();
				((Ghost)a).resetGhost();
				((Ghost)a).setCovered(null);
			}
			else if (a instanceof Ghost){
				dead = true;
				lives--;
				//Not here??  ---   GetEaten();
				removeSelfFromGrid();
				return;
			}	
		}
		
		//REVISIT!!!!!!!!!!
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
		GetEaten();
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
	
	//SOUND STUFF//
	
	public void Chomp(){
		new AePlayWave("pacman_chomp.wav").start();
	}
	public void EatFruit(){
		new AePlayWave("pacman_eatfruit.wav").start();
	}
	public void EatGhost(){
		new AePlayWave("pacman_eatghost.wav").start();
	}
	public static void GetEaten(){
		new AePlayWave("pacman_death.wav").start();
	}
	
	public static void extrapac(){
		new AePlayWave("pacman_extrapac.wav").start();
	}
}