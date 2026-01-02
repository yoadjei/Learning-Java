import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/**
 * Digital Business Card Generator
 * 
 * This program lets you create, save, and load digital business cards.
 * Its a great way to learn about file handling, user input, and string formatting in Java.
 * 
 * Concepts covered:
 * - Scanner for getting user input
 * - FileWriter and FileReader for saving and loading files
 * - String formatting and ASCII art
 * - Menu driven programs with loops
 */
public class CardHolder {
    
    // We use a Scanner for all our input needs throughout the program
    static Scanner scanner = new Scanner(System.in);
    
    // These hold the card info. We make them static so all methods can access them
    static String name = "";
    static String title = "";
    static String company = "";
    static String address = "";
    static String phone = "";
    static String email = "";
    static String website = "";
    static String linkedin = "";
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("     Digital Business Card Generator");
        System.out.println("===========================================");
        System.out.println();
        
        boolean running = true;
        
        // Main program loop. Keeps running until user chooses to exit
        while (running) {
            showMenu();
            
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            
            // Handle the menu choice. We use a switch because its cleaner than if else chains
            switch (choice) {
                case "1":
                    createNewCard();
                    break;
                case "2":
                    displayCard();
                    break;
                case "3":
                    saveCard();
                    break;
                case "4":
                    loadCard();
                    break;
                case "5":
                    editCard();
                    break;
                case "6":
                    System.out.println("\nThanks for using the Business Card Generator!");
                    running = false;
                    break;
                default:
                    System.out.println("\nThat option doesnt exist. Try again.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Shows the main menu options
     */
    static void showMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create new card");
        System.out.println("2. Display current card");
        System.out.println("3. Save card to file");
        System.out.println("4. Load card from file");
        System.out.println("5. Edit current card");
        System.out.println("6. Exit");
        System.out.println();
    }
    
    /**
     * Walks the user through creating a new business card
     * Each field is optional so they can just press enter to skip
     */
    static void createNewCard() {
        System.out.println("\n--- Create Your Business Card ---");
        System.out.println("(Press Enter to skip any field)\n");
        
        System.out.print("Full Name: ");
        name = scanner.nextLine();
        
        System.out.print("Job Title: ");
        title = scanner.nextLine();
        
        System.out.print("Company: ");
        company = scanner.nextLine();
        
        System.out.print("Address: ");
        address = scanner.nextLine();
        
        System.out.print("Phone: ");
        phone = scanner.nextLine();
        
        System.out.print("Email: ");
        email = scanner.nextLine();
        
        System.out.print("Website: ");
        website = scanner.nextLine();
        
        System.out.print("LinkedIn: ");
        linkedin = scanner.nextLine();
        
        System.out.println("\nCard created! Use option 2 to see it.");
    }
    
    /**
     * Displays the business card with a nice ASCII border
     * Only shows fields that actually have content
     */
    static void displayCard() {
        // Check if theres actually a card to display
        if (name.isEmpty() && email.isEmpty()) {
            System.out.println("\nNo card to display. Create one first!");
            return;
        }
        
        // We build the card line by line, only adding fields that have content
        // This makes the card look cleaner
        
        System.out.println();
        System.out.println("+--------------------------------------------------+");
        System.out.println("|                                                  |");
        
        // Name gets special treatment since its the most important
        if (!name.isEmpty()) {
            printCentered(name.toUpperCase());
        }
        
        if (!title.isEmpty()) {
            printCentered(title);
        }
        
        if (!company.isEmpty()) {
            printCentered(company);
        }
        
        System.out.println("|                                                  |");
        System.out.println("|--------------------------------------------------|");
        System.out.println("|                                                  |");
        
        // Contact details go on the left side
        if (!address.isEmpty()) {
            printLeftAligned("Address: " + address);
        }
        
        if (!phone.isEmpty()) {
            printLeftAligned("Phone: " + phone);
        }
        
        if (!email.isEmpty()) {
            printLeftAligned("Email: " + email);
        }
        
        if (!website.isEmpty()) {
            printLeftAligned("Web: " + website);
        }
        
        if (!linkedin.isEmpty()) {
            printLeftAligned("LinkedIn: " + linkedin);
        }
        
        System.out.println("|                                                  |");
        System.out.println("+--------------------------------------------------+");
    }
    
    /**
     * Helper method to print text centered in the card
     * Math might look tricky but its just calculating spaces on each side
     */
    static void printCentered(String text) {
        int cardWidth = 50;
        
        // If text is too long, trim it
        if (text.length() > cardWidth - 4) {
            text = text.substring(0, cardWidth - 7) + "...";
        }
        
        int padding = (cardWidth - text.length()) / 2;
        String spaces = " ".repeat(padding);
        
        // The extra logic handles odd length strings
        String line = "|" + spaces + text + spaces;
        if (line.length() < cardWidth + 1) {
            line += " ";
        }
        System.out.println(line + "|");
    }
    
    /**
     * Helper method to print text aligned to the left
     */
    static void printLeftAligned(String text) {
        int cardWidth = 50;
        
        // Trim if too long
        if (text.length() > cardWidth - 4) {
            text = text.substring(0, cardWidth - 7) + "...";
        }
        
        String line = "|  " + text;
        // Pad with spaces to reach the right edge
        while (line.length() < cardWidth + 1) {
            line += " ";
        }
        System.out.println(line + "|");
    }
    
    /**
     * Saves the current card to a text file
     * Uses FileWriter which is a simple way to write text to files
     */
    static void saveCard() {
        if (name.isEmpty()) {
            System.out.println("\nNo card to save. Create one first!");
            return;
        }
        
        System.out.print("\nEnter filename (without .txt): ");
        String filename = scanner.nextLine().trim();
        
        if (filename.isEmpty()) {
            filename = "my_card";
        }
        
        // Try with resources automatically closes the file when done
        // This is the modern way to handle file operations
        try (FileWriter writer = new FileWriter(filename + ".txt")) {
            // We save each field on its own line with a label
            // This makes it easy to read back later
            writer.write("NAME:" + name + "\n");
            writer.write("TITLE:" + title + "\n");
            writer.write("COMPANY:" + company + "\n");
            writer.write("ADDRESS:" + address + "\n");
            writer.write("PHONE:" + phone + "\n");
            writer.write("EMAIL:" + email + "\n");
            writer.write("WEBSITE:" + website + "\n");
            writer.write("LINKEDIN:" + linkedin + "\n");
            
            System.out.println("Card saved to " + filename + ".txt");
            
        } catch (IOException e) {
            // Something went wrong with writing the file
            System.out.println("Couldnt save the file. Error: " + e.getMessage());
        }
    }
    
    /**
     * Loads a card from a previously saved file
     * Uses BufferedReader which is efficient for reading text files line by line
     */
    static void loadCard() {
        System.out.print("\nEnter filename to load (without .txt): ");
        String filename = scanner.nextLine().trim();
        
        if (filename.isEmpty()) {
            System.out.println("No filename entered.");
            return;
        }
        
        File file = new File(filename + ".txt");
        
        // Check if the file actually exists before trying to read it
        if (!file.exists()) {
            System.out.println("File not found: " + filename + ".txt");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            
            // Read each line and extract the value after the colon
            while ((line = reader.readLine()) != null) {
                // Split on the first colon only. The value might contain colons too
                int colonIndex = line.indexOf(":");
                if (colonIndex > 0) {
                    String key = line.substring(0, colonIndex);
                    String value = line.substring(colonIndex + 1);
                    
                    // Match the key to the right field
                    switch (key) {
                        case "NAME": name = value; break;
                        case "TITLE": title = value; break;
                        case "COMPANY": company = value; break;
                        case "ADDRESS": address = value; break;
                        case "PHONE": phone = value; break;
                        case "EMAIL": email = value; break;
                        case "WEBSITE": website = value; break;
                        case "LINKEDIN": linkedin = value; break;
                    }
                }
            }
            
            System.out.println("Card loaded from " + filename + ".txt");
            displayCard();
            
        } catch (IOException e) {
            System.out.println("Couldnt read the file. Error: " + e.getMessage());
        }
    }
    
    /**
     * Lets the user edit individual fields without recreating the whole card
     */
    static void editCard() {
        if (name.isEmpty() && email.isEmpty()) {
            System.out.println("\nNo card to edit. Create one first!");
            return;
        }
        
        System.out.println("\n--- Edit Card ---");
        System.out.println("1. Name: " + name);
        System.out.println("2. Title: " + title);
        System.out.println("3. Company: " + company);
        System.out.println("4. Address: " + address);
        System.out.println("5. Phone: " + phone);
        System.out.println("6. Email: " + email);
        System.out.println("7. Website: " + website);
        System.out.println("8. LinkedIn: " + linkedin);
        System.out.println("9. Back to main menu");
        
        System.out.print("\nWhich field to edit? ");
        String choice = scanner.nextLine().trim();
        
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();
        
        switch (choice) {
            case "1": name = newValue; break;
            case "2": title = newValue; break;
            case "3": company = newValue; break;
            case "4": address = newValue; break;
            case "5": phone = newValue; break;
            case "6": email = newValue; break;
            case "7": website = newValue; break;
            case "8": linkedin = newValue; break;
            case "9": return;
            default: System.out.println("Invalid choice.");
        }
        
        System.out.println("Field updated!");
    }
}
