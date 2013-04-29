/**
 * 
 */
package gameObjects;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import bb.main.GameEngineXT;

/**
 * Returns game to normal state after the timer for the power ups runs out.
 * @author Tim Wöstemeier
 *
 */
public class PowerUpController implements IAlarm {

	
	public Alarm alarm;
	public Ball ball;
	private GameEngineXT game;
	/**
	 * 
	 */
	public PowerUpController(GameEngineXT game) {
		// TODO Auto-generated constructor stub
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.alarms.IAlarm#alarmsActiveForThisObject()
	 */
	public boolean alarmsActiveForThisObject() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see android.gameengine.icadroids.alarms.IAlarm#triggerAlarm(int)
	 */
	public void triggerAlarm(int alarmID) {
		// TODO Auto-generated method stub
		for(Ball b: game.balls)
		{
			b.setSpeed(ball.game.ballBaseSpeed);
		}
	}

}
