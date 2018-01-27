package sounds;

import sun.audio.AudioPlayer;

public class MouseMoving extends Sounds {
	public MouseMoving(){
		ConnectSoundFile("res/sounds/mousemoving.wav");
	}
	public void playMouseMovingSound(){
		AudioPlayer.player.start(audio);
	}
}
