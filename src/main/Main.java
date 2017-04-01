package main;

import java.io.*;

/** Main program.
 * Nama         : Zacki Zulfikar Fauzi
 * <br>NIM          : 13515147
 * <br>Nama File    : ${NAME}.java
 * <br>Tanggal      : ${DATE}
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        RCM test = new RCM();
        //test.readFile("input.txt", "output.txt");
        System.out.println(test.getBaris());
        System.out.println(test.getKolom());
        test.printMatriks(test.getMatriks());
        //test.printMatriks(test.getMatriksFix());
        System.out.println();
       // System.out.println("Hasil Reduksi :");
        //System.out.println(test.cekBaris(0));
        //test.reduceCost();
       // System.out.println(test.getCost());
        //test.printMatriks(test.getTempMatriks());
        //test.printMatriks(test.getTempMin());
        // write your code here
        test.solveRCM();
        test.printUrutan();
        System.out.println(test.getCost());
        test.buatGraf();


    }
}
