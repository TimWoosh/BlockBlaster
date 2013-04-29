package gameObjects;

import java.util.ArrayList;

import android.gameengine.icadroids.input.TouchInput;
import android.gameengine.icadroids.objects.GameObject;
import bb.main.GameEngineXT;

/**
 * Bouncing game ball.
 * @author Tim Wostemeier
 *
 * TODO Bugvrij maken zonder gebruik van undoMove(), want undoMove() doet het geheel houterig bewegen.
 *
 * @version $Revision: 1.0 $
 * --Moved super.update() in update() below first collission detection.
 */

public class Ball extends MoveableGameObjectXT{
	
	Collider collider = new Collider(game);
	public boolean free;
	private int timer;
	private int timeLastTouch;
	private boolean firstTouch;
	/**
	 * 
	 * @param game
	 * @param sprite String
	 */
	public Ball(GameEngineXT game, String sprite){
		super(game);
		this.free = false;
		this.timer = 0;
		this.timeLastTouch = -1;
		this.firstTouch = false;
		this.setSprite(sprite);
	}
	
	/**
	 * 
	 * @param sprite
	 * @param locX
	 * @param locY
	 * @param game
	 */
	public Ball(String sprite, double locX, double locY, GameEngineXT game){
		this(game, sprite);
		this.setX(locX);
		this.setY(locY);
	}
	 
	/**
	 * 
	 */
	@Override
	public void update()
	{
		if(this.free)
		{
			if(this.getCollidedObjects() != null)
			{
				System.out.println("\n \n ");
				ArrayList<GameObject> collidedWith = this.getCollidedObjects();
				
				System.out.println("#The total number of collided objects is: " + collidedWith.size()+ "#");
				System.out.println("#The first object in collided Objects is: " + collidedWith.get(0)+ "#");
				
				if(collidedWith.get(0) instanceof Block)
				{
					Block b = (Block)collidedWith.get(0);
					this.bounce(!this.collidedWithHorizontal(b));
					b.hitByBall(this);
				}
				else if (collidedWith.get(0) instanceof Platform)
				{
					Platform p = (Platform)collidedWith.get(0);
					
					System.out.println(">Collided with Platform " + p.getClass());
					
					this.bounce(!this.collidedWithHorizontal(p));
					double angleMod = p.getAngleModifier(this.getHitLoc(p)[0], this.getDirection());
					this.setDirection(angleMod);
				}
			}
	
			super.update();
			if(this.reachedEdge())
			{
				this.undoMove();
				int direction = this.getCollissionDirection(game);
				if(direction == BOTTOM)
				{
					game.ballLost(this);
					this.deleteThisGameObject();
				}
				else
				{
					this.bounce(!this.collidedWithHorizontal(game));
				}
			}
		}
		else
		{
			//Ball hasn't been set free yet. Means it follows the platform around until it is released.
			//Touching it for a second will release the ball as it is now
			timer++;
			if(TouchInput.onPress)
			{
				this.stickToPlatform();
				if(firstTouch && (timer - this.timeLastTouch) < 10)
				{
					this.free = true;
					this.setY(this.getFullY() - 10);
					this.setDirectionSpeed(0, game.ballBaseSpeed);
				}
				
				this.timeLastTouch = timer;
				
			}
			super.update();
			this.firstTouch = true;
		}
	}
	
	
	public void stickToPlatform()
	{
		this.setPosition(game.platform.getX() + game.platform.getFrameWidth()/2 - this.getFrameWidth()/2, game.platform.getY() - (this.getFrameHeight()) + 10);
	}
	/**
	 * 
	 */
	@Override
	public void printDebug()
	{
		System.out.println("Ball.update...");
	}
}
