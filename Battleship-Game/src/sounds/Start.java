package sounds;

import sun.audio.AudioPlayer;

public class Start extends Sounds {
	public Start(){
		ConnectSoundFile("res/sounds/start.wav");
	}
	public void playStartSound(){
		AudioPlayer.player.start(audio);
	}
}
