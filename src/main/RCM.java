package main;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Nama         : Zacki Zulfikar Fauzi
 * <br>NIM          : 13515147
 * <br>Nama File    : RCM.java
 * <br>Tanggal      : 31-Mar-17
 */
public class RCM {

  private int[][] matriks;
  private int[][] tempMin;
  private int[][] tempMatriks;
  private int[][] matriksFix;
  private int baris;
  private int kolom;
  private int infinite = 999;
  private int minimum;
  private int cost;
  private int costLokal;
  private int[] urutanJalur;
  private int pilihan;


  public RCM() throws IOException{
    int i,j;
    baris =0;
    kolom = 0;
    matriks = new int[3][3];
    for(i=0;i<3;i++){
      for(j=0;j<3;j++){
        matriks[i][j]=0;
      }
    }
    readFile("input2.txt", "output.txt");
    urutanJalur =  new int[baris+1];
    for(i=0;i<baris;i++){
      urutanJalur[i]=infinite;
    }
    matriksFix = new int[baris][kolom];
    tempMatriks= new int[baris][kolom];
    tempMin = new int[baris][kolom];
    copyMatriksFix(matriks);
    copyTempMatriks(matriks);
    copyTempMin(matriks);
    cost=0;
    pilihan=0;
    minimum=999;
    urutanJalur[0]=0;
    urutanJalur[baris]=0;
  }

  public void buatGraf() throws IOException {
    File file = new File("GrafRCM.puml");
    file.createNewFile();
    FileWriter writer = new FileWriter(file);
    int i,j,k;
    k=0;
    Integer temp;
    writer.write("@startdot\n");
    writer.write("digraph graf {\n");
    for(i=0;i<baris;i++){
      for(j=0;j<kolom;j++){
        if(matriks[i][j]!=infinite) {
          writer.write(i+49);
          writer.write(" -> ");
          writer.write(j+49);
          writer.write(" [label=");
          temp=matriks[i][j];
          writer.write(String.valueOf(temp));
          if((j==urutanJalur[findUrutan(i)+1])&&(i==urutanJalur[findUrutan(i)])){
            writer.write(", color=Blue");

          }
          writer.write("];\n");
        }
      }
    }
    writer.write("}\n");
    writer.write("@enddot");
    writer.flush();
    writer.close();
    }


