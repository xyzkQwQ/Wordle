package fr.maxenzo;

import java.util.Random;
import java.util.Scanner;

public class First {

    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";

    public static void main(String[] args) {
        String randomWord = randomWord();
        int lives = howManyLives();
        System.out.println("Put a word with 5 letters : ");

        do {
            String wordChoose = displayWord();
            boolean niceWord = checkWord(randomWord, wordChoose, lives - 1);
            if (niceWord == true) {
                System.out.println("You win the game WP ! ");
                break;
            } else {
                lives = lives - 1;
            }

            if (lives == 0) {
                System.out.println("You lose the game ! ");
                System.out.println("The word was " + RED + randomWord);
            }
        } while (lives != 0);
    }

    public static String displayWord() {
        Scanner xy = new Scanner(System.in);
        String word = xy.nextLine();

        while (word.length() != 5) {
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

        int length = randomWord.length();
        int[] colors = new int[length];// 1 = vert, 2 = jaune, 0 = pas de couleur
        int[] remainingCounts = new int[26];// Stock des lettres restantes du mot secret (A..Z => 26 cases)

        // 1) D'abord : on marque les VERTS
        // Et on met dans "remainingCounts" les lettres du mot secret qui n'ont PAS servi en vert
        for (int i = 0; i < length; i++) {
            char secretLetter = randomWord.charAt(i);
            char guessedLetter = wordChoose.charAt(i);

            if (guessedLetter == secretLetter) {
                colors[i] = 1; // vert
            } else {
                int index = secretLetter - 'A'; // On récupère le code ASCII de la lettre secrete et on fait une soustraction avec le code ASCII de la lettre A.
                remainingCounts[index]++;       // On ajoute cette lettre au tableau remainingCounts
            }
        }

        // 2) Ensuite : on marque les JAUNES (si la lettre existe encore dans le stock)
        for (int i = 0; i < length; i++) {
            if (colors[i] == 1) {
                continue; // déjà vert, on ne change rien
            }

            char guessedLetter = wordChoose.charAt(i);
            int index = guessedLetter - 'A';// On récupère le code ASCII de la lettre du mot Choisi et on fait une soustraction avec le code ASCII de la lettre A.

            if (index >= 0 && index < 26 && remainingCounts[index] > 0) {//Si l'index est supérieur à 0 et inférieur à 26 et le contenu du tableau remainingCounts est supérieur à 0 ALORS
                colors[i] = 2;        // jaune
                remainingCounts[index]--; // on consomme 1 lettre du stock du tableau
            }
        }

        // 3) Affichage
        System.out.println("┌───┬───┬───┬───┬───┐");

        for (int i = 0; i < length; i++) {
            char letter = wordChoose.charAt(i);

            switch (colors[i]) {
                case 1 ->
                    System.out.print("│ " + GREEN + letter + WHITE + " ");
                case 2 ->
                    System.out.print("│ " + YELLOW + letter + WHITE + " ");
                default ->
                    System.out.print("│ " + letter + " ");
            }
        }

        System.out.println("│ lives remaining : " + lives);
        System.out.println("└───┴───┴───┴───┴───┘");

        return randomWord.equals(wordChoose);
    }

    public static int howManyLives() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Send 1, 2 or 3 to choose how many lives do you want to have : ");
        System.out.println("1. Piece of cake - 10 lives");
        System.out.println("2. Let's rock - 6 lives");
        System.out.println("3. Damn I'm good - 5 lives");

        int choice = scanner.nextInt();
        while (choice < 1 && choice > 3) {
            choice = scanner.nextInt();
        }
        if (choice == 1) {
            System.out.println("You've choose the first Level !");
            return 10;
        } else if (choice == 2) {
            System.out.println("You've choose the second Level !");
            return 6;
        } else {
            System.out.println("You've choose the third Level !");
            return 5;
        }
    }
}
