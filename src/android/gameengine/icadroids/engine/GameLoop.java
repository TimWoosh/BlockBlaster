package android.gameengine.icadroids.engine;

import android.gameengine.icadroids.renderer.GameView;
import android.graphics.Canvas;
import android.util.Log;

/**
 * GameLoop is the Thread that handles the timing of the GameLogic and the
 * drawing.
 * 
 * @author Edward & Bas
 * 
 */
public class GameLoop implements Runnable {

	/**
	 * The view to draw on
	 */
	private GameView view;
	/**
	 * The currently running GameEngine
	 */
	private GameEngine gameEngine;
	/**
	 * running is true when the gameloop is running, false otherwise
	 */
	private boolean running = false;
	/**
	 * Max FPS of this Game
	 */
	public static int MAX_FPS = 30;

	/**
	 * Registers the game FPS
	 */
	private GameFPSCounter fps = new GameFPSCounter();

	/**
	 * Canvas that needs to be drawn
	 */
	public Canvas c;

	/**
	 * Intialize the Gameloop
	 * 
	 * @param ge
	 *            GameEngine which the game is currently running on
	 */
	protected GameLoop(GameEngine ge) {
		super();
		Log.d("GameLoop", "Initialising...");
		gameEngine = ge;
	}

	/**
	 * Start or stop the gameloop from running
	 * 
	 * @param running
	 *            true to start, false to stop
	 */
	public final void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Get the current system time in milliseconds
	 * 
	 * @return The current time in milliseconds
	 */
	public final long getCurrentSystemTime() {
		return System.currentTimeMillis();
	}

	/***
	 * Main game-loop thread that handles gamelogic and rendering.
	 */
	public void run() {
		long ticksPS = 1000 / MAX_FPS;
		long startTime;
		long sleepTime;
		gameEngine.initialize();
		gameEngine.intializeTouch();
		while (running) {
			fps.logFrame("Render");
			startTime = getCurrentSystemTime();
			updateGame();
			updateEngine();
			sleepTime = ticksPS - (getCurrentSystemTime() - startTime);
			try {
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (InterruptedException e) {
				Log.wtf("GameLoop", "loop interupted", e);
			}
		}
	}

	/**
	 * Request the device to draw
	 */
	private void updateEngine() {
		try {
			startDraw();
			synchronized (view.getHolder()) {
				view.onDraw(c);
			}
		} finally {
			if (c != null) {
				endDraw(c);
			}
		}
	}

	/**
	 * Start drawing on the canvas
	 */
	public final void startDraw() {
		c = view.getHolder().lockCanvas();
	}

	/**
	 * Stop Drawing
	 * 
	 * @param c
	 *            Canvas that needs to be stop drawing
	 */
	public final void endDraw(Canvas c) {
		view.getHolder().unlockCanvasAndPost(c);
	}

	/**
	 * Allows the game to run logic such as updating the world, checking for
	 * collisions, gathering input, and playing audio.
	 */
	protected void updateGame() {
		if (!GameEngine.UPDATE_LOOP_ON) {
			gameEngine.updateGame();
		}
	}

	/**
	 * Set the view which the GameLoop draws on
	 * 
	 * @param view
	 */
	public final void setView(GameView view) {
		this.view = view;
	}

	/**
	 * Get the running staat of the GameLoop Thread
	 * 
	 * @return True when running, false when stopped
	 */
	public final boolean getRunningState() {
		return running;
	}

}
