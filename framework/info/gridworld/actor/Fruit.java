package info.gridworld.actor;

/*
 * Notes:
 * 
 * Fruit only appears under the cages of the ghosts
 * Fruits: Cherry, Apple, Strawberry, Orange, Melon, Galaxian
 * 
 * 
 */

public abstract class Fruit extends Actor{		//Abstract

	private int points;
	private int counter;	//counter for fruit to stay on screen
	
	public Fruit(int pts){
		points = pts;
		counter = 60;	//default counter
	}
	
	//returns the points
	public int getPoints(){
		return points;
	}
	
	//"ages" the fruit
	public void act(){
		counter--;
		if(counter == 0){
			removeSelfFromGrid();
		}
	}
	

}
