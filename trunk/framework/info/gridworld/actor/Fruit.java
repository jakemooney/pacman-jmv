package info.gridworld.actor;

//Vivek G - 5/21/11 - My attempt at making fruit

/*
 * Notes:
 * -Fruit only appears under the cages of the ghosts
 * -this only 
 */

public abstract class Fruit extends Actor{			//Abstract?

	private int points;
	private int counter;				//??? counter for point value to be displayed
	
	public Fruit(int pts){
		points = pts;
	}
	
	//returns the points
	public int getPoints(){
		return points;
	}
	
	
}
