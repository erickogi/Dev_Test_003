/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devtest003;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author kimani kogi
 */
public class Part_1_ReadCSVFileIntoDbAndActivemq {

    //Delimiter used in CSV file
    private static final String PIPE_DELIMITER = "\\|";

    //columns  index
    private static final int COLUMN1 = 0;
    private static final int COLUMN2 = 1;
    private static final int COLUMN3 = 2;
    private static final int COLUMN4 = 3;
    private static final int COLUMN5 = 4;

    private static final int COLUMN6 = 5;
    private static final int COLUMN7 = 6;
    private static final int COLUMN8 = 7;
    private static final int COLUMN9 = 8;
    private static final int COLUMN10 = 9;

    private static final int COLUMN11 = 10;
    private static final int COLUMN12 = 11;

    public static void readCsvFile(String fileName) {

        BufferedReader fileReader = null;

        try {

            List<Pojo> pojo = new ArrayList<Pojo>();
            String line = "";

            fileReader = new BufferedReader(new FileReader(fileName));

            while ((line = fileReader.readLine()) != null) {

                String[] tokens = line.split(PIPE_DELIMITER);
                if (tokens.length > 0) {

                    Pojo pojo1 = new Pojo(tokens[COLUMN1], tokens[COLUMN2], tokens[COLUMN3], tokens[COLUMN4],
                            tokens[COLUMN5], tokens[COLUMN6], tokens[COLUMN7], tokens[COLUMN8],
                            tokens[COLUMN9], tokens[COLUMN10], tokens[COLUMN11], tokens[COLUMN12]);
                    pojo.add(pojo1);
                }

            }
            Gson gson = new Gson();
            ArrayList<Pojo> toActivemq = new ArrayList<>();
            for (int a = 0; a < pojo.size(); a++) {

                if (pojo.get(a).getColumn4().equals("G") || pojo.get(a).getColumn4().equals("C")) {
                    //to db
                    String query = "INSERT INTO `csvdata`(`c1`, `c2`, `c3`,`c4`,`c5`,`c6`, `c7`, `c8`,`c9`,`c10`,`c11`,`c12`)"
                            + " VALUES ('" + pojo.get(a).getColumn0() + "','" + pojo.get(a).getColumn1() + "','" + pojo.get(a).getColumn2() + "','" + pojo.get(a).getColumn3() + "','" + pojo.get(a).getColumn4() + "',"
                            + "'" + pojo.get(a).getColumn5() + "','" + pojo.get(a).getColumn6() + "','" + pojo.get(a).getColumn7() + "','" + pojo.get(a).getColumn8() + "','" + pojo.get(a).getColumn9() + "',"
                            + "'" + pojo.get(a).getColumn10() + "','" + pojo.get(a).getColumn11() + "')";

                    executeSQlQueryN(query);
                } else {
                    //to activeMQ
                    Pojo pojo1 = new Pojo(pojo.get(a).getColumn0(), pojo.get(a).getColumn1(), pojo.get(a).getColumn2(), pojo.get(a).getColumn3(),
                            pojo.get(a).getColumn4(), pojo.get(a).getColumn5(), pojo.get(a).getColumn6(), pojo.get(a).getColumn7(),
                            pojo.get(a).getColumn8(), pojo.get(a).getColumn9(), pojo.get(a).getColumn10(), pojo.get(a).getColumn11());

                    toActivemq.add(pojo1);
                }

            }
            String jsonObject = gson.toJson(toActivemq);
            sendMessage sm = new sendMessage();
            sm.sendMessage(jsonObject);

        } catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }

    }

    public static Connection getConnection() {

        Connection con = null;
        String path = "";
        try {

            String db = ":3306/devtest003?autoReeconnect=true&useSSL=false";
            String jdbc = "jdbc:mysql://";
            String user = "root";
            String pass = "123ERYcog.";
            path = "localhost";
            String dbp = (jdbc + path + db);

            con = DriverManager.getConnection(dbp, user, pass);

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return con;
    }

    public static int executeSQlQueryN(String query) {
        Connection con = getConnection();
        try {
            Statement st = con.createStatement();
            if (st.executeUpdate(query) == 1) {
                st.close();
                con.close();
                return 1;

            } else {
                return 0;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    public static void main(String[] args) {
        System.out.println("ENTER FILE NAME");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        readCsvFile(fileName);
    }
}
