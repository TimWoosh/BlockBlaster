package bb.main;

import gameObjects.Ball;
import gameObjects.GameBlocks;
import gameObjects.Platform;
import gameObjects.PowerUpController;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.gameengine.icadroids.engine.GameEngine;
import android.graphics.Rect;

/**
 * Extension on the GameEngine. It adds extra functionality on top of GameEngine.
 * @author Tim Wöstemeier
 * @version $Revision: 1.0 $
 */
public class GameEngineXT extends GameEngine {
	/**
	 * Rectangle that defines the playing field.
	 */
	protected Rect borders;
	/**
	 * Angle that defines the leftmost boundary of the TOP side triangle of the playing field
	 */
	public double topAngleLL;
	/**
	 * Angle that defines the middle_left boundary of the TOP side triangle of the playing field
	 */
	public double topAngleLR;
	/**
	 * Angle that defines the middle_right boundary of the TOP side triangle of the playing field
	 */
	public double topAngleRL;
	/**
	 * Angle that defines the rightmost boundary of the TOP side triangle of the playing field
	 */
	public double topAngleRR;
	/**
	 * Angle that defines the top boundary of the RIGHT side triangle of the playing field
	 */
	public double rightAngleT;
	/**
	 * Angle that defines the bottom boundary of the RIGHT side triangle of the playing field
	 */
	public double rightAngleB;
	/**
	 * Angle that defines the rightmost boundary of the BOTTOM side triangle of the playing field
	 */
	public double bottomAngleR;
	/**
	 * Angle that defines the leftmost boundary of the BOTTOM side triangle of the playing field
	 */
	public double bottomAngleL;
	/**
	 * Angle that defines the bottom boundary of the LEFT side triangle of the playing field
	 */
	public double leftAngleB;
	/**
	 * Angle that defines the top boundary of the LEFT side triangle of the playing field
	 */
	public double leftAngleT;
	/**
	 * Holds and handles score related matters.
	 */
	public ScoreBoard score;
	/**
	 * Stores the default value for the speed of the ball, so this is known at all time.
	 * Needed for instance when the ball's speed is changed by a powerup and the balls speed needs to be reset to default after a certain amount of time
	 */
	public double ballBaseSpeed;
	/**
	 * The platform that is used by the player to bounce the ball back to the blocks
	 */
	public Platform platform;
	/**
	 * Stores the number of balls (lives) left
	 */
	protected int numBalls;
	/**
	 * Class to create and store all the game blocks.
	 */
	public GameBlocks gameBlocks;
	/**
	 * Returns the state of the game back to normal after the powerups expire
	 */
	public PowerUpController puController;
	/**
	 * Container for all balls currently active on the screen. Makes it easier to apply powerups to all balls.
	 */
	public LinkedList<Ball> balls;
	
	/**
	 * Constructor for GameEngineXT.
	 */
	public GameEngineXT() {
		super();
		/*
		 * Initialize the game's borders (balls will bounce on these borders)
		 */
		borders = new Rect();
		/*
		 * Initialize balls collection.
		 */
		balls = new LinkedList<Ball>();
	}
	
	/**
	 * Constructor overload for GameEngineXT.
	 * @param top double
	 * @param right double
	 * @param bottom double
	 * @param left double
	 */

	public GameEngineXT(double top, double right, double bottom, double left)
	{
		this();
		this.setDim(left, top, right, bottom);
	}
	
	/**
	 * Method setDim.
	 * @param top double
	 * @param right double
	 * @param bottom double
	 * @param left double
	 */
	public void setDim(double top, double right, double bottom, double left)
	{
		borders.set((int)left, (int)top, (int)right, (int)bottom);
		this.setDirectionAngles((getRight()-getLeft())/2, (getBottom()-getTop())/2);
	}
	
	/**
	 * Method getTop.
	
	 * @return double */
	public double getTop()
	{
		return borders.top;
	}
	
	/**
	 * Method getRight.
	
	 * @return double */
	public double getRight()
	{
		return borders.right;
	}
	
	/**
	 * Method getBottom.
	
	 * @return double */
	public double getBottom()
	{
		return borders.bottom;
	}
	
	/**
	 * Method getLeft.
	
	 * @return double */
	public double getLeft()
	{
		return borders.left;
	}
	
	/**
	 * Method getDim.
	
	 * @return Rect */
	public Rect getBoundingBox()
	{
		return borders;
	}
	
	/**
	 * Method setDirectionAngles.
	 *  defines the sides of the playing field. Used for collision
	 * @param width double
	 * @param height double
	 */
	private void setDirectionAngles(double width, double height)
	{
		double alphaAngle = Math.toDegrees(Math.atan2(height, width));
		this.topAngleLL = 270 + alphaAngle;
		this.topAngleLR = 360;
		this.topAngleRL = 0;
		this.topAngleRR = 90 - alphaAngle;
		this.rightAngleT = this.topAngleRR;
		this.rightAngleB = 90 + alphaAngle;
		this.bottomAngleR = this.rightAngleB;
		this.bottomAngleL = 270 - alphaAngle;
		this.leftAngleB = this.bottomAngleL;
		this.leftAngleT = this.topAngleLL;
		this.printDebug();
	}
	
	/**
	 * Method ballLost
	 * @param lostBall
	 */
	public void ballLost(Ball lostBall)
	{
		if(balls.size() <= 1)
		{
			System.out.println("only 1 ball in balls");
			if(numBalls > 0)
			{
				System.out.println("new ball");
				balls.remove(lostBall);
				Ball ball = new Ball(this, "ball");
				ball.setBoundingBox();
				ball.stickToPlatform();
				addGameObject(ball);
				balls.add(ball);
				numBalls--;
			}
			else{
				System.out.println("no more balls left in store");
			}
		}
		else
		{
			System.out.println("more than 1 ball in balls, removing");
			balls.remove(lostBall);
		}
	}
	
	private void printDebug()
	{
		System.out.println("Dimensions of playing field set:");
		System.out.println("Top: " + getTop());
		System.out.println("Right: " + getRight());
		System.out.println("Bottom: " + getBottom());
		System.out.println("Left: " + getLeft());
		System.out.println("Height: " + (getBottom()-getTop()));
		System.out.println("Width: " + (getRight()-getLeft()));
	}
}
