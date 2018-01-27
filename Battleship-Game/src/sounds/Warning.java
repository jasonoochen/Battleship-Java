package sounds;

import sun.audio.AudioPlayer;

public class Warning extends Sounds {
	public Warning(){
		ConnectSoundFile("res/sounds/warning.wav");
	}
	public void playWarningSound(){
		AudioPlayer.player.start(audio);
	}
}
