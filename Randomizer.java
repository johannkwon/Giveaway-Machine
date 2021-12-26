import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Randomizer {

    private List<String> currentNameList; // constant name list

    /**
     * basic randomizer (only 1 winner)
     * 
     * @param names
     */
    public void randomize(List<String> names, int numWinners) {
        List<String> currentNames = names;
        System.out.println("\nThe winner(s) are: ");
        for (int i = 0; i < numWinners; i++) {
            int rand = (int) (Math.random() * currentNames.size()); // range is currentNames.size()
            String winner = currentNames.get(rand);
            System.out.println(winner);

            // removes all instances of winner's name from list
            currentNames.removeAll(Collections.singleton(winner));
        }
        System.out.println("\nThe current names are: \n" + currentNames);
    }

    /**
     * tiered randomizer (different tiers of products)
     * 
     * @param names
     * @param tierWinners
     * @param order
     */
    public void tieredRandomize(List<String> names, List<Integer> tierWinners, boolean order) {
        List<String> currentNames = names;
        // if order is bottom up (e.g. show tier 3 winners first, then tier 2, then tier
        // 1)
        if (order) {
            try {
                for (int i = tierWinners.size() - 1; i >= 0; i--) {
                    System.out.println("\nTier " + (i + 1) + " winner(s) are: ");
                    for (int j = 0; j < tierWinners.get(i); j++) {
                        int rand = (int) (Math.random() * currentNames.size()); // range is currentNames.size()
                        String winner = currentNames.get(rand);
                        System.out.println(winner);

                        // removes all instances of winner's name from list
                        currentNames.removeAll(Collections.singleton(winner));
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("There are not enough names to choose winners from");
            }
        } else {
            try {
                // if order is top down (e.g. show tier 1 winners first, then tier 2, then tier
                // 3)
                for (int i = 0; i < tierWinners.size(); i++) {
                    System.out.println("\nTier " + (i + 1) + " winner(s) are: ");
                    for (int j = 0; j < tierWinners.get(i); j++) {
                        int rand = (int) (Math.random() * currentNames.size()); // range is currentNames.size()
                        String winner = currentNames.get(rand);
                        System.out.println(winner);

                        // removes all instances of winner's name from list
                        currentNames.removeAll(Collections.singleton(winner));
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                System.out.println("There are not enough names to choose winners from");
            }
        }
        System.out.println("\nThe current names are: \n" + currentNames);
    }

    /**
     * starts randomizing process, prompts users for randomizer information
     * 
     * @param names
     */
    public void startRandomizer(List<String> names) {
        Scanner scan = new Scanner(System.in);
        List<Integer> tierWinners = new ArrayList<Integer>();
        currentNameList = new ArrayList<String>();
        boolean again = true;
        boolean responseCheck = false;

        while (again) {
            for (String name : names) {
                currentNameList.add(name);
            }
            System.out.println("The current names are: \n" + currentNameList);

            // checks for: prize tiers, winners (with/without prize tiers), winner order
            System.out.println("How many prize tiers?");
            int numTiers = scan.nextInt();
            scan.nextLine();
            // if only 0 or 1 tiers, normal randomize
            if (numTiers == 0 || numTiers == 1) {
                System.out.println("How many winners?");
                int numWinners = scan.nextInt();
                scan.nextLine();
                randomize(currentNameList, numWinners);
            } else {
                // if >1 tiers, tiered randomize
                for (int i = 0; i < numTiers; i++) {
                    System.out.println("How many winners in Tier " + (i + 1) + "?");
                    try {
                        int numWinners = scan.nextInt();
                        scan.nextLine();
                        tierWinners.add(numWinners);
                    } catch(InputMismatchException e) {
                        e.printStackTrace();
                        System.out.println("That is not a valid response");
                    }
                }

                System.out.println("In what order would you like winners to be chosen?");
                System.out.println("Top down (Tier 1 -> Tier 2 -> Tier 3) or bottom up? (Tier 3 -> Tier 2 -> Tier 1)");
                String order = scan.nextLine();
                // default order is top down (e.g. show tier 1 winners first, then tier 2, then
                // tier 3)
                boolean botFirst = false;
                if (order.equalsIgnoreCase("bottom up")) {
                    botFirst = true;
                }

                tieredRandomize(currentNameList, tierWinners, botFirst);
            }

            while(!responseCheck) {
                System.out.println("\nWould you like to pick another winner? Y or N");
                String response = scan.nextLine();
                if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
                    again = false;
                    responseCheck = true;
                } else if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
                    currentNameList.clear();
                    responseCheck = true;
                } else {
                    System.out.println("That is not a valid response");
                }
            }
        }
        scan.close();
    }

    /**
     * adds and returns total number of winners
     * 
     * @param tierWinners
     * @return
     */
    public int totalWinners(List<Integer> tierWinners) {
        int sum = 0;
        for (int numWinners : tierWinners) {
            sum += numWinners;
        }

        return sum;
    }
}