/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devtest003;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author kimani kogi
 */
public class Part_2_ReadDBRowsAndActivemqQueuesIntoCSV {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;

    private static final String PIPE_DELIMITER = "|";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static ArrayList<Integer> specialItemsPositions = new ArrayList<>(20);

    public void receiveMessage() {
        try {
            factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = factory.createConnection();
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("data");
            consumer = session.createConsumer(destination);
            Message message = consumer.receive();

            if (message instanceof TextMessage) {
                TextMessage text = (TextMessage) message;

                Gson gson = new Gson();

                ArrayList<Pojo> ap = new ArrayList<>();

                Type collectionType = new TypeToken<Collection<Pojo>>() {
                }.getType();
                Collection<Pojo> p = gson.fromJson(text.getText(), collectionType);

                FileWriter fileWriter = null;

                try {
                    fileWriter = new FileWriter("csvFromActivemq");

                    for (Pojo poj : p) {
                        fileWriter.append(poj.getColumn0());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn1());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn2());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn3());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn4());
                        fileWriter.append(PIPE_DELIMITER);

                        fileWriter.append(poj.getColumn5());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn6());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn7());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn8());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn9());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn10());
                        fileWriter.append(PIPE_DELIMITER);
                        fileWriter.append(poj.getColumn11());

                        fileWriter.append(NEW_LINE_SEPARATOR);
                    }
                    connection.close();
                    System.out.println("CSV file was created successfully !!!");
                } catch (Exception e) {

                    System.out.println("Error in CsvFileWriter !!!");
                    e.printStackTrace();

                } finally {

                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        System.out.println("Error while flushing/closing fileWriter !!!");
                        e.printStackTrace();
                    }

                }

            } else {
                System.out.println("Message ");
            }
        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("Message ");
        }

    }

    public ArrayList<Pojo> ListDBItems() {
        ArrayList<Pojo> itemsList = new ArrayList();
        try {

            java.sql.Connection con = Part_1_ReadCSVFileIntoDbAndActivemq.getConnection();

            Statement st = con.createStatement();

            String searchQuery = "select * from csvdata";
            ResultSet rs = st.executeQuery(searchQuery);
            while (rs.next()) {

                Pojo data = new Pojo(
                        rs.getString("c1"), rs.getString("c2"), rs.getString("c3"), rs.getString("c4"),
                        rs.getString("c5"), rs.getString("c6"), rs.getString("c7"), rs.getString("c8"), rs.getString("c9"), rs.getString("c10"),
                        rs.getString("c11"),
                        rs.getString("c12")
                );

                itemsList.add(data);
            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return itemsList;
    }

    public void readDbWriteToCSV() {

        ArrayList<Pojo> p = ListDBItems();

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter("csvFronDb");

            for (Pojo poj : p) {
                fileWriter.append(poj.getColumn0());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn1());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn2());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn3());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn4());
                fileWriter.append(PIPE_DELIMITER);

                fileWriter.append(poj.getColumn5());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn6());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn7());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn8());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn9());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn10());
                fileWriter.append(PIPE_DELIMITER);
                fileWriter.append(poj.getColumn11());

                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            connection.close();
            System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {

            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();

        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
        Part_2_ReadDBRowsAndActivemqQueuesIntoCSV receiver = new Part_2_ReadDBRowsAndActivemqQueuesIntoCSV();
        receiver.receiveMessage();
        receiver.readDbWriteToCSV();
    }

}
