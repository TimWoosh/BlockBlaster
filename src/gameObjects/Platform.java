package gameObjects;

import android.gameengine.icadroids.input.TouchInput;
import bb.main.GameEngineXT;

/**
 * Player controlled platform game object for directing the balls.
 * @author Tim Wöstemeier
 * @version $Revision: 1.0 $
 */
public class Platform extends MoveableGameObjectXT {

	private double halfWidth;
	private String sprite = "platform";
	/**
	 * Constructor for Platform.
	 * @param game GameEngineXT
	 */
	public Platform(GameEngineXT game) {
		super(game);
		this.setSprite(sprite);
		// TODO Auto-generated constructor stub
		halfWidth = this.getFrameWidth()/2;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		this.updateBoundingBox();
		
		if(TouchInput.onPress)
		{
			double x = TouchInput.xPos;
			
			if(x < (game.getBoundingBox().left + halfWidth))
			{
				x = (game.getBoundingBox().left + halfWidth);
			}
			else if (x > (game.getBoundingBox().right - halfWidth))
			{
				x = (game.getBoundingBox().right - halfWidth);
			}
			this.setX(x - halfWidth);
			System.out.println("Pressed on the Screen: (" +TouchInput.xPos+","+TouchInput.yPos+")");
		}
	}
	
	/**
	 * Method getAngleModifier.
	 * @param hitLoc double
	 * @param angle double
	
	 * @return double */
	public double getAngleModifier(double hitLoc, double angle)
	{
		double modAngle = 0;
		double width = this.getFrameWidth();
		double center = width/2;
		double distFromCenter = 0;
		
		distFromCenter = hitLoc - center;

		double locPart = (distFromCenter/center);
		modAngle = 70 * locPart;
		double newAngle = (modAngle + angle)%360;
		
		if(newAngle  < 290 && newAngle >= 180)
		{
			newAngle = 290;
		}
		else if (newAngle > 70 && newAngle < 180)
		{
			newAngle = 70;
		}
		
		
		return newAngle;
	}
}
