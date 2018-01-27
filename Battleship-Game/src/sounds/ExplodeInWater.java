package sounds;

import sun.audio.AudioPlayer;

public class ExplodeInWater extends Sounds {
	public ExplodeInWater(){
		ConnectSoundFile("res/sounds/explodeInWater.wav");
	}
	public void playExplodeInWaterSound(){
		AudioPlayer.player.start(audio);
	}
}
