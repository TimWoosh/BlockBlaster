package android.gameengine.icadroids.sound;

import java.io.IOException;
import java.util.HashSet;

import android.gameengine.icadroids.engine.GameEngine;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

/**
 * This class features a number of static methods that can be used to add music
 * to your game.
 * 
 * This class should be used when you want to play long sound clips or
 * background music. For short soundclips, use the GameSound class.
 * 
 * @author Lex & Leon
 * 
 */
public final class MusicPlayer {

	private static HashSet<MediaPlayer> mpSet = new HashSet<MediaPlayer>();

	/**
	 * Plays the specified music.
	 * 
	 * @param resId
	 *            The name of the music that needs to played.
	 */
	public static void play(String resId, boolean loop) {
		try {
			MediaPlayer mp = new MediaPlayer();
			int resID = GameEngine.getAppContext().getResources()
					.getIdentifier(resId, "raw",
							GameEngine.getAppContext().getPackageName());
			mp.setDataSource(GameEngine.getAppContext(),
					Uri.parse("android.resource://android.GameAPI.ICA_DROID/"	+ resID));
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			if (loop) {
				mp.setLooping(true);
			} else {
				mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						mpSet.remove(mp);
						mp.stop();
						mp.release();
					}
				});
			}
			mp.prepare();
			mpSet.add(mp);
			mp.start();
		} catch (IOException e) {
			Log.wtf("MusicPlayer", e);
		}
	}

	/**
	 * Stop playing music.
	 */
	public static void stop() {
		for (MediaPlayer mp : mpSet) {
			if (mp != null) {
				mp.stop();
				mp.release();
			}
		}
		mpSet.clear();
	}

	/**
	 * Pauses all playing music, call resumeAll to continue playing music.
	 */
	public static void pauseAll() {
		for (MediaPlayer mp : mpSet) {
			if (mp != null) {
				mp.pause();
			}
		}
	}

	/**
	 * Resumes playing all paused music.
	 */
	public static void resumeAll() {
		for (MediaPlayer mp : mpSet) {
			if (mp != null) {
				mp.start();
			}
		}
	}
}