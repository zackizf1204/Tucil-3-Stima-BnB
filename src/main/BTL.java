package main;

import java.io.*;

/**
 * Nama         : Zacki Zulfikar Fauzi
 * <br>NIM          : 13515147
 * <br>Nama File    : BTL.java
 * <br>Tanggal      : 02-Apr-17
 */
public class BTL {

  public int[][] matriks;
  private int[][] tempMatriks;
  private int[][] queueUrutan;
  private int baris;
  private int kolom;
  private int infinite = 999;
  private int[] queueCost;
  public int cost;
  private int nodeMin;
  private int costLokal;
  private int[] urutanJalur;
  private int pilihan;
  private int jumlahJalur;
  private boolean[] nodeHidup;
  public int[][] edgeMinimum;

  public BTL() throws IOException{
    int i,j;
    baris =0;
    kolom = 0;
    matriks = new int[3][3];
    for(i=0;i<3;i++){
      for(j=0;j<3;j++){
        matriks[i][j]=0;
      }
    }
    readFile("inputBTL.txt", "outputBTL.txt");
    urutanJalur =  new int[baris+1];
    for(i=0;i<baris;i++){
      urutanJalur[i]=infinite;
    }
    queueUrutan = new int[factorial(baris)*baris][kolom+2];
    for(i=0;i<factorial(baris)*baris;i++){
      for(j=0;j<kolom+2;j++){
        queueUrutan[i][j]=infinite;
      }
    }
    nodeHidup = new boolean[factorial(baris)*baris];
    for(i=0;i<factorial(baris)*baris;i++){
      nodeHidup[i]=false;
    }
    queueCost = new int[factorial(baris)*baris];
    for(i=0;i<factorial(baris)*baris;i++){
      queueCost[i]=infinite;
    }
    tempMatriks= new int[baris][kolom];
    copyTempMatriks(matriks);
    cost=0;
    pilihan=0;
    urutanJalur[0]=0;
    urutanJalur[baris]=0;
    jumlahJalur=0;
    edgeMinimum = new int[baris][2];
    cariMinimum();
  }

  public static int factorial(int n) {
    int fact = 1; // this  will be the result
    for (int i = 1; i <= n; i++) {
      fact *= i;
    }
    return fact;
  }

