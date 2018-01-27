package sounds;

import sun.audio.AudioPlayer;

public class FailedToPlaceShip extends Sounds{
	public FailedToPlaceShip(){
		ConnectSoundFile("res/sounds/failedToPlaceShip.wav");
	}
	public void playFailedToPlaceShipSound(){
		AudioPlayer.player.start(audio);
	}
}
