/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package River_Crossing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mary'sRose
 */
public class Crossing_Sequence_Validator {

    private static String inputFileName;
    private static ArrayList<String> inputList;
    private static ArrayList<String> OkList;

    public static void main(String[] args) {
        System.out.println("Input file name: ");
        Scanner sc = new Scanner(System.in);
        inputFileName = sc.next();
        OkList = new ArrayList<>();

        try {
            readInputFile(inputFileName);
        } catch (IOException ex) {
            System.out.println("Input file name: ");
            inputFileName = sc.next();
        }

        validateInputs();
        writeOutputFile();
    }

    public static void readInputFile(String fname) throws IOException {
        inputList = new ArrayList<>();
        FileReader reader = null;
        try {
            reader = new FileReader(fname);
            BufferedReader br = new BufferedReader(reader);
            String line;

            while ((line = br.readLine()) != null) {
                inputList.add(line);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Crossing_Sequence_Validator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Crossing_Sequence_Validator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void validateInputs() {
        System.out.println("in:" + inputList.size());
        for (String s : inputList) {
            ArrayList<Character> WestSide = new ArrayList<>(Arrays.asList('M', 'L', 'R', 'C'));
            ArrayList<Character> EastSide = new ArrayList<>();
            String moves = s;
            boolean west = true;
            boolean deadMove;
            for (char move : moves.toCharArray()) {
                if (west) {
                    EastSide.add('M');
                    WestSide.remove(WestSide.indexOf('M'));
                    if (move != 'N') {
                        if (WestSide.contains(move)) {
                            EastSide.add(move);
                            WestSide.remove(WestSide.indexOf(move));
                        } else {
                            break;
                        }
                    }
                    west = false;
                } else {
                    WestSide.add('M');
                    EastSide.remove(EastSide.indexOf('M'));
                    if (move != 'N') {
                        if (EastSide.contains(move)) {
                            WestSide.add(move);
                            EastSide.remove(EastSide.indexOf(move));
                        } else {
                            break;
                        }
                    }
                    west = true;
                }

                deadMove = (checkStates(WestSide) || checkStates(EastSide));

                if (deadMove) {
                    break;
                }
            }
            if (WestSide.isEmpty()) {
                OkList.add(moves);
                System.out.println(moves);
            }
        }
    }

    public static void writeOutputFile() {
        try {
            PrintWriter writer = new PrintWriter(inputFileName + ".out", "UTF-8");
            for (String s : inputList) {
                if (OkList.contains(s)) {
                    writer.println("OK");
                }else{
                    writer.println("NG");
                }
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Crossing_Sequence_Validator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean checkStates(ArrayList<Character> side) {
        return (((side.contains('L') && side.contains('R'))
                || (side.contains('R') && side.contains('C')))
                && !side.contains('M'));
    }
}
