package sounds;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioStream;

public class Sounds {
	InputStream in;
	AudioStream audio;
	String directory;

	public Sounds(){
		in = null;
		audio = null;
		directory=null;
	}
	public void ConnectSoundFile(String directory){
	try {
			in = new FileInputStream(directory);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				audio = new AudioStream(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
