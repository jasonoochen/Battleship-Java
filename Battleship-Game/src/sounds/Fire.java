package sounds;

import sun.audio.AudioPlayer;

public class Fire extends Sounds{
	public Fire(){
		ConnectSoundFile("res/sounds/fire.wav");
	}
	public void playFireSound(){
		AudioPlayer.player.start(audio);
	}
}
