package gameObjects;

/**
 * Tough block. Destroyed after being hit 2 times by a ball.
 * @author Tim Wostemeier, Juri de Vos
 * @version $Revision: 1.0 $
 */
public class BlockTough extends Block {
	
	public int numHitPoints = 2;

	public BlockTough() {
		// TODO Auto-generated constructor stub
		super("block_tough");
	}
	
	/**
	 * Method hitByBall.
	 * @param ball Ball
	 */
	@Override
	public void hitByBall(Ball ball)
	{
		numHitPoints--;
		if(numHitPoints <= 0)
		{
			super.hitByBall(ball);
			deleteThisGameObject();
		}
		this.setSprite("block_basic");
		
	}
}
