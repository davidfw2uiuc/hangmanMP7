import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.*;

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
	
	private JTextField guessField = new JTextField(15);
	private JFrame popUp = new JFrame();
	private JLabel word;
	private JLabel guessed;
	public String curGuessed = "";
	private JLabel numGuessesLabel = new JLabel("6");
	
	
	
	/** 
	 * Create a new Hangman game with the given word and
	 *  maximum number of guesses. 
	 *  
	 *  @param word the word for the new Hangman game
	 *  @param max the number of guesses for the game
	 * @throws URISyntaxException 
	 *  */
	
	
	public Hangman(String word, int max) throws URISyntaxException {
		this.guessWord = word;
		this.maxGuesses = max;
		setUpWindow();
	}
	
	public void setUpWindow() throws URISyntaxException {
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		for (int i = 0; i < guessWord.length(); i++) {
			curGuessed += "_ ";
		}
		
		word = new JLabel(curGuessed);
		word.setHorizontalAlignment(SwingConstants.CENTER);
		word.setFont(new Font("default", Font.BOLD,24));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(50,0,50,0);
		gbc.gridwidth = 2;
		f.add(word,gbc);
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		
		JLabel guessText = new JLabel("Guess: ");
		guessText.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.insets = new Insets(0,10,10,10);
		
		f.add(guessText,gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.insets = new Insets(0,0,10,20);
		f.add(guessField,gbc);
		
		JLabel guessedLabel = new JLabel("Guesses: ");
		guessedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.insets = new Insets(0,10,5,10);
		
		f.add(guessedLabel,gbc);
		
		guessed = new JLabel("No Guesses Yet");
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.insets = new Insets(0,0,5,10);
		f.add(guessed,gbc);
		
		JLabel guessesLabel = new JLabel("Guesses Left:");
		guessesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(0,10,10,10);
		
		f.add(guessesLabel, gbc);
		
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.insets = new Insets(0,0,10,10);
		
		f.add(numGuessesLabel, gbc);
		
		JButton button = new JButton("Enter Guess");
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 10;
		button.addActionListener(new GuessHandler());
		gbc.insets = new Insets(0,10,10,10);
		
		f.add(button, gbc);
		
		
		
		f.pack();
	}
	
	
	public void updateCurGuessed() {
		word.setText(curGuessed);
	}
	
	public void updateNumGuesses() {
		numGuessesLabel.setText("" + (getMaxGuesses() - getGuessCount()));
	}
	
	public void losingScreen() {
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new GridBagLayout());
		f.setPreferredSize(new Dimension (250,100));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel title = new JLabel("You Lose!");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("default", Font.BOLD,12));
		title.setPreferredSize(new Dimension(250,25));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		f.add(title,gbc);
		
		JLabel message = new JLabel("The word was " + guessWord);
		gbc.gridx = 0;
		gbc.gridy = 1;
		f.add(message,gbc);
		
		JButton ok = new JButton(":(");
		ok.addActionListener(new EndListener());
		ok.setPreferredSize(new Dimension(100,25));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10,0,0,0);
		f.add(ok,gbc);
		
		f.pack();
		
	}
	
	public void winningScreen() {
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new GridBagLayout());
		f.setPreferredSize(new Dimension (250,100));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel title = new JLabel("You Win!");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("default", Font.BOLD,12));
		title.setPreferredSize(new Dimension(250,25));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		f.add(title,gbc);
		
		JLabel message = new JLabel("The word was " + guessWord);
		gbc.gridx = 0;
		gbc.gridy = 1;
		f.add(message,gbc);
		
		JButton ok = new JButton(":)");
		ok.addActionListener(new EndListener());
		ok.setPreferredSize(new Dimension(100,25));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10,0,0,0);
		f.add(ok,gbc);
		
		f.pack();
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
			winningScreen();
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
		} else if (!guessed.getText().contains(str)) {
			this.guessCount++;
		}
		
		
		if (guessCount >= maxGuesses) {
			losingScreen();
		}
		
		return false;
	}
	
	public boolean displayAndUpdate(String guessedLetters) {
		boolean word_complete = true;
		String currentLetter = "";
		curGuessed = "";
		System.out.println("You have " + (getMaxGuesses() - getGuessCount()) + " strikes remaining.");
		for (int i = 0; i < guessWord.length(); i++) {
			currentLetter = guessWord.toUpperCase().substring(i, i + 1);
			if (guessedLetters.indexOf(currentLetter) != -1) {
				curGuessed += (currentLetter + " ");
			} else {
				curGuessed += ("_ ");
				word_complete = false;
			}
		}
		if (word_complete) {
			updateGameStatus(true);		
		} else {
			updateGameStatus(false);
		}
		updateCurGuessed();
		return getGameStatus();
	}
	
	private class GuessHandler implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if (guessField.getText().length() != 1) {
				
				popUp.setVisible(true);
				popUp.setResizable(false);
				popUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				popUp.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
					
				JLabel title = new JLabel("Invalid guess");
				title.setHorizontalAlignment(SwingConstants.CENTER);
				title.setFont(new Font("default", Font.BOLD,12));
				title.setPreferredSize(new Dimension(250,25));
				gbc.gridx = 0;
				gbc.gridy = 0;
				popUp.add(title,gbc);
				
				JLabel message = new JLabel("Please enter a single character");
				gbc.gridx = 0;
				gbc.gridy = 1;
				popUp.add(message,gbc);
				
				JButton ok = new JButton("OK");
				ok.addActionListener(new CloseListener());
				ok.setPreferredSize(new Dimension(100,25));
				gbc.gridx = 0;
				gbc.gridy = 2;
				gbc.insets = new Insets(10,0,0,0);
				popUp.add(ok,gbc);
				
				
				popUp.setSize(250, 100);
				popUp.pack();
			} else {
				if (!guess(guessField.getText().toUpperCase())) {
					if (guessed.getText().equals("No Guesses Yet")) {
						guessed.setText(guessField.getText().toUpperCase());
					} else {
						if (!guessed.getText().contains(guessField.getText().toUpperCase())) {
							guessed.setText(guessed.getText() + " " + guessField.getText().toUpperCase());
						}
					}
				} else {
					if (guessed.getText().equals("No Guesses Yet")) {
						guessed.setText(guessField.getText().toUpperCase());
						displayAndUpdate(guessed.getText());
					} else {
						if (!guessed.getText().contains(guessField.getText().toUpperCase())) {
							guessed.setText(guessed.getText() + " " + guessField.getText().toUpperCase());
							displayAndUpdate(guessed.getText());
						}
					}
				}
				updateNumGuesses();
			}
			
			guessField.setText("");
		}
		
		
		
	}
	
	
	
	private class CloseListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	   		popUp.setVisible(false);
	   		popUp.dispose();
	    }
	}
	
	private class EndListener implements ActionListener{
	    public void actionPerformed(ActionEvent e) {
	   		System.exit(0);
	    }
	}

}
