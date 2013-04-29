package gameObjects;

/**
 * Basic block. Destroyed after being hit 1 time by a ball.
 * @author Tim Wostemeier, Juri de Vos
 * @version $Revision: 1.0 $
 */
public class BlockBasic extends Block {

	public BlockBasic() {
		// TODO Auto-generated constructor stub
		super("block_basic");
	}

	/**
	 * Constructor for BlockBasic.
	 * @param sprite String
	 */
	public BlockBasic(String sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for BlockBasic.
	 * @param sprite String
	 * @param locX double
	 * @param locY double
	 */
	public BlockBasic(String sprite, double locX, double locY) {
		super(sprite, locX, locY);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Method hitByBall.
	 * @param ball Ball
	 */
	@Override
	public void hitByBall(Ball ball)
	{
		super.hitByBall(ball);
		this.deleteThisGameObject();
	}
	
}
