/*
 * TODO methode hasReachedEdge() 	//Deze methode kijkt of de MoveableGameObject de rand van het speelveld heeft bereikt.
 * 									//returned true of false
 * 									//Evt. uitbreiding/wijziging: returned ipv. true of false welke edge de object heeft bereikt (return bijv. 0,1,2,3,-1 = top, right, bottom, left, nog geen edge bereikt)
 */


package gameObjects;

import android.gameengine.icadroids.objects.MoveableGameObject;
import android.graphics.Rect;
import bb.main.GameEngineXT;

/**
 * Extension on MoveableGameObject.
 * @author Tim
 * @version $Revision: 1.0 $
 */
public class MoveableGameObjectXT extends MoveableGameObject 
{
	protected GameEngineXT game;

	public static final int TOP = 0;
	public static final int RIGHT = 1;
	public static final int BOTTOM = 2;
	public static final int LEFT = 3;

	public Object prevCollidedObject = new Object();
	/**
	 * Constructor for MoveableGameObjectXT.
	 * @param game GameEngineXT
	 */
	public MoveableGameObjectXT(GameEngineXT game)
	{
		super();
		this.game = game;
		
		this.setBoundingBox();
	}
	
	/**
	 * 
	
	 * @return true if the Rect of MoveableGameObjectXT is not fully enclosed in the playing field Rect */
	public boolean reachedEdge()
	{
		return !game.getBoundingBox().contains(this.getBoundingBox().left, this.getBoundingBox().top, this.getBoundingBox().right, this.getBoundingBox().bottom);
	}
	
	/**
	 * update the location of the MoveableGameObjectXT's rectangle
	 */
	public void updateBoundingBox()
	{
		int left = this.getX();
		int top = this.getY();
		this.getBoundingBox().offsetTo(left, top);
	}
	
	/**
	 * Calculates which side of the object this MoveableGameobjectXT collided with. 
	
	 * @param obj Object
	
	 * @return int */
	public int getCollissionDirection(Object obj)
	{
		Collider collider = new Collider(game);
		//TODO check of dit if else blok overbodig is. (dubbele code)
//		if(obj instanceof ArrayList<?>)
		if(obj instanceof GameObjectXT)
		{
			System.out.println("getCollissionDir(" + obj + ")");
			GameObjectXT firstCollider = (GameObjectXT)obj;//(GameObjectXT) ((ArrayList<?>) obj).get(0);
			double angle = collider.calculateCollisionAngle(this, firstCollider);
			if ((angle >= firstCollider.topAngleLL && angle < firstCollider.topAngleLR) || (angle >= firstCollider.topAngleRL && angle <= firstCollider.topAngleRR)) { // collision
				// top
			return TOP;
			}
			else if (angle >= firstCollider.bottomAngleR && angle <= firstCollider.bottomAngleL) { // collision bottom
			return BOTTOM;
			}
			else if (angle > firstCollider.rightAngleT && angle < firstCollider.rightAngleB) { // collision right
			return RIGHT;
			}
			else if (angle > firstCollider.leftAngleB && angle < firstCollider.leftAngleT) { // collision left
			return LEFT;
			}
		}
		else if(obj instanceof GameEngineXT)
		{
			System.out.println("getCollissionDir(" + obj + ")");
			GameEngineXT game = (GameEngineXT) obj;
			Rect firstCollider = game.getBoundingBox();
			String bounceDir = "";
			double angle = collider.calculateCollisionAngle(this, firstCollider);
			if ((angle >= game.topAngleLL && angle < game.topAngleLR) || (angle >= game.topAngleRL && angle <= game.topAngleRR)) { // collision
				bounceDir = "TOP";
				System.out.println(">>>>>>>>BOUNCED ON " + bounceDir + " SIDE OF OBJECT " + firstCollider);
				// top
				return TOP;
			}
			else if (angle >= game.bottomAngleR && angle <= game.bottomAngleL) { // collision bottom
				bounceDir = "BOTTOM";
				System.out.println(">>>>>>>>BOUNCED ON " + bounceDir + " SIDE OF OBJECT " + firstCollider);
				return BOTTOM;
			}
			else if (angle > game.rightAngleT && angle < game.rightAngleB) { // collision right
				bounceDir = "RIGHT";
				System.out.println(">>>>>>>>BOUNCED ON " + bounceDir + " SIDE OF OBJECT " + firstCollider);
				return RIGHT;
			}
			else if (angle > game.leftAngleB && angle < game.leftAngleT) { // collision left
				bounceDir = "LEFT";
				System.out.println(">>>>>>>>BOUNCED ON " + bounceDir + " SIDE OF OBJECT " + firstCollider);
				return LEFT;
			}
		}
//		else if(obj instanceof ArrayList<?>)
//		{
//			GameObjectXT firstCollider = (GameObjectXT) ((ArrayList<?>)obj).get(0);//(GameObjectXT) ((ArrayList<?>) obj).get(0);
//			double angle = collider.calculateCollisionAngle(this, firstCollider);
//			if ((angle >= firstCollider.topAngleLL && angle < firstCollider.topAngleLR) || (angle >= firstCollider.topAngleRL && angle <= firstCollider.topAngleRR)) { // collision
//				// top
//			return TOP;
//			}
//			if (angle >= firstCollider.bottomAngleR && angle <= firstCollider.bottomAngleL) { // collision bottom
//			return BOTTOM;
//			}
//			if (angle > firstCollider.rightAngleT && angle < firstCollider.rightAngleB) { // collision right
//			return RIGHT;
//			}
//			if (angle > firstCollider.leftAngleB && angle < firstCollider.leftAngleT) { // collision left
//			return LEFT;
//			}
//		}
		return -1;
	}

