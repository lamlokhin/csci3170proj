// SalesSystem.java
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
public class CSCI3170Proj{
    static void main_menu() throws NumberFormatException, IOException, SQLException{
        System.out.print("\n-----Main menu-----\n"+
                          "What kinds of operation would you like to perform?\n"+
                          "1. Operations for administrator\n"+
                          "2. Operations for salesperson\n"+
                          "3. Operations for manager\n"+
                          "4. Exit this program\n"+
                          "Enter Your Choice: "); 
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input;
        
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
        
        return;
    }
    
    private static void salesperson_menu(){
        return; 
    }

    private static void manager_menu() throws NumberFormatException, IOException, SQLException {
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
        
        input = Integer.parseInt(in.readLine());
        if (input == 1){
            list_salespersons();
        }
        if (input == 2){
            count_sales();
        }
        if (input == 3){
            show_manufacturer();
        }
        if (input == 4){
            show_popular_parts();
        }
        if (input == 5){
            main_menu();
        }   
        return;
    }

    private static void admin_menu() throws NumberFormatException, IOException, SQLException {
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
    }

    static void list_salespersons() throws NumberFormatException, IOException, SQLException {
        System.out.print("Choose ordering:\n"+
                           "1. By ascending order\n"+
                           "2. By descending order\n"+
                           "Choose the list ordering: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input_order;
        
        input_order = Integer.parseInt(in.readLine());
        // retrieve list in requested order
        ResultSet result = null;
        if (input_order == 1){
            // retrieve in ascending
        }
        if (input_order == 2){
            // retrieve in descending
        }
        // print list
        System.out.println("| ID | Name | Mobile Phone | Years of Experience |");
        while (result.next()){
            System.out.printf(" | %s | %s | %s | %s |",
                              result.getString(1),
                              result.getString(2),
                              result.getString(3),
                              result.getString(4));
        }
        main_menu();
        return;

    }
    static void count_sales() throws NumberFormatException, IOException, SQLException{
        System.out.print("Type in the lower bound for years of experience: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int lower_bound = Integer.parseInt(in.readLine());
        System.out.print("Type in the upper bound for years of experience: ");
        int upper_bound = Integer.parseInt(in.readLine());
        // retrieve count of sales
        ResultSet result = null;

        // print list
        System.out.println("Transaction Record:\n"+
                           "| ID | Name | Years of Experience | Number of Transaction |");
        while (result.next()){
            System.out.printf(" | %s | %s | %s | %s |",
                              result.getString(1),
                              result.getString(2),
                              result.getString(3),
                              result.getString(4));
        }
        System.out.println("End of Query");
        main_menu();
    }
    static void show_manufacturer() throws SQLException{
        // retrieve manufacturers in decending order of total sales value
        ResultSet result = null;        
        // print manufacuturers
        System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
        while (result.next()){
            System.out.printf(" | %s | %s | %s |",
                              result.getString(1),
                              result.getString(2),
                              result.getString(3));
        }
        System.out.println("End of Query");
    }
    static void show_popular_parts() throws NumberFormatException, IOException, SQLException{
        System.out.print("Type in the number of parts: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(in.readLine());
        // retrieve the N most popular parts
        ResultSet result = null;
        // print the parts
        System.out.println("| Part ID | Part Name | No. of Transaction |");
        while (result.next()){
            System.out.printf(" | %s | %s | %s |",
                              result.getString(1),
                              result.getString(2),
                              result.getString(3));
        }
        System.out.println("End of Query");
        main_menu();
    }

    static void create_database() throws NumberFormatException, IOException, SQLException{
        System.out.print("Processing...");
        // initialize database
        System.out.println("Done! Database is initialized!");
        main_menu();
        return;
    }
    static void delete_database() throws NumberFormatException, IOException, SQLException{
        System.out.print("Processing...");
        // initialize database
        System.out.println("Done! Database is removed!");
        main_menu();
        return;
    }
    static void load_data() throws NumberFormatException, IOException, SQLException {
        System.out.print("Type in the Source Data Folder Path: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputPath;
        
        inputPath = in.readLine();
        System.err.print("Processing...");
        // read files and load data from inputPath
        System.err.println("Done! Data is inputted to the database!");
        main_menu();
        return;
        
    }
    static void show_content() throws NumberFormatException, IOException, SQLException {
        System.out.print("Which table would you like to show: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputTable = in.readLine();
        System.err.println("Content of the table "+inputTable+": ");
        // Retrieve table from database
        // For each tuple; each field, print line
        if (inputTable.equals("category")){
            System.out.println("| c_id | c_name |");
        }
        if (inputTable.equals("manufacturer")){
            System.out.println("| m_id | m_name | m_address | m_phone_number |");
        }
        if (inputTable.equals("part")){
            System.out.println("| p_id | p_name | p_price | m_id | c_id | p_warranty | p_quantity |");
        }
        if (inputTable.equals("salesperson")){
            System.out.println("| s_id | s_name | s_address | s_phone_number | s_experience |");
        }
        if (inputTable.equals("transaction")){
            System.out.println("| t_id | p_id | s_id | t_date |");
        }
        main_menu();
        return; 
    }

    public static void main(String[] args) throws NumberFormatException, IOException, SQLException{
        System.out.println("Welcome to sales system!");
        main_menu();
        
    }
}