  public void buatGraf() throws IOException {
    File file = new File("GrafBTL.puml");
    file.createNewFile();
    FileWriter writer = new FileWriter(file);
    int i,j,k;
    k=0;
    Integer temp;
    writer.write("@startdot\n");
    writer.write("graph graf {\n");
    for(i=0;i<baris;i++){
      for(j=i;j<kolom;j++){
        if(matriks[i][j]!=infinite) {
          writer.write(i+49);
          writer.write(" -- ");
          writer.write(j+49);
          writer.write(" [label=");
          temp=matriks[i][j];
          writer.write(String.valueOf(temp));
          if(((j==urutanJalur[findUrutan(i)+1])&&(i==urutanJalur[findUrutan(i)]))||((j==urutanJalur[findUrutan(j)])&&(i==urutanJalur[findUrutan(j)+1]))){
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
  public void printEdgeMinimum(){
    int i,j;
    for(i=0;i<baris;i++){
      for(j=0;j<2;j++){
        if(j==1){
          System.out.println(edgeMinimum[i][j]);
        }
        else{
          System.out.print(edgeMinimum[i][j]);
          System.out.print(" ");
        }
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

  public void cariMinimum() {
    int min1, min2, j, i;
    for (i = 0; i < baris; i++) {
      min1 = infinite;
      min2 = infinite;
      for (j = 0; j < kolom; j++) {
        if (matriks[i][j] < min1) {
          min1 = matriks[i][j];
          edgeMinimum[i][0] = j;
        }
      }
      for (j = 0; j < kolom; j++) {
        if ((matriks[i][j] < min2) && (matriks[i][j] != min1)) {
          min2 = matriks[i][j];
          edgeMinimum[i][1] = j;
        }
      }
    }
  }

  public void testCost(){
    queueUrutan[1][0]=0;
    queueUrutan[1][1]=1;
    queueUrutan[1][2]=2;
    queueUrutan[1][3]=3;
    queueUrutan[1][4]=4;
    queueUrutan[1][5]=5;
    queueUrutan[1][6]=0;
  }
  public void solveBTL() throws IOException {
    queueUrutan[0][0]=0;
    queueUrutan[0][kolom]=0;
    queueUrutan[0][kolom+1]=1;
    nodeHidup[0]=true;
    int i,j,k,l;
    queueCost[0]=hitungCost(0);
    costMinimum();
    System.out.println(nodeMin);
    while(queueUrutan[nodeMin][kolom+1]<baris){
      for(l=0;l<jumlahJalur+1;l++){
        System.out.println(nodeHidup[l]);
      }
      System.out.println();
      System.out.println(queueUrutan[nodeMin][kolom+1]);
      System.out.println(nodeMin);
      for(i=0;i<jumlahJalur+1;i++){
        for(j=0;j<kolom+2;j++){
          if(j==kolom+1){
            System.out.println(queueUrutan[i][j]);
          }
          else{
            System.out.print(queueUrutan[i][j]);
            System.out.print(" ");
          }
        }
      }
      for(i=0;i<baris;i++){
        if(cekQueue(nodeMin,i)==false){
          for(j=0;j<queueUrutan[nodeMin][kolom+1];j++){
            queueUrutan[jumlahJalur+1][j]=queueUrutan[nodeMin][j];
          }
          queueUrutan[jumlahJalur+1][queueUrutan[nodeMin][kolom+1]]=i;
          queueUrutan[jumlahJalur+1][kolom]=0;
          queueUrutan[jumlahJalur+1][kolom+1]=queueUrutan[nodeMin][kolom+1]+1;
          nodeHidup[jumlahJalur+1]=true;
          queueCost[jumlahJalur+1]=hitungCost(jumlahJalur+1);
          jumlahJalur++;
        }
      }
      cost=999;
      nodeHidup[nodeMin]= false;
      costMinimum();
    }
    salinUrutan();
    buatGraf();
  }

  public int hitungCost(int inputIndex){
    int i,j, costLokal;
    costLokal=0;
    int[][] tempNodeMin;
    tempNodeMin = new int[baris][2];
    for(i=0;i<baris;i++){
      for(j=0;j<2;j++){
        tempNodeMin[i][j]=edgeMinimum[i][j];
      }
    }
    for(j=0;j<kolom;j++){
      if((queueUrutan[inputIndex][j]!=infinite)&&(queueUrutan[inputIndex][j+1]!=infinite)){
          tempNodeMin[queueUrutan[inputIndex][j]][1]=queueUrutan[inputIndex][j+1];
          tempNodeMin[queueUrutan[inputIndex][j+1]][0]=queueUrutan[inputIndex][j];
      }
    }
    for(i=0;i<baris;i++){
      for(j=0;j<2;j++){
        costLokal=costLokal+matriks[i][tempNodeMin[i][j]];
      }
    }
    for(i=0;i<baris;i++){
      for(j=0;j<2;j++){
        edgeMinimum[i][j]=tempNodeMin[i][j];
      }
    }
    costLokal=costLokal/2;
    return costLokal;
  }

  public void costMinimum(){
    int i;
    for(i=0;i<jumlahJalur;i++){
      if((queueCost[i] < cost)&&(nodeHidup[i]==true)){
        cost=queueCost[i];
        nodeMin=i;
      }
    }
  }
  public boolean cekQueue(int inputBaris, int inputNode){
    boolean found;
    found=false;
    int i;
    for(i=0;i<kolom;i++){
      if(queueUrutan[inputBaris][i]==inputNode){
        found=true;
      }
    }
    return found;
  }

  public void salinUrutan(){
    int i;
    for(i=0;i<baris+1;i++){
      urutanJalur[i]=queueUrutan[nodeMin][i];
      System.out.println(urutanJalur[i]);
    }
  }

}
