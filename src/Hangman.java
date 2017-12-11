
/** A class that implements a hang-man game*/
public class Hangman { 
	
	/** The word set by player 1 that player 2 is trying to guess */
	private String guessWord;
	
	/** The number of guesses used by player 2 */
	private int guessCount = 0;
	
	/** States whether game has ended */
	private boolean gameOver = false;
	
	/** The maximum number of guesses available to player 2*/
	private int maxGuesses;
	
	/** 
	 * Create a new Hangman game with the given word and
	 *  maximum number of guesses. 
	 *  
	 *  @param word the word for the new Hangman game
	 *  @param max the number of guesses for the game
	 *  */
	public Hangman(String word, int max) {
		this.guessWord = word;
		this.maxGuesses = max;
	}
	
	/**
	 * @return the guessword.
	 */
	public String getGuessWord() {
		return this.guessWord;
	}
	
	/**
	 * @return the number of guesses used. 
	 */
	public int getGuessCount() {
		return this.guessCount;
	}
	
	/**
	 * @return whether game has ended. 
	 */
	public boolean getGameStatus() { 
		return this.gameOver;
	}
	
	/**
	 * @return the maximum number of guesses. 
	 */
	public int getMaxGuesses() {
		return this.maxGuesses;
	}
	
	/**
	 * Sets gameOver to true if the max number of guesses have been used. 
	 */
	public void updateGameStatus(boolean b) {
		if (guessCount >= maxGuesses) {
			this.gameOver = true;
			System.out.println("Well poo. You're out of guesses. \n The word was " 
			+ guessWord +  ".\n Better luck next time!");
		} else if (b == true) {
			this.gameOver = true;
			System.out.println("You guessed the word! Congrats!");
		}
	}
	
	/** 
	 * Guess method for individual letters. 
	 * 
	 * @param str - player 2's guess
	 * @return true if str is in guessword, false otherwise. 
	 */
	public boolean guess(String str) {
		if (guessWord.contains(str)) {
			return true;
		}
		this.guessCount++;
		return false;
	}	
}
