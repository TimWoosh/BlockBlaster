package bb.main;


import gameObjects.Ball;
import gameObjects.GameBlocks;
import gameObjects.Platform;
import gameObjects.PowerUpController;
import android.gameengine.icadroids.forms.IFormInput;
import android.gameengine.icadroids.input.TouchInput;
import android.gameengine.icadroids.renderer.GameView;
import android.graphics.Color;
import android.view.View;

/**
 * Game launcher class
 * XT classes are classes specific for this project that extend existing game framework classes. 
 * This was done to implement extra functionality needed for the game.
 * 
 * @author Tim Wostemeier
 * 
 */

public class Game extends GameEngineXT implements IFormInput{

	
	public Game()
	{
		this.setScreenLandscape(false); //Android device screen set to Portrait mode.
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see android.gameengine.icadroids.engine.GameEngine#initialize()
	 * Setup and start game.
	 * 
	 */
	@Override
	protected void initialize()
	{
		super.initialize();
		
		/*
		 * Set number of balls/lives the player has.
		 */
		this.numBalls = 3;
		/*
		 * Set standard speed for the balls.
		 */
		this.ballBaseSpeed = 10;
		
		/*
		 * Setup gameworld
		 */
		GameView.BACKGROUND_COLOR = Color.rgb(50,50,50);
		this.setBackground("gamebg");
		this.setDim(60, 440, 760, 40);
		
		/*
		 * Setup power up functionality
		 */
		this.puController = new PowerUpController(this);
		
		/*
		 * Setup scoreboard functionality
		 */
		this.score = new ScoreBoard(this);
		
		/*
		 * Setup touch
		 */
		TouchInput.use = true;
		TouchInput.maxFingers = 2;
		
		/*
		 * Define layout for the game blocks
		 * -1 = no block
		 *  0 = basic block
		 *  1 = tough block (needs 2 collissions to be destroyed)
		 *  2 = powerup block (releases falling powerup)
		 */
//		int[][] blocks1 = {
//				{0,-1,-1,-1,-1,1,1,0,0,0},
//				{-1,-1,-1,-1,-1,0,1,0,1,0},
//				{-1,-1,-1,-1,-1,0,-1,0,1,0},
//				{-1,-1,-1,-1,-1,0,-1,0,1,0}
//		};
		
		
		int[][] blocks2 = {
				{0,0,0,0,0,0,0,0,0,0},
				{2,1,2,1,2,1,2,1,2,1,2},
				{0,2,0,0,1,1,0,0,2,0},
				{1,1,1,1,1,1,1,1,1,1},
				{2,2,2,2,2,2,2,2,2,2}
		};
		
//		int[][] blocks3 = {
//				{0,1,2,0,1,2,0,0,1,2},
//				{2,-1,-1,-1,-1,-1,-1,-1,-1,0},
//				{1,-1,-1,-1,-1,-1,-1,-1,-1,1},
//				{0,-1,-1,-1,-1,-1,-1,-1,-1,2},
//				{2,-1,-1,-1,-1,-1,-1,-1,-1,0},
//				{1,-1,-1,-1,-1,-1,-1,-1,-1,1},
//				{0,-1,-1,-1,-1,-1,-1,-1,-1,2},
//				{2,-1,-1,-1,-1,-1,-1,-1,-1,0},
//				{1,-1,-1,-1,-1,-1,-1,-1,-1,1},
//				{0,-1,-1,-1,-1,-1,-1,-1,-1,2},
//				{2,-1,-1,-1,-1,-1,-1,-1,-1,0},
//				{1,-1,-1,-1,-1,-1,-1,-1,-1,1},
//				{0,-1,-1,-1,-1,-1,-1,-1,-1,2},
//				{2,-1,-1,-1,-1,-1,-1,-1,-1,0},
//				{1,-1,-1,-1,-1,-1,-1,-1,-1,1},
//				{0,-1,-1,-1,-1,-1,-1,-1,-1,2},
//				{2,-1,-1,-1,-1,-1,-1,-1,-1,0},
//				{1,0,2,1,0,2,1,0,2,1}
//		};
		/*
		 * Create the block layout
		 */
		createBlocks(blocks2);
		
		/*
		 * Setup the platform
		 */
		this.platform = new Platform(this);
		this.platform.setPosition(this.getBoundingBox().exactCenterX() - platform.getFrameWidth()/2, 640);
		this.platform.setBoundingBox();
		addGameObject(this.platform);
		
		/*
		 * Setup ball
		 */
		Ball ball = new Ball("ball", 200, 200, this);
		ball.stickToPlatform(); //Ball will initially move with (stick to) the platform.
		ball.setBoundingBox();
		addGameObject(ball);
		this.balls.add(ball); //Add the ball to the collection of balls. Collection of balls is used by PowerUpController
	}
	
	/**
	 * Method createBlocks.
	 * @param map int[][] Accepted int values for this game are -1, 0, 1, 2: each defining a block type. Minimum size = [1][10]
	 */
	public void createBlocks(int[][] map)
	{
		//New collection of Blocks with dimensions of the blocks being 40x20 pixels
		gameBlocks = new GameBlocks(this, 40, 20);
		
		/*
		 * Set the block types that are used by this game.
		 */
		int[] blockTypes = {
				GameBlocks.BLOCK_BASIC,
				GameBlocks.BLOCK_TOUGH,
				GameBlocks.BLOCK_POWERUP
				};
		gameBlocks.setBlockTypes(blockTypes);
		
		/*
		 * Generate the blocksa
		 */
		gameBlocks.setMap(map);
		gameBlocks.createBlocks();
	}

	public void formElementClicked(View touchedElement) {
		// TODO Auto-generated method stub
	}

}
