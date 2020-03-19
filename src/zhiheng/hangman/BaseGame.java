package zhiheng.hangman;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public abstract class BaseGame {
    
    protected String word;
    protected int lives;
    protected ArrayList<Character> usedLetters;
    
    protected BaseGame(int lives) {
        try {
            word = getRandomWord();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.lives = lives;
        usedLetters = new ArrayList<>();
    }
    
    
    /* * * * * * * * * * * * * * * * * * * * * * *
    Methods
    * * * * * * * * * * * * * * * * * * * * * * */
    
    /**
     * @param letter single letter to test
     * @return false if letter used already
     */
    protected void tryLetter(char letter) {
        char capLetter = Character.toUpperCase(letter);
        
        ArrayList<Integer> positions = findLetterLocations(letter);
        
        if (positions.size() > 0) {
            foundLetterAction(positions, capLetter);
        } else {
            lives--;
            failToFindLetterAction();
        }
    }
    
    public abstract void play();
    
    protected abstract char prompt();
    
    protected abstract void foundLetterAction(ArrayList<Integer> positions, char letter);
    
    protected abstract void failToFindLetterAction();
    
    protected abstract void win();
    
    protected abstract void lose();
    
    private ArrayList<Integer> findLetterLocations(char letter) throws IllegalArgumentException {
        ArrayList<Integer> positions = new ArrayList<>();
        
        if (!word.contains(Character.toString(letter))) {
            return positions;
        }
        
        int index = 0;
        while (index < word.length()) {
            index = word.indexOf(letter, index);
            if (index != -1) {
                positions.add(index);
                index++;
            } else {
                break;
            }
        }
        
        return positions;
    }
    
    private String getRandomWord() throws IOException {
        File textFile = new File("src/zhiheng/hangman/wordlist.txt");
        final RandomAccessFile randomAccessFile = new RandomAccessFile(textFile, "r");
        String gameWord;
        do {
            int random = (int) (Math.random() * randomAccessFile.length());
            randomAccessFile.seek(random);
            gameWord = randomAccessFile.readLine().trim();
        } while (!checkWord(gameWord));
        return gameWord.toUpperCase();
    }
    
    private boolean checkWord(String word) {
        return word.length() > 3 && word.matches("[A-Za-z]*");
    }
}
