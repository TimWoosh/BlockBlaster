package gameObjects;


/**
 * Game block "Mother" class
 * @author Tim Wostemeier, Juri de Vos
 * @version $Revision: 1.0 $
 */
public class Block extends GameObjectXT{
	
	private String sprite = "block_basic";
	public Block()
	{
		super();
		this.setSprite(sprite);
		this.setDirectionAngles();
		System.out.println("Block()...");
	}
	
	/**
	 * Constructor overload for Block.
	 * @param sprite String
	 */
	public Block(String sprite)
	{
		super();
		this.setSprite(sprite);
		this.setDirectionAngles();
		System.out.println("Block(String sprite)...");
	}
	
	/**
	 * Constructor overload for Block.
	 * @param sprite String
	 * @param locX double
	 * @param locY double
	 */
	public Block(String sprite, double locX, double locY)
	{
		this(sprite);
		this.setX(locX);
		this.setY(locY);
		System.out.println("Block(String sprite, double locX, double locY)...");
	}
	
	/**
	 * Method hitByBall.
	 * @param ball Ball
	 */
	public void hitByBall(Ball ball)
	{
		ball.game.score.updateScore(this);
	}
}
