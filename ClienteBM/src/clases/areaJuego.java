package clases;


import clases.Celda;
import clases.Cronometro;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class areaJuego implements MouseListener{
    
      public static int filas=20;
      public static int columnas=20;
      public static Cronometro crono = new Cronometro();
      
      //Cantidad de minas
      int minas =50;
      //Botones
      public static Celda buttons [][] = new Celda[filas][columnas];
      //G
      //int tablero[][] = new int[filas][columnas];
      
      //Frame a utilizar
      public JFrame frame = new JFrame();
      
       //Panel de boton y cronómetro
       JPanel panel1;
       BoxLayout gbag;
       public JButton boton;
       //Panel de jugadores
       JPanel panel3;
       GridLayout jugadores;
       //Panel de botones
       JPanel panel2;
       GridLayout grid;
       //Variable de clase Cliente
       public static clientebm.ClienteBM varCliente;
      
       
       public void crearTablero(int jugador, int total){
            crearFrame();
            crearPanelSuperior();
            crearSeccionJugadores();
            crearSeccionDelTablero();
            crearEscuchadores();
            frame.pack();
            frame.setVisible(true);
       }
       
       public void crearFrame()
       {
          frame.setMinimumSize(new Dimension(1200, 800));
          frame.setLayout(new BorderLayout());
          frame.setLocationRelativeTo(null);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       }
       //Crea la seccion en la cual incluye el cronometro, jugadores y las banderas del jugador, con botón
       public void crearPanelSuperior()
       {
           //la parte de arriba
            panel1 = new JPanel(); 
            gbag = new BoxLayout(panel1, BoxLayout.X_AXIS);
            panel1.setLayout(gbag);
            boton = new JButton("Empezar");
            boton.setEnabled(false);
            boton.setPreferredSize(new Dimension(200, 100));
            
            panel1.setPreferredSize(new Dimension(550, 70));
            panel1.add(Box.createRigidArea(new Dimension(200, 10)));
            panel1.add(boton);
            panel1.add(Box.createRigidArea(new Dimension(50, 10)));
            panel1.add(crono);
            panel1.add(Box.createRigidArea(new Dimension(150, 10)));
       }
       
       public void crearSeccionJugadores()
       {
          //parte de los jugadores
            panel3 = new JPanel(); 
            jugadores = new GridLayout(2,2);
            panel3.setLayout(jugadores);
            panel3.setPreferredSize(new Dimension(500, 70));
            panel1.add(panel3);
       }
       //Crea la zona donde se juega
       public void crearSeccionDelTablero()
       {
          //el tablero
           panel2 = new JPanel();
           grid = new GridLayout(filas, columnas);
           panel2.setLayout(grid);
           frame.add(panel1, BorderLayout.NORTH);
           frame.pack();
           frame.add(panel2, BorderLayout.CENTER);
           frame.pack();
       }
       
       public void crearEscuchadores()
       {
           //listener al boton de comenzar
            boton.addActionListener((ActionEvent ae) -> {
            varCliente.getOut().println("EMPEZARPARTIDA " + varCliente.getNumSala());
           });
            crono.addPropertyChangeListener((pce) -> {
                String tiempo = crono.getText();
                tiempo = tiempo.substring(tiempo.indexOf(":")+1);
                tiempo = tiempo.substring(tiempo.indexOf(":")+1);
                if(Integer.parseInt(tiempo)==30 || Integer.parseInt(tiempo)==0 )
                {
                    varCliente.getOut().println("hola " + varCliente.getNumSala());
                }
            });
       }
      
       //genera el tablero total, donde se va a jugar
       public void generarAreaJuego(int jug)
       {
          //Crear botones
          for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                buttons[i][j] = new Celda(i, j);
                panel2.add(buttons[i][j]);
                buttons[i][j].setEnabled(false);
            }
          }
          
          panel2.revalidate();
          panel2.repaint();
          //Se habilita area solo al jugador en turno
          habilitarArea(jug);
          
       }
      
       //Actualizar cuando se agrega bomba después de 30 segundos
       public void cambiarTableroBombaAgregada(int x ,int y){
          if(verSiVista(x, y)){
              int a= buttons[x][y].getTipoCelda()+1;
              areaJuego.buttons[x][y].setText(String.valueOf(a));
              areaJuego.buttons[x][y].setTipoCelda(a);
          }
          panel2.revalidate();
          panel2.repaint();
       }
      
       //Habilitar todo el tablero al dar clic en la zona
       public void habilitarTodo(int jug){
          switch(jug){
              case 1:
                 for(int i=0;i<filas;i++){
                     for(int j=1;j<columnas;j++){
                        buttons[i][j].setEnabled(true);
                        accion(i, j);
                     }
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
             case 2:
                 for(int i=1;i<filas;i++){
                     for(int j=0;j<columnas;j++){
                         accion(i, j);
                        buttons[i][j].setEnabled(true);
                        
                     }
                 }
                panel2.revalidate();
                 panel2.repaint();
                 break;
             case 3:
                 for(int i=0;i<filas;i++){
                     for(int j=0;j<columnas-1;j++){
                         accion(i, j);
                        buttons[i][j].setEnabled(true);
                     }
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
             case 4:
                 for(int i=0;i<filas-1;i++){
                     for(int j=0;j<columnas;j++){
                         accion(i, j);
                        buttons[i][j].setEnabled(true);
                     }
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
         }
       
       }
       //habilitar area específica de cada jugador, con colores distintos de acuerdo al número
       public void habilitarArea(int jug){
          switch(jug){
             case 1:
                 for(int i=0;i<filas;i++){
                     buttons[i][0].setEnabled(true);
                     buttons[i][0].setBackground(new java.awt.Color(254, 207, 207));
                     accion(i, 0);
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
             case 2:
                 for(int i=0;i<columnas;i++){
                     buttons[0][i].setEnabled(true);
                     buttons[0][i].setBackground(new java.awt.Color(249, 254, 207));
                     accion(0,i);
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
             case 3:
                 for(int i=0;i<columnas;i++){
                     buttons[i][columnas-1].setEnabled(true);
                     buttons[i][columnas-1].setBackground(new java.awt.Color(213, 254, 207));
                     accion(i,columnas-1);
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
             case 4:
                 for(int i=0;i<filas;i++){
                     buttons[filas-1][i].setEnabled(true);
                     buttons[filas-1][i].setBackground(new java.awt.Color(207, 254, 242));
                     accion(filas-1,i);
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
          }
       }
      
       //Colorear area especifica dependiendo jugador
       public void habilitarColor(int jug){
          switch(jug){
             case 1:
                 for(int i=0;i<filas;i++){
                     if(!buttons[i][0].getVis() && !buttons[i][0].getBandera()){
                          buttons[i][0].setBackground(new java.awt.Color(0, 0, 204));
                     }else if(buttons[i][0].getVis() && !buttons[i][0].getBandera()){
                          buttons[i][0].setBackground(new java.awt.Color(240, 240, 240));
                     }
                     
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
             case 2:
                 for(int i=0;i<columnas;i++){
                     if(!buttons[0][i].getVis() && !buttons[0][i].getBandera()){
                          buttons[0][i].setBackground(new java.awt.Color(0, 0, 204));
                     }else if(buttons[0][i].getVis() && !buttons[0][i].getBandera()){
                          buttons[0][i].setBackground(new java.awt.Color(240, 240, 240));
                     }
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
             case 3:
                 for(int i=0;i<columnas;i++){
                     if(!buttons[i][columnas-1].getVis() && !buttons[i][columnas-1].getBandera()){
                          buttons[i][columnas-1].setBackground(new java.awt.Color(0, 0, 204));
                     }else if(buttons[i][columnas-1].getVis() && !buttons[i][columnas-1].getBandera()){
                          buttons[i][columnas-1].setBackground(new java.awt.Color(240, 240, 240));
                     }
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
             case 4:
                 for(int i=0;i<filas;i++){
                     if(!buttons[filas-1][i].getVis() && !buttons[filas-1][i].getBandera()){
                          buttons[filas-1][i].setBackground(new java.awt.Color(0, 0, 204));
                     }else if(buttons[filas-1][i].getVis() && !buttons[filas-1][i].getBandera()){
                          buttons[filas-1][i].setBackground(new java.awt.Color(240, 240, 240));
                     }
                 }
                 panel2.revalidate();
                 panel2.repaint();
                 break;
          }
       }
       //Al dar clic izquierdo, dependiendo jugador, se pone color de bandera
       public void ponerBandera(int x, int y,int jug)
       {
           buttons[x][y].setBandera(true);
           switch (jug) {
              case 1:
                  buttons[x][y].setBackground(new java.awt.Color(23, 192, 26));
                  areaJuego.buttons[x][y].setText("B");
                  areaJuego.buttons[x][y].setFont(new java.awt.Font("Tahoma", 1, 10));
                  areaJuego.buttons[x][y].setForeground(Color.decode("#FFFFFF"));
                  break;
              case 2:
                  buttons[x][y].setBackground(new java.awt.Color(213, 58, 58));
                  areaJuego.buttons[x][y].setText("B");
                  areaJuego.buttons[x][y].setFont(new java.awt.Font("Tahoma", 1, 10));
                  areaJuego.buttons[x][y].setForeground(Color.decode("#FFFFFF"));
                  break;
              case 3:
                  buttons[x][y].setBackground(new java.awt.Color(172, 178, 59));
                  areaJuego.buttons[x][y].setText("B");
                  areaJuego.buttons[x][y].setFont(new java.awt.Font("Tahoma", 1, 10));
                  areaJuego.buttons[x][y].setForeground(Color.decode("#FFFFFF"));
                  break;
              case 4:
                  buttons[x][y].setBackground(new java.awt.Color(142, 75, 205));
                  areaJuego.buttons[x][y].setText("B");
                  areaJuego.buttons[x][y].setFont(new java.awt.Font("Tahoma", 1, 10));
                  areaJuego.buttons[x][y].setForeground(Color.decode("#FFFFFF"));
                  break;
              default:
                  break;
           }
       }
       //Inicia cronómetro, que sirve para ver cuando se agregará bomba
       public void iniciarCrono()
       {
          crono.iniciarHilo();
          crono.run();
       }
       public void pararCrono()
       {
          crono.pararHilo();
       }
       public void quitarBandera(int x, int y)
       {
          buttons[x][y].setBandera(false);
          buttons[x][y].setBackground(new java.awt.Color(0, 0, 204));
          buttons[x][y].setForeground(Color.decode("#000000"));
          buttons[x][y].setFont(new java.awt.Font("Tahoma", 1, 14));
          areaJuego.buttons[x][y].setText("");
       }
       public void quitarBanderaConvista(int x, int y)
       {
          buttons[x][y].setBandera(false);
          buttons[x][y].setBackground(new java.awt.Color(240, 240, 240));
          buttons[x][y].setForeground(Color.decode("#000000"));
          buttons[x][y].setFont(new java.awt.Font("Tahoma", 1, 14));
          areaJuego.buttons[x][y].setText("");
       }
       public void celdaDescubierta(int x, int y)
       {
          if(!verSiBandera(x, y))
          {
             buttons[x][y].setBackground(new java.awt.Color(240, 240, 240));
          }
       }
      
       public void marcarVista(int x, int y)
       {
          buttons[x][y].setVis(true);
       }
       public boolean verSiVista(int x, int y)
       {
          return buttons[x][y].getVis();
       }

       public void setMinas(int minas) {
          this.minas = minas;
       }
       //Dependiendo el contenido, es lo que se muestra a través del metodo
       public void mostrarContenidoVisualCelda(int x, int y, int tipo)
       {
          if(tipo == 0)
          {
              buttons[x][y].setTipoCelda(tipo);
              if(!verSiBandera(x, y))
              {
                  buttons[x][y].setText("");
              }
          }
          else if(tipo == -1)
          {
              buttons[x][y].setTipoCelda(tipo);
              if(!verSiBandera(x, y))
              {
                  buttons[x][y].setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/min2.png")));
              }
          }else if(tipo > 0)
          {
              buttons[x][y].setTipoCelda(tipo);
              if(!verSiBandera(x, y))
              {
                  buttons[x][y].setText(String.valueOf(tipo));
              }
          }
       }
       public void mostrarContenidoConBandera(int x, int y)
       {
          if(buttons[x][y].getTipoCelda() == 0)
          {
              buttons[x][y].setText(""); 
          }
          else if(buttons[x][y].getTipoCelda() == -1)
          {
            buttons[x][y].setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/min2.png")));
          }else if(buttons[x][y].getTipoCelda() > 0)
          {
             buttons[x][y].setText(String.valueOf(buttons[x][y].getTipoCelda()));
          }
         
       }
      
       public int getContenido(int x, int y)
       {
          return buttons[x][y].getTipoCelda();
       }
       public boolean verSiBandera(int x, int y)
       {
          return buttons[x][y].getBandera();
       }
     
     
       JLabel lb1, lb2,lb3,lb4;
       //Para actualizar zona superior
       public void editarDatosZonaJugador(int jug, int totaljug, int band)
       {
          int cont = 0;
          for(int x = 0;x<totaljug;x++)
          { 
              cont++;
              if (x == 0) {
                  panel3.removeAll();
                  if (cont == jug) {
                      frame.setTitle("Minesweeper: Jugador " + String.valueOf(cont));
                      lb1 = new JLabel(">>>jugador " + cont + "- banderas: " + band);
                      panel3.add(lb1);
                      panel3.revalidate();
                      panel3.repaint();
                  } else {
                      lb1 = new JLabel("     jugador " + cont);
                      lb1.setForeground(Color.decode("#298037"));
                      panel3.add(lb1);
                  }
              }
              if (x == 1) {
                  if (cont == jug) {
                      frame.setTitle("Minesweeper: Jugador " + String.valueOf(cont));
                      lb2 = new JLabel(">>>jugador " + cont + "- banderas: " + band);
                      //  lb2.setForeground(Color.decode("#298037"));
                      panel3.add(lb2);
                  } else {
                      lb2 = new JLabel("     jugador " + cont);
                      lb2.setForeground(Color.decode("#298037"));
                      panel3.add(lb2);
                  }
              }
              if (x == 2) {

                  if (cont == jug) {
                      frame.setTitle("Minesweeper: Jugador " + String.valueOf(cont));
                      lb3 = new JLabel(">>>jugador " + cont + "- banderas: " + band);
                      panel3.add(lb3);
                  } else {
                      lb3 = new JLabel("     jugador " + cont);
                      lb3.setForeground(Color.decode("#298037"));
                      panel3.add(lb3);
                  }
              }
              if(x == 3){
                  if(cont == jug){
                      frame.setTitle("Minesweeper: Jugador " + String.valueOf(cont));
                      lb4 = new JLabel(">>>jugador " + cont + "- banderas: " + band);
                      panel3.add(lb4);
                  }else{
                      lb4 = new JLabel("     jugador " + cont);
                      lb4.setForeground(Color.decode("#298037"));
                      panel3.add(lb4);
                  }
              }
           }
        
       }
      
     

       public Celda[][] getButtons() {
          return buttons;
       }
       public int getMinas() {
          return minas;
       }
       public int getFilas() {
          return filas;
       }

       public int getColumnas() {
          return columnas;
       }
       public void habilitarBoton()
       {
          boton.setEnabled(true);
       }
       public void deshabilitarBoton()
       {
          boton.setEnabled(false);
       }
       
       MouseListener m1;
    
       //Se usa para avisar que perdió jugador, además de deshabilitar todo
       public void mostrarPerdedor(){
          varCliente.setConVida(false);
          for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                buttons[i][j].setEnabled(false);
            }
          }
         
          JOptionPane.showMessageDialog(null, "Perdiste");
       }
     
       //Muestra puntuaje
       public void mostrarGanador(String t)
       {
           JPanel j = new JPanel();
           j.add(new JLabel(t));
           JOptionPane.showMessageDialog(null, j,"Puntajes",-1);
       }
    
       //Es utilizado para agregarle el evento a cada boton
       public void accion(int il, int jl)
       {
        
          m1 = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              if(me.getButton()==1 && varCliente.isConVida())
                {   
                    varCliente.getOut().println("IZQUIERDO " +il + " " + jl + " sala " + varCliente.getNumSala());
                    if(!varCliente.isPicar()){
                        //Dependiendo el jugador, se pone el tablero normal al dar clic
                     habilitarColor(varCliente.getJugador());
                     habilitarTodo(varCliente.getJugador());
                     varCliente.setPicar(true);
                    }
                }
                else if(me.getButton()==3 && varCliente.isConVida())
                {
                    varCliente.getOut().println("DERECHO " + il + " " + jl + " sala " + varCliente.getNumSala() + "u " + varCliente.getBand());
                }
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