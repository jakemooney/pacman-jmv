import java.applet.*; 
import java.net.*; 
import java.io.File;

//http://www.cs.cmu.edu/~illah/CLASSDOCS/javasound.pdf

public class Sound {
	
	private String url;
	
	public Sound(String path){
		this.url = path;
	}
	
	public void play(){
		try {
			URL Url = new URL(url);
			System.out.println(url);
			AudioClip clip = Applet.newAudioClip("file:" + Url);				//c:/blah/foo.wav
			clip.play(); 
			
			System.out.print("why doesnt it play");
		
		}catch (MalformedURLException murle) {
			System.out.println(murle);
		}
	}
	




}
