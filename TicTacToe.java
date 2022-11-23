
import java.util.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/** 
 * Escola Superior de Tecnologia e Gestão do
 * Instituto Politécnico de Beja
 * em 2022/11/22
 * -----------------------------------------------------
 * @author Luiz Carlos Morais Fehlberg 
 * @version 2022/11/18
 */
public class TicTacToe
{
    // as duas linhas seguintes não devem ser apagadas
    final static Scanner scanner = new Scanner(System.in); // objecto para ler texto
    static { scanner.useLocale(Locale.ENGLISH); } // para garantir que os números reais são lidos com '.' em lugar de ','

    static ArrayList<Integer>playerXPositions = new ArrayList<>();
    static ArrayList<Integer>playerOPositions = new ArrayList<>();

    final static String PLAYER_X = "player_x";
    final static String PLAYER_O = "player_o";

    public static void main(String[] args){
        startGameMenu();     
    }

    public static void startGameMenu() {
        char[][] gameBoard = {
                {'1','|','2','|','3'},
                {'-','+','-','+','-'},
                {'4','|','5','|','6'},
                {'-','+','-','+','-'},
                {'7','|','8','|','9'}};

        int option = gameOptions();
        if(option == 1) {
            System.out.println("You'll play against each other!! Good Luck for both\n");
            playerVsPlayer(gameBoard);
        }
        if(option == 2) {
            System.out.println("You'll play against pc!! Good Luck\n");
            playerVsPc(gameBoard);
        }
        if(option == 3) {
            System.out.flush();
        }
    }

    public static int gameOptions(){
        System.out.println(" ====================== ");
        System.out.println("| Choose an Option:    |\n" +
        "| 1 - Player vs Player |\n" + 
        "| 2 - Player vs PC     |\n" +
        "| 3 - Quit             |");
        System.out.println(" ====================== ");
        int option = scanner.nextInt();
        while(option < 1 || option > 3) {
            option = scanner.nextInt();
        }
        return option;
    }

    public static void playerVsPlayer(char[][] gameBoard) {
        printGameBoard(gameBoard);
        String result = "";
        while(result.equals("")) {
            System.out.println("Player X\nEnter your placement (1-9)"); 
            int playerXPos = scanner.nextInt();
            System.out.println("\n");
            playerXPos = verifyInput(playerXPos);
            while(playerXPositions.contains(playerXPos) || playerOPositions.contains(playerXPos)){
                System.out.println("Position taken! Enter a correct Position");
                playerXPos = scanner.nextInt();
                System.out.println("\n");
            }
            playerXPositions.add(playerXPos);
            placement(gameBoard, playerXPos, PLAYER_X);
            printGameBoard(gameBoard);
            result = checkWinner();
            if(result.equals("")) {
                System.out.println("Player Y\nEnter your placement (1-9)"); 
                int playerOPos = scanner.nextInt();
                System.out.println("\n");
                playerOPos = verifyInput(playerOPos);
                while(playerXPositions.contains(playerOPos) || playerOPositions.contains(playerOPos)){
                    System.out.println("Position taken! Enter a correct Position");
                    playerOPos = scanner.nextInt();
                    System.out.println("\n");
                }
                playerOPositions.add(playerOPos);
                placement(gameBoard, playerOPos, PLAYER_O); 
            }
            printGameBoard(gameBoard);
            result = checkWinner();
        }
        System.out.println(result + "\n\n");
        startGameMenu();
    }

    public static void playerVsPc(char[][] gameBoard){
        printGameBoard(gameBoard);
        String result = "";
        while(result.equals("")) {
            System.out.println("Enter your placement (1-9)");
            int playersPos = scanner.nextInt();
            System.out.println("\n");
            playersPos = verifyInput(playersPos);
            while(playerXPositions.contains(playersPos) || playerOPositions.contains(playersPos)){
                System.out.println("Position taken! Enter a correct Position");
                playersPos = scanner.nextInt();
                System.out.println("\n");
            }
            playerXPositions.add(playersPos);
            placement(gameBoard, playersPos, PLAYER_X);
            result = checkWinner();
            if(result.equals("")) {
                Random rand = new Random();
                int cpuPos = rand.nextInt(9) + 1;
                while(playerXPositions.contains(cpuPos) || playerOPositions.contains(cpuPos)){
                    cpuPos = rand.nextInt(9) + 1;
                }
                playerOPositions.add(cpuPos);
                placement(gameBoard, cpuPos, PLAYER_O);
            }
            printGameBoard(gameBoard);
            result = checkWinner();            
        }
        System.out.println(result + "\n\n");
        startGameMenu();
    }

    public static void printGameBoard(char[][] gameBoard){
        for (char[] row : gameBoard) {
            for(char c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static int verifyInput(int number){
        while(number < 1 || number > 9){
            System.out.print("Position doesn't exist!! Enter a correct Postion");
            number = scanner.nextInt();
        }
        return number;
    }

    public static void placement(char[][] gameBoard, int pos, String user){
        char sysmbol = ' ';
        if(user.equals(PLAYER_X)){
            sysmbol = 'X';
        }else if(user.equals(PLAYER_O)){    
            sysmbol = 'O' ;   
        }

        switch (pos){
            case 1:
                gameBoard [0] [0] =  sysmbol;
                break;
            case 2:
                gameBoard [0] [2] = sysmbol;
                break;
            case 3:
                gameBoard [0] [4] = sysmbol;
                break;
            case 4:
                gameBoard [2] [0] = sysmbol;
                break;
            case 5:
                gameBoard [2] [2] = sysmbol;
                break;
            case 6:
                gameBoard [2] [4] = sysmbol;
                break;
            case 7:
                gameBoard [4] [0] = sysmbol;
                break;
            case 8:
                gameBoard [4] [2] = sysmbol;
                break;
            case 9:
                gameBoard [4] [4] = sysmbol;
                break;
        }
    }

    public static String checkWinner(){

        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(7, 5, 3);

        List<List> winning = new ArrayList<List>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);

        for(List l : winning){
            if(playerXPositions.containsAll(l)){
                return "Player X win";
            }else if(playerOPositions.containsAll(l)){
                return "Player O win!";
            }else if(playerXPositions.size() + playerOPositions.size() == 9) {
                return "Its a draw!";
            }
        }

        return "";
    }
}

