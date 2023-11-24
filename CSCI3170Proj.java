// SalesSystem.java
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
public class CSCI3170Proj{
    static void main_menu(Connection conn) throws NumberFormatException, IOException, SQLException{
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
        if (input == 1) {
            admin_menu(conn);
        }
        if (input == 2) {
            salesperson_menu(conn);
        }
        if (input == 3) {
            manager_menu(conn);
        }

        return;
    }

    private static void salesperson_menu(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("\n-----Operations for salesperson menu-----\n" +
                "What kinds of operation would you like to perform?\n" +
                "1. Search for part\n" +
                "2. Sell a part\n" +
                "3. Return to the main menu\n" +
                "Enter Your Choice: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int input = Integer.parseInt(in.readLine());
        if (input == 1) {
            search_part(conn);
        }
        if (input == 2) {
            sell_part(conn);
        }
        if (input == 3) {
            main_menu(conn);
        }

        return;
    }

    private static void search_part(Connection conn) throws NumberFormatException, IOException {
        System.out.print("\nChoose the Search criterion:\n" +
                "1. Part Name\n" +
                "2. Manufacturer Name\n" +
                "Choose the Search criterion: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int criterion = Integer.parseInt(in.readLine());
        System.out.print("\nType in the Search Keyword:");
        String keyword = in.readLine();
        System.out.print("\nChoose ordering:\n" +
                "1. By price, ascending order\n" +
                "2. By price, descending order\n" +
                "Choose ordering: ");
        int order = Integer.parseInt(in.readLine());
        String query = "select Part.ID,Part.Name,Manufacturer.mName,Category.cName,Part.pAvailableQuantity,Part.pWarrantyPeriod,Part.pPrice";
        query += "from Part,Manufacturer,Category ";
        query += "where Part.cid = Category.cid and Part.mID = manufacturer.mID and";
        if (criterion == 1) {
            query += "Part.Name like ? ";
        } else if (criterion == 2) {
            query += "Manufacturer.mName like ? ";
        }
        if (order == 1) {
            query += "order by price ASC;";
        } else if (order == 2) {
            query += "order by price DESC;";
        }
        // PreparedStatement preQuery=conn.prepareStatement(query);
        // preQuery.setString(1, "'%" + keyword + "%'");
        // System.out.println("contructed query is " + query);

        // ResultSet result=preQuery.executeQuery()
        // System.out.println("| ID | Name | Manufacturer | Category | Quantity |
        // Warrantly | Price");
        // while (result.next()) {
        // System.println(String.format("| %s | %s | %s | %s | %s | %s | %s |",
        // result.getString(1),
        // result.getString(2), result.getString(3), result.getString(4),
        // result.getString(5),
        // result.getString(6), result.getString(7)));
        // }
        // System.out.println("End of Query");
        // preQuery.close();
        // result.close();
    }

    private static void sell_part(Connection conn) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        /* get part id with checking */
        System.out.print("\nEnter The Part ID:\n");
        int part_id = Integer.parseInt(in.readLine());
        String quanCheckQuery = "Select pAvailableQuantity from Part where Part.pID = ?";
        // PreparedStatement preQuanCheckQuery = conn.prepareStatement(quanCheckQuery);
        // preQuanCheckQuery.setInt(1, part_id);
        // ResultSet result1 = preQuanCheckQuery.executeQuery();
        int availableQuantity = (int) Math.random();// result.getInt(1);
        while (availableQuantity < 1) {
            System.out.println(
                    String.format("xxxErrorxxx:part with part id: %s available quantity less than 1,currently:%s\n",
                            part_id, availableQuantity));
            System.out.println("Enter The Part ID:");
            part_id = Integer.parseInt(in.readLine());
            // preQuanCheckQuery.setInt(1, part_id);
            // result1 = preQuanCheckQuery.executeQuery();
            // availableQuantity = result.getInt(1);
        }

        /* get salesperson id with checking */
        System.out.print("\nEnter The Salesperson ID:\n");
        int salesperson_id = Integer.parseInt(in.readLine());
        String salesPersonCheckQuery = "Select count(*) from salseperson where sID=?";
        // PreparedStatement
        // preSalesPersonCheckQuery=conn.prepareStatement(salesPersonCheckQuery);
        // preSalesPersonCheckQuery.setInt(1, salesperson_id);
        // ResultSet result2 = preSalesPersonCheckQuery.executeQuery();
        Boolean salesPersonExist = (int) Math.random() > 0;// result2.getInt(1);
        while (!salesPersonExist) {
            System.out.println(String.format("xxxErrorxxx:SalesPerson not exist with id:%s\n", salesperson_id));
            System.out.println("please re-enter sales person id:");
            salesperson_id = Integer.parseInt(in.readLine());
            // preSalesPersonCheckQuery.setInt(1, salesperson_id);
            // result2 = preSalesPersonCheckQuery.executeQuery();
            // salesPersonExist = result2.getInt(1);
        }

        String getLastTIDQuery = "Select max(tID) from Transaction;";
        // PreparedStatement preGetLastTIDQuery =conn.prepareStatement(getLastTIDQuery);
        // ResultSet res3= preGetLastTIDQuery.executeQuery();
        int lastTID = 0;// res3.getInt(1)==null?0:res3.getInt(1);

        /* Inserting transaction */
        String addTransactionQuery = "Insert into transaction (tID,pID,sID,tDate) values (?,?,?,?);";

        // PreapredStatement
        // preAddTransactionQuery=conn.prepareStatement(preAddTransactionQuery);
        // preAddTransactionQuery.setInt(1,lastTID + 1);
        // preAddTransactionQuery.setInt(2,part_id);
        // preAddTransactionQuery.setint(3,salesperson_id);
        // preAddTransacctionQuery.setDate(4,LocalDate.now().format(DateTimeFormatter.ofPattern("dd/mm/yyyy")));

        /* Updating part available quantity */
        String updatePartQuanQuery = "Update Part set pAvailableQuantity= ? where pID= ?;";
        // PreparedStatement
        // preUpdatePartQuanQuery=conn.prepareStatement(updatePartQuanQuery);
        // preUpdatePartQuanQuery.setInt(1,availableQuantity-1);
        // preUpdatePartQuanQuery.setInt(2,part_id);

        /* get record after update quantity */
        String getUpdatedPartRecordQuery = "Select pName,pAvailableQuantity from Part where pID= ?;";
        // PreparedStatement
        // preGetUpdatedPartRecordQuery=conn.prepareStatement(getUpdatedPartRecordQuery);
        // preGetUpdatedPartRecordQuery.setInt(1,part_id);
        // ResultSet res4= preGetUpdatedPartRecordQuery.executeQuery();
        // System.out.println(String.format("Product: %s (id : %s) Remaining Quality:
        // %s", res4.getString(1), part_id,
        // res4.getString(22)));

        // preQuanCheckQuery.close();
        // preSalesPersonCheckQuery.close();
        // preGetLastTIDQuery.close();
        // preAddTransactionQuery.close();
        // preUpdatePartQuanQuery.close();
        // preGetUpdatedPartRecordQuert.close();
        // result1.close();
        // result2.close();
        // result3.close();
        // result4.close();
        // return;

    }

    private static void manager_menu(Connection conn) throws NumberFormatException, IOException, SQLException {
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
        if (input == 1) {
            list_salespersons(conn);
        }
        if (input == 2) {
            count_sales(conn);
        }
        if (input == 3) {
            show_manufacturer(conn);
        }
        if (input == 4) {
            show_popular_parts(conn);
        }
        if (input == 5) {
            main_menu(conn);
        }   
        return;
    }

    private static void admin_menu(Connection conn) throws NumberFormatException, IOException, SQLException {
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
        if (input == 1) {
            create_database(conn);
        }
        if (input == 2) {
            delete_database(conn);
        }
        if (input == 3) {
            load_data(conn);
        }
        if (input == 4) {
            show_content(conn);
        }
        if (input == 5) {
            main_menu(conn);
        }
        return;
    }

    static void list_salespersons(Connection conn) throws NumberFormatException, IOException, SQLException {
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
        if (input_order == 2) {
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
        main_menu(conn);
        return;

    }
    static void count_sales(Connection conn) throws NumberFormatException, IOException, SQLException{
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
        main_menu(conn);
    }
    static void show_manufacturer(Connection conn) throws SQLException{
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
    static void show_popular_parts(Connection conn) throws NumberFormatException, IOException, SQLException{
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
        main_menu(conn);
    }

    static void create_database(Connection conn) throws NumberFormatException, IOException, SQLException{
        System.out.print("Processing...");
        // initialize database
        System.out.println("Done! Database is initialized!");
        main_menu(conn);
        return;
    }
    static void delete_database(Connection conn) throws NumberFormatException, IOException, SQLException{
        System.out.print("Processing...");
        // initialize database
        System.out.println("Done! Database is removed!");
        main_menu(conn);
        return;
    }
    static void load_data(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("Type in the Source Data Folder Path: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputPath;

        inputPath = in.readLine();
        System.err.print("Processing...");
        // read files and load data from inputPath
        System.err.println("Done! Data is inputted to the database!");
        main_menu(conn);
        return;

    }
    static void show_content(Connection conn) throws NumberFormatException, IOException, SQLException {
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
        main_menu(conn);
        return;
    }

    public static void main(String[] args) throws NumberFormatException, IOException, SQLException{
        // connect to mySQL
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch(Exception x) { 
            System.err.println("Unable to load the driver class!"); 
        }
        Connection conn = DriverManager.getConnection( "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db20?autoReconnect=true&useSSL=false", "Group20", "CSCI3170");

        System.out.println("Welcome to sales system!");
        main_menu(conn);
    }
}
