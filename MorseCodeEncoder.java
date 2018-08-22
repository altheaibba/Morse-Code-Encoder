import java.util.*;
import javax.sound.sampled.*;

public class MorseCodeEncoder {
	
	public static void main (String [] args) throws LineUnavailableException {
		char [] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
				'r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9',
				',','.','?',' '};
		
		String [] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", 
                ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
                "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
                "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
                "-----", "--..--", ".-.-.-", "..--..", "/"};
		
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine(); 
		String s = input.toLowerCase();
		char [] c = s.toCharArray();
		
		String res = "";
		for(int i = 0; i<c.length; i++)
		{
			for(int j = 0; j<alphabet.length; j++)
			{
				if(alphabet[j]==c[i])
				{
					res = res+morse[j] + " ";
				}
			}
		}
		
		System.out.println(res);
		
		play(res);
	}
	
	public static void tone(int hz, int msecs, double vol) throws LineUnavailableException {
		float SAMPLE_RATE = 8000f;
		byte[] buf = new byte[1];
		AudioFormat af = new AudioFormat(SAMPLE_RATE,8,1,true,false);
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		for(int i = 0; i<msecs*8; i++)
		{
			double angle = i/(SAMPLE_RATE/hz)*2.0*Math.PI;
			buf[0] = (byte)(Math.sin(angle)*127.0*vol);
			sdl.write(buf,0,1);
		}
		sdl.drain();
		sdl.stop();
		sdl.close();
	}
	
	public static void play(String res) throws LineUnavailableException {
		int oneUnit = 240;
		int dashLength = 3;
		int dotLength = 1;
		
		for(int i = 0; i<res.length(); i++)
		{
			char c = res.charAt(i);
			
			if(c == '-')
			{
				tone(600,oneUnit*dashLength,0.5);
			}
			else if(c == '.')
			{
				tone(600,oneUnit*dotLength,0.5);
			}
		}
	}
	
}
