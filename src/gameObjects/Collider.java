package gameObjects;

import java.util.ArrayList;

import android.graphics.Rect;
import bb.main.GameEngineXT;

/**
 * Object for collision calculations
 * @author Tim Wostemeier
 * 
 * @version $Revision: 1.0 $
 */

public class Collider{
	
	private GameEngineXT game;
	private double thisX;
	private double thisY;
	
	private double otherX;
	private double otherY;
	
	/**
	 * 
	 * @param game
	 */
	public Collider(GameEngineXT game) {
		// TODO Auto-generated constructor stub
		this.game = game;
	}
	
	/**
	 * 
	 * @param thisObject
	 * @param otherObject
	
	
	 * @return double */
	public double calculateCollisionAngle(MoveableGameObjectXT thisObject, Object otherObject)
	{
		double[] thisCenter;
		double[] otherCenter;
		if(!(otherObject instanceof Rect))
		{
			thisCenter = this.calcObjectCenter(thisObject);
			otherCenter = this.calcObjectCenter(otherObject);
		}
		else //if(otherObject instanceof Rect)
		{
			thisCenter = this.calcObjectRectCenter(thisObject);
			otherCenter = this.calcObjectRectCenter(otherObject);
		}
		this.thisX = thisCenter[0];
		this.thisY = thisCenter[1];

		this.otherX = otherCenter[0];
		this.otherY = otherCenter[1];
		
		double calcAngle = calcAngle();
		System.out.println("###The angle between " +otherObject+ " and " +thisObject+ " is " +calcAngle+ " degrees!###");
		return calcAngle;
	}
	
	
	/**
	 * 
	
	
	
	
	
	
	 * @return double */
	private double calcAngle()
	{
		double deltaX = thisX - otherX;
		double deltaY = thisY - otherY;
		
		if (deltaX >= 0 || deltaY >= 0) {
			return Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90;
		} else {
			return Math.toDegrees((Math.atan2(deltaY, deltaX))) + 450;
		}
	}
	
	
	/**
	 * Calculates the absolute center of Object obj.
	 * @param obj
	
	 * @return double[0] = x; double[1] = y; */
	public double[] calcObjectCenter(Object obj)
	{
		System.out.println("++++++++\ncalcObjectCenter(" +obj+ ") ->obj is of type: " + obj.getClass() + "\n+++++++++");
		double x = 0;
		double y = 0;
		double[] d = new double[2];
		if(obj instanceof GameObjectXT || obj instanceof MoveableGameObjectXT)
		{
			//Waarschijnlijk cast van GameObjectXT voldoende
			if(obj instanceof MoveableGameObjectXT)
			{
				System.out.println("#The object's speed is: " +((MoveableGameObjectXT)obj).getSpeed() + "#");
				System.out.println("#The object's direction is: " +((MoveableGameObjectXT)obj).getDirection() + "#");
				
			}
//			else
//			{
//				x = ((GameObjectXT)obj).getCenterX();
//				y = ((GameObjectXT)obj).getCenterY();
////				x = ((GameObjectXT)obj).getSpace().exactCenterX();
////				y = ((GameObjectXT)obj).getSpace().exactCenterY();
//			}
			x = ((GameObjectXT)obj).getCenterX();
			y = ((GameObjectXT)obj).getCenterY();

			System.out.println("#Object left top is located at: " +((GameObjectXT)obj).getFullX()+ ", " +((GameObjectXT)obj).getFullY() + "#");
			System.out.println("#Object size is: " +((GameObjectXT)obj).getFrameWidth()+ ", " +((GameObjectXT)obj).getFrameHeight() + "#");
			System.out.println("#Object's Rect's left top is located at: " +((GameObjectXT)obj).getBoundingBox().left+ ", " +((GameObjectXT)obj).getBoundingBox().top + " This is different from Object left top: " +(((GameObjectXT)obj).getBoundingBox().left-((GameObjectXT)obj).getFullX())+ ", " +((((GameObjectXT)obj).getBoundingBox().top)-((GameObjectXT)obj).getFullY()));
			System.out.println("#Object's Rect's size is: " +((GameObjectXT)obj).getBoundingBox().width()+ " x " +((GameObjectXT)obj).getBoundingBox().height() + "#");
			System.out.println("#Object center is located at: " +((GameObjectXT)obj).getCenterX()+ ", " +((GameObjectXT)obj).getCenterY() + "#");
		}
		System.out.println("#Object Rect's center is located at: " +x+ ", " +y + "#");
		d[0] = x;
		d[1] = y;
		return d;
	}
	
