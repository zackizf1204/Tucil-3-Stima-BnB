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
        BTL test2 = new BTL();
        long startTime = System.currentTimeMillis();
        test.tampilkanSolusiRCM();
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        System.out.println();
        long startTime2 = System.currentTimeMillis();
        test2.tampilkanSolusiBTL();
        long endTime2 = System.currentTimeMillis();
        System.out.println("That took " + (endTime2 - startTime2) + " milliseconds");
    }
}
