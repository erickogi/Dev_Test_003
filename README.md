# Dev_Test_003
Programing exercise.
Arranged into 3 parts (classes)  :

              Part-0_GenerateCSVFile
              
              Part_1_ReadCSVFileIntoDbAndActivemq
              
              Part_2_ReadDBRowsAndActivemqQueuesIntoCSV
             
Each part solves a specific question as provided in the excersice .

# Part-0_GenerateCSVFile

This class generates a CSV file according to conditions provided in Appendix 1.

The file produced contains 100 rows with 20 of them having either of the two characters (C and G) as  values for colum 5.

The rest of the characters in other rows are (A.G.C.T) randomly selected.

   #Random no generator
   
   
      public static int randomNoGenerator(int range) {
        Random r = new Random();
      
        return Math.abs(r.nextInt()) % range;

        }
        
    
 Random class
    
 An instance of this class is used to generate a stream of pseudorandom numbers in the range given as a parameter of the method.
 
#generateCsvFile Method
    
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
        
        
      This method generates 20 random positions for the special characters (C,G) adds all the other elements at random positions in the file using a while loop and a POJO class object.
      
      At the end of the loop the method writeToCsvFile(String fileName, List<Pojo> pojo) is called providing it with the file name and a List of type Pojo which contains our generated csv items.
      
#writeToCsvFile
This method writes a file on the computers disk at the default folder which is the projects directory with our generated csv items.

    public static boolean writeToCsvFile(String fileName, List<Pojo> pojo) {
        FileWriter fileWriter = null;

        ...............................................
      }
      
      
  
  
  
This method accepts two parameters :  fileName ,List 
      
 FileWriter
      
 the method uses an instance of FileWriter class  used for writing streams of characters.
      
 Using an enhanced for loop , the list is iterated and the append method of thhe filewriter class is used to append the contents of the List to the file with the PIPE_DELIMITER separating characters in a row.
      
      
      
 Main method 
 
 the generateCsvFile method is called.
 
 
 
 
 
 
 
#  Part_1_ReadCSVFileIntoDbAndActivemq.java


This class is used to read the csv generated in classPart-0_GenerateCSVFile and follow the conditions stipulated in the excercise question to insert rows into a database or into Apache Activemq as json object.
 
 
 
 
      
      
        
        
        
        
        
        
        
        
        
