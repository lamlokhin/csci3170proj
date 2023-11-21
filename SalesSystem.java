// SalesSystem.java

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
            if (input == 2){
                salesperson_menu();
            }
            if (input == 3){
                manager_menu();
            }
            return 0;

        } catch (IOException e) {
            System.err.println("Cannot read input");
            e.printStackTrace();
        }
        return 0;
    }
    
    private static void salesperson_menu(){
        return; 
    }

    private static void manager_menu(){
        System.out.print("\n-----Operations for manager menu-----\n"+
                           "What kinds of operation would you like to perform?\n"+
                           "1. List all salespersons\n"+
                           "2. Count to no. of sales record of each salesperson under a specific range on years of experience\n"+
                           "3. Show the total sales value of each manufacturer\n"+
                           "4. Show the N most popular part\n"+
                           "5. Return to the main menu\n"+
                           "Enter Your Choice: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input;
        try {
            input = Integer.parseInt(in.readLine());
            if (input == 1){
                list_salespersons();
            }
            if (input == 2){
                count_sales();
            }
            if (input == 3){
                show_sales_value();
            }
            if (input == 4){
                show_popular_parts();
            }
            if (input == 5){
                main_menu();
            }
            return;
        } catch (IOException e) {
            System.err.println("Cannot read input");
            e.printStackTrace();
        }
        return;
    }

    private static void admin_menu() {
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
            if (input == 4){
                show_content();
            }
            if (input == 5){
                main_menu();
            }
            return;
        } catch (IOException e) {
            System.err.println("Cannot read input");
            e.printStackTrace();
        }  
        return;
    }

    static void list_salespersons(){

    }
    static void count_sales(){

    }
    static void show_sales_value(){

    }
    static void show_popular_parts(){
        
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
    static void show_content(){
        System.out.print("Which table would you like to show: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputTable;
        try {
            inputTable = in.readLine();
            System.err.println("Content of the table "+inputTable+": ");
            // Retrieve table from database
            // For each tuple; each field, print line
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
