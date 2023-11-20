// main.java

import java.io.*;
class SalseSystem{
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
    public static void main(String[] args){
        int main_choice = main_menu();
        // System.out.println(main_choice);
        if (main_choice == 1){
            int admin_choice = admin_menu(); 
        }
    }
    private static int admin_menu() {
        System.out.print("-----Operations for administrator menu----"+
                         "What kinds of operation would you like to perform?"+
                         "1. Create all tables"+
                         "2. Delete all tables"+
                         "3. Load from datafile"+
                         "4. Show content of a table"+
                         "5. Return to the main menu"+
                         "Enter Your Choice: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input;
        try {
            input = Integer.parseInt(in.readLine());
            if (input == 1){

                
            }
            return 0;
        } catch (IOException e) {
            System.err.println("Cannot read input");
            e.printStackTrace();
        }  
        return 0;
    }
}