	/**
	 * 
	
	 * @param obj Object
	
	 * @return boolean */
	public boolean collidedWithHorizontal(Object obj)
	{
		int dir = this.getCollissionDirection(obj);
		pullFreeFromObject(dir, obj);
		System.out.println("pulled free");
		if(dir == TOP || dir == BOTTOM)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Method pullFreeFromObject.
	 * @param collissionSide int
	 * @param obj Object
	 */
	public void pullFreeFromObject(int collissionSide, Object obj)
	{
		double factor = 1;
		if(obj instanceof GameObjectXT)
		{
			GameObjectXT gameObj = (GameObjectXT) obj;
			if(obj instanceof MoveableGameObjectXT)
			{
				//not in use yet. In case of collission with another MGOXT need to know movement direction of other MGOXT
				//factor = 0.5;
			}
			
			double minPossibleXDistance = (((gameObj.getFrameWidth()/2) + (this.getFrameWidth()/2)) * factor) + 2;
			double minPossibleYDistance = (((gameObj.getFrameHeight()/2) + (this.getFrameHeight()/2)) * factor) + 2;
			//Different calculations for each different collissionSide of obj that this bounced on.
			if(collissionSide == TOP)
			{
				this.setY(gameObj.getFullY() - minPossibleYDistance);
			}
			else if(collissionSide == RIGHT)
			{
				this.setX(gameObj.getFullX() + minPossibleXDistance);
			}
			else if(collissionSide == BOTTOM)
			{
				this.setY(gameObj.getFullY() + minPossibleYDistance);
			}
			else if(collissionSide == LEFT)
			{
				this.setX(gameObj.getFullX() - minPossibleXDistance);
			}
		}
		else
		{
			
		}
	}
	
	/**
	 * Method bounceXT.
	 * @param horizontal boolean
	 */
	public void bounceXT(boolean horizontal)
	{
		if(!horizontal)
		{
			this.reverseVerticalDirection();
		}
		else
		{
			this.reverseHorizontalDirection();
		}
	}
	
	/**
	 * Method prevCollidedSame.
	 * @param obj Object
	
	 * @return boolean */
	public boolean prevCollidedSame(Object obj)
	{
		System.out.println("Comparing " + this.prevCollidedObject.toString() + " with " +obj.toString());
		if(this.prevCollidedObject.toString().equals(obj.toString()))
		{
			this.prevCollidedObject = obj;
			return true;
		}
		else
		{
			this.prevCollidedObject = obj;
			return false;
		}
	}
	
	/**
	 * Method getHitLoc.
	 * @param obj Object
	
	 * @return double[] */
	public double[] getHitLoc(Object obj)
	{
		double[] hitLoc = {0,0};
		if(obj instanceof GameObjectXT)
		{
			hitLoc[0] = this.getCenterX() - ((GameObjectXT)obj).getFullX();
			hitLoc[1] = this.getCenterY() - ((GameObjectXT)obj).getFullY();
			return hitLoc;
		}
		else
			return hitLoc;
	}
	
	/**
	 * 
	 */
	@Override
	public void update()
	{
		super.update();
		this.updateBoundingBox();
	}
}
