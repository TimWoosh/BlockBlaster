package bb.main;

import gameObjects.BlockBasic;
import gameObjects.BlockPowerUp;
import gameObjects.BlockTough;
import gameObjects.GameObjectXT;
import gameObjects.PowerUp;

import java.util.LinkedList;

/**
 * Object for calculating and showing the score in a scoreboard on the game screen.
 * @author Tim Wöstemeier
 *
 * @version $Revision: 1.0 $
 */
public class ScoreBoard {

	
	public int score;
	
	private GameObjectXT[] boardValues;
	
	private String[] numbers;
	
	private Game game;
	
	/**
	 * 
	 * @param game Game
	 */
	public ScoreBoard(Game game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		numbers = new String[] {"n0","n1","n2","n3","n4","n5","n6","n7","n8","n9"};
		boardValues = new GameObjectXT[] { //180 - 310
				new GameObjectXT(numbers[0], 182, 16),
				new GameObjectXT(numbers[0], 207, 16),
				new GameObjectXT(numbers[0], 232, 16),
				new GameObjectXT(numbers[0], 257, 16),
				new GameObjectXT(numbers[0], 282, 16)
		};
		
		for(GameObjectXT number: boardValues)
		{
			game.addGameObject(number);
		}
	}
	
	/**
	 * Method updateScore.
	 * Updates scoreboard with the extra points earned.
	 * @param obj GameObjectXT
	 */
	public void updateScore(GameObjectXT obj){
		if(obj instanceof BlockTough)
		{
			score += 10;
		}
		else if (obj instanceof BlockPowerUp)
		{
			score += 8;
		}
		else if (obj instanceof BlockBasic)
		{
			score += 5;
		}
		else if (obj instanceof PowerUp)
		{
			score += 15;
		}
		
		LinkedList<Integer> splitScore = this.splitScore(score);
		
		/*
		 * Change numbers of scoreboard
		 */
		int j = 0;
		int scorePos = 0;
		for(int i=5; i>0; i--)
		{
			
			if(j<splitScore.size())
			{
				scorePos = splitScore.get((splitScore.size()-j)-1);
			}
			else
			{
				scorePos = 0;
			}
			System.out.println("ScorePos: " + scorePos);
			boardValues[i-1].setSprite(numbers[scorePos]);
			j++;
		}
		
	}
	
	/**
	 * Method splitScore.
	 * @param score int
	 * @return LinkedList<Integer>
	 */
	public LinkedList<Integer> splitScore(int score)
	{
		LinkedList<Integer> stack = new LinkedList<Integer>();
		while(score > 0)
		{
			stack.addFirst((score%10));
			score = score/10;
		}
		return stack;
	}
}
