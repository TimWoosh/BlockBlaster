package gameObjects;

import java.util.ArrayList;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.objects.GameObject;

/**
 * Power up object.
 * @author Juri de Vos, Tim Wöstemeier
 */
public class PowerUp extends MoveableGameObjectXT {

	/*
	 * storage for Ball that collided with Block
	 */
	public Ball ball;
	/*
	 * Number of different types of powerups
	 */
	private int numberOfPowerUp = 3;
	protected int selectPowerUp;
	private int puType = 0;
	private String[] puSprites;
	
	private static final int SLOWDOWN = 0;
	private static final int SPEEDUP = 1;
	private static final int EXTRA_BALL = 2;
	
	
	/**
	 * Constructor for PowerUp.
	 * @param ball Ball
	 */
	public PowerUp(Ball ball)
	{
		super(ball.game);
		this.puSprites = new String[]{
				"pu_slow",
				"pu_fast",
				"pu_2ball"
		};
		this.puType = (int)(numberOfPowerUp*Math.random());
		System.out.println("PowerUp type is: " + puType + " -- " + puSprites[puType]);
		this.setSprite(puSprites[puType]);
	}
	
	/**
	 * Constructor for PowerUp.
	 * @param ball Ball
	 * @param sprite String
	 * @param locX double
	 * @param locY double
	 */
	public PowerUp(Ball ball, double locX, double locY)
	{
		this(ball);
		this.setX(locX);
		this.setY(locY);
		this.ball = ball;
	}
	
	public void enablePowerUp()
	{
		PowerUpController puc = game.puController;
		puc.ball = ball;
		if(this.puType == SLOWDOWN)
		{
			puc.alarm = new Alarm(0, 200, puc);
			for(Ball b: game.balls)
			{
				b.setSpeed(6);
			}
		}
		else if(this.puType == SPEEDUP)
		{
			puc.alarm = new Alarm(1, 200, puc);
			for(Ball b: game.balls)
			{
				b.setSpeed(14);
			}
		}
		else if(this.puType == EXTRA_BALL)
		{
			Ball ball = new Ball("ball", getX(), getY(), game);
			ball.setDirectionSpeed(0, 10);
			ball.setBoundingBox();
			game.addGameObject(ball);
			game.balls.add(ball);
		}
	}
	
	@Override
	public void update()
	{
		printDebug();
		super.update();
		
		if(this.getCollidedObjects() != null)
		{
			ArrayList<GameObject> collidedWith = this.getCollidedObjects();
			
			if(collidedWith.get(0) instanceof Platform)
			{
				this.game.score.updateScore(this);
				this.enablePowerUp();
				this.deleteThisGameObject();
			}
			
			if(this.reachedEdge())
			{
				this.deleteThisGameObject();
			}
		}
	}
	
	@Override
	public void printDebug()
	{
		System.out.println("PowerUp.update...");
	}
}


//package gameObjects;
//
//import java.util.ArrayList;
//
//import android.gameengine.icadroids.objects.GameObject;
//import bb.main.GameEngineXT;
//import android.gameengine.icadroids.alarms.*;
//
///**
// * @author Juri de Vos
// */
//public class PowerUp extends MoveableGameObjectXT {
//
//	private String sprite = "power_up";
//	public Ball ball;
//	private Alarm alarm;
//	private int numberOfPowerUp = 3;
//	protected int selectPowerUp;
//	
//	/**
//	 * Constructor for PowerUp.
//	 * @param ball Ball
//	 */
//	public PowerUp(Ball ball)
//	{
//		super(ball.game);
//	}
//	
//	/**
//	 * Constructor for PowerUp.
//	 * @param ball Ball
//	 * @param sprite String
//	 * @param locX double
//	 * @param locY double
//	 */
//	public PowerUp(Ball ball, String sprite, double locX, double locY)
//	{
//		
//		this(ball);
//		System.out.println(this + " received following as arguments for construction: " +
//				"\n object: " + ball +
//				"\n sprite: " + sprite +
//				"x: " +locX+ " y: " +locY );
//		this.setSprite(sprite);
//		this.setX(locX);
//		this.setY(locY);
//		this.ball = ball;
//	}
//	
//	public void enablePowerUp()
//	{
//		selectPowerUp = (int)(numberOfPowerUp*Math.random())+1;
//		if(selectPowerUp == 1)
//		{
//			this.alarm = new Alarm(0, 200, (IAlarm)ball.game.puController);
//			this.ball.setSpeed(6);
//		}
//		else if(selectPowerUp == 2)
//		{
//			this.alarm = new Alarm(1, 200, (IAlarm)ball.game.puController);
//			this.ball.setSpeed(14);
//		}
//		else if(selectPowerUp == 3)
//		{
//			this.alarm = new Alarm(2, 200, (IAlarm)ball.game.puController);
//			Ball ballX = new Ball("ball", getX(), getY(), game);
//			game.addGameObject(ballX);
//			ballX.setDirectionSpeed(180, 10);
//		}
//	}
//	
//	public void update()
//	{
//		printDebug();
//		super.update();
//		
//		if(this.getCollidedObjects() != null)
//		{
//			ArrayList<GameObject> collidedWith = this.getCollidedObjects();
//			
//			if(collidedWith.get(0) instanceof Platform)
//			{
//				this.ball.game.score.updateScore(this);
//				this.enablePowerUp();
//				this.deleteThisGameObject();
//			}
//			
//			if(this.reachedEdge())
//			{
//				this.deleteThisGameObject();
//			}
//		}
//	}
//	
//	public void printDebug()
//	{
//		System.out.println("PowerUp.update...");
//	}
//}
