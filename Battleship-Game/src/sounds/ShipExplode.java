package sounds;

import sun.audio.AudioPlayer;

public class ShipExplode extends Sounds{
	public ShipExplode(){
		ConnectSoundFile("res/sounds/shipExplode.wav");
	}
	public void playShipExplodeSound(){
		AudioPlayer.player.start(audio);
	}
}
