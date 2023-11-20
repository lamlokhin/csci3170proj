// main.java

import java.io.*;
class main{
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
        String input;
        try {
            input = in.readLine();
            return Integer.parseInt(input);
        } catch (IOException e) {
            System.err.println("Cannot read input");
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args){
        int choice = main_menu();
        System.out.println(choice);
    }
}
