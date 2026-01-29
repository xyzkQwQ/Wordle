package fr.maxenzo;

import java.util.Scanner;
import java.util.Random;

public class Application {
    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String GREEN = "\u001B[32m";

    public static void main(String[] args) {
        String randomWord = randomWord();
        int lives = howManyLives();
        System.out.println("Put a word with 5 letters : ");
        
        do {
            String wordChoose = displayWord();
            boolean niceWord = checkWord(randomWord, wordChoose, lives-1);
            if (niceWord == true) {
                System.out.println("You win the game WP ! ");
                break;
            } else {
                lives = lives - 1;
            }

            if (lives == 0) {
                System.out.println("You lose the game ! ");
            }
        } while (lives != 0);
    }

    public static String displayWord() {
        Scanner xy = new Scanner(System.in);
        String word = xy.nextLine();

        while (word.length() != 5)
        {
            word = xy.nextLine();
        }

        System.out.print("\033[1A");
        System.out.print("\033[2K");
        return word.toUpperCase();
    }

    public static String randomWord() {
        Random random = new Random();
        String[] mots = {
                "CRAIE", "TABLE", "FRUIT", "LIVRE", "SPORT", "GLACE", "PIANO", "FLEUR", "ROUGE", "BLEUE",
                "CHAPE", "DOUTE", "ETAGE", "FABLE", "GIVRE", "HAVRE", "IGLOO", "JOUTE", "KAYAK", "LAMPE",
                "MOULE", "NAPPE", "OLIVE", "PERLE", "DIGNE", "COUPE", "SUCRE", "TIGRE", "USINE", "VALSE",
                "WAGON", "ZEBRE", "YACHT", "ABIME", "BAGUE", "CADRE", "DONNE", "EPICE", "FOYER", "GAZON",
                "HACHE", "IMAGE", "OUEST", "LILAS", "NOYER", "OASIS", "POMME", "DEVIS", "RADIS", "ZUMBA",
                "SABLE", "TAPIS", "UNITE", "VAGUE", "ZESTE", "ALGUE", "BILLE", "ECLAT", "RESTE", "VESTE",
                "SOEUR", "FRERE", "SAINT", "ROCHE", "REBUS", "PRUNE", "POIRE", "MEULE", "MOLLE", "HYDRE"
        };
        int tableLength = mots.length;
        int randomNumber = random.nextInt(tableLength);
        return mots[randomNumber];
    }

    public static boolean checkWord(String randomWord, String wordChoose, int lives) {

        System.out.println("┌───┬───┬───┬───┬───┐");

        for (int i = 0; i < randomWord.length(); i++) {
            char letter = wordChoose.charAt(i); // <-- lettre tapée (PAS celle du mot secret)

            if (letter == randomWord.charAt(i)) {
                System.out.print("│ " + GREEN + letter + WHITE + " ");
            } else if (randomWord.indexOf(letter) != -1) { // <-- on cherche dans le mot secret
                int countLetterRandom = 0;
                int countLetterChoose = 0;

                for (int j = 0; j < randomWord.length(); j++) {
                    if (letter == randomWord.charAt(j))
                        countLetterRandom++;
                }

                for (int j = 0; j <= i; j++) { // <= i (inclure la position courante)
                    if (letter == wordChoose.charAt(j))
                        countLetterChoose++;
                }

                if (countLetterChoose <= countLetterRandom) {
                    System.out.print("│ " + YELLOW + letter + WHITE + " ");
                } else {
                    System.out.print("│ " + letter + " ");
                }
            } else {
                System.out.print("│ " + letter + " ");
            }
        }

        System.out.println("│ " + "lives remaining : " + lives);
        System.out.println("└───┴───┴───┴───┴───┘");
        if (randomWord.equals(wordChoose) == true) {
            return true;
        }
        return false;
    }

    public static int howManyLives()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Send 1, 2 or 3 to choose how many lives do you want to have : ");
        System.out.println("1. Piece of cake - 10 lives");
        System.out.println("2. Let's rock - 6 lives");
        System.out.println("3. Damn I'm good - 5 lives");
        
        int choice = scanner.nextInt();
        while (choice < 1 && choice > 3)
        {
            choice = scanner.nextInt();
        }
        if (choice == 1)
        {
            System.out.println("You've choose the first Level !");
            return 10;
        } 
        else if (choice == 2)
        {
            System.out.println("You've choose the second Level !");
            return 6;
        } 
        else 
        {
            System.out.println("You've choose the third Level !");
            return 5;
        }
    }
}