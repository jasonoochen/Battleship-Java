package sounds;

import sun.audio.AudioPlayer;

public class ShipHit extends Sounds{
	public ShipHit(){
		ConnectSoundFile("res/sounds/shipHit.wav");
	}
	public void playShipHitSound(){
		AudioPlayer.player.start(audio);
	}
}
