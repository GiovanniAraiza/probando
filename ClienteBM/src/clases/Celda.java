
package clases;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;


public class Celda extends JButton{
     private int coordenadax;
     private int coordenaday;
     private int tipoCelda;
     private boolean bandera;
     private boolean vis;
 
     Celda celda [][];
     Color color [];
     
     public Celda(int x, int y) {
        coordenadax = x;
        coordenaday = y;
        this.vis=false;
        this.color= new Color[]{Color.BLUE,Color.GREEN,Color.ORANGE,Color.YELLOW,Color.PINK,Color.BLACK,Color.MAGENTA,Color.lightGray};
        this.setPreferredSize(new Dimension(9, 9));
        this.setBackground(new java.awt.Color(0,0,204));
        this.setFont(new java.awt.Font("Tahoma", 1, 14));
        
     }

     public void setTipoCelda(int tipoCelda) {
        this.tipoCelda = tipoCelda;
     } 

     public int getTipoCelda() {
        return tipoCelda;
     }
     
     public boolean getVis() {
        return vis;
     }
     
     public void setVis(boolean vis) {
        this.vis = vis;
     }
     
     public boolean getBandera() {
        return bandera;
     }
     
     public void setBandera(boolean bandera) {
        this.bandera = bandera;
     }
     
}