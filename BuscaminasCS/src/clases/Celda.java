
package clases;

import forms.areaJuego;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;


public class Celda extends JButton{
     private int x;
     private int y;
     private int tipoCelda;
     private boolean bandera;
     private boolean vis;
 
      Celda celda [][];
      Color color [];
    public Celda(int x, int y) {
        this.x = x;
        this.y = y;
       // this.tipoCelda = tipoCelda;
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
    
    //si es cero la celda se pinta de esta forma
    public void accionesEnCeldaDe0(int x, int y)
    {
        areaJuego.buttons[x][y].setVis(true);
        areaJuego.buttons[x][y].setBackground(new java.awt.Color(240, 240, 240));
        areaJuego.buttons[x][y].setText("");
        areaJuego.buttons[x][y].setForeground(this.color[0]);
    }
    //si es mayor a 0 se pinta de esta forma
    public void accionesEnCeldaMayor0(int x, int y)
    {
        areaJuego.buttons[x][y].setVis(true);
        areaJuego.buttons[x][y].setBackground(new java.awt.Color(240, 240, 240));
        areaJuego.buttons[x][y].setText("" + areaJuego.buttons[x][y].getTipoCelda());
        areaJuego.buttons[x][y].setForeground(this.color[0]);
    }
    //se usa en caso de encontrar otro grupo de 0
    public void accionesEnCelda0ConRecur(int x, int y,int recur)
    {
         areaJuego.buttons[x][y].setBackground(new java.awt.Color(240, 240, 240));
         areaJuego.buttons[x][y].setText("");
         areaJuego.buttons[x][y].setForeground(this.color[0]);
         if (recur < 18 && (areaJuego.buttons[x][y].getVis() == false)) 
         {
             areaJuego.buttons[x][y].setVis(true);
             clic(x, y, recur);
         }
    }
    
    public void clic(int xx, int yy,int recur){
        int xActual = xx;
        int yActual = yy;
        ArrayList<Integer> cerosY = new ArrayList<>();
        ArrayList<Integer> cerosX = new ArrayList<>();
        //izquierda de x
        if(yActual > 0)
        {   
            for(int n = yActual-1;n>-1;n--) //recoore toda la izquierda hasta encontrar un numero mayor a 0
            {
                if (areaJuego.buttons[xActual][n].getTipoCelda() == 0) 
                {
                    accionesEnCeldaDe0(xActual, n);
                    cerosY.add(n);
                } 
                else if (areaJuego.buttons[xActual][n].getTipoCelda() > 0) 
                {
                    accionesEnCeldaMayor0(xActual, n);
                    if((xActual-1) > -1)
                    {
                         //izquina inferior izquierda
                        if(areaJuego.buttons[xActual-1][n].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(xActual-1, n);
                        }
                    }
                    //izquina inferior derecha
                    if((xActual+1) < (areaJuego.filas))
                    {
                        if(areaJuego.buttons[xActual+1][n].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(xActual+1, n);
                        }
                    }
                    break;
                }
                
            }
        }
        //arriba
        if(xActual > 0)
        {
            for(int n = xActual-1;n>-1;n--) //recorre todo arriba hasta encontrar un numero mayor a 0
            {
                if (areaJuego.buttons[n][yActual].getTipoCelda() == 0) 
                {
                   accionesEnCeldaDe0(n, yActual);
                   cerosX.add(n);
                } 
                else if (areaJuego.buttons[n][yActual].getTipoCelda() > 0) 
                {
                    accionesEnCeldaMayor0(n, yActual);
                    //izquina superior izquierda
                    if((yActual-1) > -1)
                    {
                        
                        if(areaJuego.buttons[n][yActual-1].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(n, yActual-1);
                        }
                    }
                    //izquina superior derecha
                    if((yActual+1) < (areaJuego.columnas))
                    {
                        if(areaJuego.buttons[n][yActual+1].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(n, yActual+1);
                        }
                    }
                    break;
                }
            }
        }
        //derecha
        if(yActual < areaJuego.columnas)  //recorre todo a la derecha hasta encontrar un numero mayor a 0
        {
            for(int n = yActual+1;n<areaJuego.columnas;n++)
            {
                if (areaJuego.buttons[xActual][n].getTipoCelda() == 0) 
                {
                    accionesEnCeldaDe0(xActual, n);
                    cerosY.add(n);
                } 
                else if (areaJuego.buttons[xActual][n].getTipoCelda() > 0) 
                {
                    accionesEnCeldaMayor0(xActual, n);
                    if((xActual-1) > -1)
                    {
                        //izquina inferior izquierda
                        if(areaJuego.buttons[xActual-1][n].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(xActual-1, n);
                        }
                    }
                    //izquina inferior derecha
                    if((xActual+1) < (areaJuego.filas))
                    {
                        if(areaJuego.buttons[xActual+1][n].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(xActual+1, n);
                        }
                    }
                    break;
                }
            }
        }
        //abajo
        if(xActual < areaJuego.filas)
        {
            for(int n = xActual+1;n<areaJuego.filas;n++)  //recorre todo abajo hasta encontrar un numero mayor a 0
            {
                if (areaJuego.buttons[n][yActual].getTipoCelda() == 0) 
                {
                    accionesEnCeldaDe0(n, yActual);
                    cerosX.add(n);
                } 
                else if (areaJuego.buttons[n][yActual].getTipoCelda() > 0) 
                {
                    accionesEnCeldaMayor0(n, yActual);
                    //izquina inferior izquierda
                    if((yActual-1) > -1)
                    {
                        
                        if(areaJuego.buttons[n][yActual-1].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(n, yActual-1);
                        }
                    }
                    //izquina inferior derecha
                    if((yActual+1) < (areaJuego.columnas))
                    {
                        if(areaJuego.buttons[n][yActual+1].getTipoCelda() > 0)
                        {
                            accionesEnCeldaMayor0(n, yActual+1);
                        }
                    }
                    break;
                }
            }
        }
        if((cerosX.size()) == 0 && (cerosY.size() == 0)) //si solo la casilla clicleada es 0, se agrega para revisar los alrededores
        {
           cerosX.add(xActual);
           cerosY.add(yActual);
        }
        //recorrer cuales fueron 0 en x
        for(int paraRecorrer = 0;paraRecorrer<cerosY.size();paraRecorrer++)
        {
            int revelarY = cerosY.get(paraRecorrer);
            //arriba
            if(xActual > 0)
            {
                for(int n = xActual-1;n>-1;n--)
                {
                   if (areaJuego.buttons[n][revelarY].getTipoCelda() == 0) 
                   {
                        accionesEnCeldaDe0(n, revelarY);
                        //verifica si es el ultimo 0 de todo arriba
                        if(n-1 == -1)
                        {
                            if((revelarY-1) > -1)
                            {
                                //celda oeste
                                if(areaJuego.buttons[n][revelarY-1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY-1);
                                }
                                if(areaJuego.buttons[n][revelarY-1].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(n, revelarY-1, recur+1);
                                }
                            }
                            //celda este
                            if((revelarY+1) < (areaJuego.columnas))
                            {
                                if(areaJuego.buttons[n][revelarY+1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY+1);
                                }
                                if(areaJuego.buttons[n][revelarY+1].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(n, revelarY+1, recur+1);
                                }
                            }
                        }
                        else
                        {
                            //revisa celda oeste
                            if((revelarY-1) > -1)
                            {

                                if(areaJuego.buttons[n][revelarY-1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY-1);
                                }
                                if(areaJuego.buttons[n][revelarY-1].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(n, revelarY-1, recur+1);
                                }
                            }
                            //revisa celda este
                            if((revelarY+1) < (areaJuego.columnas))
                            {
                                if(areaJuego.buttons[n][revelarY+1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY+1);
                                }
                                if(areaJuego.buttons[n][revelarY+1].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(n, revelarY+1, recur+1);
                                }
                            }
                        }
                    }
                    else if (areaJuego.buttons[n][revelarY].getTipoCelda() > 0) 
                    {
                        accionesEnCeldaMayor0(n, revelarY);
                        //izquina superior izquierda
                        if((revelarY-1) > -1)
                        {

                            if(areaJuego.buttons[n][revelarY-1].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(n, revelarY-1);
                            }
                           if(areaJuego.buttons[n][revelarY-1].getTipoCelda() == 0)
                           {
                                accionesEnCelda0ConRecur(n, revelarY-1, recur+1);
                           }
                        }
                        //izquina superior derecha
                        if((revelarY+1) < (areaJuego.columnas))
                        {
                            if(areaJuego.buttons[n][revelarY+1].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(n, revelarY+1);
                            }
                            if(areaJuego.buttons[n][revelarY+1].getTipoCelda() == 0)
                            {
                               
                                accionesEnCelda0ConRecur(n, revelarY+1, recur+1);
                            }
                        }
                        break;
                    } 
                }
            }
            //abajo
            if(xActual < areaJuego.filas)
            {
                for(int n = xActual+1;n<areaJuego.filas;n++)
                {
                    if (areaJuego.buttons[n][revelarY].getTipoCelda() == 0) 
                    {
                        accionesEnCeldaDe0(n, revelarY);
                        //verifica si es el ultimo 0 de todo abajo
                        if(n+1 == areaJuego.filas)
                        {
                            if((revelarY-1) > -1)
                            {
                                //celda oeste
                                if(areaJuego.buttons[n][revelarY-1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY-1);
                                }
                                if(areaJuego.buttons[n][revelarY-1].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(n, revelarY-1, recur+1);
                                }
                            }
                            //celda este
                            if((revelarY+1) < (areaJuego.columnas))
                            {
                                if(areaJuego.buttons[n][revelarY+1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY+1);
                                }
                                if(areaJuego.buttons[n][revelarY+1].getTipoCelda() == 0)
                                {
                                   
                                    accionesEnCelda0ConRecur(n, revelarY+1, recur+1);
                                }
                            }
                        }
                        else
                        {
                            //revisa celda oeste
                            if((revelarY-1) > -1)
                            {
                                if(areaJuego.buttons[n][revelarY-1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY-1);
                                }
                                if(areaJuego.buttons[n][revelarY-1].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(n, revelarY-1, recur+1);
                                }
                            }
                            //revisa celda este
                            if((revelarY+1) < (areaJuego.columnas))
                            {
                                if(areaJuego.buttons[n][revelarY+1].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(n, revelarY+1);
                                }
                                if(areaJuego.buttons[n][revelarY+1].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(n, revelarY+1, recur+1);
                                }
                            }
                        }
                    } 
                    else if (areaJuego.buttons[n][revelarY].getTipoCelda() > 0) 
                    {
                        accionesEnCeldaMayor0(n, revelarY);
                        //izquierda 
                        if((revelarY-1) > -1)
                        {
                            if(areaJuego.buttons[n][revelarY-1].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(n, revelarY-1);
                            }
                            if(areaJuego.buttons[n][revelarY-1].getTipoCelda() == 0)
                            {
                                
                                accionesEnCelda0ConRecur(n, revelarY-1, recur+1);
                                
                            }
                        }
                        //derecha de el ultimo
                        if((revelarY+1) < (areaJuego.columnas))
                        {
                            if(areaJuego.buttons[n][revelarY+1].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(n, revelarY+1);
                            }
                            if(areaJuego.buttons[n][revelarY+1].getTipoCelda() == 0)
                            {
                                accionesEnCelda0ConRecur(n, revelarY+1, recur+1);
                            }
                        }
                        break;
                    } 
                }
            }
        }
         //recorrer cuales fueron 0 en y
        for(int paraRecorrer = 0;paraRecorrer<cerosX.size();paraRecorrer++)
        {
            int revelarX = cerosX.get(paraRecorrer);
            //izquierda
            if(yActual > 0)
            {
                for(int n = yActual-1;n>-1;n--)
                {
                    if (areaJuego.buttons[revelarX][n].getTipoCelda() == 0) 
                    {
                        accionesEnCeldaDe0(revelarX, n);
                        //verifica si es el ultimo 0 de todo a la izquierda
                        if(n-1 == -1)
                        {
                            if((revelarX-1) > -1 )
                            {
                                //celda arriba
                                if(areaJuego.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX-1, n);
                                }
                                if(areaJuego.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCelda0ConRecur(revelarX-1, n, recur+1);
                                }
                            }
                            //celda abajo
                            if((revelarX+1) < (areaJuego.filas))
                            {
                                if(areaJuego.buttons[revelarX+1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX+1, n);
                                }
                                if(areaJuego.buttons[revelarX+1][n].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(revelarX+1, n, recur+1);
                                }
                            }
                        }
                        else
                        {
                           if((revelarX-1) > -1 )
                           {
                                //revisa celda norte
                                if(areaJuego.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX-1, n);
                                }
                                if(areaJuego.buttons[revelarX-1][n].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(revelarX-1, n, recur+1);   
                                }
                           }
                           //revisa celda sur
                           if((revelarX+1) < (areaJuego.filas))
                           {
                                if(areaJuego.buttons[revelarX+1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX+1, n);
                                }
                                if(areaJuego.buttons[revelarX+1][n].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(revelarX+1, n, recur+1);
                                }
                           }
                        }
                    } 
                    else if (areaJuego.buttons[revelarX][n].getTipoCelda() > 0) 
                    {
                        accionesEnCeldaMayor0(revelarX, n);
                        if((revelarX-1) > -1)
                        {

                            if(areaJuego.buttons[revelarX-1][n].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(revelarX-1, n);
                            }
                            if(areaJuego.buttons[revelarX-1][n].getTipoCelda() == 0)
                            {
                                accionesEnCelda0ConRecur(revelarX-1, n, recur+1);
                            }
                            //veriica si hay otro grupo de 0 en esta posicion
                        }
                        //izquina inferior derecha
                        if((revelarX+1) < (areaJuego.filas))
                        {
                            if(areaJuego.buttons[revelarX+1][n].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(revelarX+1, n);
                            }
                            if(areaJuego.buttons[revelarX+1][n].getTipoCelda() == 0)
                            {
                                accionesEnCelda0ConRecur(revelarX+1, n, recur+1);
                            }
                            //veriica si hay otro grupo de 0 en esta posicion  
                        }
                        break;
                    }
                }
            }
            //derecha
            if(yActual < areaJuego.columnas)
            {
                for(int n = yActual+1;n<areaJuego.columnas;n++)
                {
                    if (areaJuego.buttons[revelarX][n].getTipoCelda() == 0) 
                    {
                        accionesEnCeldaDe0(revelarX, n);
                        //verifica si es el ultimo 0 de todo a la derecha
                        if(n+1 == areaJuego.columnas)
                        {
                            //norte
                            if((revelarX-1) > -1)
                            {
                                if(areaJuego.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX-1, n);
                                }
                                if(areaJuego.buttons[revelarX-1][n].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(revelarX-1, n, recur+1);
                                }
                            }
                            //sur
                            if((revelarX+1) < (areaJuego.filas))
                            {
                                if(areaJuego.buttons[revelarX+1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX+1, n);
                                }
                                if(areaJuego.buttons[revelarX+1][n].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(revelarX+1, n, recur+1);
                                }
                            }
                        }
                        else
                        {
                            if((revelarX-1) > -1)
                            {
                                //practicamente revisa su celda norte
                                if(areaJuego.buttons[revelarX-1][n].getTipoCelda() > 0)
                                {
                                    accionesEnCeldaMayor0(revelarX-1, n);
                                }
                                if(areaJuego.buttons[revelarX-1][n].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(revelarX-1, n, recur+1);
                                }
                            }
                            if((revelarX+1) < (areaJuego.filas))
                            {
                                //practicamente revisa su celda sur
                                if (areaJuego.buttons[revelarX + 1][n].getTipoCelda() > 0) 
                                {
                                    accionesEnCeldaMayor0(revelarX+1, n);
                                }
                                if(areaJuego.buttons[revelarX + 1][n].getTipoCelda() == 0)
                                {
                                    accionesEnCelda0ConRecur(revelarX+1, n, recur+1);
                                }
                            }
                        }
                    } 
                    else if (areaJuego.buttons[revelarX][n].getTipoCelda() > 0) 
                    {
                        accionesEnCeldaMayor0(revelarX, n);
                        if((revelarX-1) > -1)
                        {
                            //norte
                            if(areaJuego.buttons[revelarX-1][n].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(revelarX-1, n);
                            }
                            if(areaJuego.buttons[revelarX-1][n].getTipoCelda() == 0)
                            {
                                accionesEnCelda0ConRecur(revelarX-1, n, recur+1);
                            }
                            //veriica si hay otro grupo de 0 en esta posicion
                        }
                        //izquina inferior derecha
                        if((revelarX+1) < (areaJuego.filas))
                        {
                            //sur
                            if(areaJuego.buttons[revelarX+1][n].getTipoCelda() > 0)
                            {
                                accionesEnCeldaMayor0(revelarX+1, n);
                            }
                            if(areaJuego.buttons[revelarX+1][n].getTipoCelda() == 0)
                            {
                                accionesEnCelda0ConRecur(revelarX+1, n, recur+1);
                            }
                            //veriica si hay otro grupo de 0 en esta posicion
                        }
                        break;
                    }
                }
            }
        }
    }
}
