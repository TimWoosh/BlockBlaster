package gameObjects;


/**
 * Power up block. Destroyed after being hit 1 time by a ball. Releases a random power up upon destruction.
 * @author Tim Wostemeier, Juri de Vos
 * @version $Revision: 1.0 $
 */
public class BlockPowerUp extends Block {

	public BlockPowerUp() {
		// TODO Auto-generated constructor stub
		super("block_powerup");
	}
	
	/**
	 * Method hitByBall.
	 * @param ball Ball
	 */
	@Override
	public void hitByBall(Ball ball)
	{
		super.hitByBall(ball);
		PowerUp powerUp = new PowerUp(ball, this.getCenterX(), this.getCenterY());
		powerUp.setDirectionSpeed(180, 4);
		powerUp.setBoundingBox();
		ball.game.addGameObject(powerUp);
		deleteThisGameObject();
	}

}
