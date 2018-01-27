package sounds;

import sun.audio.AudioPlayer;

public class PressButton extends Sounds{
	public PressButton(){
		ConnectSoundFile("res/sounds/pressButton.wav");
	}
	public void playPressButtonSound(){
		AudioPlayer.player.start(audio);
	}
}
