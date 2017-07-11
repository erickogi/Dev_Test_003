# Dev_Test_003
Programing exercise.
Arranged into 3 parts (classes)  :

              Part-0_GenerateCSVFile
              
              Part_1_ReadCSVFileIntoDbAndActivemq
              
              Part_2_ReadDBRowsAndActivemqQueuesIntoCSV
             
Each part solves a specific question as provided in the excersice .


****************************************************************************************************************************

# Part-0_GenerateCSVFile

This class generates a CSV file according to conditions provided in Appendix 1.

The file produced contains 100 rows with 20 of them having either of the two characters (C and G) as  values for colum 5.

The rest of the characters in other rows are (A.G.C.T) randomly selected.

 # Random no generator
   
   
      public static int randomNoGenerator(int range) {
        Random r = new Random();
      
        return Math.abs(r.nextInt()) % range;

        }
        
    
 Random class
    
 An instance of this class is used to generate a stream of pseudorandom numbers in the range given as a parameter of the method.
 
# generateCsvFile Method
    
    public static boolean generateCsvFile(String fileName, String elements[], int rows, String specialElements[], int noOfSpecialElements, String safeChA[]) {
       ...........................................
       ..........................................
        writeToCsvFile(fileName, pojo);

        return true;
    }
    
    
    
    
This method requires these parameters
    
   fileName - the name of the csv file to be generated 
        
   elements - array of the 4 elements supposed to be randomly filled in the csv file (A,C,G,T)
        
   rows  - number(int) of rows of the csv file  (100)
        
   specialElements -array of the two elements (C,G) to be in 20 rows in column five of the csv
        
   noOfSpecialElements - no of rows to be filed with the special elements at column five (20)
        
    safeChA -array of the other elements that are not to be filled in the 20% of csv file in column five --(A,T)
        
        
This method generates 20 random positions for the special characters (C,G) adds all the other elements at random positions in the file

using a while loop and a POJO class object.
      
 At the end of the loop the method writeToCsvFile(String fileName, List<Pojo> pojo) is called providing it with the file name and a List
 
 of type Pojo which contains our generated csv items.
      
# writeToCsvFile

This method writes a file on the computers disk at the default folder which is the projects directory with our generated csv items.

    public static boolean writeToCsvFile(String fileName, List<Pojo> pojo) {
        FileWriter fileWriter = null;

        ...............................................
      }
      
      
  
  
  
This method accepts two parameters :  fileName ,List 
      
 FileWriter
      
 the method uses an instance of FileWriter class  used for writing streams of characters.
      
 Using an enhanced for loop , the list is iterated and the append method of thhe filewriter class is used to append the contents of the
 
 List to the file with the PIPE_DELIMITER separating characters in a row.
      
      
      
# Main method 
 
 the generateCsvFile method is called.
 
 
 
 
 **************************************************************************************************************************
 
 
 
 
 
 
#  Part_1_ReadCSVFileIntoDbAndActivemq.java


This class is used to read the csv generated in classPart-0_GenerateCSVFile and follow the conditions stipulated in the excercise

question to insert rows into a database or into Apache Activemq as json object.
 
 # readCsvFile  - method 

     public static void readCsvFile(String fileName) {

        BufferedReader fileReader = null;
        …………………………………………………………………………………………
         …………………………………………………………………………………………..
            fileReader = new BufferedReader(new FileReader(fileName));
         ………………………………………………………………………………………………..
           …………………………………………………………………………………………..

            Gson gson = new Gson();
            String jsonObject = gson.toJson(toActivemq);
            sendMessage sm = new sendMessage();
            sm.sendMessage(jsonObject);
         …………………………………………………………………
        ……………………………………………………..
         }

In this method, the name of the csv file to be read is provided as a parameter ,using the BufferedReader class to read characters on the

csv file . Using a while loop ,the whole file is read and the method split is used to split the strings using the pipe delimeter .

The elements are then added to an arraylist of type Pojo.

Then a for loop is used to go through each row while an if condition checks for the  condition at index 4 or column 5 (ie if the element

at column five is G or C. Inserting the whole row to a database if it is and pushing the row to a new list to be sent as a message using

activemq)


# GSON 
To convert my Pojo object to Json object ,I use the Gson library specifically the toJsonMethod.

        String jsonObject = gson.toJson(toActivemq);
            sendMessage sm = new sendMessage();
            sm.sendMessage(jsonObject); 


# getConnection()

This method is used to establish a connection to my database and return the Connection.


              public static Connection getConnection() {

                  Connection con = null;
                  ………………………………………………………..
                  ……………………………………………………………..
                 con = DriverManager.getConnection(dbp, user, pass);

                  return con;
                   }



# executeSQlQueryN(String query)

An sql query string is passed to this method as a string for the method to execute.

      public static int executeSQlQueryN(String query) {
        Connection con = getConnection();
            Statement st = con.createStatement();
                      ……………………………………………………………..
            st.executeUpdate(query
                         …………………………………………………………………….

    }




****************************************************************************************************



#  Part_2_ReadDBRowsAndActivemqQueuesIntoCSV

This class  :

 connects to a db and reads the content inserted by Part_1_ReadCSVFileIntoDbAndActivemq them to a separate  CSV file
              
  Connects to Connect to the ActiveMQ queue and read the data contained therein and write                 them to a separate
              
   CSV file
              
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




In this method ,  a connection to ActiveMq is created  and a session established and the message sent from 

Part_1_ReadCSVFileIntoDbAndActivemq is read and converted back to a Pojo Object which is then written to a new csv file .

I use Gson to converted back and filewriter to append o the new csv file 

The key used for the messages is “data”

The new csv file has the file name ” csvFromActivemq”.




# ListDBItems()
To retrieve contents of the database populated in Part_1_ReadCSVFileIntoDbAndActivemq and calls the readDbWriteToCSV method to  append

them to a new csv file named “csvFronDb”


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

 
 
      
      
        
        
        
        
        
        
        
        
        
