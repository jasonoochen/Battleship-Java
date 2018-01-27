package sounds;

import sun.audio.AudioPlayer;

public class UnitLost extends Sounds{
	public UnitLost(){
		ConnectSoundFile("res/sounds/unitLost.wav");
	}
	public void playUnitLostSound(){
		AudioPlayer.player.start(audio);
	}
}
