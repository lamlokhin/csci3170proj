
// SalesSystem.java
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.util.Date;

public class CSCI3170Proj {
    static void main_menu(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("\n-----Main menu-----\n" +
                "What kinds of operation would you like to perform?\n" +
                "1. Operations for administrator\n" +
                "2. Operations for salesperson\n" +
                "3. Operations for manager\n" +
                "4. Exit this program\n" +
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

    private static void search_part(Connection conn) throws NumberFormatException, IOException, SQLException {
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
        String query = "select part.pID,part.pName,manufacturer.mName,category.cName,part.pAvailableQuantity,part.pWarrantyPeriod,part.pPrice ";
        query += "from part,manufacturer,category ";
        query += "where part.cID = category.cID and part.mID = manufacturer.mID and ";
        if (criterion == 1) {
            query += "part.pName like ? ";
        } else if (criterion == 2) {
            query += "manufacturer.mName like ? ";
        }
        if (order == 1) {
            query += "order by part.pPrice ASC;";
        } else if (order == 2) {
            query += "order by part.pPrice DESC;";
        }
        PreparedStatement preQuery = conn.prepareStatement(query);
        preQuery.setString(1, "%" + keyword + "%");

        ResultSet result = preQuery.executeQuery();
        System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warrantly | Price");
        while (result.next()) {
            System.out.println(String.format("| %s | %s | %s | %s | %s | %s | %s |",
                    result.getString(1),
                    result.getString(2), result.getString(3), result.getString(4),
                    result.getString(5),
                    result.getString(6), result.getString(7)));
        }
        System.out.println("End of Query");
        preQuery.close();
        result.close();
        main_menu(conn);
    }

    private static void sell_part(Connection conn) throws NumberFormatException, IOException, SQLException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        /* get part id with checking */
        System.out.print("Enter The Part ID:");
        int part_id = Integer.parseInt(in.readLine());
        String quanCheckQuery = "Select pAvailableQuantity from part where pID = ?";
        PreparedStatement preQuanCheckQuery = conn.prepareStatement(quanCheckQuery);
        preQuanCheckQuery.setInt(1, part_id);
        ResultSet result1 = preQuanCheckQuery.executeQuery();
        int availableQuantity = 0;
        if (result1.next()) {
            availableQuantity = result1.getInt(1);
        }
        while (availableQuantity < 1) {
            System.out.println(
                    String.format("xxxErrorxxx:part with part id: %s have no available quantity, currently:%s\n",
                            part_id, availableQuantity));
            System.out.println("Enter The Part ID:");
            part_id = Integer.parseInt(in.readLine());
            preQuanCheckQuery.setInt(1, part_id);
            result1 = preQuanCheckQuery.executeQuery();
            if (result1.next()) {
                availableQuantity = result1.getInt(1);
            }
        }
        /* get salesperson id with checking */
        System.out.print("Enter The Salesperson ID:");
        int salesperson_id = Integer.parseInt(in.readLine());
        String salesPersonCheckQuery = "Select count(*) as num from salesperson where sID = ?";
        PreparedStatement preSalesPersonCheckQuery = conn.prepareStatement(salesPersonCheckQuery);
        preSalesPersonCheckQuery.setInt(1, salesperson_id);
        ResultSet result2 = preSalesPersonCheckQuery.executeQuery();
        Boolean salesPersonExist = false;
        if (result2.next()) {
            salesPersonExist = true;
        }
        while (!salesPersonExist) {
            System.out.println(String.format("xxxErrorxxx:SalesPerson not exist with id:%s\n", salesperson_id));
            System.out.println("please re-enter sales person id:");
            salesperson_id = Integer.parseInt(in.readLine());
            preSalesPersonCheckQuery.setInt(1, salesperson_id);
            result2 = preSalesPersonCheckQuery.executeQuery();
            if (result2.next()) {
                salesPersonExist = true;
            }
        }
        String getLastTIDQuery = "select max(tID) from transaction;";
        PreparedStatement preGetLastTIDQuery = conn.prepareStatement(getLastTIDQuery);
        ResultSet result3 = preGetLastTIDQuery.executeQuery();
        int lastTID = 0;
        if (result3.next())
            lastTID = result3.getInt(1);
        /* Inserting transaction */
        String addTransactionQuery = "Insert into transaction (tID,pID,sID,tDate) values (?,?,?,?);";

        PreparedStatement preAddTransactionQuery = conn.prepareStatement(addTransactionQuery);
        preAddTransactionQuery.setInt(1, lastTID + 1);
        preAddTransactionQuery.setInt(2, part_id);
        preAddTransactionQuery.setInt(3, salesperson_id);
        java.sql.Date sqlDate = new java.sql.Date((new Date()).getTime());
        preAddTransactionQuery.setDate(4, sqlDate);
        preAddTransactionQuery.executeUpdate();

        /* Updating part available quantity */
        String updatePartQuanQuery = "Update part set pAvailableQuantity= ? where pID= ?;";
        PreparedStatement preUpdatePartQuanQuery = conn.prepareStatement(updatePartQuanQuery);
        preUpdatePartQuanQuery.setInt(1, availableQuantity - 1);
        preUpdatePartQuanQuery.setInt(2, part_id);
        preUpdatePartQuanQuery.executeUpdate();
        String getUpdatedPartRecordQuery = "Select pName,pAvailableQuantity from part where pID= ?;";
        PreparedStatement preGetUpdatedPartRecordQuery = conn.prepareStatement(getUpdatedPartRecordQuery);
        preGetUpdatedPartRecordQuery.setInt(1, part_id);
        ResultSet result4 = preGetUpdatedPartRecordQuery.executeQuery();
        result4.next();
        System.out.println(String.format("Product: %s (id : %s) Remaining Quality:%s", result4.getString(1), part_id,
                result4.getString(2)));
        preQuanCheckQuery.close();
        preSalesPersonCheckQuery.close();
        preGetLastTIDQuery.close();
        preAddTransactionQuery.close();
        preUpdatePartQuanQuery.close();
        preGetUpdatedPartRecordQuery.close();
        result1.close();
        result2.close();
        result3.close();
        result4.close();
        main_menu(conn);
        return;
    }

    private static void manager_menu(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("\n-----Operations for manager menu-----\n" +
                "What kinds of operation would you like to perform?\n" +
                "1. List all salespersons\n" +
                "2. Count to no. of sales record of each salesperson under a specific range on years of experience\n" +
                "3. Show the total sales value of each manufacturer\n" +
                "4. Show the N most popular part\n" +
                "5. Return to the main menu\n" +
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
        System.out.print("\n-----Operations for administrator menu----\n" +
                "What kinds of operation would you like to perform?\n" +
                "1. Create all tables\n" +
                "2. Delete all tables\n" +
                "3. Load from datafile\n" +
                "4. Show content of a table\n" +
                "5. Return to the main menu\n" +
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
        System.out.print("Choose ordering:\n" +
                "1. By ascending order\n" +
                "2. By descending order\n" +
                "Choose the list ordering: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input_order;
        input_order = Integer.parseInt(in.readLine());
        // retrieve list in requested order
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        if (input_order == 1) {
            // retrieve in ascending
            rs = stmt.executeQuery("SELECT sID, sName, sPhoneNumber, sExperience FROM SALESPERSON ORDER BY sExperience ASC;");
        }
        if (input_order == 2) {
            // retrieve in descending
             rs = stmt.executeQuery("SELECT sID, sName, sPhoneNumber, sExperience FROM SALESPERSON ORDER BY sExperience DESC;");
        }
        // print list
        System.out.println("| ID | Name | Mobile Phone | Years of Experience |");
        while (rs.next()) {
            System.out.printf(" | %d | %s | %d | %d |\n",
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4));
        }
        System.out.println("End of Query");
        main_menu(conn);
        return;

    }

    static void count_sales(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("Type in the lower bound for years of experience: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int lower_bound = Integer.parseInt(in.readLine());
        System.out.print("Type in the upper bound for years of experience: ");
        int upper_bound = Integer.parseInt(in.readLine());
        // retrieve count of sales
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        rs = stmt.executeQuery("SELECT s.sID, s.sName, s.sExperience, COUNT(t.tID) FROM SALESPERSON s JOIN TRANSACTION t ON s.sID = t.sID WHERE s.sExperience BETWEEN " + lower_bound + " AND " + upper_bound + " GROUP BY s.sID, s.sName, s.sExperience ORDER BY s.sID DESC;");

        // print list
        System.out.println("Transaction Record:\n" +
                "| ID | Name | Years of Experience | Number of Transaction |");
        while (rs.next()) {
            System.out.printf(" | %d | %s | %d | %d |\n",
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4));
        }
        System.out.println("End of Query");
        main_menu(conn);
    }

    static void show_manufacturer(Connection conn) throws SQLException {
        // retrieve manufacturers in decending order of total sales value
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        rs = stmt.executeQuery("SELECT m.mID, m.mName, SUM(p.pPrice) AS total_sales_value FROM MANUFACTURER m JOIN PART p ON m.mID = p.mID GROUP BY m.mID, m.mName ORDER BY total_sales_value DESC;");
        // print manufacuturers
        System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
        while (rs.next()) {
            System.out.printf(" | %d | %s | %d |\n",
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3));
        }
        System.out.println("End of Query");
    }

    static void show_popular_parts(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("Type in the number of parts: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(in.readLine());
        // retrieve the N most popular parts
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        rs = stmt.executeQuery("SELECT p.pID, p.pName, total_no_trans FROM( SELECT p.pID, p.pName, COUNT(t.tID) AS total_no_trans, ROW_NUMBER() OVER (ORDER BY COUNT(t.Transaction_ID) DESC) AS RowNumber FROM PART p JOIN TRANSACTION t ON p.pID = t.pID GROUP BY p.pID, p.pName ORDER BY total_no_trans DESC) AS subquery WHERE RowNumber <= " + input + ";");
        // print the parts
        System.out.println("| Part ID | Part Name | No. of Transaction |");
        while (rs.next()) {
            System.out.printf(" | %d | %s | %d |",
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3));
        }
        System.out.println("End of Query");
        main_menu(conn);
    }

    static void create_database(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("Processing...");
        // initialize database
        Statement stmt = conn.createStatement();
        // Delete Original Table
        String drop = "DROP TABLE IF EXISTS CATEGORY, MANUFACTURER, PART,SALESPERSON, TRANSACTION;";
        stmt.executeUpdate(drop);
        // Create Table
        String create_category_string = "CREATE TABLE CATEGORY(cID INT NOT NULL PRIMARY KEY, cName VARCHAR(255) NOT NULL);";
        String create_manufacturer_string = "CREATE TABLE MANUFACTURER(mID INT NOT NULL PRIMARY KEY, mName VARCHAR(255) NOT NULL, mAddress VARCHAR(255) NOT NULL, mPhoneNumber INT NOT NULL);";
        String create_part_string = "CREATE TABLE PART(pID INT NOT NULL PRIMARY KEY, pName VARCHAR(255) NOT NULL, pPrice INT NOT NULL, mID INT NOT NULL, cID INT NOT NULL, pWarrantyPeriod INT NOT NULL, pAvailableQuantity INT NOT NULL);";
        String create_salesperson_string = "CREATE TABLE SALESPERSON(sID INT NOT NULL PRIMARY KEY, sName VARCHAR(255) NOT NULL, sAddress VARCHAR(255) NOT NULL, sPhoneNumber INT NOT NULL, sExperience INT NOT NULL);"; 
        String create_transaction_string = "CREATE TABLE TRANSACTION(tID INT NOT NULL PRIMARY KEY, pID INT NOT NULL REFERENCES PART(pID), sID INT NOT NULL REFERENCES SALESPERSON(sID), tDate DATE NOT NULL);";
        stmt.executeUpdate(create_category_string);
        stmt.executeUpdate(create_manufacturer_string);
        stmt.executeUpdate(create_part_string);
        stmt.executeUpdate(create_salesperson_string);
        stmt.executeUpdate(create_transaction_string);
        stmt.close();
        
        System.out.println("Done! Database is initialized!");
        main_menu(conn);
        return;
    }

    static void delete_database(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("Processing...");
        // initialize database
        Statement stmt = conn.createStatement();
        String drop = "DROP TABLE IF EXISTS CATEGORY, MANUFACTURER, PART,SALESPERSON, TRANSACTION;";
        stmt.executeUpdate(drop);
        stmt.close();
        System.out.println("Done! Database is removed!");
        main_menu(conn);
        return;
    }

    static void load_data(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("Type in the Source Data Folder Path: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputPath;
        Statement stmt = conn.createStatement();
        inputPath = in.readLine();
        System.err.print("Processing...");
        // Read "category.txt"
        BufferedReader categoryFileReader = new BufferedReader(new FileReader(inputPath + "/category.txt"));
        String categoryLine;
        while ((categoryLine = categoryFileReader.readLine()) != null) {
            // Split the line by tab delimiter
            String[] data_category = categoryLine.split("\t");
            // Extract the data values
            int cID = Integer.parseInt(data_category[0]);
            String cName = data_category[1];
            // Create the SQL INSERT statement
            String insert_category = "INSERT INTO CATEGORY (cID, cName) VALUES (" + cID + ",'" + cName + "');";
            // Execute the INSERT statement
            stmt.executeUpdate(insert_category);
        }

        // Read "manufacturer.txt"
        BufferedReader manufacturerFileReader = new BufferedReader(new FileReader(inputPath + "/manufacturer.txt"));
        String manufacturerLine;
        while ((manufacturerLine = manufacturerFileReader.readLine()) != null) {
            String[] data_manufacturer= manufacturerLine.split("\t");
            // Extract the data values
            int mID = Integer.parseInt(data_manufacturer[0]);
            String mName = data_manufacturer[1];
            String mAddress = data_manufacturer[2];
            int mPhoneNumber = Integer.parseInt(data_manufacturer[3]);
            // Create the SQL INSERT statement
            String insert_manufacturer = "INSERT INTO MANUFACTURER (mID, mName, mAddress, mPhoneNUmber) VALUES (" + mID + ",'" + mName + "','" + mAddress + "'," + mPhoneNumber + ");";
            // Execute the INSERT statement
            stmt.executeUpdate(insert_manufacturer);
        }

        // Read "part.txt"
        BufferedReader partFileReader = new BufferedReader(new FileReader(inputPath + "/part.txt"));
        String partLine;
        while ((partLine = partFileReader.readLine()) != null) {
            String[] data_part = partLine.split("\t");
            // Extract the data values
            int pID = Integer.parseInt(data_part[0]);
            String pName = data_part[1];
            int pPrice = Integer.parseInt(data_part[2]);
            int mID = Integer.parseInt(data_part[3]);
            int cID = Integer.parseInt(data_part[4]);
            int pWarrantyPeriod = Integer.parseInt(data_part[5]);
            int pAvailableQuantity = Integer.parseInt(data_part[6]);

            // Create the SQL INSERT statement
            String insert_part = "INSERT INTO PART (pID, pName, pPrice, mID, cID, pWarrantyPeriod, pAvailableQuantity) VALUES (" + pID + ",'" + pName + "'," + pPrice + "," + mID + "," + cID + "," + pWarrantyPeriod + "," + pAvailableQuantity + ");";
            // Execute the INSERT statement
            stmt.executeUpdate(insert_part);
        }

        // Read "salesperson.txt"
        BufferedReader salespersonFileReader = new BufferedReader(new FileReader(inputPath + "/salesperson.txt"));
        String salespersonLine;
        while ((salespersonLine = salespersonFileReader.readLine()) != null) {
            String[] data_salesperson = salespersonLine.split("\t");
            // Extract the data values
            int sID = Integer.parseInt(data_salesperson[0]);
            String sName = data_salesperson[1];
            String sAddress = data_salesperson[2];
            int sPhoneNumber = Integer.parseInt(data_salesperson[3]);
            int sExperience = Integer.parseInt(data_salesperson[4]);
            // Create the SQL INSERT statement
            String insert_salesperson = "INSERT INTO SALESPERSON (sID, sName, sAddress, sPhoneNUmber, sExperience) VALUES (" + sID + ",'" + sName + "','" + sAddress + "'," + sPhoneNumber + "," + sExperience + ");";
            // Execute the INSERT statement
            stmt.executeUpdate(insert_salesperson);
        }

        // Read "transaction.txt"
        BufferedReader transactionFileReader = new BufferedReader(new FileReader(inputPath + "/transaction.txt"));
        String transactionLine;
        SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy");
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
        while ((transactionLine = transactionFileReader.readLine()) != null) {
            String[] data_transaction = transactionLine.split("\t");
            // Extract the data values
            int tID = Integer.parseInt(data_transaction[0]);
            int pID = Integer.parseInt(data_transaction[1]);
            int sID = Integer.parseInt(data_transaction[2]);
            Date tDate_temp;
            try {
                tDate_temp = dt.parse(data_transaction[3]);
            } catch (ParseException e) {
                e.printStackTrace();
                System.err.println("Date format ERROR");
                return;
            }
            String tDate = dt1.format(tDate_temp);
            // Create the SQL INSERT statement
            String insert_transaction = "INSERT INTO TRANSACTION (tID, pID, sID, tDate) VALUES (" + tID + ", " + pID + ", " + sID + ",'" + tDate + "');";
            // Execute the INSERT statement
            stmt.executeUpdate(insert_transaction);
        }

        categoryFileReader.close();
        manufacturerFileReader.close();
        partFileReader.close();
        salespersonFileReader.close();
        transactionFileReader.close();
        stmt.close();
        // read files and load data from inputPath
        System.err.println("Done! Data is inputted to the database!");
        main_menu(conn);
        return;

    }

    static void show_content(Connection conn) throws NumberFormatException, IOException, SQLException {
        System.out.print("Which table would you like to show: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String inputTable = in.readLine();
        System.err.println("Content of the table " + inputTable + ": ");
        // Retrieve table from database
        // For each tuple; each field, print line
        Statement stmt = conn.createStatement();
        ResultSet rs = null;
        if (inputTable.equals("category")) {
            System.out.println("| cID | cNAme |");
            // Show content of Category
            rs = stmt.executeQuery("SELECT * FROM CATEGORY");
            while (rs.next()) {
                System.out.printf(" | %d | %s |\n",
                rs.getInt(1),
                rs.getString(2));
            }
        }
        if (inputTable.equals("manufacturer")) {
            System.out.println("| mID | mName | mAddress | mPhoneNumber |");
             // Show content of Manufacturer
            rs = stmt.executeQuery("SELECT * FROM MANUFACTURER");
            while (rs.next()) {
                System.out.printf(" | %d | %s | %s | %d |\n",
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4));
            }
        }
        if (inputTable.equals("part")) {
            System.out.println("| pID | pName | pPrice | mID | cID | pWarrantyPeriod | pAvailableQuantity |");
             // Show content of Part
            rs = stmt.executeQuery("SELECT * FROM PART");
            while (rs.next()) {
                System.out.printf(" | %d | %s | %d | %d | %d | %d | %d |\n",
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6),
                rs.getInt(7));
            }
        }
        if (inputTable.equals("salesperson")) {
            System.out.println("| sID | sName | sAddress | sPhoneNumber | sExperience |");
             // Show content of Salesperson
            rs = stmt.executeQuery("SELECT * FROM SALESPERSON");
            while (rs.next()) {
                System.out.printf(" | %d | %s | %s | %d | %d |\n",
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5));
            }
        }
        if (inputTable.equals("transaction")) {
            System.out.println("| tID | pID | sID | tDate |");
             // Show content of Transaction
            rs = stmt.executeQuery("SELECT * FROM TRANSACTION");
            while (rs.next()) {
                System.out.printf(" | %d | %d | %d | %s |\n",
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getString(4));
            }
        }
        rs.close();
        stmt.close();
        main_menu(conn);
        return;
    }

    public static void main(String[] args) throws NumberFormatException, IOException, SQLException {
        // connect to mySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception x) {
            System.err.println("Unable to load the driver class!");
        }
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db20?autoReconnect=true&useSSL=false", "Group20", "CSCI3170");

        System.out.println("Welcome to sales system!");
        main_menu(conn);
    }
}
