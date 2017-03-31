package main;

import java.io.*;
import java.util.ArrayList;

/**
 * Nama         : Zacki Zulfikar Fauzi
 * <br>NIM          : 13515147
 * <br>Nama File    : RCM.java
 * <br>Tanggal      : 31-Mar-17
 */
public class RCM {

  private int[][] matriks;
  private int baris;
  private int kolom;

  public RCM() {
    int i,j;
    baris =0;
    kolom = 0;
    matriks = new int[3][3];
    for(i=0;i<3;i++){
      for(j=0;j<3;j++){
        matriks[i][j]=0;
      }
    }

  }

  public void readFile(String namaInput, String namaOutput) throws IOException {
    int absis;
    int ordinat;
    int value;
    absis=0;
    ordinat=0;
    value=0;
    FileInputStream in = null;
    FileOutputStream out = null;

    try {
      in = new FileInputStream(namaInput);
      out = new FileOutputStream(namaOutput);

      int c;
      while ((c = in.read()) != -1) {
        out.write(c);
        if((c != ' ')&&((c != '\n'))){
          value = value * 10 + (c-48);
        }
        else{
          if((c == ' ')){
            set(absis, ordinat, value);
            ordinat++;
            value=0;
          }
          else{
            set(absis, ordinat, (value+35)/10);
            baris = absis+2;
            kolom = ordinat+1;
            value=0;
            absis++;
            ordinat=0;
          }
        }

      }
      set(absis, ordinat, value);
    } finally {
      if (in != null) {
        in.close();
      }
      if (out != null) {
        out.close();
      }
    }
  }

  public void set(int x, int y, int value) {
    if (x >= matriks.length) {
      int[][] tmp = matriks;
      matriks = new int[x + 1][];
      System.arraycopy(tmp, 0, matriks, 0, tmp.length);
      for (int i = x; i < x + 1; i++) {
        matriks[i] = new int[y];
      }
    }

    if (y >= matriks[x].length) {
      int[] tmp = matriks[x];
      matriks[x] = new int[y + 1];
      System.arraycopy(tmp, 0, matriks[x], 0, tmp.length);
    }

    matriks[x][y] = value;
  }

  public void printMatriks(){
    int i,j;
    for(i=0;i<baris;i++){
      for(j=0;j<kolom;j++){
        if(j==kolom-1){
          System.out.println(matriks[i][j]);
        }
        else{
          System.out.print(matriks[i][j]);
          System.out.print(" ");
        }
      }
    }
  }

  public int getBaris(){
    return baris;
  }

  public int getKolom(){
    return kolom;
  }

  public void reduceCost(){

  }
}


