
package clases;

import clases.Celda;
import java.util.Random;
import javax.swing.JFrame;


public final class areaJuego{
    
      public static int filas=20;
      public static int columnas=20;
       
      int minas =50;
      public  Celda buttons [][] = new Celda[filas][columnas];
      int tablero[][] = new int[filas][columnas];
     // int mina[][] = new int[filas][columnas];
     //int resMinas=50;
    
      
      public JFrame frame = new JFrame("Minesweeper");
    
      //el area con el que cada sala juea
      public areaJuego() 
      {
          generaMatriz();
          for(int i=0;i<filas;i++){
             for(int j=0;j<columnas;j++){
                  buttons[i][j] = new Celda(i, j);
                  buttons[i][j].setTipoCelda(tablero[i][j]);
                
             }
          }
      }
      
      //Generar matríz que se usará en el juego
      public void generaMatriz(){
        Random r = new Random();
        
        while(minas > 0){
           int x = r.nextInt(filas);
           int y = r.nextInt(columnas);
            
           if (tablero[x][y] != -1) {
              tablero[x][y] = -1;
              minas--;
           }
        }
        
        for (int i = 0; i < filas; i++) {
           for (int j = 0; j < columnas; j++) {
              for (int k = i - 1; k <= i + 1; k++) {
                 for (int l = j - 1; l <= j + 1; l++) {
                    if (k >= 0 && l >= 0 && k < filas && l < columnas) {
                       if (tablero[k][l] == -1 && tablero[i][j] != -1) {
                                tablero[i][j]++;
                       }
                    }
                 }
              }
           }
        }
        
      }
      
