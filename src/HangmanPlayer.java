import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class HangmanPlayer {
	
	static Scanner myScan = new Scanner(System.in);
	
	public static Hangman buildHangman() throws URISyntaxException {
		System.out.println("Set the word: ");
		String tempWord = myScan.next().toUpperCase();
		return new Hangman(tempWord, 6);	
	}
	
	
	public static void main(final String[] args) throws URISyntaxException {
		Hangman game = buildHangman();
		
		
	}
}