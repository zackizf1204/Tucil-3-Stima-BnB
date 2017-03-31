package main;

import java.io.*;

/** Main program.
 * Nama         : Zacki Zulfikar Fauzi
 * <br>NIM          : 13515147
 * <br>Nama File    : ${NAME}.java
 * <br>Tanggal      : ${DATE}
 */
public class Main {

    public static void main(String[] args) throws IOException{
        RCM test = new RCM();
        test.readFile("input.txt", "output.txt");
        System.out.println(test.getBaris());
        System.out.println(test.getKolom());
        test.printMatriks();
	// write your code here
    }
}
