package info.gridworld.actor;

/*
 * Notes:
 * 
 * Fruit only appears under the cages of the ghosts
 * Fruits: Cherry, Apple, Strawberry, Orange, Melon, Galaxian
 * Read more: http://wiki.answers.com/Q/What_are_all_of_the_fruits_in_pac_man#ixzz1NYIEoPEW
 * 
 * 
 * 
 * reset fruitplaced for level!!!!!!!!!!!!!!
 * 
 */

public abstract class Fruit extends Actor{			//Abstract

	private int points;
	private int counter;				//??? counter for point value to be displayed
	
	public Fruit(int pts){
		points = pts;
		counter = 60;				//default counter
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
		//super.act();???????????????
	}
	

}
