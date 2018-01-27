package sounds;

import sun.audio.AudioPlayer;

public class Welcome extends Sounds{
	public Welcome(){
		ConnectSoundFile("res/sounds/welcome.wav");
	}
	public void playWelcomeSound(){
		AudioPlayer.player.start(audio);
	}
}