	/**
	 * Method calcObjectRectCenter.
	 * @param obj Object
	
	 * @return double[] */
	public double[] calcObjectRectCenter(Object obj)
	{
		System.out.println("++++++++\ncalcObjectRectCenter(" +obj+ ") ->obj is of type: " + obj.getClass() + "\n+++++++++");
		double x = 0;
		double y = 0;
		double[] d = new double[2];
		if(obj instanceof GameObjectXT || obj instanceof MoveableGameObjectXT)
		{
			//Waarschijnlijk cast van GameObjectXT voldoende
			if(obj instanceof MoveableGameObjectXT)
			{
				System.out.println("#The object's speed is: " +((MoveableGameObjectXT)obj).getSpeed() + "#");
				System.out.println("#The object's direction is: " +((MoveableGameObjectXT)obj).getDirection() + "#");
				
			}
//			else
//			{
//				x = ((GameObjectXT)obj).getCenterX();
//				y = ((GameObjectXT)obj).getCenterY();
////				x = ((GameObjectXT)obj).getSpace().exactCenterX();
////				y = ((GameObjectXT)obj).getSpace().exactCenterY();
//			}
			x = ((GameObjectXT)obj).getBoundingBox().exactCenterX();
			y = ((GameObjectXT)obj).getBoundingBox().exactCenterY();

			System.out.println("#Object left top is located at: " +((GameObjectXT)obj).getFullX()+ ", " +((GameObjectXT)obj).getFullY() + "#");
			System.out.println("#Object size is: " +((GameObjectXT)obj).getFrameWidth()+ ", " +((GameObjectXT)obj).getFrameHeight() + "#");
			System.out.println("#Object's Rect's left top is located at: " +((GameObjectXT)obj).getBoundingBox().left+ ", " +((GameObjectXT)obj).getBoundingBox().top + " This is different from Object left top: " +(((GameObjectXT)obj).getBoundingBox().left-((GameObjectXT)obj).getFullX())+ ", " +((((GameObjectXT)obj).getBoundingBox().top)-((GameObjectXT)obj).getFullY()));
			System.out.println("#Object's Rect's size is: " +((GameObjectXT)obj).getBoundingBox().width()+ " x " +((GameObjectXT)obj).getBoundingBox().height() + "#");
			System.out.println("#Object center is located at: " +((GameObjectXT)obj).getCenterX()+ ", " +((GameObjectXT)obj).getCenterY() + "#");
		}
		else if(obj instanceof Rect)
		{
			x = game.getBoundingBox().exactCenterX();
			y = game.getBoundingBox().exactCenterY();
		}
		System.out.println("#Object Rect's center is located at: " +x+ ", " +y + "#");
		d[0] = x;
		d[1] = y;
		return d;
	}
	
	/**
	 * Method distanceBetweenObjects.
	 * @param thisX double
	 * @param thisY double
	 * @param otherX double
	 * @param otherY double
	
	 * @return double */
	public double distanceBetweenObjects(double thisX, double thisY, double otherX, double otherY)
	{
		double deltaX = Math.abs(thisX - otherX);
		double deltaY = Math.abs(thisY - otherY);
		
		return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
	}
	
	/**
	 * Method closestCollider.
	 * @param collidingObjects ArrayList<?>
	
	 * @return Object */
	public Object closestCollider(ArrayList<?> collidingObjects)
	{	
		double[] otherObjCenter = this.calcObjectCenter(collidingObjects.get(0));
		double closestDistance = distanceBetweenObjects(this.thisX, this.thisY, otherObjCenter[0], otherObjCenter[1]);
		double distance = 0;
		Object closestObject = collidingObjects.get(0);
		for(Object obj: collidingObjects)
		{
			if(obj instanceof GameObjectXT)
			{
				otherObjCenter = this.calcObjectCenter(obj);
				distance = distanceBetweenObjects(this.thisX, this.thisY, otherObjCenter[0], otherObjCenter[1]);
				if(distance < closestDistance)
				{
					closestDistance = distance;
					closestObject = obj;
				}
			}
		}
		return closestObject;
	}
	/**
	 * 
	
	
	 * @return double */
	public double getOtherX()
	{
		return otherX;
	}
	
	/**
	 * 
	
	
	 * @return double */
	public double getOtherY()
	{
		return otherY;
	}
	
	/**
	 * 
	
	
	 * @return double */
	public double getThisX()
	{
		return thisX;
	}
	
	/**
	 * 
	
	
	 * @return double */
	public double getThisY()
	{
		return thisY;
	}
}
