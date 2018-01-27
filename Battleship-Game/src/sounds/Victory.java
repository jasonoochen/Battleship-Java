package sounds;

import sun.audio.AudioPlayer;

public class Victory extends Sounds {
	public Victory(){
		ConnectSoundFile("res/sounds/victory.wav");
	}
	public void playVictorySound(){
		AudioPlayer.player.start(audio);
	}
}
