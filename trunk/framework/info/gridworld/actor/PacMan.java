package info.gridworld.actor;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;
public class PacMan extends Actor{
	private int points, count;
	private static int lives;
	private static boolean dead;
	public PacMan(){
		lives = 3;
		points = 0;
		count = 51;
		dead = false;
		setColor(Color.YELLOW);
	}
	
	public void act(){
		if (count == 50)
			Ghost.setVulnerable(false);
		count++;
		int dir = getDirection();
		Location loc = getLocation().getAdjacentLocation(dir);
		Actor a = getGrid().get(loc);
		if (a instanceof MazeWall )
			return;
		else if (a instanceof Pellet){
			points += 10;
			a.removeSelfFromGrid();
			this.moveTo(loc);
		}
		else if (a instanceof Ghost && Ghost.isvulnerable()){
			points += 200;
			a.removeSelfFromGrid();
			this.moveTo(loc);
		}
		else if (a instanceof Ghost){
			dead = true;
			lives--;
			this.removeSelfFromGrid();
		}
		else if (a instanceof PowerPellet1 || a instanceof PowerPellet2){
			points += 50;
			a.removeSelfFromGrid();
			this.moveTo(loc);
			count = 0;
			Ghost.setVulnerable(true);
		}
		//else if (loc.isValid())
		else //if (a.equals(null))
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
	
	
	
	

}

