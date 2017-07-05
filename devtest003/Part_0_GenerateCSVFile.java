/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package devtest003;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author kimani kogi
 */
public class Part_0_GenerateCSVFile {

    private static final String PIPE_DELIMITER = "|";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static ArrayList<Integer> specialItemsPositions = new ArrayList<>(20);

    public static int randomNoGenerator(int range) {
        Random r = new Random();
        // return r.nextInt(100 - 1) + 1;
        return Math.abs(r.nextInt()) % range;

    }

    public static boolean generateCsvFile(String fileName, String elements[], int rows, String specialElements[], int noOfSpecialElements, String safeChA[]) {
        List<Pojo> pojo = new ArrayList<>();
        int rows_counter = 0;
        int special_charactersCounter = 0;
        while (special_charactersCounter < 20) {
            int rand = randomNoGenerator(100);
            if (!specialItemsPositions.contains(rand)) {
                specialItemsPositions.add(rand);
                special_charactersCounter++;
            } else {

            }
        }

        while (rows_counter < rows) {
            if (specialItemsPositions.contains(rows_counter)) {

                String p0 = elements[randomNoGenerator(4)];
                String p1 = elements[randomNoGenerator(4)];
                String p2 = elements[randomNoGenerator(4)];
                String p3 = elements[randomNoGenerator(4)];
                String p4 = specialElements[randomNoGenerator(2)];

                String p5 = elements[randomNoGenerator(4)];
                String p6 = elements[randomNoGenerator(4)];
                String p7 = elements[randomNoGenerator(4)];
                String p8 = elements[randomNoGenerator(4)];
                String p9 = elements[randomNoGenerator(4)];
                String p10 = elements[randomNoGenerator(4)];
                String p11 = elements[randomNoGenerator(4)];

                Pojo pojo1 = new Pojo(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
                pojo.add(pojo1);
                rows_counter++;

            } else {

                String p0 = elements[randomNoGenerator(4)];
                String p1 = elements[randomNoGenerator(4)];
                String p2 = elements[randomNoGenerator(4)];
                String p3 = elements[randomNoGenerator(4)];
                String p4 = safeChA[randomNoGenerator(2)];

                String p5 = elements[randomNoGenerator(4)];
                String p6 = elements[randomNoGenerator(4)];
                String p7 = elements[randomNoGenerator(4)];
                String p8 = elements[randomNoGenerator(4)];
                String p9 = elements[randomNoGenerator(4)];
                String p10 = elements[randomNoGenerator(4)];
                String p11 = elements[randomNoGenerator(4)];

                Pojo pojo1 = new Pojo(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
                pojo.add(pojo1);
                rows_counter++;
            }

        }
        writeToCsvFile(fileName, pojo);

        return true;
    }

    public static boolean writeToCsvFile(String fileName, List<Pojo> pojo) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            for (Pojo poj : pojo) {
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

            System.out.println("CSV file was created successfully !!!");
            return true;

        } catch (Exception e) {

            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
            return false;
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
        System.out.println("ENTER FILE NAME");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        String elements[] = {"A", "G", "C", "T"};
        String specialElements[] = {"C", "G"};
        String safeElements[] = {"A", "T"};
        generateCsvFile(fileName, elements, 100, specialElements, 20, safeElements);
    }
}
