package sounds;

import sun.audio.AudioPlayer;

public class ShipPlacing extends Sounds {
	public ShipPlacing(){
		ConnectSoundFile("res/sounds/placeship.wav");
	}
	public void playShipPlacingSound(){
		AudioPlayer.player.start(audio);
	}
}
