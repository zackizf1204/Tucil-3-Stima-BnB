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
        //RCM test = new RCM();
        BTL test2 = new BTL();
        int costLokal;
        //test.tampilkanSolusiRCM();
        test2.printMatriks(test2.matriks);
        //test2.testCost();
        test2.solveBTL();
        test2.printEdgeMinimum();
        System.out.println();
        System.out.println(test2.cost);
    }
}
