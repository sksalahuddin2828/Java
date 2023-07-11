import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String[] choices = {"rock", "paper", "scissors"};

        System.out.println("Welcome to the game!");
        System.out.println("Enter:");
        System.out.println("r for rock");
        System.out.println("p for paper");
        System.out.println("s for scissors");

        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        int userScoreTotal = 0;
        int computerScoreTotal = 0;
        int i = 1;

        while (i == 1) {
            String userChoice = userInputChecker(scanner);
            while (userChoice.equals("")) {
                userChoice = userInputChecker(scanner);
            }

            String computerChoice = choices[random.nextInt(choices.length)];
            System.out.println("Computer chooses: " + computerChoice);

            int[] scores = gameLogic(computerChoice, userChoice, userScoreTotal, computerScoreTotal);
            i = scores[0];
            userScoreTotal = scores[1];
            computerScoreTotal = scores[2];

            if (i == 0) {
                System.out.println("Scores for this match are as follows:");
                System.out.println(playerName + "'s score: " + userScoreTotal);
                System.out.println("Computer's score: " + computerScoreTotal);
                System.out.println("Thank you for playing the game.");
                System.out.println("Have a nice day!");
            } else if (i != 0 && i != 1) {
                System.out.println("Invalid Input!");
                System.out.print("Please enter 1 to continue or 0 to leave the game: ");
                i = scanner.nextInt();
            }
        }
    }

    public static String userInputChecker(Scanner scanner) {
        System.out.print("Enter your choice: ");
        String userChoice = scanner.nextLine();
        if (userChoice.equals("r") || userChoice.equals("p") || userChoice.equals("s")) {
            return userChoice;
        } else {
            System.out.println("Wrong Input!!");
            return "";
        }
    }

    public static int[] gameLogic(String computerChoice, String userChoice, int userScore, int computerScore) {
        int[] scores = new int[3];
        if (computerChoice.equals("rock") && userChoice.equals("p")) {
            System.out.println("Player Wins");
            System.out.println("Enter 1 to continue and 0 to leave the game");
            userScore += 1;
            scores[0] = scanner.nextInt();
            scores[1] = userScore;
            scores[2] = computerScore;
            return scores;
        } else if (computerChoice.equals("rock") && userChoice.equals("s")) {
            System.out.println("Computer Wins");
            System.out.println("Enter 1 to continue and 0 to leave the game");
            computerScore += 1;
            scores[0] = scanner.nextInt();
            scores[1] = userScore;
            scores[2] = computerScore;
            return scores;
        } else if (computerChoice.equals("paper") && userChoice.equals("r")) {
            System.out.println("Computer Wins");
            System.out.println("Enter 1 to continue and 0 to leave the game");
            computerScore += 1;
            scores[0] = scanner.nextInt();
            scores[1] = userScore;
            scores[2] = computerScore;
            return scores;
        } else if (computerChoice.equals("paper") && userChoice.equals("s")) {
            System.out.println("Player Wins");
            System.out.println("Enter 1 to continue and 0 to leave the game");
            userScore += 1;
            scores[0] = scanner.nextInt();
            scores[1] = userScore;
            scores[2] = computerScore;
            return scores;
        } else if (computerChoice.equals("scissors") && userChoice.equals("r")) {
            System.out.println("Player Wins");
            System.out.println("Enter 1 to continue and 0 to leave the game");
            userScore += 1;
            scores[0] = scanner.nextInt();
            scores[1] = userScore;
            scores[2] = computerScore;
            return scores;
        } else if (computerChoice.equals("scissors") && userChoice.equals("p")) {
            System.out.println("Computer Wins");
            System.out.println("Enter 1 to continue and 0 to leave the game");
            computerScore += 1;
            scores[0] = scanner.nextInt();
            scores[1] = userScore;
            scores[2] = computerScore;
            return scores;
        } else if (computerChoice.equals(userChoice)) {
            System.out.println("Draw");
            System.out.println("Enter 1 to continue and 0 to leave the game");
            userScore += 1;
            computerScore += 1;
            scores[0] = scanner.nextInt();
            scores[1] = userScore;
            scores[2] = computerScore;
            return scores;
        }

        return scores;
    }
}
