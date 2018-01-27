package sounds;

import sun.audio.AudioPlayer;

public class Lose extends Sounds{
	public Lose(){
		ConnectSoundFile("res/sounds/lose.wav");
	}
	public void playLoseSound(){
		AudioPlayer.player.start(audio);
	}
}
