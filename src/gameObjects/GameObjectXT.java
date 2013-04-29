package gameObjects;

import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.graphics.Sprite;
import android.graphics.Rect;

/**
 * Extension on GameObject.
 * Important!
 * Call GameObjectXT.setBoundingBox AFTER dimensions and location for this GameObjectXT are defined
 * or boundingBox for this GameObjectXT will not function properly. (Game engine limitation)
 * @author Tim Wostemeier
 * 
 * 
 * 
 * @version $Revision: 1.0 $
 */

public class GameObjectXT extends GameObject {
	
	public double alphaAngle;
	public double topAngleLL;
	public double topAngleLR;
	public double topAngleRL;
	public double topAngleRR;
	public double rightAngleT;
	public double rightAngleB;
	public double bottomAngleR;
	public double bottomAngleL;
	public double leftAngleB;
	public double leftAngleT;
	
	/*
	 * object dimensions
	 */
	protected int width;
	protected int height;
	/*
	 * object bounding box needed for collisions
	 */
	private Rect boundingBox;
	
	/**
	 * Constructor for GameObjectXT
	 */
	public GameObjectXT() {
		super();
		//this.printDebug();
	}
	
	/**
	 * Constructor overload for GameObjectXT.
	 * @param sprite String
	 * @param locX double
	 * @param locY double
	 */
	public GameObjectXT (String sprite, double locX, double locY)
	{
		this();
		this.setSprite(sprite);
		this.setPosition(locX, locY);
	}

	/**
	 * Method setDirectionAngles
	 *  defines the sides of the object. Used for collision
	 * @return void
	 */
	protected void setDirectionAngles()
	{
		Sprite sprite = this.getSprite();
		double width = sprite.getFrameWidth()/2;
		double height =	sprite.getFrameHeight()/2;
		this.alphaAngle = Math.toDegrees(Math.atan2(height, width));
		this.topAngleLL = 270 + alphaAngle;
		this.topAngleLR = 360;
		this.topAngleRL = 0;
		this.topAngleRR = 90 - alphaAngle;
		this.rightAngleT = topAngleRR;
		this.rightAngleB = 90 + alphaAngle;
		this.bottomAngleR = rightAngleB;
		this.bottomAngleL = 270 - alphaAngle;
		this.leftAngleB = bottomAngleL;
		this.leftAngleT = topAngleLL;
		
		this.printDebug();
	}
	
	/**
	 * Method getBoundingBox.
	
	 * @return Rect */
	public Rect getBoundingBox()
	{
		return this.boundingBox;
	}
	
	/**
	 * Method setBoundingBox
	 * Sets the bounding box used for collision detection
	 * 
	 * @return void
	 */
	public void setBoundingBox()
	{
		int left = this.getX();
		int top = this.getY();
		int right = left + this.getFrameWidth();
		int bottom = top + this.getFrameHeight();
		this.boundingBox = new Rect(left, top, right, bottom);
		System.out.println(">>setBoundingBox " + boundingBox + " for " + this + " <<");
		this.setDirectionAngles();
	}
	
	/*
	 * This is needed by the game engine
	 * (non-Javadoc)
	 * @see android.gameengine.icadroids.objects.GameObject#update()
	 */
	@Override
	public void update() {
		super.update();
	}
	
	/*
	 * Debug info
	 */
	public void printDebug()
	{
		System.out.println("The angles for the sides of this GameObjectXT are:");
		System.out.println("TOP: between " +topAngleLL+ " and " +topAngleRR+ " degrees" );
		System.out.println("RIGHT: between " +rightAngleT+ " and " +rightAngleB+ " degrees" );
		System.out.println("BOTTOM: between " +bottomAngleR+ " and " +bottomAngleL+ " degrees" );
		System.out.println("LEFT: between " +leftAngleB+ " and " +leftAngleT+ " degrees" );
		System.out.println("The alpha angle is:" +alphaAngle+ " degrees" );
	}
}
