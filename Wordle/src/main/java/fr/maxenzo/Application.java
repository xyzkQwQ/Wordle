package fr.maxenzo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Application {

    public static final String YELLOW = "\u001B[33m";
    public static final String WHITE = "\u001B[37m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";

    static Vector<String> useLetterVec = new Vector<>();
    static int[] bestLetterColors = new int[26]; // 0=rouge, 1=vert, 2=jaune

    public static void main(String[] args) {
        int choice = choiceNumber();
        String randomWord = randomWord(choice);
        int lives = howManyLives();
        useLetterVec.clear();
        for (int i = 0; i < 26; i++) {
            bestLetterColors[i] = 0;
        }
        System.out.println("Put a word with " + randomWord.length() + " letters : ");

        do {
            String wordChoose = displayWord(choice);
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

    public static String displayWord(int choice) {
        Scanner xy = new Scanner(System.in);
        String word = xy.nextLine();

        while (word.length() != choice) {
            word = xy.nextLine();
        }
        System.out.print("\033[1A");
        System.out.print("\033[2K");
        return word.toUpperCase();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static String randomWord(int choice) {
        Random random = new Random();

        Vector<String> vecList = new Vector<>();
        String path;

        switch (choice) {
            case 5 -> {
                path = "Wordle\\src\\main\\java\\fr\\maxenzo\\words\\5_letters.txt";
            }
            case 6 -> {
                path = "Wordle\\src\\main\\java\\fr\\maxenzo\\words\\6_letters.txt";
            }
            default -> {
                path = "Wordle\\src\\main\\java\\fr\\maxenzo\\words\\7_letters.txt";
            }
        }

        File file = new File(path);
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String word = scan.nextLine();
                vecList.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return vecList.get(random.nextInt(vecList.size()));
    }

    public static boolean checkWord(String randomWord, String wordChoose, int lives) {

        int length = randomWord.length();
        int[] colors = new int[length];// 1 = vert, 2 = jaune, 0 = rouge
        int[] remainingCounts = new int[26];// Stock des lettres restantes du mot secret (A..Z => 26 cases)

        // 1) D'abord : on marque les VERTS
        // Et on met dans "remainingCounts" les lettres du mot secret qui n'ont PAS servi en vert
        for (int i = 0; i < length; i++) {
            char secretLetter = randomWord.charAt(i);
            char guessedLetter = wordChoose.charAt(i);

            if (guessedLetter == secretLetter) {
                colors[i] = 1; // vert
                bestLetterColors[guessedLetter - 'A'] = 1;
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
                bestLetterColors[index] = 2;
                remainingCounts[index]--; // on consomme 1 lettre du stock du tableau
            }
        }

        // 3) Affichage
        if (length == 5) {
            System.out.println("┌───┬───┬───┬───┬───┐");
        } else if (length == 6) {
            System.out.println("┌───┬───┬───┬───┬───┬───┐");
        } else if (length == 7) {
            System.out.println("┌───┬───┬───┬───┬───┬───┬───┐");
        }
        for (int i = 0; i < length; i++) {
            char letter = wordChoose.charAt(i);

            if (useLetterVec.contains(String.valueOf(letter)) != true) {
                useLetterVec.add(String.valueOf(letter));
            }

            switch (colors[i]) {
                case 1 ->
                    System.out.print("│ " + GREEN + letter + WHITE + " ");
                case 2 ->
                    System.out.print("│ " + YELLOW + letter + WHITE + " ");
                default ->
                    System.out.print("│ " + letter + " ");
            }
        }

        System.out.print("│ lives remaining : " + lives + " | Letters used : ");

        for (String letter : useLetterVec) {
            char carac = letter.charAt(0);
            int colorIndex = bestLetterColors[carac - 'A'];

            // Trouver la meilleure couleur pour cette lettre
            switch (colorIndex) {
                case 1 ->
                    System.out.print(GREEN + letter + WHITE + " ");
                case 2 ->
                    System.out.print(YELLOW + letter + WHITE + " ");
                default ->
                    System.out.print(RED + letter + WHITE + " ");
            }
        }

        System.out.println();

        if (length == 5) {
            System.out.println("└───┴───┴───┴───┴───┘");
        } else if (length == 6) {
            System.out.println("└───┴───┴───┴───┴───┴───┘");
        } else if (length == 7) {
            System.out.println("└───┴───┴───┴───┴───┴───┴───┘");
        }

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
            System.out.println("Send 1, 2 or 3 !");
            choice = scanner.nextInt();
        }
        return switch (choice) {
            case 1 -> {
                System.out.println("You've choose the first Level !");
                yield 10;
            }
            case 2 -> {
                System.out.println("You've choose the second Level !");
                yield 6;
            }
            default -> {
                System.out.println("You've choose the third Level !");
                yield 5;
            }
        };
    }

    public static int choiceNumber() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Send 1, 2 or 3 to choose how many letters do you want to have : ");
        System.out.println("1. 5 letters");
        System.out.println("2. 6 letters");
        System.out.println("3. 7 letters");

        int choice = scanner.nextInt();

        while (choice < 1 && choice > 3) {
            System.out.println("Send 1, 2 or 3 !");
            choice = scanner.nextInt();
        }

        return switch (choice) {
            case 1 -> {
                System.out.println("You've choose 5 letters");
                yield 5;
            }
            case 2 -> {
                System.out.println("You've choose 6 letters");
                yield 6;
            }
            default -> {
                System.out.println("You've choose 7 letters");
                yield 7;
            }
        };
    }

}