    public int findUrutan(int node){
      int i,n;
      n=0;
      for(i=0;i<baris;i++){
        if(urutanJalur[i]==node){
          n=i;
        }
      }
      return n;
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

  public boolean cekUrutan(){
    int i;
    boolean full;
    full=true;
    for (i=0;i<baris;i++){
      if(urutanJalur[i]==infinite){
        full=false;
      }
    }
    return full;
  }

  public void solveRCM() throws InterruptedException {
    int j,k;
    reduceCost();
    k=1;
    int temp;
    temp =0;
    copyTempMin(tempMatriks);
    cost= costLokal;
    costLokal=0;
    while(cekUrutan()==false) {
      minimum =999;
      for (j = 0; j < kolom; j++) {
        costLokal=0;
        copyTempMatriks(tempMin);
        if (tempMatriks[pilihan][j] != infinite) {
          costLokal =  costLokal+tempMatriks[pilihan][j];
          infiniteBaris(pilihan);
          infiniteKolom(j);
          infiniteNode(j);
          reduceCost();
          if(costLokal < minimum){
            temp =j;
            minimum=costLokal;
            copyMatriksFix(tempMatriks);
          }
        }
        System.out.println(costLokal);
      }
      pilihan = temp;
      urutanJalur[k]=pilihan;
      k++;
      copyTempMin(matriksFix);
      printMatriks(tempMin);
      System.out.println();
      cost=cost+minimum;
    }
  }

  public void printUrutan(){
    int i;
    for(i=0;i<baris;i++){
      System.out.println(urutanJalur[i]);
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

  public void printMatriks(int[][] inputMatriks){
    int i,j;
    for(i=0;i<baris;i++){
      for(j=0;j<kolom;j++){
        if(j==kolom-1){
          System.out.println(inputMatriks[i][j]);
        }
        else{
          System.out.print(inputMatriks[i][j]);
          System.out.print(" ");
        }
      }
    }
  }

  public boolean cekKolom(int inputKolom){
    int i;
    boolean found;
    found=false;
    for (i=0;i<baris;i++){
      if(tempMatriks[i][inputKolom]==0){
        found = true;
      }
    }
    return found;
  }

  public boolean cekBaris(int inputBaris){
    int j;
    boolean found;
    found=false;
    for (j=0;j<baris;j++){
      if(tempMatriks[inputBaris][j]==0){
        found = true;
      }

    }
    return found;
  }

  public void reduceCost() throws InterruptedException {
    int i, j;
    for (i = 0; i < baris; i++) {
      if (cekBaris(i) == true) {
        sleep(1);
      } else {
        if (cariMinimumBaris(i) != infinite) {
          kurangiBaris(i, cariMinimumBaris(i));
        }
      }
    }
    for (j = 0; j < kolom; j++) {
      if (cekKolom(j) == true) {
        sleep(1);
      }
      else {
        if (cariMinimumKolom(j) != infinite) {
          kurangiKolom(j, cariMinimumKolom(j));
        }
      }
    }
  }


  public void kurangiKolom(int inputKolom, int inputMin){
    int i;
    for (i=0;i<baris;i++){
      if(tempMatriks[i][inputKolom]!=infinite){
        tempMatriks[i][inputKolom]= tempMatriks[i][inputKolom] - inputMin;
      }
    }
    costLokal=costLokal+inputMin;
  }

  public void kurangiBaris(int inputBaris, int inputMin){
    int j;
    for (j=0;j<kolom;j++){
      if(tempMatriks[inputBaris][j]!=infinite) {
        tempMatriks[inputBaris][j] = tempMatriks[inputBaris][j] - inputMin;
      }
    }
    costLokal=costLokal+inputMin;
  }


  public int cariMinimumKolom(int inputKolom){
    int i;
    int minimumLokal;
    minimumLokal = infinite;
    for (i=0;i<baris;i++){
      if((tempMatriks[i][inputKolom]) < minimumLokal){
        minimumLokal= tempMatriks[i][inputKolom];
      }
    }
    return minimumLokal;
  }

  public int cariMinimumBaris(int inputBaris){
    int j;
    int minimumLokal;
    minimumLokal = infinite;
    for (j=0;j<kolom;j++){
      if(tempMatriks[inputBaris][j]<minimumLokal){
        minimumLokal= tempMatriks[inputBaris][j];
      }
    }
    return minimumLokal;
  }

  public void copyMatriksFix(int[][] inputMatriks){
    int i,j;
    for(i=0;i<baris;i++){
      for(j=0;j<kolom;j++){
        matriksFix[i][j]=inputMatriks[i][j];
      }
    }
  }

  public void copyTempMatriks(int[][] inputMatriks){
    int i,j;
    for(i=0;i<baris;i++){
      for(j=0;j<kolom;j++){
        tempMatriks[i][j]=inputMatriks[i][j];
      }
    }
  }

  public void copyTempMin(int[][] inputMatriks){
    int i,j;
    for(i=0;i<baris;i++){
      for(j=0;j<kolom;j++){
        tempMin[i][j]=inputMatriks[i][j];
      }
    }
  }

  public void infiniteBaris(int inputBaris){
    int j;
    for (j=0;j<kolom;j++){
      tempMatriks[inputBaris][j]=infinite;
    }
  }

  public void infiniteKolom(int inputKolom){
    int i;
    for (i=0;i<baris;i++){
      tempMatriks[i][inputKolom]=infinite;
    }
  }

  public void infiniteNode(int inputNode){
    tempMatriks[inputNode][0]=infinite;
  }

  public int[][] getMatriks(){
    return matriks;
  }

  public int[][] getMatriksFix(){
    return matriksFix;
  }

  public int[][] getTempMatriks(){
    return tempMatriks;
  }

  public int[][] getTempMin(){
    return tempMin;
  }

  public  int[] getUrutanJalur(){
    return urutanJalur;
  }

  public int getMinimum(){
    return minimum;
  }

  public int getCost(){
    return cost;
  }


  public int getBaris(){
    return baris;
  }

  public int getKolom(){
    return kolom;
  }

}


