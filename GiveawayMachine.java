import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class GiveawayMachine {

	private static List<String> participants;
	
	/**
	 * checks if there is .txt list of names, otherwise ask for them
	 * 
	 * @param input
	 */
	public List<String> gate(String input, String txt) {
		if(input.equalsIgnoreCase("list")) {
			return(listNames());
		} else if (input.equalsIgnoreCase("text") || input.equalsIgnoreCase("txt")){
			return(copyNames(txt));
		} else {
			return null;
		}
	}
	
	/** 
	 * asks for new names and repeats them however many times is required
	 * adds names to a list and returns it after
	 * 
	 * @return list of names
	 */
	public List<String> listNames() {
		Scanner scan = new Scanner(System.in);
        List<String> names = new ArrayList<String>();
        boolean check = true;
        boolean responseCheck = false;
        
        while (check) {
            System.out.println("Enter a new name:");
            String newName = scan.nextLine();

            System.out.println("How many times is the name repeated?");
            int repeats = scan.nextInt();
            try {
                scan.nextLine();
                for (int i = 0; i < repeats; ++i) {
                    names.add(newName);
                }
            } catch (InputMismatchException e) {
                e.printStackTrace();
                System.out.println("That is not a number");
            }

            while(!responseCheck) {
                System.out.println("Are you adding more names? Y or N?");
                String input = scan.nextLine();
                if(input.equalsIgnoreCase("N") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("yes")) {
                    responseCheck = true;
                    if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("no")) {
                        check = false;
                    }
                } else {
                    System.out.println("That is not a valid response");
                }
            }
        }
		
		return names;
	}
	
	/**
	 * copies list of names from .txt file
	 * 
	 * @param given .txt file
	 * @return list of names from given .txt file
	 */
	public List<String> copyNames(String textFile) {
		List<String> copiedNames = new ArrayList<String>();
		BufferedReader br;

		try {
			br = new BufferedReader(
					new FileReader(textFile));
			String fileData = br.readLine();
			while(fileData != null) {
				copiedNames.add(fileData);
				fileData = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
            System.out.println("That file could not be found");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return copiedNames;
	}
	
	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
        GiveawayMachine gm = new GiveawayMachine();
        Randomizer r = new Randomizer();

        try {          
            System.out.println("How would you like to input names? List or text?");
            String userInput = scan.nextLine();
            String textFileName = null;
            if(userInput.equalsIgnoreCase("text") || userInput.equalsIgnoreCase("txt")) {
                System.out.println("\nEnter the location of the text file: ");
                textFileName = scan.nextLine();
            }
            participants = gm.gate(userInput, textFileName);
            r.startRandomizer(participants);
        } finally {
            scan.close();
        }
		
	}
}
