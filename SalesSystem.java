// main.java

import java.io.*;
public class SalesSystem{
    static int main_menu(){
        System.out.print("Welcome to sales system!\n\n"+
                          "-----Main menu-----\n"+
                          "What kinds of operation would you like to perform?\n"+
                          "1. Operations for administrator\n"+
                          "2. Operations for salesperson\n"+
                          "3. Operations for manager\n"+
                          "4. Exit this program\n"+
                          "Enter Your Choice: "); 
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input;
        try {
            input = Integer.parseInt(in.readLine());
            if (input == 1){
                admin_menu();
            }
            return 0;

        } catch (IOException e) {
            System.err.println("Cannot read input");
            e.printStackTrace();
        }
        return 0;
    }
    
    private static int admin_menu() {
        System.out.print("\n-----Operations for administrator menu----\n"+
                         "What kinds of operation would you like to perform?\n"+
                         "1. Create all tables\n"+
                         "2. Delete all tables\n"+
                         "3. Load from datafile\n"+
                         "4. Show content of a table\n"+
                         "5. Return to the main menu\n"+
                         "Enter Your Choice: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input;
        try {
            input = Integer.parseInt(in.readLine());
            if (input == 1){
                create_database();                
            }
            if (input == 2){
                delete_database();
            }
            if (input == 3){
                load_data();
            }
            return 0;
        } catch (IOException e) {
            System.err.println("Cannot read input");
            e.printStackTrace();
        }  
        return 0;
    }

    static void create_database(){
        System.out.print("Processing...");
        // initialize database
        System.out.print("Done! Database is initialized!");
        return;
    }
    static void delete_database(){
        System.out.print("Processing...");
        // initialize database
        System.out.print("Done! Database is removed!");
        return;
    }
    static void load_data(){
        System.out.print("Type in the Source Data Folder Path: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputPath;
        try {
            inputPath = in.readLine();
            System.err.print("Processing...");
            // read files and load data from inputPath
            System.err.println("Done! Data is inputted to the database!");
            return;
        } catch (IOException e) {
            System.err.println("Cannot read input");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        main_menu();
        
    }
}
