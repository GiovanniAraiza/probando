
package forms;


import clases.Celda;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public final class areaJuego implements MouseListener{
    
      public static int filas=20;
      public static int columnas=20;
       
      int minas =50;
      public  Celda buttons [][] = new Celda[filas][columnas];
      int tablero[][] = new int[filas][columnas];
      int mina[][] = new int[filas][columnas];

    
      
      public JFrame frame = new JFrame("Minesweeper");
    
   //el area con el que cada sala juea
    public areaJuego() 
    {
        generaMatriz();
          for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                buttons[i][j] = new Celda(i, j);
                buttons[i][j].setTipoCelda(tablero[i][j]);
                accion(i, j);
           }
       }
    }
      
      public void crearTablero(int jugador){
          
          frame.setMinimumSize(new Dimension(1200, 800));
          frame.setLayout(new BorderLayout());
           frame.setLocationRelativeTo(null);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
           //la parte de arriba
            JPanel j1 = new JPanel(); 
            BoxLayout gbag = new BoxLayout(j1, BoxLayout.X_AXIS);
            j1.setLayout(gbag);
            JButton boton = new JButton("Empezar");
            boton.setEnabled(false);
            boton.setPreferredSize(new Dimension(200, 100));
            
            j1.setPreferredSize(new Dimension(550, 70));
            j1.add(Box.createRigidArea(new Dimension(200, 10)));
            j1.add(boton);
            j1.add(Box.createRigidArea(new Dimension(200, 10)));
            
            //parte de los jugadores
            JPanel j3 = new JPanel(); 
            GridLayout jugadores = new GridLayout(2,2);
            j3.setLayout(jugadores);
            int cont = 0;
            for(int x = 0;x<2;x++)
            {
                for(int y = 0;y<2;y++)
                {
                    cont++;
                    if(cont == jugador)
                    {
                        JLabel lb = new JLabel(">>>jugador "+cont+"- banderas: 20");
                        j3.add(lb);
                    }
                    else
                    {
                        JLabel lb = new JLabel("     jugador "+cont+" - banderas: 20");
                        j3.add(lb);
                    }

                }
            } 
        
            j3.setPreferredSize(new Dimension(500, 70));
            j1.add(j3);
            
            //el tablero
            JPanel j2 = new JPanel(); 
            GridLayout grid = new GridLayout(filas,columnas);
            j2.setLayout(grid);
            frame.add(j1,BorderLayout.NORTH);
            frame.pack();
            frame.add(j2,BorderLayout.CENTER);
            frame.pack();
           
          generaMatriz();
          for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                buttons[i][j] = new Celda(i, j);
                buttons[i][j].setTipoCelda(tablero[i][j]);
                j2.add(buttons[i][j]);
                buttons[i][j].setEnabled(true);
                accion(i, j);
           }
       }
       
         frame.pack();
         frame.setVisible(true);
      } 
      
      
      //Generar matríz que se usará en el juego
      public void generaMatriz(){
       Random r = new Random();
        
        while(minas > 0) {
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
        String Green = "\u001B[42m";
        String Defauld = "\u001B[0m";
        
        for(int i = 0; i < filas; i++) {
            for(int j = 0; j < columnas; j++) {
                if (tablero[i][j] == -1) {
                    System.out.print(Green + tablero[i][j] + Defauld + "\t");
                } else {
                    System.out.print(tablero[i][j] + "\t");
                }
            }
            System.out.println();
        }
   
    }
      //sirve para ver la matriz para efectos de prueba
      public void imprimirMatriz()
      {
          String Green = "\u001B[42m";
        String Defauld = "\u001B[0m";
        
            for(int i = 0; i < filas; i++) {
                for(int j = 0; j < columnas; j++) {
                    if (tablero[i][j] == -1) {
                        System.out.print(Green + tablero[i][j] + Defauld + "\t");
                    } else {
                        System.out.print(tablero[i][j] + "\t");
                    }
                }
                System.out.println();
            }
      }
      public String editarJugador()
      {
          return new String();
      }
    //metodo que sirve para llamar al metodo para obtener todas las celdas descubiertas
    public String resultadoCoordenadas(int x, int y, areaJuego f)
    {
        String gel = f.buttons[x][y].clic(x, y, 0 , f) + x + " " + y + " ";
       // System.out.println(gel);
        return gel;
    }
    public boolean verSiVista(int x, int y)
    {
          return buttons[x][y].getVis();
    }
    public void ponerVista(int x, int y)
    {
         buttons[x][y].setVis(true);
    }
    public boolean verSiBandera(int x, int y)
    {
          return buttons[x][y].getBandera();
    }
    public void ponerBandera(int x, int y)
    {
        buttons[x][y].setBandera(true);
    }
    public void quitarBandera(int x, int y)
    {
        buttons[x][y].setBandera(false);
    }
    //sirve para obtener el contenido de cada celda
    public String obtenerDatos(int x, int y)
    {
        String datos = "";
        datos = datos + buttons[x][y].getTipoCelda();
        return datos;
    }
    
    public int obtenerContenido(int x, int y)
      {
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
    
    public void accion(int il, int jl)
    {
        MouseListener m1;
        m1 = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if(me.getButton()==1)
                {
                    
                }
                else if(me.getButton()==3)
                {
                   
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        buttons[il][jl].addMouseListener(m1);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
