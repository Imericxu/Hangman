package zhiheng.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ConsoleGame extends BaseGame {
    
    private char[] gameWord;
    private int remainingLetters;
    
    public ConsoleGame(int lives) {
        super(lives);
        gameWord = new char[word.length()];
        Arrays.fill(gameWord, '_');
        remainingLetters = gameWord.length;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * *
    Methods
    * * * * * * * * * * * * * * * * * * * * * * */
    
    @Override
    public void play() {
        System.out.printf("Letters: %d\n", gameWord.length);
        System.out.println(toString());
        
        while (lives > 0 && remainingLetters > 0) {
            tryLetter(prompt());
            try {
                sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println();
        
        if (remainingLetters == 0) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose :(");
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The word was: ");
            System.out.println(word);
        }
    }
    
    @Override
    protected char prompt() {
        Scanner sc = new Scanner(System.in);
        char letter;
        
        while (true) {
            System.out.println();
            Collections.sort(usedLetters);
            System.out.println("Used: " + usedLetters);
            System.out.print("Guess a letter: ");
            
            try {
                letter = sc.nextLine().trim().charAt(0);
                
                if (usedLetters.contains(letter)) {
                    System.out.println("You used this letter already!");
                } else if (Character.isAlphabetic(letter) && !usedLetters.contains(letter)) {
                    break;
                } else {
                    System.out.println("Invalid input!");
                }
                System.out.println(toString());
            } catch (Exception e) {
                System.out.println("Invalid input!");
                System.out.println(toString());
            }
        }
        
        usedLetters.add(letter);
        
        return Character.toUpperCase(letter);
    }
    
    @Override
    protected void foundLetterAction(ArrayList<Integer> positions, char letter) {
        System.out.println("You got it!");
        for (Integer position : positions) {
            gameWord[position] = letter;
            remainingLetters--;
        }
        System.out.println(toString());
    }
    
    @Override
    protected void failToFindLetterAction() {
        System.out.println("Uh oh, that letter isn't right");
        System.out.println(toString());
    }
    
    @Override
    protected void win() {
    
    }
    
    @Override
    protected void lose() {
    
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lives: ").append(lives).append("\n");
        for (char c : gameWord) {
            sb.append(c);
            sb.append(' ');
        }
        return sb.toString();
    }
}
