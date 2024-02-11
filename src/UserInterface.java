import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private static Scanner scanner;

    public UserInterface() {
       scanner = new Scanner(System.in);
       displayWelcomeMes();
       mainMenu();
   }

   public static void mainMenu() {
       final int START = 0, EXIT = 1;

       int option = Utils.getOption(
               "\nPlease select one of the following:",
               "Start", "Exit"
       );

       switch (option) {
           case START:
               startApp();
               break;
           case EXIT:
               exitApp();
               break;
           default:
               Utils.printMessage("Invalid option. Please try again.");
               break;
       }

   }

   private static void subMenu() {
       final int SATURDAY_GOLD_LOTTO = 0, OZ_LOTTO = 1, POWERBALL = 2, MONDAY_WEDNESDAY_GOLD_LOTTO = 3, BACK = 4;

       int option = Utils.getOption(
               "\nWhich game are you interested in?",
               "Saturday Gold Lotto", "OZ Lotto", "Powerball", "Monday & Wednesday Gold Lotto", "Back"
       );

       switch (option) {
           case SATURDAY_GOLD_LOTTO:
               DrawCollection sgl = new DrawCollection("LottoGenerator/data/sgl.json");
               Utils.load("\nConnecting to database");
               sgl.loadData(sgl.getFilePath());

               contextMenu(sgl, "Saturday Gold Lotto", 45, 1, false);
               break;
           case OZ_LOTTO:
               DrawCollection ozlotto = new DrawCollection("LottoGenerator/data/ozlotto.json");
               Utils.load("\nConnecting to database");
               ozlotto.loadData(ozlotto.getFilePath());

               contextMenu(ozlotto, "OZ Lotto", 47, 3, false);
               break;
           case POWERBALL:
               DrawCollection powerball = new DrawCollection("LottoGenerator/data/powerball.json");
               Utils.load("\nConnecting to database");
               powerball.loadData(powerball.getFilePath());

               contextMenu(powerball, "Powerball", 35, 1, true);
               break;
           case MONDAY_WEDNESDAY_GOLD_LOTTO:
               DrawCollection mwgl = new DrawCollection("LottoGenerator/data/mwgl.json");
               Utils.load("\nConnecting to database");
               mwgl.loadData(mwgl.getFilePath());

               contextMenu(mwgl, "Monday & Wednesday Gold Lotto", 45, 1, false);
               break;
           case BACK:
               mainMenu();
               break;
           default:
               Utils.printMessage("Invalid option. Please try again.");
               break;
       }
   }

   private static void contextMenu(DrawCollection drawCollection, String gameType, int maxNum, int numSpecial, boolean isPowerball) {
       boolean isDone = false;
       while (!isDone) {
           final int VIEW_LATEST = 0, ADD_DRAW = 1, DELETE_DRAW = 2, VIEW_WINNINGS_FREQ = 3, VIEW_SPECIAL_FREQ = 4, GENERATE_RAND = 5, GENERATE_RAND_FREQ = 6, SAVE_EXIT = 7, BACK = 8;

           int option = Utils.getOption(
                   "\nWhat would you like to do?",
                   "View Latest Draw",
                   "Add New Draw Results",
                   "Delete Existing Draw",
                   "View Winning Numbers Frequency",
                   "View Special Numbers Frequency",
                   "Generate Random Plays",
                   "Generate Random Plays with Frequency",
                   "Save & Exit",
                   "Back"
           );

           // Get the winning numbers frequency data
           Map<Integer, Integer> winningsFreq = drawCollection.getWinningsFreq();
           // Get the winning numbers frequency data
           Map<Integer, Integer> specialFreq = drawCollection.getSpecialFreq();

           switch (option) {
               case VIEW_LATEST:
                   drawCollection.printLatestDraw(drawCollection.getAllDraws(),gameType);

                   Utils.waitInput(scanner);
                   break;
               case ADD_DRAW:
                   System.out.println("Enter draw number:");
                   int drawNumber = Integer.parseInt(scanner.nextLine());

                   System.out.println("Enter winning numbers (separated by spaces):");
                   String[] winningNumbersStr = scanner.nextLine().split(" ");
                   List<Integer> winningNumbers = Utils.parseNumbers(winningNumbersStr);

                   System.out.println("Enter special numbers (separated by spaces):");
                   String[] specialNumbersStr = scanner.nextLine().split(" ");
                   List<Integer> specialNumbers = Utils.parseNumbers(specialNumbersStr);

                   System.out.println("Enter draw date (dd/mm/yyyy):");
                   String drawDate = scanner.nextLine();

                   Draw newDraw = new Draw(drawNumber, winningNumbers, specialNumbers, drawDate);
                   drawCollection.addNewDraw(newDraw);

                   Utils.waitInput(scanner);
                   break;
               case DELETE_DRAW:
                   System.out.println("Enter draw number you want to delete:");
                   int drawNumberDel = Integer.parseInt(scanner.nextLine());

                   drawCollection.deleteDraw(drawNumberDel);

                   Utils.waitInput(scanner);
                   break;
               case VIEW_WINNINGS_FREQ:
                   drawCollection.printNumberFreq(winningsFreq, gameType + " Winnings");

                   Utils.waitInput(scanner);
                   break;
               case VIEW_SPECIAL_FREQ:
                   drawCollection.printNumberFreq(specialFreq, gameType + " Special");

                   Utils.waitInput(scanner);
                   break;
               case GENERATE_RAND:
                   System.out.println("How many plays would you like to generate?");
                   int numPlay = Integer.parseInt(scanner.nextLine());

                   List<List<Integer>> randomPlays = Utils.generateRandomPlays(numPlay, maxNum, numSpecial, isPowerball);
                   Utils.printPlays(gameType, randomPlays);

                   Utils.waitInput(scanner);
                   break;
               case GENERATE_RAND_FREQ:
                   System.out.println("How many plays would you like to generate?");
                   int numPlayFreq = Integer.parseInt(scanner.nextLine());

                   List<List<Integer>> randomPlaysFreq = Utils.generatePlaysFreq(numPlayFreq, winningsFreq, specialFreq, isPowerball);
                   Utils.printPlays(gameType, randomPlaysFreq);

                   Utils.waitInput(scanner);
                   break;
               case SAVE_EXIT:
                   drawCollection.saveData(drawCollection.getFilePath());
                   drawCollection.clearDraws();
                   isDone = true;
                   exitApp();
                   break;
               case BACK:
                   drawCollection.clearDraws();
                   isDone = true;
                   subMenu();
                   break;
               default:
                   Utils.printMessage("Invalid option. Please try again.");
                   Utils.waitInput(scanner);
                   break;
           }
       }
   }

   private void displayWelcomeMes() {
        String mes = "\nWelcome to LottoGenerator!";
        Utils.printMessage(mes);
   }

   private static void startApp() {
       Utils.load("\nStarting LottoGenerator");
       subMenu();
   }

   private static void exitApp() {
       Utils.printMessage("\nExiting LottoGenerator. Bye Bye!\n");
       System.exit(0);
   }
}
