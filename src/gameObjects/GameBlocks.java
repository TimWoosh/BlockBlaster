/**
 * 
 */
package gameObjects;

import bb.main.GameEngineXT;


/**
 * Stores all game blocks in a game.
 * @author Tim Wostemeier
 * 
 * @version $Revision: 1.0 $
 */
public class GameBlocks {
	
	/**
	 * Contains the location and type id of each block
	 * The map has to be at least 10 wide and 1 high (1 row)
	 * Used by creatMap()
	 */
	private int[][] map;
	/**
	 * Contains the actual GameObjects that are placed in the game
	 * Created by creatMap()
	 */
	private Block[][] blocks;
	/**
	 * defines the width of a block 
	 */
	private double blockWidth;
	private double blockHeight;
	private int numRows;
	private GameEngineXT game;
	
	/**
	 * 0
	 */
	public static final int BLOCK_BASIC = 0;
	/**
	 * 1
	 */
	public static final int BLOCK_TOUGH = 1;
	/**
	 * 2
	 */
	public static final int BLOCK_POWERUP = 2;
	
	
	/**
	 * Constructor for GameBlocks
	 */
	public GameBlocks() {
		
	}

	/**
	 * Constructor overload for GameBlocks
	 * @param map int[][]
	 */
	public GameBlocks(int[][] map) {
		this.setMap(map);
	}
	
	/**
	 * Constructor overload for GameBlocks
	 * @param blockWidth
	 * @param blockHeight
	 * @param game GameEngineXT
	 */
	public GameBlocks(GameEngineXT game, double blockWidth, double blockHeight)
	{
		this.game = game;
		this.setBlockDimensions(blockWidth, blockHeight);
	}
	
	/**
	 * Method setMap
	 * @param map
	 * @return void
	 */
	public void setMap(int[][] map)
	{
		this.map = map;
		this.setNumRows(this.map);
	}
	
	/**
	 * 
	 * @param map
	 */
	public void setNumRows(int[][] map)
	{
		this.numRows = map.length;
	}
	
	/**
	 * Not used
	 * @param blockTypes
	 */
	public void setBlockTypes(int[] blockTypes)
	{
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void setBlockDimensions(double width, double height)
	{
		this.blockWidth = width;
		this.blockHeight = height;
	}
	
	/**
	 * Returns Blocks of predefined types. See static fields for possible arguments and returns.
	 * 
	 * 
	 * @param blockType
	
	
	 * @return Block */
	public Block getBlockFromType(int blockType)
	{
		System.out.println("calling GameBlocks.getBlock(" + blockType + ")");
		
		switch(blockType)
		{
			case BLOCK_BASIC: 
				return new BlockBasic();
				
			case BLOCK_TOUGH:
				return new BlockTough();
			
			case BLOCK_POWERUP:
				return new BlockPowerUp();
				
			case -1:
				return null; //mogelijk (vrij zeker) overbodig
		}
		return null;
	}
	
	/**
	 * returns the block at location x, y (location in the 2d "blocks" array, not in pixels)
	 * @param x int
	 * @param y int
	
	 * @return Block */
	public Block getBlockByLoc(int x, int y)
	{
		return this.blocks[y][x];
	}
	
	private void initBlocksArray()
	{
		blocks = new Block[numRows][10];
	}
	
	/**
	 * Create and place actual blocks on the playing field.
	 * Needs the following attributes to be initialized and/or set:
	 * -this.map //location of the blocks
	 * -this.blocks //container for all the blocks
	 * -this.game (.getLeft(), .getTop()) //x and y playing area offset
	 * -this.numRows //Number of rows of blocks
	 * -this.blockHeight and this.blockWidth //block dimensions
	 */
	public void createBlocks()
	{
		//System.out.println("calling GameBlocks.createMap(). numRows = " + this.numRows);
		//Width and height of 1 block, for calculating block positions
		double w = this.blockWidth;
		double h = this.blockHeight;
		//the x and y of the left top corner of the playing field. Used as offset for placing blocks at correct location
		double origX = game.getLeft();
		double origY = game.getTop();
		//initialize this.blocks to avoid nullpointer exceptions
		this.initBlocksArray();

		for(int i = 0; i < this.numRows; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				this.blocks[i][j] = this.getBlockFromType(this.map[i][j]);
				/*
				 * Only if not null, perform operations on the block
				 */
				if(this.blocks[i][j] != null)
				{
					//Calculate block position including offsets
					double blockX = j*w + origX;
					double blockY = i*h + origY;
					
					this.blocks[i][j].setPosition(blockX, blockY);
					//Initialize the bounding box for this Block, used for collission calculations 
					this.blocks[i][j].setBoundingBox();
					//publish object to the game
					this.game.addGameObject(this.blocks[i][j]);
				}
				else
				{
					//is null! No block on this position. Do nothing
				}
			}
		}
	}
}