      public int contarVistos(){
         int vistos=0;
         for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
              
               if(!buttons[i][j].getVis()){
                   vistos++;
                   //System.out.println(buttons[i][j].getTipoCelda());
               }
            }
         }
         return vistos;
      }
      
      public int contarNoVistosBomba(){
         int vistos=0;
         for(int i=0;i<filas;i++){
           for(int j=0;j<columnas;j++){
               if(!buttons[i][j].getVis() && buttons[i][j].getTipoCelda() == -1){
                   vistos++;
               }
           }
         }
         return vistos;
      }
   
      //Genera otra bomba y con estos valores genera un string para que se actualice el cliente
      public String generarOtra(){
         int cc=0;
         Random r = new Random();
         boolean p=false;
         String acumulado="";
         
         while(!p) {
            int x = r.nextInt(filas);
            int y = r.nextInt(columnas);
            if (!buttons[x][y].getVis() && buttons[x][y].getTipoCelda()!=-1 && !buttons[x][y].getBandera()) {
                buttons[x][y].setTipoCelda(-1);
                buttons[x][y].setVis(false);
                //System.out.println("x " + x + " y" + y);
                p=true;
                
                //Esto es para actualizar todos los vecinos
                //Si no se debe de actualizar, no se guarda para que lo use el cliente
                if (x - 1 >= 0 && y - 1 >= 0 ) {
                   if(buttons[x-1][y-1].getTipoCelda()!= -1)
                   {
                      acumulado += String.valueOf(x - 1) + "," + String.valueOf(y - 1) + " ";
                      cc++;
                      int c= buttons[x-1][y-1].getTipoCelda();
                      buttons[x-1][y-1].setTipoCelda(c + 1);
                   }
                }

                if (x - 1 >= 0 ) {
                   if(buttons[x-1][y].getTipoCelda()!=-1)
                   {
                       acumulado += String.valueOf(x - 1) + "," + String.valueOf(y) + " ";
                       cc++;
                       int c= buttons[x-1][y].getTipoCelda();
                       buttons[x-1][y].setTipoCelda(c + 1);
                   }
                }
                if (x - 1 >= 0 && y + 1 < columnas) {
                   if(buttons[x-1][y+1].getTipoCelda()!=-1)
                   {
                       acumulado += String.valueOf(x - 1) + "," + String.valueOf(y + 1) + " ";
                       cc++;
                       int c= buttons[x-1][y+1].getTipoCelda();
                       buttons[x-1][y+1].setTipoCelda(c + 1);
                   }
                }

                //////////////
                if (y - 1 >= 0){
                   if(buttons[x][y-1].getTipoCelda()!=-1 )
                   {
                       acumulado += String.valueOf(x) + "," + String.valueOf(y - 1) + " ";
                       cc++;
                       int c= buttons[x][y-1].getTipoCelda();
                       buttons[x][y-1].setTipoCelda(c + 1);
                   }
                }
                if (y + 1 < columnas ) {
                   if(buttons[x][y+1].getTipoCelda()!=-1)
                   {
                       acumulado += String.valueOf(x) + "," + String.valueOf(y + 1) + " ";
                       cc++;
                       int c= buttons[x][y+1].getTipoCelda();
                       buttons[x][y+1].setTipoCelda(c + 1);
                   }
                }
                ////////////////
                if (x + 1 < filas && y - 1 >= 0) {
                   if(buttons[x+1][y-1].getTipoCelda()!=-1)
                   {
                       acumulado += String.valueOf(x + 1) + "," + String.valueOf(y - 1) + " ";
                       cc++;
                       int c= buttons[x+1][y-1].getTipoCelda();
                       buttons[x+1][y-1].setTipoCelda(c + 1);
                   }
                }
                if (x + 1 < filas ) {
                   if(buttons[x+1][y].getTipoCelda()!=-1)
                   {
                       acumulado += String.valueOf(x + 1) + "," + String.valueOf(y) + " ";
                       cc++;
                       int c= buttons[x+1][y].getTipoCelda();
                       buttons[x+1][y].setTipoCelda(c + 1);
                   }
                }
                if (x + 1 < filas && y + 1 < columnas) {
                   if(buttons[x+1][y+1].getTipoCelda()!=-1)
                   {
                       cc++;
                       acumulado += String.valueOf(x + 1) + "," + String.valueOf(y + 1) + " ";
                       int c= buttons[x+1][y+1].getTipoCelda();
                       buttons[x+1][y+1].setTipoCelda(c + 1);
                   }
                }
            }
         }
         acumulado+=cc;
         return acumulado;
        
      }
      //sirve para ver la matriz para efectos de prueba
      public void imprimirMatriz()
      {
         for(int i = 0; i < filas; i++) {
            for(int j = 0; j < columnas; j++) {
               if(buttons[i][j].getTipoCelda() == -1){
                   System.out.print(buttons[i][j].getTipoCelda() + "\t");
               }else{
                   System.out.print(buttons[i][j].getTipoCelda() + "\t");
               }
            }
            System.out.println();
         }
      }
      
      public String editarJugador(){
          return new String();
      }
      
      //metodo que sirve para llamar al metodo para obtener todas las celdas descubiertas
      public String resultadoCoordenadas(int x, int y, areaJuego f){
         String gel = f.buttons[x][y].clic(x, y, 0 , f) + x + " " + y + " ";
          return gel;
      }
      
      public boolean verSiEstaVista(int x, int y){
         return buttons[x][y].getVis();
      }
      
      public void ponerVista(int x, int y){
         buttons[x][y].setVis(true);
      }
      
      public void quitarVista(int x, int y){
         buttons[x][y].setVis(false);
      }
      
      public boolean verSiHayBandera(int x, int y){
         return buttons[x][y].getBandera();
      }
      
      public void ponerBandera(int x, int y){
         buttons[x][y].setBandera(true);
      }
      
      public void quitarBandera(int x, int y) {
          buttons[x][y].setBandera(false);
      }

      //sirve para obtener el contenido de cada celda
      public String obtenerDatos(int x, int y) {
          String datos = "";
          datos = datos + buttons[x][y].getTipoCelda();
          return datos;
      }

      public int obtenerContenido(int x, int y) {
          return buttons[x][y].getTipoCelda();
      }

      public int getFilas() {
          return filas;
      }

      public int[][] getTablero() {
          return tablero;
      }

      public int getColumnas() {
          return columnas;
      }
    

}
